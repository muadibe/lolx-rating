package pl.dorobie.rating.domain

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pl.dorobie.rating.domain.model.UpdateRating
import pl.dorobie.rating.domain.model.UserRating
import pl.dorobie.rating.domain.support.Comment

@Component
class RatingService {

    private static final Logger log = LoggerFactory.getLogger(RatingService.class);

    @Autowired
    RatingRepository ratingRepository

    @Autowired
    UserRatingRepository userRatingRepository

    UserRating add(UpdateRating updateRating) {
        if (updateRating.customerId == null){
            return null;
        }
        if (updateRating.userId.equals(updateRating.customerId)){
            return null;
        }
        updateRating.date = new Date()
        updateRating.id = getUpdateRatingId(updateRating.announceId, updateRating.customerId)
        def oldUpdateRating = getVoterRate(updateRating.customerId, updateRating.announceId)

        def userRating
        if (oldUpdateRating != null){
            userRating = changeVote(oldUpdateRating, updateRating)
        } else {
            userRating = addNewVote(updateRating)
        }
        userRating
    }

    private UserRating addNewVote(UpdateRating updateRating) {
        log.info("Add vote: {}", updateRating)

        def result = ratingRepository.save(updateRating)
        def userRating = userRatingRepository.getByUserId(result.userId)
        if (userRating == null) {
            userRating = new UserRating(likeCount: 0, id: result.userId, starRate: 0, lastComments: [])
        }
        if (updateRating.type == "LIKE" && updateRating.rate > 0) {
            userRating.likeCount += 1
            updateLastComments(updateRating, userRating)
        }
        if (updateRating.type == "STAR" && updateRating.rate >= 0 && updateRating.rate <= 5) {
            userRating.starRateSum += updateRating.rate
            userRating.starRateCount += 1
            userRating.starRate = userRating.starRateSum / userRating.starRateCount
            updateLastComments(updateRating, userRating)
        }
        if (updateRating.type == "COMMENT") {
            updateLastComments(updateRating, userRating)
        }
        log.info("Saving user rating: {}", userRating)
        userRatingRepository.save(userRating)
    }

    private UserRating changeVote(UpdateRating oldUpdateRating, UpdateRating updateRating) {
        log.info("Replacing vote: old:{} new:{}", oldUpdateRating, updateRating)

        def result = ratingRepository.save(updateRating)
        def userRating = userRatingRepository.getByUserId(result.userId)

        if (updateRating.type == "STAR" && updateRating.rate >= 0 && updateRating.rate <= 5) {
            userRating.starRateSum += updateRating.rate - oldUpdateRating.rate
            userRating.starRate = userRating.starRateSum / userRating.starRateCount
        }
        log.info("Saving user rating: {}", userRating)
        userRatingRepository.save(userRating)
    }

    private static void updateLastComments(UpdateRating updateRating, UserRating userRating) {
        if (userRating.lastComments == null){
            userRating.lastComments = []
        } else if (userRating.lastComments.size() > 20) {
            userRating.lastComments = userRating.lastComments.subList(0, 19)
        }
        if (updateRating.comment != null && !updateRating.comment.empty) {
            userRating.lastComments.add(new Comment(msg: updateRating.comment))
        }
    }

    UserRating get(String userId) {
        userRatingRepository.getByUserId(userId)
    }

    UpdateRating getVoterRate(String voterId, String announceId) {
        String id = getUpdateRatingId(announceId, voterId)
        ratingRepository.get(id)
    }

    static String getUpdateRatingId(String announceId, String voterId) {
        announceId + '_' + voterId
    }
}

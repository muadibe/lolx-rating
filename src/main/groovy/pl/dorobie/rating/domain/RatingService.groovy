package pl.dorobie.rating.domain

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pl.dorobie.rating.domain.model.UpdateRating
import pl.dorobie.rating.domain.model.UserRating
import pl.dorobie.rating.domain.model.Vote
import pl.dorobie.rating.domain.support.Comment

@Component
class RatingService {

    private static final Logger log = LoggerFactory.getLogger(RatingService.class);

    @Autowired
    RatingRepository ratingRepository

    @Autowired
    UserRatingRepository userRatingRepository

    UserRating updateUserRating(UpdateRating updateRating) {
        if (updateRating.customerId == null){
            return userRatingRepository.getByUserId(updateRating.userId)
        }
        if (updateRating.userId.equals(updateRating.customerId)){
            return userRatingRepository.getByUserId(updateRating.userId)
        }
        def lastUpdateRating = getVoterUpdateRating(updateRating)

        def userRating
        if (lastUpdateRating != null){
            userRating = changeVote(lastUpdateRating, updateRating)
        } else {
            userRating = addVote(updateRating)
        }
        userRating
    }

    private UserRating addVote(UpdateRating updateRating) {
        log.info("Adding vote: {}", updateRating)
        updateRating.date = new Date()
        updateRating.id = getUpdateRatingId(updateRating)
        def result = ratingRepository.save(updateRating)
        def userRating = userRatingRepository.getByUserId(result.userId)
        if (userRating == null) {
            userRating = new UserRating(
                    likeCount: 0,
                    id: result.userId,
                    starRate: 0,
                    lastComments: []
            )
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

    private UserRating changeVote(UpdateRating lastUpdateRating, UpdateRating updateRating) {
        log.info("Changing vote: old:{} new:{}", lastUpdateRating, updateRating)
        updateRating.date = new Date()
        def result = ratingRepository.save(updateRating)
        def userRating = userRatingRepository.getByUserId(result.userId)
        log.info("Last user vote: {}", userRating)

        if (updateRating.type == "STAR" && updateRating.rate >= 0 && updateRating.rate <= 5) {
            userRating.starRateSum += (updateRating.rate - lastUpdateRating.rate)
            log.info("New vote sum: {}", userRating.starRateSum)

            userRating.starRate = userRating.starRateSum / userRating.starRateCount
            updateLastComments(updateRating, userRating)
        }
        if (updateRating.type == "LIKE" && updateRating.rate > 0) {
            if (updateRating.rate > 0 && lastUpdateRating.rate < 0) {
                userRating.likeCount += 1
            } else if (updateRating.rate < 0 && lastUpdateRating.rate > 0) {
                userRating.likeCount -= 1
            }
            updateLastComments(updateRating, userRating)
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

    UpdateRating getVoterUpdateRating(UpdateRating updateRating) {
        ratingRepository.get(getUpdateRatingId(updateRating))
    }

    Vote getVote(voterId, announceId) {
        UpdateRating starUpdateRating = getVoterUpdateRating(
                new UpdateRating(customerId: voterId, announceId: announceId, type: "STAR")
        )
        UpdateRating likeUpdateRating = getVoterUpdateRating(
                new UpdateRating(customerId: voterId, announceId: announceId, type: "LIKE")
        )
         new Vote(
                like: likeUpdateRating != null ? likeUpdateRating.rate : 0,
                starRate: starUpdateRating != null ? starUpdateRating.rate : null,
                comment:  starUpdateRating != null ? starUpdateRating.comment : ""
        )
    }

    static String getUpdateRatingId(UpdateRating updateRating) {
        updateRating.announceId + '_' + updateRating.customerId + '_' + updateRating.type
    }
}

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
    public static final String LIKE = "LIKE"
    public static final String STAR = "STAR"
    public static final String COMMENT = "COMMENT"

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
        if (updateRating.announceId == null){
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
        def userRating = userRatingRepository.getByUserId(updateRating.userId)
        def isUpdated = false
        if (userRating == null) {
            userRating = new UserRating(
                    likeCount: 0,
                    id: updateRating.userId,
                    starRate: 0,
                    lastComments: []
            )
        }
        if (updateRating.type == LIKE && updateRating.rate > 0) {
            userRating.likeCount += 1
            isUpdated = true
        }
        if (updateRating.type == STAR && updateRating.rate > 0 && updateRating.rate <= 5) {
            userRating.starRateSum += updateRating.rate
            userRating.starRateCount += 1
            userRating.starRate = userRating.starRateSum / userRating.starRateCount
            updateLastComments(updateRating, userRating, null)
            isUpdated = true
        }
        if (updateRating.type == COMMENT) {
            updateLastComments(updateRating, userRating, null)
            isUpdated = true
        }
        log.info("Saving user rating ({}): {}", isUpdated, userRating)
        if (isUpdated) {
            ratingRepository.save(updateRating)
            userRatingRepository.save(userRating)
        }
        userRating
    }

    private UserRating changeVote(UpdateRating lastUpdateRating, UpdateRating updateRating) {
        log.info("Changing vote: old:{} new:{}", lastUpdateRating, updateRating)
        updateRating.date = new Date()
        updateRating.id = lastUpdateRating.id
        def userRating = userRatingRepository.getByUserId(updateRating.userId)
        def isUpdated = false
        log.info("Last user vote: {}", userRating)

        if (updateRating.type == STAR && updateRating.rate > 0 && updateRating.rate <= 5) {
            long diff = updateRating.rate - lastUpdateRating.rate
            userRating.starRateSum = userRating.starRateSum + diff
            userRating.starRate = userRating.starRateSum / userRating.starRateCount
            updateLastComments(updateRating, userRating, lastUpdateRating.comment)
            isUpdated = true
        }
        if (updateRating.type == LIKE) {
            if (updateRating.rate > 0 && lastUpdateRating.rate < 0) {
                userRating.likeCount += 1
            } else if (updateRating.rate < 0 && lastUpdateRating.rate > 0) {
                userRating.likeCount -= 1
            }
            isUpdated = true
        }
        log.info("Saving user rating ({}): {}", isUpdated, userRating)
        if (isUpdated) {
            ratingRepository.save(updateRating)
            userRatingRepository.save(userRating)
        }
        userRating
    }

    private static void updateLastComments(UpdateRating updateRating, UserRating userRating, String lastComment) {
        if (userRating.lastComments == null){
            userRating.lastComments = []
        } else if (userRating.lastComments.size() > 20) {
            userRating.lastComments = userRating.lastComments.subList(1, 20)
        }
        if (updateRating.comment != null
                && !updateRating.comment.empty
                && updateRating.comment != lastComment) {

            userRating.lastComments.add(new Comment(
                stars: updateRating.rate,
                nick: updateRating.voterNick,
                msg: updateRating.comment)
            )
        }
    }

    UserRating get(String userId) {
        userRatingRepository.getByUserId(userId)
    }

    UpdateRating getVoterUpdateRating(UpdateRating updateRating) {
        def updates  = ratingRepository.getByAnnounceIdAndCustomerIdAndType(updateRating.announceId, updateRating.customerId, updateRating.type)
        if (updates.isEmpty()){
            return null
        }
        updates.sort { -it.date }[0]
    }

    Vote getVote(voterId, announceId) {
        log.info("getting vote voterId: {}, announceId: {}", voterId, announceId)
        UpdateRating starUpdateRating = getVoterUpdateRating(
                new UpdateRating(customerId: voterId, announceId: announceId, type: STAR)
        )
        UpdateRating likeUpdateRating = getVoterUpdateRating(
                new UpdateRating(customerId: voterId, announceId: announceId, type: LIKE)
        )
        log.info("votes: {}, {}", starUpdateRating, likeUpdateRating)
        new Vote(
                like: likeUpdateRating != null ? likeUpdateRating.rate : 0,
                starRate: starUpdateRating != null ? starUpdateRating.rate : 0.0,
                comment:  starUpdateRating != null ? starUpdateRating.comment : ""
        )
    }

}

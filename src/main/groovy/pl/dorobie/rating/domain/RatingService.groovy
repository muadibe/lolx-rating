package pl.dorobie.rating.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pl.dorobie.rating.domain.model.UpdateRating
import pl.dorobie.rating.domain.model.UserRating
import pl.dorobie.rating.domain.support.Comment

@Component
class RatingService {

    @Autowired
    RatingRepository ratingRepository

    @Autowired
    UserRatingRepository userRatingRepository

    UserRating add(UpdateRating updateRating) {
        updateRating.date = new Date()
        def result = ratingRepository.save(updateRating)
        def userRating = userRatingRepository.getByUserId(result.userId)
        if (userRating == null) {
            userRating = new UserRating(likeCount: 0, id: result.userId, starRate: 0, lastComments: [])
        }
        if (userRating.lastComments == null){
            userRating.lastComments = []
        } else if (userRating.lastComments.size() > 20) {
            userRating = userRating.lastComments.subList(0, 19)
        }
        if (updateRating.type == "LIKE" && updateRating.rate > 0) {
            userRating.likeCount += 1
            userRating.lastComments.add(new Comment(msg: updateRating.comment))
        }
        if (updateRating.type == "STAR" && updateRating.rate >= 0 && updateRating.rate <= 5) {
            userRating.starRateSum += updateRating.rate
            userRating.starRateCount += 1
            userRating.starRate = userRating.starRateSum / userRating.starRateCount
            userRating.lastComments.add(new Comment(msg: updateRating.comment))
        }
        if (updateRating.type == "COMMENT") {
            userRating.lastComments.add(new Comment(msg: updateRating.comment))
        }
        return userRatingRepository.save(userRating)
    }

    UserRating get(String userId) {
        return userRatingRepository.getByUserId(userId)
    }
}

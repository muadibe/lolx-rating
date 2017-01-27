package pl.dorobie.rating.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pl.dorobie.rating.domain.model.UpdateRating
import pl.dorobie.rating.domain.model.UserRating

@Component
class RatingService {

    @Autowired
    RatingRepository ratingRepository

    @Autowired
    UserRatingRepository userRatingRepository

    UserRating add(UpdateRating updateRating) {
       def result = ratingRepository.save(updateRating)

       def userRating = userRatingRepository.getByUserId(result.userId)
       if (userRating == null){
           return userRatingRepository.save(new UserRating(likeCount: 1, id: result.userId, starRate: 3))
       }
       return userRating
    }

    UserRating get(String userId) {
        return userRatingRepository.getByUserId(userId)
    }
}

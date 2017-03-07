package pl.dorobie.rating.infrastructure

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pl.dorobie.rating.domain.UserRatingRepository
import pl.dorobie.rating.domain.model.UserRating

@Component
public class UserRatingDao implements UserRatingRepository {

    UserRatingMongoRepository userRatingMongoRepository

    @Autowired
    public UserRatingDao(UserRatingMongoRepository userRatingMongoRepository) {
        this.userRatingMongoRepository = userRatingMongoRepository
    }

    @Override
    UserRating getByUserId(String userId) {
        def userRating = userRatingMongoRepository.findOne(userId)
        if (userRating == null){
            return null
        }
        return RatingMapper.map(userRating)
    }

    @Override
    UserRating save(UserRating userRating) {
        UserRatingDocument dbUserRating = userRatingMongoRepository.save(RatingMapper.map(userRating))
        RatingMapper.map(dbUserRating)
    }
}

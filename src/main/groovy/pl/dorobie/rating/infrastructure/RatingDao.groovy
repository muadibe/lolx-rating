package pl.dorobie.rating.infrastructure

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pl.dorobie.rating.domain.RatingRepository
import pl.dorobie.rating.domain.model.UpdateRating

@Component
public class RatingDao implements RatingRepository {

    RatingMongoRepository ratingMongoRepository

    @Autowired
    public RatingDao(RatingMongoRepository ratingMongoRepository) {
        this.ratingMongoRepository = ratingMongoRepository
    }

    @Override
    public UpdateRating save(UpdateRating updateRating) {
        def ratingDocument = ratingMongoRepository.save(RatingMapper.map(updateRating))
        return RatingMapper.map(ratingDocument)
    }
}

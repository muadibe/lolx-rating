package pl.dorobie.rating.infrastructure

import org.springframework.data.repository.CrudRepository

interface RatingMongoRepository extends CrudRepository<UpdateRatingDocument, String> {
    List<UpdateRatingDocument> getByUserId(String userId)
}

package pl.dorobie.rating.infrastructure

import org.springframework.data.repository.CrudRepository

interface UserRatingMongoRepository extends CrudRepository<UserRatingDocument, String> {
}

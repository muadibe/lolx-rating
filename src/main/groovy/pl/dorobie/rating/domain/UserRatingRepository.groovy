package pl.dorobie.rating.domain

import pl.dorobie.rating.domain.model.UserRating

interface UserRatingRepository {
    UserRating getByUserId(String userId)
    UserRating save(UserRating userRating)
}
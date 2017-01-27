package pl.dorobie.rating.domain

import pl.dorobie.rating.domain.model.UpdateRating
import pl.dorobie.rating.domain.model.UserRating

interface RatingDao {
    UserRating get(String userId)
    UpdateRating add(UpdateRating updateRatingMessage)
}
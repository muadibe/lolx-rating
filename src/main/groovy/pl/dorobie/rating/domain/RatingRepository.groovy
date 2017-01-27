package pl.dorobie.rating.domain

import pl.dorobie.rating.domain.model.UpdateRating

interface RatingRepository {
    UpdateRating save(UpdateRating updateRating)
}
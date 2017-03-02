package pl.dorobie.rating.domain

import pl.dorobie.rating.domain.model.UpdateRating

interface RatingRepository {
    UpdateRating save(UpdateRating updateRating)
    public UpdateRating get(String id)
    List<UpdateRating> getByAnnounceIdAndCustomerIdAndType(String announceId, String customerId, String type)
}
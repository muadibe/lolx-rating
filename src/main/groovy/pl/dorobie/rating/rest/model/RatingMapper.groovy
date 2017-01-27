package pl.dorobie.rating.rest.model

import pl.dorobie.rating.domain.model.UpdateRating
import pl.dorobie.rating.domain.model.UserRating

public class RatingMapper {

    public static UserRatingMessage map(UserRating userRating){
        UserRatingMessage userRatingMessage = new UserRatingMessage(
                id: userRating.id,
                likeCount: userRating.likeCount,
                starRate: userRating.starRate
        )
    }

    public static UpdateRating map(UpdateRatingMessage userRatingMessage){
        UpdateRating userRating = new UpdateRating(
                id: userRatingMessage.id,
                announceId: userRatingMessage.announceId,
                comment: userRatingMessage.comment,
                customerId: userRatingMessage.customerId,
                rate: userRatingMessage.rate,
                userId: userRatingMessage.userId,
                tags: userRatingMessage.tags,
                type: userRatingMessage.type
        );
    }
}

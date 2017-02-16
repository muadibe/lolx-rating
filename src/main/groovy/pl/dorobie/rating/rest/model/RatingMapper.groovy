package pl.dorobie.rating.rest.model

import pl.dorobie.rating.domain.model.UpdateRating
import pl.dorobie.rating.domain.model.UserRating

public class RatingMapper {

    public static UserRatingMessage map(UserRating userRating){
        new UserRatingMessage(
                id: userRating.id,
                likeCount: userRating.likeCount,
                starRate: userRating.starRate,
                starRateCount: userRating.starRateCount,
                lastComments: userRating.lastComments != null ? userRating.lastComments : []
        )
    }

    public static UpdateRating map(UpdateRatingMessage userRatingMessage){
        new UpdateRating(
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

    public static UpdateRatingMessage map(UpdateRating userRating){
        new UpdateRatingMessage(
                id: userRating.id,
                announceId: userRating.announceId,
                comment: userRating.comment,
                customerId: userRating.customerId,
                rate: userRating.rate,
                userId: userRating.userId,
                tags: userRating.tags,
                type: userRating.type
        );
    }
}

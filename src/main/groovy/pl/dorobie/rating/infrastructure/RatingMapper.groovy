package pl.dorobie.rating.infrastructure

import pl.dorobie.rating.domain.model.UpdateRating
import pl.dorobie.rating.domain.model.UserRating

public class RatingMapper {

    public static UserRatingDocument map(UserRating userRating){
        new UserRatingDocument(
                id: userRating.id,
                likeCount: userRating.likeCount,
                starRate: userRating.starRate
        )
    }

    public static UserRating map(UserRatingDocument userRating){
        new UserRating(
                id: userRating.id,
                likeCount: userRating.likeCount,
                starRate: userRating.starRate
        )
    }

    public static UpdateRating map(UpdateRatingDocument userRatingDocument){
        new UpdateRating(
                id: userRatingDocument.id,
                announceId: userRatingDocument.announceId,
                comment: userRatingDocument.comment,
                customerId: userRatingDocument.customerId,
                rate: userRatingDocument.rate,
                userId: userRatingDocument.userId,
                tags: userRatingDocument.tags,
                type: userRatingDocument.type
        )
    }

    public static UpdateRatingDocument map(UpdateRating updateRating){
        new UpdateRatingDocument(
                id: updateRating.id,
                announceId: updateRating.announceId,
                comment: updateRating.comment,
                customerId: updateRating.customerId,
                rate: updateRating.rate,
                userId: updateRating.userId,
                tags: updateRating.tags,
                type: updateRating.type
        )
    }
}

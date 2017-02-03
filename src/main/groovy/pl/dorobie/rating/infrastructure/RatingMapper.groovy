package pl.dorobie.rating.infrastructure

import pl.dorobie.rating.domain.model.UpdateRating
import pl.dorobie.rating.domain.model.UserRating

public class RatingMapper {

    public static UserRatingDocument map(UserRating userRating){
        if (userRating == null) {
            return null
        }
        new UserRatingDocument(
                id: userRating.id,
                likeCount: userRating.likeCount,
                starRate: userRating.starRate,
                starRateCount: userRating.starRateCount,
                starRateSum: userRating.starRateSum,
                lastComments: userRating.lastComments
        )
    }

    public static UserRating map(UserRatingDocument userRatingDocument){
        if (userRatingDocument == null) {
            return null
        }
        new UserRating(
                id: userRatingDocument.id,
                likeCount: userRatingDocument.likeCount,
                starRate: userRatingDocument.starRate,
                starRateCount: userRatingDocument.starRateCount,
                starRateSum: userRatingDocument.starRateSum,
                lastComments: userRatingDocument.lastComments
        )
    }

    public static UpdateRating map(UpdateRatingDocument userRatingDocument){
        if (userRatingDocument == null) {
            return null
        }
        new UpdateRating(
                id: userRatingDocument.id,
                announceId: userRatingDocument.announceId,
                comment: userRatingDocument.comment,
                customerId: userRatingDocument.customerId,
                rate: userRatingDocument.rate,
                userId: userRatingDocument.userId,
                tags: userRatingDocument.tags,
                type: userRatingDocument.type,
                date: userRatingDocument.date
        )
    }

    public static UpdateRatingDocument map(UpdateRating updateRating){
        if (updateRating == null) {
            return null
        }
        new UpdateRatingDocument(
                id: updateRating.id,
                announceId: updateRating.announceId,
                comment: updateRating.comment,
                customerId: updateRating.customerId,
                rate: updateRating.rate,
                userId: updateRating.userId,
                tags: updateRating.tags,
                type: updateRating.type,
                date: updateRating.date
        )
    }
}

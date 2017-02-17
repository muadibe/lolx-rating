package pl.dorobie.rating.rest.model

import pl.dorobie.rating.domain.model.Vote

public class VoteMapper {

    public static VoteMessage map(Vote vote){
        new VoteMessage(
                like: vote.like,
                starRate: vote.starRate,
                comment: vote.comment
        )
    }

}

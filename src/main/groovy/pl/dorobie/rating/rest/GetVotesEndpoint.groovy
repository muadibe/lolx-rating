package pl.dorobie.rating.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import pl.dorobie.rating.domain.RatingService
import pl.dorobie.rating.rest.model.VoteMapper
import pl.dorobie.rating.rest.model.VoteMessage

@RestController
@RequestMapping(value = "/votes")
class GetVotesEndpoint {

    RatingService ratingService

    @Autowired
    public GetVotesEndpoint(RatingService ratingService) {
        this.ratingService = ratingService
    }

    @RequestMapping(value = "/voter/{voterId}/announce/{announceId}", method = RequestMethod.GET)
    public ResponseEntity<VoteMessage> get(
            @PathVariable("voterId") String voterId,
            @PathVariable("announceId") String announceId
            ) {
        def vote = ratingService.getVote(voterId, announceId)

        if (vote == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND)
        }
        return new ResponseEntity<>(VoteMapper.map(vote), HttpStatus.OK)
    }
}

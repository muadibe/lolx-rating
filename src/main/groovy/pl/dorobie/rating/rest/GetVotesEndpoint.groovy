package pl.dorobie.rating.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import pl.dorobie.rating.domain.RatingService
import pl.dorobie.rating.domain.jwt.JwtChecker
import pl.dorobie.rating.rest.model.VoteMapper
import pl.dorobie.rating.rest.model.VoteMessage

@RestController
@RequestMapping(value = "/votes")
class GetVotesEndpoint {

    RatingService ratingService
    JwtChecker jwtChecker

    @Autowired
    public GetVotesEndpoint(RatingService ratingService, JwtChecker jwtChecker) {
        this.ratingService = ratingService
        this.jwtChecker = jwtChecker
    }

    @RequestMapping(value = "/announce/{announceId}", method = RequestMethod.GET)
    public ResponseEntity<VoteMessage> get(
            @PathVariable("announceId") String announceId,
            @RequestHeader(value = "Authorization") String authorizationHeader
            ) {
        def customerId = jwtChecker.subject(authorizationHeader)
        def vote = ratingService.getVote(customerId, announceId)
        if (vote == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND)
        }
        return new ResponseEntity<>(VoteMapper.map(vote), HttpStatus.OK)
    }
}

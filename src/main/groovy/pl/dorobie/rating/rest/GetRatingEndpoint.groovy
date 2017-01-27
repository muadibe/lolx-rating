package pl.dorobie.rating.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import pl.dorobie.rating.domain.RatingService
import pl.dorobie.rating.domain.model.UserRating
import pl.dorobie.rating.rest.model.RatingMapper
import pl.dorobie.rating.rest.model.UserRatingMessage

@RestController
@RequestMapping(value = "/ratings")
class GetRatingEndpoint {

    RatingService ratingService

    @Autowired
    public GetRatingEndpoint(RatingService ratingService) {
        this.ratingService = ratingService
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<UserRatingMessage> get(@PathVariable("userId") String userId) {

        UserRating userRating = ratingService.get(userId)
        if (userRating == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND)
        }
        return new ResponseEntity<>(RatingMapper.map(userRating), HttpStatus.OK)
    }
}

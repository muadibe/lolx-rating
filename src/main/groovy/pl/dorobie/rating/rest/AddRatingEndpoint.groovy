package pl.dorobie.rating.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.dorobie.rating.domain.RatingService
import pl.dorobie.rating.domain.model.UserRating
import pl.dorobie.rating.rest.model.RatingMapper
import pl.dorobie.rating.rest.model.UpdateRatingMessage
import pl.dorobie.rating.rest.model.UserRatingMessage

@RestController
@RequestMapping(value = "/ratings")
class AddRatingEndpoint {

    RatingService ratingService;

    @Autowired
    public AddRatingEndpoint(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserRatingMessage> add(@RequestHeader(value = "Authorization") String authorizationHeader,
                                                 @RequestBody @Validated UpdateRatingMessage dto) {

        UserRating result = ratingService.add(RatingMapper.map(dto))
        return new ResponseEntity<>(RatingMapper.map(result), HttpStatus.ACCEPTED)
    }
}

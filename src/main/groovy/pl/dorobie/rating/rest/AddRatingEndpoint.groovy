package pl.dorobie.rating.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.dorobie.rating.domain.RatingService
import pl.dorobie.rating.domain.jwt.JwtChecker
import pl.dorobie.rating.domain.model.UpdateRating
import pl.dorobie.rating.domain.model.UserRating
import pl.dorobie.rating.rest.model.RatingMapper
import pl.dorobie.rating.rest.model.UpdateRatingMessage
import pl.dorobie.rating.rest.model.UserRatingMessage

@RestController
@RequestMapping(value = "/ratings")
class AddRatingEndpoint {

    RatingService ratingService;
    JwtChecker jwtChecker

    @Autowired
    public AddRatingEndpoint(RatingService ratingService, JwtChecker jwtChecker) {
        this.ratingService = ratingService;
        this.jwtChecker = jwtChecker
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserRatingMessage> add(@RequestHeader(value = "Authorization") String authorizationHeader,
                                                 @RequestBody @Validated UpdateRatingMessage dto) {
        def customerId = jwtChecker.subject(authorizationHeader)
        def updateRating = RatingMapper.map(dto)
        updateRating.customerId = customerId
        UserRating result = ratingService.add(updateRating)

        return new ResponseEntity<>(RatingMapper.map(result), HttpStatus.ACCEPTED)
    }
}

package pl.dorobie.rating.rest

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.dorobie.rating.domain.RatingService
import pl.dorobie.rating.domain.jwt.JwtChecker
import pl.dorobie.rating.domain.model.UserRating
import pl.dorobie.rating.rest.model.RatingMapper
import pl.dorobie.rating.rest.model.UpdateRatingMessage
import pl.dorobie.rating.rest.model.UserRatingMessage

import javax.validation.constraints.NotNull

@RestController
@RequestMapping(value = "/ratings")
class AddRatingEndpoint {

    private static final Logger log = LoggerFactory.getLogger(RatingService.class);

    RatingService ratingService;
    JwtChecker jwtChecker

    @Autowired
    public AddRatingEndpoint(RatingService ratingService, JwtChecker jwtChecker) {
        this.ratingService = ratingService;
        this.jwtChecker = jwtChecker
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserRatingMessage> add(@RequestHeader(value = "Authorization") String authorizationHeader,
                                                 @RequestBody @Validated @NotNull UpdateRatingMessage dto) {
        def customerId = jwtChecker.subject(authorizationHeader)
        def updateRating = RatingMapper.map(dto)
        updateRating.customerId = customerId
        log.info("Updating rating {}", updateRating)
        UserRating result = ratingService.updateUserRating(updateRating)
        log.info("New user rating {}", result)
        return new ResponseEntity<>(RatingMapper.map(result), HttpStatus.ACCEPTED)
    }
}

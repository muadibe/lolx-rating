package pl.dorobie.rating.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import pl.dorobie.rating.domain.RatingService
import pl.dorobie.rating.domain.model.UpdateRating
import pl.dorobie.rating.rest.model.RatingMapper
import pl.dorobie.rating.rest.model.UpdateRatingMessage

@RestController
@RequestMapping(value = "/voters-rates")
class GetVotersRatesEndpoint {

    RatingService ratingService

    @Autowired
    public GetVotersRatesEndpoint(RatingService ratingService) {
        this.ratingService = ratingService
    }

    @RequestMapping(value = "/voter/{voterId}/announce/{announceId}", method = RequestMethod.GET)
    public ResponseEntity<UpdateRatingMessage> get(
            @PathVariable("voterId") String voterId,
            @PathVariable("announceId") String announceId
            ) {

        UpdateRating updateRating = ratingService.getVoterRate(voterId, announceId)
        if (updateRating == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND)
        }
        return new ResponseEntity<>(RatingMapper.map(updateRating), HttpStatus.OK)
    }
}

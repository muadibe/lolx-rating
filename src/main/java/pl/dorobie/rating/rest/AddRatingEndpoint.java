package pl.dorobie.rating.rest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.dorobie.rating.rest.model.UpdateRatingMessage;
import pl.dorobie.rating.rest.model.UserRatingMessage;

@RestController
@RequestMapping(value = "/ratings")
class AddRatingEndpoint {

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserRatingMessage> add(@RequestHeader(value = "Authorization") String authorizationHeader,
                                                 @RequestBody @Validated UpdateRatingMessage dto) {

       return new ResponseEntity<>(new UserRatingMessage(), HttpStatus.ACCEPTED);
    }
}

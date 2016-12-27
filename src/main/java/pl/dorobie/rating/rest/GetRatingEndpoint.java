package pl.dorobie.rating.rest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.dorobie.rating.rest.model.UserRatingMessage;

@RestController
@RequestMapping(value = "/ratings")
class GetRatingEndpoint {

    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    public ResponseEntity<UserRatingMessage> get(@PathVariable("userId") String userId) {

       return new ResponseEntity<>(new UserRatingMessage(), HttpStatus.OK);
    }
}

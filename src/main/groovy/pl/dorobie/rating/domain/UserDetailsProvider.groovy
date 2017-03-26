package pl.dorobie.rating.domain

import pl.dorobie.rating.domain.model.UserDetails;

interface UserDetailsProvider {

    UserDetails getUserDetails(String userId, String jwt)
}

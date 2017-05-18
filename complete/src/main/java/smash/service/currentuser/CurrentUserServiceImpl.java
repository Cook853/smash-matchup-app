//package smash.service.currentuser;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//import smash.model.CurrentUser;
//import smash.model.Role;
//
//
///**
// * Created by Lauren on 5/15/2017.
// */
//@Service
//public class CurrentUserServiceImpl implements CurrentUserService {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentUserDetailsService.class);
//
//    @Override
//    public boolean canAccessUser(CurrentUser currentUser, int userId) {
//        LOGGER.debug("Checking if user={} has access to user={}", currentUser, userId);
//        return currentUser != null
//                && (currentUser.getRole() == Role.ADMIN || currentUser.getId() == (userId));
//    }
//
//}
//

package smash.config;

import org.springframework.security.web.context.*;

/**
 * Created by Lauren on 5/17/2017.
 */
public class SecurityWebApplicationInitializer
        extends AbstractSecurityWebApplicationInitializer {

    public SecurityWebApplicationInitializer() {
        super(WebSecurityConfig.class);
    }
}

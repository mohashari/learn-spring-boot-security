package me.muklis.config;

import me.muklis.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


//    private AuthorizationRequestRepository authorizationRequestRepository;
//
//    private OAuth2AuthorizationRequestResolver authorizationRequestResolver;
//
//    private GrantedAuthoritiesMapper grantedAuthoritiesMapper;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeRequests(a -> a
                        .antMatchers("/", "/error", "/webjars/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .csrf(c -> c
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .logout(l -> l
                        .logoutSuccessUrl("/").permitAll()
                )
                .oauth2Login(oauth2 -> oauth2.redirectionEndpoint(redirectionEndpointConfig -> redirectionEndpointConfig.baseUri("/oauth2/callback/*"))
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService)));
    }


//    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
//        final OidcUserService delegate = new OidcUserService();
//
//        return (userRequest) -> {
//            // Delegate to the default implementation for loading a user
//            OidcUser oidcUser = delegate.loadUser(userRequest);
//
//            OAuth2AccessToken accessToken = userRequest.getAccessToken();
//            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
//            oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
//            return oidcUser;
//
//        };
//    }
//    @Bean
//    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
//        return grantedAuthoritiesMapper;
//    }

    /**
     * custom userAuthoritiesMapper
     *
     * @return
     */
//    private GrantedAuthoritiesMapper userAuthoritiesMapper() {
//
//
//        return (authorities) -> {
//            Set<GrantedAuthority> mappedGrandAuthority = new HashSet<>();
//            authorities.forEach(authority -> {
//                if (OidcUserAuthority.class.isInstance(authority)) {
//                    OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;
//                    OidcIdToken idToken = oidcUserAuthority.getIdToken();
//                    OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();
//                } else if (OAuth2UserAuthority.class.isInstance(authority)) {
//                    OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority) authority;
//                    Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();
//                }
//            });
//            return mappedGrandAuthority;
//        };
//    }
//    private OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
//        return null;
//    }
//
//    private OAuth2AuthorizationRequestResolver authorizationRequestResolver() {
//        return this.authorizationRequestResolver;
//    }
//
//    private AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
//        return this.authorizationRequestRepository;
//    }


}

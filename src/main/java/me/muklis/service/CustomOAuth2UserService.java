package me.muklis.service;

import me.muklis.config.GoogleOAuth2User;
import me.muklis.domain.User;
import me.muklis.domain.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = loadUser(userRequest);
        return processOAuthUser(userRequest, oAuth2User);
    }

    private OAuth2User processOAuthUser(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {

        //todo: change to factory for dynamic implementation
        GoogleOAuth2User googleOAuth2User = new GoogleOAuth2User(oAuth2User.getAttributes());

        if (googleOAuth2User.getEmail() == null)
            throw new RuntimeException("Email Null");

        User user = userRepo.findByEmail(googleOAuth2User.getEmail())
                .map(user1 -> updateUser(user1, googleOAuth2User))
                .orElse(createUser(googleOAuth2User));
        userRepo.save(user);
        return user;
    }


    private User createUser(GoogleOAuth2User googleOAuth2User) {
        return new User(googleOAuth2User.getEmail(), googleOAuth2User.getName(), googleOAuth2User.getAttributes());
    }

    private User updateUser(User user, GoogleOAuth2User googleOAuth2User) {
        user.setEmail(googleOAuth2User.getEmail());
        user.setName(googleOAuth2User.getName());
        return user;
    }
}

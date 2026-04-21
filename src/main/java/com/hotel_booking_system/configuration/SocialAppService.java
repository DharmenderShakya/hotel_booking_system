package com.hotel_booking_system.configuration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.hotel_booking_system.entity.Roles;
import com.hotel_booking_system.entity.Users;
import com.hotel_booking_system.repository.UsersRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SocialAppService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UsersRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(SocialAppService.class);

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {

        //  Default service to fetch user from Google
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate =
                new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //  Extract user details
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        logger.info("OAuth2 Login Success: {}", email);

        //  Check if user exists
        com.hotel_booking_system.entity.Users user = userRepository.findByUserName(email).orElse(null);

        if (user == null) {
            // New user → Save in DB
            user = new Users();
            user.setUserName(email);
            user.setPassword("OAUTH2_USER"); // dummy password

            userRepository.save(user);

            logger.info("New user saved: {}", email);
        }

        // Assign authorities (VERY IMPORTANT)
        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + "")
        );

        // Return OAuth2User with authorities
        return new DefaultOAuth2User(
                authorities,
                oAuth2User.getAttributes(),
                "email" // key attribute
        );
    }

}

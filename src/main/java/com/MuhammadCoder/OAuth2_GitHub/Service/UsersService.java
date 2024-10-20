package com.MuhammadCoder.OAuth2_GitHub.Service;

import com.MuhammadCoder.OAuth2_GitHub.EmailService.EmailSenderService;
import com.MuhammadCoder.OAuth2_GitHub.Entity.Users;
import com.MuhammadCoder.OAuth2_GitHub.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private EmailSenderService senderService;

    public Users getUserFromAuth(AbstractAuthenticationToken auth) {
        Map<String, Object> details;
        if (auth instanceof OAuth2AuthenticationToken) {
            details =((OAuth2AuthenticationToken) auth).getPrincipal().getAttributes();
        }else
            throw new IllegalArgumentException("Invalid authentication token");

        Users user = getUser(details);
        if (user != null){
            usersRepository.save(user);
        }
        return user;
    }

    public Users getUser(Map<String, Object> details) {
        Users user = new Users();
        if (details.get("given_name") != null) {
            user.setFirstName(details.get("given_name").toString());
        }
        if (details.get("family_name") != null) {
            user.setLastName(details.get("family_name").toString());
        }
        if (details.get("email") != null) {
            user.setEmail(details.get("email").toString());
            String email = user.getEmail();
            String subject = "OAuth2 Provider";
            String body = "You have registered as a new user Congratulations!!!!! " + user.getFirstName() + " " + user.getLastName();
            senderService.sendEmail(email, subject, body);
        }
        if (details.get("picture") != null) {
            user.setImageUrl(details.get("picture").toString());
        }
        if (details.get("email_verified") != null) {
            user.setActive((Boolean) details.get("email_verified"));
        }
        return user;
    }
}

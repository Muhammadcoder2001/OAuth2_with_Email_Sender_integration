package com.MuhammadCoder.OAuth2_GitHub.Controller;

import com.MuhammadCoder.OAuth2_GitHub.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/user")
    public ResponseEntity<?> getUser(Principal principal) {
        if (principal instanceof AbstractAuthenticationToken) {
            return ResponseEntity.ok(usersService.getUserFromAuth((AbstractAuthenticationToken) principal));
        }else
            throw new IllegalArgumentException("Failed");
    }
// shu orqali githubni tekshirib ko'rdim githubda email yo'qligi sababli email message jo'natishni githubga moslay olmadim lekin githubni o'ziga notification qilish joyi chiqib qolsa albatta to'g'rilab  qo'yaman
    @GetMapping("/")
    public ResponseEntity<?> getUsers(Principal principal) {
        if (principal instanceof AbstractAuthenticationToken) {
            return ResponseEntity.ok(principal);
        }else
            throw new IllegalArgumentException("Failed");
    }
}

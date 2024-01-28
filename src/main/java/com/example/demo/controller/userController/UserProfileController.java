package com.example.demo.controller.userController;

import com.example.demo.entity.User;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserProfileController {
    @Autowired
    UserRepo userRepo;
@GetMapping("/profile/{userId}")
        public ResponseEntity<User> getUserProfile(@PathVariable Long userId) {
            User user = userRepo.findById(userId).orElse(null);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

}

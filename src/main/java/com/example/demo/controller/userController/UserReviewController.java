package com.example.demo.controller.userController;

import com.example.demo.Service.ReviewService;
import com.example.demo.dto.ReviewDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserReviewController {
@Autowired
    ReviewService reviewService;
    @PostMapping("/userReviews")
    public ResponseEntity<String> addReview(@RequestBody ReviewDto reviewDto){
        if (reviewDto == null || reviewDto.getTitle() == null || reviewDto.getDescription() == null ||
                reviewDto.getRating() < 0 || reviewDto.getRating() > 5 || reviewDto.getHotelId() == null ||
                reviewDto.getUserId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid review data");
        }
        try {
            reviewService.addReview(reviewDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Review added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding review");
        }
    }
}

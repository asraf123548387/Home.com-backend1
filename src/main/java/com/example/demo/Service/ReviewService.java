package com.example.demo.Service;

import com.example.demo.dto.ReviewDto;
import com.example.demo.entity.Hotel;
import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import com.example.demo.repo.HotelRepo;
import com.example.demo.repo.ReviewRepo;
import com.example.demo.repo.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    ReviewRepo reviewRepo;
    @Autowired
    HotelRepo hotelRepo;
    @Autowired
    UserRepo userRepo;
    public void addReview(ReviewDto reviewDto) {
        Hotel hotel = hotelRepo.findByHotelId(reviewDto.getHotelId());

        User user=userRepo.findById(reviewDto.getUserId()) .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + reviewDto.getUserId()));

        Review review = new Review();
        review.setTitle(reviewDto.getTitle());
        review.setDescription(reviewDto.getDescription());
        review.setRating(reviewDto.getRating());
        review.setHotel(hotel);
        review.setUser(user);

        // Save the Review entity
        reviewRepo.save(review);


    }
}

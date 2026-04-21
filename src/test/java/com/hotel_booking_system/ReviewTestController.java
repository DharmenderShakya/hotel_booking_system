package com.hotel_booking_system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel_booking_system.configuration.JwtFilter;
import com.hotel_booking_system.configuration.SocialAppService;
import com.hotel_booking_system.configuration.UserInfoService;
import com.hotel_booking_system.controller.AuthController;
import com.hotel_booking_system.controller.ReviewController;
import com.hotel_booking_system.request.ReviewRequest;
import com.hotel_booking_system.response.ReviewResponse;
import com.hotel_booking_system.service.ReviewService;
import com.hotel_booking_system.service.UserService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ReviewController.class,
excludeAutoConfiguration = {
    org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
    org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration.class,
    org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientWebSecurityAutoConfiguration.class

}
)
@AutoConfigureMockMvc(addFilters = false)
public class ReviewTestController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private UserService userService;
    @MockBean private JwtFilter jwtFilter;
    @MockBean private UserInfoService userInfoService;
    @MockBean private SocialAppService socialAppService;
    
    @MockBean
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    // ✅ Mock logged-in user
    private Principal mockPrincipal() {
        return () -> "test@gmail.com";
    }

    // ✅ Create Review
    @Test
    void testCreateReview() throws Exception {

        ReviewRequest request = new ReviewRequest();
        request.setHotel_id(1L);
        request.setRating(5);
        request.setComment("Excellent stay");

        Mockito.when(reviewService.createReview(Mockito.any(), Mockito.anyString()))
                .thenReturn(new ReviewResponse());

        mockMvc.perform(post("/api/review")
                .principal(mockPrincipal())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // ✅ Update Review
    @Test
    void testUpdateReview() throws Exception {

        ReviewRequest request = new ReviewRequest();
        request.setRating(4);
        request.setComment("Good experience");

        Mockito.when(reviewService.updateReviewById(Mockito.eq(1L), Mockito.any(), Mockito.anyString()))
                .thenReturn(new ReviewResponse());

        mockMvc.perform(put("/api/review/{id}", 1L)
                .principal(mockPrincipal())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // ✅ Delete Review
    @Test
    void testDeleteReview() throws Exception {

        Mockito.when(reviewService.deleteReviewById(Mockito.eq(1L), Mockito.anyString()))
                .thenReturn(new ReviewResponse());

        mockMvc.perform(delete("/api/review/{id}", 1L)
                .principal(mockPrincipal()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // ✅ Get Reviews By Hotel (FIXED URL)
    @Test
    void testGetReviewsByHotel() throws Exception {

        List<ReviewResponse> list = List.of(new ReviewResponse());

        Mockito.when(reviewService.getReviewByOwnerId(Mockito.eq(1L), Mockito.anyString()))
                .thenReturn(list);

        mockMvc.perform(get("/api/review/hotel/{hotelId}", 1L) // ✅ FIXED
                .principal(mockPrincipal()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    // ✅ Exception Case
    @Test
    void testReview_NotFound() throws Exception {

        Mockito.when(reviewService.updateReviewById(Mockito.eq(1L), Mockito.any(), Mockito.anyString()))
                .thenThrow(new RuntimeException("Review not found"));

        ReviewRequest request = new ReviewRequest();
        request.setRating(3);

        mockMvc.perform(put("/api/review/{id}", 1L)
                .principal(mockPrincipal())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
}
package com.supportportal.controller;

import com.supportportal.domain.Review;
import com.supportportal.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ReviewControllerTest {

    @Mock
    private ReviewServiceImpl reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddRandomReviewsSuccess() {
        doNothing().when(reviewService).addRandomReviews();

        ResponseEntity<String> response = reviewController.addRandomReviews();

        verify(reviewService, times(1)).addRandomReviews();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Recenzii adăugate cu succes.", response.getBody());
    }

    @Test
    public void testAddRandomReviewsFailure() {
        doThrow(new RuntimeException("Test Exception")).when(reviewService).addRandomReviews();

        ResponseEntity<String> response = reviewController.addRandomReviews();

        verify(reviewService, times(1)).addRandomReviews();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Eroare la adăugarea recenziilor: Test Exception", response.getBody());
    }

    @Test
    public void testFindAllReviews() {
        List<Review> reviews = Collections.singletonList(new Review());
        when(reviewService.findAllReviews()).thenReturn(reviews);

        ResponseEntity<List<Review>> response = reviewController.findAllReviews();

        verify(reviewService, times(1)).findAllReviews();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reviews, response.getBody());
    }

    @Test
    public void testFindReviewById() {
        Review review = new Review();
        when(reviewService.findReviewById(anyLong())).thenReturn(review);

        ResponseEntity<Review> response = reviewController.findReviewById("1");

        verify(reviewService, times(1)).findReviewById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(review, response.getBody());
    }

    @Test
    public void testFindReviewsByDoctorUsernameNoContent() {
        when(reviewService.findAllReviewsByDoctorUsername(anyString())).thenReturn(Collections.emptyList());

        ResponseEntity<List<Review>> response = reviewController.findReviewsByDoctorUsername("testUsername");

        verify(reviewService, times(1)).findAllReviewsByDoctorUsername("testUsername");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testFindReviewsByDoctorUsernameSuccess() {
        List<Review> reviews = Collections.singletonList(new Review());
        when(reviewService.findAllReviewsByDoctorUsername(anyString())).thenReturn(reviews);

        ResponseEntity<List<Review>> response = reviewController.findReviewsByDoctorUsername("testUsername");

        verify(reviewService, times(1)).findAllReviewsByDoctorUsername("testUsername");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reviews, response.getBody());
    }
}

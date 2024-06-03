package com.supportportal.service;

import com.supportportal.domain.Review;
import com.supportportal.repository.OrganizationRepository;
import com.supportportal.repository.ReviewRepository;
import com.supportportal.repository.users.AssistantRepository;
import com.supportportal.repository.users.DoctorRepository;
import com.supportportal.repository.users.SpecialUserRepository;
import com.supportportal.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private SpecialUserRepository specialUserRepository;

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private AssistantRepository assistantRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddRandomReviews() {
        when(specialUserRepository.findAllIds()).thenReturn(List.of(1L, 2L, 3L));
        when(organizationRepository.findAllIds()).thenReturn(List.of(1L, 2L, 3L));
        when(doctorRepository.findAllIds()).thenReturn(List.of(1L, 2L, 3L));
        when(assistantRepository.findAllIds()).thenReturn(List.of(1L, 2L, 3L));

        when(specialUserRepository.existsById(anyLong())).thenReturn(true);
        when(organizationRepository.existsById(anyLong())).thenReturn(true);
        when(doctorRepository.existsById(anyLong())).thenReturn(true);
        when(assistantRepository.existsById(anyLong())).thenReturn(true);

        reviewService.addRandomReviews();

        verify(reviewRepository, times(100)).save(any(Review.class));
    }

    @Test
    void testCreateReviewWithValidOrganization() {
        when(organizationRepository.findAllIds()).thenReturn(List.of(235L));
        when(organizationRepository.existsById(anyLong())).thenReturn(true);
        when(doctorRepository.existsById(anyLong())).thenReturn(true);
        when(specialUserRepository.existsById(anyLong())).thenReturn(true);
        when(assistantRepository.existsById(anyLong())).thenReturn(true);

        reviewService.createReviewWithValidOrganization();

        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void testFindAllReviews() {
        List<Review> reviews = List.of(new Review(), new Review());
        when(reviewRepository.findAll()).thenReturn(reviews);

        List<Review> actualReviews = reviewService.findAllReviews();

        assertEquals(reviews.size(), actualReviews.size());
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void testFindReviewById() {
        Review expectedReview = new Review();
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(expectedReview));

        Review actualReview = reviewService.findReviewById(1L);

        assertNotNull(actualReview);
        assertEquals(expectedReview, actualReview);
        verify(reviewRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAllReviewsByDoctorUsername() {
        List<Review> expectedReviews = List.of(new Review(), new Review());
        when(reviewRepository.findAllByDoctorUsername(anyString())).thenReturn(expectedReviews);

        List<Review> actualReviews = reviewService.findAllReviewsByDoctorUsername("doctorUsername");

        assertEquals(expectedReviews.size(), actualReviews.size());
        verify(reviewRepository, times(1)).findAllByDoctorUsername("doctorUsername");
    }
}

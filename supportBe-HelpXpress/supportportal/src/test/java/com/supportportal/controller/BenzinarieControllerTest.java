package com.supportportal.controller;

import com.supportportal.domain.GazStation;
import com.supportportal.service.impl.BenzinarieServiceImpl;
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

public class BenzinarieControllerTest {

    @Mock
    private BenzinarieServiceImpl benzinarieService;

    @InjectMocks
    private BenzinarieController benzinarieController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllBenzinarii() {
        List<GazStation> benzinarii = Collections.singletonList(new GazStation());
        when(benzinarieService.getAllBenzinarii()).thenReturn(benzinarii);

        ResponseEntity<List<GazStation>> response = benzinarieController.getAllBenzinarii();

        verify(benzinarieService, times(1)).getAllBenzinarii();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(benzinarii, response.getBody());
    }

    @Test
    public void testGenerateBenzinarii() {
        doNothing().when(benzinarieService).generateRandomBenzinarii();

        ResponseEntity<?> response = benzinarieController.generateBenzinarii();

        verify(benzinarieService, times(1)).generateRandomBenzinarii();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Benzinarii generate cu succes.", response.getBody());
    }
}

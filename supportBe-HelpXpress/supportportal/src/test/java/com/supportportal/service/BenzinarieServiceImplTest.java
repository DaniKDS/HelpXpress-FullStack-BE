package com.supportportal.service.impl;

import com.supportportal.domain.GazStation;
import com.supportportal.repository.BenzinarieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class BenzinarieServiceImplTest {

    @Mock
    private BenzinarieRepository benzinarieRepository;

    @InjectMocks
    private BenzinarieServiceImpl benzinarieService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllBenzinarii() {
        List<GazStation> expectedBenzinarii = new ArrayList<>();
        expectedBenzinarii.add(new GazStation());
        expectedBenzinarii.add(new GazStation());

        when(benzinarieRepository.findAll()).thenReturn(expectedBenzinarii);

        List<GazStation> actualBenzinarii = benzinarieService.getAllBenzinarii();

        assertNotNull(actualBenzinarii);
        assertEquals(expectedBenzinarii.size(), actualBenzinarii.size());
        verify(benzinarieRepository, times(1)).findAll();
    }

    @Test
    public void testGenerateRandomBenzinarii() {
        benzinarieService.generateRandomBenzinarii();

        verify(benzinarieRepository, times(1)).saveAll(anyList());
    }
}

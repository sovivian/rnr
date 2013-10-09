package com.springapp.mvc.web.unit.controller;

import com.springapp.mvc.web.controller.HomeController;
import org.junit.Before;
import org.junit.Test;
import com.springapp.mvc.web.service.CalculatorService;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class HomeControllerTest {
    CalculatorService mockCalculatorService;
    HttpServletRequest mockHttpServletRequest;


    @Before
    public void setUp() throws Exception {
        mockHttpServletRequest = mock(HttpServletRequest.class);
        mockCalculatorService = mock(CalculatorService.class);

    }

    @Test
    public void get_shouldReturnHomeView() {
        HomeController homeController = new HomeController(mock(CalculatorService.class));
        assertThat(homeController.get(), is("home"));
    }

    @Test
    public void shouldCallCalculatorServiceWhenGivenRateAndRollover() throws Exception {

        HomeController homeController = new HomeController(mockCalculatorService);
        when(mockHttpServletRequest.getParameter("rolloverdays")).thenReturn("1");
        when(mockHttpServletRequest.getParameter("accrualRate")).thenReturn("10");

        homeController.postDate(mockHttpServletRequest);

        verify(mockCalculatorService, times(1)).calculateVacationDaysGivenRate(anyDouble(), anyString(), anyDouble());

    }
}
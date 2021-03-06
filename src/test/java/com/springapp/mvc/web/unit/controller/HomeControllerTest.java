package com.springapp.mvc.web.unit.controller;

import com.springapp.mvc.web.controller.HomeController;
import com.springapp.mvc.web.model.AccrualRateCalculator;
import com.springapp.mvc.web.model.VacationCalculator;
import com.springapp.mvc.web.model.Employee;
import com.springapp.mvc.web.service.DateParserService;
import com.springapp.mvc.web.service.EmployeeService;
import com.springapp.mvc.web.service.SalesForceParserService;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class HomeControllerTest {
    HttpServletRequest mockHttpServletRequest;
    SalesForceParserService mockSalesForceParserService;
    EmployeeService mockEmployeeService;
    VacationCalculator mockVacationCalculator;
    AccrualRateCalculator mockAccrualRateCalculator;
    DateParserService mockDateParserService;

    @Before
    public void setUp() throws Exception {
        mockHttpServletRequest = mock(HttpServletRequest.class);
        mockSalesForceParserService = mock(SalesForceParserService.class);
        mockEmployeeService = mock(EmployeeService.class);
        mockVacationCalculator = mock(VacationCalculator.class);
        mockAccrualRateCalculator = mock(AccrualRateCalculator.class);
        mockDateParserService = mock(DateParserService.class);
    }

    @Test
    public void get_shouldReturnHomeView() {
        HomeController homeController = new HomeController(mockEmployeeService, mockSalesForceParserService, mockVacationCalculator, mockAccrualRateCalculator, mockDateParserService);
        assertThat(homeController.get(), is("home"));
    }

    @Test
    public void shouldInteractWithSalesForceParserServiceAndEmployeeService() throws Exception {

        HomeController homeController = new HomeController(mockEmployeeService, mockSalesForceParserService, mockVacationCalculator, mockAccrualRateCalculator, mockDateParserService);

        when(mockHttpServletRequest.getParameter("rolloverdays")).thenReturn("1");
        when(mockHttpServletRequest.getParameter("accrualRate")).thenReturn("10");
        when(mockHttpServletRequest.getParameter("salesForceText")).thenReturn("Test");
        when(mockHttpServletRequest.getParameter("startDate")).thenReturn("10/22/2012");
        when(mockHttpServletRequest.getParameter("endDate")).thenReturn("11/22/2013");

        when(mockVacationCalculator.getVacationDays(any(Employee.class), any(AccrualRateCalculator.class), any(LocalDate.class))).thenReturn(20d);

        homeController.postDate(mockHttpServletRequest);

        verify(mockSalesForceParserService, times(1)).parse(anyString());
        verify(mockEmployeeService, times(1)).createEmployee(any(LocalDate.class), anyString(), any(Map.class), anyString());
        verify(mockDateParserService, times(2)).parse(anyString());
    }

}

package com.springapp.mvc.web.unit.model;

import com.springapp.mvc.web.model.AccrualRateCalculator;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class AccrualRateCalculatorTest {

    public static final double MAXIMUM_VACATION_DAYS = 30d;
    private final LocalDate TODAY = new LocalDate();
    private final LocalDate TOMORROW = new LocalDate().plusDays(1);
    private final LocalDate ONE_YEAR_FROM_NOW = new LocalDate().plusYears(1).plusDays(1);
    private final LocalDate THREE_YEARS_FROM_NOW = new LocalDate().plusYears(3).plusDays(1);
    private final LocalDate SIX_YEARS_FROM_NOW = new LocalDate().plusYears(6).plusDays(1);

    private final double DEFAULT_ACCRUAL_RATE = 10d;
    private final double ACCRUAL_RATE_AFTER_ONE_YEAR = 15d;
    private final double ACCRUAL_RATE_AFTER_THREE_YEARS = 20d;
    private final double ACCRUAL_RATE_AFTER_SIX_YEARS = 25d;

    private final double CUSTOM_INITIAL_ACCRUAL_RATE = 17d;

    private final double YEAR_IN_DAYS = 365.25;

    AccrualRateCalculator accrualRateCalculator;

    @Before
    public void setUp() throws Exception {
        accrualRateCalculator = new AccrualRateCalculator();
    }

    @Test
    public void shouldHaveAccrualRateOfTenDaysBeforeOneYearElapses() {
        assertThat(accrualRateCalculator.calculateDailyAccrualRate(TODAY, TOMORROW, DEFAULT_ACCRUAL_RATE), is(DEFAULT_ACCRUAL_RATE / YEAR_IN_DAYS));
    }

    @Test
    public void shouldHaveAccrualRateOfFifteenDaysAfterOneYear() {
        assertThat(accrualRateCalculator.calculateDailyAccrualRate(TODAY, ONE_YEAR_FROM_NOW, DEFAULT_ACCRUAL_RATE), is(ACCRUAL_RATE_AFTER_ONE_YEAR / YEAR_IN_DAYS));
    }

    @Test
    public void shouldHaveAccrualRateOfTwentyDaysAfterThreeYears() {
        assertThat(accrualRateCalculator.calculateDailyAccrualRate(TODAY, THREE_YEARS_FROM_NOW, DEFAULT_ACCRUAL_RATE), is(ACCRUAL_RATE_AFTER_THREE_YEARS / YEAR_IN_DAYS));
    }

    @Test
    public void shouldHaveAccrualRateOfTwentyFiveDaysAfterSixYears() {
        assertThat(accrualRateCalculator.calculateDailyAccrualRate(TODAY, SIX_YEARS_FROM_NOW, DEFAULT_ACCRUAL_RATE), is(ACCRUAL_RATE_AFTER_SIX_YEARS / YEAR_IN_DAYS));
    }

    @Test
    public void shouldHaveCustomAccrualRateIfSpecified() {
        assertThat(accrualRateCalculator.calculateDailyAccrualRate(TODAY, TOMORROW, CUSTOM_INITIAL_ACCRUAL_RATE), is(CUSTOM_INITIAL_ACCRUAL_RATE / YEAR_IN_DAYS));
        assertThat(accrualRateCalculator.calculateDailyAccrualRate(TODAY, ONE_YEAR_FROM_NOW, CUSTOM_INITIAL_ACCRUAL_RATE), is(CUSTOM_INITIAL_ACCRUAL_RATE / YEAR_IN_DAYS));
        assertThat(accrualRateCalculator.calculateDailyAccrualRate(TODAY, THREE_YEARS_FROM_NOW, CUSTOM_INITIAL_ACCRUAL_RATE), is(ACCRUAL_RATE_AFTER_THREE_YEARS / YEAR_IN_DAYS));

    }

    @Test
    public void shouldReturnVacationDayCap() {
        assertThat(accrualRateCalculator.calculateVacationDayCap(TODAY, TOMORROW, DEFAULT_ACCRUAL_RATE), is(DEFAULT_ACCRUAL_RATE * 1.5));
        assertThat(accrualRateCalculator.calculateVacationDayCap(TODAY, ONE_YEAR_FROM_NOW, DEFAULT_ACCRUAL_RATE), is(ACCRUAL_RATE_AFTER_ONE_YEAR * 1.5));
        assertThat(accrualRateCalculator.calculateVacationDayCap(TODAY, SIX_YEARS_FROM_NOW, DEFAULT_ACCRUAL_RATE), is(MAXIMUM_VACATION_DAYS));
    }


}
package com.springapp.mvc.web.service;

import com.springapp.mvc.web.model.Calculator;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    private Calculator calculator;

    @Autowired
    public CalculatorService(Calculator calculator) {
        this.calculator = calculator;
    }

    public double calculateVacationDaysGivenRate(double rolloverDays, double rate) {
        DateTime currentDate = new DateTime();
        return calculator.calculateVacationDaysGivenRate(currentDate, rolloverDays, rate);
    }

    public double calculateUsedVacationDays(String salesForceText) {
        SalesForceParserService salesForceParser = new SalesForceParserService();
        return salesForceParser.parse(salesForceText);
    }

    public double calculateVacationDays(String rollover, String accrualRate, String salesForceText) {
        double usedDays = calculateUsedVacationDays(salesForceText);
        double vacationDays = calculateVacationDaysGivenRate(Double.parseDouble(rollover), Double.parseDouble(accrualRate));
        vacationDays = vacationDays - usedDays;
        return Math.round(vacationDays*100)/100d;
    }
}

package com.springapp.mvc.web.service;

import org.joda.time.LocalDate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SalesForceParserService {

    private int findLine(String stringToFind, List<String> listOfStrings) {
        for (String line : listOfStrings) {
            if (line.contains(stringToFind)) {
                return listOfStrings.indexOf(line);
            }
        }
        return -1;
    }

    public HashMap<LocalDate, Double> parse(String salesForceText) {
        HashMap<LocalDate, Double> vacationDaysAndHours = new HashMap<LocalDate, Double>();

        List<String> textLines = Arrays.asList(salesForceText.split("\n"));
        int indexOfVacationLine = findLine("vacation", textLines);
        boolean moreLinesToRead =  textLines.size() - findLine("vacation", textLines)> 1 ;

        if (salesForceText.contains("vacation") && moreLinesToRead ) {
            int vacationInformation = indexOfVacationLine + 2;

            while (textLines.get(vacationInformation).contains("Non-sick leave")) {
                extractDateAndHours(vacationDaysAndHours, textLines, vacationInformation);
                vacationInformation++;
            }
        }

        return vacationDaysAndHours;
    }

    private void extractDateAndHours(HashMap<LocalDate, Double> vacationDaysAndHours, List<String> textLines, int vacationInformation) {
        List<String> parsedVacationInformation = Arrays.asList(textLines.get(vacationInformation).split("\t"));

        double hours = Double.parseDouble(parsedVacationInformation.get(5));

        String[] dateFields = parsedVacationInformation.get(3).split("/");
        LocalDate date = new LocalDate(Integer.parseInt(dateFields[2]), Integer.parseInt(dateFields[0]),
                Integer.parseInt(dateFields[1]));

        vacationDaysAndHours.put(date, hours);
    }
}

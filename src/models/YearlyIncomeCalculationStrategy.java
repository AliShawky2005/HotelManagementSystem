package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class YearlyIncomeCalculationStrategy implements IncomeCalculationStrategy {
    @Override
    public double calculateIncome(BufferedReader reader, LocalDate startDate, LocalDate endDate) throws IOException {
        double totalIncome = 0.0;
        String line;
        // Get the start and end of the year
        LocalDate startOfYear = startDate.withDayOfYear(1); // First day of the year
        LocalDate endOfYear = endDate.withDayOfYear(endDate.lengthOfYear()); // Last day of the year

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 5) {
                LocalDate reservationDate = LocalDate.parse(parts[4], DateTimeFormatter.ISO_DATE);
                double totalCost = Double.parseDouble(parts[4]);

                // Check if the reservation is within the current year
                if (!reservationDate.isBefore(startOfYear) && !reservationDate.isAfter(endOfYear)) {
                    totalIncome += totalCost;
                }
            }
        }
        return totalIncome;
    }
}

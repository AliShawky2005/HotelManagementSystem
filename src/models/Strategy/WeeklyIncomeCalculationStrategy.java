package models.Strategy;


import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WeeklyIncomeCalculationStrategy implements IncomeCalculationStrategy {
    @Override
    public double calculateIncome(BufferedReader reader, LocalDate startDate, LocalDate endDate) throws IOException {
        double totalIncome = 0.0;
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 5) {
                LocalDate reservationDate = LocalDate.parse(parts[5], DateTimeFormatter.ISO_DATE);
                double totalCost = Double.parseDouble(parts[4]);

                // Check if reservationDate is within the given week
                if (!reservationDate.isBefore(startDate) && !reservationDate.isAfter(endDate)) {
                    totalIncome += totalCost;
                }
            }
        }
        return totalIncome;
    }
}

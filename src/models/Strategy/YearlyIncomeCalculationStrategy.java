package models.Strategy;


import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class YearlyIncomeCalculationStrategy implements IncomeCalculationStrategy {
    @Override
    public double calculateIncome(BufferedReader reader) throws IOException {
        double totalIncome = 0.0;
        String line;
        // Get the start and end of the year
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusYears(1);

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 5) {
                LocalDate reservationDate = LocalDate.parse(parts[5], DateTimeFormatter.ISO_DATE);
                double totalCost = Double.parseDouble(parts[4]);

                // Check if the reservation is within the current year
                if (!reservationDate.isBefore(startDate) && !reservationDate.isAfter(endDate)) {
                    totalIncome += totalCost;
                }
            }
        }
        return totalIncome;
    }
}

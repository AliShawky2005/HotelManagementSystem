package models;

import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

interface IncomeCalculationStrategy {
    double calculateIncome(BufferedReader reader, LocalDate startDate, LocalDate endDate) throws IOException;
}

public class IncomeTracker {
    private static final String RESERVATIONS_FILE = "reservations.txt";
    private static IncomeCalculationStrategy incomeCalculationStrategy;

    public IncomeTracker(IncomeCalculationStrategy incomeCalculationStrategy) {
        this.incomeCalculationStrategy = incomeCalculationStrategy;
    }

    public void setIncomeCalculationStrategy(IncomeCalculationStrategy incomeCalculationStrategy) {
        this.incomeCalculationStrategy = incomeCalculationStrategy;
    }

    public static double calculateIncomeByDateRange(LocalDate startDate, LocalDate endDate) {
        double totalIncome = 0.0;

        try (BufferedReader reader = new BufferedReader(new FileReader(RESERVATIONS_FILE))) {
            totalIncome = incomeCalculationStrategy.calculateIncome(reader, startDate, endDate);
        } catch (IOException e) {
            System.out.println("Error calculating income: " + e.getMessage());
        }

        return totalIncome;
    }

    public static void generateIncomeReportForPeriod(JTextArea reportArea, LocalDate startDate, LocalDate endDate) {
        try (BufferedReader reader = new BufferedReader(new FileReader(RESERVATIONS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    try {
                        LocalDate reservationDate = LocalDate.parse(parts[5], DateTimeFormatter.ISO_DATE);
                        if ((reservationDate.isEqual(startDate) || reservationDate.isAfter(startDate))
                                && (reservationDate.isEqual(endDate) || reservationDate.isBefore(endDate))) {
                            reportArea.append(String.format("Room: %s | Email: %s | Description: %s | Nights: %s | Total: $%s | Date: %s\n",
                                    parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]));
                        }
                    } catch (DateTimeParseException e) {
                        System.err.println("Error parsing date: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            reportArea.append("Error generating detailed report: " + e.getMessage());
        }
        reportArea.repaint();
    }
}

package models;

import models.Strategy.IncomeCalculationStrategy;

import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class IncomeTracker {
    private static final String RESERVATIONS_FILE = "past_reservations.txt";
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
            totalIncome = incomeCalculationStrategy.calculateIncome(reader);
        } catch (IOException e) {
            System.out.println("Error calculating income: " + e.getMessage());
        }

        return totalIncome;
    }

    public static void generateIncomeReportForPeriod(JTextArea reportArea, LocalDate startDate, LocalDate endDate) {
        File reservationsFile = new File(RESERVATIONS_FILE);
        if (!reservationsFile.exists()) {
            reportArea.append("No reservation data found.\n");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(reservationsFile))) {
            String line;
            boolean hasReservations = false;

            reportArea.append("=== Detailed ReservationInfo Report ===\n");
            reportArea.append(String.format("From: %s To: %s\n\n", startDate, endDate));

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    try {
                        LocalDate reservationDate = LocalDate.parse(parts[5], DateTimeFormatter.ISO_DATE);
                        double totalCost = Double.parseDouble(parts[4]);

                        // Check if the reservation date is within the range
                        if (!reservationDate.isBefore(startDate) && !reservationDate.isAfter(endDate)) {
                            // Append reservation details to JTextArea
                            String reservationDetails = String.format(
                                    "Room: %s | Email: %s | Description: %s | Nights: %s | Total: $%.2f | Date: %s\n",
                                    parts[0], parts[1], parts[2], parts[3], totalCost, reservationDate
                            );

                            SwingUtilities.invokeLater(() -> reportArea.append(reservationDetails));
                            hasReservations = true;
                        }
                    } catch (DateTimeParseException | NumberFormatException e) {
                        System.err.println("Error parsing reservation entry: " + line + " -> " + e.getMessage());
                    }
                } else {
                    System.err.println("Invalid reservation entry: " + line);
                }
            }

            if (!hasReservations) {
                reportArea.append("No reservations found for the specified date range.\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            reportArea.append("Error generating detailed report: " + e.getMessage() + "\n");
        }

        reportArea.revalidate();
        reportArea.repaint();
    }


}

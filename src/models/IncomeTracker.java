package models;

import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class IncomeTracker {

    private static final String RESERVATIONS_FILE = "reservations.txt";

    // Method to calculate total income for a given date range
    public static double calculateIncomeByDateRange(LocalDate startDate, LocalDate endDate) {
        double totalIncome = 0.0;

        try (BufferedReader reader = new BufferedReader(new FileReader(RESERVATIONS_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length >= 5) {
                    // Extract the reservation date and total cost
                    LocalDate reservationDate = LocalDate.parse(parts[5], DateTimeFormatter.ISO_DATE);
                    double totalCost = Double.parseDouble(parts[4]);

                    // Check if the reservation date is within the specified range
                    if ((reservationDate.isEqual(startDate) || reservationDate.isAfter(startDate)) &&
                            (reservationDate.isEqual(endDate) || reservationDate.isBefore(endDate))) {
                        totalIncome += totalCost;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return totalIncome;
    }

    // Method to generate detailed income report for a given date range
    public static void generateIncomeReportForPeriod(JTextArea reportArea, LocalDate startDate, LocalDate endDate) {
        try (BufferedReader reader = new BufferedReader(new FileReader(RESERVATIONS_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length >= 5) {
                    LocalDate reservationDate = LocalDate.parse(parts[5], DateTimeFormatter.ISO_DATE);

                    // Check if the reservation date is within the range
                    if ((reservationDate.isEqual(startDate) || reservationDate.isAfter(startDate)) &&
                            (reservationDate.isEqual(endDate) || reservationDate.isBefore(endDate))) {
                        reportArea.append(String.format("Room: %s | Email: %s | Description: %s | Nights: %s | Total: $%s | Date: %s\n",
                                parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]));
                    }
                }
            }
        } catch (IOException e) {
            reportArea.append("Error generating report: " + e.getMessage());
        }
    }

    // Method to display all income data (for debugging purposes)
    public static void displayAllIncomeData() {
        System.out.println("=== All Income Data ===");

        try (BufferedReader reader = new BufferedReader(new FileReader(RESERVATIONS_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Optional: Method to add test data to the reservations file
    public static void addTestData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESERVATIONS_FILE, true))) {
            writer.write("101,Single Room,3,300.0,2024-06-14");
            writer.newLine();
            writer.write("102,Double Room,2,400.0,2024-06-15");
            writer.newLine();
            writer.write("103,Triple Room,5,750.0,2024-06-18");
            writer.newLine();
            writer.write("104,Single Room,1,100.0,2024-06-19");
            writer.newLine();
            System.out.println("Test data added.");
        } catch (IOException e) {
            System.out.println("Error writing test data: " + e.getMessage());
        }
    }

//    public static void main(String[] args) {
//        // Test the IncomeTracker functionality
//        LocalDate startDate = LocalDate.now().minusDays(7);
//        LocalDate endDate = LocalDate.now();
//
//        System.out.println("=== Weekly Income Report ===");
//        double weeklyIncome = calculateIncomeByDateRange(startDate, endDate);
//        System.out.println("Total Income: $" + weeklyIncome);
//
//        // Display all data
//        displayAllIncomeData();
//
//        // Add test data (optional)
//        // addTestData();
//    }
}

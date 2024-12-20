package gui;

import controllers.DataStore;
import models.IncomeTracker;
import models.Strategy.CustomDateRangeCalculationStrategy;
import models.Strategy.MonthlyIncomeCalculationStrategy;
import models.Strategy.WeeklyIncomeCalculationStrategy;
import models.Strategy.YearlyIncomeCalculationStrategy;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class IncomeTrackerForm {
    private JFrame frame;
    private JTextArea reportArea;
    private JButton weeklyButton, monthlyButton, yearlyButton, customButton, backButton;
    private JTextField startDateField, endDateField;
    private IncomeTracker incomeTracker;

    public IncomeTrackerForm() {
        incomeTracker = new IncomeTracker(new WeeklyIncomeCalculationStrategy()); // Default strategy
        initializeUI();
    }

    private void initializeUI() {
        // Main Frame
        frame = new JFrame("Income Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Top Panel: Buttons for Reports
        JPanel buttonPanel = createButtonPanel();
        frame.add(buttonPanel, BorderLayout.NORTH);

        // Center Panel: Report Area
        reportArea = new JTextArea(20, 50);
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(reportArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel: Custom Date Range Input
        JPanel customRangePanel = createCustomRangePanel();
        frame.add(customRangePanel, BorderLayout.SOUTH);

        // Left Panel: Back Button
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            frame.dispose(); // Close the current form
            new ManagerDashboard(DataStore.loggedInUser);
        });
        backPanel.add(backButton);
        frame.add(backPanel, BorderLayout.WEST);

        frame.setVisible(true);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Quick Reports"));

        weeklyButton = new JButton("Weekly Report");
        monthlyButton = new JButton("Monthly Report");
        yearlyButton = new JButton("Yearly Report");

        // Action Listeners
        weeklyButton.addActionListener(e -> showReportForPeriod("weekly"));
        monthlyButton.addActionListener(e -> showReportForPeriod("monthly"));
        yearlyButton.addActionListener(e -> showReportForPeriod("yearly"));

        panel.add(weeklyButton);
        panel.add(monthlyButton);
        panel.add(yearlyButton);

        return panel;
    }

    private JPanel createCustomRangePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Custom Date Range"));

        panel.add(new JLabel("Start Date (YYYY-MM-DD):"));
        startDateField = new JTextField(10);
        panel.add(startDateField);

        panel.add(new JLabel("End Date (YYYY-MM-DD):"));
        endDateField = new JTextField(10);
        panel.add(endDateField);

        customButton = new JButton("Generate Report");
        customButton.addActionListener(e -> showCustomDateRangeReport());
        panel.add(customButton);

        return panel;
    }

    private void showReportForPeriod(String period) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = null;

        // Calculate the start date based on the selected period
        switch (period) {
            case "weekly":
                incomeTracker.setIncomeCalculationStrategy(new WeeklyIncomeCalculationStrategy());
                startDate = endDate.minusWeeks(1);
                break;
            case "monthly":
                incomeTracker.setIncomeCalculationStrategy(new MonthlyIncomeCalculationStrategy());
                startDate = endDate.minusMonths(1);

                break;
            case "yearly":
                incomeTracker.setIncomeCalculationStrategy(new YearlyIncomeCalculationStrategy());
                startDate = endDate.minusYears(1);
                break;
        }


            double totalIncome = IncomeTracker.calculateIncomeByDateRange(startDate, endDate);
            updateReportArea(startDate, endDate, totalIncome);
            IncomeTracker.generateIncomeReportForPeriod(reportArea, startDate, endDate);

    }




    private void showCustomDateRangeReport() {
        try {
            LocalDate startDate = LocalDate.parse(startDateField.getText(), DateTimeFormatter.ISO_DATE);
            LocalDate endDate = LocalDate.parse(endDateField.getText(), DateTimeFormatter.ISO_DATE);

            if (startDate.isAfter(endDate)) {
                throw new IllegalArgumentException("Start date cannot be after end date.");
            }

            // Set a custom date range strategy
            incomeTracker.setIncomeCalculationStrategy(new CustomDateRangeCalculationStrategy());

            // Calculate income for the specified date range
            double totalIncome = incomeTracker.calculateIncomeByDateRange(startDate, endDate);

            // Update the report area with the results
            updateReportArea(startDate, endDate, totalIncome);
            IncomeTracker.generateIncomeReportForPeriod(reportArea, startDate, endDate);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(frame, "Invalid date format. Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void updateReportArea(LocalDate startDate, LocalDate endDate, double totalIncome) {
        SwingUtilities.invokeLater(() -> {
            reportArea.setText("=== CUSTOM DATE RANGE REPORT ===\n");
            reportArea.append("From: " + startDate + " To: " + endDate + "\n");
            reportArea.append("Total Income: $" + totalIncome + "\n\n");
            reportArea.append("Detailed Report:\n");
            reportArea.append("-------------------------------------------\n");
            reportArea.repaint();
        });
    }
}

package models.Strategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;

public interface IncomeCalculationStrategy {
    double calculateIncome(BufferedReader reader) throws IOException;
}

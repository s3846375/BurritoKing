package model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CreditCardAuthenticator {

    public static boolean validateCreditCard(String cardNo, String expiryDate, String cvv) {
        // Check if the card number has 16 digits
        if (cardNo.isEmpty() || !cardNo.matches("[0-9]{16}")) {
            System.out.println("Invalid card number");
            return false;
        }

        // Check if the expiry date is a future date
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            // Parse MM/yy to YearMonth type
            YearMonth expiryDateParsed = YearMonth.parse(expiryDate, formatter);
            // Converts the YearMonth to the last day of that month
            LocalDate endOfMonthExpiry = expiryDateParsed.atEndOfMonth();
            LocalDate currentDate = LocalDate.now();

            if (expiryDate.isEmpty() || endOfMonthExpiry.isBefore(currentDate) || endOfMonthExpiry.isEqual(currentDate)) {
                System.out.println("Invalid expiry date");
                return false;
            }

        } catch (DateTimeParseException e) {
            System.out.println("Invalid expiry date input format");
            return false;
        }

        // Check if the CVV has 3 digits
        if (cvv.isEmpty() || !cvv.matches("[0-9]{3}")) {
            System.out.println("Invalid cvv");
            return false;
        }
        return true;
    }
}
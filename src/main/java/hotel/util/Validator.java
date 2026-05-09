package hotel.util;

import hotel.exception.InvalidDateException;
import hotel.exception.InvalidFormatException;

import java.time.LocalDate;

public final class Validator {

    private Validator() {}

    public static void validateName(String name) {
        if (name == null || name.trim().length() < 2) {
            throw new InvalidFormatException("Name", "must have at least 2 characters");
        }
    }

    public static void validateEmail(String email) {
        if (email == null || !email.matches("^[\\w.+\\-]+@[\\w\\-]+\\.[a-zA-Z]{2,}$")) {
            throw new InvalidFormatException("Email", "invalid format (e.g. name@domain.com)");
        }
    }

    public static void validatePhone(String phone) {
        if (phone == null || !phone.matches("\\d{9,15}")) {
            throw new InvalidFormatException("Phone", "must have between 9 and 15 digits");
        }
    }

    public static void validateTaxId(String taxId) {
        if (taxId == null || !taxId.matches("\\d{9}")) {
            throw new InvalidFormatException("Tax ID", "must have exactly 9 digits");
        }
    }

    public static void validateDates(LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkInDate == null || checkOutDate == null || !checkInDate.isBefore(checkOutDate)) {
            throw new InvalidDateException();
        }
    }

    public static void validatePricePerNight(double pricePerNight) {
        if (pricePerNight <= 0) {
            throw new InvalidFormatException("Price per night", "must be greater than 0");
        }
    }
}

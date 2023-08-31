package logic;

public class Logic {

    // Used to check whether a given string contains purely numeric values.
    public static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    public static boolean isValidYear(int year) {
        return (year > 0 && year < 9999);
    }

    public static boolean isValidMonth(int month) {
        return (month > 0 && month < 13);
    }

    public static boolean isValidDay(int day) {
        return (day > 0 && day < 32);
    }

    public static boolean isValidHour(int hour) { return (hour >= 0 && hour <= 23 ); }

    public static boolean isValidMinute(int min) { return (min >= 0 && min <= 59); }
}

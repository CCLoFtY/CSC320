import java.util.Locale;
import java.util.Scanner;
import java.text.NumberFormat;

public class AverageWithholding {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US); // ensure '.' decimal and $ formatting
        Scanner sc = new Scanner(System.in);
        NumberFormat money = NumberFormat.getCurrencyInstance(Locale.US);

        System.out.print("How many weeks of income will you enter? ");
        int weeks = readPositiveInt(sc);

        double totalWithholding = 0.0;

        for (int i = 1; i <= weeks; i++) {
            double income = readNonNegativeDouble(sc, "Enter income for week " + i + ": ");

            double rate = taxRateForWeeklyIncome(income);
            double withholding = income * rate;
            totalWithholding += withholding;

            System.out.println("  Rate applied: " + (int)(rate * 100) + "%");
            System.out.println("  Week " + i + " withholding: " + money.format(withholding));
            System.out.println();
        }

        double average = totalWithholding / weeks;
        System.out.println("======================================");
        System.out.println("Average weekly withholding: " + money.format(average));
        System.out.println("Total withholding over " + weeks + " week(s): " + money.format(totalWithholding));

        sc.close();
    }

    // Brackets per assignment
    private static double taxRateForWeeklyIncome(double income) {
        if (income < 500.0) {
            return 0.10;
        } else if (income < 1500.0) {
            return 0.15;
        } else if (income < 2500.0) {
            return 0.20;
        } else {
            return 0.30;
        }
    }

    // --- small input helpers to satisfy style/robustness rubric ---
    private static int readPositiveInt(Scanner sc) {
        while (true) {
            String line = sc.nextLine().trim();
            try {
                int n = Integer.parseInt(line);
                if (n > 0) return n;
            } catch (NumberFormatException ignored) {}
            System.out.print("Please enter a positive whole number: ");
        }
    }

    private static double readNonNegativeDouble(Scanner sc, String prompt) {
        System.out.print(prompt);
        while (true) {
            String line = sc.nextLine().trim().replace("$", "");
            try {
                double v = Double.parseDouble(line);
                if (v >= 0.0) return v;
            } catch (NumberFormatException ignored) {}
            System.out.print("  Enter a non-negative number (e.g., 1234.56): ");
        }
    }
}
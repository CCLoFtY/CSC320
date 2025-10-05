import java.util.Locale;
import java.util.Scanner;

public class FiveStatsWhile {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US); // ensure dot decimal parsing
        Scanner sc = new Scanner(System.in);

        int count = 0;
        double total = 0.0;
        double max = Double.NEGATIVE_INFINITY;
        double min = Double.POSITIVE_INFINITY;

        System.out.println("Please enter 5 floating point values.");

        // Main while loop to ensure exactly five values are recorded
        while (count < 5) {
            System.out.print("Enter value #" + (count + 1) + ": ");

            int attempts = 0; // prevents endless loop on repeated bad input
            boolean accepted = false;

            while (attempts < 5 && !accepted) {
                String line = sc.nextLine().trim();
                try {
                    double value = Double.parseDouble(line);
                    total += value;
                    if (value > max) max = value;
                    if (value < min) min = value;
                    count++;
                    accepted = true; // exit inner loop
                } catch (NumberFormatException e) {
                    attempts++;
                    if (attempts < 5) {
                        System.out.print("  Invalid number. Try again: ");
                    } else {
                        System.out.println("  Too many invalid attempts. Recording 0 for this entry.");
                        double value = 0.0;
                        total += value;
                        if (value > max) max = value;
                        if (value < min) min = value;
                        count++;
                    }
                }
            }
        }

        double average = total / 5.0;
        double interest = total * 0.20;

        System.out.println("\n===== Results =====");
        System.out.printf("Total: %.2f%n", total);
        System.out.printf("Average: %.2f%n", average);
        System.out.printf("Maximum: %.2f%n", max);
        System.out.printf("Minimum: %.2f%n", min);
        System.out.printf("Interest on total at 20%%: %.2f%n", interest);

        sc.close();
    }
}
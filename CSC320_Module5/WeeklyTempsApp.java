import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class WeeklyTempsApp {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        // Two ArrayLists as required
        ArrayList<String> days = new ArrayList<>(Arrays.asList(
                "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        ));
        ArrayList<Double> temps = new ArrayList<>(7);

        // Collect temperatures for the week
        System.out.println("Enter the average temperature for each day:");
        for (int i = 0; i < days.size(); i++) {
            double value = readDouble(sc, "  " + days.get(i) + ": ");
            temps.add(value);
        }

        // Query loop
        System.out.println();
        System.out.println("Type a day name (e.g., Monday), 'week' for all days + average, or 'quit' to exit.");
        while (true) {
            System.out.print("> ");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("quit")) {
                break;
            } else if (input.equalsIgnoreCase("week")) {
                double total = 0.0;
                System.out.println("Temperatures for the week:");
                for (int i = 0; i < days.size(); i++) {
                    System.out.printf("  %-10s : %.2f%n", days.get(i), temps.get(i));
                    total += temps.get(i);
                }
                double avg = total / days.size();
                System.out.printf("Weekly average: %.2f%n", avg);
            } else {
                int idx = indexOfDay(days, input);
                if (idx >= 0) {
                    System.out.printf("%s: %.2f%n", days.get(idx), temps.get(idx));
                } else {
                    System.out.println("Invalid day. Please enter Monday..Sunday, 'week', or 'quit'.");
                }
            }
        }

        System.out.println("Goodbye!");
        sc.close();
    }

    private static int indexOfDay(ArrayList<String> days, String input) {
        for (int i = 0; i < days.size(); i++) {
            if (days.get(i).equalsIgnoreCase(input)) return i;
        }
        return -1;
    }

    private static double readDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a valid number (e.g., 72 or 72.5).");
            }
        }
    }
}
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.Year;
import java.util.Scanner;

public class AutomobileApp {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            try {
                // Parameterized constructor
                Automobile car = new Automobile("Toyota", "Corolla", "Blue", 2020, 21500);

                // List values
                System.out.println("Initial vehicle (from parameterized constructor):");
                for (String line : car.listVehicleInfo()) {
                    System.out.println("  " + line);
                }

                // Remove vehicle
                String removed = car.removeVehicle("Toyota", "Corolla", "Blue", 2020);
                System.out.println("\nRemoveVehicle result: " + removed);

                // Add a new vehicle
                String added = car.addVehicle("Honda", "Civic", "Red", 2022, 12500);
                System.out.println("AddVehicle result: " + added);

                // List after add
                System.out.println("\nAfter AddVehicle:");
                for (String line : car.listVehicleInfo()) {
                    System.out.println("  " + line);
                }

                // Update vehicle
                String updated = car.updateVehicleAttributes(null, "Civic EX", null, 2023, 13100);
                System.out.println("\nUpdate result: " + updated);

                // List after update
                System.out.println("\nAfter Update:");
                String[] info = car.listVehicleInfo();
                for (String line : info) {
                    System.out.println("  " + line);
                }

                // Ask to print to file
                System.out.print("\nPrint information to file (Y/N)? ");
                String answer = sc.nextLine().trim();
                if (answer.equalsIgnoreCase("Y")) {
                    String dir = "C:\\Temp";
                    String path = dir + "\\Autos.txt";
                    ensureDir(dir);
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, false))) {
                        for (String line : info) {
                            bw.write(line);
                            bw.newLine();
                        }
                    }
                    System.out.println("File written to: " + path);
                } else {
                    System.out.println("File will not be printed.");
                }
            } catch (Exception inner) {
                System.out.println("Main flow error: " + inner.getMessage());
            }
        } catch (Exception outer) {
            System.out.println("Application error: " + outer.getMessage());
        }
    }

    private static void ensureDir(String dirPath) {
        try {
            File d = new File(dirPath);
            if (!d.exists()) d.mkdirs();
        } catch (Exception ignored) { }
    }
}

// Note: no 'public' here so we can keep both classes in one file
class Automobile {
    private String make;
    private String model;
    private String color;
    private int year;
    private int mileage;

    public Automobile() {
        try {
            this.make = "";
            this.model = "";
            this.color = "";
            this.year = 0;
            this.mileage = 0;
        } catch (Exception e) { /* keep safe defaults */ }
    }

    public Automobile(String make, String model, String color, int year, int mileage) {
        try {
            if (isValidText(make) && isValidText(model) && isValidText(color)
                    && isValidYear(year) && mileage >= 0) {
                this.make = make.trim();
                this.model = model.trim();
                this.color = color.trim();
                this.year = year;
                this.mileage = mileage;
            } else {
                throw new IllegalArgumentException("Invalid input for Automobile constructor");
            }
        } catch (Exception e) {
            this.make = "";
            this.model = "";
            this.color = "";
            this.year = 0;
            this.mileage = 0;
        }
    }

    public String addVehicle(String make, String model, String color, int year, int mileage) {
        try {
            if (isValidText(make) && isValidText(model) && isValidText(color)
                    && isValidYear(year) && mileage >= 0) {
                this.make = make.trim();
                this.model = model.trim();
                this.color = color.trim();
                this.year = year;
                this.mileage = mileage;
                return "AddVehicle: success";
            } else {
                return "AddVehicle: invalid input";
            }
        } catch (Exception e) {
            return "AddVehicle: failure — " + e.getMessage();
        }
    }

    public String[] listVehicleInfo() {
        try {
            return new String[]{
                "Make: " + make,
                "Model: " + model,
                "Color: " + color,
                "Year: " + year,
                "Mileage: " + mileage
            };
        } catch (Exception e) {
            return new String[]{"List: failure — " + e.getMessage()};
        }
    }

    public String removeVehicle(String autoMake, String autoModel, String autoColor, int autoYear) {
        try {
            boolean matches =
                safeEquals(this.make, autoMake) &&
                safeEquals(this.model, autoModel) &&
                safeEquals(this.color, autoColor) &&
                this.year == autoYear;

            if (matches) {
                this.make = "";
                this.model = "";
                this.color = "";
                this.year = 0;
                this.mileage = 0;
                return "RemoveVehicle: success";
            } else {
                return "RemoveVehicle: mismatch — not removed";
            }
        } catch (Exception e) {
            return "RemoveVehicle: failure — " + e.getMessage();
        }
    }

    public String updateVehicleAttributes(String newMake, String newModel, String newColor,
                                          Integer newYear, Integer newMileage) {
        try {
            int changes = 0;

            if (isValidText(newMake))  { this.make = newMake.trim();   changes++; }
            if (isValidText(newModel)) { this.model = newModel.trim(); changes++; }
            if (isValidText(newColor)) { this.color = newColor.trim(); changes++; }

            if (newYear != null) {
                if (isValidYear(newYear)) { this.year = newYear; changes++; }
                else return "Update: invalid year";
            }
            if (newMileage != null) {
                if (newMileage >= 0) { this.mileage = newMileage; changes++; }
                else return "Update: invalid mileage";
            }

            return (changes > 0) ? "Update: success (" + changes + " field(s))"
                                 : "Update: no changes";
        } catch (Exception e) {
            return "Update: failure — " + e.getMessage();
        }
    }

    private static boolean isValidText(String s) {
        return s != null && !s.trim().isEmpty();
    }
    private static boolean safeEquals(String a, String b) {
        return (a == null ? "" : a).equalsIgnoreCase(b == null ? "" : b);
    }
    private static boolean isValidYear(int y) {
        int current = Year.now().getValue();
        return y >= 1886 && y <= current + 1;
    }
}
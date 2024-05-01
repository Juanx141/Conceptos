import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SalesEvaluator {
    private Map<String, Integer> salesCount;

    public SalesEvaluator() {
        this.salesCount = new HashMap<>();
    }

    public void evaluateSalesFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            String currentVendor = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim(); 
                if (line.isEmpty()) {
                    continue; // En caso de que la linea este en blanco
                }

                if (line.startsWith("CE") || line.startsWith("TI") || line.startsWith("CC")) {
                    int firstSpaceIndex = line.indexOf(" ");
                    if (firstSpaceIndex == -1) { // ID
                        currentVendor = line;
                    } else {
                        currentVendor = line.substring(0, firstSpaceIndex).trim();
                    }
                    salesCount.putIfAbsent(currentVendor, 0); 
                } else if (currentVendor != null && line.contains(";")) {
                    String[] parts = line.split(";");
                    if (parts.length == 2) {
                        try {
                            int sales = Integer.parseInt(parts[1].trim());
                            salesCount.put(currentVendor, salesCount.get(currentVendor) + sales);
                        } catch (NumberFormatException e) {
                            System.out.println("Skipping non-integer quantity: " + parts[1]);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public Map<String, Integer> getSalesCount() {
        return this.salesCount;
    }

    public void writeSalesToCSV(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.append("Vendor ID,Sales\n");
            for (Map.Entry<String, Integer> entry : salesCount.entrySet()) {
                writer.append(entry.getKey())
                      .append(",")
                      .append(Integer.toString(entry.getValue()))
                      .append("\n");
            }
        } catch (IOException e) {
            System.out.println("CSV no se leyo correctamente: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String filename = "ventas.txt"; // Hard-coded filename
        SalesEvaluator evaluator = new SalesEvaluator();
        evaluator.evaluateSalesFromFile(filename);

        System.out.println("Ventas por vendedor:");
        evaluator.getSalesCount().forEach((key, value) -> System.out.println(key + ": " + value));

        evaluator.writeSalesToCSV("vendeventas.csv");
    }
}
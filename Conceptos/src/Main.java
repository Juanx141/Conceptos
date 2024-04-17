import java.io.*;
import java.util.*;

public class Main {
    private static final String SALES_FILE_PREFIX = "Vendedor"; // Prefijo común para los archivos de ventas
    private static Map<Integer, String> productInfo; // Almacena la información de los productos globalmente
    private static Map<String, Integer> productSales = new HashMap<>(); // Mapa para total de ventas por producto

    // Método para leer y procesar los datos de los productos
    private static void loadProducts() throws IOException {
        productInfo = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader("productos.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(";");
            if (parts.length == 3) {
                int id = Integer.parseInt(parts[0].replace("IDProducto", ""));
                productInfo.put(id, parts[1] + ";" + parts[2]); // Almacenamos nombre y precio del producto
            } else {
                System.err.println("No se encontro registro e esta linea:  " + line);
            }
        }
        reader.close();
    }

    // Método para procesar archivos de ventas de cada vendedor y acumular ventas por producto
    private static void processSalesFiles() throws IOException {
        File dir = new File(".");
        File[] files = dir.listFiles((d, name) -> name.startsWith(SALES_FILE_PREFIX) && name.endsWith(".txt"));
        if (files != null) {
            for (File file : files) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("IDProducto")) {
                        String[] parts = line.split(";");
                        if (parts.length == 2) {
                            int productID = Integer.parseInt(parts[0].replace("IDProducto", ""));
                            int cantidad = Integer.parseInt(parts[1]);
                            String productName = productInfo.get(productID).split(";")[0];
                            productSales.put(productName, productSales.getOrDefault(productName, 0) + cantidad); // Suma al total del producto
                        }
                    }
                }
                reader.close();
            }
        } else {
            System.err.println("No se encontro.");
        }
    }

    // Método para crear el archivo CSV 
    private static void createProductSalesCSV() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("ventas_productos.csv"));
        productSales.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
            .forEach(e -> {
                try {
                    writer.write(e.getKey() + ";" + e.getValue());
                    writer.newLine();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        writer.close();
    }

    public static void main(String[] args) {
        try {
            loadProducts(); // Carga inicial de los productos
            processSalesFiles();
            createProductSalesCSV(); // Crear el archivo CSV para productos
        } catch (IOException e) {
            System.err.println("Hubo algun error con los archivos: " + e.getMessage());
        }
    }
}
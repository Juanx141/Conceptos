import java.io.*;
import java.util.*;

public class GenerateIntoFiles {
    
    // Método para crear archivo de ventas de un vendedor pseudoaleatorio
    public static void createSalesMenFile(int randomSalesCount, String name, long id) {
        try {
            Random rand = new Random(); 
            BufferedWriter writer = new BufferedWriter(new FileWriter(name + "_" + id + ".txt"));
            String[] documents = {"CC", "TI", "CE"};
            for (int i = 0; i < randomSalesCount; i++) {
                String docType = documents[rand.nextInt(documents.length)];
                int numeroDoc = 1000000 + rand.nextInt(9000000); //Se suma 1M para asegurar ID de 7 digitos y que la funcion random no genere ID de pocos digitos 
                writer.write(docType + ";" + numeroDoc);
                writer.newLine();
                int cantidadProd = 1 + rand.nextInt(5); //Limite hasta 5 productos
                for (int j = 0; j < cantidadProd; j++) {
                    int produID = 1 + rand.nextInt(10);
                    int cantidad = 1 + rand.nextInt(10);
                    writer.write("IDProducto" + produID + ";" + cantidad);
                    writer.newLine();
                }
            }
            writer.close();
            System.out.println("Archivo de ventas para " + name + " creado exitosamente. C:");
        } catch (IOException e) {
            System.err.println("Error al crear archivo de ventas para " + name + ":C : " + e.getMessage());
        }
    }
    
    // Método para crear archivo de información de productos pseudoaleatorio
    public static void createProductsFile(int productsCount) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("productos.txt"));
            Random rand = new Random();
            for (int i = 1; i <= productsCount; i++) {
                writer.write("IDProducto" + i + ";NombreProducto" + i + ";" + (10 + rand.nextInt(91)));
                writer.newLine();
            }
            writer.close();
            System.out.println("Archivo de productos creado exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al crear archivo de productos: " + e.getMessage());
        }
    }
    
    // Método para crear archivo de información de vendedores pseudoaleatorio
    public static void createSalesManInfoFile(int salesmanCount) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("vendedores.txt"));
            Random rand = new Random();
            String[] documentTypes = {"CC", "TI", "CE"};
            List<String> nombres = Arrays.asList("Juan", "Josefin", "Carlos", "Luis", "Esperancita", "Markitoz", "Laura", "Diego");
            List<String> apellidos = Arrays.asList("Bretaña", "Perez", "Rodriguez", "Lopez", "Martinez", "Gonzalez");
            for (int i = 1; i <= salesmanCount; i++) {
                String tipoDoc = documentTypes[rand.nextInt(documentTypes.length)];
                int numID = 1000000 + rand.nextInt(9000000); //Se suma 1M para asegurar ID de 7 digitos y que la funcion random no genere ID de pocos digitos
                String uNombre = nombres.get(rand.nextInt(nombres.size()));
                String uApellido = apellidos.get(rand.nextInt(apellidos.size()));
                writer.write(tipoDoc + ";" + numID + ";" + uNombre + ";" + uApellido);
                writer.newLine();
            }
            writer.close();
            System.out.println("Archivo de información de vendedores creado exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al crear archivo de información de vendedores: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
    	
        createSalesMenFile(10, "Vendedor1", 123456789);
        createProductsFile(10);
        createSalesManInfoFile(10);
    }
}
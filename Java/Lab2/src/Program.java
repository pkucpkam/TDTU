
import java.util.List;
import java.util.Scanner;

public class Program {
    private static ProductDAO productDAO;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
//      Enter db connection
        System.out.println("Enter database url: ");
//      jdbc:mysql://localhost:3306/ProductManagement
        String dbUrl = scanner.nextLine();
        System.out.println("Enter database username: ");
        String dbUsername = scanner.nextLine();
        System.out.println("Enter database password: ");
        String dbPassword = scanner.nextLine();


        try {
            productDAO = new ProductDAO(dbUrl, dbUsername, dbPassword);
            productDAO.createDatabase();
            productDAO.createTable();

            int choice;
            do {
                displayMenu();
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        readProductList();
                        break;
                    case 2:
                        readProductById();
                        break;
                    case 3:
                        addNewProduct();
                        break;
                    case 4:
                        updateProduct();
                        break;
                    case 5:
                        deleteProduct();
                        break;
                    case 6:
                        System.out.println("Exiting program...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 6);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (productDAO != null) {
                productDAO.close();
            }
            scanner.close();
        }
    }

    private static void displayMenu() {
        System.out.println("\n--- Product Management Menu ---");
        System.out.println("1. Read product list");
        System.out.println("2. Read a product by ID");
        System.out.println("3. Add a new product");
        System.out.println("4. Update a product");
        System.out.println("5. Delete a product");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void readProductList() {
        List<Product> products = productDAO.readAll();
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }

    private static void readProductById() {
        System.out.print("Enter product ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Product product = productDAO.read(id);
        if (product != null) {
            System.out.println(product);
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void addNewProduct() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter product color: ");
        String color = scanner.nextLine();

        Product newProduct = new Product(0, name, price, color);
        Integer id = productDAO.add(newProduct);
        if (id != null) {
            System.out.println("Product added successfully. New ID: " + id);
        } else {
            System.out.println("Failed to add product.");
        }
    }

    private static void updateProduct() {
        System.out.print("Enter product ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Product existingProduct = productDAO.read(id);
        if (existingProduct == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("Enter new name (press enter to keep current): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            existingProduct.setName(name);
        }

        System.out.print("Enter new price (enter -1 to keep current): ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        if (price != -1) {
            existingProduct.setPrice(price);
        }

        System.out.print("Enter new color (press enter to keep current): ");
        String color = scanner.nextLine();
        if (!color.isEmpty()) {
            existingProduct.setColor(color);
        }

        boolean updated = productDAO.update(existingProduct);
        if (updated) {
            System.out.println("Product updated successfully.");
        } else {
            System.out.println("Failed to update product.");
        }
    }

    private static void deleteProduct() {
        System.out.print("Enter product ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        boolean deleted = productDAO.delete(id);
        if (deleted) {
            System.out.println("Product deleted successfully.");
        } else {
            System.out.println("Failed to delete product. Product may not exist.");
        }
    }
}
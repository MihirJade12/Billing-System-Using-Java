import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;


class Product {
    private String name;
    private double price;
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
}

class InvoiceItem {
    private Product product;
    private int quantity;
    public InvoiceItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    public double getTotal() {
        return product.getPrice() * quantity;
    }
    @Override
    public String toString() {
        return String.format("%s - $%.2f x %d = $%.2f", product.getName(), product.getPrice(), quantity, getTotal());
    }
}

class Invoice {
    private List<InvoiceItem> items;
    public Invoice() {
        items = new ArrayList<>();
    }
    public void addItem(InvoiceItem item) {
        items.add(item);
    }
    public double getTotal() {
        double total = 0;
        for (InvoiceItem item : items) {
            total +=item.getTotal();
        }
        return total;
    }
    public void printInvoice() {
        System.out.println("Invoice:");
        for (InvoiceItem item : items) {
            System.out.println(item);
        }
        System.out.println("Total: Rs" + getTotal());
    }
}

public class BillingSystem {
    private List<Product> products;
    private Invoice currentInvoice;
    public BillingSystem() {
        products = new ArrayList<>();
        currentInvoice = new Invoice();
    }
    public void addProduct(Product product) {
        products.add(product);
    }
    public void startNewInvoice() {
        currentInvoice = new Invoice();
    }
    public void addToInvoice(Product product, int quantity) {
        currentInvoice.addItem(new InvoiceItem(product, quantity));
    }
    public void generateBill() {
        currentInvoice.printInvoice();
    }
    public static void main(String[] args) {
        BillingSystem billingSystem = new BillingSystem();
        billingSystem.addProduct(new Product("Product A", 50));
        billingSystem.addProduct(new Product("Product B", 100));
        billingSystem.addProduct(new Product("Product C",65));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Start New Invoice");
            System.out.println("2. Add Product to Invoice");
            System.out.println("3. Generate Bill");
            System.out.println("4. Exit");
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); //Consume new line

            switch (choice) {
                case 1:
                    billingSystem.startNewInvoice();
                    break;
                case 2:
                    System.out.println("Enter product name: ");
                    String productName = scanner.nextLine();
                    Product product = billingSystem.products.stream().filter(p -> p.getName().equalsIgnoreCase(productName)).findFirst().orElse(null);
                    if (product == null) {
                        System.out.println("Product not found.");
                        break;
                    }
                    System.out.println("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    billingSystem.addToInvoice(product, quantity);
                    break;
                case 3:
                    billingSystem.generateBill();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
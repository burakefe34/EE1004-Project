import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter library name: ");
        Library library = new Library(scanner.nextLine().trim());
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Choose an option (1-6): ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                continue;
            }

            switch (choice) {

                case 1: 
                    System.out.print("Enter item type (book/magazine/dvd): ");
                    String type = scanner.nextLine().trim().toLowerCase(); 

                    System.out.print("Enter title: ");
                    String title = scanner.nextLine().trim();   

                    System.out.print("Enter author: ");
                    String author = scanner.nextLine().trim();  

                    System.out.print("Enter year: ");
                    int year;
                    try {
                        year = Integer.parseInt(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid year. Skipping...");
                        break;
                    }

                    switch (type) {
                        case "book":
                            library.addItem(new Book(title, author, year));
                            break;
                        case "magazine":
                            library.addItem(new Magazine(title, author, year));
                            break;
                        case "dvd":
                            library.addItem(new DVD(title, author, year));
                            break;
                        default:
                            System.out.println("Unknown item type. Skipping..."); 
                            break;
                    }
                    break;

                case 2:
                    library.listAllItems();
                    break;

                case 3: 
                    System.out.print("Enter title to check out: ");
                    library.checkOutItem(scanner.nextLine().trim());
                    break;

                case 4: 
                    System.out.print("Enter title to return: ");
                    library.returnItem(scanner.nextLine().trim());
                    break;

                case 5: 
                    System.out.print("Enter search keyword: ");
                    library.searchByTitle(scanner.nextLine().trim());
                    break;

                case 6: 
                    System.out.println("Exiting. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please choose between 1 and 6.");
                    break;
            }
        }

        scanner.close();
    }
    private static void printMenu() {
        System.out.println("--- Library Menu ---");
        System.out.println("1. Add a new item");
        System.out.println("2. List all items");
        System.out.println("3. Check out an item");
        System.out.println("4. Return an item");
        System.out.println("5. Search by title");
        System.out.println("6. Exit");
    }
}

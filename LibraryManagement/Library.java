import java.util.ArrayList;
import java.util.List;
public class Library {

    private String            name;
    private List<LibraryItem> items = new ArrayList<>();
    public Library(String name) {
        this.name = name;
    }
    public void addItem(LibraryItem item) {
        items.add(item);
        System.out.println("Item added!");
    }
    public void checkOutItem(String title) {
        for (LibraryItem item : items) {
            if (item.getTitle().equalsIgnoreCase(title)) {
                if (item.checkOut()) {
                    System.out.println("Checked out: " + item.getTitle());
                } else {
                    System.out.println("Item is already checked out: " + item.getTitle());
                }
                return;
            }
        }
        System.out.println("Item not found: " + title);
    }
    public void returnItem(String title) {
        for (LibraryItem item : items) {
            if (item.getTitle().equalsIgnoreCase(title)) {
                if (item.returnItem()) {
                    System.out.println("Returned: " + item.getTitle());
                } else {
                    System.out.println("Item was not checked out: " + item.getTitle());
                }
                return;
            }
        }
        System.out.println("Item not found: " + title);
    }
    public void searchByTitle(String keyword) {
        System.out.println("Search results:");
        boolean found = false;
        for (LibraryItem item : items) {
            if (item.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println("- " + item);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No items found matching: " + keyword);
        }
    }
    public void listAllItems() {
        if (items.isEmpty()) {
            System.out.println("No items in the library.");
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ") " + items.get(i));
        }
    }
    public String getName() { return name; }
    public List<LibraryItem> getItems() { return items; }
}

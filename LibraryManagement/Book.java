public class Book extends LibraryItem {
    private static final int MAX_LOAN_DAYS = 21;
    public Book(String title, String author, int year) {
        super(title, author, year);
    }
    public int getMaxLoanDays() {
        return MAX_LOAN_DAYS;
    }
    protected String getTypeTag() {
        return "[Book]";
    }
}

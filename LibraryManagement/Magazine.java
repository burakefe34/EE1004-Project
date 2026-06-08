
public class Magazine extends LibraryItem {
    private static final int MAX_LOAN_DAYS = 7;
    public Magazine(String title, String author, int year) {
        super(title, author, year);
    }
    public int getMaxLoanDays() {
        return MAX_LOAN_DAYS;
    }
    protected String getTypeTag() {
        return "[Magazine]";
    }
}

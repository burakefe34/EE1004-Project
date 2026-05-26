
public abstract class LibraryItem {

    protected String  title;
    protected String  author;
    protected int     year;
    protected boolean isCheckedOut = false;
    public LibraryItem(String title, String author, int year) {
        this.title  = title;
        this.author = author;
        this.year   = year;
    }
    public abstract int getMaxLoanDays();
    protected abstract String getTypeTag();
    public boolean checkOut() {
        if (isCheckedOut) return false;
        isCheckedOut = true;
        return true;
    }
    public boolean returnItem() {
        if (!isCheckedOut) return false;
        isCheckedOut = false;
        return true;
    }
    public String toString() {
        String status = isCheckedOut ? "Checked Out" : "Available";
        return String.format("%s %s by %s (%d) - %s | Max Loan: %d days",
                getTypeTag(), title, author, year, status, getMaxLoanDays());
    }
    public String  getTitle()     { return title; }
    public String  getAuthor()    { return author; }
    public int     getYear()      { return year; }
    public boolean isCheckedOut() { return isCheckedOut; }
}

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.util.List;
import java.util.regex.Pattern;
public class LibraryGUI extends JFrame {

    
    private final Library library;

    
    private final DefaultTableModel tableModel;
    private final JTable itemsTable;
    private final TableRowSorter<DefaultTableModel> rowSorter;

    
    private final JComboBox<String> typeCombo;
    private final JTextField titleField;
    private final JTextField authorField;
    private final JTextField yearField;

    
    private final JTextField searchField;
    private final JLabel statusLabel;


    private static final int COL_TYPE     = 0;
    private static final int COL_TITLE    = 1;
    private static final int COL_AUTHOR   = 2;
    private static final int COL_YEAR     = 3;
    private static final int COL_STATUS   = 4;
    private static final int COL_MAX_LOAN = 5;

    public LibraryGUI(String libraryName) {
        this.library = new Library(libraryName);

        setTitle("Library Management System - " + libraryName);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(960, 620);
        setMinimumSize(new Dimension(720, 480));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        JPanel addPanel = new JPanel(new GridBagLayout());
        addPanel.setBorder(BorderFactory.createTitledBorder("Add a New Item"));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 6, 4, 6);
        c.anchor = GridBagConstraints.WEST;

        typeCombo   = new JComboBox<>(new String[] { "Book", "Magazine", "DVD" });
        titleField  = new JTextField(18);
        authorField = new JTextField(18);
        yearField   = new JTextField(6);

       
        c.gridx = 0; c.gridy = 0; addPanel.add(new JLabel("Type:"), c);
        c.gridx = 1;              addPanel.add(typeCombo, c);
        c.gridx = 2;              addPanel.add(new JLabel("Title:"), c);
        c.gridx = 3; c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0;
                                  addPanel.add(titleField, c);
        c.fill = GridBagConstraints.NONE; c.weightx = 0;

        
        c.gridx = 0; c.gridy = 1; addPanel.add(new JLabel("Author:"), c);
        c.gridx = 1; c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0;
                                  addPanel.add(authorField, c);
        c.fill = GridBagConstraints.NONE; c.weightx = 0;
        c.gridx = 2;              addPanel.add(new JLabel("Year:"), c);
        c.gridx = 3;              addPanel.add(yearField, c);

        
        JButton addButton = new JButton("Add Item");
        addButton.setMnemonic('A');
        c.gridx = 4; c.gridy = 0; c.gridheight = 2;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(4, 14, 4, 6);
        addPanel.add(addButton, c);

        add(addPanel, BorderLayout.NORTH);

        
        JPanel centerPanel = new JPanel(new BorderLayout(4, 4));
        centerPanel.setBorder(BorderFactory.createTitledBorder("Library Items"));

       
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        searchPanel.add(new JLabel("Search by title:"));
        searchField = new JTextField(24);
        searchPanel.add(searchField);
        JButton clearSearchBtn = new JButton("Clear");
        searchPanel.add(clearSearchBtn);
        centerPanel.add(searchPanel, BorderLayout.NORTH);

        
        String[] columns = { "Type", "Title", "Author", "Year", "Status", "Max Loan" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };

        itemsTable = new JTable(tableModel);
        itemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemsTable.setRowHeight(24);
        itemsTable.setShowGrid(true);
        itemsTable.getTableHeader().setReorderingAllowed(false);
        itemsTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

    
        itemsTable.getColumnModel().getColumn(COL_TYPE).setPreferredWidth(80);
        itemsTable.getColumnModel().getColumn(COL_TITLE).setPreferredWidth(260);
        itemsTable.getColumnModel().getColumn(COL_AUTHOR).setPreferredWidth(180);
        itemsTable.getColumnModel().getColumn(COL_YEAR).setPreferredWidth(60);
        itemsTable.getColumnModel().getColumn(COL_STATUS).setPreferredWidth(110);
        itemsTable.getColumnModel().getColumn(COL_MAX_LOAN).setPreferredWidth(90);

        
        itemsTable.getColumnModel().getColumn(COL_STATUS)
                .setCellRenderer(new StatusCellRenderer());

        
        rowSorter = new TableRowSorter<>(tableModel);
        itemsTable.setRowSorter(rowSorter);

        centerPanel.add(new JScrollPane(itemsTable), BorderLayout.CENTER);

        
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        JButton checkOutBtn = new JButton("Check Out Selected");
        JButton returnBtn   = new JButton("Return Selected");
        JButton refreshBtn  = new JButton("Refresh");
        JButton exitBtn     = new JButton("Exit");
        checkOutBtn.setMnemonic('C');
        returnBtn.setMnemonic('R');
        actionPanel.add(checkOutBtn);
        actionPanel.add(returnBtn);
        actionPanel.add(refreshBtn);
        actionPanel.add(Box.createHorizontalStrut(20));
        actionPanel.add(exitBtn);
        centerPanel.add(actionPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        
        statusLabel = new JLabel(" Ready.");
        statusLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(2, 6, 2, 6)));
        add(statusLabel, BorderLayout.SOUTH);

        
        addButton.addActionListener(e -> handleAdd());
        checkOutBtn.addActionListener(e -> handleCheckOut());
        returnBtn.addActionListener(e -> handleReturn());
        refreshBtn.addActionListener(e -> { refreshTable(); showStatus("Table refreshed.", false); });
        exitBtn.addActionListener(e -> dispose());
        clearSearchBtn.addActionListener(e -> { searchField.setText(""); applyFilter(); });

        
        yearField.addActionListener(e -> handleAdd());

        
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate (DocumentEvent e) { applyFilter(); }
            public void removeUpdate (DocumentEvent e) { applyFilter(); }
            public void changedUpdate(DocumentEvent e) { applyFilter(); }
        });
    }

    private void handleAdd() {
        String type    = ((String) typeCombo.getSelectedItem()).toLowerCase();
        String title   = titleField.getText().trim();
        String author  = authorField.getText().trim();
        String yearStr = yearField.getText().trim();

        if (title.isEmpty()) {
            showStatus("Please enter a title.", true);
            titleField.requestFocusInWindow();
            return;
        }
        if (author.isEmpty()) {
            showStatus("Please enter an author.", true);
            authorField.requestFocusInWindow();
            return;
        }

        int year;
        try {
            year = Integer.parseInt(yearStr);
        } catch (NumberFormatException ex) {
            showStatus("Invalid year. Skipping...", true);
            yearField.requestFocusInWindow();
            return;
        }

        LibraryItem item;
        switch (type) {
            case "book":     item = new Book(title, author, year);     break;
            case "magazine": item = new Magazine(title, author, year); break;
            case "dvd":      item = new DVD(title, author, year);      break;
            default:
                
                showStatus("Unknown item type. Skipping...", true);
                return;
        }

        library.addItem(item);   
        refreshTable();

        // Reset the form for the next entry
        titleField.setText("");
        authorField.setText("");
        yearField.setText("");
        titleField.requestFocusInWindow();

        showStatus("Item added: " + item.getTitle(), false);
    }
    private void handleCheckOut() {
        LibraryItem item = getSelectedItem();
        if (item == null) {
            showStatus("Please select an item from the table first.", true);
            return;
        }
        if (item.checkOut()) {
            refreshTable();
            showStatus("Checked out: " + item.getTitle(), false);
        } else {
            showStatus("Item is already checked out: " + item.getTitle(), true);
        }
    }

    private void handleReturn() {
        LibraryItem item = getSelectedItem();
        if (item == null) {
            showStatus("Please select an item from the table first.", true);
            return;
        }
        if (item.returnItem()) {
            refreshTable();
            showStatus("Returned: " + item.getTitle(), false);
        } else {
            showStatus("Item was not checked out: " + item.getTitle(), true);
        }
    }

    private LibraryItem getSelectedItem() {
        int viewRow = itemsTable.getSelectedRow();
        if (viewRow < 0) return null;
        int modelRow = itemsTable.convertRowIndexToModel(viewRow);
        List<LibraryItem> items = library.getItems();
        if (modelRow < 0 || modelRow >= items.size()) return null;
        return items.get(modelRow);
    }

    private void applyFilter() {
        String q = searchField.getText().trim();
        if (q.isEmpty()) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(
                    RowFilter.regexFilter("(?i)" + Pattern.quote(q), COL_TITLE));
        }
    }


    private void refreshTable() {
        LibraryItem previouslySelected = getSelectedItem();

        tableModel.setRowCount(0);
        for (LibraryItem item : library.getItems()) {
            tableModel.addRow(new Object[] {
                    item.getClass().getSimpleName(),                       // Type
                    item.getTitle(),                                       // Title
                    item.getAuthor(),                                      // Author
                    item.getYear(),                                        // Year
                    item.isCheckedOut() ? "Checked Out" : "Available",     // Status
                    item.getMaxLoanDays() + " days"                        // Max Loan (polymorphic)
            });
        }

        if (previouslySelected != null) {
            int modelIdx = library.getItems().indexOf(previouslySelected);
            if (modelIdx >= 0) {
                try {
                    int viewIdx = itemsTable.convertRowIndexToView(modelIdx);
                    if (viewIdx >= 0) {
                        itemsTable.setRowSelectionInterval(viewIdx, viewIdx);
                    }
                } catch (IndexOutOfBoundsException ignored) {
                    
                }
            }
        }
    }

    private void showStatus(String msg, boolean isError) {
        statusLabel.setText(" " + msg);
        statusLabel.setForeground(isError ? new Color(170, 0, 0) : new Color(0, 110, 0));
    }

    private static class StatusCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            Component cmp = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
            if (!isSelected) {
                String text = String.valueOf(value);
                if ("Available".equals(text)) {
                    cmp.setForeground(new Color(0, 120, 0));
                } else if ("Checked Out".equals(text)) {
                    cmp.setForeground(new Color(170, 0, 0));
                } else {
                    cmp.setForeground(Color.BLACK);
                }
            }
            return cmp;
        }
    }


    public static void main(String[] args) {
        // Native look-and-feel — falls back silently if unavailable
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { /* default L&F is fine */ }

        SwingUtilities.invokeLater(() -> {
            String name = JOptionPane.showInputDialog(
                    null,
                    "Enter library name:",
                    "Library Setup",
                    JOptionPane.QUESTION_MESSAGE);

            if (name == null) {              // user pressed Cancel
                System.exit(0);
            }
            name = name.trim();
            if (name.isEmpty()) {
                name = "Central Library";    // sensible default
            }

            new LibraryGUI(name).setVisible(true);
        });
    }
}

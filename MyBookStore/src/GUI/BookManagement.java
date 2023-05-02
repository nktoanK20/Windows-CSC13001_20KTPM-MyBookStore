package GUI;

import BUS.*;
import POJO.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.Book;
import java.util.*;

public class BookManagement extends JFrame implements ActionListener {
    JPanel menuPane;
    JButton backButton;
    String username;

    final int SIDEBAR_PANE_WIDTH = 170;
    JPanel sidebarPane;
    JButton selectedButton;
    JButton allBooksButton;
    JButton newBooksButton;
    JButton hotBooksButton;
    JButton outOfStockBooksButton;
    JButton addBookButton;

    JPanel contentPane;
    JLabel contentLabel;
    JPanel filterBookPanel;
    boolean isFiltering = false;
    JLabel inputIdOrNameLabel;
    JTextField inputIdOrNameField;
    JLabel fromPriceLabel;
    JTextField fromPriceField;
    JLabel toPriceLabel;
    JTextField toPriceField;
    JButton filterButton;
    ArrayList<BookPOJO> bookList = null;
    JTable bookTable;
    JScrollPane bookTableScroll;
    AddBookFormPanel addBookFormPanel;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void CloseFrame() {
        this.setVisible(false);
        this.dispose();
    }

    public BookManagement(String username) {
        setUsername(username);
        setTitle("Employee - Book Management");
        setSize(1000, 850);
        setLocationRelativeTo(null);
        setResizable(true);
        setLayout(new BorderLayout(0, 0));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponent();
        this.setVisible(true);
    }

    private void initComponent() {
        menuPane = new JPanel();
        menuPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        menuPane.setBorder(new LineBorder(Color.BLACK, 3));

        backButton = new JButton("Back");
        backButton.setFocusable(false);
        backButton.addActionListener(this);
        menuPane.add(backButton);

        sidebarPane = new JPanel();
        sidebarPane.setLayout(new BoxLayout(sidebarPane, BoxLayout.Y_AXIS));
        sidebarPane.setPreferredSize(new Dimension(SIDEBAR_PANE_WIDTH, 0));
        sidebarPane.setBorder(new LineBorder(Color.BLACK, 3));

        allBooksButton = new JButton("All Books");
        allBooksButton.setMaximumSize(new Dimension(SIDEBAR_PANE_WIDTH, 30));
        allBooksButton.setFocusable(false);
        allBooksButton.addActionListener(this);

        newBooksButton = new JButton("New Books");
        newBooksButton.setMaximumSize(new Dimension(SIDEBAR_PANE_WIDTH, 30));
        newBooksButton.setFocusable(false);
        newBooksButton.addActionListener(this);

        hotBooksButton = new JButton("Hot Books");
        hotBooksButton.setMaximumSize(new Dimension(SIDEBAR_PANE_WIDTH, 30));
        hotBooksButton.setFocusable(false);
        hotBooksButton.addActionListener(this);

        outOfStockBooksButton = new JButton("Out Of Stock Books");
        outOfStockBooksButton.setMaximumSize(new Dimension(SIDEBAR_PANE_WIDTH, 30));
        outOfStockBooksButton.setFocusable(false);
        outOfStockBooksButton.addActionListener(this);

        addBookButton = new JButton("Add New Book");
        addBookButton.setMaximumSize(new Dimension(SIDEBAR_PANE_WIDTH, 30));
        addBookButton.setFocusable(false);
        addBookButton.addActionListener(this);

        sidebarPane.add(allBooksButton);
        sidebarPane.add(newBooksButton);
        sidebarPane.add(hotBooksButton);
        sidebarPane.add(outOfStockBooksButton);
        sidebarPane.add(addBookButton);

        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        contentLabel = new JLabel();
        contentLabel.setText("Please choose an action in the sidebar...");
        contentLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);

        filterBookPanel = new JPanel();
        filterBookPanel.setPreferredSize(new Dimension(500, 150));
        filterBookPanel.setMaximumSize(new Dimension(1000, 200));
        filterBookPanel.setLayout(new GridBagLayout());

        inputIdOrNameLabel = new JLabel("Input Name Or Id:");
        inputIdOrNameField = new JTextField();

        fromPriceLabel = new JLabel("From Price:");
        fromPriceField = new JTextField();
        fromPriceField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                char key = ke.getKeyChar();
                fromPriceField.setEditable(key >= '0' && key <= '9' || key == '\b');
            }
        });

        toPriceLabel = new JLabel("To Price:");
        toPriceField = new JTextField();
        toPriceField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                char key = ke.getKeyChar();
                toPriceField.setEditable(key >= '0' && key <= '9' || key == '\b');
            }
        });

        filterButton = new JButton("Filter");
        filterButton.addActionListener(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 20, 5, 20);
        gbc.fill = GridBagConstraints.BOTH;

        // add labels
        int i = 0;
        gbc.gridx = 0;
        gbc.gridy = i++;
        filterBookPanel.add(inputIdOrNameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = i++;
        filterBookPanel.add(fromPriceLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = i++;
        filterBookPanel.add(toPriceLabel, gbc);

        // add fields
        gbc.weightx = 1;
        i = 0;
        gbc.gridx = 1;
        gbc.gridy = i++;
        filterBookPanel.add(inputIdOrNameField, gbc);

        gbc.gridx = 1;
        gbc.gridy = i++;
        filterBookPanel.add(fromPriceField, gbc);

        gbc.gridx = 1;
        gbc.gridy = i++;
        filterBookPanel.add(toPriceField, gbc);

        // add buttons
        gbc.weightx = 0;
        i = 2;
        gbc.gridx = 2;
        gbc.gridy = i;
        filterBookPanel.add(filterButton, gbc);

        bookTableScroll = new JScrollPane();

        contentPane.add(contentLabel);

        add(menuPane, BorderLayout.NORTH);
        add(sidebarPane, BorderLayout.WEST);
        add(contentPane, BorderLayout.CENTER);
    }

    // class methods

    // New books are books that have release date within 1 month back
    // Hot books are books that have total purchase >= 100
    public JTable getBookTable() {
        if (selectedButton == allBooksButton) {
            bookList = BookBUS.getAll();
        } else if (selectedButton == newBooksButton) {
            bookList = BookBUS.getNewBooks();
        } else if (selectedButton == hotBooksButton) {
            bookList = BookBUS.getHotBooks();
        } else if (selectedButton == outOfStockBooksButton) {
            bookList = BookBUS.getOutOfStockBooks();
        }

        ArrayList<BookPOJO> filterBookList = null;
        if (isFiltering) {
            if (bookList != null) {
                filterBookList = new ArrayList<>();
                for (BookPOJO book : bookList) {
                    filterBookList.add(book.clone());
                }

                String input = inputIdOrNameField.getText().toLowerCase();
                String fromPrice = fromPriceField.getText();
                String toPrice = toPriceField.getText();
                for (int i = 0; i < filterBookList.size(); i++) {
                    BookPOJO book = filterBookList.get(i);
                    if (!book.getName().toLowerCase().matches("(.*)" + input + "(.*)") && !book.getId().toLowerCase().matches("(.*)" + input + "(.*)")) {
                        filterBookList.remove(book);
                        i--;
                    } else if (!fromPrice.isEmpty() && book.getPrice() < Integer.parseInt(fromPrice)) {
                        filterBookList.remove(book);
                        i--;
                    } else if (!toPrice.isEmpty() && book.getPrice() > Integer.parseInt(toPrice)) {
                        filterBookList.remove(book);
                        i--;
                    }
                }
            }
        }


        JTable table = new JTable();
        String[] col = {"ID", "NAME", "AUTHOR", "CATEGORY", "ID PUBLISHER", "PRICE", "STOCK", "TOTAL PURCHASE",
                "RELEASE DATE", "STATUS", "ACTION", "EDIT"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);

        if (isFiltering) {
            if (filterBookList != null) {
                for (BookPOJO book : filterBookList) {
                    String id = book.getId();
                    String name = book.getName();
                    String author = "Show Author";
                    String category = "Show Category";
                    String idPublisher = book.getIdPublisher();
                    Integer price = book.getPrice();
                    Integer stock = book.getStock();
                    Integer totalPurchase = book.getTotalPurchase();
                    Date releaseDate = book.getReleaseDate();
                    String enabled = book.isEnabled() ? "Enabled" : "Disabled";
                    String action = book.isEnabled() ? "Disable" : "Enable";

                    Object[] data = {id, name, author, category, idPublisher, price, stock, totalPurchase, releaseDate,
                            enabled, action, "Edit"};
                    tableModel.addRow(data);
                }
            }
        } else {
            if (bookList != null) {
                for (BookPOJO book : bookList) {
                    String id = book.getId();
                    String name = book.getName();
                    String author = "Show Author";
                    String category = "Show Category";
                    String idPublisher = book.getIdPublisher();
                    Integer price = book.getPrice();
                    Integer stock = book.getStock();
                    Integer totalPurchase = book.getTotalPurchase();
                    Date releaseDate = book.getReleaseDate();
                    String enabled = book.isEnabled() ? "Enabled" : "Disabled";
                    String action = book.isEnabled() ? "Disable" : "Enable";

                    Object[] data = {id, name, author, category, idPublisher, price, stock, totalPurchase, releaseDate,
                            enabled, action, "Edit"};
                    tableModel.addRow(data);
                }
            }
        }
        table.setModel(tableModel);
        table.setAutoCreateRowSorter(true);
        table.getColumn("AUTHOR").setCellRenderer(new ButtonRenderer());
        table.getColumn("AUTHOR").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.getColumn("CATEGORY").setCellRenderer(new ButtonRenderer());
        table.getColumn("CATEGORY").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.getColumn("ACTION").setCellRenderer(new ButtonRenderer());
        table.getColumn("ACTION").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.getColumn("EDIT").setCellRenderer(new ButtonRenderer());
        table.getColumn("EDIT").setCellEditor(new ButtonEditor(new JCheckBox()));
        return table;
    }

    public void refreshBookTable() {
        contentPane.add(filterBookPanel);

        contentLabel.setText(selectedButton.getText() + " List");
        contentPane.add(contentLabel);

        bookTable = getBookTable();
        bookTableScroll.setViewportView(bookTable);
        contentPane.add(bookTableScroll);
        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        contentPane.removeAll();
        if ((JButton) e.getSource() == filterButton) {
            isFiltering = true;
            refreshBookTable();
            revalidate();
            repaint();
            return;
        }

        selectedButton = (JButton) e.getSource();
        isFiltering = false;
        if (selectedButton == allBooksButton || selectedButton == newBooksButton
                || selectedButton == hotBooksButton || selectedButton == outOfStockBooksButton) {
            refreshBookTable();
        } else if (selectedButton == addBookButton) {
            addBookFormPanel = new AddBookFormPanel();
            contentLabel.setText("Add A New Book");
            contentPane.add(contentLabel);
            contentPane.add(addBookFormPanel);
        } else if (selectedButton == backButton) {
            UserControl userControl = new UserControl(username);
            userControl.setVisible(true);
            this.setVisible(false);
        }

        revalidate();
        repaint();
    }

    // local classes
    static class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;

        private String label;

        private boolean isPushed;
        int row;
        int column;
        String type;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            type = label;
            isPushed = true;
            this.row = row;
            this.column = column;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                String bookId = bookTable.getValueAt(row, 0).toString();
                if (Objects.equals(type, "Show Author")) {
                    new ShowAuthorListDialog(BookManagement.this, "Author List", true, bookId);
                } else if (Objects.equals(type, "Show Category")) {
                    new ShowCategoryListDialog(BookManagement.this, "Category List", true, bookId);
                } else if (Objects.equals(type, "Enable")) {
                    BookBUS.enable(bookId);
                    refreshBookTable();
                } else if (Objects.equals(type, "Disable")) {
                    BookBUS.disable(bookId);
                    refreshBookTable();
                } else if (Objects.equals(type, "Edit")) {
                    new EditBookDialog(BookManagement.this, "Edit Book", true, bookId);
                    refreshBookTable();
                } else {
                    System.out.println("Unknown action: " + bookTable.getValueAt(row, 0) + " - " + type);
                }
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

    public class EditBookDialog extends JDialog implements ActionListener {
        BookPOJO book;

        JLabel headLabel;

        JLabel idLabel;
        JTextField idField;

        JLabel nameLabel;
        JTextField nameField;

        ArrayList<AuthorPOJO> allAuthors;
        JLabel listAuthorLabel;
        JList<CheckboxListItem> listAuthorField;
        JScrollPane listAuthorScroll;

        ArrayList<CategoryPOJO> allCategories;
        JLabel listCategoryLabel;
        JList<CheckboxListItem> listCategoryField;
        JScrollPane listCategoryScroll;

        JLabel publisherIdLabel;
        JComboBox<String> publisherIdField;

        JLabel priceLabel;
        JTextField priceField;

        JLabel stockLabel;
        JTextField stockField;

        JLabel totalPurchaseLabel;
        JTextField totalPurchaseField;

        JLabel releaseDateLabel;
        UtilDateModel releaseDateModel;
        JDatePanelImpl releaseDatePanel;
        JDatePickerImpl releaseDateField;

        JLabel statusLabel;
        JComboBox<String> statusField;

        JButton cancelButton;
        JButton saveButton;

        GridBagConstraints gbc;

        EditBookDialog(JFrame parent, String title, boolean modal, String bookId) {
            super(parent, title, modal);
            this.book = BookBUS.getOne(bookId);

            this.setSize(900, 850);
            this.setLocationRelativeTo(null);
            this.setResizable(true);
            setLayout(new GridBagLayout());
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.initComponent();
            this.setVisible(true);
        }

        public void initComponent() {
            headLabel = new JLabel("You are editing book ID: " + book.getId());

            idLabel = new JLabel("ID:");
            idField = new JTextField(book.getId());
            idField.setEditable(false);

            nameLabel = new JLabel("Name:");
            nameField = new JTextField(book.getName());

            allAuthors = new AuthorBUS().getAllAuthor();
            listAuthorLabel = new JLabel("Author List: ");
            ArrayList<CheckboxListItem> listAuthorItem = new ArrayList<>();
            for (AuthorPOJO author : allAuthors) {
                if (!author.isDisable()) {
                    String authorLabel = author.getId() + " - " + author.getName();
                    listAuthorItem.add(new CheckboxListItem(authorLabel));
                }
            }
            listAuthorField = new JList<>(listAuthorItem.toArray(new CheckboxListItem[0]));
            listAuthorField.setCellRenderer(new CheckboxListRenderer());
            listAuthorField.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            ArrayList<AuthorPOJO> listAuthor = AuthorBUS.getAuthorListOfBook(book.getId());
            ListModel<CheckboxListItem> listAuthorModel = listAuthorField.getModel();

            for (int i = 0; i < listAuthorModel.getSize(); i++) {
                CheckboxListItem item = listAuthorModel.getElementAt(i);
                String authorId = item.toString().split("-")[0].trim();
                for (AuthorPOJO author : listAuthor) {
                    if (authorId.equals(author.getId())) {
                        item.setSelected(true);
                    }
                }
            }
            listAuthorField.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent event) {
                    // Get index of item clicked
                    int index = listAuthorField.locationToIndex(event.getPoint());
                    CheckboxListItem item = listAuthorField.getModel().getElementAt(index);

                    // Toggle selected state
                    item.setSelected(!item.isSelected());

                    // Repaint cell
                    listAuthorField.repaint(listAuthorField.getCellBounds(index, index));
                }
            });

            listAuthorScroll = new JScrollPane();
            listAuthorScroll.setViewportView(listAuthorField);

            allCategories = CategoryBUS.getAll();
            listCategoryLabel = new JLabel("Category List: ");
            ArrayList<CheckboxListItem> listCategoryItem = new ArrayList<>();
            for (CategoryPOJO category : allCategories) {
                if (category.isIsEnabled()) {
                    String CategoryLabel = category.getId() + " - " + category.getName();
                    listCategoryItem.add(new CheckboxListItem(CategoryLabel));
                }
            }
            listCategoryField = new JList<>(listCategoryItem.toArray(new CheckboxListItem[0]));
            listCategoryField.setCellRenderer(new CheckboxListRenderer());
            listCategoryField.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            ArrayList<CategoryPOJO> listCategory = CategoryBUS.getCategoryListOfBook(book.getId());
            ListModel<CheckboxListItem> listCategoryModel = listCategoryField.getModel();

            for (int i = 0; i < listCategoryModel.getSize(); i++) {
                CheckboxListItem item = listCategoryModel.getElementAt(i);
                String categoryId = item.toString().split("-")[0].trim();
                for (CategoryPOJO category : listCategory) {
                    if (categoryId.equals(category.getId())) {
                        item.setSelected(true);
                    }
                }
            }
            listCategoryField.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent event) {
                    // Get index of item clicked
                    int index = listCategoryField.locationToIndex(event.getPoint());
                    CheckboxListItem item = listCategoryField.getModel().getElementAt(index);

                    // Toggle selected state
                    item.setSelected(!item.isSelected());

                    // Repaint cell
                    listCategoryField.repaint(listCategoryField.getCellBounds(index, index));
                }
            });

            listCategoryScroll = new JScrollPane();
            listCategoryScroll.setViewportView(listCategoryField);

            publisherIdLabel = new JLabel("Publisher:");
            ArrayList<PublisherPOJO> publishers = PublisherBUS.getAll();
            ArrayList<String> publisherModel = new ArrayList<>();
            String selectedItem = "";
            for (PublisherPOJO publisher : publishers) {
                if (publisher.getId().equals(book.getIdPublisher())) {
                    selectedItem = publisher.getId() + " - " + publisher.getName();
                }
                publisherModel.add(publisher.getId() + " - " + publisher.getName());
            }
            publisherIdField = new JComboBox<>(publisherModel.toArray(new String[0]));
            publisherIdField.setSelectedItem(selectedItem);

            priceLabel = new JLabel("Price:");
            priceField = new JTextField(book.getPrice().toString());
            priceField.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent ke) {
                    char key = ke.getKeyChar();
                    priceField.setEditable(key >= '0' && key <= '9' || key == '\b');
                }
            });

            stockLabel = new JLabel("Left In Stock:");
            stockField = new JTextField(book.getStock().toString());
            stockField.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent ke) {
                    char key = ke.getKeyChar();
                    stockField.setEditable(key >= '0' && key <= '9' || key == '\b');
                }
            });

            totalPurchaseLabel = new JLabel("Total Purchased Number:");
            totalPurchaseField = new JTextField(book.getTotalPurchase().toString());
            totalPurchaseField.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent ke) {
                    char key = ke.getKeyChar();
                    totalPurchaseField.setEditable(key >= '0' && key <= '9' || key == '\b');
                }
            });

            Properties p = new Properties();
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.year", "Year");
            releaseDateLabel = new JLabel("Release Date: ");
            releaseDateModel = new UtilDateModel();
            if (book.getReleaseDate() != null) {
                Calendar releaseDate = Calendar.getInstance();
                releaseDate.setTime(book.getReleaseDate());
                releaseDateModel.setDate(releaseDate.get(Calendar.YEAR), releaseDate.get(Calendar.MONTH),
                        releaseDate.get(Calendar.DAY_OF_MONTH));
                releaseDateModel.setSelected(true);
            }
            releaseDatePanel = new JDatePanelImpl(releaseDateModel, p);
            releaseDateField = new JDatePickerImpl(releaseDatePanel, new PromotionManagement.DateLabelFormatter());

            statusLabel = new JLabel("Status:");
            String[] statuses = {"Enabled", "Disabled"};
            statusField = new JComboBox<>(statuses);
            String status = book.isEnabled() ? "Enabled" : "Disabled";
            statusField.setSelectedItem(status);

            cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(this);

            saveButton = new JButton("Save");
            saveButton.addActionListener(this);

            gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 20, 5, 20);

            gbc.fill = GridBagConstraints.BOTH;

            // add labels
            int i;
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(headLabel, gbc);
            i = 2;

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(idLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(nameLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(listAuthorLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(listCategoryLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(publisherIdLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(priceLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(stockLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(totalPurchaseLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(releaseDateLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i;
            add(statusLabel, gbc);

            // add fields
            gbc.weightx = 2;
            i = 2;
            gbc.gridx = 1;
            gbc.gridy = i++;
            add(idField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(nameField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(listAuthorScroll, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(listCategoryScroll, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(publisherIdField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(priceField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(stockField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(totalPurchaseField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(releaseDateField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(statusField, gbc);

            // add buttons
            gbc.weightx = 1;
            gbc.gridx = 0;
            gbc.gridy = i;
            add(cancelButton, gbc);

            gbc.gridx = 1;
            gbc.gridy = i;
            add(saveButton, gbc);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == cancelButton) {
                this.dispose();
            } else if (e.getSource() == saveButton) {
                try {
                    BookPOJO modifiedBook = new BookPOJO(
                            idField.getText(),
                            nameField.getText(),
                            Objects.requireNonNull(publisherIdField.getSelectedItem()).toString().split("-")[0].trim(),
                            Integer.parseInt(priceField.getText()),
                            Integer.parseInt(stockField.getText()),
                            Integer.parseInt(totalPurchaseField.getText()),
                            (Date) releaseDateField.getModel().getValue(),
                            statusField.getSelectedItem() == "Enabled");

                    Boolean result = BookBUS.updateOne(book.getId(), modifiedBook);
                    if (!result) {
                        JOptionPane.showMessageDialog(this, "Error when updating" +
                                " book's information!", "Error", JOptionPane.WARNING_MESSAGE);
                    }

                    ArrayList<String> listInsertAuthorId = new ArrayList<>();
                    ArrayList<String> listDeleteAuthorId = new ArrayList<>();
                    ListModel<CheckboxListItem> listAuthorModel = listAuthorField.getModel();
                    ArrayList<AuthorPOJO> listAuthor = AuthorBUS.getAuthorListOfBook(book.getId());
                    for (int i = 0; i < listAuthorModel.getSize(); i++) {
                        CheckboxListItem item = listAuthorModel.getElementAt(i);
                        String authorId = item.toString().split("-")[0].trim();
                        if (item.isSelected()) {
                            boolean isInInsertList = true;
                            for (AuthorPOJO author : listAuthor) {
                                if (authorId.equals(author.getId())) {
                                    isInInsertList = false;
                                    break;
                                }
                            }
                            if (isInInsertList) {
                                listInsertAuthorId.add(authorId);
                            }
                        } else {
                            boolean isInDeleteList = false;
                            for (AuthorPOJO author : listAuthor) {
                                if (authorId.equals(author.getId())) {
                                    isInDeleteList = true;
                                    break;
                                }
                            }
                            if (isInDeleteList) {
                                listDeleteAuthorId.add(authorId);
                            }
                        }
                    }
                    result = BookBUS.insertBookAuthors(book.getId(), listInsertAuthorId);
                    if (!result) {
                        JOptionPane.showMessageDialog(this, "Error when inserting chosen" +
                                " authors into this book!", "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    result = BookBUS.deleteBookAuthors(book.getId(), listDeleteAuthorId);
                    if (!result) {
                        JOptionPane.showMessageDialog(this, "Error when deleting not chosen" +
                                " authors from this book!", "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    ArrayList<String> listInsertCategoryId = new ArrayList<>();
                    ArrayList<String> listDeleteCategoryId = new ArrayList<>();
                    ListModel<CheckboxListItem> listCategoryModel = listCategoryField.getModel();
                    ArrayList<CategoryPOJO> listCategory = CategoryBUS.getCategoryListOfBook(book.getId());
                    for (int i = 0; i < listCategoryModel.getSize(); i++) {
                        CheckboxListItem item = listCategoryModel.getElementAt(i);
                        String categoryId = item.toString().split("-")[0].trim();
                        if (item.isSelected()) {
                            boolean isInInsertList = true;
                            for (CategoryPOJO category : listCategory) {
                                if (categoryId.equals(category.getId())) {
                                    isInInsertList = false;
                                    break;
                                }
                            }
                            if (isInInsertList) {
                                listInsertCategoryId.add(categoryId);
                            }
                        } else {
                            boolean isInDeleteList = false;
                            for (CategoryPOJO category : listCategory) {
                                if (categoryId.equals(category.getId())) {
                                    isInDeleteList = true;
                                    break;
                                }
                            }
                            if (isInDeleteList) {
                                listDeleteCategoryId.add(categoryId);
                            }
                        }
                    }
                    result = BookBUS.insertBookCategories(book.getId(), listInsertCategoryId);
                    if (!result) {
                        JOptionPane.showMessageDialog(this, "Error when inserting chosen" +
                                " categories into this book!", "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    result = BookBUS.deleteBookCategories(book.getId(), listDeleteCategoryId);
                    if (!result) {
                        JOptionPane.showMessageDialog(this, "Error when deleting not chosen" +
                                " categories from this book!", "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    JOptionPane.showMessageDialog(this, "Update success!", "Success", JOptionPane.PLAIN_MESSAGE);
                    this.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Information, please check again!");
                    System.out.println(Arrays.toString(ex.getStackTrace()));
                }
            }
        }
    }

    class AddBookFormPanel extends JPanel implements ActionListener {
        JLabel headLabel;

        JLabel idLabel;
        JTextField idField;

        JLabel nameLabel;
        JTextField nameField;

        ArrayList<AuthorPOJO> allAuthors;
        JLabel listAuthorLabel;
        JList<CheckboxListItem> listAuthorField;
        JScrollPane listAuthorScroll;

        ArrayList<CategoryPOJO> allCategories;
        JLabel listCategoryLabel;
        JList<CheckboxListItem> listCategoryField;
        JScrollPane listCategoryScroll;

        JLabel publisherIdLabel;
        JComboBox<String> publisherIdField;

        JLabel priceLabel;
        JTextField priceField;

        JLabel stockLabel;
        JTextField stockField;

        JLabel totalPurchaseLabel;
        JTextField totalPurchaseField;

        JLabel releaseDateLabel;
        UtilDateModel releaseDateModel;
        JDatePanelImpl releaseDatePanel;
        JDatePickerImpl releaseDateField;

        JLabel statusLabel;
        JComboBox<String> statusField;

        JButton clearButton;
        JButton addButton;

        GridBagConstraints gbc;

        AddBookFormPanel() {
            setLayout(new GridBagLayout());
            this.initComponent();
        }

        public void initComponent() {
            headLabel = new JLabel("Book's information");

            idLabel = new JLabel("ID:");
            idField = new JTextField();

            nameLabel = new JLabel("Name:");
            nameField = new JTextField();

            allAuthors = new AuthorBUS().getAllAuthor();
            listAuthorLabel = new JLabel("Author List: ");
            ArrayList<CheckboxListItem> listAuthorItem = new ArrayList<>();
            for (AuthorPOJO author : allAuthors) {
                if (!author.isDisable()) {
                    String authorLabel = author.getId() + " - " + author.getName();
                    listAuthorItem.add(new CheckboxListItem(authorLabel));
                }
            }
            listAuthorField = new JList<>(listAuthorItem.toArray(new CheckboxListItem[0]));
            listAuthorField.setCellRenderer(new CheckboxListRenderer());
            listAuthorField.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listAuthorField.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent event) {
                    // Get index of item clicked
                    int index = listAuthorField.locationToIndex(event.getPoint());
                    CheckboxListItem item = listAuthorField.getModel().getElementAt(index);

                    // Toggle selected state
                    item.setSelected(!item.isSelected());

                    // Repaint cell
                    listAuthorField.repaint(listAuthorField.getCellBounds(index, index));
                }
            });

            listAuthorScroll = new JScrollPane();
            listAuthorScroll.setViewportView(listAuthorField);

            allCategories = CategoryBUS.getAll();
            listCategoryLabel = new JLabel("Category List: ");
            ArrayList<CheckboxListItem> listCategoryItem = new ArrayList<>();
            for (CategoryPOJO category : allCategories) {
                if (category.isIsEnabled()) {
                    String CategoryLabel = category.getId() + " - " + category.getName();
                    listCategoryItem.add(new CheckboxListItem(CategoryLabel));
                }
            }
            listCategoryField = new JList<>(listCategoryItem.toArray(new CheckboxListItem[0]));
            listCategoryField.setCellRenderer(new CheckboxListRenderer());
            listCategoryField.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listCategoryField.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent event) {
                    // Get index of item clicked
                    int index = listCategoryField.locationToIndex(event.getPoint());
                    CheckboxListItem item = listCategoryField.getModel().getElementAt(index);

                    // Toggle selected state
                    item.setSelected(!item.isSelected());

                    // Repaint cell
                    listCategoryField.repaint(listCategoryField.getCellBounds(index, index));
                }
            });

            listCategoryScroll = new JScrollPane();
            listCategoryScroll.setViewportView(listCategoryField);

            publisherIdLabel = new JLabel("Publisher:");
            ArrayList<PublisherPOJO> publishers = PublisherBUS.getAll();
            ArrayList<String> publisherModel = new ArrayList<>();
            for (PublisherPOJO publisher : publishers) {
                publisherModel.add(publisher.getId() + " - " + publisher.getName());
            }
            publisherIdField = new JComboBox<>(publisherModel.toArray(new String[0]));

            priceLabel = new JLabel("Price:");
            priceField = new JTextField();
            priceField.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent ke) {
                    char key = ke.getKeyChar();
                    priceField.setEditable(key >= '0' && key <= '9' || key == '\b');
                }
            });

            stockLabel = new JLabel("Left In Stock:");
            stockField = new JTextField();
            stockField.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent ke) {
                    char key = ke.getKeyChar();
                    stockField.setEditable(key >= '0' && key <= '9' || key == '\b');
                }
            });

            totalPurchaseLabel = new JLabel("Total Purchased Number:");
            totalPurchaseField = new JTextField();
            totalPurchaseField.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent ke) {
                    char key = ke.getKeyChar();
                    totalPurchaseField.setEditable(key >= '0' && key <= '9' || key == '\b');
                }
            });

            statusLabel = new JLabel("Status:");
            String[] statuses = {"Enabled", "Disabled"};
            statusField = new JComboBox<>(statuses);

            Properties p = new Properties();
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.year", "Year");
            releaseDateLabel = new JLabel("Release Date: ");
            releaseDateModel = new UtilDateModel();
            releaseDatePanel = new JDatePanelImpl(releaseDateModel, p);
            releaseDateField = new JDatePickerImpl(releaseDatePanel, new PromotionManagement.DateLabelFormatter());

            clearButton = new JButton("Clear");
            clearButton.addActionListener(this);

            addButton = new JButton("Add");
            addButton.addActionListener(this);

            gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 20, 5, 20);

            gbc.fill = GridBagConstraints.BOTH;

            // add labels
            int i;
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(headLabel, gbc);
            i = 2;

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(idLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(nameLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(listAuthorLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(listCategoryLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(publisherIdLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(priceLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(stockLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(totalPurchaseLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(releaseDateLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i;
            add(statusLabel, gbc);

            // add fields
            gbc.weightx = 2;
            i = 2;
            gbc.gridx = 1;
            gbc.gridy = i++;
            add(idField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(nameField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(listAuthorScroll, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(listCategoryScroll, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(publisherIdField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(priceField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(stockField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(totalPurchaseField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(releaseDateField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(statusField, gbc);

            // add buttons
            gbc.weightx = 1;
            gbc.gridx = 0;
            gbc.gridy = i;
            add(clearButton, gbc);

            gbc.gridx = 1;
            gbc.gridy = i;
            add(addButton, gbc);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == clearButton) {
                idField.setText("");
                nameField.setText("");
                ListModel<CheckboxListItem> listAuthorModel = listAuthorField.getModel();
                for (int i = 0; i < listAuthorModel.getSize(); i++) {
                    listAuthorModel.getElementAt(i).setSelected(false);
                }
                ListModel<CheckboxListItem> listCategoryModel = listCategoryField.getModel();
                for (int i = 0; i < listCategoryModel.getSize(); i++) {
                    listCategoryModel.getElementAt(i).setSelected(false);
                }
                publisherIdField.setSelectedIndex(0);
                priceField.setText("");
                stockField.setText("");
                totalPurchaseField.setText("");
                statusField.setSelectedIndex(0);
                revalidate();
                repaint();
            } else if (e.getSource() == addButton) {
                try {
                    BookPOJO book = new BookPOJO(
                            idField.getText(),
                            nameField.getText(),
                            Objects.requireNonNull(publisherIdField.getSelectedItem()).toString().split("-")[0].trim(),
                            Integer.parseInt(priceField.getText()),
                            Integer.parseInt(stockField.getText()),
                            Integer.parseInt(totalPurchaseField.getText()),
                            (Date) releaseDateField.getModel().getValue(),
                            statusField.getSelectedItem() == "Enabled");
                    Boolean result = BookBUS.insertOne(book);
                    if (!result) {
                        JOptionPane.showMessageDialog(this, "Error when adding new book!", "Error",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    ArrayList<String> listInsertAuthorId = new ArrayList<>();
                    ListModel<CheckboxListItem> listAuthorModel = listAuthorField.getModel();
                    for (int i = 0; i < listAuthorModel.getSize(); i++) {
                        CheckboxListItem item = listAuthorModel.getElementAt(i);
                        String authorId = item.toString().split("-")[0].trim();
                        if (item.isSelected()) {
                            listInsertAuthorId.add(authorId);
                        }
                    }
                    result = BookBUS.insertBookAuthors(book.getId(), listInsertAuthorId);
                    if (!result) {
                        JOptionPane.showMessageDialog(this, "Error when inserting chosen" +
                                " authors into this book!", "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    ArrayList<String> listInsertCategoryId = new ArrayList<>();
                    ListModel<CheckboxListItem> listCategoryModel = listCategoryField.getModel();
                    for (int i = 0; i < listCategoryModel.getSize(); i++) {
                        CheckboxListItem item = listCategoryModel.getElementAt(i);
                        String categoryId = item.toString().split("-")[0].trim();
                        if (item.isSelected()) {
                            listInsertCategoryId.add(categoryId);
                        }
                    }
                    result = BookBUS.insertBookCategories(book.getId(), listInsertCategoryId);
                    if (!result) {
                        JOptionPane.showMessageDialog(this, "Error when inserting chosen" +
                                " categories into this book!", "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    JOptionPane.showMessageDialog(this, "Add new book success!", "Success", JOptionPane.PLAIN_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Information, please check again!");
                    System.out.println(Arrays.toString(ex.getStackTrace()));
                }
            }
        }

    }

    // Represents items in the list that can be selected
    class CheckboxListItem {
        private String label;
        private boolean isSelected = false;

        public CheckboxListItem(String label) {
            this.label = label;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean isSelected) {
            this.isSelected = isSelected;
        }

        public String toString() {
            return label;
        }
    }

    // Handles rendering cells in the list using a checkbox
    class CheckboxListRenderer extends JCheckBox implements
            ListCellRenderer<CheckboxListItem> {
        @Override
        public Component getListCellRendererComponent(
                JList<? extends CheckboxListItem> list, CheckboxListItem value,
                int index, boolean isSelected, boolean cellHasFocus) {
            setEnabled(list.isEnabled());
            setSelected(value.isSelected());
            setFont(list.getFont());
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            setText(value.toString());
            return this;
        }
    }

    static class ShowAuthorListDialog extends JDialog {
        BookPOJO book;

        JLabel headLabel;
        JTable authorTable;
        JScrollPane authorTableScroll;

        ShowAuthorListDialog(JFrame parent, String title, boolean modal, String bookId) {
            super(parent, title, modal);
            this.book = BookBUS.getOne(bookId);

            this.setSize(700, 500);
            this.setLocationRelativeTo(null);
            this.setResizable(true);
            setLayout(new BorderLayout());
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.initComponent();
            this.setVisible(true);
        }

        public void initComponent() {
            headLabel = new JLabel("Author list of book: " + book.getId() + " - " + book.getName());

            String[] col = {"ID", "NAME", "ADDRESS", "PHONE NUMBER", "STATUS"};
            authorTable = new JTable();
            DefaultTableModel tableModel = new DefaultTableModel(col, 0);

            ArrayList<AuthorPOJO> authorList = AuthorBUS.getAuthorListOfBook(book.getId());
            for (AuthorPOJO author : authorList) {
                String id = author.getId();
                String name = author.getName();
                String address = author.getAddress();
                String phone = author.getPhone();
                String enabled = author.isDisable() ? "Disabled" : "Enabled";

                Object[] data = {id, name, address, phone, enabled};
                tableModel.addRow(data);
            }
            authorTable.setModel(tableModel);
            authorTable.setAutoCreateRowSorter(true);
            authorTableScroll = new JScrollPane();
            authorTableScroll.setViewportView(authorTable);
            add(headLabel, BorderLayout.NORTH);
            add(authorTableScroll, BorderLayout.CENTER);
        }
    }

    static class ShowCategoryListDialog extends JDialog {
        BookPOJO book;

        JLabel headLabel;
        JTable categoryTable;
        JScrollPane categoryTableScroll;

        ShowCategoryListDialog(JFrame parent, String title, boolean modal, String bookId) {
            super(parent, title, modal);
            this.book = BookBUS.getOne(bookId);

            this.setSize(700, 500);
            this.setLocationRelativeTo(null);
            this.setResizable(true);
            setLayout(new BorderLayout());
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.initComponent();
            this.setVisible(true);
        }

        public void initComponent() {
            headLabel = new JLabel("Category list of book: " + book.getId() + " - " + book.getName());

            String[] col = {"ID", "NAME", "DESCRIPTION", "STATUS"};
            categoryTable = new JTable();
            DefaultTableModel tableModel = new DefaultTableModel(col, 0);

            ArrayList<CategoryPOJO> categoryList = CategoryBUS.getCategoryListOfBook(book.getId());
            for (CategoryPOJO category : categoryList) {
                String id = category.getId();
                String name = category.getName();
                String description = category.getDescription();
                String enabled = category.isIsEnabled() ? "Enabled" : "Disabled";

                Object[] data = {id, name, description, enabled};
                tableModel.addRow(data);
            }
            categoryTable.setModel(tableModel);
            categoryTable.setAutoCreateRowSorter(true);
            categoryTableScroll = new JScrollPane();
            categoryTableScroll.setViewportView(categoryTable);
            add(headLabel, BorderLayout.NORTH);
            add(categoryTableScroll, BorderLayout.CENTER);
        }
    }

}

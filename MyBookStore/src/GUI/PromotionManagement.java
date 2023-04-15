package GUI;

import BUS.BookBUS;
import BUS.PromotionBUS;
import POJO.BookPOJO;
import POJO.PromotionPOJO;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PromotionManagement extends JFrame implements ActionListener {
    JPanel menuPane;
    JButton backButton;
    String username;

    final int SIDEBAR_PANE_WIDTH = 170;
    JPanel sidebarPane;
    JButton selectedButton;
    JButton allPromotionsButton;
    JButton pastPromotionsButton;
    JButton currentPromotionsButton;
    JButton upcomingPromotionsButton;
    JButton addPromotionButton;

    JPanel contentPane;
    JLabel contentLabel;
    JTable promotionTable;
    JScrollPane promotionTableScroll;
    PromotionManagement.AddPromotionFormPanel addPromotionFormPanel;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void closeFrame() {
        this.setVisible(false);
        this.dispose();
    }

    public PromotionManagement(String username) {
        setUsername(username);
        setTitle("Employee - Promotion Management");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(true);
        setLayout(new BorderLayout(0, 0));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.BLUE);
        initComponent();
        this.setVisible(true);
    }

    private void initComponent() {
        menuPane = new JPanel();
        menuPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        menuPane.setBackground(Color.CYAN);

        backButton = new JButton("Back");
        backButton.setFocusable(false);
        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                closeFrame();
                UserControl userControl = new UserControl(username);
                userControl.setVisible(true);
            }

        });
        menuPane.add(backButton);

        sidebarPane = new JPanel();
        sidebarPane.setLayout(new BoxLayout(sidebarPane, BoxLayout.Y_AXIS));
        sidebarPane.setPreferredSize(new Dimension(SIDEBAR_PANE_WIDTH, 0));
        sidebarPane.setBorder(new LineBorder(Color.BLACK, 3));
        sidebarPane.setBackground(Color.GREEN);

        allPromotionsButton = new JButton("All Promotions");
        allPromotionsButton.setMaximumSize(new Dimension(SIDEBAR_PANE_WIDTH, 30));
        allPromotionsButton.setFocusable(false);
        allPromotionsButton.addActionListener(this);

        pastPromotionsButton = new JButton("Past Promotions");
        pastPromotionsButton.setMaximumSize(new Dimension(SIDEBAR_PANE_WIDTH, 30));
        pastPromotionsButton.setFocusable(false);
        pastPromotionsButton.addActionListener(this);

        currentPromotionsButton = new JButton("Current Promotions");
        currentPromotionsButton.setMaximumSize(new Dimension(SIDEBAR_PANE_WIDTH, 30));
        currentPromotionsButton.setFocusable(false);
        currentPromotionsButton.addActionListener(this);

        upcomingPromotionsButton = new JButton("Upcoming Promotions");
        upcomingPromotionsButton.setMaximumSize(new Dimension(SIDEBAR_PANE_WIDTH, 30));
        upcomingPromotionsButton.setFocusable(false);
        upcomingPromotionsButton.addActionListener(this);

        addPromotionButton = new JButton("Add New Promotion");
        addPromotionButton.setMaximumSize(new Dimension(SIDEBAR_PANE_WIDTH, 30));
        addPromotionButton.setFocusable(false);
        addPromotionButton.addActionListener(this);

        sidebarPane.add(allPromotionsButton);
        sidebarPane.add(pastPromotionsButton);
        sidebarPane.add(currentPromotionsButton);
        sidebarPane.add(upcomingPromotionsButton);
        sidebarPane.add(addPromotionButton);

        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        contentLabel = new JLabel();
        contentLabel.setText("Please choose an action in the sidebar...");
        contentLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);

        promotionTableScroll = new JScrollPane();

        contentPane.add(contentLabel);

        add(menuPane, BorderLayout.NORTH);
        add(sidebarPane, BorderLayout.WEST);
        add(contentPane, BorderLayout.CENTER);
    }

    public JTable getPromotionTable() {
        String[] col = { "ID", "NAME", "DESCRIPTION", "START DATE", "END DATE",
                "PERCENT", "APPLY FOR", "LIMIT", "STATUS", "ACTION", "EDIT", "BOOKS APPLIED" };
        ArrayList<PromotionPOJO> promotionList = null;
        if (selectedButton == allPromotionsButton) {
            promotionList = PromotionBUS.getAll();
        } else if (selectedButton == pastPromotionsButton) {
            promotionList = PromotionBUS.getPastPromotions();
        } else if (selectedButton == currentPromotionsButton) {
            promotionList = PromotionBUS.getCurrentPromotions();
        } else if (selectedButton == upcomingPromotionsButton) {
            promotionList = PromotionBUS.getUpcomingPromotions();
        }

        JTable table = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);

        if (promotionList != null) {
            for (PromotionPOJO promotion : promotionList) {
                String id = promotion.getId();
                String name = promotion.getName();
                String description = promotion.getDescription();
                Date startDate = promotion.getStartDate();
                Date endDate = promotion.getEndDate();
                Double percent = promotion.getPercent();
                String applyOption = promotion.getApplyOption();
                Integer limitOrders = promotion.getLimitOrders();
                String status = promotion.isEnabled() ? "Enabled" : "Disabled";
                String action = promotion.isEnabled() ? "Disable" : "Enable";

                Object[] data = { id, name, description, startDate, endDate, percent, applyOption, limitOrders,
                        status, action, "Edit", "Show" };
                tableModel.addRow(data);
            }
        }
        table.setModel(tableModel);
        table.setAutoCreateRowSorter(true);
        table.getColumn("ACTION").setCellRenderer(new ButtonRenderer());
        table.getColumn("ACTION").setCellEditor(new PromotionManagement.ButtonEditor(new JCheckBox()));
        table.getColumn("EDIT").setCellRenderer(new ButtonRenderer());
        table.getColumn("EDIT").setCellEditor(new PromotionManagement.ButtonEditor(new JCheckBox()));
        table.getColumn("BOOKS APPLIED").setCellRenderer(new ButtonRenderer());
        table.getColumn("BOOKS APPLIED").setCellEditor(new PromotionManagement.ButtonEditor(new JCheckBox()));
        return table;
    }

    // class methods
    public void refreshPromotionTable() {
        contentLabel.setText(selectedButton.getText() + " List");
        contentPane.add(contentLabel);
        promotionTable = getPromotionTable();
        promotionTableScroll.setViewportView(promotionTable);
        contentPane.add(promotionTableScroll);
        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        contentPane.removeAll();
        selectedButton = (JButton) e.getSource();

        if (selectedButton == allPromotionsButton || selectedButton == pastPromotionsButton
                || selectedButton == currentPromotionsButton || selectedButton == upcomingPromotionsButton) {
            refreshPromotionTable();
        } else if (selectedButton == addPromotionButton) {
            addPromotionFormPanel = new AddPromotionFormPanel();
            contentLabel.setText("Add A New Promotion");
            contentPane.add(contentLabel);
            contentPane.add(addPromotionFormPanel);
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
                String promotionId = promotionTable.getValueAt(row, 0).toString();
                if (Objects.equals(type, "Enable")) {
                    PromotionBUS.enable(promotionId);
                    refreshPromotionTable();
                } else if (Objects.equals(type, "Disable")) {
                    PromotionBUS.disable(promotionId);
                    refreshPromotionTable();
                } else if (Objects.equals(type, "Edit")) {
                    new EditPromotionDialog(PromotionManagement.this, "Edit Promotion", true, promotionId);
                    refreshPromotionTable();
                } else if (Objects.equals(type, "Show")) {
                    new ShowListOfBooksAppliedDialog(PromotionManagement.this, "List Of Books Applied", true,
                            promotionId);
                } else {
                    System.out.println("ERROR: " + promotionTable.getValueAt(row, 0) + " - " + type);
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

    public static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private final String datePattern = "yyyy-MM-dd";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }

    }

    public class EditPromotionDialog extends JDialog implements ActionListener {
        PromotionPOJO promotion;

        JLabel headLabel;

        JLabel idLabel;
        JTextField idField;

        JLabel nameLabel;
        JTextField nameField;

        JLabel descriptionLabel;
        JTextField descriptionField;

        JLabel startDateLabel;
        UtilDateModel startDateModel;
        JDatePanelImpl startDatePanel;
        JDatePickerImpl startDateField;

        JLabel endDateLabel;
        UtilDateModel endDateModel;
        JDatePanelImpl endDatePanel;
        JDatePickerImpl endDateField;

        JLabel percentLabel;
        JTextField percentField;

        JLabel applyOptionLabel;
        JComboBox<String> applyOptionField;

        JLabel limitOrdersLabel;
        JTextField limitOrdersField;

        JLabel statusLabel;
        JComboBox<String> statusField;

        ArrayList<BookPOJO> allBooks;
        JLabel listBookLabel;
        JList<CheckboxListItem> listBookField;
        JScrollPane listBookScroll;

        JButton cancelButton;
        JButton saveButton;

        GridBagConstraints gbc;

        EditPromotionDialog(JFrame parent, String title, boolean modal, String promotionId) {
            super(parent, title, modal);
            this.promotion = PromotionBUS.getOne(promotionId);

            this.setSize(700, 700);
            this.setLocationRelativeTo(null);
            this.setResizable(true);
            setLayout(new GridBagLayout());
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.initComponent();
            this.setVisible(true);
        }

        public void initComponent() {
            headLabel = new JLabel("You are editing promotion ID: " + promotion.getId());

            idLabel = new JLabel("ID:");
            idField = new JTextField(promotion.getId());
            idField.setEditable(false);

            nameLabel = new JLabel("Name:");
            nameField = new JTextField(promotion.getName());

            descriptionLabel = new JLabel("Description:");
            descriptionField = new JTextField(promotion.getDescription());

            Properties p = new Properties();
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.year", "Year");
            startDateLabel = new JLabel("Start Date: ");
            startDateModel = new UtilDateModel();
            if (promotion.getStartDate() != null) {
                Calendar startDateCalendar = Calendar.getInstance();
                startDateCalendar.setTime(promotion.getStartDate());
                startDateModel.setDate(startDateCalendar.get(Calendar.YEAR), startDateCalendar.get(Calendar.MONTH),
                        startDateCalendar.get(Calendar.DAY_OF_MONTH));
                startDateModel.setSelected(true);
            }
            startDatePanel = new JDatePanelImpl(startDateModel, p);
            startDateField = new JDatePickerImpl(startDatePanel, new DateLabelFormatter());

            endDateLabel = new JLabel("End Date: ");
            endDateModel = new UtilDateModel();
            if (promotion.getEndDate() != null) {
                Calendar endDateCalendar = Calendar.getInstance();
                endDateCalendar.setTime(promotion.getEndDate());
                endDateModel.setDate(endDateCalendar.get(Calendar.YEAR), endDateCalendar.get(Calendar.MONTH),
                        endDateCalendar.get(Calendar.DAY_OF_MONTH));
                endDateModel.setSelected(true);
            }
            endDatePanel = new JDatePanelImpl(endDateModel, p);
            endDateField = new JDatePickerImpl(endDatePanel, new DateLabelFormatter());

            percentLabel = new JLabel("Percent Discount: ");
            percentField = new JTextField(promotion.getPercent().toString());
            percentField.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent ke) {
                    char key = ke.getKeyChar();
                    percentField.setEditable(key >= '0' && key <= '9' || key == '\b' || key == '.');
                    try {
                        double currentPercent = Double.parseDouble(percentField.getText() + key);
                        if (key == 'd' || currentPercent > 1.0) {
                            throw new Exception();
                        }
                        percentField.setEditable(true);
                    } catch (Exception ex) {
                        percentField.setEditable(false);
                    }
                }
            });

            applyOptionLabel = new JLabel("Apply For: ");
            String[] applyOptions = { "All", "Only Official Customer" };
            applyOptionField = new JComboBox<>(applyOptions);
            applyOptionField.setSelectedItem(promotion.getApplyOption());

            limitOrdersLabel = new JLabel("Limit First Orders: ");
            limitOrdersField = new JTextField(promotion.getLimitOrders().toString());
            limitOrdersField.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent ke) {
                    char key = ke.getKeyChar();
                    limitOrdersField.setEditable(key >= '0' && key <= '9' || key == '\b');
                }
            });

            statusLabel = new JLabel("Status:");
            String[] statuses = { "Enabled", "Disabled" };
            statusField = new JComboBox<>(statuses);
            String status = promotion.isEnabled() ? "Enabled" : "Disabled";
            statusField.setSelectedItem(status);

            allBooks = BookBUS.getAll();
            listBookLabel = new JLabel("List Of Books Applied: ");
            ArrayList<CheckboxListItem> listBookItem = new ArrayList<>();
            for (BookPOJO book : allBooks) {
                if (book.isEnabled()) {
                    String bookLabel = book.getId() + " - " + book.getName();
                    listBookItem.add(new CheckboxListItem(bookLabel));
                }
            }
            listBookField = new JList<>(listBookItem.toArray(new CheckboxListItem[0]));
            listBookField.setCellRenderer(new CheckboxListRenderer());
            listBookField.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            ArrayList<BookPOJO> listBookApplied = promotion.getListBook();
            ListModel<CheckboxListItem> listBookModel = listBookField.getModel();

            for (int i = 0; i < listBookModel.getSize(); i++) {
                CheckboxListItem item = listBookModel.getElementAt(i);
                String bookId = item.toString().split("-")[0].trim();
                for (BookPOJO book : listBookApplied) {
                    if (bookId.equals(book.getId())) {
                        item.setSelected(true);
                    }
                }
            }
            listBookField.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent event) {
                    // Get index of item clicked
                    int index = listBookField.locationToIndex(event.getPoint());
                    CheckboxListItem item = listBookField.getModel().getElementAt(index);

                    // Toggle selected state
                    item.setSelected(!item.isSelected());

                    // Repaint cell
                    listBookField.repaint(listBookField.getCellBounds(index, index));
                }
            });

            listBookScroll = new JScrollPane();
            listBookScroll.setViewportView(listBookField);

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
            add(descriptionLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(startDateLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(endDateLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(percentLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(applyOptionLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(limitOrdersLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(statusLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(listBookLabel, gbc);

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
            add(descriptionField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(startDateField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(endDateField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(percentField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(applyOptionField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(limitOrdersField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(statusField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(listBookScroll, gbc);

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
                Date startDate = (Date) startDateField.getModel().getValue();
                Date endDate = (Date) endDateField.getModel().getValue();
                if ((startDate != null && endDate != null) && startDate.compareTo(endDate) > 0) {
                    JOptionPane.showMessageDialog(this, "Start date cannot be after end date!");
                    return;
                }
                try {
                    PromotionPOJO modifiedPromotion = new PromotionPOJO(
                            idField.getText(),
                            nameField.getText(),
                            descriptionField.getText(),
                            (Date) startDateField.getModel().getValue(),
                            (Date) endDateField.getModel().getValue(),
                            Double.parseDouble(percentField.getText()),
                            Objects.requireNonNull(applyOptionField.getSelectedItem()).toString(),
                            Integer.parseInt(limitOrdersField.getText()),
                            statusField.getSelectedItem() == "Enabled");

                    Boolean result = PromotionBUS.updateOne(promotion.getId(), modifiedPromotion);
                    if (!result) {
                        JOptionPane.showMessageDialog(this, "Error when updating" +
                                " promotion's information!", "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    ArrayList<String> listInsertBookId = new ArrayList<>();
                    ArrayList<String> listDeleteBookId = new ArrayList<>();
                    ListModel<CheckboxListItem> listBookModel = listBookField.getModel();
                    ArrayList<BookPOJO> listBookApplied = promotion.getListBook();
                    for (int i = 0; i < listBookModel.getSize(); i++) {
                        CheckboxListItem item = listBookModel.getElementAt(i);
                        String bookId = item.toString().split("-")[0].trim();
                        if (item.isSelected()) {
                            boolean isInInsertList = true;
                            for (BookPOJO book : listBookApplied) {
                                if (bookId.equals(book.getId())) {
                                    isInInsertList = false;
                                    break;
                                }
                            }
                            if (isInInsertList) {
                                listInsertBookId.add(bookId);
                            }
                        } else {
                            boolean isInDeleteList = false;
                            for (BookPOJO book : listBookApplied) {
                                if (bookId.equals(book.getId())) {
                                    isInDeleteList = true;
                                    break;
                                }
                            }
                            if (isInDeleteList) {
                                listDeleteBookId.add(bookId);
                            }
                        }
                    }
                    result = PromotionBUS.insertAppliedBooks(promotion.getId(), listInsertBookId);
                    if (!result) {
                        JOptionPane.showMessageDialog(this, "Error when inserting applied" +
                                " books into this promotion!", "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    result = PromotionBUS.deleteNotAppliedBooks(promotion.getId(), listDeleteBookId);
                    if (!result) {
                        JOptionPane.showMessageDialog(this, "Error when deleting not applied" +
                                " books from this promotion!", "Error", JOptionPane.WARNING_MESSAGE);
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

    class AddPromotionFormPanel extends JPanel implements ActionListener {
        JLabel headLabel;

        JLabel idLabel;
        JTextField idField;

        JLabel nameLabel;
        JTextField nameField;

        JLabel descriptionLabel;
        JTextField descriptionField;

        JLabel startDateLabel;
        UtilDateModel startDateModel;
        JDatePanelImpl startDatePanel;
        JDatePickerImpl startDateField;

        JLabel endDateLabel;
        UtilDateModel endDateModel;
        JDatePanelImpl endDatePanel;
        JDatePickerImpl endDateField;

        JLabel percentLabel;
        JTextField percentField;

        JLabel applyOptionLabel;
        JComboBox<String> applyOptionField;

        JLabel limitOrdersLabel;
        JTextField limitOrdersField;

        JLabel statusLabel;
        JComboBox<String> statusField;

        ArrayList<BookPOJO> allBooks;
        JLabel listBookLabel;
        JList<CheckboxListItem> listBookField;
        JScrollPane listBookScroll;

        JButton clearButton;
        JButton addButton;

        GridBagConstraints gbc;

        AddPromotionFormPanel() {
            this.setSize(700, 500);
            setLayout(new GridBagLayout());
            this.initComponent();
        }

        public void initComponent() {
            headLabel = new JLabel("Promotion's Information");

            idLabel = new JLabel("ID:");
            idField = new JTextField();

            nameLabel = new JLabel("Name:");
            nameField = new JTextField();

            descriptionLabel = new JLabel("Description:");
            descriptionField = new JTextField();

            Properties p = new Properties();
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.year", "Year");
            startDateLabel = new JLabel("Start Date: ");
            startDateModel = new UtilDateModel();
            startDatePanel = new JDatePanelImpl(startDateModel, p);
            startDateField = new JDatePickerImpl(startDatePanel, new DateLabelFormatter());

            endDateLabel = new JLabel("End Date: ");
            endDateModel = new UtilDateModel();
            endDatePanel = new JDatePanelImpl(endDateModel, p);
            endDateField = new JDatePickerImpl(endDatePanel, new DateLabelFormatter());

            percentLabel = new JLabel("Percent Discount: ");
            percentField = new JTextField();
            percentField.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent ke) {
                    char key = ke.getKeyChar();
                    percentField.setEditable(key >= '0' && key <= '9' || key == '\b' || key == '.');
                    try {
                        double currentPercent = Double.parseDouble(percentField.getText() + key);
                        if (key == 'd' || currentPercent > 1.0) {
                            throw new Exception();
                        }
                        percentField.setEditable(true);
                    } catch (Exception ex) {
                        percentField.setEditable(false);
                    }
                }
            });

            applyOptionLabel = new JLabel("Apply For: ");
            String[] applyOptions = { "All", "Only Official Customer" };
            applyOptionField = new JComboBox<>(applyOptions);

            limitOrdersLabel = new JLabel("Limit First Orders: ");
            limitOrdersField = new JTextField();
            limitOrdersField.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent ke) {
                    char key = ke.getKeyChar();
                    limitOrdersField.setEditable(key >= '0' && key <= '9' || key == '\b');
                }
            });

            statusLabel = new JLabel("Status:");
            String[] statuses = { "Enabled", "Disabled" };
            statusField = new JComboBox<>(statuses);

            allBooks = BookBUS.getAll();
            listBookLabel = new JLabel("List Of Books Applied: ");
            ArrayList<CheckboxListItem> listBookItem = new ArrayList<>();
            for (BookPOJO book : allBooks) {
                if (book.isEnabled()) {
                    String bookLabel = book.getId() + " - " + book.getName();
                    listBookItem.add(new CheckboxListItem(bookLabel));
                }
            }
            listBookField = new JList<>(listBookItem.toArray(new CheckboxListItem[0]));
            listBookField.setCellRenderer(new CheckboxListRenderer());
            listBookField.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listBookField.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent event) {
                    // Get index of item clicked
                    int index = listBookField.locationToIndex(event.getPoint());
                    CheckboxListItem item = listBookField.getModel().getElementAt(index);

                    // Toggle selected state
                    item.setSelected(!item.isSelected());

                    // Repaint cell
                    listBookField.repaint(listBookField.getCellBounds(index, index));
                }
            });

            listBookScroll = new JScrollPane();
            listBookScroll.setViewportView(listBookField);

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
            add(descriptionLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(startDateLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(endDateLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(percentLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(applyOptionLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(limitOrdersLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(statusLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(listBookLabel, gbc);

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
            add(descriptionField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(startDateField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(endDateField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(percentField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(applyOptionField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(limitOrdersField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(statusField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(listBookScroll, gbc);

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
                descriptionField.setText("");
                percentField.setText("");
                applyOptionField.setSelectedIndex(0);
                limitOrdersField.setText("");
                statusField.setSelectedIndex(0);
                ListModel<CheckboxListItem> listBookModel = listBookField.getModel();
                for (int i = 0; i < listBookModel.getSize(); i++) {
                    listBookModel.getElementAt(i).setSelected(false);
                }
                revalidate();
                repaint();

            } else if (e.getSource() == addButton) {
                Date startDate = (Date) startDateField.getModel().getValue();
                Date endDate = (Date) endDateField.getModel().getValue();
                if ((startDate != null && endDate != null) && startDate.compareTo(endDate) > 0) {
                    JOptionPane.showMessageDialog(this, "Start date cannot be after end date!");
                    return;
                }
                try {
                    PromotionPOJO promotion = new PromotionPOJO(
                            idField.getText(),
                            nameField.getText(),
                            descriptionField.getText(),
                            (Date) startDateField.getModel().getValue(),
                            (Date) endDateField.getModel().getValue(),
                            Double.parseDouble(percentField.getText()),
                            Objects.requireNonNull(applyOptionField.getSelectedItem()).toString(),
                            Integer.parseInt(limitOrdersField.getText()),
                            statusField.getSelectedItem() == "Enabled");

                    Boolean result = PromotionBUS.insertOne(promotion);
                    if (!result) {
                        JOptionPane.showMessageDialog(this, "Error when adding new promotion!", "Error",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    ArrayList<String> listInsertBookId = new ArrayList<>();
                    ListModel<CheckboxListItem> listBookModel = listBookField.getModel();
                    for (int i = 0; i < listBookModel.getSize(); i++) {
                        CheckboxListItem item = listBookModel.getElementAt(i);
                        String bookId = item.toString().split("-")[0].trim();
                        if (item.isSelected()) {
                            listInsertBookId.add(bookId);
                        }
                    }
                    result = PromotionBUS.insertAppliedBooks(promotion.getId(), listInsertBookId);
                    if (!result) {
                        JOptionPane.showMessageDialog(this, "Error inserting applied" +
                                " books into this promotion!", "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    JOptionPane.showMessageDialog(this, "Add new promotion success!", "Success",
                            JOptionPane.PLAIN_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid information, please check again!");
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

    static class ShowListOfBooksAppliedDialog extends JDialog {
        PromotionPOJO promotion;

        JLabel headLabel;
        JTable bookTable;
        JScrollPane bookTableScroll;

        ShowListOfBooksAppliedDialog(JFrame parent, String title, boolean modal, String promotionId) {
            super(parent, title, modal);
            this.promotion = PromotionBUS.getOne(promotionId);

            this.setSize(700, 500);
            this.setLocationRelativeTo(null);
            this.setResizable(true);
            setLayout(new BorderLayout());
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.initComponent();
            this.setVisible(true);
        }

        public void initComponent() {
            headLabel = new JLabel("List of books applied for promotion with id: " + promotion.getId());

            String[] col = { "ID", "NAME", "ID PUBLISHER", "PRICE", "STOCK", "TOTAL PURCHASE", "RELEASE DATE",
                    "STATUS" };
            ArrayList<BookPOJO> bookList = promotion.getListBook();

            bookTable = new JTable();
            DefaultTableModel tableModel = new DefaultTableModel(col, 0);

            for (BookPOJO book : bookList) {
                String id = book.getId();
                String name = book.getName();
                String idPublisher = book.getIdPublisher();
                Integer price = book.getPrice();
                Integer stock = book.getStock();
                Integer totalPurchase = book.getTotalPurchase();
                Date releaseDate = book.getReleaseDate();
                String enabled = book.isEnabled() ? "Enabled" : "Disabled";

                Object[] data = { id, name, idPublisher, price, stock, totalPurchase, releaseDate, enabled };
                tableModel.addRow(data);
            }
            bookTable.setModel(tableModel);
            bookTable.setAutoCreateRowSorter(true);
            bookTableScroll = new JScrollPane();
            bookTableScroll.setViewportView(bookTable);
            add(headLabel, BorderLayout.NORTH);
            add(bookTableScroll, BorderLayout.CENTER);
        }
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import BUS.BookBUS;
import BUS.CustomerBUS;
import BUS.OrderDetailBUS;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import BUS.OrdersBUS;
import POJO.BookPOJO;
import POJO.CustomerPOJO;
import POJO.OrdersPOJO;
import POJO.OrderDetailPOJO;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bachl
 */
public class CreateOrderFrame extends javax.swing.JFrame {
    List<BookPOJO> books;
    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Initialize Table
    DefaultTableModel viewBooksListTableModel = new DefaultTableModel() {
        // disable to edit table
        public boolean isCellEditable(int rowIndex, int mColIndex) {
            if (mColIndex == 3)
                return true;
            return false;
        }

    };

    DefaultTableModel viewAddedBooksTableModel = new DefaultTableModel() {
        // disable to edit table
        public boolean isCellEditable(int rowIndex, int mColIndex) {
            return false;
        }
    };

    /**
     * Creates new form CreateOrderFrame
     */
    public CreateOrderFrame(String username) {
        setUsername(username);
        initComponents();
        this.setLocationRelativeTo(null);

        books = BookBUS.getAll();
        initTable();
        fillTableViewBooksList(books);
    }

    private String createID(String maxID) {
        String codePart = "";
        String numberPart = "";
        String id = null;

        for (int i = 0; i < maxID.length(); i++) {
            char ch = maxID.charAt(i);
            if (Character.isDigit(ch)) {
                numberPart += ch;
            } else {
                codePart += ch;
            }
        }

        for (int i = 0; i < numberPart.length(); i++) {
            if (numberPart.charAt(0) == '0') {
                numberPart.substring(i + 1, numberPart.length() - 1);
                break;
            } else
                break;
        }

        int numberID = Integer.parseInt(numberPart) + 1;
        if (numberID < 10) {
            numberPart = "0" + Integer.toString(numberID);
        } else {
            numberPart = Integer.toString(numberID);
        }

        id = codePart + numberPart;
        return id;
    }

    private void initTable() {
        String[] colsInBooksList = new String[] { "ID", "Name", "Price", "Quantity" };
        viewBooksListTableModel.setColumnIdentifiers(colsInBooksList);
        viewBooksListTableModel.setRowCount(0);

        String[] colsInAddedBooks = new String[] { "ID", "Quantity", "Price" };
        viewAddedBooksTableModel.setColumnIdentifiers(colsInAddedBooks);
        viewAddedBooksTableModel.setRowCount(0);

        tableViewBooksList.setModel(viewBooksListTableModel);
        tableViewAddedBooks.setModel(viewAddedBooksTableModel);
    }

    private void fillTableViewBooksList(List<BookPOJO> books) {
        viewBooksListTableModel.setRowCount(0);

        for (int i = 0; i < books.size(); i++) {
            String id = books.get(i).getId();
            String name = books.get(i).getName();
            // Sale price is more than 10% of import price
            double percentCompareToImportPrice = 0.1;
            int price = (int) (books.get(i).getPrice() * (1 + percentCompareToImportPrice));

            viewBooksListTableModel.addRow(new String[] { id, name, "" + price });
        }
        viewBooksListTableModel.fireTableDataChanged();
    }

    private List<OrderDetailPOJO> createOrderDetail(String idOrder) {
        List<OrderDetailPOJO> orderDetailList = new ArrayList<>();
        OrderDetailPOJO orderDetail = null;

        for (int i = 0; i < viewAddedBooksTableModel.getRowCount(); i++) {
            String idBook = viewAddedBooksTableModel.getValueAt(i, 0).toString();
            int quantity = Integer.parseInt(viewAddedBooksTableModel.getValueAt(i, 1).toString());
            int price = Integer.parseInt(viewAddedBooksTableModel.getValueAt(i, 2).toString());

            orderDetail = new OrderDetailPOJO(idOrder, idBook, quantity, price);
            orderDetailList.add(orderDetail);
        }
        return orderDetailList;
    }

    private boolean addCustomer(String idCustomer) {
        String name = textFieldNameCustomer.getText();
        boolean isOfficialCustomer = false;
        double discount = 0;

        if (checkboxOfficialCustomer.isSelected()) {
            isOfficialCustomer = true;
            discount = 0.05;
        }

        CustomerPOJO cus = new CustomerPOJO(idCustomer, name, isOfficialCustomer, discount);
        CustomerBUS bus2 = new CustomerBUS();

        return bus2.addNewCustomer(cus);
    }

    private boolean addOrder(String id, String createAt, String createBy, String boughtBy, int sumCost) {
        OrdersBUS bus = new OrdersBUS();
        return bus.insertOrder(id, createAt, createBy, boughtBy, sumCost);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelEmployeeID = new javax.swing.JLabel();
        labelCustomerName = new javax.swing.JLabel();
        textFieldIDEmployee = new javax.swing.JTextField();
        textFieldNameCustomer = new javax.swing.JTextField();
        checkboxOfficialCustomer = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableViewBooksList = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableViewAddedBooks = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        textFieldTotalPrice = new javax.swing.JTextField();
        textFieldDiscount = new javax.swing.JTextField();
        btnBack = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        textFieldPayment = new javax.swing.JTextField();
        btnCreate = new javax.swing.JButton();
        btnAddBook = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        labelEmployeeID.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelEmployeeID.setText("Employee's ID: ");

        labelCustomerName.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelCustomerName.setText("Customer's Name: ");

        textFieldIDEmployee.setPreferredSize(new java.awt.Dimension(96, 40));
        textFieldIDEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldIDEmployeeActionPerformed(evt);
            }
        });
        textFieldIDEmployee.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textFieldIDEmployeeKeyTyped(evt);
            }
        });

        textFieldNameCustomer.setMinimumSize(new java.awt.Dimension(96, 40));
        textFieldNameCustomer.setPreferredSize(new java.awt.Dimension(96, 40));
        textFieldNameCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldNameCustomerActionPerformed(evt);
            }
        });
        textFieldNameCustomer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textFieldNameCustomerKeyTyped(evt);
            }
        });

        checkboxOfficialCustomer.setText("Official Customer");
        checkboxOfficialCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxOfficialCustomerActionPerformed(evt);
            }
        });

        tableViewBooksList.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null },
                        { null, null, null },
                        { null, null, null },
                        { null, null, null }
                },
                new String[] {
                        "ID", "Name", "Price"
                }));
        tableViewBooksList.setRowHeight(35);
        tableViewBooksList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableViewBooksListMouseClicked(evt);
            }
        });
        tableViewBooksList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tableViewBooksListKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tableViewBooksList);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 25)); // NOI18N
        jLabel3.setText("Books list");

        tableViewAddedBooks.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null },
                        { null, null, null },
                        { null, null, null },
                        { null, null, null }
                },
                new String[] {
                        "ID", "Quantity", "Price"
                }));
        tableViewAddedBooks.setRowHeight(35);
        jScrollPane2.setViewportView(tableViewAddedBooks);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 25)); // NOI18N
        jLabel4.setText("Added Books");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel5.setText("Total price: ");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel6.setText("Discount: ");

        btnBack.setBackground(new java.awt.Color(255, 153, 153));
        btnBack.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnBack.setText("Back");
        btnBack.setPreferredSize(new java.awt.Dimension(1020, 40));
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel8.setText("Payment: ");

        btnCreate.setBackground(new java.awt.Color(102, 255, 153));
        btnCreate.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnCreate.setText("Create");
        btnCreate.setPreferredSize(new java.awt.Dimension(120, 40));
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        btnAddBook.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnAddBook.setText("Add");
        btnAddBook.setMaximumSize(new java.awt.Dimension(110, 40));
        btnAddBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddBookActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(44, 44, 44)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addGroup(layout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(labelEmployeeID,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        150,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(labelCustomerName))
                                                                        .addGap(37, 37, 37)
                                                                        .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(checkboxOfficialCustomer)
                                                                                .addGroup(layout.createParallelGroup(
                                                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                                                        false)
                                                                                        .addComponent(
                                                                                                textFieldIDEmployee,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                        .addComponent(
                                                                                                textFieldNameCustomer,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                615, Short.MAX_VALUE))))
                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                        layout.createSequentialGroup()
                                                                                .addGap(205, 205, 205)
                                                                                .addComponent(jLabel3)
                                                                                .addGap(449, 449, 449)
                                                                                .addComponent(jLabel4)
                                                                                .addGap(173, 173, 173)))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                                .createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGroup(layout.createParallelGroup(
                                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addGroup(layout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(82, 82, 82)
                                                                                                .addComponent(btnCreate,
                                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                        Short.MAX_VALUE))
                                                                                        .addGroup(
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                layout.createSequentialGroup()
                                                                                                        .addGap(0, 0,
                                                                                                                Short.MAX_VALUE)
                                                                                                        .addComponent(
                                                                                                                btnAddBook,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                110,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                        .addGap(299,
                                                                                                                299,
                                                                                                                299)))
                                                                                .addGroup(layout.createParallelGroup(
                                                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                                                        false)
                                                                                        .addGroup(layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel5)
                                                                                                .addPreferredGap(
                                                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(
                                                                                                        textFieldTotalPrice,
                                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                        193,
                                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                        .addGroup(
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                layout.createParallelGroup(
                                                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                        .addGroup(layout
                                                                                                                .createSequentialGroup()
                                                                                                                .addComponent(
                                                                                                                        jLabel8)
                                                                                                                .addGap(18,
                                                                                                                        18,
                                                                                                                        18)
                                                                                                                .addComponent(
                                                                                                                        textFieldPayment,
                                                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                        193,
                                                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                        .addGroup(layout
                                                                                                                .createSequentialGroup()
                                                                                                                .addComponent(
                                                                                                                        jLabel6)
                                                                                                                .addGap(18,
                                                                                                                        18,
                                                                                                                        18)
                                                                                                                .addComponent(
                                                                                                                        textFieldDiscount,
                                                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                        193,
                                                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(jScrollPane1,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        499,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(
                                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        Short.MAX_VALUE)
                                                                                .addComponent(jScrollPane2,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        509,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(16, 16, 16))))
                                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(28, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(textFieldIDEmployee,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(labelEmployeeID))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(textFieldNameCustomer,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(labelCustomerName))
                                                .addGap(18, 18, 18)
                                                .addComponent(checkboxOfficialCustomer)
                                                .addGap(37, 37, 37)
                                                .addComponent(jLabel4)
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 298,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(34, 34, 34)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(textFieldTotalPrice,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(5, 5, 5)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(textFieldDiscount,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel6))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(textFieldPayment,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel8))
                                                .addContainerGap(12, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                .createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnAddBook, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textFieldIDEmployeeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_textFieldIDEmployeeActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_textFieldIDEmployeeActionPerformed

    private void textFieldNameCustomerActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_textFieldNameCustomerActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_textFieldNameCustomerActionPerformed

    private void checkboxOfficialCustomerActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_checkboxOfficialCustomerActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_checkboxOfficialCustomerActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        ViewOrdersFrame viewOrders = new ViewOrdersFrame(username);
        viewOrders.setVisible(true);
    }// GEN-LAST:event_btnBackActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCreateActionPerformed
        // TODO add your handling code here:
        String idEmployee = textFieldIDEmployee.getText();
        String nameCustomer = textFieldNameCustomer.getText();
        String totalPriceStr = textFieldTotalPrice.getText();

        // Check whether fill all properties
        if (idEmployee.equals("") || nameCustomer.equals("") || totalPriceStr.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Please enter full properties or Add books to payment!",
                    "Warning message", WARNING_MESSAGE);

            if (idEmployee.equals("")) {
                textFieldIDEmployee.setBorder(new LineBorder(Color.red));
            }
            if (nameCustomer.equals("")) {
                textFieldNameCustomer.setBorder(new LineBorder(Color.red));
            }
        }

        // Check whether employee exists
        OrdersBUS bus3 = new OrdersBUS();
        if (!bus3.isEmployee(idEmployee)) {
            JOptionPane.showMessageDialog(rootPane, "Employee's ID doesn't exist!", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }

        // Automatically crate create_at
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String create_at = dtf.format(now);

        // Take current maxID and create a new order ID
        OrdersBUS bus2 = new OrdersBUS();
        String maxID = bus2.getMaxIDOrder();
        String idOrder = createID(maxID);

        // Take curremt maxID and a new customer ID
        CustomerBUS bus1 = new CustomerBUS();
        maxID = bus1.getMaxIDCustomer();
        String idCustomer = createID(maxID);

        // Add customer into database
        boolean isAddCustomerSuccess = addCustomer(idCustomer);

        // Add order into database
        boolean isAddOrderSuccess = addOrder(idOrder, create_at, idEmployee, idCustomer,
                Integer.parseInt(textFieldPayment.getText()));

        // Create order detail and add order into database
        OrderDetailBUS bus4 = new OrderDetailBUS();
        List<OrderDetailPOJO> orderDetailList = createOrderDetail(idOrder);
        boolean isAddOrderDetailSuccess = bus4.addOrderDetail(orderDetailList);

        if (isAddCustomerSuccess && isAddOrderSuccess && isAddOrderDetailSuccess) {
            JOptionPane.showMessageDialog(rootPane, "You crate an order successfully!");
        } else {
            JOptionPane.showMessageDialog(rootPane, "You crate an order unsuccessfully. Please try it later!");
        }
    }// GEN-LAST:event_btnCreateActionPerformed

    private void tableViewBooksListMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tableViewBooksListMouseClicked

    }// GEN-LAST:event_tableViewBooksListMouseClicked

    private void tableViewBooksListKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_tableViewBooksListKeyPressed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(rootPane, "Active column: " + tableViewBooksList.getCellEditor());
    }// GEN-LAST:event_tableViewBooksListKeyPressed

    private void btnAddBookActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddBookActionPerformed
        // TODO add your handling code here:
        viewAddedBooksTableModel.setRowCount(0);
        int quantity = 0;
        int totalPrice = 0;

        for (int i = 0; i < viewBooksListTableModel.getRowCount(); i++) {
            if (tableViewBooksList.getValueAt(i, 3) != null) {
                quantity = Integer.parseInt(tableViewBooksList.getValueAt(i, 3).toString());

                // When employee edit quantity column it works
                if (quantity > 0) {
                    String id = tableViewBooksList.getValueAt(i, 0).toString();
                    int price = Integer.parseInt(tableViewBooksList.getValueAt(i, 2).toString()) * quantity;

                    totalPrice += price;

                    viewAddedBooksTableModel.addRow(new String[] { id, "" + quantity, "" + price });
                }
            }

        }

        viewAddedBooksTableModel.fireTableDataChanged();
        textFieldTotalPrice.setText("" + totalPrice);

        double discount = 0;
        int payment = totalPrice;

        if (checkboxOfficialCustomer.isSelected()) {
            discount = 0.05;
            payment = (int) (totalPrice * (1 - discount));
        }

        textFieldDiscount.setText("" + discount);
        textFieldPayment.setText("" + payment);
    }// GEN-LAST:event_btnAddBookActionPerformed

    private void textFieldIDEmployeeKeyTyped(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_textFieldIDEmployeeKeyTyped
        // TODO add your handling code here:
        textFieldIDEmployee.setBorder(new LineBorder(Color.black));
    }// GEN-LAST:event_textFieldIDEmployeeKeyTyped

    private void textFieldNameCustomerKeyTyped(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_textFieldNameCustomerKeyTyped
        // TODO add your handling code here:
        textFieldNameCustomer.setBorder(new LineBorder(Color.black));
    }// GEN-LAST:event_textFieldNameCustomerKeyTyped

    private void formWindowClosed(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        ViewOrdersFrame viewOrders = new ViewOrdersFrame(username);
        viewOrders.setVisible(true);
    }// GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CreateOrderFrame.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreateOrderFrame.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreateOrderFrame.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateOrderFrame.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }
        // </editor-fold>

        /* Create and display the form */

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddBook;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCreate;
    private javax.swing.JCheckBox checkboxOfficialCustomer;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelCustomerName;
    private javax.swing.JLabel labelEmployeeID;
    private javax.swing.JTable tableViewAddedBooks;
    private javax.swing.JTable tableViewBooksList;
    private javax.swing.JTextField textFieldDiscount;
    private javax.swing.JTextField textFieldIDEmployee;
    private javax.swing.JTextField textFieldNameCustomer;
    private javax.swing.JTextField textFieldPayment;
    private javax.swing.JTextField textFieldTotalPrice;
    // End of variables declaration//GEN-END:variables
}

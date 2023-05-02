/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import BUS.AccountBUS;
import BUS.BookBUS;
import BUS.CustomerBUS;
import BUS.OrderDetailBUS;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import BUS.OrdersBUS;
import BUS.PromotionBUS;
import BUS.UserBUS;
import POJO.AccountPOJO;
import POJO.BookPOJO;
import POJO.CustomerPOJO;
import POJO.OrdersPOJO;
import POJO.OrderDetailPOJO;
import POJO.PromotionPOJO;
import POJO.UserPOJO;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bachl
 */
public class UpdateOrderFrame extends javax.swing.JFrame {
    List<BookPOJO> books;
    List<OrderDetailPOJO> listBooksInOrder;
    List<Integer> isClickedList = null;
    String username;
    UserPOJO userLogged = null;
    OrdersPOJO order = null;
    CustomerPOJO customer = null;
    int totalPrice = 0;
    int originalQuantity = 0;
    boolean isFirstAdded = false;
    
    final int columnIndexPercentSale = 2;
    final int columnIndexPrice = 3;

    // Initialize Table
    DefaultTableModel viewBooksListTableModel = new DefaultTableModel() {
        // disable to edit table
        public boolean isCellEditable(int rowIndex, int mColIndex) {
            return false;
        }

    };

    DefaultTableModel viewAddedBooksTableModel = new DefaultTableModel() {
        // disable to edit table
        public boolean isCellEditable(int rowIndex, int mColIndex) {
            return mColIndex == 1;
        }
    };
    
    /**
     * Creates new form CreateOrderFrame
     */
    public UpdateOrderFrame(String username, List<OrderDetailPOJO> listBooksInOrder, OrdersPOJO order) {
        this.username = username;
        this.listBooksInOrder = listBooksInOrder;
        this.order = order;
        
        initComponents();
        this.setLocationRelativeTo(null);
        
        CustomerBUS customerBUS = new CustomerBUS();
        customer = customerBUS.getCustomerById(order.getBoughtById());
        
        initInformation();
        
        initTable();
        books = BookBUS.getBookNotDisable();
        fillTableViewBooksList();
        fillTableAddedBooks();
        
        // Kiểm tra đã click chưa để lấy số lượng ban đầu
        createIsClickedList();
                
        textFieldTotalPrice.setText(Integer.toString(totalPrice));
        textFieldPayment.setText(Integer.toString(calculatePayment()));
        textFieldNameCustomer.setText(order.getBoughtBy());
    }
    
    private void createIsClickedList() {
        int rowCount = tableViewBooksList.getRowCount();
        isClickedList = new ArrayList();
        for(int i = 0; i < rowCount; i++) {
            this.isClickedList.add(0);
        }
    }
    
    private void initInformation() {
        AccountBUS accountBUS = new AccountBUS();
        AccountPOJO accountLogged = accountBUS.getAccountByUsername(username);
        
        UserBUS userBUS = new UserBUS();
        this.userLogged = userBUS.getUserByIdAccount(accountLogged.getId());
        
        textFieldIDEmployee.setText(userLogged.getName());
        if(customer.getOfficialCustomer() == 1) {
            checkboxOfficialCustomer.setSelected(true);
            textFieldDiscount.setText("0.05");
        } else {
            checkboxOfficialCustomer.setSelected(false);
            textFieldDiscount.setText("0");
        }
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
            numberPart = "0" + numberID;
        } else {
            numberPart = Integer.toString(numberID);
        }

        id = codePart + numberPart;
        return id;
    }

    private void initTable() {
        String[] colsInBooksList = new String[] { "ID", "Name", "Percent Sale", "Price", "Stock" };
        viewBooksListTableModel.setColumnIdentifiers(colsInBooksList);
        viewBooksListTableModel.setRowCount(0);

        String[] colsInAddedBooks = new String[] { "ID", "Quantity", "Price" };
        viewAddedBooksTableModel.setColumnIdentifiers(colsInAddedBooks);
        viewAddedBooksTableModel.setRowCount(0);

        tableViewBooksList.setModel(viewBooksListTableModel);
        tableViewAddedBooks.setModel(viewAddedBooksTableModel);
    }

    private void fillTableViewBooksList() {
        viewBooksListTableModel.setRowCount(0);
        
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        for (int i = 0; i < books.size(); i++) {
            String id = books.get(i).getId();
            String name = books.get(i).getName();
            
            // Lấy thông tin promotion
            PromotionPOJO promotion = PromotionBUS.getPromotionByIdBook(id);
            double percentSale = 0;
            if(promotion != null) {
                int compareWithStartDate = currentDate.compareTo(promotion.getStartDate());
                int compareWithEndDate = currentDate.compareTo(promotion.getEndDate());
                
                if(compareWithStartDate >= 0 && compareWithEndDate <= 0) {
                    percentSale = promotion.getPercent();
                }
            }
            // Sale price is more than 10% of import price
            double percentCompareToImportPrice = 0.1;
            int price = (int) (books.get(i).getPrice() * (1 + percentCompareToImportPrice));
            
            // Hiển thị giá mới khi có giảm giá từ promotion
            if(promotion != null && promotion.getApplyOption().equals("Only Official Customer")) {
                if(percentSale > 0 && checkboxOfficialCustomer.isSelected()) {
                    price = (int)(price - price * percentSale);
                }
            } else {
                price = (int)(price - price * percentSale);
            }
            
            int stock = books.get(i).getStock();

            viewBooksListTableModel.addRow(new String[] { id, name, percentSale + "", "" + price, "" + stock });
        }
        viewBooksListTableModel.fireTableDataChanged();
    }
    
    private void fillTableAddedBooks() {
        viewAddedBooksTableModel.setRowCount(0);
        
        for(OrderDetailPOJO orderDetail : listBooksInOrder) {
            String idBook = orderDetail.getIdBook();
            int quantity = orderDetail.getQuantity();
            int price = orderDetail.getPrice();
            
            this.totalPrice += price;
            
            viewAddedBooksTableModel.addRow(new String[] { idBook, String.valueOf(quantity), String.valueOf(price) });
        }
        
        viewAddedBooksTableModel.fireTableDataChanged();
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
        int isOfficialCustomer = 0;
        double discount = 0;

        if (checkboxOfficialCustomer.isSelected()) {
            isOfficialCustomer = 1;
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

    private int calculatePayment() {
        if(checkboxOfficialCustomer.isSelected()) {
            return (int)(this.totalPrice * (1 - 0.05));
        }
        return this.totalPrice;
    }
    
    private boolean updateSoldBook() {
        for(int i = 0; i < books.size(); i++) {
            String idBook = books.get(i).getId();
            int stockDB = books.get(i).getStock();
            int totalPurchaseDB = books.get(i).getTotalPurchase();
            int sumStockAndTotalPurchaseDB = stockDB + totalPurchaseDB;
            
            int newStock = Integer.parseInt(tableViewBooksList.getValueAt(i, 4).toString());
            int newTotalPurchase = sumStockAndTotalPurchaseDB - newStock;
            
            if(!BookBUS.updateSoldBook(newStock, newTotalPurchase, idBook)) {
                return false;
            }
        }
        
        return true;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
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
        btnDeleteRow = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        labelEmployeeID.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelEmployeeID.setText("Employee's Name: ");

        labelCustomerName.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelCustomerName.setText("Customer's Name: ");

        textFieldIDEmployee.setEditable(false);
        textFieldIDEmployee.setPreferredSize(new java.awt.Dimension(96, 35));
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

        textFieldNameCustomer.setEditable(false);
        textFieldNameCustomer.setMinimumSize(new java.awt.Dimension(96, 35));
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

        checkboxOfficialCustomer.setEnabled(false);
        checkboxOfficialCustomer.setText("Official Customer");
        // checkboxOfficialCustomer.addActionListener(new java.awt.event.ActionListener() {
        //     public void actionPerformed(java.awt.event.ActionEvent evt) {
        //         checkboxOfficialCustomerActionPerformed(evt);
        //     }
        // });

        textFieldTotalPrice.setEditable(false);
        textFieldDiscount.setEditable(false);
        textFieldPayment.setEditable(false);

        tableViewBooksList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Percent Sale", "Price", "Stock"
            }
        ));
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

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel3.setText("Books List");

        tableViewAddedBooks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Quantity", "Price"
            }
        ));
        tableViewAddedBooks.setRowHeight(35);
        tableViewAddedBooks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableViewAddedBooksMouseClicked(evt);
            }
        });
        tableViewAddedBooks.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tableViewAddedBooksPropertyChange(evt);
            }
        });
        jScrollPane2.setViewportView(tableViewAddedBooks);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
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
        btnCreate.setText("Update");
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

        btnDeleteRow.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnDeleteRow.setText("Delete Row");
        btnDeleteRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteRowActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnAddBook, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnDeleteRow, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel8))
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textFieldDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textFieldTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textFieldPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelEmployeeID, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelCustomerName))
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(textFieldIDEmployee, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
                                .addComponent(textFieldNameCustomer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkboxOfficialCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldIDEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelEmployeeID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelCustomerName)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textFieldNameCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(checkboxOfficialCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(textFieldTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(33, 33, 33))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(textFieldDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnDeleteRow, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAddBook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(textFieldPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 30, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>                        

    private void btnDeleteRowActionPerformed(java.awt.event.ActionEvent evt) {                                             

        int selectedRowIndex = tableViewAddedBooks.getSelectedRow();
        if(selectedRowIndex > -1) {
            String idBookInAdded = tableViewAddedBooks.getValueAt(selectedRowIndex, 0).toString();
            int quantity = Integer.parseInt(tableViewAddedBooks.getValueAt(selectedRowIndex, 1).toString());
            int price = Integer.parseInt(tableViewAddedBooks.getValueAt(selectedRowIndex, 2).toString());
            this.totalPrice -= price;
            
            // Duyệt qua từng dòng trong bảng view books sau đó so sánh id sách được xóa để khôi phục stock như ban đầu
            int rowCount = tableViewBooksList.getRowCount();
            int indexRowToRecover = -1;
            for(int i = 0; i < rowCount; i++) {
                String idBookInBooksList = tableViewBooksList.getValueAt(i, 0).toString();
                
                // Lấy index của dòng cần khôi phục stock
                if(idBookInAdded.equals(idBookInBooksList)) {
                    indexRowToRecover = i;
                    break;
                }
            }
            
            // Kiểm tra id sách trong added có trùng khớp với id book bên books list không
            if(indexRowToRecover > -1) {
                int stock = Integer.parseInt(tableViewBooksList.getValueAt(indexRowToRecover, 4).toString());
                int newStock = stock + quantity;
                viewBooksListTableModel.setValueAt(newStock, indexRowToRecover, 4);
                viewBooksListTableModel.fireTableCellUpdated(indexRowToRecover, 4);
                viewAddedBooksTableModel.removeRow(selectedRowIndex);
            }
        }

        viewAddedBooksTableModel.fireTableDataChanged();
        textFieldTotalPrice.setText("" + totalPrice);
        textFieldPayment.setText("" + calculatePayment());
    }                                            

    private void tableViewAddedBooksPropertyChange(java.beans.PropertyChangeEvent evt) {                                                   
        // TODO add your handling code here:
        if ("tableCellEditor".equals(evt.getPropertyName())) {
            int column = tableViewAddedBooks.getEditingColumn();
            if (column == 1) {
                int row = tableViewAddedBooks.getSelectedRow();
                if(row > -1) {
                    int quantity = Integer.parseInt(viewAddedBooksTableModel.getValueAt(row, 1).toString());
                    
                    int priceBook = 0;
                    for(BookPOJO book : books) {
                        String idBook = book.getId();
                        String idBookInTable = viewAddedBooksTableModel.getValueAt(row, 0).toString();
                        
                        if(idBook.equals(idBookInTable)) {
                            // Lấy thông tin promotion
                            PromotionPOJO promotion = PromotionBUS.getPromotionByIdBook(idBook);
                            double percentSale = 0;
                            if(promotion != null) {
                                percentSale = promotion.getPercent();
                            }
                            // Sale price is more than 10% of import price
                            double percentCompareToImportPrice = 0.1;
                            int price = (int) (book.getPrice() * (1 + percentCompareToImportPrice));

                            // Hiển thị giá mới khi có giảm giá từ promotion
                            if(percentSale > 0) {
                                price = (int)(price - price * percentSale);
                            }
                            
                            priceBook = price;
                        }
                    }
                    
                    int oldPrice = Integer.parseInt(viewAddedBooksTableModel.getValueAt(row, 2).toString());
                    this.totalPrice -= oldPrice;
                    int newPrice = priceBook * quantity;
                    viewAddedBooksTableModel.setValueAt(newPrice, row, 2);
                    viewAddedBooksTableModel.fireTableCellUpdated(row, 2);

                    // Thay đổi stock khi tăng số lượng mua
                    int quantityCurrentAddedBook = Integer.parseInt(tableViewAddedBooks.getValueAt(row, 1).toString());
                    String idBookSelected = tableViewAddedBooks.getValueAt(row, 0).toString();
                    int newStock = -1;
                    for(BookPOJO book : books) {
                        if(book.getId().equals(idBookSelected)) {
                            int validNumberBook = 0;
                            if(isFirstAdded) {
                                newStock = book.getStock() - quantityCurrentAddedBook;
                                validNumberBook = book.getStock();
                            } else {
                                newStock = book.getStock() + this.originalQuantity - quantityCurrentAddedBook;
                                validNumberBook = book.getStock() + this.originalQuantity;
                            }
                            
                             
                            if(newStock < 0) {
                                this.totalPrice += oldPrice;
                                // Khôi phục lại trước khi thay đổi
                                newStock = book.getStock();
                                viewAddedBooksTableModel.setValueAt(originalQuantity, row, 1);
                                viewAddedBooksTableModel.fireTableCellUpdated(row, 1);
                               
                                viewAddedBooksTableModel.setValueAt(oldPrice, row, 2);
                                viewAddedBooksTableModel.fireTableCellUpdated(row, 2);
                                JOptionPane.showMessageDialog(rootPane, "The book only has " + validNumberBook, "Warning message", WARNING_MESSAGE);
                                return;
                            }   
                            break;
                        }
                    }
                    
                    // Duyệt qua từng dòng trong bảng view books sau đó so sánh id sách được xóa để khôi phục stock như ban đầu
                    int rowCount = tableViewBooksList.getRowCount();
                    int indexRowToRecover = -1;
                    for(int i = 0; i < rowCount; i++) {
                        String idBookInTable = viewAddedBooksTableModel.getValueAt(row, 0).toString();
                        String idBookInBooksList = tableViewBooksList.getValueAt(i, 0).toString();

                        // Lấy index của dòng cần khôi phục stock
                        if(idBookInTable.equals(idBookInBooksList)) {
                            indexRowToRecover = i;
                            break;
                        }
                    }
                   
                    if(newStock != -1) {
                        viewBooksListTableModel.setValueAt(newStock, indexRowToRecover, 4);
                        viewBooksListTableModel.fireTableCellUpdated(indexRowToRecover, 4);
                    }
                    
                    
                    this.totalPrice += newPrice;
                    textFieldTotalPrice.setText(Integer.toString(totalPrice));
                    textFieldPayment.setText(Integer.toString(calculatePayment()));
                }
            }
        }
    }                                                  

    // private void checkboxOfficialCustomerActionPerformed(java.awt.event.ActionEvent evt) {                                                         
    //     // TODO add your handling code here:
    //     // Mỗi lần check thì tải lại dữ liệu của bảng
    //     fillTableViewBooksList();
    //     if(checkboxOfficialCustomer.isSelected()) {
    //         textFieldNameCustomer.setText(customer.getName());
    //     } else {
    //         textFieldNameCustomer.setText("Anonymous");
    //     }
    // }                                                        

    private void tableViewAddedBooksMouseClicked(java.awt.event.MouseEvent evt) {                                                 
        // TODO add your handling code here:
        int selectedRowIndex = tableViewAddedBooks.getSelectedRow();
        if(selectedRowIndex > -1 && isClickedList.get(selectedRowIndex) != 1) {
            this.originalQuantity = Integer.parseInt(tableViewAddedBooks.getValueAt(selectedRowIndex, 1).toString());
            isClickedList.set(selectedRowIndex, 1);
        }
    }                                                

    private void textFieldIDEmployeeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_textFieldIDEmployeeActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_textFieldIDEmployeeActionPerformed

    private void textFieldNameCustomerActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_textFieldNameCustomerActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_textFieldNameCustomerActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        ViewOrdersFrame viewOrders = new ViewOrdersFrame(username);
        viewOrders.setVisible(true);
    }// GEN-LAST:event_btnBackActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCreateActionPerformed
        // TODO add your handling code here:
        String idEmployee = userLogged.getId();
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

        // Automatically create create_at
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String create_at = dtf.format(now);

        // Take current maxID and create a new order ID
        OrdersBUS bus2 = new OrdersBUS();
        String maxID = bus2.getMaxIDOrder();
        String newIdOrder = createID(maxID);

        // Kiểm tra xem các cập nhật có thành công hay không
        boolean isSuccessUpdated = true;
        // Xóa các sách trong đơn hàng cũ
        OrderDetailBUS orderDetailBUS = new OrderDetailBUS();
        boolean isSuccessDeleted = orderDetailBUS.deleteOrder(order.getId());
        if(!isSuccessDeleted) {
            isSuccessUpdated = false;
        }
        
        // Xóa đơn hàng cũ
        OrdersBUS orderBUS = new OrdersBUS();
        boolean isDeleteOrder = orderBUS.deleteOrder(order.getId());
        if(!isDeleteOrder) {
            isSuccessUpdated = false;
        }

        // Cập nhật đơn hàng cũ thành đơn hàng mới - Nếu nhân viên mới cập nhật thì sẽ hiển thị thông tin mới nhất của người đó
        boolean isAddOrderSuccess = addOrder(newIdOrder, create_at, idEmployee, order.getBoughtById(),
                Integer.parseInt(textFieldPayment.getText()));
        if(!isAddOrderSuccess) {
            isSuccessUpdated = false;
        }
        
        // Create order detail and write into database
        OrderDetailBUS bus4 = new OrderDetailBUS();
        List<OrderDetailPOJO> orderDetailList = createOrderDetail(newIdOrder);
        boolean isAddOrderDetailSuccess = bus4.addOrderDetail(orderDetailList);
        if(!isAddOrderDetailSuccess) {
            isSuccessUpdated = false;
        }  
        
        // Cập nhật số lượng trong kho và tổng số sách đó bán được
        boolean isSoldBook = updateSoldBook();
        if(!isSoldBook) {
            isSuccessUpdated = false;
        }

        // Kiểm quá trình cập nhật đơn hàng thành công hay không?
        if (isSuccessUpdated) {
            JOptionPane.showMessageDialog(rootPane, "You update an order successfully!");
        } else {
            JOptionPane.showMessageDialog(rootPane, "You update an order unsuccessfully. Please try it later!");
        }
    }// GEN-LAST:event_btnCreateActionPerformed

    private void tableViewBooksListMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tableViewBooksListMouseClicked

    }// GEN-LAST:event_tableViewBooksListMouseClicked

    private void tableViewBooksListKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_tableViewBooksListKeyPressed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(rootPane, "Active column: " + tableViewBooksList.getCellEditor());
    }// GEN-LAST:event_tableViewBooksListKeyPressed

    private void btnAddBookActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddBookActionPerformed
        isFirstAdded = true;
        int selectedRowIndex = tableViewBooksList.getSelectedRow();
        String idBook = tableViewBooksList.getValueAt(selectedRowIndex, 0).toString();
        for (int i = 0; i < tableViewAddedBooks.getRowCount(); i++){
            String idAddedBook = tableViewAddedBooks.getValueAt(i, 0).toString();
            if (idBook.equals(idAddedBook)){
                return;
            }
        }

        if(selectedRowIndex > -1) {
            int stock = Integer.parseInt(tableViewBooksList.getValueAt(selectedRowIndex, 4).toString());
            if(stock < 1) {
                JOptionPane.showMessageDialog(rootPane, "The book is sold off. Please choose another.");
                return;

            } else {
                stock--;
                viewBooksListTableModel.setValueAt(stock, selectedRowIndex, 4);
                viewBooksListTableModel.fireTableCellUpdated(selectedRowIndex, 4);
            }
            String id = tableViewBooksList.getValueAt(selectedRowIndex, 0).toString();
            int quantity = 1;
            int price = Integer.parseInt(tableViewBooksList.getValueAt(selectedRowIndex, columnIndexPrice).toString()) * quantity;
            this.totalPrice += price;

            viewAddedBooksTableModel.addRow(new String[] { id, "" + quantity, "" + price });
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


    // Variables declaration - do not modify                     
    private javax.swing.JButton btnAddBook;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnDeleteRow;
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
    // End of variables declaration                   
}

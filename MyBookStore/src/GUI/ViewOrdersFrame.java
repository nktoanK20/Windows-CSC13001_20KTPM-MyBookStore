/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import BUS.OrdersBUS;
import POJO.OrdersPOJO;
import java.awt.Color;
import java.awt.Component;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author bachl
 */
public class ViewOrdersFrame extends javax.swing.JFrame {
    private String typeChooser;
    private int totalCost;
    private final List<OrdersPOJO> orders;
    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    DefaultTableModel tableModel = new DefaultTableModel() {
        // disable to edit table
        public boolean isCellEditable(int rowIndex, int mColIndex) {
            return false;
        }
    };

    public void CloseFrame() {
        this.setVisible(false);
        this.dispose();
    }

    /**
     * Creates new form ViewOrdersFrame
     */
    public ViewOrdersFrame(String username) {
        setUsername(username);
        initComponents();
        this.typeChooser = "";
        this.totalCost = 0;
        this.orders = getAllOrdersList();

        initDefaultComboBoxTimeRange();

        btnViewOrders.setBackground(Color.yellow);
        initTable();
        initGroupTimeRange();
        fillTableViewOrders(orders);

        this.setLocationRelativeTo(null);
    }

    private void initDefaultComboBoxTimeRange() {
        comboBoxWeek.setEnabled(false);
        comboBoxMonthOfWeek.setEnabled(false);
        yearChooserOfWeek.setEnabled(false);
        comboBoxMonth.setEnabled(false);
        yearChooser.setEnabled(false);

        labelFromDate.setVisible(false);
        labelToDate.setVisible(false);
        calendarFromDate.setVisible(false);
        calendarToDate.setVisible(false);
    }

    private void initTable() {
        String[] cols = new String[] { "ID", "Create at", "Create by", "Bought by", "Sum cost" };
        tableModel.setColumnIdentifiers(cols);
        tableModel.setRowCount(0);

        tableViewOrders.setModel(tableModel);
    }

    private void initGroupTimeRange() {
        ButtonGroup groupTimeRange = new ButtonGroup();
        groupTimeRange.add(radioWeek);
        groupTimeRange.add(radioMonth);
        groupTimeRange.add(radioFromToDate);
    }

    private List<OrdersPOJO> getAllOrdersList() {
        OrdersBUS bus = new OrdersBUS();
        return bus.getAllOrders();
    }

    ArrayList<String> createFromToDate() {
        ArrayList<String> date = new ArrayList<String>();

        String week = comboBoxWeek.getSelectedItem().toString();
        int month = comboBoxMonthOfWeek.getMonth() + 1;
        int year = yearChooserOfWeek.getYear();

        if (week.equals("Week 1")) {
            date.add(year + "-" + month + "-01");
            date.add(year + "-" + month + "-07");
        } else if (week.equals("Week 2")) {
            date.add(year + "-" + month + "-08");
            date.add(year + "-" + month + "-14");

            System.out.println(date.get(0));
            System.out.println(date.get(1));
        } else if (week.equals("Week 3")) {
            date.add(year + "-" + month + "-15");
            date.add(year + "-" + month + "-21");
        } else if (week.equals("Week 4")) {
            date.add(year + "-" + month + "-22");
            date.add(year + "-" + month + "-28");
        } else if (week.equals("Week 5")) {
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                date.add(year + "-" + month + "-29");
                date.add(year + "-" + month + "-31");
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                date.add(year + "-" + month + "-29");
                date.add(year + "-" + month + "-30");
            } else {
                date.add(year + "-" + month + "-28");
                date.add(year + "-" + month + "-28");
            }
        }

        return date;
    }

    private List<OrdersPOJO> getOrdersByFromToDate(String fromDate, String toDate) {
        OrdersBUS bus = new OrdersBUS();

        return bus.getOrdersByFromToDate(fromDate, toDate);
    }

    private List<OrdersPOJO> getOrdersByMonth() {
        int month = comboBoxMonth.getMonth() + 1;
        int year = yearChooser.getYear();

        OrdersBUS bus = new OrdersBUS();

        return bus.getOrdersByMonth(month, year);
    }

    private void fillTableViewOrders(List<OrdersPOJO> orders) {
        this.totalCost = 0;
        tableModel.setRowCount(0);

        for (int i = 0; i < orders.size(); i++) {
            String id = orders.get(i).getId();
            String createAt = orders.get(i).getCreateAt();
            String createBy = orders.get(i).getCreateBy();
            String boughtBy = orders.get(i).getBoughtBy();
            int cost = orders.get(i).getSumCost();
            this.totalCost += cost;

            tableModel.addRow(new String[] { id, createAt, createBy, boughtBy, "" + cost });
        }
        tableModel.fireTableDataChanged();
        textFieldTotalCost.setText("" + totalCost);
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

        jPanel1 = new javax.swing.JPanel();
        btnViewOrders = new javax.swing.JButton();
        btnCreateNewOrder = new javax.swing.JButton();
        radioWeek = new javax.swing.JRadioButton();
        radioMonth = new javax.swing.JRadioButton();
        radioFromToDate = new javax.swing.JRadioButton();
        comboBoxWeek = new javax.swing.JComboBox<>();
        comboBoxMonthOfWeek = new com.toedter.calendar.JMonthChooser();
        comboBoxMonth = new com.toedter.calendar.JMonthChooser();
        yearChooserOfWeek = new com.toedter.calendar.JYearChooser();
        yearChooser = new com.toedter.calendar.JYearChooser();
        labelFromDate = new javax.swing.JLabel();
        calendarFromDate = new com.toedter.calendar.JCalendar();
        labelToDate = new javax.swing.JLabel();
        calendarToDate = new com.toedter.calendar.JCalendar();
        btnView = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        textFieldTotalCost = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableViewOrders = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setPreferredSize(new java.awt.Dimension(200, 690));

        btnViewOrders.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnViewOrders.setText("View Orders");
        btnViewOrders.setPreferredSize(new java.awt.Dimension(198, 40));
        btnViewOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewOrdersActionPerformed(evt);
            }
        });

        btnCreateNewOrder.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnCreateNewOrder.setText("Create a new order");
        btnCreateNewOrder.setPreferredSize(new java.awt.Dimension(198, 40));
        btnCreateNewOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateNewOrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnViewOrders, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnCreateNewOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnViewOrders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCreateNewOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        radioWeek.setText("A week");
        radioWeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioWeekActionPerformed(evt);
            }
        });

        radioMonth.setText("A month");
        radioMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioMonthActionPerformed(evt);
            }
        });

        radioFromToDate.setText("Date");
        radioFromToDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioFromToDateActionPerformed(evt);
            }
        });

        comboBoxWeek.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Week 1", "Week 2", "Week 3", "Week 4", "Week 5" }));
        comboBoxWeek.setPreferredSize(new java.awt.Dimension(155, 35));
        comboBoxWeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxWeekActionPerformed(evt);
            }
        });

        comboBoxMonth.setPreferredSize(new java.awt.Dimension(120, 35));

        labelFromDate.setText("From:");

        labelToDate.setText("to:");

        btnView.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnView.setText("View");
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });

        btnBack.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel1.setText("Total cost: ");

        textFieldTotalCost.setEditable(false);
        textFieldTotalCost.setFont(new java.awt.Font("Arial", 0, 17)); // NOI18N
        textFieldTotalCost.setMargin(new java.awt.Insets(2, 10, 2, 6));
        textFieldTotalCost.setPreferredSize(new java.awt.Dimension(96, 40));

        tableViewOrders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableViewOrders.setRowHeight(35);
        tableViewOrders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableViewOrdersMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableViewOrders);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(radioWeek)
                            .addComponent(radioFromToDate, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(radioMonth))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(calendarFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelToDate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(calendarToDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(comboBoxWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(comboBoxMonthOfWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(comboBoxMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(yearChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(yearChooserOfWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(textFieldTotalCost, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 935, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(radioWeek)
                        .addComponent(comboBoxWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(comboBoxMonthOfWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yearChooserOfWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(radioMonth))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(yearChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboBoxMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(calendarToDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelToDate)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(radioFromToDate)
                        .addComponent(labelFromDate))
                    .addComponent(calendarFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textFieldTotalCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    private void tableViewOrdersMouseClicked(java.awt.event.MouseEvent evt) {                                             
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            evt.consume();

            int selectedRow = tableViewOrders.getSelectedRow();
            String idOrder = tableViewOrders.getValueAt(selectedRow, 0).toString();
              
            this.dispose();
            ViewOrderDetailsFrame viewOrderDetails = new ViewOrderDetailsFrame(username, idOrder);
            viewOrderDetails.setVisible(true);
        }
    }                                            

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
        UserControl userControl = new UserControl(username);
        userControl.setVisible(true);
        this.setVisible(false);
    }                                       

    private void radioWeekActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_radioWeekActionPerformed
        // TODO add your handling code here:
        this.typeChooser = "week";
        comboBoxWeek.setEnabled(true);
        comboBoxMonthOfWeek.setEnabled(true);
        yearChooserOfWeek.setEnabled(true);

        // Set states of others comboBox
        comboBoxMonth.setEnabled(false);
        yearChooser.setEnabled(false);
        labelFromDate.setVisible(false);
        labelToDate.setVisible(false);
        calendarFromDate.setVisible(false);
        calendarToDate.setVisible(false);
    }// GEN-LAST:event_radioWeekActionPerformed

    private void comboBoxWeekActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_comboBoxWeekActionPerformed
        // TODO add your handling code here:

    }// GEN-LAST:event_comboBoxWeekActionPerformed

    private void radioMonthActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_radioMonthActionPerformed
        // TODO add your handling code here:
        this.typeChooser = "month";
        comboBoxMonth.setEnabled(true);
        yearChooser.setEnabled(true);

        // Set states of others comboBox
        comboBoxWeek.setEnabled(false);
        comboBoxMonthOfWeek.setEnabled(false);
        yearChooserOfWeek.setEnabled(false);
        labelFromDate.setVisible(false);
        labelToDate.setVisible(false);
        calendarFromDate.setVisible(false);
        calendarToDate.setVisible(false);

    }// GEN-LAST:event_radioMonthActionPerformed

    private void radioFromToDateActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_radioFromToDateActionPerformed
        // TODO add your handling code here:
        this.typeChooser = "fromDatetoDate";
        labelFromDate.setVisible(true);
        labelToDate.setVisible(true);
        calendarFromDate.setVisible(true);
        calendarToDate.setVisible(true);

        // Set states of others comboBox
        comboBoxWeek.setEnabled(false);
        comboBoxMonthOfWeek.setEnabled(false);
        yearChooserOfWeek.setEnabled(false);
        comboBoxMonth.setEnabled(false);
        yearChooser.setEnabled(false);
    }// GEN-LAST:event_radioFromToDateActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnViewActionPerformed
        // TODO add your handling code here:
        List<OrdersPOJO> orders = null;
        if (this.typeChooser.equals("week")) {
            ArrayList<String> date = createFromToDate();
            String fromDate = date.get(0);
            String toDate = date.get(1);

            orders = getOrdersByFromToDate(fromDate, toDate);
            fillTableViewOrders(orders);
        } else if (this.typeChooser.equals("month")) {
            orders = getOrdersByMonth();
            fillTableViewOrders(orders);
        } else if (this.typeChooser.equals("fromDatetoDate")) {
            DateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
            String fromDate = date_format.format(calendarFromDate.getCalendar().getTime());
            String toDate = date_format.format(calendarToDate.getCalendar().getTime().getTime());

            orders = getOrdersByFromToDate(fromDate, toDate);
            fillTableViewOrders(orders);
        }
    }// GEN-LAST:event_btnViewActionPerformed

    private void btnViewOrdersActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnViewOrdersActionPerformed
        // TODO add your handling code here:
        btnViewOrders.setBackground(Color.yellow);
        btnCreateNewOrder.setBackground(Color.white);
    }// GEN-LAST:event_btnViewOrdersActionPerformed

    private void btnCreateNewOrderActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCreateNewOrderActionPerformed
        // TODO add your handling code here:
        btnViewOrders.setBackground(Color.white);
        btnCreateNewOrder.setBackground(Color.yellow);

        this.dispose();
        CreateOrderFrame createOrder = new CreateOrderFrame(username);
        createOrder.setVisible(true);
    }// GEN-LAST:event_btnCreateNewOrderActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
    }// GEN-LAST:event_formWindowClosed

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCreateNewOrder;
    private javax.swing.JButton btnView;
    private javax.swing.JButton btnViewOrders;
    private com.toedter.calendar.JCalendar calendarFromDate;
    private com.toedter.calendar.JCalendar calendarToDate;
    private com.toedter.calendar.JMonthChooser comboBoxMonth;
    private com.toedter.calendar.JMonthChooser comboBoxMonthOfWeek;
    private javax.swing.JComboBox<String> comboBoxWeek;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel labelFromDate;
    private javax.swing.JLabel labelToDate;
    private javax.swing.JRadioButton radioFromToDate;
    private javax.swing.JRadioButton radioMonth;
    private javax.swing.JRadioButton radioWeek;
    private javax.swing.JTable tableViewOrders;
    private javax.swing.JTextField textFieldTotalCost;
    private com.toedter.calendar.JYearChooser yearChooser;
    private com.toedter.calendar.JYearChooser yearChooserOfWeek;
    // End of variables declaration                   
}

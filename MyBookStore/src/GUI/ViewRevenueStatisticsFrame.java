/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import BUS.StatisticsBUS;
import POJO.StatisticsPOJO;
import java.util.Date;
import com.toedter.calendar.*;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bachl
 */
public class ViewRevenueStatisticsFrame extends javax.swing.JFrame {
    private String typeChooser;
    private String typeTimeRange;
    private int totalRevenue;
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

    /**
     * Creates new form ViewRevenueStatisticsFrame
     */
    public ViewRevenueStatisticsFrame(String username) {
        setUsername(username);
        initComponents();
        typeChooser = "";
        this.typeTimeRange = "";
        totalRevenue = 0;

        initDefaultComboBoxTimeRange();
        initTable();
        initGroupTimeRange();

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
        String[] cols = new String[] { "ID", "Name", "Quantity", "Revenue" };
        tableModel.setColumnIdentifiers(cols);
        tableModel.setRowCount(0);

        tableViewStatistics.setModel(tableModel);
    }

    private void initGroupTimeRange() {
        ButtonGroup groupTimeRange = new ButtonGroup();
        groupTimeRange.add(radioWeek);
        groupTimeRange.add(radioMonth);
        groupTimeRange.add(radioFromToDate);
    }

    private void fillTableViewStatistics(List<StatisticsPOJO> statistics) {
        totalRevenue = 0;
        tableModel.setRowCount(0);

        for (int i = 0; i < statistics.size(); i++) {
            String id = statistics.get(i).getId();
            String name = statistics.get(i).getName();
            int quantity = statistics.get(i).getQuantity();
            int revenue = statistics.get(i).getRevenue();

            this.totalRevenue += revenue;

            tableModel.addRow(new String[] { id, name, "" + quantity, "" + revenue });
        }
        tableModel.fireTableDataChanged();
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

    private List<StatisticsPOJO> getStatisticsByFromDateToDate() {
        String fromDate = null;
        String toDate = null;

        if (this.typeTimeRange.equals("week")) {
            ArrayList<String> date = createFromToDate();
            fromDate = date.get(0);
            toDate = date.get(1);
        } else if (this.typeTimeRange.equals("fromDatetoDate")) {
            DateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
            fromDate = date_format.format(calendarFromDate.getCalendar().getTime());
            toDate = date_format.format(calendarToDate.getCalendar().getTime().getTime());
        }

        StatisticsBUS bus = new StatisticsBUS();
        if (this.typeChooser.equals("book"))
            return bus.getGroupedByBookFromDateToDate(fromDate, toDate);
        else if (this.typeChooser.equals("category"))
            return bus.getGroupedByCategoryFromDateToDate(fromDate, toDate);
        else if (this.typeChooser.equals("customer"))
            return bus.getGroupedByCustomerFromDateToDate(fromDate, toDate);
        return bus.getGroupedByEmployeeFromDateToDate(fromDate, toDate);
    }

    private List<StatisticsPOJO> getStatisticsByMonth() {
        int month = comboBoxMonth.getMonth() + 1;
        int year = yearChooser.getYear();

        StatisticsBUS bus = new StatisticsBUS();
        if (this.typeChooser.equals("book"))
            return bus.getGroupedByBookForMonth(month, year);
        else if (this.typeChooser.equals("category"))
            return bus.getGroupedByCategoryForMonth(month, year);
        else if (this.typeChooser.equals("customer"))
            return bus.getGroupedByCustomerForMonth(month, year);

        return bus.getGroupedByEmployeeForMonth(month, year);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnGroupedByBook = new javax.swing.JButton();
        btnGroupedByCategory = new javax.swing.JButton();
        btnGroupedByCustomer = new javax.swing.JButton();
        btnGroupedByEmployee = new javax.swing.JButton();
        btnGroupedByPromotion = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        radioWeek = new javax.swing.JRadioButton();
        radioMonth = new javax.swing.JRadioButton();
        radioFromToDate = new javax.swing.JRadioButton();
        comboBoxWeek = new javax.swing.JComboBox<>();
        labelFromDate = new javax.swing.JLabel();
        calendarFromDate = new com.toedter.calendar.JCalendar();
        labelToDate = new javax.swing.JLabel();
        calendarToDate = new com.toedter.calendar.JCalendar();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableViewStatistics = new javax.swing.JTable();
        btnViewStatistic = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        textFieldTotalRevenue = new javax.swing.JTextField();
        panelImage = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        comboBoxMonthOfWeek = new com.toedter.calendar.JMonthChooser();
        yearChooser = new com.toedter.calendar.JYearChooser();
        yearChooserOfWeek = new com.toedter.calendar.JYearChooser();
        comboBoxMonth = new com.toedter.calendar.JMonthChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Revenue statistics");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setPreferredSize(new java.awt.Dimension(200, 700));

        btnGroupedByBook.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnGroupedByBook.setText("Grouped by Book");
        btnGroupedByBook.setMargin(new java.awt.Insets(2, 5, 2, 14));
        btnGroupedByBook.setPreferredSize(new java.awt.Dimension(198, 40));
        btnGroupedByBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGroupedByBookActionPerformed(evt);
            }
        });

        btnGroupedByCategory.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnGroupedByCategory.setText("Grouped by Category");
        btnGroupedByCategory.setMargin(new java.awt.Insets(2, 5, 2, 14));
        btnGroupedByCategory.setPreferredSize(new java.awt.Dimension(198, 40));
        btnGroupedByCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGroupedByCategoryActionPerformed(evt);
            }
        });

        btnGroupedByCustomer.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnGroupedByCustomer.setText("Grouped by Customer");
        btnGroupedByCustomer.setMargin(new java.awt.Insets(2, 5, 2, 14));
        btnGroupedByCustomer.setPreferredSize(new java.awt.Dimension(198, 40));
        btnGroupedByCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGroupedByCustomerActionPerformed(evt);
            }
        });

        btnGroupedByEmployee.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnGroupedByEmployee.setText("Grouped by Employee");
        btnGroupedByEmployee.setMargin(new java.awt.Insets(2, 5, 2, 14));
        btnGroupedByEmployee.setPreferredSize(new java.awt.Dimension(198, 40));
        btnGroupedByEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGroupedByEmployeeActionPerformed(evt);
            }
        });

        btnGroupedByPromotion.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnGroupedByPromotion.setText("Grouped by Promotion");
        btnGroupedByPromotion.setMargin(new java.awt.Insets(2, 5, 2, 14));
        btnGroupedByPromotion.setPreferredSize(new java.awt.Dimension(198, 40));
        btnGroupedByPromotion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGroupedByPromotionActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton1.setText("Back");
        jButton1.setPreferredSize(new java.awt.Dimension(198, 40));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnGroupedByBook, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                        .addComponent(btnGroupedByCategory, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGroupedByCustomer, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGroupedByEmployee, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGroupedByPromotion, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnGroupedByBook, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGroupedByCategory, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGroupedByCustomer, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGroupedByEmployee, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGroupedByPromotion, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)));

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

        comboBoxWeek.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[] { "Week 1", "Week 2", "Week 3", "Week 4", "Week 5" }));
        comboBoxWeek.setName(""); // NOI18N
        comboBoxWeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxWeekActionPerformed(evt);
            }
        });

        labelFromDate.setText("From: ");

        labelToDate.setText("to: ");

        tableViewStatistics.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        tableViewStatistics.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null }
                },
                new String[] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }) {
            boolean[] canEdit = new boolean[] {
                    false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tableViewStatistics.setRowHeight(35);
        jScrollPane1.setViewportView(tableViewStatistics);

        btnViewStatistic.setBackground(new java.awt.Color(255, 153, 51));
        btnViewStatistic.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnViewStatistic.setText("View");
        btnViewStatistic.setPreferredSize(new java.awt.Dimension(115, 40));
        btnViewStatistic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewStatisticActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel3.setText("Total Revenue: ");

        textFieldTotalRevenue.setEditable(false);
        textFieldTotalRevenue.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        textFieldTotalRevenue.setMargin(new java.awt.Insets(2, 12, 2, 6));
        textFieldTotalRevenue.setMinimumSize(new java.awt.Dimension(96, 96));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/statistic_2.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/statistic_1.png"))); // NOI18N

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/statistic_3.png"))); // NOI18N

        javax.swing.GroupLayout panelImageLayout = new javax.swing.GroupLayout(panelImage);
        panelImage.setLayout(panelImageLayout);
        panelImageLayout.setHorizontalGroup(
                panelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelImageLayout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        panelImageLayout.setVerticalGroup(
                panelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImageLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panelImageLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelImageLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel4))
                                        .addGroup(panelImageLayout.createSequentialGroup()
                                                .addGap(2, 2, 2)
                                                .addComponent(jLabel5)))
                                .addGap(45, 45, 45)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 220,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(16, 16, 16)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(jScrollPane1,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 935,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel3)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(textFieldTotalRevenue,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        Short.MAX_VALUE)
                                                                .addComponent(btnViewStatistic,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(radioWeek, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(radioMonth,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 115,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(radioFromToDate,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 115,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(comboBoxMonth,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        149,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(
                                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(yearChooser,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(comboBoxWeek,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        150,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(
                                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(comboBoxMonthOfWeek,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        150,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(
                                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(yearChooserOfWeek,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        97,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        Short.MAX_VALUE)
                                                                .addComponent(panelImage,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(6, 6, 6)
                                                                .addComponent(labelFromDate)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(calendarFromDate,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(labelToDate)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(calendarToDate,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, Short.MAX_VALUE)))))
                                .addGap(95, 95, 95)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(panelImage, javax.swing.GroupLayout.PREFERRED_SIZE, 119,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(27, 27, 27)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(radioWeek)
                                                                .addComponent(comboBoxWeek,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(comboBoxMonthOfWeek,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(yearChooserOfWeek,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(24, 24, 24)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(yearChooser,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(comboBoxMonth,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(radioMonth,
                                                                javax.swing.GroupLayout.Alignment.LEADING))))
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(calendarFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(labelFromDate)
                                                .addComponent(radioFromToDate))
                                        .addComponent(labelToDate)
                                        .addComponent(calendarToDate, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(textFieldTotalRevenue, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnViewStatistic, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(75, Short.MAX_VALUE))
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE));

        pack();
    }// </editor-fold>

    private void comboBoxWeekActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void btnGroupedByBookActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        this.typeChooser = "book";
        tableModel.setRowCount(0);
        textFieldTotalRevenue.setText("");
        btnGroupedByBook.setBackground(Color.yellow);

        // Set default background color for the left buttons
        btnGroupedByCategory.setBackground(Color.white);
        btnGroupedByCustomer.setBackground(Color.white);
        btnGroupedByEmployee.setBackground(Color.white);
        btnGroupedByPromotion.setBackground(Color.white);
    }

    private void btnViewStatisticActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        List<StatisticsPOJO> statistics = null;

        if (this.typeTimeRange.equals("week") || this.typeTimeRange.equals("fromDatetoDate")) {
            statistics = getStatisticsByFromDateToDate();
        } else if (this.typeTimeRange.equals("month")) {
            statistics = getStatisticsByMonth();
        }

        if (statistics != null) {
            fillTableViewStatistics(statistics);
            textFieldTotalRevenue.setText("" + this.totalRevenue);
        }
    }

    private void btnGroupedByCategoryActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        this.typeChooser = "category";
        tableModel.setRowCount(0);
        textFieldTotalRevenue.setText("");
        btnGroupedByCategory.setBackground(Color.yellow);

        // Set default background color for the left buttons
        btnGroupedByBook.setBackground(Color.white);
        btnGroupedByCustomer.setBackground(Color.white);
        btnGroupedByEmployee.setBackground(Color.white);
        btnGroupedByPromotion.setBackground(Color.white);
    }

    private void btnGroupedByCustomerActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        this.typeChooser = "customer";
        tableModel.setRowCount(0);
        textFieldTotalRevenue.setText("");
        btnGroupedByCustomer.setBackground(Color.yellow);

        // Set default background color for the left buttons
        btnGroupedByBook.setBackground(Color.white);
        btnGroupedByCategory.setBackground(Color.white);
        btnGroupedByEmployee.setBackground(Color.white);
        btnGroupedByPromotion.setBackground(Color.white);
    }

    private void btnGroupedByEmployeeActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        this.typeChooser = "employee";
        tableModel.setRowCount(0);
        textFieldTotalRevenue.setText("");
        btnGroupedByEmployee.setBackground(Color.yellow);

        // Set default background color for the left buttons
        btnGroupedByBook.setBackground(Color.white);
        btnGroupedByCategory.setBackground(Color.white);
        btnGroupedByCustomer.setBackground(Color.white);
        btnGroupedByPromotion.setBackground(Color.white);
    }

    private void btnGroupedByPromotionActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        this.typeChooser = "promotion";
        tableModel.setRowCount(0);
        textFieldTotalRevenue.setText("");
        btnGroupedByPromotion.setBackground(Color.yellow);

        // Set default background color for the left buttons
        btnGroupedByBook.setBackground(Color.white);
        btnGroupedByCategory.setBackground(Color.white);
        btnGroupedByCustomer.setBackground(Color.white);
        btnGroupedByEmployee.setBackground(Color.white);

        this.dispose();
        ViewDetailStatisticPromotionFrame viewPromotion = new ViewDetailStatisticPromotionFrame(username);
        viewPromotion.setVisible(true);
    }

    private void radioWeekActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        this.typeTimeRange = "week";
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
    }

    private void radioMonthActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        this.typeTimeRange = "month";

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
    }

    private void radioFromToDateActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        this.typeTimeRange = "fromDatetoDate";

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
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        AdminControllerGUI adminControllerGUI = new AdminControllerGUI(username);
        adminControllerGUI.setVisible(true);
        System.out.print(username);
        setVisible(false);
    }

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
            java.util.logging.Logger.getLogger(ViewRevenueStatisticsFrame.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewRevenueStatisticsFrame.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewRevenueStatisticsFrame.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewRevenueStatisticsFrame.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        // java.awt.EventQueue.invokeLater(new Runnable() {
        // public void run() {
        // new ViewRevenueStatisticsFrame().setVisible(true);
        // }
        // });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton btnGroupedByBook;
    private javax.swing.JButton btnGroupedByCategory;
    private javax.swing.JButton btnGroupedByCustomer;
    private javax.swing.JButton btnGroupedByEmployee;
    private javax.swing.JButton btnGroupedByPromotion;
    private javax.swing.JButton btnViewStatistic;
    private com.toedter.calendar.JCalendar calendarFromDate;
    private com.toedter.calendar.JCalendar calendarToDate;
    private com.toedter.calendar.JMonthChooser comboBoxMonth;
    private com.toedter.calendar.JMonthChooser comboBoxMonthOfWeek;
    private javax.swing.JComboBox<String> comboBoxWeek;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelFromDate;
    private javax.swing.JLabel labelToDate;
    private javax.swing.JPanel panelImage;
    private javax.swing.JRadioButton radioFromToDate;
    private javax.swing.JRadioButton radioMonth;
    private javax.swing.JRadioButton radioWeek;
    private javax.swing.JTable tableViewStatistics;
    private javax.swing.JTextField textFieldTotalRevenue;
    private com.toedter.calendar.JYearChooser yearChooser;
    private com.toedter.calendar.JYearChooser yearChooserOfWeek;
    // End of variables declaration

    private Color Color(int i, int i0, int i1) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

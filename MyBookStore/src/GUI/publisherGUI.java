package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.mysql.cj.callback.UsernameCallback;

import BUS.PublisherBUS;
import POJO.PublisherPOJO;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;

public class publisherGUI extends JFrame {

    private JPanel contentPane;
    private JTextField searchIDInput;
    private JTextField searchNameInput;
    private JTable listPublisherTable;
    private JTextField manageIDInput;
    private JTextField manageNameInput;
    private JTextField manageAddressInput;
    private JTextField managePhoneInput;
    String username;

    public void closeFrame() {
        this.setVisible(false);
        this.dispose();
    }

    public void showPublisher() {
        DefaultTableModel model = (DefaultTableModel) listPublisherTable.getModel();
        model.setRowCount(0);
        model.fireTableDataChanged();
        ArrayList<PublisherPOJO> listPublisher = new ArrayList<>();
        PublisherBUS publisherBU = new PublisherBUS();
        listPublisher = publisherBU.getAllPublisher();
        for (PublisherPOJO PublisherPOJO : listPublisher) {
            String id = PublisherPOJO.getId();
            String name = PublisherPOJO.getName();
            String address = PublisherPOJO.getAddress();
            String phone = PublisherPOJO.getPhone();
            boolean disable = PublisherPOJO.isDisable();
            String disableText;
            if (disable) {
                disableText = "1";
            } else {
                disableText = "0";
            }
            String[] row = { id, name, address, phone, disableText };
            model.addRow(row);
        }
    }

    public void showPublisherSearch(String _id, String _name) {
        DefaultTableModel model = (DefaultTableModel) listPublisherTable.getModel();
        model.setRowCount(0);
        model.fireTableDataChanged();
        ArrayList<PublisherPOJO> listPublisher = new ArrayList<>();
        PublisherBUS publisherBU = new PublisherBUS();
        listPublisher = publisherBU.getPublisherBySearch(_id, _name);
        if (listPublisher == null || listPublisher.size() == 0) {
            JOptionPane.showMessageDialog(null, "Publisher not found");
        } else {
            for (PublisherPOJO PublisherPOJO : listPublisher) {
                String id = PublisherPOJO.getId();
                String name = PublisherPOJO.getName();
                String address = PublisherPOJO.getAddress();
                String phone = PublisherPOJO.getPhone();
                boolean disable = PublisherPOJO.isDisable();
                String disableText;
                if (disable) {
                    disableText = "1";
                } else {
                    disableText = "0";
                }
                String[] row = { id, name, address, phone, disableText };
                model.addRow(row);
            }
        }
    }

    public PublisherPOJO getPublisherSelected() {
        DefaultTableModel model = (DefaultTableModel) listPublisherTable.getModel();
        int i_row = listPublisherTable.getSelectedRow();
        String id = (String) model.getValueAt(i_row, 0);
        String name = (String) model.getValueAt(i_row, 1);
        String address = (String) model.getValueAt(i_row, 2);
        String phone = (String) model.getValueAt(i_row, 3);
        PublisherPOJO publisher = new PublisherPOJO(id, name, address, phone);
        return publisher;
    }

    public void showPublisherSelected(java.awt.event.MouseEvent evt) {
        PublisherPOJO publisher = getPublisherSelected();
        this.manageIDInput.setText(publisher.getId());
        this.manageNameInput.setText(publisher.getName());
        this.manageAddressInput.setText(publisher.getAddress());
        this.managePhoneInput.setText(publisher.getPhone());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Create the frame.
     */
    public publisherGUI(String username) {
        setUsername(username);
        setResizable(false);
        setTitle("Bookstore Management - Employee");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 978, 704);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        // JMenuBar menuBar = new JMenuBar();
        // menuBar.setBounds(10, 10, 101, 22);
        // contentPane.add(menuBar);

        // JMenu jmenuFile = new JMenu("File");
        // menuBar.add(jmenuFile);

        // JMenuItem jmenuItemNew = new JMenuItem("New");
        // jmenuFile.add(jmenuItemNew);

        // JMenuItem jmenuItemOpen = new JMenuItem("Open");
        // jmenuFile.add(jmenuItemOpen);

        // JSeparator fileMenuSeperator = new JSeparator();
        // jmenuFile.add(fileMenuSeperator);

        // JMenuItem jmenuItemExit = new JMenuItem("Exit");
        // jmenuFile.add(jmenuItemExit);

        // JMenu jmenuAbout = new JMenu("About");
        // menuBar.add(jmenuAbout);

        JButton backButton = new JButton("Back");
        backButton.setBounds(10, 10, 101, 22);
        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                closeFrame();
                UserControl userControl = new UserControl(username);
                userControl.setVisible(true);
            }

        });
        contentPane.add(backButton);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(10, 30, 926, 2);
        contentPane.add(separator_1);

        JPanel sidebarPane = new JPanel();
        sidebarPane.setBounds(10, 42, 173, 612);
        contentPane.add(sidebarPane);
        sidebarPane.setLayout(null);

        JButton publisherManageBtn = new JButton("Display publisher");
        publisherManageBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                showPublisher();
            }

        });
        publisherManageBtn.setBounds(0, 61, 173, 41);
        sidebarPane.add(publisherManageBtn);

        JSeparator separator_2 = new JSeparator();
        separator_2.setOrientation(SwingConstants.VERTICAL);
        separator_2.setBounds(193, 30, 2, 624);
        contentPane.add(separator_2);

        JPanel managePublisherPane = new JPanel();
        managePublisherPane.setBounds(205, 42, 741, 612);
        contentPane.add(managePublisherPane);
        managePublisherPane.setLayout(null);

        JPanel headingControlPane = new JPanel();
        headingControlPane.setBounds(0, 0, 731, 67);
        managePublisherPane.add(headingControlPane);
        headingControlPane.setLayout(new GridLayout(0, 1, 2, 5));

        JPanel searchControlPane = new JPanel();
        headingControlPane.add(searchControlPane);
        searchControlPane.setLayout(null);

        JLabel searchIdLabel = new JLabel("id");
        searchIdLabel.setBounds(10, 9, 49, 13);
        searchControlPane.add(searchIdLabel);

        searchIDInput = new JTextField();
        searchIDInput.setBounds(69, 6, 96, 19);
        searchControlPane.add(searchIDInput);
        searchIDInput.setColumns(10);

        JLabel searchNameLabel = new JLabel("name");
        searchNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        searchNameLabel.setBounds(175, 9, 53, 13);
        searchControlPane.add(searchNameLabel);

        searchNameInput = new JTextField();
        searchNameInput.setBounds(238, 6, 96, 19);
        searchControlPane.add(searchNameInput);
        searchNameInput.setColumns(10);

        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String IdFind = searchIDInput.getText();
                String nameFind = searchNameInput.getText();
                showPublisherSearch(IdFind, nameFind);
            }

        });
        searchBtn.setBounds(613, 5, 87, 21);
        searchControlPane.add(searchBtn);

        JPanel sortControlPane = new JPanel();
        headingControlPane.add(sortControlPane);
        sortControlPane.setLayout(null);

        // JLabel sortLabel = new JLabel("Sort by");
        // sortLabel.setBounds(10, 10, 45, 13);
        // sortControlPane.add(sortLabel);

        // JComboBox sortCombobox = new JComboBox();
        // sortCombobox.addItem("");
        // sortCombobox.addItem("ID");
        // sortCombobox.addItem("Name");
        // sortCombobox.addItem("Address");
        // sortCombobox.addItem("Phone");
        // sortCombobox.setBounds(65, 6, 105, 21);
        // sortControlPane.add(sortCombobox);

        JSeparator separator_3 = new JSeparator();
        separator_3.setBounds(0, 70, 731, 2);
        managePublisherPane.add(separator_3);

        JScrollPane listPublisherPane = new JScrollPane();
        listPublisherPane.setBounds(0, 93, 731, 205);
        managePublisherPane.add(listPublisherPane);

        String[] columnNames = { "ID", "Name", "Address", "Phone", "Disable" };
        listPublisherTable = new JTable() {
            @Override
            public java.awt.Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                java.awt.Component comp = super.prepareRenderer(renderer, row, col);
                Object value = getModel().getValueAt(row, 4);
                if (value.equals("1")) {
                    comp.setBackground(Color.RED);
                } else {
                    comp.setBackground(Color.WHITE);
                }
                return comp;
            }
        };
        listPublisherTable.setModel(new DefaultTableModel(new Object[][] {}, columnNames));
        listPublisherTable.setAutoCreateRowSorter(true);
        listPublisherTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showPublisherSelected(evt);
            }
        });
        listPublisherPane.setViewportView(listPublisherTable);

        JScrollBar scrollBar = new JScrollBar();
        listPublisherPane.setRowHeaderView(scrollBar);

        JLabel listPublisherLabel = new JLabel("List Publisher");
        listPublisherLabel.setBounds(37, 70, 127, 18);
        managePublisherPane.add(listPublisherLabel);

        JPanel manageControlPane = new JPanel();
        manageControlPane.setBounds(0, 369, 731, 243);
        managePublisherPane.add(manageControlPane);
        manageControlPane.setLayout(null);

        JLabel manageIDLabel = new JLabel("ID");
        manageIDLabel.setBounds(17, 33, 45, 13);
        manageControlPane.add(manageIDLabel);

        manageIDInput = new JTextField();
        manageIDInput.setBounds(72, 20, 178, 39);
        manageControlPane.add(manageIDInput);
        manageIDInput.setColumns(10);

        JLabel manageNameLabel = new JLabel("Name");
        manageNameLabel.setBounds(17, 82, 45, 13);
        manageControlPane.add(manageNameLabel);

        manageNameInput = new JTextField();
        manageNameInput.setBounds(72, 69, 178, 39);
        manageControlPane.add(manageNameInput);
        manageNameInput.setColumns(10);

        JLabel manageAddressLabel = new JLabel("Address");
        manageAddressLabel.setBounds(308, 33, 69, 13);
        manageControlPane.add(manageAddressLabel);

        manageAddressInput = new JTextField();
        manageAddressInput.setBounds(387, 20, 197, 39);
        manageControlPane.add(manageAddressInput);
        manageAddressInput.setColumns(10);

        JLabel managePhoneLabel = new JLabel("Phone");
        managePhoneLabel.setBounds(308, 82, 69, 13);
        manageControlPane.add(managePhoneLabel);

        managePhoneInput = new JTextField();
        managePhoneInput.setBounds(387, 69, 197, 39);
        manageControlPane.add(managePhoneInput);
        managePhoneInput.setColumns(10);

        JButton manageAddBtn = new JButton("Add");
        manageAddBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = manageIDInput.getText();
                String name = manageNameInput.getText();
                String address = manageAddressInput.getText();
                String phone = managePhoneInput.getText();

                PublisherPOJO publisher = new PublisherPOJO(id, name, address, phone);
                PublisherBUS publisherBU = new PublisherBUS();
                boolean res = publisherBU.addPublisher(publisher);
                if (res) {
                    System.out.println("Add publisher successfully");
                    JOptionPane.showMessageDialog(contentPane, "Add publisher successfully");
                    DefaultTableModel model = (DefaultTableModel) listPublisherTable.getModel();
                    model.setRowCount(0);
                    showPublisher();
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Add publisher failed");
                }
            }
        });
        manageAddBtn.setBounds(17, 151, 93, 39);
        manageControlPane.add(manageAddBtn);

        JButton manageUpdateBtn = new JButton("Update");
        manageUpdateBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String id = manageIDInput.getText();
                String name = manageNameInput.getText();
                String address = manageAddressInput.getText();
                String phone = managePhoneInput.getText();
                PublisherPOJO publisher = new PublisherPOJO(id, name, address, phone);
                PublisherBUS publisherBU = new PublisherBUS();
                boolean res = publisherBU.updatePublisher(publisher);
                DefaultTableModel model = (DefaultTableModel) listPublisherTable.getModel();
                model.setRowCount(0);
                showPublisher();
                if (res) {
                    JOptionPane.showMessageDialog(null, "Update publisher successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Update publisher failed");
                }
            }
        });
        manageUpdateBtn.setBounds(131, 151, 93, 39);
        manageControlPane.add(manageUpdateBtn);

        JButton manageEnableBtn = new JButton("Enable");
        manageEnableBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub

                PublisherPOJO publisher = getPublisherSelected();
                PublisherBUS publisherBU = new PublisherBUS();
                boolean res = publisherBU.enablePublisher(publisher);
                showPublisher();
                if (res) {
                    JOptionPane.showMessageDialog(null, "Enable successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Error");
                }

            }

        });

        manageEnableBtn.setBounds(387, 151, 85, 39);
        manageControlPane.add(manageEnableBtn);

        JButton manageDisableBtn = new JButton("Disable");
        manageDisableBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                PublisherPOJO publisher = getPublisherSelected();
                PublisherBUS publisherBU = new PublisherBUS();
                boolean res = publisherBU.disablePublisher(publisher);
                showPublisher();
                if (res) {
                    JOptionPane.showMessageDialog(null, "Disable successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Error");
                }
            }
        });
        manageDisableBtn.setBounds(513, 151, 85, 39);
        manageControlPane.add(manageDisableBtn);

        JButton manageCancelBtn = new JButton("Cancel");
        manageCancelBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                closeFrame();
                UserControl userControl = new UserControl(username);
                userControl.setVisible(true);

            }

        });
        manageCancelBtn.setBounds(628, 151, 93, 39);
        manageControlPane.add(manageCancelBtn);

        JSeparator separator_4 = new JSeparator();
        separator_4.setBounds(0, 298, 731, 2);
        managePublisherPane.add(separator_4);

        JLabel manageLabel = new JLabel("Manage");
        manageLabel.setBounds(37, 335, 45, 13);
        managePublisherPane.add(manageLabel);
    }
}

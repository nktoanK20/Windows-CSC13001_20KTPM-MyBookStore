package GUI;

import BUS.AccountBUS;
import BUS.UserBUS;
import DAO.AccountDAO;
import DAO.UserDAO;
import GUI.AdminManagement.ButtonEditor;
import GUI.AdminManagement.ButtonRenderer;
import POJO.AccountPOJO;
import POJO.UserPOJO;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class AdminManagement extends JPanel implements ActionListener {
    private JPanel menuPane;
    private JButton backBtn;
    JTable accountTable;

    private JButton editInfoButton;
    JButton selectedButton;

    private final int SIDEBARPANE_WIDTH = 180;
    private JPanel sidebarPane;
    private JButton allAccountsButton;
    private JButton allUserButton;
    JScrollPane accountTableScroll;

    private JButton addNewAccount;

    private JPanel contentPane;
    private JLabel contentLabel;
    static JFrame frame;

    private String username;
    private AdminControllerGUI adminControllerGUI;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AdminManagement(String username) {
        super();
        setUsername(username);
        setLayout(new BorderLayout(0, 0));

        menuPane = new JPanel();
        menuPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        menuPane.setBackground(Color.GRAY);

        backBtn = new JButton("Back");
        backBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                adminControllerGUI = new AdminControllerGUI(username);
                adminControllerGUI.setVisible(true);
                hideFrame();
            }

        });
        backBtn.setFocusable(false);
        backBtn.addActionListener(this);
        menuPane.add(backBtn);

        // editInfoButton = new JButton("Information");
        // editInfoButton.setFocusable(false);
        // editInfoButton.addActionListener(this);
        // menuPane.add(editInfoButton);

        sidebarPane = new JPanel();
        sidebarPane.setLayout(new BoxLayout(sidebarPane, BoxLayout.Y_AXIS));
        sidebarPane.setPreferredSize(new Dimension(SIDEBARPANE_WIDTH, 0));
        sidebarPane.setBorder(new LineBorder(Color.BLACK, 1));
        sidebarPane.setBackground(Color.LIGHT_GRAY);

        allAccountsButton = new JButton("All accounts");
        allAccountsButton.setMaximumSize(new Dimension(SIDEBARPANE_WIDTH, 30));
        allAccountsButton.setFocusable(false);
        allAccountsButton.addActionListener(this);

        addNewAccount = new JButton("Add a new account");
        addNewAccount.setMaximumSize(new Dimension(SIDEBARPANE_WIDTH, 30));
        addNewAccount.setFocusable(false);
        addNewAccount.addActionListener(this);

        sidebarPane.add(allAccountsButton);
        sidebarPane.add(addNewAccount);

        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        contentLabel = new JLabel();
        contentLabel.setText("WELCOME TO THE ADMIN PAGE!");
        contentLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        contentLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentLabel.setBounds(221, 41, 212, 31);
        contentPane.add(contentLabel);
        accountTableScroll = new JScrollPane();
        add(menuPane, BorderLayout.NORTH);
        add(sidebarPane, BorderLayout.WEST);
        add(contentPane, BorderLayout.CENTER);
    }

    public JTable getTableAllAccounts() {
        ArrayList<AccountPOJO> accountList = AccountDAO.getAll();
        ArrayList<UserPOJO> userList = UserDAO.getAll();

        JTable table = new JTable();
        String[] col = { "ID", "USERNAME", "PASSWORD", "ID USER", "NAME", "ADDRESS", "ROLE", "STATUS", "ACTION",
                "EDIT", "RESET_PASSWORD" };
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);

        if (accountList != null) {
            for (AccountPOJO account : accountList) {
                String id = account.getId();
                String username = account.getUsername();
                String password = account.getPassword();
                Boolean isActive = account.getIsActive();

                String enabled = !isActive ? "Enable" : "Disable";
                String status = isActive ? "Active" : "Inactive";
                for (UserPOJO user : userList) {
                    if (id.equals(user.getIdAccount())) {
                        String idUser = user.getId();
                        String name = user.getName();
                        String address = user.getAddress();
                        String role = user.getRole() == 1 ? "Admin" : "User";
                        Object[] data = { id, username, password, idUser, name, address, role, status, enabled,
                                "Edit", "Reset Password" };
                        tableModel.addRow(data);
                        break;
                    }
                }
            }
        }
        table.setModel(tableModel);
        table.setAutoCreateRowSorter(true);
        table.getColumn("ACTION").setCellRenderer(new ButtonRenderer());
        table.getColumn("ACTION").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.getColumn("EDIT").setCellRenderer(new ButtonRenderer());
        table.getColumn("EDIT").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.getColumn("RESET_PASSWORD").setCellRenderer(new ButtonRenderer());
        table.getColumn("RESET_PASSWORD").setCellEditor(new ButtonEditor(new JCheckBox()));
        return table;
    }

    public void hideFrame() {
        frame.setVisible(false);
    }

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
                String userId = accountTable.getValueAt(row, 3).toString();
                String usernameGet = accountTable.getValueAt(row, 1).toString();
                if (Objects.equals(type, "Enable") || Objects.equals(type, "Disable")) {
                    AccountPOJO accEN = getAccount(usernameGet);
                    AccountBUS accountBUS = new AccountBUS();
                    accountBUS.updateActive(accEN);
                    refreshAccountTable();
                } else if (Objects.equals(type, "Reset Password")) {
                    new ResetPassFormPanel(usernameGet);
                    refreshAccountTable();
                } else if (Objects.equals(type, "Edit")) {
                    new InfoFormPanel(userId);
                    refreshAccountTable();
                } else {
                    System.out.println("Unknown action: " + accountTable.getValueAt(row, 0) + " - " + type);
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

    public void refreshAccountTable() {
        contentLabel.setText(selectedButton.getText() + " list");
        contentPane.add(contentLabel);
        accountTable = getTableAllAccounts();
        accountTableScroll.setViewportView(accountTable);
        contentPane.add(accountTableScroll);
        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        selectedButton = (JButton) e.getSource();
        if (selectedButton == allAccountsButton) {
            contentPane.removeAll();
            refreshAccountTable();
        } else if (selectedButton == addNewAccount) {
            new CreateNewAccountUserFrame();
        }
        revalidate();
        repaint();
    }

    public AccountPOJO getAccount(String usernameIn) {
        ArrayList<AccountPOJO> accountList = AccountBUS.getAll();
        for (AccountPOJO a : accountList) {
            if (a != null && a.getUsername().equals(usernameIn)) {
                return a;
            }
        }
        return null;
    }

    public void createAndShowGUI() {
        // Create and set up the window.
        frame = new JFrame("Admin - Book Management");
        frame.setPreferredSize(new Dimension(1200, 600));
        frame.setLayout(new BorderLayout(0, 0));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane.
        AdminManagement newContentPane = new AdminManagement(username);
        newContentPane.setOpaque(true); // content panes must be opaque
        frame.setContentPane(newContentPane);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    // public static void main(String[] args) {
    // // Schedule a job for the event-dispatching thread:
    // // creating and showing this application's GUI.
    // javax.swing.SwingUtilities.invokeLater(new Runnable() {
    // public void run() {
    // AdminManagement adminManagement = new AdminManagement(username);
    // adminManagement.createAndShowGUI();
    // }
    // });
    // }

    class InfoFormPanel extends JDialog implements ActionListener {
        JLabel headLabel;

        JLabel idLabel;
        JLabel idField;

        JLabel nameLabel;
        JTextField nameField;

        JLabel addressLabel;
        JTextField addressField;

        JLabel idAccountLabel;
        JLabel idAccountField;

        JLabel roleLabel;
        JTextField roleField;

        JButton clearButton;
        JButton addButton;

        GridBagConstraints gbc;
        UserPOJO user;

        InfoFormPanel(String userId) {
            user = getUser(userId);
            this.setSize(700, 300);
            this.setLocationRelativeTo(null);
            this.setResizable(true);
            setLayout(new GridBagLayout());
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.initComponent();
            this.setVisible(true);
        }

        public UserPOJO getUser(String userId) {
            ArrayList<UserPOJO> userList = UserBUS.getAll();
            for (UserPOJO userI : userList) {
                if (userI.getId().equals(userId)) {
                    return userI;
                }
            }
            return null;
        }

        public void initComponent() {
            headLabel = new JLabel("User's information");
            headLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
            headLabel.setHorizontalAlignment(SwingConstants.CENTER);
            headLabel.setBounds(221, 41, 212, 31);

            idLabel = new JLabel("ID:");
            idField = new JLabel(user.getId());

            nameLabel = new JLabel("Name:");
            nameField = new JTextField(user.getName());

            addressLabel = new JLabel("Address:");
            addressField = new JTextField(user.getAddress());

            idAccountLabel = new JLabel("ID Account:");
            idAccountField = new JLabel(user.getIdAccount());
            roleLabel = new JLabel("Role: ");
            String roleString = user.getRole() == 1 ? "Admin" : "User";
            roleField = new JTextField(roleString);

            clearButton = new JButton("Clear");
            clearButton.addActionListener(this);

            addButton = new JButton("Update");
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
            add(addressLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(roleLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(idAccountLabel, gbc);

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
            add(addressField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(roleField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(idAccountField, gbc);

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
                nameField.setText("");
                addressField.setText("");
                roleField.setText("");
            } else if (e.getSource() == addButton) {

                try {
                    int roleSet = 0;
                    if (roleField.getText().contentEquals("Admin")) {
                        roleSet = 1;
                    }
                    UserPOJO userUpdate = new UserPOJO(
                            user.getId(),
                            nameField.getText(),
                            user.getIdAccount(),
                            addressField.getText(),
                            roleSet);
                    UserBUS userBUS = new UserBUS();
                    Boolean result = userBUS.update(userUpdate);
                    if (result) {
                        JOptionPane.showMessageDialog(this, "Update success!", "Success", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Something went wrong..., please review the " +
                                "information", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Information, please check again!");
                    System.out.println(Arrays.toString(ex.getStackTrace()));
                }
            }

        }

    }

    class ResetPassFormPanel extends JDialog implements ActionListener {
        JLabel headLabel;

        JLabel usernameLabel;
        JLabel usernameShowLabel;

        JLabel oldPasswordLabel;
        JPasswordField oldPasswordFieldEn;
        JTextField oldPasswordField;

        JLabel newPasswordLabel;
        JPasswordField newPasswordFieldEn;
        JTextField newPasswordField;

        JLabel repeatPasswordLabel;
        JPasswordField repeatPasswordFieldEn;
        JTextField repeatPasswordField;

        JButton changePasswordButton;

        GridBagConstraints gbc;
        AccountPOJO acc;

        ResetPassFormPanel(String usernameIn) {
            acc = getAccount(usernameIn);

            this.setSize(700, 300);
            this.setLocationRelativeTo(null);
            this.setResizable(true);
            setLayout(new GridBagLayout());
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.initComponent();
            this.setVisible(true);
        }

        public AccountPOJO getAccount(String usernameIn) {
            ArrayList<AccountPOJO> accountList = AccountBUS.getAll();
            for (AccountPOJO a : accountList) {
                if (a != null && a.getUsername().equals(usernameIn)) {
                    return a;
                }
            }
            return null;
        }

        public void initComponent() {
            headLabel = new JLabel("Change password");
            headLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
            headLabel.setHorizontalAlignment(SwingConstants.CENTER);
            headLabel.setBounds(221, 41, 212, 31);
            usernameLabel = new JLabel("Username:");
            usernameShowLabel = new JLabel(acc.getUsername());

            newPasswordLabel = new JLabel("Input new password:");
            newPasswordField = new JTextField();

            repeatPasswordLabel = new JLabel("Repeat your new password:");
            repeatPasswordField = new JTextField();

            changePasswordButton = new JButton("Update");
            changePasswordButton.addActionListener(this);

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
            add(usernameLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(newPasswordLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(repeatPasswordLabel, gbc);

            // add fields
            gbc.weightx = 2;
            i = 2;
            gbc.gridx = 1;
            gbc.gridy = i++;
            add(usernameShowLabel, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(newPasswordField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(repeatPasswordField, gbc);

            // add buttons

            gbc.gridx = 1;
            gbc.gridy = i;
            add(changePasswordButton, gbc);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == changePasswordButton) {
                Boolean result = doChangePassword();
                if (!result)
                    JOptionPane.showMessageDialog(this, "Update failed, please check again!");
            }
        }

        public Boolean doChangePassword() {
            String newPasswordFieldVal = newPasswordField.getText();
            String repeatPasswordFieldVal = repeatPasswordField.getText();
            if (newPasswordFieldVal.isEmpty()
                    || repeatPasswordFieldVal.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are requirement!");
                return false;
            }

            if (!newPasswordFieldVal.equals(repeatPasswordFieldVal)) {
                JOptionPane.showMessageDialog(this, "Repeat password went wrong!");

                return false;
            }
            String encryptedPassword = encryptPassword(newPasswordFieldVal);
            try {

                AccountPOJO accountUpdate = new AccountPOJO(
                        acc.getId(),
                        acc.getUsername(),
                        encryptedPassword,
                        acc.getIsActive());
                AccountBUS accountBUS = new AccountBUS();
                Boolean result = accountBUS.update(accountUpdate);
                if (result) {
                    JOptionPane.showMessageDialog(this, "Change password success!", "Success",
                            JOptionPane.PLAIN_MESSAGE);
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Something went wrong..., please review the " +
                            "information", "Error", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Can't update password, please check again!");
                System.out.println(Arrays.toString(ex.getStackTrace()));
            }

            return true;
        }
    }

    class CreateNewAccountUserFrame extends JDialog implements ActionListener {
        JLabel headLabel;

        JLabel idLabel;
        JTextField idField;

        JLabel usernameLabel;
        JTextField usernameField;

        JLabel passwordLabel;
        JTextField passwordField;

        JLabel idUserLabel;
        JTextField idUserField;

        JLabel nameLabel;
        JTextField nameField;

        JLabel addressLabel;
        JTextField addressField;

        JLabel roleLabel;
        JTextField roleField;

        JButton clearButton;
        JButton addButton;

        GridBagConstraints gbc;
        UserPOJO user;

        CreateNewAccountUserFrame() {

            this.setSize(700, 400);
            this.setLocationRelativeTo(null);
            this.setResizable(true);
            setLayout(new GridBagLayout());
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.initComponent();
            this.setVisible(true);
        }

        public UserPOJO getUser(String userId) {
            ArrayList<UserPOJO> userList = UserBUS.getAll();
            for (UserPOJO userI : userList) {
                if (userI.getId().equals(userId)) {
                    return userI;
                }
            }
            return null;
        }

        public void initComponent() {
            headLabel = new JLabel("Add a new user");
            headLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
            headLabel.setHorizontalAlignment(SwingConstants.CENTER);
            headLabel.setBounds(221, 41, 212, 31);

            idLabel = new JLabel("ID Account:");
            idField = new JTextField();

            usernameLabel = new JLabel("Username:");
            usernameField = new JTextField();

            passwordLabel = new JLabel("Password:");
            passwordField = new JTextField();

            nameLabel = new JLabel("Name:");
            nameField = new JTextField();

            addressLabel = new JLabel("Address:");
            addressField = new JTextField();

            idUserLabel = new JLabel("ID User:");
            idUserField = new JTextField();

            roleLabel = new JLabel("Role: ");
            roleField = new JTextField("Admin /or/ User");

            clearButton = new JButton("Clear");
            clearButton.addActionListener(this);

            addButton = new JButton("Update");
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
            add(usernameLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(passwordLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(idUserLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(nameLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(addressLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = i++;
            add(roleLabel, gbc);

            // add fields
            gbc.weightx = 2;
            i = 2;
            gbc.gridx = 1;
            gbc.gridy = i++;
            add(idField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(usernameField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(passwordField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(idUserField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(nameField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(addressField, gbc);

            gbc.gridx = 1;
            gbc.gridy = i++;
            add(roleField, gbc);

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
                usernameField.setText("");
                passwordField.setText("");
                idUserField.setText("");
                nameField.setText("");
                addressField.setText("");
                roleField.setText("");
            } else if (e.getSource() == addButton) {
                if (idField.getText().isEmpty() || usernameField.getText().isEmpty() ||
                        passwordField.getText().isEmpty() || idUserField.getText().isEmpty()
                        || nameField.getText().isEmpty() || addressField.getText().isEmpty()
                        || roleField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Something went wrong..., please review the " +
                            "information", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (isExistAccountId(idField.getText())) {
                    JOptionPane.showMessageDialog(this, "Something went wrong..., please review the " +
                            "ID Account field", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (isExistUserId(idUserField.getText())) {
                    JOptionPane.showMessageDialog(this, "Something went wrong..., please review the " +
                            "ID User field", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!roleField.getText().trim().equals("Admin") && !roleField.getText().trim().equals("User")) {
                    JOptionPane.showMessageDialog(this, "Something went wrong..., please review the " +
                            "role field, it must be the Admin or User", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    int roleSet = 0;
                    if (roleField.getText().trim().contentEquals("Admin")) {
                        roleSet = 1;
                    }

                    String passwordEncrypted = encryptPassword(passwordField.getText());
                    AccountPOJO accountNew = new AccountPOJO(idField.getText(), usernameField.getText(),
                            passwordEncrypted, true);
                    AccountBUS accountBUS = new AccountBUS();
                    Boolean result = accountBUS.insert(accountNew);
                    UserPOJO newUser = new UserPOJO(
                            idUserField.getText(),
                            nameField.getText(),
                            idField.getText(),
                            addressField.getText(),
                            roleSet);
                    UserBUS userBUS = new UserBUS();
                    result = userBUS.insert(newUser);
                    if (result) {
                        JOptionPane.showMessageDialog(this, "Create success!", "Success",
                                JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Something went wrong..., please review the " +
                                "information", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Information, please check again!");
                    System.out.println(Arrays.toString(ex.getStackTrace()));
                }
            }

        }

    }
    private String encryptPassword(String password){
        String encryptedPassword = null;
        try
        {
            /* MessageDigest instance for MD5. */
            MessageDigest m = MessageDigest.getInstance("MD5");

            /* Add plain-text password bytes to digest using MD5 update() method. */
            m.update(password.getBytes());

            /* Convert the hash value into bytes */
            byte[] bytes = m.digest();

            /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */
            StringBuilder s = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            /* Complete hashed password in hexadecimal format */
            encryptedPassword = s.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return encryptedPassword;
    }
    private Boolean isExistAccountId(String accountId) {
        ArrayList<AccountPOJO> accList = AccountBUS.getAll();
        for (AccountPOJO acc : accList) {
            if (acc.getId().equals(accountId)) {
                return true;
            }
        }
        return false;
    }

    private Boolean isExistUserId(String userId) {
        ArrayList<UserPOJO> userList = UserBUS.getAll();

        for (UserPOJO userCheck : userList) {
            if (userCheck.getId().equals(userId)) {
                return true;
            }
        }
        return false;
    }
}

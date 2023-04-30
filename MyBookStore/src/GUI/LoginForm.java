package GUI;

import BUS.AccountBUS;
import BUS.UserBUS;
import POJO.AccountPOJO;
import POJO.UserPOJO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

public class LoginForm extends JFrame implements ActionListener {
    JButton loginButton;
    JPasswordField pass;
    JTextField user;
    JLabel message;
    JCheckBox rememberMeCheckBox;
    AdminControllerGUI adminControllerGUI;
    UserControl userControl;

    public LoginForm() {
        // setUndecorated(true);

        setSize(900, 600);
        // setLocationRelativeTo(null);
        userInterface();
        try {
            File myObj = new File(".config.txt");
            if (!myObj.createNewFile()) {

                try {
                    Scanner myReader = new Scanner(myObj);
                    if(myReader.hasNextLine()){
                        user.setText(myReader.nextLine());
                        pass.setText(myReader.nextLine());
                    }

                    myReader.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void userInterface() {
        JPanel main_pan = new JPanel(new GridLayout(1, 2));

        JPanel left_pan = new JPanel(new BorderLayout());
        left_pan.setBackground(Color.DARK_GRAY);

        JLabel cover = new JLabel(new ImageIcon(getClass().getResource("../images/cover.jpeg")));

        cover.setHorizontalTextPosition(JLabel.CENTER);
        cover.setVerticalTextPosition(JLabel.BOTTOM);
        cover.setForeground(Color.white);
        cover.setFont(new Font("Segoe UI", 0, 15));
        left_pan.add(cover);

        main_pan.add(left_pan);

        JPanel right_pan = new JPanel(new BorderLayout());
        right_pan.setBackground(Color.white);

        JPanel right_comp = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Sign In");
        title.setPreferredSize(new Dimension(this.getWidth(), 70));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Segoe UI", 0, 30));
        right_comp.add(title, "North");

        JPanel pan = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel _user = new JLabel("Username");
        _user.setFont(new Font("Segoe UI", 0, 14));
        _user.setPreferredSize(new Dimension(200, 20));
        pan.add(_user);
        user = new JTextField();
        user.setPreferredSize(new Dimension(200, 30));
        pan.add(user);

        JLabel _pass = new JLabel("Password");
        _pass.setFont(new Font("Segoe UI", 0, 14));
        _pass.setPreferredSize(new Dimension(200, 20));
        pan.add(_pass);
        pass = new JPasswordField();
        pass.setPreferredSize(new Dimension(200, 30));
        pan.add(pass);

        rememberMeCheckBox = new JCheckBox("Remember Me", true);
        rememberMeCheckBox.setBounds(100, 150,50, 50);
        pan.add(rememberMeCheckBox);

        right_comp.add(pan);

        right_pan.add(right_comp);

        JPanel pan_btn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pan_btn.setPreferredSize(new Dimension(this.getWidth(), 170));

        message = new JLabel("Pls login to access our system!");
        message.setFont(new Font("Segoe UI", 0, 14));
        message.setForeground(Color.blue);
        pan.add(message);

        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(120, 30));
        loginButton.setFont(new Font("Segoe UI", 0, 17));
        loginButton.setContentAreaFilled(false);
        loginButton.setForeground(Color.blue);
        loginButton.setBorder(BorderFactory.createLineBorder(Color.blue, 1, true));
        loginButton.addActionListener(this);
        pan_btn.add(loginButton);
        right_pan.add(pan_btn, "South");

        main_pan.add(right_pan);

        getContentPane().add(main_pan);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            this.doLogin();
        }
    }

    private void doLogin() {
        String userValue = user.getText().trim(); // get user entered username from the textField1
        String passValue = String.valueOf(pass.getPassword()).trim(); // get user entered password from the textField2
        Boolean rememberMe = rememberMeCheckBox.isSelected();
        if(rememberMe.equals(true)){
            try {
                FileWriter myWriter = new FileWriter(".config.txt");
                myWriter.write(userValue+"\n");
                myWriter.write(passValue);
                myWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int role = validateAccount(userValue, passValue);
        if (role == -3) {
            message.setForeground(Color.red);
            message.setText("Your account is not activated!\nContact to admin");
            return;
        }
        if (role == -2) {
            message.setForeground(Color.red);
            message.setText("Username and password are required!");
            return;
        }
        if (role == -1) {
            message.setForeground(Color.red);
            message.setText("Username or password went wrong!");
            return;
        }
        if (role == 0) {
            message.setForeground(Color.blue);
            message.setText("Login as user successfully!");
            userControl = new UserControl(userValue);
            userControl.setVisible(true);
            setVisible(false);
            return;
        }
        if (role == 1) {
            message.setForeground(Color.blue);
            message.setText("Login as admin successfully!");
            adminControllerGUI = new AdminControllerGUI(userValue);
            adminControllerGUI.setVisible(true);
            setVisible(false);
            return;
        }
    }

    public int validateAccount(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return -2; // missing input
        }
        ArrayList<AccountPOJO> accountList = new ArrayList<>();
        accountList = AccountBUS.getAll();
        ArrayList<UserPOJO> userList = new ArrayList<>();
        userList = UserBUS.getAll();
        for (AccountPOJO a : accountList) {

            String passwordEncrypted = encryptPassword(password);
            if (a != null && a.getUsername().equals(username) && a.getPassword().equals(passwordEncrypted)) {
                if (!a.getIsActive()) {
                    return -3; // is not active
                }
                UserPOJO userTemp = userList.stream().filter(u -> a.getId().equals(u.getIdAccount())).findFirst()
                        .orElse(null);
                if (userTemp != null) {
                    if (userTemp.getRole() == 1) {
                        return 1; // admin
                    } else {
                        return 0; // user
                    }
                }
            }

        }
        return -1; // failed
    }

    public String encryptPassword(String password){
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

    public static void main(String[] args) {
        LoginForm obj = new LoginForm();
        obj.setVisible(true);
    }
}
package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import BUS.AccountBUS;
import BUS.UserBUS;
import DAO.UserDAO;
import POJO.AccountPOJO;
import POJO.UserPOJO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class UserControl extends JFrame {
	UserPOJO user;
	String username;
	private final JPanel contentPane;
	private final JPanel topPane = new JPanel();
	BookManagement bookControlGUI;
	PromotionManagement promotionControlGUI;
	importSheet importSheetControlGUI;
	AuthorGUI authorControlGUI;
	ViewOrdersFrame orderControlGUI;
	ViewBookCategoriesFrame bookCategoryControlGUI;
	publisherGUI publisherControlGUI;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// UserControl frame = new UserControl();
	// frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	public AccountPOJO getAccount() {
		ArrayList<AccountPOJO> accountList = AccountBUS.getAll();
		for (AccountPOJO a : accountList) {
			if (a != null && a.getUsername().equals(username)) {
				return a;
			}
		}
		return null;
	}

	public UserPOJO getUser() {
		ArrayList<AccountPOJO> accountList = AccountBUS.getAll();
		ArrayList<UserPOJO> userList = UserBUS.getAll();
		UserPOJO userTemp = null;
		for (AccountPOJO a : accountList) {
			if (a != null && a.getUsername().equals(username)) {
				userTemp = userList.stream().filter(u -> a.getId().equals(u.getIdAccount())).findFirst().orElse(null);
			}
		}
		return userTemp;
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
	public UserControl(String username) {
		this.setResizable(false);

		this.setUsername(username);
		user = this.getUser();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 663, 493);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		topPane.setBounds(0, 0, 649, 31);
		contentPane.add(topPane);
		topPane.setLayout(null);

		JButton logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(10, 10, 85, 21);
		logoutBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				LoginForm loginForm = new LoginForm();
				loginForm.setVisible(true);
				hideUserControl();

			}

		});
		topPane.add(logoutBtn);
		topPane.add(logoutBtn);

		JButton editInfoBtn = new JButton("Edit info");
		editInfoBtn.setBounds(109, 10, 85, 21);
		editInfoBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				InfoFormPanel infoFormPanel = new InfoFormPanel();
				infoFormPanel.setVisible(true);

			}

		});
		topPane.add(editInfoBtn);

		JButton changePasswordBtn = new JButton("Change password");
		changePasswordBtn.setBounds(200, 10, 140, 21);
		changePasswordBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ChangePasswordForm changePasswordForm = new ChangePasswordForm();
				changePasswordForm.setVisible(true);

			}

		});
		topPane.add(changePasswordBtn);

		JLabel userControlLabel = new JLabel("USER CONTROL");
		userControlLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		userControlLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userControlLabel.setBounds(221, 41, 212, 31);
		contentPane.add(userControlLabel);

		JLabel greetingLabel = new JLabel("Welcome, " + username);
		greetingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		greetingLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		greetingLabel.setBounds(190, 66, 268, 31);
		contentPane.add(greetingLabel);

		JButton BookManageBtn = new JButton("Book Management");
		BookManageBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				bookControlGUI = new BookManagement(username);
				hideUserControl();
			}

		});
		BookManageBtn.setBounds(255, 92, 150, 31);
		contentPane.add(BookManageBtn);

		JButton PromotionManageBtn = new JButton("Promotion Management");
		PromotionManageBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				promotionControlGUI = new PromotionManagement(username);
				promotionControlGUI.setVisible(true);
				hideUserControl();
			}

		});

		PromotionManageBtn.setBounds(10, 146, 132, 31);
		contentPane.add(PromotionManageBtn);

		JButton importSheetBtn = new JButton("Import Sheet");
		importSheetBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				importSheetControlGUI = new importSheet(username);
				importSheetControlGUI.setVisible(true);
				hideUserControl();
			}

		});
		importSheetBtn.setBounds(10, 230, 132, 31);
		contentPane.add(importSheetBtn);

		JButton authorManageBtn = new JButton("Author Management");
		authorManageBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				authorControlGUI = new AuthorGUI(username);
				authorControlGUI.setVisible(true);
				hideUserControl();
			}

		});
		authorManageBtn.setBounds(10, 316, 132, 31);
		contentPane.add(authorManageBtn);

		JButton orderManageBtn = new JButton("Order Management");
		orderManageBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				orderControlGUI = new ViewOrdersFrame(username);
				orderControlGUI.setVisible(true);
				hideUserControl();
			}

		});
		orderManageBtn.setBounds(507, 146, 132, 31);
		contentPane.add(orderManageBtn);

		JButton categoryManageBtn = new JButton("Category Management");
		categoryManageBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				bookCategoryControlGUI = new ViewBookCategoriesFrame(username);
				bookCategoryControlGUI.setVisible(true);
			}

		});
		categoryManageBtn.setBounds(507, 230, 132, 31);
		contentPane.add(categoryManageBtn);

		JButton publisherManageBtn = new JButton("Publisher Management");
		publisherManageBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				publisherControlGUI = new publisherGUI(username);
				publisherControlGUI.setVisible(true);
				hideUserControl();
			}

		});
		publisherManageBtn.setBounds(507, 316, 132, 31);
		contentPane.add(publisherManageBtn);
//		BufferedImage image;
//		try {
			JLabel imageLable = new JLabel(new ImageIcon(getClass().getResource("/images/hieu-sach-nha-nam-214377.jpg")));

			imageLable.setBounds(152, 129, 345, 253);
			contentPane.add(imageLable);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	private void hideUserControl() {
		this.setVisible(false);
	}

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
		JLabel roleField;

		JButton clearButton;
		JButton addButton;

		GridBagConstraints gbc;

		InfoFormPanel() {
			this.setSize(700, 300);
			this.setLocationRelativeTo(null);
			this.setResizable(true);
			setLayout(new GridBagLayout());
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.initComponent();
			this.setVisible(true);
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
			roleField = new JLabel(roleString);

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
			} else if (e.getSource() == addButton) {
				try {
					if (nameField.getText().isEmpty() || addressField.getText().isEmpty()
							|| roleField.getText().isEmpty()) {
						JOptionPane.showMessageDialog(this, "Something went wrong..., please review the " +
								"information", "Error", JOptionPane.WARNING_MESSAGE);
						return;
					}
					UserPOJO userUpdate = new UserPOJO(
							user.getId(),
							nameField.getText(),
							user.getIdAccount(),
							addressField.getText(),
							user.getRole());
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

	class ChangePasswordForm extends JDialog implements ActionListener {
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

		JButton addButton;

		GridBagConstraints gbc;
		AccountPOJO acc;

		ChangePasswordForm() {
			this.setSize(700, 300);
			this.setLocationRelativeTo(null);
			this.setResizable(true);
			setLayout(new GridBagLayout());
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.initComponent();
			this.setVisible(true);
		}

		public void initComponent() {
			headLabel = new JLabel("Change password");
			headLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
			headLabel.setHorizontalAlignment(SwingConstants.CENTER);
			headLabel.setBounds(221, 41, 212, 31);
			acc = getAccount();
			usernameLabel = new JLabel("Username:");
			usernameShowLabel = new JLabel(acc.getUsername());

			oldPasswordLabel = new JLabel("Input old password:");
			oldPasswordFieldEn = new JPasswordField();

			newPasswordLabel = new JLabel("Input new password:");
			newPasswordFieldEn = new JPasswordField();

			repeatPasswordLabel = new JLabel("Repeat your new password:");
			repeatPasswordFieldEn = new JPasswordField();

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
			add(usernameLabel, gbc);

			gbc.gridx = 0;
			gbc.gridy = i++;
			add(oldPasswordLabel, gbc);

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
			add(oldPasswordFieldEn, gbc);

			gbc.gridx = 1;
			gbc.gridy = i++;
			add(newPasswordFieldEn, gbc);

			gbc.gridx = 1;
			gbc.gridy = i++;
			add(repeatPasswordFieldEn, gbc);

			// add buttons

			gbc.gridx = 1;
			gbc.gridy = i;
			add(addButton, gbc);
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

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == addButton) {
				Boolean result = doChangePassword();
//				if (!result)
//					JOptionPane.showMessageDialog(this, "Invalid Information, please check again!");
			}
		}

		public Boolean doChangePassword() {
			String newPasswordFieldVal = String.valueOf(newPasswordFieldEn.getPassword());
			String repeatPasswordFieldVal = String.valueOf(repeatPasswordFieldEn.getPassword());
			String oldPasswordFieldVal = String.valueOf(oldPasswordFieldEn.getPassword());
			if (newPasswordFieldVal.isEmpty()
					|| repeatPasswordFieldVal.isEmpty()) {
				JOptionPane.showMessageDialog(this, "All fields are requirement!");
				return false;
			}

			if (!newPasswordFieldVal.equals(repeatPasswordFieldVal)) {
				JOptionPane.showMessageDialog(this, "Repeat password went wrong!");

				return false;
			}
			String currentPassword = acc.getPassword();
			String inputOldPassword = encryptPassword(oldPasswordFieldVal);
			if (!inputOldPassword.equals(currentPassword)) {
				JOptionPane.showMessageDialog(this, "Old password went wrong!");
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
}

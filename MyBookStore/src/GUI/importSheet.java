package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import BUS.BookBUS;
import BUS.ImportSheetBUS;
import BUS.PublisherBUS;
import BUS.UserBUS;
import POJO.BookInImportSheetPOJO;
import POJO.BookPOJO;
import POJO.ImportSheetPOJO;
import POJO.PublisherPOJO;
import POJO.UserPOJO;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;
import java.sql.Date;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JTable;

public class importSheet extends JFrame {

	private JPanel contentPane;
	private JTextField idInput;
	private JTable listImportBookTable;
	private JLabel totalCostValueLabel;
	private JTextField quantityInput;
	private JTextField priceInput;
	private JTextField idBookInput;
	private JTextField nameBookInput;
	JComboBox employeeCombobox;
	JComboBox dayComboBox;
	JComboBox monthComboBox;
	JComboBox yearComboBox;
	JComboBox bookComboBox;
	JComboBox publisherCombobox;
	JCheckBox newBookCheckBox;
	private int total_cost;
	String username;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// importSheet frame = new importSheet();
	// frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ArrayList<UserPOJO> getEmployees() {
		UserBUS userBus = new UserBUS();
		ArrayList<UserPOJO> listEmp = userBus.getUserNotDisable();
		return listEmp;
	}

	public ArrayList<BookPOJO> getBookEnabled() {
		BookBUS bookBus = new BookBUS();
		ArrayList<BookPOJO> listBook = bookBus.getBookNotDisable();
		return listBook;
	}

	public ArrayList<PublisherPOJO> getPublisherEnabled() {
		PublisherBUS publisherBus = new PublisherBUS();
		ArrayList<PublisherPOJO> listPublisher = publisherBus.getPublisherNotDisable();
		return listPublisher;
	}

	public UserPOJO getEmployeeSelected(ActionEvent e) {
		UserPOJO employee = (UserPOJO) employeeCombobox.getSelectedItem();
		return employee;
	}

	public BookPOJO getBookSelected(ActionEvent e) {
		BookPOJO book = (BookPOJO) bookComboBox.getSelectedItem();
		return book;
	}

	public PublisherPOJO getPublisherSelected(ActionEvent e) {
		PublisherPOJO publisher = (PublisherPOJO) publisherCombobox.getSelectedItem();
		return publisher;
	}

	public String getDay(ActionEvent e) {
		String day = (String) dayComboBox.getSelectedItem();
		return day;
	}

	public String getMonth(ActionEvent e) {
		String month = (String) monthComboBox.getSelectedItem();
		return month;
	}

	public String getYear(ActionEvent e) {
		String year = (String) yearComboBox.getSelectedItem();
		return year;
	}

	public BookInImportSheetPOJO getInfoBookImported(ActionEvent e) {
		BookInImportSheetPOJO book = new BookInImportSheetPOJO();
		boolean isNewBook = newBookCheckBox.isSelected();
		String idIps = idInput.getText();
		String idBook;
		String name;
		String id_publisher;
		int quantity = Integer.valueOf(quantityInput.getText());
		int import_price;
		if (isNewBook) {
			idBook = idBookInput.getText();
			name = nameBookInput.getText();
			import_price = Integer.valueOf(priceInput.getText());
			id_publisher = getPublisherSelected(e).getId();
			book = new BookInImportSheetPOJO(idIps, idBook, name, id_publisher, quantity, import_price);

		} else {

			idBook = getBookSelected(e).getId();
			name = getBookSelected(e).getName();
			import_price = getBookSelected(e).getPrice();
			id_publisher = getBookSelected(e).getIdPublisher();
			book = new BookInImportSheetPOJO(idIps, idBook, name, id_publisher, quantity, import_price);
		}

		return book;
	}

	public ImportSheetPOJO getImportSheetInfo(ActionEvent e) {
		String idIPS = idInput.getText();
		String date = getYear(e) + "-" + getMonth(e) + "-" + getDay(e);
		Date create_at = Date.valueOf(date);
		String id_employee = getEmployeeSelected(e).getId();
		String name = getEmployeeSelected(e).getName();
		int total_cost = this.total_cost;
		ImportSheetPOJO ips = new ImportSheetPOJO(idIPS, create_at, id_employee, name, total_cost);
		return ips;
	}

	public ArrayList<BookInImportSheetPOJO> getListImportBook() {
		ArrayList<BookInImportSheetPOJO> listImportBook = new ArrayList<>();
		String idIPS = idInput.getText();
		for (int i = 0; i < listImportBookTable.getRowCount(); i++) {
			String id_book = (String) listImportBookTable.getValueAt(i, 0);
			String name_book = (String) listImportBookTable.getValueAt(i, 1);
			String id_publisher = (String) listImportBookTable.getValueAt(i, 2);
			int quantity = Integer.parseInt((String) listImportBookTable.getValueAt(i, 3));
			int price = Integer.parseInt((String) listImportBookTable.getValueAt(i, 4));
			BookInImportSheetPOJO book = new BookInImportSheetPOJO(idIPS, id_book, name_book, id_publisher, quantity,
					price);
			listImportBook.add(book);
		}
		return listImportBook;
	}

	public void CloseFrame() {
		this.setVisible(false);
		this.dispose();
	}

	// --------------------------------------------------------------------------------------------------------------------------
	/**
	 * Create the frame.
	 */
	public importSheet(String username) {
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
		JButton backBtn = new JButton("Back");
		backBtn.setBounds(10, 10, 101, 22);
		backBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				CloseFrame();
				UserControl userControl = new UserControl(username);
				userControl.setVisible(true);
			}

		});
		contentPane.add(backBtn);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 30, 926, 2);
		contentPane.add(separator_1);

		JPanel sidebarPane = new JPanel();
		sidebarPane.setBounds(10, 42, 173, 612);
		contentPane.add(sidebarPane);
		sidebarPane.setLayout(null);

		JButton importBookBtn = new JButton("List Imported Sheet");

		importBookBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				listImportSheet.main(null);
			}

		});
		importBookBtn.setBounds(10, 60, 153, 41);

		sidebarPane.add(importBookBtn);

		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(193, 30, 2, 624);
		contentPane.add(separator_2);

		JPanel mainContentPane = new JPanel();
		mainContentPane.setBounds(205, 45, 741, 612);
		contentPane.add(mainContentPane);
		mainContentPane.setLayout(null);

		JLabel IPSBookLabel = new JLabel("IMPORT SHEET BOOK");
		IPSBookLabel.setHorizontalAlignment(SwingConstants.CENTER);
		IPSBookLabel.setFont(new Font("Segoe UI Black", Font.ITALIC, 20));
		IPSBookLabel.setBounds(170, 10, 392, 41);
		mainContentPane.add(IPSBookLabel);

		JLabel CommonInfoLabel = new JLabel("Common Infomation");
		CommonInfoLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		CommonInfoLabel.setBounds(32, 36, 143, 25);
		mainContentPane.add(CommonInfoLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(170, 49, 569, 2);
		mainContentPane.add(separator);

		JLabel idLabel = new JLabel("ID:");
		idLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		idLabel.setBounds(10, 61, 100, 30);
		mainContentPane.add(idLabel);

		idInput = new JTextField();
		idInput.setBounds(42, 61, 166, 34);
		mainContentPane.add(idInput);
		idInput.setColumns(10);

		JLabel dayLabel = new JLabel("Day:");
		dayLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dayLabel.setBounds(52, 102, 46, 19);
		mainContentPane.add(dayLabel);

		dayComboBox = new JComboBox();

		for (int i = 1; i <= 31; i++) {
			if (i < 10) {
				dayComboBox.addItem("0" + i);
			} else {
				dayComboBox.addItem("" + i);
			}
		}

		dayComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				getDay(e);
			}

		});

		dayComboBox.setBounds(108, 101, 46, 25);
		mainContentPane.add(dayComboBox);

		JLabel monthLabel = new JLabel("Month:");
		monthLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		monthLabel.setBounds(168, 105, 54, 19);
		mainContentPane.add(monthLabel);

		monthComboBox = new JComboBox();

		for (int i = 1; i <= 12; i++) {
			if (i < 10) {
				monthComboBox.addItem("0" + i);
			} else {
				monthComboBox.addItem("" + i);
			}
		}
		monthComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				getMonth(e);
			}

		});

		monthComboBox.setBounds(214, 101, 46, 25);
		mainContentPane.add(monthComboBox);

		JLabel yearLabel = new JLabel("Year:");
		yearLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		yearLabel.setBounds(270, 102, 66, 25);
		mainContentPane.add(yearLabel);

		yearComboBox = new JComboBox();

		for (int i = 2022; i <= 2030; i++) {
			yearComboBox.addItem("" + i);
		}

		yearComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				getYear(e);
			}

		});
		yearComboBox.setBounds(306, 104, 66, 25);
		mainContentPane.add(yearComboBox);

		JLabel dateLabel = new JLabel("Date");
		dateLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dateLabel.setBounds(10, 101, 78, 25);
		mainContentPane.add(dateLabel);

		JLabel listImportBookLabel = new JLabel("List Imported Book");
		listImportBookLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		listImportBookLabel.setBounds(32, 388, 143, 25);
		mainContentPane.add(listImportBookLabel);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(153, 404, 586, 2);
		mainContentPane.add(separator_3);

		JScrollPane listImportBookPane = new JScrollPane();
		listImportBookPane.setBounds(10, 423, 731, 96);
		mainContentPane.add(listImportBookPane);

		JScrollBar listImportBookScrollBar = new JScrollBar();
		listImportBookPane.setRowHeaderView(listImportBookScrollBar);

		String[] columnNames = { "ID", "Name", "ID_Publisher", "Quantity", "Price Per One" };

		listImportBookTable = new JTable();
		listImportBookTable.setModel(new DefaultTableModel(new Object[][] {}, columnNames));
		listImportBookPane.setViewportView(listImportBookTable);

		JButton addRowBtn = new JButton("Add row");
		addRowBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				BookInImportSheetPOJO book = getInfoBookImported(e);
				DefaultTableModel model = (DefaultTableModel) listImportBookTable.getModel();
				String[] row = { book.getId_book(), book.getName(), book.getId_publisher(), book.getQuantity() + "",
						book.getImport_price() + "" };
				model.addRow(row);
				total_cost += book.getImport_price() * book.getQuantity();
				totalCostValueLabel.setText(total_cost + "");
			}

		});
		addRowBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addRowBtn.setBounds(634, 369, 97, 25);
		mainContentPane.add(addRowBtn);

		JLabel totalCostLabel = new JLabel("Total Cost");
		totalCostLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		totalCostLabel.setBounds(10, 532, 66, 25);
		mainContentPane.add(totalCostLabel);

		////
		totalCostValueLabel = new JLabel();
		totalCostValueLabel.setBounds(92, 537, 96, 19);
		mainContentPane.add(totalCostValueLabel);
		// totalCostLabel.setColumns(10);

		JLabel vndLabel = new JLabel("VND");
		vndLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		vndLabel.setBounds(198, 538, 46, 17);
		mainContentPane.add(vndLabel);

		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cancelBtn.setBounds(500, 577, 85, 25);
		cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				CloseFrame();
			}

		});
		mainContentPane.add(cancelBtn);

		JButton saveBtn = new JButton("Save");
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ImportSheetPOJO ips = getImportSheetInfo(e);
				ArrayList<BookInImportSheetPOJO> listImportBook = getListImportBook();
				ImportSheetBUS importSheetBus = new ImportSheetBUS();
				boolean addIPSRes = importSheetBus.addImportSheet(ips);
				boolean configTableBookRes = importSheetBus.configTableBook(listImportBook, ips.getCreate_at());
				System.out.println("config: " + configTableBookRes);
				boolean addListImportBookRes = importSheetBus.addListImportBook(listImportBook);
				System.out.println("add list import book: " + addListImportBookRes);
				if (addIPSRes && configTableBookRes && addListImportBookRes) {
					JOptionPane.showMessageDialog(null, "Add import sheet successfully");
				} else {
					JOptionPane.showMessageDialog(null, "Error");
				}

			}

		});
		saveBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		saveBtn.setBounds(607, 577, 85, 25);
		mainContentPane.add(saveBtn);

		JLabel employeeNameLabel = new JLabel("Employee");
		employeeNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		employeeNameLabel.setBounds(326, 64, 78, 24);
		mainContentPane.add(employeeNameLabel);

		employeeCombobox = new JComboBox();
		ArrayList<UserPOJO> listEmp = getEmployees();
		DefaultComboBoxModel modelEmpCombobox = new DefaultComboBoxModel(listEmp.toArray());
		employeeCombobox.setModel(modelEmpCombobox);
		employeeCombobox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				getEmployeeSelected(e);
			}

		});
		employeeCombobox.setBounds(397, 61, 229, 34);
		mainContentPane.add(employeeCombobox);

		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(170, 143, 569, 2);
		mainContentPane.add(separator_4);

		JLabel bookInfoLabel = new JLabel("Book infomation");
		bookInfoLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		bookInfoLabel.setBounds(32, 131, 133, 25);
		mainContentPane.add(bookInfoLabel);

		bookComboBox = new JComboBox();
		ArrayList<BookPOJO> listBook = getBookEnabled();
		DefaultComboBoxModel modelBookCombobox = new DefaultComboBoxModel(listBook.toArray());
		bookComboBox.setModel(modelBookCombobox);
		bookComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				getBookSelected(e);
			}

		});
		bookComboBox.setBounds(78, 204, 166, 30);
		mainContentPane.add(bookComboBox);

		JLabel bookComboBoxLabel = new JLabel("Book");
		bookComboBoxLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		bookComboBoxLabel.setBounds(10, 204, 58, 30);
		mainContentPane.add(bookComboBoxLabel);

		JLabel quantityLabel = new JLabel("Quantity");
		quantityLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		quantityLabel.setBounds(270, 207, 66, 21);
		mainContentPane.add(quantityLabel);

		quantityInput = new JTextField();
		quantityInput.setBounds(336, 204, 100, 30);
		mainContentPane.add(quantityInput);
		quantityInput.setColumns(10);

		JLabel priceLabel = new JLabel("Price");
		priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		priceLabel.setBounds(460, 204, 66, 26);
		mainContentPane.add(priceLabel);

		priceInput = new JTextField();
		priceInput.setBounds(514, 204, 112, 30);
		mainContentPane.add(priceInput);
		priceInput.setColumns(10);

		JLabel newBookLabel = new JLabel("New book (not available)");
		newBookLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		newBookLabel.setBounds(10, 244, 178, 25);
		mainContentPane.add(newBookLabel);

		newBookCheckBox = new JCheckBox();
		newBookCheckBox.setBounds(200, 234, 50, 50);
		mainContentPane.add(newBookCheckBox);

		JLabel idBookLabel = new JLabel("ID_Book");
		idBookLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		idBookLabel.setBounds(10, 286, 66, 25);
		mainContentPane.add(idBookLabel);

		idBookInput = new JTextField();
		idBookInput.setBounds(78, 285, 96, 31);
		mainContentPane.add(idBookInput);
		idBookInput.setColumns(10);

		JLabel nameBookLabel = new JLabel("Name");
		nameBookLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nameBookLabel.setBounds(198, 286, 54, 25);
		mainContentPane.add(nameBookLabel);

		nameBookInput = new JTextField();
		nameBookInput.setBounds(241, 286, 148, 30);
		mainContentPane.add(nameBookInput);
		nameBookInput.setColumns(10);

		JLabel publisherLabel = new JLabel("Publisher");
		publisherLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		publisherLabel.setBounds(415, 288, 66, 25);
		mainContentPane.add(publisherLabel);

		publisherCombobox = new JComboBox();

		ArrayList<PublisherPOJO> listPublisher = getPublisherEnabled();
		DefaultComboBoxModel modelPublisherCombobox = new DefaultComboBoxModel(listPublisher.toArray());
		publisherCombobox.setModel(modelPublisherCombobox);
		publisherCombobox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				getPublisherSelected(e);
			}

		});

		publisherCombobox.setBounds(500, 285, 166, 30);
		mainContentPane.add(publisherCombobox);

	}
}

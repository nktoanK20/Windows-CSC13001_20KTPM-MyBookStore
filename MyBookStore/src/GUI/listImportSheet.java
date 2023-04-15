package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import BUS.ImportSheetBUS;
import POJO.BookInImportSheetPOJO;
import POJO.ImportSheetPOJO;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class listImportSheet extends JFrame {

	private JPanel contentPane;
	private JTable listImportBookTable;
	private JTable listImportSheetTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					listImportSheet frame = new listImportSheet();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void showListImportSheet(){
		DefaultTableModel model = (DefaultTableModel) listImportSheetTable.getModel();
		model.setRowCount(0);
		ArrayList<ImportSheetPOJO> listImportSheet = new ArrayList<>();
		ImportSheetBUS importSheetBus = new ImportSheetBUS();
		listImportSheet = importSheetBus.getAllImportSheet();
		for (ImportSheetPOJO importSheetPOJO : listImportSheet) {
			String id = importSheetPOJO.getId();
			String create_at = importSheetPOJO.getCreate_at().toString();
			String id_employee = importSheetPOJO.getId_employee();
			String name = importSheetPOJO.getName();
			String total = importSheetPOJO.getTotal_cost()+"";

			String[] row = {id, create_at, id_employee, name, total};
			model.addRow(row);
		}
	}

	public String getIDImportSheetSelected(){
		DefaultTableModel model = (DefaultTableModel) listImportSheetTable.getModel();
		int i_row = listImportSheetTable.getSelectedRow();
		String id = (String) model.getValueAt(i_row, 0);
		

		return id;
	}

	public void showImportSheetSelected(){
		String idImportSheet = getIDImportSheetSelected();
		ImportSheetBUS importSheetBUS = new ImportSheetBUS();
		ArrayList<BookInImportSheetPOJO> listBookInIPS = importSheetBUS.getBookInImportSheet(idImportSheet);

		DefaultTableModel model = (DefaultTableModel) listImportBookTable.getModel();
		model.setRowCount(0);
		for (BookInImportSheetPOJO bookInImportSheetPOJO : listBookInIPS) {
			String idBook = bookInImportSheetPOJO.getId_book();
			String nameBook = bookInImportSheetPOJO.getName();
			String id_publisher = bookInImportSheetPOJO.getId_publisher();
			String quantity = bookInImportSheetPOJO.getQuantity()+"";
			String import_price = bookInImportSheetPOJO.getImport_price()+"";
			String[] row = {idBook, nameBook,id_publisher, quantity, import_price};
			model.addRow(row);
		}
	}

	public void closeFrame(){
		this.setVisible(false);
		this.dispose();
	}

	/**
	 * Create the frame.
	 */
	public listImportSheet() {
		setResizable(false);
		setTitle("Bookstore Management - Employee");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		backBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				closeFrame();
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
		
		JButton viewListIPSBtn = new JButton("View List Imported Sheet");
		viewListIPSBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showListImportSheet();

			}
		});
		viewListIPSBtn.setBounds(10, 60, 153, 41);
		sidebarPane.add(viewListIPSBtn);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(193, 30, 2, 624);
		contentPane.add(separator_2);
		
		JPanel mainContentPane = new JPanel();
		mainContentPane.setBounds(213, 45, 741, 612);
		contentPane.add(mainContentPane);
		mainContentPane.setLayout(null);
		
		JLabel listIPSLabel = new JLabel("LIST IMPORT SHEET BOOK");
		listIPSLabel.setHorizontalAlignment(SwingConstants.CENTER);
		listIPSLabel.setFont(new Font("Segoe UI Black", Font.ITALIC, 20));
		listIPSLabel.setBounds(170, 10, 392, 41);
		mainContentPane.add(listIPSLabel);
				
		JLabel listImportBookLabel = new JLabel("List Imported Book");
		listImportBookLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		listImportBookLabel.setBounds(26, 333, 143, 25);
		mainContentPane.add(listImportBookLabel);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(155, 345, 586, 2);
		mainContentPane.add(separator_3);
		
		JScrollPane listImportBookPane = new JScrollPane();
		listImportBookPane.setBounds(10, 368, 731, 184);
		mainContentPane.add(listImportBookPane);
		
		JScrollBar listImportBookScrollBar = new JScrollBar();
		listImportBookPane.setRowHeaderView(listImportBookScrollBar);
		String[] columnNames = {"ID", "Name","ID_Publisher","Quantity","Price Per One"};
		
		listImportBookTable = new JTable();

		listImportBookTable.setModel(new DefaultTableModel(new Object[][] {},columnNames));
		listImportBookPane.setViewportView(listImportBookTable);
		
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				closeFrame();
			}
			
		});
		cancelBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cancelBtn.setBounds(603, 568, 128, 34);
		mainContentPane.add(cancelBtn);
		
		JScrollPane listImportSheetPane = new JScrollPane();
		listImportSheetPane.setBounds(10, 52, 731, 225);
		mainContentPane.add(listImportSheetPane);
		
		String[] columnNamesIPS = {"ID Import Sheet","Create At", "ID Employee","Name", "Total Cost"};
		listImportSheetTable = new JTable();
		listImportSheetTable.setModel(new DefaultTableModel(new Object[][] {},columnNamesIPS));
		listImportSheetTable.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseClicked(java.awt.event.MouseEvent evt){
					showImportSheetSelected();
			}

			
		});
		listImportSheetPane.setViewportView(listImportSheetTable);
		
		JScrollBar listImportSheetScrollBar = new JScrollBar();
		listImportSheetPane.setRowHeaderView(listImportSheetScrollBar);
		
		JButton viewDetailBtn = new JButton("View Detail");
		viewDetailBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		viewDetailBtn.setBounds(603, 287, 128, 34);
		mainContentPane.add(viewDetailBtn);
		
		
	}
}

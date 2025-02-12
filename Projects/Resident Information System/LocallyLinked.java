import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class LocallyLinked {
	
	public static Color primaryColor = new Color(224, 119, 0);
	public static Color textColor = new Color(85, 85, 85);
	public static Color dirtyWhite = new Color(240, 240, 240);
	public static Color darkGrey = new Color(191, 191, 191);
	
	public static Font textBoxFont = new Font("Arial", Font.PLAIN, 18);
	public static Font boxLabelFont = new Font("Arial", Font.PLAIN, 18);
	public static Font brandFont = new Font("Arial", Font.BOLD, 25);
	public static Font taglineFont = new Font("Arial", Font.BOLD, 20);
	
	public static String residentsFilePath = "residents_record.txt";
	public static String dataBreaker = ":";
	
	public static JTable recordsTable = new JTable();
	public static DefaultTableModel model;
	
	public static List<String> residentsRecords;

	public static void main(String[] args) throws IOException {
		
		onCheckFile();
		openDashboard();

	}
	
	public static void onCheckFile() throws IOException {
		File recordsFile = new File(residentsFilePath);
		
		if(!recordsFile.exists()) {
			recordsFile.createNewFile();
		}
		
		return;
	}
	
	public static void openDashboard() throws IOException {
		String[] columnNames = {"First Name", "Middle Name", "Last Name", "Age", "Address", "Contact Number"};
		
		JFrame dashboardFrame = new JFrame();
		dashboardFrame.setBounds(0, 0, 1100, 800);
		dashboardFrame.setBackground(Color.white);
		dashboardFrame.setLayout(null);
		dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dashboardFrame.setLocationRelativeTo(null);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 1100, 800);
		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.white);
		dashboardFrame.add(mainPanel);
		
		JLabel brandName = new JLabel();
		brandName.setBounds(25, 15, 250, 50);
		brandName.setText("LocallyLinked.");
		brandName.setFont(brandFont);
		brandName.setForeground(primaryColor);
		mainPanel.add(brandName);
		
		JButton addButton = new JButton();
		addButton.setBounds(585, 20, 150, 40);
		addButton.setBackground(primaryColor);
		addButton.setForeground(Color.white);
		addButton.setFont(textBoxFont);
		addButton.setFocusPainted(false);
		addButton.setText("Add");
		mainPanel.add(addButton);
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					onModifyData(true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		JButton updateButton = new JButton();
		updateButton.setBounds(745, 20, 150, 40);
		updateButton.setBackground(primaryColor);
		updateButton.setForeground(Color.white);
		updateButton.setFont(textBoxFont);
		updateButton.setFocusPainted(false);
		updateButton.setText("Update");
		mainPanel.add(updateButton);
		updateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					onModifyData(false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		JButton deleteButton = new JButton();
		deleteButton.setBounds(905, 20, 150, 40);
		deleteButton.setBackground(primaryColor);
		deleteButton.setForeground(Color.white);
		deleteButton.setFont(textBoxFont);
		deleteButton.setFocusPainted(false);
		deleteButton.setText("Delete");
		mainPanel.add(deleteButton);
		deleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(recordsTable.getSelectedRow() >= 0) {
					try {
						residentsRecords.remove(recordsTable.getSelectedRow());
						Files.write(Paths.get(residentsFilePath), residentsRecords);
						model.removeRow(recordsTable.getSelectedRow());
						JOptionPane.showMessageDialog(null, "Successfully Removed!");
					} catch (Exception ex2) {
						System.out.println("Error: " + ex2.toString());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please select a row to continue!");
				}
				
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 80, 1025, 685);
		scrollPane.setLayout(null);
		mainPanel.add(scrollPane);
		
		model = new DefaultTableModel(columnNames, 0);
		recordsTable = new JTable(model);
		recordsTable.setFont(boxLabelFont);
		recordsTable.setRowHeight(40);
		recordsTable.setBounds(0, 0, 1025, 685);
		scrollPane.add(recordsTable);
		
		onLoadData();
		

		dashboardFrame.setVisible(true);
	}
	
	public static void onModifyData(boolean isAdding) throws IOException {
		
		if(recordsTable.getSelectedRow() < 0 && !isAdding) {
			JOptionPane.showMessageDialog(null, "Please select a row to continue!");
			return;
		}
		
		JFrame mainFrame = new JFrame();
		mainFrame.setBounds(0, 0, 800, 420);
		mainFrame.setLayout(null);
		mainFrame.setTitle("LocallyLinked - Add New Record");
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 800, 500);
		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.white);
		mainFrame.add(mainPanel);
		
		JLabel brandName = new JLabel();
		brandName.setBounds(25, 0, 500, 50);
		brandName.setText("LocallyLinked - Create New Record");
		brandName.setFont(brandFont);
		brandName.setForeground(primaryColor);
		mainPanel.add(brandName);
		
		JLabel firstnameLbl = new JLabel();
		firstnameLbl.setBounds(25, 35, 200, 50);
		firstnameLbl.setText("First Name");
		firstnameLbl.setFont(textBoxFont);
		firstnameLbl.setForeground(textColor);
		mainPanel.add(firstnameLbl);
		
		JTextField firstNameBox = new JTextField();
		firstNameBox.setBounds(25, 75, 365, 40);
		firstNameBox.setFont(boxLabelFont);
		mainPanel.add(firstNameBox);
		
		JLabel middlenameLbl = new JLabel();
		middlenameLbl.setBounds(400, 35, 200, 50);
		middlenameLbl.setText("Middle Name");
		middlenameLbl.setFont(textBoxFont);
		middlenameLbl.setForeground(textColor);
		mainPanel.add(middlenameLbl);
		
		JTextField middleNameBox = new JTextField();
		middleNameBox.setBounds(400, 75, 365, 40);
		middleNameBox.setFont(boxLabelFont);
		mainPanel.add(middleNameBox);
		
		JLabel lastnameLbl = new JLabel();
		lastnameLbl.setBounds(25, 115, 200, 50);
		lastnameLbl.setText("Last Name");
		lastnameLbl.setFont(textBoxFont);
		lastnameLbl.setForeground(textColor);
		mainPanel.add(lastnameLbl);
		
		JTextField lastNameBox = new JTextField();
		lastNameBox.setBounds(25, 155, 365, 40);
		lastNameBox.setFont(boxLabelFont);
		mainPanel.add(lastNameBox);
		
		JLabel ageLbl = new JLabel();
		ageLbl.setBounds(400, 115, 200, 50);
		ageLbl.setText("Age");
		ageLbl.setFont(textBoxFont);
		ageLbl.setForeground(textColor);
		mainPanel.add(ageLbl);
		
		JTextField ageBox = new JTextField();
		ageBox.setBounds(400, 155, 365, 40);
		ageBox.setFont(boxLabelFont);
		mainPanel.add(ageBox);
		
		JLabel addressLbl = new JLabel();
		addressLbl.setBounds(25, 195, 200, 50);
		addressLbl.setText("Address");
		addressLbl.setFont(textBoxFont);
		addressLbl.setForeground(textColor);
		mainPanel.add(addressLbl);
		
		JTextField addressBox = new JTextField();
		addressBox.setBounds(25, 235, 365, 40);
		addressBox.setFont(boxLabelFont);
		mainPanel.add(addressBox);
		
		JLabel contactnumLbl = new JLabel();
		contactnumLbl.setBounds(400, 195, 200, 50);
		contactnumLbl.setText("Contact Number");
		contactnumLbl.setFont(textBoxFont);
		contactnumLbl.setForeground(textColor);
		mainPanel.add(contactnumLbl);
		
		JTextField contactnumBox = new JTextField();
		contactnumBox.setBounds(400, 235, 365, 40);
		contactnumBox.setFont(boxLabelFont);
		mainPanel.add(contactnumBox);
		
		JButton cancelButton = new JButton();
		cancelButton.setBounds(455, 310, 150, 40);
		cancelButton.setBackground(dirtyWhite);
		cancelButton.setForeground(Color.darkGray);
		cancelButton.setFont(textBoxFont);
		cancelButton.setFocusPainted(false);
		cancelButton.setText("Cancel");
		mainPanel.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.dispose();
								
			}
		});
		
		JButton addButton = new JButton();
		addButton.setBounds(615, 310, 150, 40);
		addButton.setBackground(primaryColor);
		addButton.setForeground(Color.white);
		addButton.setFont(textBoxFont);
		addButton.setFocusPainted(false);
		addButton.setText("Add Record");
		mainPanel.add(addButton);
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String finalData, firstName, middleName, lastName, age, address,
				contactNumber;
				
				firstName = firstNameBox.getText().toString();
				middleName = middleNameBox.getText().toString();
				lastName = lastNameBox.getText().toString();
				age = ageBox.getText().toString();
				address =  addressBox.getText().toString();
				contactNumber = contactnumBox.getText().toString();
				
				finalData = firstName + dataBreaker 
						+ middleName + dataBreaker 
						+ lastName + dataBreaker
						+ age + dataBreaker
						+ address + dataBreaker
						+ contactNumber;
				
				if(isAdding) {
					model.addRow(new Object[] {firstName, middleName, lastName, age, address, contactNumber});
					
					try(BufferedWriter writer = new BufferedWriter(new FileWriter(residentsFilePath, true))) {
						writer.write(finalData);
						writer.newLine();
						writer.close();
						
						firstNameBox.setText("");
						middleNameBox.setText("");
						lastNameBox.setText("");
						ageBox.setText("");
						addressBox.setText("");
						contactnumBox.setText("");
						
						JOptionPane.showMessageDialog(null, "Successfully Recorded!");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						residentsRecords = Files.readAllLines(Paths.get(residentsFilePath));
						
						residentsRecords.set(recordsTable.getSelectedRow(), finalData);
						Files.write(Paths.get(residentsFilePath), residentsRecords);
						residentsRecords = Files.readAllLines(Paths.get(residentsFilePath));
						
						model.setValueAt(firstName, recordsTable.getSelectedRow(), 0);
						model.setValueAt(middleName, recordsTable.getSelectedRow(), 1);
						model.setValueAt(lastName, recordsTable.getSelectedRow(), 2);
						model.setValueAt(age, recordsTable.getSelectedRow(), 3);
						model.setValueAt(address, recordsTable.getSelectedRow(), 4);
						model.setValueAt(contactNumber, recordsTable.getSelectedRow(), 5);

						JOptionPane.showMessageDialog(null, "Successfully Updated!");
						mainFrame.dispose();
					} catch (Exception ex1) {
						System.out.println("Error: " + ex1.toString());
					}
					
				}
				

			}
		});
		
		if(!isAdding) {
			mainFrame.setTitle("LocallyLinked - Update Record");
			brandName.setText("LocallyLinked - Update Record");
			addButton.setText("Update");
			
			firstNameBox.setText(recordsTable.getValueAt(recordsTable.getSelectedRow(), 0).toString());
			middleNameBox.setText(recordsTable.getValueAt(recordsTable.getSelectedRow(), 1).toString());
			lastNameBox.setText(recordsTable.getValueAt(recordsTable.getSelectedRow(), 2).toString());
			ageBox.setText(recordsTable.getValueAt(recordsTable.getSelectedRow(), 3).toString());
			addressBox.setText(recordsTable.getValueAt(recordsTable.getSelectedRow(), 4).toString());
			contactnumBox.setText(recordsTable.getValueAt(recordsTable.getSelectedRow(), 5).toString());
		}
		
		mainFrame.setVisible(true);
		
	}
	
	public static void onLoadData() throws IOException {
		residentsRecords = Files.readAllLines(Paths.get(residentsFilePath));
		
		for(int i = 0; i < residentsRecords.size(); i++) {
			String firstName, middleName, lastName, age, address, contactNumber;
			String[] residentInfo = residentsRecords.get(i).split(dataBreaker);
			
			firstName = residentInfo[0];
			middleName = residentInfo[1];
			lastName = residentInfo[2];
			age = residentInfo[3];
			address = residentInfo[4];
			contactNumber = residentInfo[5];
			
			model.addRow(new Object[] {firstName, middleName, lastName, age, address, contactNumber});
			
		}
	}

}

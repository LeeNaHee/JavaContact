import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class GUI extends JFrame implements ActionListener{
	private JFrame frame, frame2;
	private JScrollPane scrollPane;
	private JPanel panel, panel2;
	private JTextField phoneNumTextField, addressTextField, emailTextField, nameTextField, searchTextField;
	private Container contentPane;
	private JLabel nameLabel, phoneNumLabel, addressLabel, emailLabel, searchNameLabel, searchPhoneLabel, searchAddressLabel, searchEmailLabel;
	private JButton deleteButton, checkButton, saveButton, modifyButton, addButton, searchButton;
	private JComboBox comboBox;
	private JTable table;
	private JTextArea nameTextArea, phoneTextArea, addressTextArea, emailTextArea;
	private JOptionPane errorMessage;
	private JComboBox cb;
		
	AddressBook ad;
	//ObjectOutputStream out = null;
	public GUI(AddressBook ad)
	{
		this.ad = ad;	
		DBConnection();
		GUI();
	}
	
	public void DBConnection()
	{
		Connection conn = null;
		try
		{
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/contact", "root", "1234");
		}
		catch(ClassNotFoundException e){
			errorMessage = new JOptionPane();
			JOptionPane.showMessageDialog(null, "Ŭ������ ã�� �� �����ϴ�.", "ERROR", JOptionPane.ERROR_MESSAGE);			
		}
		catch(SQLException e){
			errorMessage = new JOptionPane();
			JOptionPane.showMessageDialog(null, "SQL ������ �߻��߽��ϴ�.", "ERROR", JOptionPane.ERROR_MESSAGE);			
		}
		catch (Exception e) {
			errorMessage = new JOptionPane();
			JOptionPane.showMessageDialog(null, "������ �߻��߽��ϴ�.", "ERROR", JOptionPane.ERROR_MESSAGE);			
		}
	}
	
	// GUI���� �Լ� 
	private void GUI() {
		frame = new JFrame("��ȭ��ȣ��"); 
		frame.setPreferredSize(new Dimension(565, 550)); // ������ ũ�� ����
		frame.setLocation(300, 200); // ������ ��ġ ����
		
		// contentPane ����
		contentPane = frame.getContentPane();

		// ���̺� ����
		String colNames[] = {"�̸�", "��ȭ��ȣ", "�ּ�", "�̸���"};
		DefaultTableModel model = new DefaultTableModel(colNames, 0) 
		{
			//���̺� Ŭ���ص� ������ �� ������ �Ѵ�. 
			public boolean isCellEditable(int i, int j)
			{
				return false;
			}
		};
		
		frame.getContentPane().setLayout(null);
		table = new JTable(model);
		
		//table.addMouseListener(new MouseAction());
				
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 168, 525, 319);
		contentPane.add(scrollPane);
		
		// ���̺��� �������� ���ʿ� ��ġ�� �κ��� �г� ����
		panel = new JPanel();
		panel.setBounds(0, 0, 536, 159);
				
		// �̸�, ��ȭ��ȣ, �ּ�, �̸��Ͽ� ���� TextField ����
		nameTextField = new JTextField(6);
		nameTextField.setBounds(51, 17, 94, 28);
		panel.add(nameTextField);
		
		phoneNumTextField = new JTextField(13);
		phoneNumTextField.setBounds(210, 17, 110, 28);
		panel.add(phoneNumTextField);
		
		addressTextField = new JTextField(5);
		addressTextField.setBounds(51, 55, 94, 30);
		panel.add(addressTextField);
		
		emailTextField = new JTextField(8);
		emailTextField.setBounds(210, 57, 110, 28);
		panel.add(emailTextField);
		
		// ���, ���� ��ư ����
		addButton = new JButton("���");
		addButton.setBounds(342, 13, 91, 29);
		panel.setLayout(null);
		
		// ��Ϲ�ư�� ���� ActionListener
		addButton.addActionListener(this);
		
		// �̸�, ��ȭ��ȣ, �ּ�, �̸��Ͽ� ���� Label
		nameLabel = new JLabel("�̸�");
		nameLabel.setBounds(17, 13, 39, 35);
		panel.add(nameLabel);
								
		phoneNumLabel = new JLabel("��ȭ��ȣ");
		phoneNumLabel.setBounds(157, 23, 53, 15);
		panel.add(phoneNumLabel);
		
		addressLabel = new JLabel("�ּ�");
		addressLabel.setBounds(17, 67, 32, 15);
		panel.add(addressLabel);
				
		emailLabel = new JLabel("�̸���");
		emailLabel.setBounds(157, 67, 39, 15);
		panel.add(emailLabel);
		
		panel.add(addButton);
		contentPane.add(panel);
		
		// ����, ����, ����, ��ȸ ��ư
		deleteButton = new JButton("����");
		deleteButton.setBounds(342, 44, 91, 29);
		panel.add(deleteButton);
		
		deleteButton.addActionListener(this);
					
		checkButton = new JButton("��ȸ");
		checkButton.setBounds(435, 44, 91, 61);
		panel.add(checkButton);
		
		checkButton.addActionListener(this);
		
		/*
		saveButton = new JButton("����");
		saveButton.setBounds(435, 13, 91, 29);
		panel.add(saveButton);
		*/
		
		//saveButton.addActionListener(this);
		
		modifyButton = new JButton("����");
		modifyButton.setBounds(342, 76, 91, 29);
		panel.add(modifyButton);
		
		modifyButton.addActionListener(this);
		
		// �޺� �ڽ��� ���� ����
		String box[] = {"�̸�", "��ȭ��ȣ"};
		comboBox = new JComboBox(box);	
		comboBox.setBounds(17, 115, 128, 35);
		panel.add(comboBox);
				
		// �˻����� ����
		searchTextField = new JTextField();
		searchTextField.setBounds(149, 115, 284, 35);
		panel.add(searchTextField);
		searchTextField.setColumns(10);
		
		searchButton = new JButton("�˻�");
		searchButton.setBounds(435, 115, 91, 35);
		panel.add(searchButton);
		
		searchButton.addActionListener(this);
								
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);	
		
	}
	
	// �˻���ư�� ������ �� �ߴ� â 
	private void searchGui()
	{
		frame2 = new JFrame("�˻����");
		frame2.setPreferredSize(new Dimension(400, 300)); 
		frame2.setLocation(900, 200); 
		contentPane = frame2.getContentPane();
			
		panel2 = new JPanel();
		panel2.setBounds(33, 10, 356, 243);
		contentPane.add(panel2);
		panel2.setLayout(null);
		
		// �̸�, ��ȭ��ȣ, �ּ�, �̸��Ͽ� ���� ��
		searchNameLabel = new JLabel("�̸�");
		searchNameLabel.setBounds(45, 38, 50, 15);
		panel2.add(searchNameLabel);
		
		searchPhoneLabel = new JLabel("��ȭ��ȣ");
		searchPhoneLabel.setBounds(45, 77, 50, 15);
		panel2.add(searchPhoneLabel);
		
		searchAddressLabel = new JLabel("�ּ�");
		searchAddressLabel.setBounds(45, 127, 50, 15);
		panel2.add(searchAddressLabel);
		
		searchEmailLabel = new JLabel("�̸���");
		searchEmailLabel.setBounds(45, 184, 50, 15);
		panel2.add(searchEmailLabel);
		
		// �̸�, ��ȭ��ȣ, �ּ�, �̸��Ͽ� ���� TextArea
		nameTextArea = new JTextArea();
		nameTextArea.setBounds(139, 33, 144, 24);
		panel2.add(nameTextArea);
		
		phoneTextArea = new JTextArea();
		phoneTextArea.setBounds(139, 84, 144, 24);
		panel2.add(phoneTextArea);
		
		addressTextArea = new JTextArea();
		addressTextArea.setBounds(139, 127, 144, 24);
		panel2.add(addressTextArea);
		
		emailTextArea = new JTextArea();
		emailTextArea.setBounds(139, 184, 144, 24);
		panel2.add(emailTextArea);
				
		frame2.pack();
		frame2.setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// ���콺 Ŭ���� ���� �����, ���콺�� ���̺� ���� Ŭ���ϸ� �ؽ�Ʈ �ʵ�� �� ������ ������
	
	public class MouseAction extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			int row = table.getSelectedRow();
			Person p = null;
			try {
				p = ad.getPerson(row);
			} catch (SQLException e1) {
				nameTextField.setText(p.getName());
				phoneNumTextField.setText(p.getPhoneNum());
				addressTextField.setText(p.getPhoneNum());
				emailTextField.setText(p.getEmail());
			}			
		}
	}
	
	// �� ��ư�� ���� actionPerformed
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addButton)
		{
			try {
				addCord();
			} catch (HeadlessException | ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource() == searchButton)
		{	
			searchGui();
			searchCode();			
		}
		else if(e.getSource() == modifyButton)
		{
			try {
				modifyCode();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		else if(e.getSource() == checkButton)
		{
			try {
				checkCode();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		}
		else if(e.getSource() == deleteButton)
		{
			try {
				deleteCode();
			} catch (ClassNotFoundException | SQLException e1) {
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		/*
		else if(e.getSource() == saveButton)
		{
			saveCode();		
		}*/
	}

	// �ּҷ� ���� 
	private void deleteCode() throws Exception {
		
		// ���̺�� ���� ���� �޾ƿͼ� ���õ� ���� �����. 
		int row = table.getSelectedRow();
		String index = searchTextField.getText();
		if(row == -1)
			return;
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.removeRow(row);
		ad.delete(index);
		clearTextField();
	}

	/*
	// �ּҷ� ����
	private void saveCode() {
		try {
			out = new ObjectOutputStream(new FileOutputStream("addressbook.dat"));
		} catch (Exception ex) {
			errorMessage = new JOptionPane();
			JOptionPane.showMessageDialog(null, "���� ���� ������ �߻��߽��ϴ�.", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		try {
			ad.writeFile(out);
			errorMessage = new JOptionPane(); 
			JOptionPane.showMessageDialog(null, "����Ǿ����ϴ�", "���� ����", JOptionPane.ERROR_MESSAGE);
		} catch (FileNotFoundException fnfe) {
			errorMessage = new JOptionPane();
			JOptionPane.showMessageDialog(null, "������ ã�� �� �����ϴ�.", "ERROR", JOptionPane.ERROR_MESSAGE);
		} catch(IOException ioe) {
			errorMessage = new JOptionPane();
			JOptionPane.showMessageDialog(null, "���� ���� ������ �߻��߽��ϴ�.", "ERROR", JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			errorMessage = new JOptionPane();
			JOptionPane.showMessageDialog(null, "���� ���� ������ �߻��߽��ϴ�.", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
			
		finally
		{
			try {
				out.close();
			} catch (IOException ex) {
				errorMessage = new JOptionPane();
				JOptionPane.showMessageDialog(null, "���� ���� ������ �߻��߽��ϴ�.", "ERROR", JOptionPane.ERROR_MESSAGE);
			} catch(Exception ex) {
				errorMessage = new JOptionPane();
				JOptionPane.showMessageDialog(null, "���� ���� ������ �߻��߽��ϴ�.", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
	}*/

	
	// �ּҷ� ��ȸ
	private void checkCode() throws SQLException {
		int count = ad.getCount();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		// ��ϵ� �ּҷ��� ���� ���
		if(count == 0)
		{
			errorMessage = new JOptionPane();
			JOptionPane.showMessageDialog(null, "��ϵ� �ּҷ��� �����ϴ�", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			// ���̺� ������
			for(int i=0; i<count; i++)
			{
				String arr[] = new String[4];
				arr[0] = ad.getPerson(i).getName();
				arr[1] = ad.getPerson(i).getPhoneNum();
				arr[2] = ad.getPerson(i).getAddress();
				arr[3] = ad.getPerson(i).getEmail();
				
				String name = arr[0];	
				String phoneNum = arr[1];
				String address = arr[2];
				String email = arr[3];
				
				DefaultTableModel model2 = (DefaultTableModel) table.getModel();
				model2.addRow(arr);
			}
		}
		
	}

	// �ּҷ� ����
	private void modifyCode() throws SQLException {
		int index = table.getSelectedRow() + 1;
		String searchname = searchTextField.getText();
		
		if(index == -1) return;
		
		// �ؽ�Ʈ �ʵ忡�� �ּҷ� ����
		String arr[] = new String[4];
		arr[0] = nameTextField.getText();
		arr[1] = phoneNumTextField.getText();
		arr[2] = addressTextField.getText();
		arr[3] = emailTextField.getText();
		
		String name = arr[0];	
		String phoneNum = arr[1];
		String address = arr[2];
		String email = arr[3];	
		
		Person p= new Person(name, phoneNum, address, email);
		ad.modify(p, searchname);
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0); // ���̺��� ������ �����ٰ�
		checkCode(); // ������ �ּҷ����� �����ش�. 
		
		clearTextField();	
	}

	
	// �ּҷ� �˻�
	private void searchCode() {
		String name = searchTextField.getText();
		String phoneNum = searchTextField.getText();
		String field = (String)comboBox.getSelectedItem(); // �޺� �ڽ� ������ 
		
		try {
			// �޺� �ڽ��� �̸��� ��
			if(field.equals("�̸�"))
			{
				//Person p1 = ad.searchName(name);
				//Person p1 = ad.getPerson(nameIndex);
				Person p1 = ad.searchName(name);
				nameTextArea.setText(p1.getName());
				phoneTextArea.setText(p1.getPhoneNum());
				addressTextArea.setText(p1.getAddress());
				emailTextArea.setText(p1.getEmail());
			}
			// �޺� �ڽ��� ��ȭ��ȣ�� �� 
			else if(field.equals("��ȭ��ȣ"))
			{
				//Person phoneNumIndex= ad.searchPhoneNum(phoneNum);
				//Person p2 = ad.getPerson(phoneNumIndex);
				Person p2 = ad.searchPhoneNum(phoneNum);
				nameTextArea.setText(p2.getName());
				phoneTextArea.setText(p2.getPhoneNum());
				addressTextArea.setText(p2.getAddress());
				emailTextArea.setText(p2.getEmail());			
			}
												
		} catch (Exception e1) {
			// �˻��� ������ ���� ��� 
			frame2.setVisible(false);
			errorMessage = new JOptionPane();
			JOptionPane.showMessageDialog(null, "�˻��� ����� �����ϴ� ", "ERROR", JOptionPane.ERROR_MESSAGE);

		}
		searchTextField.setText("");
		
	}


	// �ּҷ� �߰� 
	private void addCord() throws HeadlessException, ClassNotFoundException, SQLException {
		String arr[] = new String[4];
		arr[0] = nameTextField.getText();
		arr[1] = phoneNumTextField.getText();
		arr[2] = addressTextField.getText();
		arr[3] = emailTextField.getText();
		
		String name = arr[0];	
		String phoneNum = arr[1];
		String address = arr[2];
		String email = arr[3];	

		// �������� üũ
		if(ad.checkName(name) == true)
		{
			errorMessage = new JOptionPane();
			JOptionPane.showMessageDialog(null, "�ٸ� �̸� �Է����ּ���", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		// ������ ��ȭ��ȣ üũ
		else if(ad.checkPhoneNum(phoneNum) == true)
		{
			errorMessage = new JOptionPane();
			JOptionPane.showMessageDialog(null, "�ٸ� ��ȭ��ȣ �Է����ּ���", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		// �̸��� ��ȭ��ȣ�� �ּ��� �Է��ϵ�Ȥ ��
		else if(arr[0].equals("") || arr[1].equals(""))
		{
			errorMessage = new JOptionPane();
			JOptionPane.showMessageDialog(null, "�̸� �Ǵ� ��ȭ��ȣ�� �Է����ּ���", "ERROR", JOptionPane.ERROR_MESSAGE);			
		}
		else
		{
			try {
			// �ƴ� ��� ���̺� �߰� 
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.addRow(arr);
			ad.add(new Person(name, phoneNum, address, email));
			}
			catch(Exception adde)
			{
				errorMessage = new JOptionPane();
				JOptionPane.showMessageDialog(null, "��ϰ��� �ο����� �ʰ��Ǿ����ϴ�", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
		clearTextField();
		
	}
	
	// �ؽ�Ʈ �ʵ� �ʱ�ȭ 
	private void clearTextField()
	{
		nameTextField.setText("");
		phoneNumTextField.setText("");
		addressTextField.setText("");
		emailTextField.setText("");
	}
}

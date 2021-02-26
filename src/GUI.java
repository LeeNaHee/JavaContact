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
			JOptionPane.showMessageDialog(null, "클래스를 찾을 수 없습니다.", "ERROR", JOptionPane.ERROR_MESSAGE);			
		}
		catch(SQLException e){
			errorMessage = new JOptionPane();
			JOptionPane.showMessageDialog(null, "SQL 오류가 발생했습니다.", "ERROR", JOptionPane.ERROR_MESSAGE);			
		}
		catch (Exception e) {
			errorMessage = new JOptionPane();
			JOptionPane.showMessageDialog(null, "오류가 발생했습니다.", "ERROR", JOptionPane.ERROR_MESSAGE);			
		}
	}
	
	// GUI구성 함수 
	private void GUI() {
		frame = new JFrame("전화번호부"); 
		frame.setPreferredSize(new Dimension(565, 550)); // 프레임 크기 설정
		frame.setLocation(300, 200); // 프레임 위치 선정
		
		// contentPane 설정
		contentPane = frame.getContentPane();

		// 테이블 설정
		String colNames[] = {"이름", "전화번호", "주소", "이메일"};
		DefaultTableModel model = new DefaultTableModel(colNames, 0) 
		{
			//테이블 클릭해도 수정할 수 없도록 한다. 
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
		
		// 테이블을 기준으로 윗쪽에 위치할 부분의 패널 설정
		panel = new JPanel();
		panel.setBounds(0, 0, 536, 159);
				
		// 이름, 전화번호, 주소, 이메일에 대한 TextField 설정
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
		
		// 등록, 종료 버튼 설정
		addButton = new JButton("등록");
		addButton.setBounds(342, 13, 91, 29);
		panel.setLayout(null);
		
		// 등록버튼에 대한 ActionListener
		addButton.addActionListener(this);
		
		// 이름, 전화번호, 주소, 이메일에 대한 Label
		nameLabel = new JLabel("이름");
		nameLabel.setBounds(17, 13, 39, 35);
		panel.add(nameLabel);
								
		phoneNumLabel = new JLabel("전화번호");
		phoneNumLabel.setBounds(157, 23, 53, 15);
		panel.add(phoneNumLabel);
		
		addressLabel = new JLabel("주소");
		addressLabel.setBounds(17, 67, 32, 15);
		panel.add(addressLabel);
				
		emailLabel = new JLabel("이메일");
		emailLabel.setBounds(157, 67, 39, 15);
		panel.add(emailLabel);
		
		panel.add(addButton);
		contentPane.add(panel);
		
		// 삭제, 저장, 수정, 조회 버튼
		deleteButton = new JButton("삭제");
		deleteButton.setBounds(342, 44, 91, 29);
		panel.add(deleteButton);
		
		deleteButton.addActionListener(this);
					
		checkButton = new JButton("조회");
		checkButton.setBounds(435, 44, 91, 61);
		panel.add(checkButton);
		
		checkButton.addActionListener(this);
		
		/*
		saveButton = new JButton("저장");
		saveButton.setBounds(435, 13, 91, 29);
		panel.add(saveButton);
		*/
		
		//saveButton.addActionListener(this);
		
		modifyButton = new JButton("수정");
		modifyButton.setBounds(342, 76, 91, 29);
		panel.add(modifyButton);
		
		modifyButton.addActionListener(this);
		
		// 콤보 박스에 대한 설정
		String box[] = {"이름", "전화번호"};
		comboBox = new JComboBox(box);	
		comboBox.setBounds(17, 115, 128, 35);
		panel.add(comboBox);
				
		// 검색관련 설정
		searchTextField = new JTextField();
		searchTextField.setBounds(149, 115, 284, 35);
		panel.add(searchTextField);
		searchTextField.setColumns(10);
		
		searchButton = new JButton("검색");
		searchButton.setBounds(435, 115, 91, 35);
		panel.add(searchButton);
		
		searchButton.addActionListener(this);
								
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);	
		
	}
	
	// 검색버튼을 눌렀을 시 뜨는 창 
	private void searchGui()
	{
		frame2 = new JFrame("검색결과");
		frame2.setPreferredSize(new Dimension(400, 300)); 
		frame2.setLocation(900, 200); 
		contentPane = frame2.getContentPane();
			
		panel2 = new JPanel();
		panel2.setBounds(33, 10, 356, 243);
		contentPane.add(panel2);
		panel2.setLayout(null);
		
		// 이름, 전화번호, 주소, 이메일에 대한 라벨
		searchNameLabel = new JLabel("이름");
		searchNameLabel.setBounds(45, 38, 50, 15);
		panel2.add(searchNameLabel);
		
		searchPhoneLabel = new JLabel("전화번호");
		searchPhoneLabel.setBounds(45, 77, 50, 15);
		panel2.add(searchPhoneLabel);
		
		searchAddressLabel = new JLabel("주소");
		searchAddressLabel.setBounds(45, 127, 50, 15);
		panel2.add(searchAddressLabel);
		
		searchEmailLabel = new JLabel("이메일");
		searchEmailLabel.setBounds(45, 184, 50, 15);
		panel2.add(searchEmailLabel);
		
		// 이름, 전화번호, 주소, 이메일에 대한 TextArea
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
	
	// 마우스 클릭에 대한 어댑터, 마우스로 태이블 행을 클릭하면 텍스트 필드로 그 정보를 가져옴
	
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
	
	// 각 버튼에 대한 actionPerformed
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

	// 주소록 삭제 
	private void deleteCode() throws Exception {
		
		// 테이블로 부터 행을 받아와서 선택된 행을 지운다. 
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
	// 주소록 저장
	private void saveCode() {
		try {
			out = new ObjectOutputStream(new FileOutputStream("addressbook.dat"));
		} catch (Exception ex) {
			errorMessage = new JOptionPane();
			JOptionPane.showMessageDialog(null, "파일 관련 오류가 발생했습니다.", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		try {
			ad.writeFile(out);
			errorMessage = new JOptionPane(); 
			JOptionPane.showMessageDialog(null, "저장되었습니다", "저장 성공", JOptionPane.ERROR_MESSAGE);
		} catch (FileNotFoundException fnfe) {
			errorMessage = new JOptionPane();
			JOptionPane.showMessageDialog(null, "파일을 찾을 수 없습니다.", "ERROR", JOptionPane.ERROR_MESSAGE);
		} catch(IOException ioe) {
			errorMessage = new JOptionPane();
			JOptionPane.showMessageDialog(null, "파일 관련 오류가 발생했습니다.", "ERROR", JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			errorMessage = new JOptionPane();
			JOptionPane.showMessageDialog(null, "파일 관련 오류가 발생했습니다.", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
			
		finally
		{
			try {
				out.close();
			} catch (IOException ex) {
				errorMessage = new JOptionPane();
				JOptionPane.showMessageDialog(null, "파일 관련 오류가 발생했습니다.", "ERROR", JOptionPane.ERROR_MESSAGE);
			} catch(Exception ex) {
				errorMessage = new JOptionPane();
				JOptionPane.showMessageDialog(null, "파일 관련 오류가 발생했습니다.", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
	}*/

	
	// 주소록 조회
	private void checkCode() throws SQLException {
		int count = ad.getCount();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		// 등록된 주소록이 없는 경우
		if(count == 0)
		{
			errorMessage = new JOptionPane();
			JOptionPane.showMessageDialog(null, "등록된 주소록이 없습니다", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			// 테이블에 보여줌
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

	// 주소록 수정
	private void modifyCode() throws SQLException {
		int index = table.getSelectedRow() + 1;
		String searchname = searchTextField.getText();
		
		if(index == -1) return;
		
		// 텍스트 필드에서 주소록 수정
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
		model.setRowCount(0); // 테이블을 완전히 지웠다가
		checkCode(); // 수정된 주소록으로 보여준다. 
		
		clearTextField();	
	}

	
	// 주소록 검색
	private void searchCode() {
		String name = searchTextField.getText();
		String phoneNum = searchTextField.getText();
		String field = (String)comboBox.getSelectedItem(); // 콤보 박스 아이템 
		
		try {
			// 콤보 박스가 이름일 때
			if(field.equals("이름"))
			{
				//Person p1 = ad.searchName(name);
				//Person p1 = ad.getPerson(nameIndex);
				Person p1 = ad.searchName(name);
				nameTextArea.setText(p1.getName());
				phoneTextArea.setText(p1.getPhoneNum());
				addressTextArea.setText(p1.getAddress());
				emailTextArea.setText(p1.getEmail());
			}
			// 콤보 박스가 전화번호일 때 
			else if(field.equals("전화번호"))
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
			// 검색된 정보가 없는 경우 
			frame2.setVisible(false);
			errorMessage = new JOptionPane();
			JOptionPane.showMessageDialog(null, "검색된 사람이 없습니다 ", "ERROR", JOptionPane.ERROR_MESSAGE);

		}
		searchTextField.setText("");
		
	}


	// 주소록 추가 
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

		// 동명이인 체크
		if(ad.checkName(name) == true)
		{
			errorMessage = new JOptionPane();
			JOptionPane.showMessageDialog(null, "다른 이름 입력해주세요", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		// 동일한 전화번호 체크
		else if(ad.checkPhoneNum(phoneNum) == true)
		{
			errorMessage = new JOptionPane();
			JOptionPane.showMessageDialog(null, "다른 전화번호 입력해주세요", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		// 이름과 전화번호는 최소한 입력하도혹 함
		else if(arr[0].equals("") || arr[1].equals(""))
		{
			errorMessage = new JOptionPane();
			JOptionPane.showMessageDialog(null, "이름 또는 전화번호를 입력해주세요", "ERROR", JOptionPane.ERROR_MESSAGE);			
		}
		else
		{
			try {
			// 아닐 경우 테이블에 추가 
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.addRow(arr);
			ad.add(new Person(name, phoneNum, address, email));
			}
			catch(Exception adde)
			{
				errorMessage = new JOptionPane();
				JOptionPane.showMessageDialog(null, "등록가능 인원수가 초과되었습니다", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
		clearTextField();
		
	}
	
	// 텍스트 필드 초기화 
	private void clearTextField()
	{
		nameTextField.setText("");
		phoneNumTextField.setText("");
		addressTextField.setText("");
		emailTextField.setText("");
	}
}

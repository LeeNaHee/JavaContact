import java.awt.Component;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import java.sql.*;

public class AddressBook {
		
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private PreparedStatement psmt;
	
	// addressbook ������
	public AddressBook(Connection conn, Statement stmt, ResultSet rs, PreparedStatement psmt) 
	{
		this.conn = conn;
		this.stmt = stmt;
		this.rs = rs;
		this.psmt = psmt;
	}
		
	// ��� �޼ҵ� 
	public void add(Person p)throws Exception{ 
		String sql;

		sql = "insert into member (name,phoneNum,address,email) ";
		sql+= "values (?,?,?,?)";

		psmt = conn.prepareStatement(sql);
		psmt.setString(1, p.getName());
		psmt.setString(2, p.getPhoneNum());
		psmt.setString(3, p.getAddress());
		psmt.setString(4, p.getEmail());
			
		psmt.executeUpdate();
	}
	
	// ��ϵ� ��� �� ��ȯ �޼ҵ� 
	public int getCount() throws SQLException{
		String sql = "select *from member";
		
		rs = stmt.executeQuery(sql);
		rs.last();
		int rowCnt = rs.getRow();
		while(true) 
		{
			return rowCnt;			
		}
	}
		
	
	// �ּҷ� ���� �޼ҵ�
	public void delete(String name) throws SQLException, ClassNotFoundException{ 
		String sql="delete from member where name=?";
		psmt = conn.prepareStatement(sql);
		psmt.setString(1, name);
		psmt.executeUpdate();	
	}
	
	// �ּҷ� ���� �޼ҵ� 
	public void modify(Person p, String name) throws SQLException{    
		String sql = "update member set name =?, phoneNum=?, address=?, email=? where name=?";
  	   
		psmt = conn.prepareStatement(sql);
  	    psmt.setString(1, p.getName());
  	    psmt.setString(2, p.getPhoneNum());
  	    psmt.setString(3, p.getAddress());
  	    psmt.setString(4, p.getEmail());
  	    psmt.setString(5, name);
  	    psmt.executeUpdate();   
     
	}
	
	// �̸�����  �˻� 
	public Person searchName(String searchname) throws Exception
	{
		Person p = null;
		String sql = "select name, phoneNum, address, email from member where name =?";
		psmt = conn.prepareStatement(sql);
		psmt.setString(1, searchname);
		rs = psmt.executeQuery();
			
		if(rs.next())
		{
			String name = rs.getString("name");
			String phoneNum = rs.getString("phoneNum");
			String address = rs.getString("address");
			String email = rs.getString("email");
			p = new Person(name, phoneNum, address, email);	
		}
		return p;
	}
	
	// ��ȭ��ȣ�� �˻� 
	public Person searchPhoneNum(String searchphoneNum) throws Exception
	{
		Person p = null;
		
		String sql = "select name, phoneNum, address, email from member where phoneNum =?";
		psmt = conn.prepareStatement(sql);
		psmt.setString(1, searchphoneNum);
		rs = psmt.executeQuery();
			
		if(rs.next())
		{
			String name = rs.getString("name");
			String phoneNum = rs.getString("phoneNum");
			String address = rs.getString("address");
			String email = rs.getString("email");
			p = new Person(name, phoneNum, address, email);
		}

		return p;
	}
	
	// Person ��ü �Ѱ��ִ� �޼ҵ�
	public Person getPerson(int index) throws SQLException{  
		String sql = "select *from member";
		rs = stmt.executeQuery(sql);
		while(rs.next()) 
		{
			if(rs.getRow()==index+1)
			{
				String name =rs.getString("name");
				String phonNum =rs.getString("phoneNum");
				String address =rs.getString("adress");
				String email =rs.getString("email");
			
				Person p =new Person(name,phonNum,address,email);	
		
				return p;
			}
		}
		
		Person p =new Person("","","","");	
		return p;
	}
	
	// �������� Ȯ�� �޼ҵ�
	public boolean checkName(String name) throws ClassNotFoundException, SQLException {		
		rs = stmt.executeQuery("select * from member where name = '" + name + "';");
		
		while(rs.next())
		{
			if(rs.getString("name").equals(name))
			{
				return true;
			}
		}
		return false;
				
	}
			
	//��ϵ� ��ȭ��ȣ�� �ִ��� Ȯ�� �޼ҵ�
	public boolean checkPhoneNum(String phoneNum) throws ClassNotFoundException, SQLException {
		String sql = "select * from";
		
		rs = stmt.executeQuery(sql);
		
		while(rs.next())
		{
			if(rs.getString("phoneNum").equals(phoneNum))
			{
				return true;
			}
		}
		
		return false;
	}
	
			
}

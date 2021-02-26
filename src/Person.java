import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Person implements java.io.Serializable {
	private String name;		 // �̸� �ʵ�
	private String phoneNum;	 // ��ȭ��ȣ �ʵ�
	private String address;	 	 // ���ּ� �ʵ�
	private String email;		 // �̸��� �ʵ�
		
	public Person(String name, String phoneNum, String address, String email){ 
		this.name=name;
		this.phoneNum = phoneNum;
		this.address = address;
		this.email = email;
	}
	public Person() {
		this.name=null;
		this.phoneNum = null;
		this.address = null;
		this.email = null;
	}
	public void setName(String name){ 		// �̸� ������
		this.name=name;
	}		
	public void setPhoneNum(String phoneNum){ 	// ��ȭ��ȣ ������
		this.phoneNum = phoneNum;
	}
	public void setAddress(String address){ 	// ���ּ� ������
		this.address = address;
	}
	public void setEmail(String email){ 		// �̸��� ������
		this.email = email;
	}
	public String getEmail(){ 			// �̸��� ������
		return email;
	}
	public String getName(){ 			// �̸� ������
		return name;
	}
	public String getPhoneNum(){ 		// ��ȭ��ȣ ������
		return phoneNum;
	}
	public String getAddress(){ 		// ���ּ� ������
		return address;
	}
	
}

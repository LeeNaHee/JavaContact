import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.table.DefaultTableModel;

public class UI {
			
public static void main(String[] args) throws Exception  {
	
	    AddressBook ad = null;
	    
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement psmt = null;
		
		ad = new AddressBook(conn, stmt, rs, psmt);
				
		GUI gui = new GUI(ad);
	}
}


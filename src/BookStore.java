/*
||========================================================
|| CS680: Intro to Software Engineering
|| EECS Dept, Wichita State University
|| Spring, 2014
||
|| Student: Chinh D. Trang
|| WSU ID: C824N474
|| Programming Project
|| Date: 04/01/2014
||========================================================
*/


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.*;
import java.util.*;
import java.text.*;

class BookStore extends Frame implements ActionListener, ItemListener{
	Choice cbBookCat, cbBookName;
	TextField txtInvoiceDate,txtCustomer, txtUnitPrice, txtQuantity, txtAmount;
	Button bSave, bCancel,bView;
	GridBagLayout gb;
	GridBagConstraints gbc;
	
	public BookStore(String title){
		super(title);

		//===========
		Panel p1 = new Panel();
		p1.setBackground(Color.orange);
		add(p1,BorderLayout.NORTH);
		
		Label l = new Label("Book Store System");
		Font f = new Font("Arial",Font.BOLD,20);
		l.setFont(f);
		p1.add(l);
		
		//===========
		Panel p2 = new Panel();
		add(p2,BorderLayout.CENTER);
		gb = new GridBagLayout();
		gbc = new GridBagConstraints();
		p2.setLayout(gb);
		
		Date d = new Date();
		SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy");

		addComp(p2,new Label("Invoice Date :"),0,0,1,1);
		txtInvoiceDate = new TextField(40);
		txtInvoiceDate.setText(sdf.format(d));
		addComp(p2,txtInvoiceDate,0,1,1,1);

		addComp(p2,new Label("Customer Name :"),1,0,1,1);
		txtCustomer = new TextField(40);
		addComp(p2,txtCustomer,1,1,1,1);

		//=====
		addComp(p2,new Label("Book Catalogue :"),2,0,1,1);
		cbBookCat = new Choice(); cbBookCat.addItemListener(this);
		cbBookCat.add("Computer");
		cbBookCat.add("Programming");
		cbBookCat.add("Novel");
		cbBookCat.add("History");
		addComp(p2,cbBookCat,2,1,1,1);
		
		//=====
		addComp(p2,new Label("Book title :"),3,0,1,1);
		cbBookName = new Choice();
		addComp(p2,cbBookName,3,1,1,1);
		
		//====
		addComp(p2,new Label("Unit price :"),4,0,1,1);
		txtUnitPrice = new TextField(40);
		addComp(p2,txtUnitPrice,4,1,1,1);

		//====
		addComp(p2,new Label("Quantity :"),5,0,1,1);
		txtQuantity = new TextField(40);
		addComp(p2,txtQuantity,5,1,1,1);

		//====
	
		addComp(p2,new Label("Amount :"),6,0,1,1);
		txtAmount = new TextField(40);
		txtAmount.setEnabled(false);
		addComp(p2,txtAmount,6,1,1,1);

		//============
		Panel p3 = new Panel();
		p3.setBackground(Color.orange);
		add(p3, BorderLayout.SOUTH);
		
		bSave = new Button("Save");
		bCancel = new Button("Cancel");
		bView = new Button("View");
		p3.add(bSave); bSave.addActionListener(this);
		p3.add(bCancel); bCancel.addActionListener(this);
		p3.add(bView); bView.addActionListener(this);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				System.exit(0);
			}
			});
			
			
		setSize(500,300);
		setVisible(true);
		txtCustomer.requestFocus();
		
	}
	
	public void addComp(Panel p, Component com, int row, int col, int rows, int cols){
		gbc.gridx = col;
		gbc.gridy = row;
		gbc.gridwidth = cols;
		gbc.gridheight = rows;
		gbc.fill = GridBagConstraints.BOTH;
		gb.setConstraints(com,gbc);
		p.add(com);
	}
	
	public void itemStateChanged(ItemEvent ie){
		int i = cbBookCat.getSelectedIndex();
		cbBookName.removeAll();
		
		switch (i){
			case 0:{
				cbBookName.add("How to use the MacIntosh Computer");
				cbBookName.add("Computer - friend or enemy");
				cbBookName.add("Phylosophy of the Computer");
				break;
			}
			case 1:{
				cbBookName.add("Logic programming with C");
				cbBookName.add("HTML, DHTML and Javascript");
				cbBookName.add("Implementing SQL Server 2000");
				break;
			}
			case 2:{
				cbBookName.add("Gone with the wind");
				cbBookName.add("Murder on the Orient Express");
				cbBookName.add("Harry Potter and the Chamber of Secrets");
				break;
			}
			case 3:{
				cbBookName.add("Ancient Civilization");
				cbBookName.add("The Shape of the Past");
				cbBookName.add("The Lessons of History");
				break;
			}
		}
	}
	
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==bSave){
			if(validation()) save();
		}
		else if(ae.getSource()==bCancel){
			reset();
		}
		else if(ae.getSource()==bView){
			view();
		}
	}
	
	public boolean validation(){
		
		if(txtCustomer.getText().trim().length()==0){
			javax.swing.JOptionPane.showMessageDialog(null,"Customer name could not be blank!");
			txtCustomer.requestFocus();
			return false;
		}
		
		if(cbBookName.getSelectedIndex()==-1){
			javax.swing.JOptionPane.showMessageDialog(null,"Book title could not be blank!");
			cbBookCat.requestFocus();
			return false;
		}


		float price, quantity;
		price = quantity =0;
		
		if(txtUnitPrice.getText().trim().length()==0){
			javax.swing.JOptionPane.showMessageDialog(null,"Unit price could not be blank!");
			txtUnitPrice.requestFocus();
			return false;
		}
		else
		{
			try {
				price = Float.parseFloat(txtUnitPrice.getText().trim());
		  }
		  catch (NumberFormatException ex) {
				javax.swing.JOptionPane.showMessageDialog(null,"Unit price could be number!");
				txtUnitPrice.requestFocus();
				return false;
		  }
		}
		
		if(txtQuantity.getText().trim().length()==0){
			javax.swing.JOptionPane.showMessageDialog(null,"Quantity could not be blank!");
			txtQuantity.requestFocus();
			return false;
		}
		else
		{
			try {
				quantity = Float.parseFloat(txtQuantity.getText().trim());
		  }
		  catch (NumberFormatException ex) {
				javax.swing.JOptionPane.showMessageDialog(null,"Quantity could be number !");
				txtQuantity.requestFocus();
				return false;
		  }
		}
		
		txtAmount.setText(price*quantity+"");
		return true;
	}	
	
	public void save(){
		String s="";
		s = txtInvoiceDate.getText();
		s = s + "; " + txtCustomer.getText().trim();
		s = s + "; " + cbBookCat.getSelectedItem();
		s = s + "; " + cbBookName.getSelectedItem();
		s = s + "; " + txtUnitPrice.getText();
		s = s + "; " + txtQuantity.getText();
		s = s + "; " + txtAmount.getText();
		try {
			PrintStream ps = new PrintStream(new FileOutputStream("BookShop.txt",true));
			ps.println(s);
			ps.close();
	  }
	  catch (IOException ex) {
	  	System.out.println ("File Writing Error!");
	  }
	}
	
	public void reset(){
		txtCustomer.setText(""); 
		txtCustomer.requestFocus();
		
		cbBookName.removeAll();
		cbBookCat.select(0);
		
		txtUnitPrice.setText("");
		txtQuantity.setText("");
		txtAmount.setText("");
	}
	
	public void view(){
		try {
			Runtime r = Runtime.getRuntime();
			Process p = r.exec("Notepad.exe BookShop.dat");
	  }
	  catch (IOException ex) {
	  	System.out.println ("File Reading Error");
	  }
	  catch(Exception ex){
	  	System.out.println ("Runtime Error");
	  }
	}
	
	public static void main(String [] s){
		new BookStore("Book Store System");	
	}
}
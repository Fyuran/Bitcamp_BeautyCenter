package com.bitcamp.centro.estetico.gui;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import com.bitcamp.centro.estetico.models.Customer;

public class SearchCustomerDialog extends JDialog{

	
	private Customer selectedCustomer;
    private List<Customer> customers;
    private JTextField textField;
    private JList<Customer> customerList;
    
	public SearchCustomerDialog(Frame owner, List<Customer> customers) {
		super(owner, "Seleziona Cliente", true);
		this.customers = customers;
		initialize();
		
		populateCustomersList();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	    this.setBounds(100, 100, 335, 500);
	    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    getContentPane().setLayout(null);
	    
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setBounds(10, 49, 301, 375);
	    getContentPane().add(scrollPane);
	    
	    customerList = new JList<>();
	    scrollPane.setViewportView(customerList);	    
	    
	    JButton searchButton = new JButton("Cerca");
	    searchButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		
	    	}
	    });
	    searchButton.setBounds(145, 18, 85, 21);
	    getContentPane().add(searchButton);
	    
	    JTextField searchText = new JTextField();
	    searchText.setBounds(10, 19, 125, 19);
	    getContentPane().add(searchText);
	    searchText.setColumns(10);
	    
	    JButton okButton = new JButton("Ok");
	    
	    okButton.setBounds(134, 432, 85, 21);
	    getContentPane().add(okButton);
	    
	    JButton cancelButton = new JButton("Annulla");
	    
	    cancelButton.setBounds(226, 432, 85, 21);
	    getContentPane().add(cancelButton);
	    
	    JButton resetButton = new JButton("Pulisci");
	    resetButton.setBounds(226, 18, 85, 21);
	    getContentPane().add(resetButton);
	    
	    
	    okButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		selectedCustomer = customerList.getSelectedValue();
	    		if(selectedCustomer==null) {
	    			JOptionPane.showMessageDialog(SearchCustomerDialog.this, "Per favore, seleziona un cliente.");
	    			return;
	    		}
	            dispose();
	    	}
	    });
	    
	    cancelButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		dispose();
	    	}
	    });
	}
	
	
	private void populateCustomersList() {
		DefaultListModel<Customer> listModel = new DefaultListModel<>();
		
		for (Customer customer : customers) {			
		    listModel.addElement(customer);
		}
		customerList.setModel(listModel);
	}
	
	public Customer getSelectedCustomer() {
        return selectedCustomer;
    }
}

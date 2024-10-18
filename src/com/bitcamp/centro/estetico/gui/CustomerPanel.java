package com.bitcamp.centro.estetico.gui;

import java.awt.Font;
import java.util.List;
import java.util.Optional;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;

import com.bitcamp.centro.estetico.DAO.CustomerDAO;
import com.bitcamp.centro.estetico.gui.render.CustomTableCellRenderer;
import com.bitcamp.centro.estetico.models.Customer;
import com.github.lgooddatepicker.components.DatePicker;

public class CustomerPanel extends BasePanel<Customer> {

	private static final long serialVersionUID = 1L;

	private static JTextField txfName;
	private static JTextField txfSurname;
	private static JTextField txfSearchBar;
	private static JTextField txfPhone;
	private static JTextField txfMail;
	private static JTextField txfAddress;
	private static JTextField txfIban;
	private static JTextField txfBirthplace;
	private static JRadioButton femaleRadioBtn;
	private static JRadioButton maleRadioBtn;
	private static DatePicker txfBirthday;
	private static JTextArea txfNotes;
	private static JTextField txfPIVA;
	private static JTextField txfRecipientCode;

	private static JLabel lbOutput;

	private static final int _ISENABLEDCOL = 14;

	public CustomerPanel() {
		setLayout(null);
		setName("Clienti");
		setTitle("GESTIONE CLIENTI");
		setSize(1024,768);
		btnSearch.setBounds(206, 8, 40, 40);
		txfSearchBar.setBounds(23, 14, 168, 24);
		btnFilter.setBounds(256, 8, 40, 40);
		btnInsert.setBounds(792, 8, 40, 40);
		btnUpdate.setBounds(842, 8, 40, 40);
		btnDisable.setBounds(892, 8, 40, 40);
		btnDelete.setBounds(742, 8, 40, 40);
		lbOutput.setBounds(306, 8, 438, 41);

		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer(customerModel, _ISENABLEDCOL));

		// label e textfield degli input
		JLabel lblName = new JLabel("Nome:");
		lblName.setBounds(3, 26, 170, 14);
		lblName.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txfName = new JTextField();
		txfName.setBounds(169, 23, 220, 20);
		txfName.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		txfName.setColumns(10);

		JLabel lblSurname = new JLabel("Cognome:");
		lblSurname.setBounds(3, 65, 170, 17);
		lblSurname.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txfSurname = new JTextField();
		txfSurname.setBounds(169, 63, 220, 20);
		txfSurname.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		txfSurname.setColumns(10);

		JLabel lblBirthday = new JLabel("Data di nascita:");
		lblBirthday.setBounds(3, 108, 170, 14);
		lblBirthday.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txfMail = new JTextField();
		txfMail.setBounds(709, 105, 220, 20);
		txfMail.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		txfMail.setColumns(10);

		txfPhone = new JTextField();
		txfPhone.setBounds(709, 145, 220, 20);
		txfPhone.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		txfPhone.setColumns(10);

		txfAddress = new JTextField();
		txfAddress.setBounds(709, 23, 220, 20);
		txfAddress.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		txfAddress.setColumns(10);

		JLabel lblMail = new JLabel("Mail:");
		lblMail.setBounds(491, 108, 170, 14);
		lblMail.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		JLabel lblPhone = new JLabel("Telefono:");
		lblPhone.setBounds(491, 148, 170, 14);
		lblPhone.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		JLabel lblAddress = new JLabel("Indirizzo:");
		lblAddress.setBounds(491, 26, 170, 14);
		lblAddress.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txfBirthday = new DatePicker();
		txfBirthday.setBounds(169, 103, 220, 25);

		JLabel lblBirthPlace = new JLabel("Città di nascita:");
		lblBirthPlace.setBounds(491, 66, 170, 14);
		lblBirthPlace.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txfBirthplace = new JTextField();
		txfBirthplace.setBounds(709, 63, 220, 20);
		txfBirthplace.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		txfBirthplace.setColumns(10);

		ButtonGroup genderBtnGroup = new ButtonGroup();
		maleRadioBtn = new JRadioButton("Uomo");
		maleRadioBtn.setBounds(491, 222, 75, 23);
		maleRadioBtn.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		genderBtnGroup.add(maleRadioBtn);

		femaleRadioBtn = new JRadioButton("Donna");
		femaleRadioBtn.setBounds(568, 222, 75, 23);
		femaleRadioBtn.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
		genderBtnGroup.add(femaleRadioBtn);

		JLabel notesLbl = new JLabel("Note:");
		notesLbl.setBounds(491, 279, 61, 16);
		notesLbl.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));

		txfNotes = new JTextArea();
		txfNotes.setBounds(709, 251, 220, 72);
		txfNotes.setBorder(UIManager.getBorder("TextField.border"));

		txfIban = new JTextField();
		txfIban.setBounds(169, 303, 220, 20);
		txfIban.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
		txfIban.setColumns(10);

		JLabel lbIban = new JLabel("IBAN:");
		lbIban.setBounds(3, 306, 170, 14);
		lbIban.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
	}

	@Override
	void search() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'search'");
	}

	@Override
	Optional<Customer> insertElement() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'insertElement'");
	}

	@Override
	int updateElement() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'updateElement'");
	}

	@Override
	int deleteElement() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'deleteElement'");
	}

	@Override
	int disableElement() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'disableElement'");
	}

	@Override
	void populateTable() {
		customerModel.setRowCount(0);
		List<Object[]> data = CustomerDAO.toTableRowAll();
		if (!data.isEmpty()) {
			for (Object[] row : data) {
				customerModel.addRow(row);
			}
		} else {
			lbOutput.setText("Lista Clienti vuota");
		}
	}

	@Override
	void clearTxfFields() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'clearTxfFields'");
	}

	@Override
	ListSelectionListener getListSelectionListener() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getListSelectionListener'");
	}

	@Override
	boolean isDataValid() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'isDataValid'");
	}
}
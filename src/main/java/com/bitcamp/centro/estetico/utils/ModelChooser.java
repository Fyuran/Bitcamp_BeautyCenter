package com.bitcamp.centro.estetico.utils;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.bitcamp.centro.estetico.gui.AbstractBasePanel;
import com.bitcamp.centro.estetico.gui.render.ButtonTableCellRenderer;
import com.bitcamp.centro.estetico.gui.render.CustomTableCellRenderer;
import com.bitcamp.centro.estetico.gui.render.NonEditableTableModel;
import com.bitcamp.centro.estetico.models.Model;
import com.github.weisj.darklaf.components.border.DarkBorders;

public class ModelChooser<T extends Model> extends JDialog {

	private final JPanel panel;
	private final JLabel lbTitle;
	private final JTable table;
	private final NonEditableTableModel<T> model;
	private final JPanel panel_1;
	private final JScrollPane scrollPane;
	private final JButton btnInsert;
	private final JButton btnDelete;
	private final JLabel lbOutput;

	public ModelChooser(JFrame owner, String title, int selectionMode, List<T> returnData) {
		this(owner, title, Dialog.ModalityType.DOCUMENT_MODAL, selectionMode, returnData);
	}

	/**
	 * @wbp.parser.constructor
	 */
	public ModelChooser(JFrame owner, String title, Dialog.ModalityType modalityType, int selectionMode,
			List<T> returnData) {
		super(owner, title, modalityType);
		setSize(1000, 480);
		setMinimumSize(getSize());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 0.2;
		gbc.weighty = 0.2;
		setLayout(gridBagLayout);

		panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		GridBagLayout gbl_panel = new GridBagLayout();
		panel.setLayout(gbl_panel);
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(panel, gbc);
		
		Font font = new Font("Microsoft Sans Serif", Font.BOLD, 16);
		lbOutput = new JLabel();
		lbOutput.setForeground(new Color(0, 204, 51));
		lbOutput.setFont(font);
		lbOutput.setHorizontalAlignment(SwingConstants.CENTER);
		gbc.gridx = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridy = 1;
		panel.add(lbOutput, gbc);

		lbTitle = new JLabel("PLACEHOLDER");
		lbTitle.setFont(font);
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(lbTitle, gbc);

		btnInsert = new JButton("Assegna Elemento/i");
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(btnInsert, gbc);

		btnDelete = new JButton("Rimuovi assegnazione/i");
		gbc.gridx = 4;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.EAST;
		panel.add(btnDelete, gbc);
		btnDelete.setRolloverEnabled(true);
		btnDelete.setRolloverIcon(new ImageIcon(
				AbstractBasePanel.class.getResource("/com/bitcamp/centro/estetico/resources/delete_rollOver.png")));
		btnDelete.addActionListener(e -> {
			returnData.clear();
			dispose();
		});

		btnInsert.addActionListener((l) -> {
			List<T> objects = getObjsAt(getSelectedRows());
			if(objects.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Nessuna opzione scelta");
				return;
			}
			returnData.clear();
			returnData.addAll(objects);

			dispose();
		});


		lbTitle.setText(title);
		table = new JTable();
		table.setSelectionMode(selectionMode);
		table.setFocusable(false);
		table.setFillsViewportHeight(true);
		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		table.setDefaultRenderer(JButton.class, new ButtonTableCellRenderer(table));
		table.setDefaultEditor(JButton.class, new ButtonTableCellRenderer(table));

		model = new NonEditableTableModel<>();
		table.setModel(model);

		scrollPane = new JScrollPane(table);

		panel_1 = new JPanel();
		panel_1.setBorder(DarkBorders.createLineBorder(5,5,5,5));
		gbc.weightx = 2.0;
		gbc.weighty = 2.0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(panel_1, gbc);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		panel_1.add(scrollPane);

	}

	public NonEditableTableModel<T> getModel() {
		return model;
	}

	public JTable getTable() {
		return table;
	}

	public T getObjAt(int row) {
		return model.getObjAt(row);
	}

	public List<T> getObjsAt(int[] row) {
		return model.getObjsAt(row);
	}

	public int getSelectedRow() {
		return table.getSelectedRow();
	}

	public int[] getSelectedRows() {
		return table.getSelectedRows();
	}

	public JLabel getLbOutput() {
		return lbOutput;
	}

	public void setSelectedRows(int[] rows) {
		for (int row : rows) {
			table.getSelectionModel().addSelectionInterval(row, row);
		}
	}
}

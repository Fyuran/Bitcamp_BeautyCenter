package com.bitcamp.centro.estetico.utils;

import java.awt.Dialog;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.bitcamp.centro.estetico.gui.render.CustomTableCellRenderer;
import com.bitcamp.centro.estetico.gui.render.NonEditableTableModel;
import com.bitcamp.centro.estetico.models.Model;

public class ModelQuickViewer<T extends Model> extends JDialog {

	private final JPanel panel;
	private final JLabel lbTitle;
	private final JTable table;
	private final NonEditableTableModel<T> model;
	private final JPanel panel_1;
	private final JScrollPane scrollPane;

	public ModelQuickViewer(JFrame owner, String title, T... objs) {
		this(owner, title, List.of(objs));
	}

	public ModelQuickViewer(JFrame owner, String title, Collection<T> objs) {
		this(owner, title, Dialog.ModalityType.DOCUMENT_MODAL, objs);
	}
	/**
	 * @wbp.parser.constructor
	 */
	public ModelQuickViewer(JFrame owner, String title, Dialog.ModalityType modalityType, Collection<T> objs) {
		super(owner, title, modalityType);
		setSize(400, 480);
		setMinimumSize(getSize());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[] { 1.0 };
		// gridBagLayout.rowHeights = new int[] {5, 33};
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0 };
		setLayout(gridBagLayout);

		panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.rowWeights = new double[] { 0.0, 0.0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0 };
		panel.setLayout(gbl_panel);

		lbTitle = new JLabel("PLACEHOLDER");
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 16));
		GridBagConstraints gbc_lbTitle = new GridBagConstraints();
		gbc_lbTitle.gridwidth = 4;
		gbc_lbTitle.fill = GridBagConstraints.BOTH;
		gbc_lbTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lbTitle.gridx = 0;
		gbc_lbTitle.gridy = 0;
		panel.add(lbTitle, gbc_lbTitle);

		panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(10, 10, 10, 10));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.weightx = 1.0;
		gbc_panel_1.weighty = 1.0;
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		add(panel_1, gbc_panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 4;
		gbc_list.insets = new Insets(0, 0, 0, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 2;

		lbTitle.setText(title);
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		
		model = new NonEditableTableModel<>(objs);
		table.setModel(model);
		
		scrollPane = new JScrollPane(table);
		panel_1.add(scrollPane);

		
	}

	public NonEditableTableModel<T> getModel() {
		return model;
	}


}

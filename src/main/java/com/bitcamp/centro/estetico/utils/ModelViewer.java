package com.bitcamp.centro.estetico.utils;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;

import com.bitcamp.centro.estetico.gui.render.ButtonTableCellRenderer;
import com.bitcamp.centro.estetico.gui.render.CustomTableCellRenderer;
import com.bitcamp.centro.estetico.gui.render.NonEditableTableModel;
import com.bitcamp.centro.estetico.models.Model;
import com.github.weisj.darklaf.components.border.DarkBorders;

public class ModelViewer<T extends Model> extends JDialog {

	private final JPanel panel;
	private final JLabel lbTitle;
	private final JTable table;
	private final NonEditableTableModel<T> model;
	private final JPanel panel_1;
	private final JScrollPane scrollPane;
	private final JLabel lbOutput;
	private final SearchBar searchFiltersBar;
	private final TableRowSorter<NonEditableTableModel<T>> tableRowSorter;

	public ModelViewer(JFrame owner, String title, int selectionMode, List<T> data) {
		this(owner, title, Dialog.ModalityType.DOCUMENT_MODAL, selectionMode, data);
	}

	public ModelViewer(JFrame owner, String title, int selectionMode, T... data) {
		this(owner, title, Dialog.ModalityType.DOCUMENT_MODAL, selectionMode, toNonNull(data));
	}

	public ModelViewer(String title, int selectionMode, List<T> data) {
		this(null, title, Dialog.ModalityType.DOCUMENT_MODAL, selectionMode, data);
	}

	public ModelViewer(String title, int selectionMode, T... data) {
		this(null, title, Dialog.ModalityType.DOCUMENT_MODAL, selectionMode, toNonNull(data));
	}

	/**
	 * @wbp.parser.constructor
	 */
	public ModelViewer(JFrame owner, String title, Dialog.ModalityType modalityType, int selectionMode,
			List<T> data) {
		super(owner, title, modalityType);
		setSize(1100, 480);
		setMinimumSize(getSize());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());

		panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 0.2;
		gbc.weighty = 0.2;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(panel, gbc);

		Font font = new Font("Microsoft Sans Serif", Font.BOLD, 16);
		lbTitle = new JLabel("PLACEHOLDER");
		lbTitle.setFont(font);
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(lbTitle, gbc);

		lbOutput = new JLabel();
		lbOutput.setForeground(new Color(0, 204, 51));
		lbOutput.setFont(font);
		lbOutput.setHorizontalAlignment(SwingConstants.CENTER);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(lbOutput, gbc);

		lbTitle.setText(title);
		table = new JTable();
		table.setSelectionMode(selectionMode);
		table.setFocusable(false);
		table.setFillsViewportHeight(true);
		table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		table.setDefaultRenderer(JButton.class, new ButtonTableCellRenderer(table));
		table.setDefaultEditor(JButton.class, new ButtonTableCellRenderer(table));

		model = new NonEditableTableModel<>(data);
		table.setModel(model);
		tableRowSorter = new TableRowSorter<>(model);
		table.setRowSorter(tableRowSorter);

		searchFiltersBar = new SearchBar(model.getColumnNames());
		searchFiltersBar.addSearchBtnActionListener(l -> {
			search();
			searchFiltersBar.clearSearchField();
		});
		searchFiltersBar.addSearchBarKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					search();
					searchFiltersBar.clearSearchField();
				}
			}	
		});
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(searchFiltersBar, gbc);

		scrollPane = new JScrollPane(table);
		panel_1 = new JPanel();
		panel_1.setBorder(DarkBorders.createLineBorder(5,5,5,5));
		gbc.weightx = 2.0;
		gbc.weighty = 2.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(panel_1, gbc);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		panel_1.add(scrollPane);
		
		if(data.isEmpty()) {
			lbOutput.setText("Lista vuota");
		}
	}

	private static <T extends Model> List<T> toNonNull(T... data) {
		List<T> nonNullObjs = new ArrayList<>();
		for(T t : data) {
			if(t != null) {
				nonNullObjs.add(t);
			}
		}
		return nonNullObjs;
	}

	private void search() {
		RowFilter<NonEditableTableModel<T>, Integer> filter = searchFiltersBar.getRowFilter();
		tableRowSorter.setRowFilter(filter);
	};
}

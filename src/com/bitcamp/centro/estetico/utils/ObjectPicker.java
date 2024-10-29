package com.bitcamp.centro.estetico.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.synth.SynthListUI;

import com.bitcamp.centro.estetico.gui.SetupWelcomeFrame;
import com.bitcamp.centro.estetico.models.Customer;
import com.bitcamp.centro.estetico.models.Gender;
import com.bitcamp.centro.estetico.models.Main;
import com.bitcamp.centro.estetico.models.Prize;
import com.bitcamp.centro.estetico.models.Subscription;
import com.bitcamp.centro.estetico.models.VAT;

import it.kamaladafrica.codicefiscale.CodiceFiscale;

public class ObjectPicker<T> extends JDialog {

	private final JPanel panel;
	private final JButton btnSearch;
	private final JButton btnFilter;
	private final JTextField textField;
	private final JLabel lbTitle;
	private final JButton btnInsert;
	private final JList<T> list = new JList<>();
	private final DefaultListModel<T> model = new DefaultListModel<>();
	private final JPanel panel_1;
	private final JScrollPane scrollPane;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Main("jdbc:mysql://204.216.214.56:1806", "bitcampUser", "newPassword");

				JFrame frame = new SetupWelcomeFrame();
				frame.setVisible(true);
				frame.setSize(400, 480);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				var picker = new ObjectPicker<VAT>(frame, "Test", Dialog.ModalityType.DOCUMENT_MODAL,
						List.of(new VAT(4), new VAT(10), new VAT(15), new VAT(22)));
				List<VAT> vats = new ArrayList<>();
				picker.addActionListener(l -> {
					System.out.println("debug");
					vats.addAll(picker.getSelectedValuesList());
				});
				picker.setVisible(true);
			}
		});

	}

	public ObjectPicker(JFrame owner, String title, Dialog.ModalityType modalityType, Collection<T> c,
			int selectionMode) {
		this(owner, title, modalityType);
		model.addAll(c);
		lbTitle.setText(title);
		list.setSelectionMode(selectionMode);
	}

	public ObjectPicker(JFrame owner, String title, Dialog.ModalityType modalityType, Collection<T> c) {
		this(owner, title, modalityType, c, ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	public ObjectPicker(JFrame owner, String title, Collection<T> c, int selectionMode) {
		this(owner, title, Dialog.ModalityType.DOCUMENT_MODAL, c, selectionMode);
	}

	public ObjectPicker(JFrame owner, String title, Collection<T> c) {
		this(owner, title, Dialog.ModalityType.DOCUMENT_MODAL, c, ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	/**
	 * @wbp.parser.constructor
	 */
	private ObjectPicker(JFrame owner, String title, Dialog.ModalityType modalityType) {
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

		btnSearch = new JButton("");
		btnSearch.setIcon(
				new ImageIcon(ObjectPicker.class.getResource("/com/bitcamp/centro/estetico/resources/searchIcon.png")));
		btnSearch.setRolloverEnabled(true);
		btnSearch.setRolloverIcon(new ImageIcon(
				ObjectPicker.class.getResource("/com/bitcamp/centro/estetico/resources/searchIcon_rollOver.png")));
		btnSearch.setOpaque(false);
		btnSearch.setContentAreaFilled(false);
		btnSearch.setBorderPainted(false);
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnSearch.gridx = 0;
		gbc_btnSearch.gridy = 1;
		panel.add(btnSearch, gbc_btnSearch);

		btnFilter = new JButton("");
		btnFilter.setIcon(
				new ImageIcon(ObjectPicker.class.getResource("/com/bitcamp/centro/estetico/resources/filterIcon.png")));
		btnFilter.setRolloverIcon(new ImageIcon(
				ObjectPicker.class.getResource("/com/bitcamp/centro/estetico/resources/filterIcon_rollOver.png")));
		btnFilter.setRolloverEnabled(true);
		btnFilter.setOpaque(false);
		btnFilter.setContentAreaFilled(false);
		btnFilter.setBorderPainted(false);
		GridBagConstraints gbc_btnFilter = new GridBagConstraints();
		gbc_btnFilter.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnFilter.insets = new Insets(0, 0, 5, 5);
		gbc_btnFilter.gridx = 1;
		gbc_btnFilter.gridy = 1;
		panel.add(btnFilter, gbc_btnFilter);

		textField = new JTextField();
		textField.setPreferredSize(new Dimension(100, 20));
		textField.setMinimumSize(new Dimension(20, 20));
		textField.setColumns(20);
		textField.setBackground(Color.WHITE);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.weightx = 1.0;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 1;
		panel.add(textField, gbc_textField);

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

		btnInsert = new JButton("");
		btnInsert.setIcon(
				new ImageIcon(ObjectPicker.class.getResource("/com/bitcamp/centro/estetico/resources/checkmark.png")));
		btnInsert.setRolloverEnabled(true);
		btnInsert.setRolloverIcon(new ImageIcon(
				ObjectPicker.class.getResource("/com/bitcamp/centro/estetico/resources/checkmark_rollOver.png")));
		btnInsert.setOpaque(false);
		btnInsert.setContentAreaFilled(false);
		btnInsert.setBorderPainted(false);
		GridBagConstraints gbc_btnInsert = new GridBagConstraints();
		gbc_btnInsert.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnInsert.insets = new Insets(0, 0, 5, 0);
		gbc_btnInsert.gridx = 3;
		gbc_btnInsert.gridy = 1;
		panel.add(btnInsert, gbc_btnInsert);

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

		list.setModel(model);
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 4;
		gbc_list.insets = new Insets(0, 0, 0, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 2;

		scrollPane = new JScrollPane(list);
		panel_1.add(scrollPane);

		list.setFixedCellHeight(50);
		// list.setFixedCellWidth(100);

		list.setCellRenderer(new DefaultListCellRenderer() {

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				setComponentOrientation(list.getComponentOrientation());

				Color bg = null;
				Color fg = null;

				JList.DropLocation dropLocation = list.getDropLocation();
				if (dropLocation != null
						&& !dropLocation.isInsert()
						&& dropLocation.getIndex() == index) {

					bg = UIManager.getColor("List.dropCellBackground");
					fg = UIManager.getColor("List.dropCellForeground");

					isSelected = true;
				}


				if (isSelected) {
					setBackground(bg == null ? list.getSelectionBackground() : bg);
					setForeground(fg == null ? list.getSelectionForeground() : fg);
				} else {
					setBackground(list.getBackground());
					setForeground(list.getForeground());
				}

				if (value instanceof Icon) {
					setIcon((Icon) value);
					setText("");
				} else {
					setIcon(null);
					if (value instanceof LocalDateTime dateTime) {
						DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
						setText(dateTime.format(dtf));
					} else if (value instanceof LocalDate date) {
						DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
						setText(date.format(dtf));
					} else if (value instanceof Optional opt) {
						setText(opt.orElse(null).toString());
					} else if (value instanceof Gender g) {
						setText(g.getGender());
					} else if (value instanceof Customer c) {
						setText(c.getFullName());
					} else if (value instanceof Subscription s) {
						setText("Periodo: " + s.getSubPeriod().toString() + " Prezzo: " + s.getPrice() + " IVA: "
								+ s.getVAT() + " Sconto: " + s.getDiscount());
					} else if (value instanceof Duration d) {
						setText(String.valueOf(d.toMinutes()));
					} else if (value instanceof CodiceFiscale cf) {
						setText(cf.getValue());
					} else if (value instanceof Prize p) {
						setText("Nome:" + p.getName() + " Punti necessari: " + p.getThreshold() + " Tipo: "
								+ p.getType() + " Quantità: " + p.getAmount());
					}
				}

				if (list.getName() == null || !list.getName().equals("ComboBox.list")
						|| !(list.getUI() instanceof SynthListUI)) {
					setEnabled(list.isEnabled());
				}

				setFont(list.getFont());

				Border border = null;
				if (cellHasFocus) {
					if (isSelected) {
						border = UIManager.getBorder("List.focusSelectedCellHighlightBorder");
					}
					if (border == null) {
						border = UIManager.getBorder("List.focusCellHighlightBorder");
					}
				} else {
					border = new LineBorder(Color.LIGHT_GRAY, 1);
				}

				setBorder(border);
				return this;

			}

		});

		list.setLocale(Locale.getDefault());
	}

	public void add(int index, T element) {
		model.add(index, element);
	}

	public void addElement(T element) {
		model.addElement(element);
	}

	public void addAll(Collection<T> c) {
		model.addAll(c);
	}

	public void addAll(int index, Collection<T> c) {
		model.addAll(index, c);
	}

	public List<T> getSelectedValuesList() {
		return list.getSelectedValuesList();
	}

	public T getSelectedValue() {
		return list.getSelectedValue();
	}

	public boolean isSelectionEmpty() {
		return list.isSelectionEmpty();
	}

	public ListModel<T> getListModel() {
		return model;
	}

	public void addActionListener(ActionListener l) {
		btnInsert.addActionListener(l);
	}

	public void setCellRenderer(ListCellRenderer<? super T> cellRenderer) {
		list.setCellRenderer(cellRenderer);
	}

	public void setSelectedIndices(int... indices) {
		list.setSelectedIndices(indices);
	}

	public void setSelectedItems(Collection<T> objs) {
		if(objs == null || objs.isEmpty()) return;

		ListModel<T> model = list.getModel();
		int size = model.getSize();
		List<Integer> indices = new ArrayList<>();
		for (T obj : objs) {
			if(obj == null) continue;
			for (int i = 0; i < size; i++) {
				T element = model.getElementAt(i);
				if (obj.equals(element)) {
					indices.add(i);
				}
			}
		}

		if(!indices.isEmpty()) {
			int[] ix = new int[indices.size()];
			for(int i = 0; i < indices.size(); i++) {
				ix[i] = indices.get(i);
			}

			setSelectedIndices(ix);
		}

		
	}
	public void setSelectedItems(T... objs) {
		setSelectedItems(Arrays.asList(objs));
	}
}

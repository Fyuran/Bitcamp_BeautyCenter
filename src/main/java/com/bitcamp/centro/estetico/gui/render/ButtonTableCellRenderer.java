package com.bitcamp.centro.estetico.gui.render;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class ButtonTableCellRenderer extends AbstractCellEditor
        implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener {
    private JTable table;
    private int mnemonic;
    private Border focusBorder;
    private Object editorValue;
    private static Color disabledBackground = new Color(255, 153, 0);
    private static Color disabledBackgroundSelect = new Color(35, 255, 101);
    private JButton button;
    private boolean isButtonTableCellRendererEditor;

    /**
     * Create the ButtonTableCellRenderer to be used as a renderer and editor. The
     * renderer and editor will automatically be installed on the TableColumn
     * of the specified column.
     *
     * @param table  the table containing the button renderer/editor
     * @param action the Action to be invoked when the button is invoked
     * @param column the column to which the button renderer/editor is added
     */
    public ButtonTableCellRenderer(JTable table) {
        super();
        this.table = table;
        // setFocusBorder(new LineBorder(Color.BLUE));
        table.addMouseListener(this);
    }

    /**
     * Get foreground color of the button when the cell has focus
     *
     * @return the foreground color
     */
    public Border getFocusBorder() {
        return focusBorder;
    }

    /**
     * The foreground color of the button when the cell has focus
     *
     * @param focusBorder the foreground color
     */
    public void setFocusBorder(Border focusBorder) {
        this.focusBorder = focusBorder;
    }

    public int getMnemonic() {
        return mnemonic;
    }

    /**
     * The mnemonic to activate the button when the cell has focus
     *
     * @param mnemonic the mnemonic
     */
    public void setMnemonic(int mnemonic) {
        this.mnemonic = mnemonic;
        /*
         * Action mnemonicAction = new AbstractAction()
         * {
         * public void actionPerformed(ActionEvent e)
         * {
         * ButtonTableCellRenderer.this.actionPerformed(e);
         * }
         * };
         * 
         * String key = "mnemonicAction";
         * KeyStroke keyStroke = KeyStroke.getKeyStroke(mnemonic, 0);
         * editButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke,
         * key);
         * editButton.getActionMap().put(key, mnemonicAction);
         */
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof JButton)
            button = (JButton) value;

        this.editorValue = value;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return editorValue;
    }

    //
    // Implement TableCellRenderer interface
    //
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof JButton)
            button = (JButton) value;

        TableModel model = table.getModel();
        TableColumn isEnabledCol = table.getColumn("Abilitato");
        boolean isEnabled = (boolean) model.getValueAt(row, isEnabledCol.getModelIndex());

        button.setEnabled(isEnabled);
        return button;
    }

    //
    // Implement ActionListener interface
    //
    /*
     * The button has been pressed. Stop editing and invoke the custom Action
     */
    public void actionPerformed(ActionEvent e) {
        int row = table.convertRowIndexToModel(table.getEditingRow());
        fireEditingStopped();

        // Invoke the Action

        ActionEvent event = new ActionEvent(
                table,
                ActionEvent.ACTION_PERFORMED,
                "" + row);
        button.getAction().actionPerformed(event);
    }

    //
    // Implement MouseListener interface
    //
    /*
     * When the mouse is pressed the editor is invoked. If you then then drag
     * the mouse to another cell before releasing it, the editor is still
     * active. Make sure editing is stopped when the mouse is released.
     */
    public void mousePressed(MouseEvent e) {
        if (table.isEditing()
                && table.getCellEditor() == this)
            isButtonTableCellRendererEditor = true;
    }

    public void mouseReleased(MouseEvent e) {
        if (isButtonTableCellRendererEditor
                && table.isEditing())
            table.getCellEditor().stopCellEditing();

        isButtonTableCellRendererEditor = false;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

}

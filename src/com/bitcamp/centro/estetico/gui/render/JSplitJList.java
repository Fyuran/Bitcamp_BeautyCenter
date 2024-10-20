package com.bitcamp.centro.estetico.gui.render;

import javax.swing.JList;
import javax.swing.JSplitPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

public class JSplitJList<T> extends JSplitLabel {
    private JList<T> list;

    public JSplitJList() {
		this("Label text");
	}

	public JSplitJList(String text) {
		this(text, new JList<T>());
	}

	public JSplitJList(String text, JList<T> list) {
		super(JSplitPane.VERTICAL_SPLIT, text, list);
        this.list = list;
	}

    public void setSelectionMode(int selectionMode) {
        list.setSelectionMode(selectionMode);
    }

    public void setModel(ListModel<T> model) {
        list.setModel(model);
    }

    public void setSelectedIndex(int index) {
        list.setSelectedIndex(index);
    }

    public void setCellRenderer(ListCellRenderer<? super T> cellRenderer) {
        list.setCellRenderer(cellRenderer);
    }

    public T getSelectedValue() {
        return list.getSelectedValue();
    }

    public void setSelectedValue(Object anObject, boolean shouldScroll) {
        list.setSelectedValue(anObject, shouldScroll);
    }
}

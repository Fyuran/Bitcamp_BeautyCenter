package com.bitcamp.centro.estetico.gui.render;

import javax.swing.JComboBox;

public class JSplitComboBox<T> extends JSplitLabel {

    public JSplitComboBox() {
		this("Label text");
	}
	public JSplitComboBox(String text) {
		this(text, new JComboBox<T>());
	}
	public JSplitComboBox(String text, JComboBox<T> datePicker) {
		super(text, datePicker);
	}

    public JComboBox<T> getComboBox() {
        return (JComboBox<T>) rightComponent;
    }

    public void addItem(T item) {
        getComboBox().addItem(item);
    }
    public void removeAllItems() {
        getComboBox().removeAllItems();
    }
    public T getSelectedItem() {
        return (T) getComboBox().getSelectedItem();
    }
    public void setSelectedItem(T anObject) {
        getComboBox().setSelectedItem(anObject);
    }


}

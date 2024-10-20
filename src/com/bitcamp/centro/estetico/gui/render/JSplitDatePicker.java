package com.bitcamp.centro.estetico.gui.render;

import java.awt.Font;
import java.time.LocalDate;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

public class JSplitDatePicker extends JSplitLabel {
    private DatePickerSettings settings;

    public JSplitDatePicker() {
		this("Label text");
	}
	public JSplitDatePicker(String text) {
		this(text, new DatePicker());
	}
	public JSplitDatePicker(String text, DatePicker datePicker) {
		super(text, datePicker);
        settings = new DatePickerSettings();
        settings.setFontValidDate(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
        datePicker.setSettings(settings);

        datePicker.addDateChangeListener(e -> {
            values.put(label.getText(), datePicker.getDate());
        });
	}

    public DatePicker getDatePicker() {
        return (DatePicker) rightComponent;
    }

    public LocalDate getDate() {
        return getDatePicker().getDate();
    }
    public void clear() {
        getDatePicker().clear();
    }
    public void setDate(LocalDate date) {
        getDatePicker().setDate(date);
    }
    public boolean isTextFieldValid() {
        return getDatePicker().isTextFieldValid();
    }
}

package com.bitcamp.centro.estetico.utils;

import java.awt.Font;
import java.time.LocalDate;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

public class JSplitDatePicker extends JSplitLabel {
    private DatePickerSettings settings;
    private DatePicker datePicker;

    public JSplitDatePicker() {
		this("Label text");
	}
	public JSplitDatePicker(String text) {
		this(text, new DatePicker());
	}
	public JSplitDatePicker(String text, DatePicker datePicker) {
		super(text, datePicker);
        this.datePicker = datePicker;
        settings = new DatePickerSettings();
        settings.setFontValidDate(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
        datePicker.setSettings(settings);
	}

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public LocalDate getDate() {
        return datePicker.getDate();
    }
    public void clear() {
        datePicker.clear();
    }
    public void setDate(LocalDate date) {
        datePicker.setDate(date);
    }
    public boolean isTextFieldValid() {
        return datePicker.isTextFieldValid();
    }
}

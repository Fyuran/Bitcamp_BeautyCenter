package com.bitcamp.centro.estetico.utils;

import java.awt.Font;
import java.time.LocalDateTime;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePicker;

public class JSplitDateTimePicker extends JSplitLabel {
    private DatePickerSettings settings;
    private DateTimePicker dateTimePicker;

    public JSplitDateTimePicker() {
		this("Label text");
	}
	public JSplitDateTimePicker(String text) {
		this(text, new DateTimePicker());
	}
	public JSplitDateTimePicker(String text, DateTimePicker dateTimePicker) {
		super(text, dateTimePicker);
        this.dateTimePicker = dateTimePicker;

        Font font = new Font("Microsoft Sans Serif", Font.PLAIN, 14);
        settings = new DatePickerSettings();
        settings.setFontValidDate(font);
        dateTimePicker.getDatePicker().setSettings(settings);
        dateTimePicker.getTimePicker().setFont(font);
	}

    public DateTimePicker getDateTimePicker() {
        return dateTimePicker;
    }

    public LocalDateTime getDateTimePermissive() {
        return dateTimePicker.getDateTimePermissive();
    }

    public void clear() {
        dateTimePicker.clear();
    }

    public void setDateTimePermissive(LocalDateTime optionalDateTime) {
        dateTimePicker.setDateTimePermissive(optionalDateTime);
    }

    public DatePicker getDatePicker() {
        return dateTimePicker.getDatePicker();
    }

    public TimePicker getTimePicker() {
        return dateTimePicker.getTimePicker();
    }
}

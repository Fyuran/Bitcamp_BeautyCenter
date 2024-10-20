package com.bitcamp.centro.estetico.gui.render;

import java.awt.Font;
import java.time.LocalDateTime;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePicker;

public class JSplitDateTimePicker extends JSplitLabel {
    private DatePickerSettings settings;

    public JSplitDateTimePicker() {
		this("Label text");
	}
	public JSplitDateTimePicker(String text) {
		this(text, new DateTimePicker());
	}
	public JSplitDateTimePicker(String text, DateTimePicker dateTimePicker) {
		super(text, dateTimePicker);

        Font font = new Font("Microsoft Sans Serif", Font.PLAIN, 14);
        settings = new DatePickerSettings();
        settings.setFontValidDate(font);
        dateTimePicker.getDatePicker().setSettings(settings);
        dateTimePicker.getTimePicker().setFont(font);

        dateTimePicker.addDateTimeChangeListener(e -> {
            values.put(label.getText(), dateTimePicker.getDateTimePermissive());
        });
	}

    public DateTimePicker getDateTimePicker() {
        return (DateTimePicker) rightComponent;
    }

    public LocalDateTime getDateTime() {
        return getDateTimePicker().getDateTimePermissive();
    }

    public void clear() {
        getDateTimePicker().clear();
    }

    public void setDateTime(LocalDateTime dateTime) {
        getDateTimePicker().setDateTimePermissive(dateTime);;
    }

    public DatePicker getDatePicker() {
        return getDateTimePicker().getDatePicker();
    }

    public TimePicker getTimePicker() {
        return getDateTimePicker().getTimePicker();
    }
}

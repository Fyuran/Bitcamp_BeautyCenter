package com.bitcamp.centro.estetico.utils;

import java.time.LocalTime;

import javax.swing.JLabel;
import javax.swing.UIManager;

import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeArea;

public class JSplitTimePicker extends JSplitLabel {
    private TimePicker timePicker;
    private TimePickerSettings settings;

    public JSplitTimePicker() {
        this("Label text");
    }

    public JSplitTimePicker(String text) {
        this(text, new TimePicker());
    }

    public JSplitTimePicker(String text, TimePicker timePicker) {
        super(new JLabel(text), timePicker);

        this.timePicker = timePicker;
        this.settings = timePicker.getSettings();

        settings.setAllowEmptyTimes(false);
        settings.fontValidTime = font;
    }

    public TimePicker getTimePicker() {
        return timePicker;
    }

    public LocalTime getTime() {
        return timePicker.getTime();
    }

    public void clear() {
        timePicker.clear();
    }

    public void setTime(LocalTime date) {
        timePicker.setTime(date);
    }

    public boolean isTextFieldValid() {
        return timePicker.isTextFieldValid();
    }

    @Override
    public void updateUI() {

        if(settings == null) return;

        settings.setColor(TimeArea.TextFieldBackgroundValidTime, UIManager.getColor("TextField.background"));
        settings.setColor(TimeArea.TimePickerTextValidTime, UIManager.getColor("TextField.foreground"));
    }
}

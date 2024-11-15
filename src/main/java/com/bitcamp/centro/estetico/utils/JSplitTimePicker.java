package com.bitcamp.centro.estetico.utils;

import java.awt.Font;
import java.time.LocalTime;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeArea;

public class JSplitTimePicker extends JPanel {
    private JLabel label;
    private TimePicker timePicker;
    private TimePickerSettings settings;
    private final static Font font;

    static {
        font = new Font("Microsoft Sans Serif", Font.PLAIN, 14);
    }

    public JSplitTimePicker(String text) {
        this(text, false);
    }

    public JSplitTimePicker(String text, boolean allowEmptyTimes) {
        super();

        label = new JLabel(text);
        if (settings != null) {
            this.timePicker = new TimePicker(settings);
        } else {
            this.timePicker = new TimePicker();
            this.settings = timePicker.getSettings();
        }

        settings.setAllowEmptyTimes(allowEmptyTimes);
        settings.fontValidTime = font;

        setLayout(new RelativeLayout());
        add(label, 0.4f);
        add(timePicker, 1f);
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

        if (settings == null)
            settings = new TimePickerSettings();

        settings.setColor(TimeArea.TextFieldBackgroundValidTime, UIManager.getColor("TextField.background"));
        settings.setColor(TimeArea.TimePickerTextValidTime, UIManager.getColor("TextField.foreground"));
    }
}

package com.bitcamp.centro.estetico.utils;

import java.awt.Font;
import java.time.LocalDate;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;

public class JSplitDatePicker extends JPanel {
    private DatePickerSettings settings;
    private DatePicker datePicker;
    private JLabel label;
    private final static Font font;

	static {
		font = new Font("Microsoft Sans Serif", Font.PLAIN, 14);
	}

    public JSplitDatePicker(String text) {
        this(text, false);
    }

    public JSplitDatePicker(String text, boolean allowEmptyDates) {
        super();

        label = new JLabel(text);
        if (settings != null) {
            this.datePicker = new DatePicker(settings);
        } else {
            this.datePicker = new DatePicker();
            this.settings = datePicker.getSettings();
        }

        settings.setFontValidDate(font);
        settings.setAllowEmptyDates(allowEmptyDates);

        setLayout(new RelativeLayout());
        add(label, 0.4f);
        add(datePicker, 1f);
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

    public DatePickerSettings getDatePickerSettings() {
        return settings;
    }

    @Override
    public void updateUI() {
        super.updateUI();

        if(settings == null)
            settings = new DatePickerSettings();
        
        settings.setColor(DateArea.CalendarBackgroundNormalDates, UIManager.getColor("Panel.background"));
        settings.setColor(DateArea.BackgroundOverallCalendarPanel, UIManager.getColor("Panel.background"));
        settings.setColor(DateArea.TextFieldBackgroundValidDate, UIManager.getColor("TextField.background"));
        settings.setColor(DateArea.CalendarBackgroundSelectedDate, UIManager.getColor("Button.background"));
        settings.setColor(DateArea.BackgroundTopLeftLabelAboveWeekNumbers, UIManager.getColor("Label.background"));
        settings.setColor(DateArea.BackgroundMonthAndYearMenuLabels, UIManager.getColor("Label.background"));
        settings.setColor(DateArea.BackgroundTodayLabel, UIManager.getColor("Label.background"));
        settings.setColor(DateArea.DatePickerTextValidDate, UIManager.getColor("TextField.foreground"));
        settings.setColor(DateArea.BackgroundClearLabel, UIManager.getColor("Button.background"));
        settings.setColor(DateArea.CalendarTextNormalDates, UIManager.getColor("Label.foreground"));
        settings.setColor(DateArea.CalendarBorderSelectedDate, UIManager.getColor("Table.selectionBackground").darker());
        settings.setColor(DateArea.BackgroundCalendarPanelLabelsOnHover, UIManager.getColor("ComboBox.buttonHighlight"));
        settings.setColor(DateArea.CalendarTextWeekdays, UIManager.getColor("Button.foreground"));
        settings.setColorBackgroundWeekdayLabels(UIManager.getColor("Button.background"), true);
    }

}

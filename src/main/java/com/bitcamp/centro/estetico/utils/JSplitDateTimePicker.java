package com.bitcamp.centro.estetico.utils;

import java.time.LocalDateTime;

import javax.swing.JLabel;
import javax.swing.UIManager;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeArea;

public class JSplitDateTimePicker extends JSplitLabel {
    private DatePickerSettings datePickerSettings;
    private TimePickerSettings timePickerSettings;
    private DateTimePicker dateTimePicker;
    private DatePicker datePicker;
    private TimePicker timePicker;

    public JSplitDateTimePicker() {
        this("Label text");
    }

    public JSplitDateTimePicker(String text) {
        this(text, new DateTimePicker());
    }

    public JSplitDateTimePicker(String text, DateTimePicker dateTimePicker) {
        super(new JLabel(text), dateTimePicker);

        if (datePickerSettings == null)
            datePickerSettings = new DatePickerSettings();

        this.dateTimePicker = dateTimePicker;
        this.datePicker = dateTimePicker.getDatePicker();
        this.timePicker = dateTimePicker.getTimePicker();
        timePickerSettings = timePicker.getSettings();

        datePickerSettings.setFontValidDate(font);
        datePickerSettings.setAllowEmptyDates(false);
        datePicker.setSettings(datePickerSettings);

        timePickerSettings.setAllowEmptyTimes(false);
        timePickerSettings.fontValidTime = font;


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

    public DatePickerSettings getDatePickerSettings() {
        return datePickerSettings;
    }

    @Override
    public void updateUI() {
        super.updateUI();

        if (datePickerSettings == null)
            datePickerSettings = new DatePickerSettings();

        datePickerSettings.setColor(DateArea.CalendarBackgroundNormalDates, UIManager.getColor("Panel.background"));
        datePickerSettings.setColor(DateArea.BackgroundOverallCalendarPanel, UIManager.getColor("Panel.background"));
        datePickerSettings.setColor(DateArea.TextFieldBackgroundValidDate, UIManager.getColor("TextField.background"));
        datePickerSettings.setColor(DateArea.CalendarBackgroundSelectedDate, UIManager.getColor("Button.background"));
        datePickerSettings.setColor(DateArea.BackgroundTopLeftLabelAboveWeekNumbers, UIManager.getColor("Label.background"));
        datePickerSettings.setColor(DateArea.BackgroundMonthAndYearMenuLabels, UIManager.getColor("Label.background"));
        datePickerSettings.setColor(DateArea.BackgroundTodayLabel, UIManager.getColor("Label.background"));
        datePickerSettings.setColor(DateArea.DatePickerTextValidDate, UIManager.getColor("TextField.foreground"));
        datePickerSettings.setColor(DateArea.BackgroundClearLabel, UIManager.getColor("Button.background"));
        datePickerSettings.setColor(DateArea.CalendarTextNormalDates, UIManager.getColor("Label.foreground"));
        datePickerSettings.setColor(DateArea.CalendarBorderSelectedDate, UIManager.getColor("Table.selectionBackground").darker());
        datePickerSettings.setColor(DateArea.BackgroundCalendarPanelLabelsOnHover, UIManager.getColor("ComboBox.buttonHighlight"));
        datePickerSettings.setColor(DateArea.CalendarTextWeekdays, UIManager.getColor("Button.foreground"));
        datePickerSettings.setColorBackgroundWeekdayLabels(UIManager.getColor("Button.background"), true);

        if(timePickerSettings == null) return;

        timePickerSettings.setColor(TimeArea.TextFieldBackgroundValidTime, UIManager.getColor("TextField.background"));
        timePickerSettings.setColor(TimeArea.TimePickerTextValidTime, UIManager.getColor("TextField.foreground"));
    }
}

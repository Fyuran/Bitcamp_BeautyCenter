package com.bitcamp.centro.estetico.utils;

import java.awt.Font;
import java.time.LocalDateTime;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeArea;

public class JSplitDateTimePicker extends JPanel {
    private DatePickerSettings datePickerSettings;
    private TimePickerSettings timePickerSettings;
    private DateTimePicker dateTimePicker;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private JLabel label;
    private final static Font font;

	static {
		font = new Font("Microsoft Sans Serif", Font.PLAIN, 14);
	}

    public JSplitDateTimePicker(String text) {
        this(text, false);
    }

    public JSplitDateTimePicker(String text, boolean allowEmptyDateTimes) {
        super();

        setLayout(new RelativeLayout());

        label = new JLabel(text);

        if(datePickerSettings != null && timePickerSettings != null) {
            this.dateTimePicker = new DateTimePicker(datePickerSettings, timePickerSettings);
        } else {
            this.dateTimePicker = new DateTimePicker();
            this.timePickerSettings = dateTimePicker.getTimePicker().getSettings();   
        }
        this.datePicker = dateTimePicker.getDatePicker();
        this.timePicker = dateTimePicker.getTimePicker();

        datePickerSettings.setFontValidDate(font);
        datePickerSettings.setAllowEmptyDates(allowEmptyDateTimes);

        timePickerSettings.setAllowEmptyTimes(allowEmptyDateTimes);
        timePickerSettings.fontValidTime = font;

        setLayout(new RelativeLayout());
        add(label, 0.4f);
        add(dateTimePicker, 1f);
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
        return datePicker;
    }

    public TimePicker getTimePicker() {
        return timePicker;
    }

    public DatePickerSettings getDatePickerSettings() {
        return datePickerSettings;
    }

    public TimePickerSettings getTimePickerSettings() {
        return timePickerSettings;
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

        if(timePickerSettings == null)
            timePickerSettings = new TimePickerSettings();

        timePickerSettings.setColor(TimeArea.TextFieldBackgroundValidTime, UIManager.getColor("TextField.background"));
        timePickerSettings.setColor(TimeArea.TimePickerTextValidTime, UIManager.getColor("TextField.foreground"));
    }
}

package com.example.taskmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import com.example.taskmanager.Database.RoomDB;
import com.example.taskmanager.Utility.TaskModel;
import com.example.taskmanager.R;
import java.time.LocalDate;
import java.util.Calendar;

public class ModifyTaskActivity extends AppCompatActivity {
    private static TextView titleLb;
    private static EditText nameView;
    private static EditText descriptionView;
    private static Spinner prioritySpinner;
    private static TextView dateView;
    private static TextView timeView;
    private static Button saveBtn;
    private static TextView priorityView;
    String currentDate = "NotSpecified";
    TaskModel task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_task);

        //Declaring Views
        titleLb = findViewById(R.id.titleLb);
        nameView = findViewById(R.id.nameView);
        descriptionView = findViewById(R.id.descriptionView);
        prioritySpinner = findViewById(R.id.prioritySpinner);
        dateView = findViewById(R.id.dateView);
        timeView = findViewById(R.id.timeView);
        saveBtn = findViewById(R.id.saveBtn);
        priorityView = findViewById(R.id.priorityView);

        // Adding items to priority Spinner
        Spinner prioritySpinner = findViewById(R.id.prioritySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_items_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(adapter);
        prioritySpinner.setSelection(0);

        //Receive info from mainActivity
        Intent i = getIntent();
        task = (TaskModel) i.getSerializableExtra("task");

        assert task != null;
        nameView.setText(task.getTitle());


        prioritySpinner.setSelection(adapter.getPosition(task.getCurrentPriority()));
        priorityView.setText(prioritySpinner.getSelectedItem().toString());
        dateView.setText(task.getCurrentDate());
        descriptionView.setText(task.getDescription());
        timeView.setText(task.getCurrentTime());
        String activityType = i.getStringExtra("activityType");
        titleLb.setText(activityType + " Task");

        assert activityType != null;
        if (activityType.equals("View")) {
            nameView.setEnabled(false);
            prioritySpinner.setEnabled(false);
            dateView.setEnabled(false);
            descriptionView.setEnabled(false);
            timeView.setEnabled(false);
            saveBtn.setVisibility(View.GONE);
            prioritySpinner.setVisibility(View.GONE);
            priorityView.setVisibility(View.VISIBLE);
        } else {//Modify
            nameView.setEnabled(true);
            prioritySpinner.setEnabled(true);
            dateView.setEnabled(true);
            descriptionView.setEnabled(true);
            timeView.setEnabled(true);
            saveBtn.setVisibility(View.VISIBLE);
            prioritySpinner.setVisibility(View.VISIBLE);
            priorityView.setVisibility(View.GONE);
        }
    }

    public void changeCalender(View v) {
        int currentMonth = LocalDate.now().getMonthValue() - 1;
        int currentDay = LocalDate.now().getDayOfMonth();
        int currentYear = LocalDate.now().getYear();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        // Construct selected date string
                        currentDate = String.valueOf(selectedDayOfMonth) + '-' + (selectedMonth + 1) + '-' + selectedYear;

                        // Compare selected date with current date
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(selectedYear, selectedMonth, selectedDayOfMonth);
                        Calendar currentCalendar = Calendar.getInstance();

                        // Check if the selected date is in the past
                        if (selectedCalendar.before(currentCalendar)) {
                            // Show message
                            AlertDialog.Builder builder = new AlertDialog.Builder(ModifyTaskActivity.this);
                            builder.setTitle("Incorrect Info").setMessage("Past dates? Future's calling! Let's go!");
                            builder.show();
                        } else {
                            // Update the TextView with the selected date
                            ((TextView) findViewById(R.id.dateView)).setText(currentDate);
                        }
                    }
                }, currentYear, currentMonth, currentDay);

// Show the DatePickerDialog
        datePickerDialog.show();
    }

    public void changeTime(View v) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar selectedTime = Calendar.getInstance();
                // TO DO:
                // USE DATE STORED HERE
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                selectedTime.set(Calendar.MINUTE, minute);

                String amPm;
                if (hourOfDay >= 12) {
                    amPm = "PM";
                } else {
                    amPm = "AM";
                }

                // Check if selected time is in the past
                if (selectedTime.before(Calendar.getInstance())) {
                    // Prompt user to select a future time
                    // Show message
                    AlertDialog.Builder builder = new AlertDialog.Builder(ModifyTaskActivity.this);
                    builder.setTitle("Incorrect Info").setMessage(hourOfDay + ":" + minute + amPm + " has passed buddy!");
                    builder.show();
                } else {
                    ((TextView) findViewById(R.id.timeView)).setText((hourOfDay % 12) + ":" + minute + " " + amPm);
                }
            }
        }, hour, minutes, false);
        timePickerDialog.show();
    }

    public void save(View v) {
        String name = nameView.getText().toString().trim();
        String description = descriptionView.getText().toString();

        if (name.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ModifyTaskActivity.this);
            builder.setTitle("Incorrect Info")
                    .setMessage("Please enter task name");
            builder.show();
        } else if (description.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ModifyTaskActivity.this);
            builder.setTitle("Incorrect Info")
                    .setMessage("Please enter task description");
            builder.show();
        } else {
            task.setTitle(name);
            task.setDescription(description);
            task.setCurrentDate(dateView.getText().toString());
            task.setCurrentTime(timeView.getText().toString());
            task.setCurrentPriority(prioritySpinner.getSelectedItem().toString());

            RoomDB instance = RoomDB.getInstance(this);

            new Thread(() -> instance.taskDAO().updateTask(task)).start();

            finish();
        }
    }
}

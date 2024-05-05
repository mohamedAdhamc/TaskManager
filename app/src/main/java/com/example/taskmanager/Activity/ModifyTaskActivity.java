package com.example.taskmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
    String currentPriority = "Low";
    String currentDate = "NotSpecified";
    String currentTime = "non";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_task);
        //Declaring Views
        titleLb = (TextView) findViewById(R.id.titleLb);
        nameView = (EditText) findViewById(R.id.nameView);
        descriptionView  = (EditText) findViewById(R.id.descriptionView);
        prioritySpinner = (Spinner) findViewById(R.id.prioritySpinner);
        dateView = (TextView) findViewById(R.id.dateView);
        timeView = (TextView) findViewById(R.id.timeView);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        priorityView = (TextView) findViewById(R.id.priorityView);

        // Adding items to priority Spinner
        Spinner prioritySpinner = findViewById(R.id.prioritySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_items_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(adapter);
        prioritySpinner.setSelection(0);

        //Receive info from mainActivity
        Intent i = getIntent();
        nameView.setText(i.getStringExtra("name"));

        prioritySpinner.setSelection(Integer.valueOf(i.getStringExtra("priority")));
        priorityView.setText(prioritySpinner.getSelectedItem().toString());
        dateView.setText(i.getStringExtra("date"));
        descriptionView.setText(i.getStringExtra("description"));
        timeView.setText(i.getStringExtra("time"));
        String activityType = i.getStringExtra("activityType");
        titleLb.setText(activityType + " Task");

        if(activityType.equals("View")){
            nameView.setEnabled(false);
            nameView.setTextColor(Color.WHITE);
            prioritySpinner.setEnabled(false);
            dateView.setEnabled(false);
            dateView.setTextColor(Color.WHITE);
            descriptionView.setEnabled(false);
            descriptionView.setTextColor(Color.WHITE);
            timeView.setEnabled(false);
            timeView.setTextColor(Color.WHITE);
            saveBtn.setVisibility(View.GONE);
            prioritySpinner.setVisibility(View.GONE);
            priorityView.setVisibility(View.VISIBLE);
        }
        else{//Modify
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
                        currentDate = String.valueOf(selectedDayOfMonth) + '-' + String.valueOf(selectedMonth + 1) + '-' + String.valueOf(selectedYear);

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
    public void changeTime(View v){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar selectedTime = Calendar.getInstance();
                // TO DO:
                // USE DATE STORED HERE
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                selectedTime.set(Calendar.MINUTE, minute);

                String amPm;
                if(hourOfDay>=12){
                    amPm = "PM";
                }
                else{
                    amPm = "AM";
                }

                // Check if selected time is in the past
                if (selectedTime.before(Calendar.getInstance())) {
                    // Prompt user to select a future time
                    // Show message
                    AlertDialog.Builder builder = new AlertDialog.Builder(ModifyTaskActivity.this);
                    builder.setTitle("Incorrect Info").setMessage(hourOfDay+":"+minute+amPm+" has passed buddy!");
                    builder.show();
                } else {
                    ((TextView)findViewById(R.id.timeView)).setText((hourOfDay%12)+":"+minute+" "+amPm);
                }
            }
        }, hour, minutes, false);
        timePickerDialog.show();
    }
    public void returnToMain(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void save(View v){
        String name = nameView.getText().toString().trim();
        String description = descriptionView.getText().toString();
        String priority = prioritySpinner.getSelectedItem().toString();
        Log.d("success",priority);
        String date = dateView.getText().toString();
        Log.d("success",date);
        String time = timeView.getText().toString();
        Log.d("success",time);
        if(name.equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(ModifyTaskActivity.this);
            builder.setTitle("Incorrect Info")
                    .setMessage("Please enter task name");
            builder.show();
        }
        else {
            String saved = "Your Task \"" + nameView.getText().toString().trim() + "\" has been saved Successfully!";
            Toast.makeText(this, saved, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }
}

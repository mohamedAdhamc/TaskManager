package com.example.taskmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.taskmanager.R;

import java.time.LocalDate;

public class CreateTaskActivity extends AppCompatActivity {
    String currentPriority = "Low";
    String currentDate = "non";
    String currentTime = "non";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        Spinner spinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_items_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Perform action on item selection
                String selectedItem = parentView.getItemAtPosition(position).toString();
                // Do something with the selected item
                currentPriority = selectedItem;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
    }

    public void selectDate(View v) {
        int currentMonth = LocalDate.now().getMonthValue()-1;
        int currentDay = LocalDate.now().getDayOfMonth();
        int currentYear = LocalDate.now().getYear();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        currentDate = String.valueOf(selectedDayOfMonth) +'-'+ String.valueOf(selectedMonth+1) + '-'+String.valueOf(selectedYear);
                        TextView currDateText = findViewById(R.id.dateText);
                        currDateText.setText(currentDate);
                        ((Button)findViewById(R.id.timeSelectButton)).setEnabled(true);
                    }

                }, currentYear, currentMonth, currentDay);

        // Show the DatePickerDialog

        datePickerDialog.show();
    }

    public void selectTime(View v){
        // Get current hour and minute

        // Create a TimePickerDialog and set its listener
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Update currentTime with the selected time
                        currentTime = String.valueOf(hourOfDay)+":"+String.valueOf(minute);
                        // Update TextView to display the selected time
                        TextView currTimeText = findViewById(R.id.timeText);
                        currTimeText.setText(currentTime);
                    }
                }, 6, 0, true);

        // Show the TimePickerDialog
        timePickerDialog.show();
    }

    public void saveToLocalDB(View v){
        String name = ((EditText)findViewById(R.id.editTextText)).getText().toString();
        String description = ((EditText)findViewById(R.id.editTextTextMultiLine)).getText().toString();
        String priority = currentPriority;
        String date = currentDate;

        if(name.isEmpty()){
            Toast.makeText(this,"Please fill the name field",Toast.LENGTH_LONG).show();
        }
        else if(description.isEmpty()){
            Toast.makeText(this,"Please fill the description field",Toast.LENGTH_LONG).show();
        }
        else{
//            Toast.makeText(this,currentDate,Toast.LENGTH_LONG).show();
//            Toast.makeText(this,currentTime,Toast.LENGTH_LONG).show();

            //Pass the items to local DB
            ////////////////
            ///////////////
            /////////////
            ///////////

        }
    }
}
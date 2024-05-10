package com.example.taskmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.taskmanager.Database.RoomDB;
import com.example.taskmanager.R;
import com.example.taskmanager.Utility.TaskModel;
import java.time.LocalDate;


public class CreateTaskActivity extends AppCompatActivity {
    String currentPriority = "Low";
    String currentDate = "Not defined";
    String currentTime = "Not defined";

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
                currentPriority = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
    }

    public void selectDate(View v) {
        int currentMonth = LocalDate.now().getMonthValue() - 1;
        int currentDay = LocalDate.now().getDayOfMonth();
        int currentYear = LocalDate.now().getYear();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                    currentDate = String.valueOf(selectedDayOfMonth) + '-' + (selectedMonth + 1) + '-' + selectedYear;
                    TextView currDateText = findViewById(R.id.dateText);
                    currDateText.setText(currentDate);
                    findViewById(R.id.timeSelectButton).setEnabled(true);
                }, currentYear, currentMonth, currentDay);

        // Show the DatePickerDialog

        datePickerDialog.show();
    }

    public void selectTime(View v) {
        // Get current hour and minute

        // Create a TimePickerDialog and set its listener
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    // Update currentTime with the selected time
                    currentTime = hourOfDay + ":" + minute;
                    // Update TextView to display the selected time
                    TextView currTimeText = findViewById(R.id.timeText);
                    currTimeText.setText(currentTime);
                }, 6, 0, true);

        // Show the TimePickerDialog
        timePickerDialog.show();
    }

    public void saveToLocalDB(View v) {
        String name = ((EditText) findViewById(R.id.editTextText)).getText().toString();
        String description = ((EditText) findViewById(R.id.editTextTextMultiLine)).getText().toString();
        String priority = currentPriority;
        String date = currentDate;
        Boolean editable = ((CheckBox)findViewById(R.id.checkBox2)).isChecked();

        if (name.isEmpty()) {
            Log.d("DB_Task_Insertion", "NAME ERROR");
            Toast.makeText(this, "Please fill the name field", Toast.LENGTH_LONG).show();
        } else if (description.isEmpty()) {
            Log.d("DB_Task_Insertion", "desc ERROR");
            Toast.makeText(this, "Please fill the description field", Toast.LENGTH_LONG).show();
        } else {
            TaskModel task = new TaskModel(0, name, description, priority, date, currentTime, editable);

            RoomDB instance = RoomDB.getInstance(this);
            new Thread(() -> instance.taskDAO().insertTask(task)).start();

            finish();
        }
    }
}
package com.example.chiragmahapatra.todo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddEditActivity extends AppCompatActivity {

    public final static String FROM = "from";
    public final static String EDITED_ITEM_VALUE = "edited_item_value";
    public String itemPosition;
    public String operation;

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        addPrioritySpinner();

        addStatusSpinner();

        addDateView();

        final Intent intent = getIntent();

        operation = intent.getStringExtra(MainActivity.OPERATION);

        if (operation.equals("edit")) {
            EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
            Spinner spinnerStatus = (Spinner) findViewById(R.id.spinner2);
            Spinner spinnerPriority = (Spinner) findViewById(R.id.spinner);
            dateView = (TextView) findViewById(R.id.textView5);

            Item itemTobeEdited = (Item) intent.getSerializableExtra(MainActivity.KEY);
            itemPosition = intent.getStringExtra(MainActivity.POSITION);

            System.out.println(getStatusPosition(itemTobeEdited.status));
            System.out.println(getPriorityPosition(itemTobeEdited.priority));

            etEditItem.setText(itemTobeEdited.itemText);
            spinnerStatus.setSelection(getStatusPosition(itemTobeEdited.status));
            spinnerPriority.setSelection(getPriorityPosition(itemTobeEdited.priority));
            dateView.setText(itemTobeEdited.dueDate);
            etEditItem.setSelection(itemTobeEdited.itemText.length());
        }
    }

    public int getStatusPosition(String status) {
        if (status.equals("DONE")) {
            return 1;
        }
        return 0;
    }

    public int getPriorityPosition(String priority) {
        if (priority.equals("LOW")) {
            return 2;
        } else if (priority.equals("MEDIUM")) {
            return 1;
        }
        return 0;
    }

    public void addDateView() {
        dateView = (TextView) findViewById(R.id.textView5);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT)
                .show();
    }

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
        }
    };

    public void addStatusSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner2);

        List<String> statusOptions = new ArrayList<String>();
        statusOptions.add("TO DO");
        statusOptions.add("DONE");

        ArrayAdapter<String> dataAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        statusOptions);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
    }

    public void addPrioritySpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        List<String> priorityCategories = new ArrayList<String>();
        priorityCategories.add("HIGH");
        priorityCategories.add("MEDIUM");
        priorityCategories.add("LOW");

        ArrayAdapter<String> dataAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        priorityCategories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
    }

    public void onSaveItem(View v){
        Intent intent = new Intent(AddEditActivity.this, MainActivity.class);
        EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
        Spinner spinnerStatus = (Spinner) findViewById(R.id.spinner2);
        Spinner spinnerPriority = (Spinner) findViewById(R.id.spinner);
        dateView = (TextView) findViewById(R.id.textView5);

        String status = spinnerStatus.getSelectedItem().toString();
        String priority = spinnerPriority.getSelectedItem().toString();
        String dueDate = dateView.getText().toString();
        String editItemText = etEditItem.getText().toString();

        Item item = new Item(editItemText, priority, status, dueDate);
        if (operation.equals("edit")) {
            intent.putExtra(FROM, "edit");
            intent.putExtra(MainActivity.POSITION, itemPosition);
            intent.putExtra(EDITED_ITEM_VALUE, item);
        } else if (operation.equals("add")){
            intent.putExtra(FROM, "add");
            intent.putExtra(EDITED_ITEM_VALUE, item);
        }

        startActivity(intent);
    }
}

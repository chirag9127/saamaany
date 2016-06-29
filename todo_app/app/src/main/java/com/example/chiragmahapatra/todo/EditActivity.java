package com.example.chiragmahapatra.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    public final static String FROM = "from";
    public final static String EDITED_ITEM_VALUE = "edited_item_value";
    public String itemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        EditText etEditItem = (EditText) findViewById(R.id.etEditItem);

        final Intent intent = getIntent();

        String itemTobeEdited = intent.getStringExtra(MainActivity.KEY);
        itemPosition = intent.getStringExtra(MainActivity.POSITION);

        etEditItem.setText(itemTobeEdited);
        etEditItem.setSelection(itemTobeEdited.length());
    }

    public void onEditItem(View v){
        Intent intent = new Intent(EditActivity.this, MainActivity.class);
        EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
        String editItemText = etEditItem.getText().toString();
        intent.putExtra(FROM, "edit_activity");
        intent.putExtra(MainActivity.POSITION, itemPosition);
        intent.putExtra(EDITED_ITEM_VALUE, editItemText);
        startActivity(intent);
    }
}

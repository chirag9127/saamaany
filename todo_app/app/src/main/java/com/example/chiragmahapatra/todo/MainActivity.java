package com.example.chiragmahapatra.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Item> items;
    private ItemAdapter itemsAdapter;
    private ListView lvItems;
    public final static String KEY = "itemName";
    public final static String POSITION = "position";
    public final static String OPERATION = "operation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        System.out.println(items);
        itemsAdapter = new ItemAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();

        final Intent intent = getIntent();
        String message = "" + intent.getStringExtra(AddEditActivity.FROM);
        if (message.equals("edit")) {
            String position = intent.getStringExtra(POSITION);
            int editPosition = Integer.parseInt(position);
            Item editedItemValue = (Item) intent.getSerializableExtra(AddEditActivity.EDITED_ITEM_VALUE);
            editItem(editPosition, editedItemValue);
        } else if (message.equals("add")) {
            Item addItemValue = (Item) intent.getSerializableExtra(AddEditActivity.EDITED_ITEM_VALUE);
            addItem(addItemValue);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void editItem(int position, Item editedItem) {
        items.remove(position);
        items.add(position, editedItem);
        writeItems();
    }

    public void addItem(Item addedItem) {
        items.add(addedItem);
        writeItems();
    }

    public void onAddItem(View v) {
        Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
        intent.putExtra(OPERATION, "add");
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        // Remove the item within array at position
                        items.remove(pos);
                        // Refresh the adapter
                        itemsAdapter.notifyDataSetChanged();
                        // Return true consumes the long click event (marks it handled)
                        writeItems();
                        return true;
                    }

                });
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {
                        Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                        Item itemTobeEdited = items.get(position);
                        intent.putExtra(OPERATION, "edit");
                        intent.putExtra(KEY, itemTobeEdited);
                        intent.putExtra(POSITION, Integer.toString(position));
                        startActivity(intent);
                    }
                }
        );
    }

    private void readItems() {
        try {
            FileInputStream fileIn = new FileInputStream(getFilesDir() + "itemDetails.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            items = (ArrayList<Item>) in.readObject();
            in.close();
            fileIn.close();

        } catch (IOException ioe) {
            items = new ArrayList<Item>();
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            items = new ArrayList<Item>();
            cnfe.printStackTrace();
        }
    }

    private void writeItems() {
        try {
                FileOutputStream fileOut = new FileOutputStream(getFilesDir() + "itemDetails.ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(items);
                /*
                for(Item e : items) {
                    out.writeObject(e);
                }*/
                out.close();
                fileOut.close();
        }catch (IOException i) {
                i.printStackTrace();
        }
    }
}

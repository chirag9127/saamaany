package com.example.chiragmahapatra.todo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by chiragmahapatra on 6/30/16.
 */
public class ItemAdapter extends ArrayAdapter<Item> {

    public ItemAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView itemText = (TextView) convertView.findViewById(R.id.itemText);
        TextView itemDueDate = (TextView) convertView.findViewById(R.id.itemDueDate);
        TextView itemPriority = (TextView) convertView.findViewById(R.id.itemPriority);

        itemText.setText(item.itemText);
        itemDueDate.setText(item.dueDate);
        if (item.priority.equals("HIGH")) {
            itemPriority.setText(item.priority);
            itemPriority.setTextColor(Color.RED);
        } else if (item.priority.equals("MEDIUM")) {
            itemPriority.setText(item.priority);
            itemPriority.setTextColor(Color.BLUE);
        } else if (item.priority.equals("LOW")) {
            itemPriority.setText(item.priority);
            itemPriority.setTextColor(Color.GREEN);
        }

        return convertView;
    }

}

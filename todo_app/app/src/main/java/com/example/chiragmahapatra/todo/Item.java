package com.example.chiragmahapatra.todo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by chiragmahapatra on 6/30/16.
 */
public class Item implements Serializable{

    String itemText;
    String priority;
    String status;
    String dueDate;

    Item(String text, String prior, String stat, String due) {
        itemText = text;
        priority = prior;
        status = stat;
        dueDate = due;
    }

    private void writeObject(ObjectOutputStream aOutputStream)
            throws IOException {
        aOutputStream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream aInputStream)
            throws ClassNotFoundException, IOException {
        aInputStream.defaultReadObject();
    }
}

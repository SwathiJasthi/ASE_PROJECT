package tutorial.cs5551.com.homeapp.data;

import android.database.Cursor;

import static tutorial.cs5551.com.homeapp.data.DatabaseContract.getColumnInt;
import static tutorial.cs5551.com.homeapp.data.DatabaseContract.getColumnLong;
import static tutorial.cs5551.com.homeapp.data.DatabaseContract.getColumnString;

/**
 * Helpful data model for holding attributes related to a task.
 */
public class Task {

    /* Constants representing missing data */
    public static final long NO_DATE = Long.MAX_VALUE;
    public static final long NO_ID = -1;

    //Unique identifier in database
    public long id;
    /*//Task description
    public final String description;
    //Marked if task is done
    public final boolean isComplete;
    //Marked if task is priority
    public final boolean isPriority;
    //Optional due date for the task
    public final long dueDateMillis;*/
    public final String itemname;

    public final String itembrand;
    public final String bname;
    public final String bemail;
    public final long duedate;

    public final boolean iscompleted;


    public Task(String name, String brand, String person , String bemail, long duedate, boolean iscompleted) {
        // TODO Auto-generated constructor stub
        this.id = NO_ID;
        this.itemname = name;
        this.itembrand = brand;
        this.bname = person;
        this.bemail = bemail;
        this.duedate = duedate;
        this.iscompleted=iscompleted;
    }
   /* *//**
     * Create a new Task with no due date
     *//*
    public Task(String description, boolean isComplete, boolean isPriority) {
        this(description, isComplete, isPriority, NO_DATE);
    }*/

    /**
     * Create a new task from a database Cursor
     */
    public Task(Cursor cursor) {;
        this.id = getColumnLong(cursor, DatabaseContract.TaskColumns._ID);
        this.duedate = getColumnLong(cursor, DatabaseContract.TaskColumns.DUE_DATE);
        this.itemname = getColumnString(cursor, DatabaseContract.TaskColumns.ITEMNAME);
        this.itembrand = getColumnString(cursor, DatabaseContract.TaskColumns.ITEMBRAND);
        this.bname = getColumnString(cursor, DatabaseContract.TaskColumns.BNAME);
        this.bemail = getColumnString(cursor, DatabaseContract.TaskColumns.BEMAIL);
        this.iscompleted = getColumnInt(cursor, DatabaseContract.TaskColumns.IS_COMPLETED) == 1;

    }

    /**
     * Return true if a due date has been set on this task.
     */
    public boolean hasDueDate() {
        return this.duedate != Long.MAX_VALUE;
    }

}

package com.example.listatodo.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.listatodo.taskDataModel.TaskCategory;
import com.example.listatodo.taskDataModel.TaskData;
import com.example.listatodo.taskDataModel.TaskStatus;

import java.util.ArrayList;
import java.util.List;

public class TaskDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tasks.db";
    private static final String TABLE_NAME = "task_table";
    private static final String ID = "ID";
    private static final String TASK_TITLE = "TASK_TITLE";
    private static final String TASK_DESCRIPTION = "TASK_DESCRIPTION";
    private static final String TASK_CREATION_TIME = "TASK_CREATION_TIME";
    private static final String TASK_END_TIME = "TASK_END_TIME";
    private static final String TASK_STATUS = "TASK_STATUS";
    private static final String TASK_NOTIFICATION = "TASK_NOTIFICATION";
    private static final String TASK_CATEGORY = "TASK_CATEGORY";
    private static final String TASK_ATTACHMENT = "TASK_ATTACHMENT";

    public TaskDatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY,"
                + TASK_TITLE + " TEXT,"
                + TASK_DESCRIPTION + " TEXT,"
                + TASK_CREATION_TIME + " TEXT,"
                + TASK_END_TIME + " TEXT,"
                + TASK_STATUS + " TEXT,"
                + TASK_NOTIFICATION + " BOOLEAN,"
                + TASK_CATEGORY + " TEXT,"
                + TASK_ATTACHMENT + " TEXT"
                +   ")";

        sqLiteDatabase.execSQL(CREATE_TASKS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

    public void addTask(TaskData task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TASK_TITLE, task.getTaskTitle());
        values.put(TASK_DESCRIPTION, task.getTaskDescription());
        values.put(TASK_CREATION_TIME, task.getTaskStart().toString());
        values.put(TASK_END_TIME, task.getTaskEnd().toString());
        values.put(TASK_STATUS, task.getTaskStatus().toString());
        values.put(TASK_NOTIFICATION, task.getHaveNotification());
        values.put(TASK_CATEGORY, task.getTaskCategory().toString());
        values.put(TASK_ATTACHMENT, "task.getAttachment().toString()");

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    TaskData getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE_NAME, new String[] {
                         ID
                        ,TASK_TITLE
                        ,TASK_CREATION_TIME
                        ,TASK_END_TIME
                        ,TASK_STATUS
                        ,TASK_NOTIFICATION
                        ,TASK_CATEGORY
                        ,TASK_ATTACHMENT}, ID + "=?"
                , new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return new TaskData(
                 Integer.valueOf(cursor.getString(0))
                ,cursor.getString(1)
                ,cursor.getString(2)
                ,Long.valueOf(cursor.getString(3))
                ,Long.valueOf(cursor.getString(4))
                , TaskStatus.valueOf(cursor.getString(5))
                ,Boolean.valueOf(cursor.getString(6))
                , TaskCategory.valueOf(cursor.getString(7))
                ,Boolean.valueOf(cursor.getString(8))
        );
    }

    public List<TaskData> getAllTasks() {
        List<TaskData> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TaskData contact = new TaskData(
                        Integer.valueOf(cursor.getString(0))
                        ,cursor.getString(1)
                        ,cursor.getString(2)
                        ,Long.valueOf(cursor.getString(3))
                        ,Long.valueOf(cursor.getString(4))
                        ,TaskStatus.valueOf(cursor.getString(5))
                        ,Boolean.valueOf(cursor.getString(6))
                        ,TaskCategory.valueOf(cursor.getString(7))
                        ,Boolean.valueOf(cursor.getString(8))
                );
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        return contactList;
    }

    public int updateTask(TaskData task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TASK_TITLE, task.getTaskTitle());
        values.put(TASK_DESCRIPTION, task.getTaskDescription());
        values.put(TASK_CREATION_TIME, task.getTaskStart().toString());
        values.put(TASK_END_TIME, task.getTaskEnd().toString());
        values.put(TASK_STATUS, task.getTaskStatus().toString());
        values.put(TASK_NOTIFICATION, task.getHaveNotification());
        values.put(TASK_CATEGORY, task.getTaskCategory().toString());
        values.put(TASK_ATTACHMENT, "task.getAttachment().toString()");

        System.out.println(task.getId());

        return db.update(TABLE_NAME, values, ID + " = ?",
                new String[] { String.valueOf(task.getId()) });
    }

    public void deleteTask(TaskData task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?",
                new String[] { String.valueOf(task.getId()) });
        db.close();
    }

    public int getTasksCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
}

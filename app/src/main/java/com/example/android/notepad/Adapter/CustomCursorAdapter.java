package com.example.android.notepad.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.notepad.NotePad;
import com.example.android.notepad.R;
import com.example.android.notepad.utills.DateDisplay;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomCursorAdapter extends CursorAdapter {

    public CustomCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.noteslist_item, parent, false);
    }
    //绑定视图
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView titleView = view.findViewById(android.R.id.text1);
        TextView dateView = view.findViewById(R.id.text_date);

        String title = cursor.getString(cursor.getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_TITLE));
        long timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE));

        // 输出日志，确认时间戳和格式化后的日期
        Log.d("CustomCursorAdapter", "Timestamp: " + timestamp);
        Log.d("CustomCursorAdapter", "Formatted Date: " + DateDisplay.showTime(timestamp));

        // 设置标题
        titleView.setText(title);

        // 设置格式化的日期
        dateView.setText(DateDisplay.showTime(timestamp));
    }

}

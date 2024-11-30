package com.example.android.notepad.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.Display;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.android.notepad.R;
import com.example.android.notepad.utills.DateDisplay;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesAdapter extends SimpleCursorAdapter {

    public NotesAdapter(Context context, int layout, Cursor cursor, String[] from, int[] to) {
        super(context, layout, cursor, from, to);

        // 设置自定义ViewBinder来格式化显示内容
        setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (view.getId() == R.id.text_date) {  // 检查是否是日期列
                    long timestamp = cursor.getLong(columnIndex); // 获取时间戳
                    String formattedDate = DateDisplay.showTime(timestamp); // 格式化日期
                    ((TextView) view).setText(formattedDate);
                    return true; // 表示已经处理了这个绑定
                }
                return false; // 使用默认绑定
            }
        });
    }

}

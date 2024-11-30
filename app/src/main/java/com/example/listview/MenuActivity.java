package com.example.listview;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view); // 假设你有一个TextView用于显示测试文本
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_font_size_small) {
            textView.setTextSize(10);
            return true;
        } else if (item.getItemId() == R.id.action_font_size_medium) {
            textView.setTextSize(16);
            return true;
        } else if (item.getItemId() == R.id.action_font_size_large) {
            textView.setTextSize(20);
            return true;
        } else if (item.getItemId() == R.id.action_show_toast) {
            Toast.makeText(this, "普通菜单项被点击了", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.action_font_color_red) {
            textView.setTextColor(getResources().getColor(R.color.red));
            return true;
        } else if (item.getItemId() == R.id.action_font_color_black) {
            textView.setTextColor(getResources().getColor(R.color.black));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
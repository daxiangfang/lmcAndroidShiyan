package com.example.listview;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        // 创建数据源
        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        data.add(new HashMap<String, Object>() {{
            put("image", R.drawable.lion);
            put("text", "Lion");
        }});
        data.add(new HashMap<String, Object>() {{
            put("image", R.drawable.tiger);
            put("text", "Tiger");
        }});
        data.add(new HashMap<String, Object>() {{
            put("image", R.drawable.monkey);
            put("text", "Monkey");
        }});
        data.add(new HashMap<String, Object>() {{
            put("image", R.drawable.dog);
            put("text", "Dog");
        }});
        data.add(new HashMap<String, Object>() {{
            put("image", R.drawable.cat);
            put("text", "Cat");
        }});
        data.add(new HashMap<String, Object>() {{
            put("image", R.drawable.elephant);
            put("text", "Elephant");
        }});


        // 设置SimpleAdapter
        SimpleAdapter adapter = new SimpleAdapter(this, data,
                R.layout.activity_list_view_simple_adapter, new String[]{"image", "text"},
                new int[]{R.id.imageView, R.id.textView});
        listView.setAdapter(adapter);

        // 设置列表项点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> selectedItem = (HashMap<String, Object>) parent.getItemAtPosition(position);
                String text = (String) selectedItem.get("text");
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
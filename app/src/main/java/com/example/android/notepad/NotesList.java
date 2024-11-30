package com.example.android.notepad;

import android.content.ClipboardManager;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import com.example.android.notepad.R;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AlertDialog;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.notepad.utills.DateDisplay;
import android.widget.EditText;

import java.util.Random;
import androidx.core.content.ContextCompat;
import android.widget.Button;
import android.graphics.Color;


public class NotesList extends AppCompatActivity {

    private static final String TAG = "NotesList";

    private static final String[] PROJECTION = new String[]{
            NotePad.Notes._ID, // 0
            NotePad.Notes.COLUMN_NAME_TITLE, // 1
            NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE // update_time
    };

    private static final int COLUMN_INDEX_TITLE = 1;

    private SimpleCursorAdapter adapter;
    private int currentSearchTextColor; // 当前搜索框文字颜色

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDarkTheme = preferences.getBoolean("dark_theme", false);
        if (isDarkTheme) {
            setTheme(R.style.AppTheme_Dark);
        } else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.notelist_main);

        currentSearchTextColor = ContextCompat.getColor(this, R.color.search_text_color); // 初始颜色

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listView = findViewById(android.R.id.list);
        listView.setOnCreateContextMenuListener(this);

        // 动态设置按钮监听，用于更改搜索框颜色
        Button changeColorButton = findViewById(R.id.change_color_button);
        changeColorButton.setOnClickListener(v -> {
            // 示例：更改为随机颜色
            currentSearchTextColor = getRandomColor();
            updateSearchViewColor();
        });

        loadNotesData();
    }

    private void updateSearchViewColor() {
        invalidateOptionsMenu(); // 重新绘制菜单以触发颜色更新
    }

    private void loadNotesData() {
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(NotePad.Notes.CONTENT_URI);
        }

        Cursor cursor = getContentResolver().query(
                intent.getData(),
                PROJECTION,
                null,
                null,
                NotePad.Notes.DEFAULT_SORT_ORDER
        );

        String[] dataColumns = {
                NotePad.Notes.COLUMN_NAME_TITLE,
                NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE
        };
        int[] viewIDs = {
                android.R.id.text1,
                R.id.text_date
        };

        adapter = new SimpleCursorAdapter(
                this,
                R.layout.noteslist_item,
                cursor,
                dataColumns,
                viewIDs,
                0
        );

        // 自定义 ViewBinder
        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (view.getId() == R.id.text_date) {
                    // 获取时间戳并格式化为日期字符串
                    long timestamp = cursor.getLong(columnIndex);
                    String formattedDate = DateDisplay.showTime(timestamp);
                    ((TextView) view).setText(formattedDate);
                    return true; // 表示自定义绑定已处理
                }
                return false; // 使用默认绑定
            }
        });

        ListView listView = findViewById(android.R.id.list);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_options_menu, menu);

        // 搜索功能
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search Notes");

        // 设置搜索框的字体和提示文字颜色
        updateSearchViewColor(searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                return true;
            }
        });

        // 动态主题切换功能
        MenuItem themeToggle = menu.findItem(R.id.menu_theme);
        themeToggle.setOnMenuItemClickListener(item -> {
            toggleTheme();
            return true;
        });

        // 背景更换功能
        MenuItem changeBackground = menu.findItem(R.id.menu_change_background);
        changeBackground.setOnMenuItemClickListener(item -> {
            showBackgroundOptions();
            return true;
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void performSearch(String query) {
        Cursor cursor = getContentResolver().query(
                NotePad.Notes.CONTENT_URI,
                PROJECTION,
                NotePad.Notes.COLUMN_NAME_TITLE + " LIKE ? OR " + NotePad.Notes.COLUMN_NAME_NOTE + " LIKE ?",
                new String[]{"%" + query + "%", "%" + query + "%"},
                NotePad.Notes.DEFAULT_SORT_ORDER
        );

        if (cursor != null) {
            adapter.changeCursor(cursor);
        }
    }

    private void toggleTheme() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDarkTheme = preferences.getBoolean("dark_theme", false);
        preferences.edit().putBoolean("dark_theme", !isDarkTheme).apply();
        recreate();
    }

    private void showBackgroundOptions() {
        String[] options = {"默认背景", "国旗背景"};
        int[] backgroundResources = {R.drawable.default_background, R.drawable.background_flag};

        new AlertDialog.Builder(this)
                .setTitle("选择背景")
                .setItems(options, (dialog, which) -> {
                    LinearLayout mainLayout = findViewById(R.id.main_layout);
                    mainLayout.setBackgroundResource(backgroundResources[which]);
                })
                .show();
    }

    private void updateSearchViewColor(SearchView searchView) {
        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        if (searchEditText != null) {
            searchEditText.setTextColor(currentSearchTextColor); // 设置动态颜色
            searchEditText.setHintTextColor(getResources().getColor(R.color.search_hint_color)); // 保持提示文字颜色
        }
    }

    private int getRandomColor() {
        Random random = new Random();
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        MenuItem mPasteItem = menu.findItem(R.id.menu_paste);

        if (clipboard != null && clipboard.hasPrimaryClip()) {
            mPasteItem.setEnabled(true);
        } else {
            mPasteItem.setEnabled(false);
        }

        final boolean haveItems = adapter != null && adapter.getCount() > 0;

        if (haveItems) {
            menu.removeGroup(Menu.CATEGORY_ALTERNATIVE);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        Intent intent = getIntent();

        if (itemId == R.id.menu_add) {
            startActivity(new Intent(Intent.ACTION_INSERT, intent.getData()));
            return true;
        } else if (itemId == R.id.menu_paste) {
            startActivity(new Intent(Intent.ACTION_PASTE, intent.getData()));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info;

        try {
            info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        } catch (ClassCastException e) {
            Log.e(TAG, "bad menuInfo", e);
            return;
        }

        Cursor cursor = (Cursor) adapter.getItem(info.position);

        if (cursor == null) {
            return;
        }

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_context_menu, menu);

        menu.setHeaderTitle(cursor.getString(COLUMN_INDEX_TITLE));

        Intent intent = new Intent(null, Uri.withAppendedPath(getIntent().getData(),
                Integer.toString((int) info.id)));
        intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        menu.addIntentOptions(Menu.CATEGORY_ALTERNATIVE, 0, 0,
                new ComponentName(this, NotesList.class), null, intent, 0, null);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;

        try {
            info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        } catch (ClassCastException e) {
            Log.e(TAG, "bad menuInfo", e);
            return false;
        }

        if (info == null) {
            return false;
        }

        Uri noteUri = ContentUris.withAppendedId(getIntent().getData(), info.id);

        int itemId = item.getItemId();

        if (itemId == R.id.context_open) {
            startActivity(new Intent(Intent.ACTION_EDIT, noteUri));
            return true;
        } else if (itemId == R.id.context_copy) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

            if (clipboard != null) {
                clipboard.setPrimaryClip(ClipData.newUri(
                        getContentResolver(),
                        "Note",
                        noteUri)
                );
            }

            return true;
        } else if (itemId == R.id.context_delete) {
            getContentResolver().delete(
                    noteUri,
                    null,
                    null
            );

            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    private void onListItemClick(View v, int position, long id) {
        Uri uri = ContentUris.withAppendedId(getIntent().getData(), id);

        String action = getIntent().getAction();

        if (Intent.ACTION_PICK.equals(action) || Intent.ACTION_GET_CONTENT.equals(action)) {
            setResult(RESULT_OK, new Intent().setData(uri));
        } else {
            startActivity(new Intent(Intent.ACTION_EDIT, uri));
        }
    }
}

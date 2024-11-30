package com.example.listview;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.MenuInflater;

public class MainActivity2 extends AppCompatActivity implements AbsListView.MultiChoiceModeListener {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[]{"One", "Two", "Three", "Four", "Five"});
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_delete) {
            // Handle item selection
            mode.finish();
            return true;
        } else {
            return false;
        }
    }
    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
        for (int i = 0; i < listView.getCount(); i++) {
            listView.setItemChecked(i, false);
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.setTitle("Select");
        return true;
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        int numChecked = listView.getCheckedItemCount();
        switch (numChecked) {
            case 1:
                mode.setTitle("1 selected");
                break;
            default:
                mode.setTitle(numChecked + " selected");
                break;
        }
    }
}
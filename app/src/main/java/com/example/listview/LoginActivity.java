package com.example.listview;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 你的主布局文件

        // 创建 AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View loginView = getLayoutInflater().inflate(R.layout.activity_login, null);
        builder.setView(loginView);

        // 获取布局中的组件
        EditText usernameEditText = loginView.findViewById(R.id.username);
        EditText passwordEditText = loginView.findViewById(R.id.password);
        Button cancelButton = loginView.findViewById(R.id.cancel);
        Button signinButton = loginView.findViewById(R.id.signin);

        // 设置按钮点击事件
        cancelButton.setOnClickListener(v -> builder.create().dismiss());
        signinButton.setOnClickListener(v -> {
            // 在这里处理登录逻辑
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            // 验证用户名和密码
            builder.create().dismiss();
        });

        // 创建并显示 AlertDialog
        builder.create().show();
    }
}
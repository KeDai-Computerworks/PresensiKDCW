package id.charles.presensikdcw.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import id.charles.presensikdcw.R;
import id.charles.presensikdcw.session.SessionManager;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SessionManager sessionManager = new SessionManager(getApplicationContext());

        TextView tvName = findViewById(R.id.tvName);
        tvName.setText(sessionManager.getDataCommit().get(SessionManager.NAME));

        findViewById(R.id.btn_logout).setOnClickListener(v -> {
            sessionManager.logOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            Toast.makeText(getApplicationContext(), "Berhasil Logout", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
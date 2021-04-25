package id.charles.presensikdcw.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import id.charles.presensikdcw.R;
import id.charles.presensikdcw.adapter.rvPresensiAdapter;
import id.charles.presensikdcw.api.ApiClient;
import id.charles.presensikdcw.api.ApiInterface;
import id.charles.presensikdcw.model.ModelPresensi;
import id.charles.presensikdcw.session.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private rvPresensiAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView rvAgenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(getApplicationContext());
        if (!sessionManager.isLogin()) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
        }

        rvAgenda = findViewById(R.id.rv_presensi);

        getPresensi();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, InputPresensiActivity.class)));
    }

    void getPresensi() {
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<ModelPresensi>> call = apiInterface.presensi();
        call.enqueue(new Callback<List<ModelPresensi>>() {
            @Override
            public void onResponse(Call<List<ModelPresensi>> call, Response<List<ModelPresensi>> response) {
                if (response.isSuccessful()) {
                    adapter = new rvPresensiAdapter(MainActivity.this, response.body());
                    layoutManager = new LinearLayoutManager(getApplicationContext());
                    rvAgenda.setLayoutManager(layoutManager);
                    rvAgenda.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<ModelPresensi>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        getPresensi();
    }
}
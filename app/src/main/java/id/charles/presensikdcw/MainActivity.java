package id.charles.presensikdcw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

import id.charles.presensikdcw.adapter.rvPresensiAdapter;
import id.charles.presensikdcw.database.AppDatabase;
import id.charles.presensikdcw.database.Kegiatan;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private ArrayList<Kegiatan> kegiatan = new ArrayList<>();
    private rvPresensiAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private AppDatabase database;
    private RecyclerView rvKegiatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
        }

        rvKegiatan = findViewById(R.id.rv_presensi);
        database = Room.databaseBuilder(this,
                AppDatabase.class, "dbKegiatan").allowMainThreadQueries().build();

        kegiatan.addAll(Arrays.asList(database.kegiatanDao().readDataKegiatan()));
        getData();

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InputPresensiActivity.class));
            }
        });
    }

    private void getData() {



        adapter = new rvPresensiAdapter(MainActivity.this, kegiatan);
        layoutManager = new LinearLayoutManager(this);

        rvKegiatan.setLayoutManager(layoutManager);
        rvKegiatan.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
}
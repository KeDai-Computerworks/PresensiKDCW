package id.charles.presensikdcw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import id.charles.presensikdcw.api.ApiClient;
import id.charles.presensikdcw.api.ApiInterface;
import id.charles.presensikdcw.database.AppDatabase;
import id.charles.presensikdcw.database.Kegiatan;
import id.charles.presensikdcw.model.ModelAbsensi;
import id.charles.presensikdcw.model.ModelKegiatan;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputPresensiActivity extends AppCompatActivity {

    private List<ModelKegiatan> kegiatan;
    private TextInputEditText inputTitle, inputCatatan;
    private TextView meetTitle;
    private Spinner list_item;
    private Button btn_save;
    int idAbsensi, sizeKegiatan;
    String namaKegiatan, hasilNamaKegiatan, hasilIdKegiatan;
    String idKegiatan, catatanKegiatan;
    private AppDatabase database;
    private ArrayAdapter<String> adapter;
    private String[] arrayKegiatan;
    private String[] arrayIdKegiatan;
    private ArrayList<String> listSpinner = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_presensi);
        //Inisialisasi dan memanggil Room Database
        database = Room.databaseBuilder(
                getApplicationContext().getApplicationContext(),
                AppDatabase.class,
                "dbKegiatan") //Nama File Database yang akan disimpan
                .build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inputCatatan = findViewById(R.id.input_etc);
        meetTitle = findViewById(R.id.tv_title_Presensi);
        list_item = findViewById(R.id.list_item);
        btn_save = findViewById(R.id.btn_save);
        getKegiatan();
        list_item.getSelectedItem();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                catatanKegiatan = inputCatatan.getText().toString();
                absensi(Integer.parseInt(arrayIdKegiatan[list_item.getSelectedItemPosition()]), catatanKegiatan, 3);
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getKegiatan() {
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<ModelKegiatan>> call = apiInterface.kegiatan();
        call.enqueue(new Callback<List<ModelKegiatan>>() {
            @Override
            public void onResponse(Call<List<ModelKegiatan>> call, Response<List<ModelKegiatan>> response) {

                if (response.isSuccessful()) {
                    Log.e("response ", "sukses");
                    kegiatan = response.body();
                    sizeKegiatan = kegiatan.size();

                    arrayKegiatan = new String[sizeKegiatan];
                    arrayIdKegiatan = new String[sizeKegiatan];

                    for (int i = 0; i < sizeKegiatan; i++) {
                        idKegiatan = String.valueOf(kegiatan.get(i).getId());
                        namaKegiatan = kegiatan.get(i).getNama();

                        arrayKegiatan[i] = namaKegiatan;
                        arrayIdKegiatan[i] = String.valueOf(response.body().get(i).getId());
                        listSpinner.add(namaKegiatan);

                    }

                    Log.e("index ", Arrays.toString(arrayIdKegiatan));
                    Log.e("index ", Arrays.toString(arrayKegiatan));

                    adapter = new ArrayAdapter<String>(InputPresensiActivity.this, R.layout.support_simple_spinner_dropdown_item, listSpinner);
                    list_item.setAdapter(adapter);
                } else Log.e("response ", response.message());
            }

            @Override
            public void onFailure(Call<List<ModelKegiatan>> call, Throwable t) {
                Log.e("response ", t.getMessage());
            }
        });
    }

    void absensi(int id, String catatan, int i) {
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ModelAbsensi> call = apiInterface.absensi(id, catatan, i);
        call.enqueue(new Callback<ModelAbsensi>() {
            @Override
            public void onResponse(Call<ModelAbsensi> call, Response<ModelAbsensi> response) {

                if (response.isSuccessful()) {

                    idAbsensi = response.body().getId();
                    Log.e("response ", "sukses = " + response.message());
                    Log.e("response ", "id kegiatan " + response.body().getKegiatan() +
                            "\nid absensi " + idAbsensi);

                    Kegiatan data = new Kegiatan();
                    data.setId(String.valueOf(id));
                    data.setNama(list_item.getSelectedItem().toString());
                    data.setCatatan(catatan);
                    data.setIdAbsensi(String.valueOf(idAbsensi));
                    insertData(data);

                } else Log.e("response ", "gagal = " + response.message());

            }

            @Override
            public void onFailure(Call<ModelAbsensi> call, Throwable t) {
                Log.e("response ", t.getMessage());
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void insertData(final Kegiatan kegiatan) {
        new AsyncTask<Void, Void, Long>() {

            @Override
            protected Long doInBackground(Void... voids) {
                return database.kegiatanDao().insertKegiatan(kegiatan);
            }

            @Override
            protected void onPostExecute(Long aLong) {

            }
        }.execute();
    }
}
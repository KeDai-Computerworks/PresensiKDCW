package id.charles.presensikdcw.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import id.charles.presensikdcw.R;
import id.charles.presensikdcw.api.ApiClient;
import id.charles.presensikdcw.api.ApiInterface;
import id.charles.presensikdcw.model.ModelAgenda;
import id.charles.presensikdcw.model.ModelPresensi;
import id.charles.presensikdcw.session.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputPresensiActivity extends AppCompatActivity {

    private List<ModelAgenda> kegiatan;
    private TextInputEditText inputTitle, inputCatatan;
    private Spinner list_item;
    private Button btn_save;
    int idAbsensi, sizeKegiatan;
    String namaKegiatan, hasilNamaKegiatan, hasilIdKegiatan;
    String idKegiatan, catatanKegiatan;
    private ArrayAdapter<String> adapter;
    private String[] arrayKegiatan;
    private String[] arrayIdKegiatan;
    private ArrayList<String> listSpinner = new ArrayList<String>();
    private ProgressDialog progressDialog;
    private Boolean server = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_presensi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(InputPresensiActivity.this);
        progressDialog.setTitle("Loading..."); // Setting Title
        progressDialog.show();
        progressDialog.setCancelable(false);

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        int createdBy = Integer.parseInt(Objects.requireNonNull(sessionManager.getDataCommit().get(SessionManager.ID)));

        inputCatatan = findViewById(R.id.input_etc);
        list_item = findViewById(R.id.list_item);
        btn_save = findViewById(R.id.btn_save);
        getKegiatan();
        list_item.getSelectedItem();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                catatanKegiatan = inputCatatan.getText().toString();
                if (inputCatatan.getText().toString().isEmpty()) {
                    inputCatatan.setError("Tidak boleh kosong");
                } else {
                    if (arrayIdKegiatan != null)
                        absensi(Integer.parseInt(arrayIdKegiatan[list_item.getSelectedItemPosition()]), catatanKegiatan, createdBy);
                }

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
        Call<List<ModelAgenda>> call = apiInterface.kegiatan();
        call.enqueue(new Callback<List<ModelAgenda>>() {
            @Override
            public void onResponse(Call<List<ModelAgenda>> call, Response<List<ModelAgenda>> response) {

                if (response.isSuccessful()) {
                    Log.e("response ", "sukses");
                    kegiatan = response.body();
                    sizeKegiatan = kegiatan.size();

                    arrayKegiatan = new String[sizeKegiatan];
                    arrayIdKegiatan = new String[sizeKegiatan];

                    for (int i = 0; i < sizeKegiatan; i++) {
                        idKegiatan = String.valueOf(kegiatan.get(i).getIdAgenda());
                        namaKegiatan = kegiatan.get(i).getNamaAgenda();

                        arrayKegiatan[i] = namaKegiatan;
                        arrayIdKegiatan[i] = String.valueOf(response.body().get(i).getIdAgenda());
                        listSpinner.add(namaKegiatan);
                    }

                    adapter = new ArrayAdapter<String>(InputPresensiActivity.this, R.layout.support_simple_spinner_dropdown_item, listSpinner);
                    list_item.setAdapter(adapter);
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(InputPresensiActivity.this, "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ModelAgenda>> call, Throwable t) {
                Toast.makeText(InputPresensiActivity.this, "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    progressDialog.dismiss();
                }, 1500);
            }
        });
    }

    void absensi(int id, String catatan, int i) {
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ModelPresensi> call = apiInterface.absensi(id, catatan, i);
        call.enqueue(new Callback<ModelPresensi>() {
            @Override
            public void onResponse(Call<ModelPresensi> call, Response<ModelPresensi> response) {

                if (response.isSuccessful()) {

                    idAbsensi = response.body().getIdPresensi();
                    Toast.makeText(InputPresensiActivity.this, "Sukses menambah presensi", Toast.LENGTH_SHORT).show();
                    finish();

                } else Log.e("response ", "gagal = " + response.message());
            }

            @Override
            public void onFailure(Call<ModelPresensi> call, Throwable t) {
                Toast.makeText(InputPresensiActivity.this, "Gagal menambah presensi", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
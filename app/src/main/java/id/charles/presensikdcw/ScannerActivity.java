package id.charles.presensikdcw;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.Result;

import id.charles.presensikdcw.api.ApiClient;
import id.charles.presensikdcw.api.ApiInterface;
import id.charles.presensikdcw.model.ModeLoglAbsensi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScannerActivity extends AppCompatActivity {

    private CodeScanner codeScanner;
    private ConstraintLayout layoutScanner;
    private static final int REQUEST_CODE_CAMERA = 1;
    private String id,idAbsensi;
    private int  idNra;
//    private static final int REQUEST_CODE_GALLERY = 2;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        getSupportActionBar().hide();
        layoutScanner = findViewById(R.id.layoutScanner);
        Intent intent = getIntent();
//        id = intent.getStringExtra("id");
        idAbsensi = intent.getStringExtra("idAbsensi");
//        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
//        }

        Toast.makeText(this, idAbsensi, Toast.LENGTH_SHORT).show();

        CodeScannerView scannerView = findViewById(R.id.scanner);
        codeScanner = new CodeScanner(this, scannerView);
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(ScannerActivity.this,
//                                id + "\n" + result.getText(),
//                                Toast.LENGTH_SHORT).show();
                        logAbsensi("884.KD.XIX.20");
//                        logAbsensi("salah");

                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeScanner.startPreview();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }

    private void logAbsensi(String result) {
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ModeLoglAbsensi> call = apiInterface.logAbsensi(result);
        call.enqueue(new Callback<ModeLoglAbsensi>() {
            @Override
            public void onResponse(Call<ModeLoglAbsensi> call, Response<ModeLoglAbsensi> response) {
                if (response.isSuccessful()) {
                    idNra =  response.body().getId();

                    postLogAbsensi(idNra,Integer.parseInt(idAbsensi));
//                    postLogAbsensi(response.body().getId(),);
                    Snackbar.make(layoutScanner, "id Kegiatan : " + id +
                                    ", id NRA : " + response.body().getId()
                            , Snackbar.LENGTH_LONG)
                            .show();
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        codeScanner.startPreview();
                    }, 1500);

                } else {
                    Snackbar.make(layoutScanner, response.message(), Snackbar.LENGTH_LONG)
                            .show();
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        codeScanner.startPreview();
                    }, 1000);
                }
            }

            @Override
            public void onFailure(Call<ModeLoglAbsensi> call, Throwable t) {
                Log.e("response ", t.getMessage());
            }
        });
    }

    private void postLogAbsensi(int idAnggota, int idAbsensi) {
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ModeLoglAbsensi> call = apiInterface.logAbsensi(idAnggota, idAbsensi);
        call.enqueue(new Callback<ModeLoglAbsensi>() {
            @Override
            public void onResponse(Call<ModeLoglAbsensi> call, Response<ModeLoglAbsensi> response) {
                if (response.isSuccessful()) {
                    Log.e("response ", "sukses");

                } else {
                    Log.e("response ", "Gagal");
                }
            }

            @Override
            public void onFailure(Call<ModeLoglAbsensi> call, Throwable t) {
                Log.e("response ", "no response");

            }
        });
    }


}
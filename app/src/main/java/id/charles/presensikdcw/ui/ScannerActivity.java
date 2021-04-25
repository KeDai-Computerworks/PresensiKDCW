package id.charles.presensikdcw.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.Result;

import id.charles.presensikdcw.R;
import id.charles.presensikdcw.api.ApiClient;
import id.charles.presensikdcw.api.ApiInterface;
import id.charles.presensikdcw.model.ModeLogPresensi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScannerActivity extends AppCompatActivity {

    private CodeScanner codeScanner;
    private ConstraintLayout layoutScanner;
    private static final int REQUEST_CODE_CAMERA = 1;
    private int idNra;
    private String idPresensi;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        getSupportActionBar().hide();
        layoutScanner = findViewById(R.id.layoutScanner);
        Intent intent = getIntent();
        idPresensi = intent.getStringExtra("idPresensi");

        CodeScannerView scannerView = findViewById(R.id.scanner);
        codeScanner = new CodeScanner(this, scannerView);
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logPresensi("891.KD-LB.XIX.20");
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

    private void logPresensi(String result) {
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ModeLogPresensi> call = apiInterface.logAbsensi(result);
        call.enqueue(new Callback<ModeLogPresensi>() {
            @Override
            public void onResponse(Call<ModeLogPresensi> call, Response<ModeLogPresensi> response) {
                if (response.isSuccessful()) {
                    idNra = response.body().getIdNra();
                    postLogPresensi(idNra, Integer.parseInt(idPresensi));

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
            public void onFailure(Call<ModeLogPresensi> call, Throwable t) {
                Snackbar.make(layoutScanner, t.getMessage(), Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void postLogPresensi(int idNra, int idPresensi) {
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ModeLogPresensi> call = apiInterface.logAbsensi(idNra, idPresensi);
        call.enqueue(new Callback<ModeLogPresensi>() {
            @Override
            public void onResponse(Call<ModeLogPresensi> call, Response<ModeLogPresensi> response) {
                if (response.isSuccessful()) {
                    Snackbar.make(layoutScanner, "id Presensi : " + idPresensi
                                    + ", id NRA : " + idNra + " Sukses"
                            , Snackbar.LENGTH_LONG)
                            .show();

                } else {
                    Snackbar.make(layoutScanner, response.body().getStatus()
                            , Snackbar.LENGTH_LONG)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ModeLogPresensi> call, Throwable t) {
                Snackbar.make(layoutScanner, t.getMessage()
                        , Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }
}
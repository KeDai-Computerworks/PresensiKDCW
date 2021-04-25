package id.charles.presensikdcw.model;

import com.google.gson.annotations.SerializedName;

public class ModeLogPresensi {

    @SerializedName("id") //get id nomor registrasi
    private int idNra;
    private String status;
    private String message;

    public int getIdNra() {
        return idNra;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

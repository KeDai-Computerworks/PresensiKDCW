package id.charles.presensikdcw.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelPresensi {
    private String status;
    private String message;

    @SerializedName("id")
    private int idPresensi;

    @SerializedName("catatan")
    private String catatanPresensi;

    @SerializedName("created_by")
    private int createdBy;

    @SerializedName("updated_at")
    private String tglPresensi;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public int getIdPresensi() {
        return idPresensi;
    }

    public String getCatatanPresensi() {
        return catatanPresensi;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public String getTglPresensi() {
        return tglPresensi;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    private Agenda agenda;

}

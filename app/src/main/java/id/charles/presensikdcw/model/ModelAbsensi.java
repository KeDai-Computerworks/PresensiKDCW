package id.charles.presensikdcw.model;

import com.google.gson.annotations.SerializedName;

public class ModelAbsensi {
    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("kegiatan")
    private String kegiatan;

    @SerializedName("waktu")
    private String waktu;

    @SerializedName("catatan")
    private String catatan;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("id")
    private int id;

    @SerializedName("created_by")
    private String createdBy;

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getKegiatan() {
        return kegiatan;
    }

    public String getWaktu() {
        return waktu;
    }

    public String getCatatan() {
        return catatan;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public String getCreatedBy() {
        return createdBy;
    }
}

package id.charles.presensikdcw.model;

import com.google.gson.annotations.SerializedName;

public class ModeLoglAbsensi {

    @SerializedName("noreg")
    private String noreg;

    @SerializedName("kontak")
    private String kontak;

    @SerializedName("nama")
    private String nama;

    @SerializedName("status_surat")
    private String statusSurat;

    @SerializedName("foto")
    private String foto;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("jabatan")
    private int jabatan;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("id")
    private int id;

    @SerializedName("email")
    private String email;

    @SerializedName("alamat")
    private String alamat;

    public String getNoreg() {
        return noreg;
    }

    public String getKontak() {
        return kontak;
    }

    public String getNama() {
        return nama;
    }

    public String getStatusSurat() {
        return statusSurat;
    }

    public String getFoto() {
        return foto;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int getJabatan() {
        return jabatan;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getAlamat() {
        return alamat;
    }
}

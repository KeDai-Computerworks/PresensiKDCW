package id.charles.presensikdcw.model;

import com.google.gson.annotations.SerializedName;

public class ModelAgenda {
    @SerializedName("id")
    private int idAgenda;

    @SerializedName("nama")
    private String namaAgenda;

    public int getIdAgenda() {
        return idAgenda;
    }

    public String getNamaAgenda() {
        return namaAgenda;
    }
}

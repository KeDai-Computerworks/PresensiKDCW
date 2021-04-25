package id.charles.presensikdcw.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tKegiatan")
public class Kegiatan implements Serializable {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id kegiatan")
    private
    String id;

    @ColumnInfo(name = "nama_kegiatan")
    private
    String nama;

    @ColumnInfo(name = "catatan_kegiatan")
    private
    String catatan;


    @ColumnInfo(name = "id_absensi")
    private
    String idAbsensi;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getIdAbsensi() {
        return idAbsensi;
    }

    public void setIdAbsensi(String idAbsensi) {
        this.idAbsensi = idAbsensi;
    }
}

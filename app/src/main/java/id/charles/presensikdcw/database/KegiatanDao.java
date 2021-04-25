package id.charles.presensikdcw.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface KegiatanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertKegiatan(Kegiatan kegiatan);

    @Query("SELECT * FROM tKegiatan")
    Kegiatan[] readDataKegiatan();

    @Delete
    void deleteKegiatan(Kegiatan kegiatan);


}

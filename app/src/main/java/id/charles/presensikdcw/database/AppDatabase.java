package id.charles.presensikdcw.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Kegiatan.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract KegiatanDao kegiatanDao();
}

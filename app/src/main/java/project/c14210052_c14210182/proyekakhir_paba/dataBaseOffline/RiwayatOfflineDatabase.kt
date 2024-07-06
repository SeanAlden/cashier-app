package project.c14210052_c14210182.proyekakhir_paba.dataBaseOffline

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromString(value: String?): ArrayList<String>? {
        val listType = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String>?): String? {
        return Gson().toJson(list)
    }
}

@Database(entities = [RiwayatOffline::class], version = 1)
@TypeConverters(Converters::class)
abstract class RiwayatOfflineDatabase :RoomDatabase(){
    abstract fun RiwayatOfflineDao(): RiwayatOfflineDAO
    companion object{
        @Volatile
        private var INSTANCE: RiwayatOfflineDatabase? = null

        @JvmStatic
        fun getDatabase(context:Context):RiwayatOfflineDatabase{
            if (INSTANCE == null){
                synchronized(RiwayatOfflineDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        RiwayatOfflineDatabase::class.java, "favorite_db")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as RiwayatOfflineDatabase
        }
    }
}
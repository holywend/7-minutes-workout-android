package wend.web.id.a7minutesworkout

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version = 1, exportSchema = false)
abstract class HistoryDatabase:RoomDatabase() {
    abstract fun historyDao():HistoryDao

    companion object{
        @Volatile
        private var INSTANCE: HistoryDatabase? = null

        fun getInstance(context: Context):HistoryDatabase{
            // use synchronized to make sure only one instance exist
            synchronized(this){
                var instance = INSTANCE
                // make sure there is only one database
                // create new database if it does not exist yet
                // or return the existing database instance
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryDatabase::class.java,
                        "db_history"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
package waslim.binar.andlima.challengech04.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteTaking::class], version = 1)
abstract class NoteTakingDatabase : RoomDatabase() {
    abstract fun noteTakingDao() : NoteTakingDao

    companion object{
        private var INSTANCE : NoteTakingDatabase? = null
        fun getInstance(context: Context) : NoteTakingDatabase? {
            if (INSTANCE == null){
                synchronized(NoteTakingDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        NoteTakingDatabase::class.java, "NoteTaking.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}
package ch.bfh.christianmueller.mymemory.data

import android.arch.persistence.room.*
import android.content.Context
import ch.bfh.christianmueller.mymemory.model.GameResult
import ch.bfh.christianmueller.mymemory.model.Player
import java.util.*

@Database(entities = arrayOf(Player::class, GameResult::class), version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object Builder {

        private var db: AppDatabase? = null

        fun build(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "myMemoryDb")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun gameResultDAO(): GameResultDao

    abstract fun playerDAO(): PlayerDao
}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
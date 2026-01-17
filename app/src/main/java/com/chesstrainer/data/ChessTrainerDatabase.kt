package com.chesstrainer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [GameEntity::class, GameResultEntity::class, PlayerRatingEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ChessTrainerDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao

    companion object {
        @Volatile
        private var instance: ChessTrainerDatabase? = null

        fun getInstance(context: Context): ChessTrainerDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: Room.databaseBuilder(
                        context.applicationContext,
                        ChessTrainerDatabase::class.java,
                        "chess_trainer.db"
                    ).build()
                        .also { instance = it }
            }
        }
    }
}

package com.chesstrainer.data

import android.content.Context
import androidx.room.Database
import androidx.room.migration.Migration
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [GameEntity::class, GameResultEntity::class, PlayerRatingEntity::class],
    version = 2,
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
                    ).addMigrations(MIGRATION_1_2)
                        .build()
                        .also { instance = it }
            }
        }
    }
}

private val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
            ALTER TABLE games
            ADD COLUMN opponentRating INTEGER NOT NULL DEFAULT 1200
            """.trimIndent()
        )
        database.execSQL(
            """
            ALTER TABLE games
            ADD COLUMN engineDepth INTEGER
            """.trimIndent()
        )
        database.execSQL(
            """
            ALTER TABLE games
            ADD COLUMN leelaNodes INTEGER
            """.trimIndent()
        )
    }
}

package com.chesstrainer.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ChessTrainerDatabase_Impl extends ChessTrainerDatabase {
  private volatile GameDao _gameDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `games` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `playedAt` INTEGER NOT NULL, `mode` TEXT NOT NULL, `engineType` TEXT NOT NULL, `result` TEXT NOT NULL, `moves` TEXT NOT NULL, `moveCount` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `game_results` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `gameId` INTEGER NOT NULL, `outcome` TEXT NOT NULL, `score` REAL NOT NULL, FOREIGN KEY(`gameId`) REFERENCES `games`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_game_results_gameId` ON `game_results` (`gameId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `player_ratings` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `gameId` INTEGER NOT NULL, `ratingBefore` INTEGER NOT NULL, `ratingAfter` INTEGER NOT NULL, `delta` INTEGER NOT NULL, `recordedAt` INTEGER NOT NULL, FOREIGN KEY(`gameId`) REFERENCES `games`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_player_ratings_gameId` ON `player_ratings` (`gameId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '73310ac6dc2b00b6d5cfec1688824feb')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `games`");
        db.execSQL("DROP TABLE IF EXISTS `game_results`");
        db.execSQL("DROP TABLE IF EXISTS `player_ratings`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsGames = new HashMap<String, TableInfo.Column>(7);
        _columnsGames.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGames.put("playedAt", new TableInfo.Column("playedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGames.put("mode", new TableInfo.Column("mode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGames.put("engineType", new TableInfo.Column("engineType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGames.put("result", new TableInfo.Column("result", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGames.put("moves", new TableInfo.Column("moves", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGames.put("moveCount", new TableInfo.Column("moveCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGames = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesGames = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGames = new TableInfo("games", _columnsGames, _foreignKeysGames, _indicesGames);
        final TableInfo _existingGames = TableInfo.read(db, "games");
        if (!_infoGames.equals(_existingGames)) {
          return new RoomOpenHelper.ValidationResult(false, "games(com.chesstrainer.data.GameEntity).\n"
                  + " Expected:\n" + _infoGames + "\n"
                  + " Found:\n" + _existingGames);
        }
        final HashMap<String, TableInfo.Column> _columnsGameResults = new HashMap<String, TableInfo.Column>(4);
        _columnsGameResults.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameResults.put("gameId", new TableInfo.Column("gameId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameResults.put("outcome", new TableInfo.Column("outcome", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameResults.put("score", new TableInfo.Column("score", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGameResults = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysGameResults.add(new TableInfo.ForeignKey("games", "CASCADE", "NO ACTION", Arrays.asList("gameId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesGameResults = new HashSet<TableInfo.Index>(1);
        _indicesGameResults.add(new TableInfo.Index("index_game_results_gameId", false, Arrays.asList("gameId"), Arrays.asList("ASC")));
        final TableInfo _infoGameResults = new TableInfo("game_results", _columnsGameResults, _foreignKeysGameResults, _indicesGameResults);
        final TableInfo _existingGameResults = TableInfo.read(db, "game_results");
        if (!_infoGameResults.equals(_existingGameResults)) {
          return new RoomOpenHelper.ValidationResult(false, "game_results(com.chesstrainer.data.GameResultEntity).\n"
                  + " Expected:\n" + _infoGameResults + "\n"
                  + " Found:\n" + _existingGameResults);
        }
        final HashMap<String, TableInfo.Column> _columnsPlayerRatings = new HashMap<String, TableInfo.Column>(6);
        _columnsPlayerRatings.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayerRatings.put("gameId", new TableInfo.Column("gameId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayerRatings.put("ratingBefore", new TableInfo.Column("ratingBefore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayerRatings.put("ratingAfter", new TableInfo.Column("ratingAfter", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayerRatings.put("delta", new TableInfo.Column("delta", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayerRatings.put("recordedAt", new TableInfo.Column("recordedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPlayerRatings = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysPlayerRatings.add(new TableInfo.ForeignKey("games", "CASCADE", "NO ACTION", Arrays.asList("gameId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesPlayerRatings = new HashSet<TableInfo.Index>(1);
        _indicesPlayerRatings.add(new TableInfo.Index("index_player_ratings_gameId", false, Arrays.asList("gameId"), Arrays.asList("ASC")));
        final TableInfo _infoPlayerRatings = new TableInfo("player_ratings", _columnsPlayerRatings, _foreignKeysPlayerRatings, _indicesPlayerRatings);
        final TableInfo _existingPlayerRatings = TableInfo.read(db, "player_ratings");
        if (!_infoPlayerRatings.equals(_existingPlayerRatings)) {
          return new RoomOpenHelper.ValidationResult(false, "player_ratings(com.chesstrainer.data.PlayerRatingEntity).\n"
                  + " Expected:\n" + _infoPlayerRatings + "\n"
                  + " Found:\n" + _existingPlayerRatings);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "73310ac6dc2b00b6d5cfec1688824feb", "38e1bde81e8ec5d816a6b39c21f0ecc8");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "games","game_results","player_ratings");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `games`");
      _db.execSQL("DELETE FROM `game_results`");
      _db.execSQL("DELETE FROM `player_ratings`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(GameDao.class, GameDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public GameDao gameDao() {
    if (_gameDao != null) {
      return _gameDao;
    } else {
      synchronized(this) {
        if(_gameDao == null) {
          _gameDao = new GameDao_Impl(this);
        }
        return _gameDao;
      }
    }
  }
}

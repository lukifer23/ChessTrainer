package com.chesstrainer.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class GameDao_Impl implements GameDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<GameEntity> __insertionAdapterOfGameEntity;

  private final EntityInsertionAdapter<GameResultEntity> __insertionAdapterOfGameResultEntity;

  private final EntityInsertionAdapter<PlayerRatingEntity> __insertionAdapterOfPlayerRatingEntity;

  public GameDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGameEntity = new EntityInsertionAdapter<GameEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `games` (`id`,`playedAt`,`mode`,`engineType`,`engineConfig`,`engineVersion`,`timeControl`,`analysisDepth`,`whiteElo`,`blackElo`,`result`,`moves`,`moveCount`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final GameEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getPlayedAt());
        if (entity.getMode() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getMode());
        }
        if (entity.getEngineType() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getEngineType());
        }
        if (entity.getEngineConfig() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getEngineConfig());
        }
        if (entity.getEngineVersion() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getEngineVersion());
        }
        if (entity.getTimeControl() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getTimeControl());
        }
        if (entity.getAnalysisDepth() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getAnalysisDepth());
        }
        if (entity.getWhiteElo() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getWhiteElo());
        }
        if (entity.getBlackElo() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getBlackElo());
        }
        if (entity.getResult() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getResult());
        }
        if (entity.getMoves() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getMoves());
        }
        statement.bindLong(13, entity.getMoveCount());
      }
    };
    this.__insertionAdapterOfGameResultEntity = new EntityInsertionAdapter<GameResultEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `game_results` (`id`,`gameId`,`outcome`,`score`,`analysisDepth`,`timeControl`,`whiteElo`,`blackElo`,`engineVersion`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final GameResultEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getGameId());
        if (entity.getOutcome() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getOutcome());
        }
        statement.bindDouble(4, entity.getScore());
        if (entity.getAnalysisDepth() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getAnalysisDepth());
        }
        if (entity.getTimeControl() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getTimeControl());
        }
        if (entity.getWhiteElo() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getWhiteElo());
        }
        if (entity.getBlackElo() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getBlackElo());
        }
        if (entity.getEngineVersion() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getEngineVersion());
        }
      }
    };
    this.__insertionAdapterOfPlayerRatingEntity = new EntityInsertionAdapter<PlayerRatingEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `player_ratings` (`id`,`gameId`,`ratingBefore`,`ratingAfter`,`delta`,`recordedAt`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PlayerRatingEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getGameId());
        statement.bindLong(3, entity.getRatingBefore());
        statement.bindLong(4, entity.getRatingAfter());
        statement.bindLong(5, entity.getDelta());
        statement.bindLong(6, entity.getRecordedAt());
      }
    };
  }

  @Override
  public Object insertGame(final GameEntity game, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfGameEntity.insertAndReturnId(game);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertGameResult(final GameResultEntity result,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfGameResultEntity.insert(result);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertPlayerRating(final PlayerRatingEntity rating,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPlayerRatingEntity.insert(rating);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<GameEntity>> getGames() {
    final String _sql = "SELECT * FROM games ORDER BY playedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"games"}, new Callable<List<GameEntity>>() {
      @Override
      @NonNull
      public List<GameEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlayedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "playedAt");
          final int _cursorIndexOfMode = CursorUtil.getColumnIndexOrThrow(_cursor, "mode");
          final int _cursorIndexOfEngineType = CursorUtil.getColumnIndexOrThrow(_cursor, "engineType");
          final int _cursorIndexOfEngineConfig = CursorUtil.getColumnIndexOrThrow(_cursor, "engineConfig");
          final int _cursorIndexOfEngineVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "engineVersion");
          final int _cursorIndexOfTimeControl = CursorUtil.getColumnIndexOrThrow(_cursor, "timeControl");
          final int _cursorIndexOfAnalysisDepth = CursorUtil.getColumnIndexOrThrow(_cursor, "analysisDepth");
          final int _cursorIndexOfWhiteElo = CursorUtil.getColumnIndexOrThrow(_cursor, "whiteElo");
          final int _cursorIndexOfBlackElo = CursorUtil.getColumnIndexOrThrow(_cursor, "blackElo");
          final int _cursorIndexOfResult = CursorUtil.getColumnIndexOrThrow(_cursor, "result");
          final int _cursorIndexOfMoves = CursorUtil.getColumnIndexOrThrow(_cursor, "moves");
          final int _cursorIndexOfMoveCount = CursorUtil.getColumnIndexOrThrow(_cursor, "moveCount");
          final List<GameEntity> _result = new ArrayList<GameEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final GameEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpPlayedAt;
            _tmpPlayedAt = _cursor.getLong(_cursorIndexOfPlayedAt);
            final String _tmpMode;
            if (_cursor.isNull(_cursorIndexOfMode)) {
              _tmpMode = null;
            } else {
              _tmpMode = _cursor.getString(_cursorIndexOfMode);
            }
            final String _tmpEngineType;
            if (_cursor.isNull(_cursorIndexOfEngineType)) {
              _tmpEngineType = null;
            } else {
              _tmpEngineType = _cursor.getString(_cursorIndexOfEngineType);
            }
            final String _tmpEngineConfig;
            if (_cursor.isNull(_cursorIndexOfEngineConfig)) {
              _tmpEngineConfig = null;
            } else {
              _tmpEngineConfig = _cursor.getString(_cursorIndexOfEngineConfig);
            }
            final String _tmpEngineVersion;
            if (_cursor.isNull(_cursorIndexOfEngineVersion)) {
              _tmpEngineVersion = null;
            } else {
              _tmpEngineVersion = _cursor.getString(_cursorIndexOfEngineVersion);
            }
            final String _tmpTimeControl;
            if (_cursor.isNull(_cursorIndexOfTimeControl)) {
              _tmpTimeControl = null;
            } else {
              _tmpTimeControl = _cursor.getString(_cursorIndexOfTimeControl);
            }
            final String _tmpAnalysisDepth;
            if (_cursor.isNull(_cursorIndexOfAnalysisDepth)) {
              _tmpAnalysisDepth = null;
            } else {
              _tmpAnalysisDepth = _cursor.getString(_cursorIndexOfAnalysisDepth);
            }
            final Integer _tmpWhiteElo;
            if (_cursor.isNull(_cursorIndexOfWhiteElo)) {
              _tmpWhiteElo = null;
            } else {
              _tmpWhiteElo = _cursor.getInt(_cursorIndexOfWhiteElo);
            }
            final Integer _tmpBlackElo;
            if (_cursor.isNull(_cursorIndexOfBlackElo)) {
              _tmpBlackElo = null;
            } else {
              _tmpBlackElo = _cursor.getInt(_cursorIndexOfBlackElo);
            }
            final String _tmpResult;
            if (_cursor.isNull(_cursorIndexOfResult)) {
              _tmpResult = null;
            } else {
              _tmpResult = _cursor.getString(_cursorIndexOfResult);
            }
            final String _tmpMoves;
            if (_cursor.isNull(_cursorIndexOfMoves)) {
              _tmpMoves = null;
            } else {
              _tmpMoves = _cursor.getString(_cursorIndexOfMoves);
            }
            final int _tmpMoveCount;
            _tmpMoveCount = _cursor.getInt(_cursorIndexOfMoveCount);
            _item = new GameEntity(_tmpId,_tmpPlayedAt,_tmpMode,_tmpEngineType,_tmpEngineConfig,_tmpEngineVersion,_tmpTimeControl,_tmpAnalysisDepth,_tmpWhiteElo,_tmpBlackElo,_tmpResult,_tmpMoves,_tmpMoveCount);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<GameResultEntity>> getResults() {
    final String _sql = "SELECT * FROM game_results ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"game_results"}, new Callable<List<GameResultEntity>>() {
      @Override
      @NonNull
      public List<GameResultEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfGameId = CursorUtil.getColumnIndexOrThrow(_cursor, "gameId");
          final int _cursorIndexOfOutcome = CursorUtil.getColumnIndexOrThrow(_cursor, "outcome");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfAnalysisDepth = CursorUtil.getColumnIndexOrThrow(_cursor, "analysisDepth");
          final int _cursorIndexOfTimeControl = CursorUtil.getColumnIndexOrThrow(_cursor, "timeControl");
          final int _cursorIndexOfWhiteElo = CursorUtil.getColumnIndexOrThrow(_cursor, "whiteElo");
          final int _cursorIndexOfBlackElo = CursorUtil.getColumnIndexOrThrow(_cursor, "blackElo");
          final int _cursorIndexOfEngineVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "engineVersion");
          final List<GameResultEntity> _result = new ArrayList<GameResultEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final GameResultEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpGameId;
            _tmpGameId = _cursor.getLong(_cursorIndexOfGameId);
            final String _tmpOutcome;
            if (_cursor.isNull(_cursorIndexOfOutcome)) {
              _tmpOutcome = null;
            } else {
              _tmpOutcome = _cursor.getString(_cursorIndexOfOutcome);
            }
            final double _tmpScore;
            _tmpScore = _cursor.getDouble(_cursorIndexOfScore);
            final String _tmpAnalysisDepth;
            if (_cursor.isNull(_cursorIndexOfAnalysisDepth)) {
              _tmpAnalysisDepth = null;
            } else {
              _tmpAnalysisDepth = _cursor.getString(_cursorIndexOfAnalysisDepth);
            }
            final String _tmpTimeControl;
            if (_cursor.isNull(_cursorIndexOfTimeControl)) {
              _tmpTimeControl = null;
            } else {
              _tmpTimeControl = _cursor.getString(_cursorIndexOfTimeControl);
            }
            final Integer _tmpWhiteElo;
            if (_cursor.isNull(_cursorIndexOfWhiteElo)) {
              _tmpWhiteElo = null;
            } else {
              _tmpWhiteElo = _cursor.getInt(_cursorIndexOfWhiteElo);
            }
            final Integer _tmpBlackElo;
            if (_cursor.isNull(_cursorIndexOfBlackElo)) {
              _tmpBlackElo = null;
            } else {
              _tmpBlackElo = _cursor.getInt(_cursorIndexOfBlackElo);
            }
            final String _tmpEngineVersion;
            if (_cursor.isNull(_cursorIndexOfEngineVersion)) {
              _tmpEngineVersion = null;
            } else {
              _tmpEngineVersion = _cursor.getString(_cursorIndexOfEngineVersion);
            }
            _item = new GameResultEntity(_tmpId,_tmpGameId,_tmpOutcome,_tmpScore,_tmpAnalysisDepth,_tmpTimeControl,_tmpWhiteElo,_tmpBlackElo,_tmpEngineVersion);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getGamesList(final Continuation<? super List<GameEntity>> $completion) {
    final String _sql = "SELECT * FROM games ORDER BY playedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<GameEntity>>() {
      @Override
      @NonNull
      public List<GameEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlayedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "playedAt");
          final int _cursorIndexOfMode = CursorUtil.getColumnIndexOrThrow(_cursor, "mode");
          final int _cursorIndexOfEngineType = CursorUtil.getColumnIndexOrThrow(_cursor, "engineType");
          final int _cursorIndexOfEngineConfig = CursorUtil.getColumnIndexOrThrow(_cursor, "engineConfig");
          final int _cursorIndexOfEngineVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "engineVersion");
          final int _cursorIndexOfTimeControl = CursorUtil.getColumnIndexOrThrow(_cursor, "timeControl");
          final int _cursorIndexOfAnalysisDepth = CursorUtil.getColumnIndexOrThrow(_cursor, "analysisDepth");
          final int _cursorIndexOfWhiteElo = CursorUtil.getColumnIndexOrThrow(_cursor, "whiteElo");
          final int _cursorIndexOfBlackElo = CursorUtil.getColumnIndexOrThrow(_cursor, "blackElo");
          final int _cursorIndexOfResult = CursorUtil.getColumnIndexOrThrow(_cursor, "result");
          final int _cursorIndexOfMoves = CursorUtil.getColumnIndexOrThrow(_cursor, "moves");
          final int _cursorIndexOfMoveCount = CursorUtil.getColumnIndexOrThrow(_cursor, "moveCount");
          final List<GameEntity> _result = new ArrayList<GameEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final GameEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpPlayedAt;
            _tmpPlayedAt = _cursor.getLong(_cursorIndexOfPlayedAt);
            final String _tmpMode;
            if (_cursor.isNull(_cursorIndexOfMode)) {
              _tmpMode = null;
            } else {
              _tmpMode = _cursor.getString(_cursorIndexOfMode);
            }
            final String _tmpEngineType;
            if (_cursor.isNull(_cursorIndexOfEngineType)) {
              _tmpEngineType = null;
            } else {
              _tmpEngineType = _cursor.getString(_cursorIndexOfEngineType);
            }
            final String _tmpEngineConfig;
            if (_cursor.isNull(_cursorIndexOfEngineConfig)) {
              _tmpEngineConfig = null;
            } else {
              _tmpEngineConfig = _cursor.getString(_cursorIndexOfEngineConfig);
            }
            final String _tmpEngineVersion;
            if (_cursor.isNull(_cursorIndexOfEngineVersion)) {
              _tmpEngineVersion = null;
            } else {
              _tmpEngineVersion = _cursor.getString(_cursorIndexOfEngineVersion);
            }
            final String _tmpTimeControl;
            if (_cursor.isNull(_cursorIndexOfTimeControl)) {
              _tmpTimeControl = null;
            } else {
              _tmpTimeControl = _cursor.getString(_cursorIndexOfTimeControl);
            }
            final String _tmpAnalysisDepth;
            if (_cursor.isNull(_cursorIndexOfAnalysisDepth)) {
              _tmpAnalysisDepth = null;
            } else {
              _tmpAnalysisDepth = _cursor.getString(_cursorIndexOfAnalysisDepth);
            }
            final Integer _tmpWhiteElo;
            if (_cursor.isNull(_cursorIndexOfWhiteElo)) {
              _tmpWhiteElo = null;
            } else {
              _tmpWhiteElo = _cursor.getInt(_cursorIndexOfWhiteElo);
            }
            final Integer _tmpBlackElo;
            if (_cursor.isNull(_cursorIndexOfBlackElo)) {
              _tmpBlackElo = null;
            } else {
              _tmpBlackElo = _cursor.getInt(_cursorIndexOfBlackElo);
            }
            final String _tmpResult;
            if (_cursor.isNull(_cursorIndexOfResult)) {
              _tmpResult = null;
            } else {
              _tmpResult = _cursor.getString(_cursorIndexOfResult);
            }
            final String _tmpMoves;
            if (_cursor.isNull(_cursorIndexOfMoves)) {
              _tmpMoves = null;
            } else {
              _tmpMoves = _cursor.getString(_cursorIndexOfMoves);
            }
            final int _tmpMoveCount;
            _tmpMoveCount = _cursor.getInt(_cursorIndexOfMoveCount);
            _item = new GameEntity(_tmpId,_tmpPlayedAt,_tmpMode,_tmpEngineType,_tmpEngineConfig,_tmpEngineVersion,_tmpTimeControl,_tmpAnalysisDepth,_tmpWhiteElo,_tmpBlackElo,_tmpResult,_tmpMoves,_tmpMoveCount);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getResultsList(final Continuation<? super List<GameResultEntity>> $completion) {
    final String _sql = "SELECT * FROM game_results ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<GameResultEntity>>() {
      @Override
      @NonNull
      public List<GameResultEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfGameId = CursorUtil.getColumnIndexOrThrow(_cursor, "gameId");
          final int _cursorIndexOfOutcome = CursorUtil.getColumnIndexOrThrow(_cursor, "outcome");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfAnalysisDepth = CursorUtil.getColumnIndexOrThrow(_cursor, "analysisDepth");
          final int _cursorIndexOfTimeControl = CursorUtil.getColumnIndexOrThrow(_cursor, "timeControl");
          final int _cursorIndexOfWhiteElo = CursorUtil.getColumnIndexOrThrow(_cursor, "whiteElo");
          final int _cursorIndexOfBlackElo = CursorUtil.getColumnIndexOrThrow(_cursor, "blackElo");
          final int _cursorIndexOfEngineVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "engineVersion");
          final List<GameResultEntity> _result = new ArrayList<GameResultEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final GameResultEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpGameId;
            _tmpGameId = _cursor.getLong(_cursorIndexOfGameId);
            final String _tmpOutcome;
            if (_cursor.isNull(_cursorIndexOfOutcome)) {
              _tmpOutcome = null;
            } else {
              _tmpOutcome = _cursor.getString(_cursorIndexOfOutcome);
            }
            final double _tmpScore;
            _tmpScore = _cursor.getDouble(_cursorIndexOfScore);
            final String _tmpAnalysisDepth;
            if (_cursor.isNull(_cursorIndexOfAnalysisDepth)) {
              _tmpAnalysisDepth = null;
            } else {
              _tmpAnalysisDepth = _cursor.getString(_cursorIndexOfAnalysisDepth);
            }
            final String _tmpTimeControl;
            if (_cursor.isNull(_cursorIndexOfTimeControl)) {
              _tmpTimeControl = null;
            } else {
              _tmpTimeControl = _cursor.getString(_cursorIndexOfTimeControl);
            }
            final Integer _tmpWhiteElo;
            if (_cursor.isNull(_cursorIndexOfWhiteElo)) {
              _tmpWhiteElo = null;
            } else {
              _tmpWhiteElo = _cursor.getInt(_cursorIndexOfWhiteElo);
            }
            final Integer _tmpBlackElo;
            if (_cursor.isNull(_cursorIndexOfBlackElo)) {
              _tmpBlackElo = null;
            } else {
              _tmpBlackElo = _cursor.getInt(_cursorIndexOfBlackElo);
            }
            final String _tmpEngineVersion;
            if (_cursor.isNull(_cursorIndexOfEngineVersion)) {
              _tmpEngineVersion = null;
            } else {
              _tmpEngineVersion = _cursor.getString(_cursorIndexOfEngineVersion);
            }
            _item = new GameResultEntity(_tmpId,_tmpGameId,_tmpOutcome,_tmpScore,_tmpAnalysisDepth,_tmpTimeControl,_tmpWhiteElo,_tmpBlackElo,_tmpEngineVersion);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PlayerRatingEntity>> getRatings() {
    final String _sql = "SELECT * FROM player_ratings ORDER BY recordedAt ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"player_ratings"}, new Callable<List<PlayerRatingEntity>>() {
      @Override
      @NonNull
      public List<PlayerRatingEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfGameId = CursorUtil.getColumnIndexOrThrow(_cursor, "gameId");
          final int _cursorIndexOfRatingBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingBefore");
          final int _cursorIndexOfRatingAfter = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingAfter");
          final int _cursorIndexOfDelta = CursorUtil.getColumnIndexOrThrow(_cursor, "delta");
          final int _cursorIndexOfRecordedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "recordedAt");
          final List<PlayerRatingEntity> _result = new ArrayList<PlayerRatingEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlayerRatingEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpGameId;
            _tmpGameId = _cursor.getLong(_cursorIndexOfGameId);
            final int _tmpRatingBefore;
            _tmpRatingBefore = _cursor.getInt(_cursorIndexOfRatingBefore);
            final int _tmpRatingAfter;
            _tmpRatingAfter = _cursor.getInt(_cursorIndexOfRatingAfter);
            final int _tmpDelta;
            _tmpDelta = _cursor.getInt(_cursorIndexOfDelta);
            final long _tmpRecordedAt;
            _tmpRecordedAt = _cursor.getLong(_cursorIndexOfRecordedAt);
            _item = new PlayerRatingEntity(_tmpId,_tmpGameId,_tmpRatingBefore,_tmpRatingAfter,_tmpDelta,_tmpRecordedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getLatestRating(final Continuation<? super PlayerRatingEntity> $completion) {
    final String _sql = "SELECT * FROM player_ratings ORDER BY recordedAt DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PlayerRatingEntity>() {
      @Override
      @Nullable
      public PlayerRatingEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfGameId = CursorUtil.getColumnIndexOrThrow(_cursor, "gameId");
          final int _cursorIndexOfRatingBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingBefore");
          final int _cursorIndexOfRatingAfter = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingAfter");
          final int _cursorIndexOfDelta = CursorUtil.getColumnIndexOrThrow(_cursor, "delta");
          final int _cursorIndexOfRecordedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "recordedAt");
          final PlayerRatingEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpGameId;
            _tmpGameId = _cursor.getLong(_cursorIndexOfGameId);
            final int _tmpRatingBefore;
            _tmpRatingBefore = _cursor.getInt(_cursorIndexOfRatingBefore);
            final int _tmpRatingAfter;
            _tmpRatingAfter = _cursor.getInt(_cursorIndexOfRatingAfter);
            final int _tmpDelta;
            _tmpDelta = _cursor.getInt(_cursorIndexOfDelta);
            final long _tmpRecordedAt;
            _tmpRecordedAt = _cursor.getLong(_cursorIndexOfRecordedAt);
            _result = new PlayerRatingEntity(_tmpId,_tmpGameId,_tmpRatingBefore,_tmpRatingAfter,_tmpDelta,_tmpRecordedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

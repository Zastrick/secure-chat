package com.tabasco.securechat.data.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
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

@SuppressWarnings({"unchecked", "deprecation"})
public final class MessageDao_Impl implements MessageDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MessageEntity> __insertionAdapterOfMessageEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateReactions;

  public MessageDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMessageEntity = new EntityInsertionAdapter<MessageEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `messages` (`id`,`contactPublicId`,`fromMe`,`kind`,`text`,`fileName`,`mimeType`,`fileBase64`,`durationMs`,`timestamp`,`reactionsRaw`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MessageEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getContactPublicId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getContactPublicId());
        }
        final int _tmp = entity.getFromMe() ? 1 : 0;
        statement.bindLong(3, _tmp);
        if (entity.getKind() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getKind());
        }
        if (entity.getText() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getText());
        }
        if (entity.getFileName() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getFileName());
        }
        if (entity.getMimeType() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getMimeType());
        }
        if (entity.getFileBase64() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getFileBase64());
        }
        if (entity.getDurationMs() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getDurationMs());
        }
        statement.bindLong(10, entity.getTimestamp());
        if (entity.getReactionsRaw() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getReactionsRaw());
        }
      }
    };
    this.__preparedStmtOfUpdateReactions = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE messages SET reactionsRaw = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final MessageEntity message, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMessageEntity.insert(message);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateReactions(final String messageId, final String reactions,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateReactions.acquire();
        int _argIndex = 1;
        if (reactions == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, reactions);
        }
        _argIndex = 2;
        if (messageId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, messageId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateReactions.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getByContact(final String contactId,
      final Continuation<? super List<MessageEntity>> $completion) {
    final String _sql = "SELECT * FROM messages WHERE contactPublicId = ? ORDER BY timestamp ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (contactId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, contactId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<MessageEntity>>() {
      @Override
      @NonNull
      public List<MessageEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfContactPublicId = CursorUtil.getColumnIndexOrThrow(_cursor, "contactPublicId");
          final int _cursorIndexOfFromMe = CursorUtil.getColumnIndexOrThrow(_cursor, "fromMe");
          final int _cursorIndexOfKind = CursorUtil.getColumnIndexOrThrow(_cursor, "kind");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "fileName");
          final int _cursorIndexOfMimeType = CursorUtil.getColumnIndexOrThrow(_cursor, "mimeType");
          final int _cursorIndexOfFileBase64 = CursorUtil.getColumnIndexOrThrow(_cursor, "fileBase64");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfReactionsRaw = CursorUtil.getColumnIndexOrThrow(_cursor, "reactionsRaw");
          final List<MessageEntity> _result = new ArrayList<MessageEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MessageEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpContactPublicId;
            if (_cursor.isNull(_cursorIndexOfContactPublicId)) {
              _tmpContactPublicId = null;
            } else {
              _tmpContactPublicId = _cursor.getString(_cursorIndexOfContactPublicId);
            }
            final boolean _tmpFromMe;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfFromMe);
            _tmpFromMe = _tmp != 0;
            final String _tmpKind;
            if (_cursor.isNull(_cursorIndexOfKind)) {
              _tmpKind = null;
            } else {
              _tmpKind = _cursor.getString(_cursorIndexOfKind);
            }
            final String _tmpText;
            if (_cursor.isNull(_cursorIndexOfText)) {
              _tmpText = null;
            } else {
              _tmpText = _cursor.getString(_cursorIndexOfText);
            }
            final String _tmpFileName;
            if (_cursor.isNull(_cursorIndexOfFileName)) {
              _tmpFileName = null;
            } else {
              _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
            }
            final String _tmpMimeType;
            if (_cursor.isNull(_cursorIndexOfMimeType)) {
              _tmpMimeType = null;
            } else {
              _tmpMimeType = _cursor.getString(_cursorIndexOfMimeType);
            }
            final String _tmpFileBase64;
            if (_cursor.isNull(_cursorIndexOfFileBase64)) {
              _tmpFileBase64 = null;
            } else {
              _tmpFileBase64 = _cursor.getString(_cursorIndexOfFileBase64);
            }
            final Integer _tmpDurationMs;
            if (_cursor.isNull(_cursorIndexOfDurationMs)) {
              _tmpDurationMs = null;
            } else {
              _tmpDurationMs = _cursor.getInt(_cursorIndexOfDurationMs);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpReactionsRaw;
            if (_cursor.isNull(_cursorIndexOfReactionsRaw)) {
              _tmpReactionsRaw = null;
            } else {
              _tmpReactionsRaw = _cursor.getString(_cursorIndexOfReactionsRaw);
            }
            _item = new MessageEntity(_tmpId,_tmpContactPublicId,_tmpFromMe,_tmpKind,_tmpText,_tmpFileName,_tmpMimeType,_tmpFileBase64,_tmpDurationMs,_tmpTimestamp,_tmpReactionsRaw);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

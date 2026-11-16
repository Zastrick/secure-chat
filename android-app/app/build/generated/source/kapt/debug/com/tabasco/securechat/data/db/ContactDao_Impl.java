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
public final class ContactDao_Impl implements ContactDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ContactEntity> __insertionAdapterOfContactEntity;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  public ContactDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfContactEntity = new EntityInsertionAdapter<ContactEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `contacts` (`publicId`,`alias`,`photoBase64`) VALUES (?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ContactEntity entity) {
        if (entity.getPublicId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getPublicId());
        }
        if (entity.getAlias() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getAlias());
        }
        if (entity.getPhotoBase64() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getPhotoBase64());
        }
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM contacts WHERE publicId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object upsert(final ContactEntity contact, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfContactEntity.insert(contact);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final String publicId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
        int _argIndex = 1;
        if (publicId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, publicId);
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
          __preparedStmtOfDelete.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getAll(final Continuation<? super List<ContactEntity>> $completion) {
    final String _sql = "SELECT * FROM contacts ORDER BY alias ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ContactEntity>>() {
      @Override
      @NonNull
      public List<ContactEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPublicId = CursorUtil.getColumnIndexOrThrow(_cursor, "publicId");
          final int _cursorIndexOfAlias = CursorUtil.getColumnIndexOrThrow(_cursor, "alias");
          final int _cursorIndexOfPhotoBase64 = CursorUtil.getColumnIndexOrThrow(_cursor, "photoBase64");
          final List<ContactEntity> _result = new ArrayList<ContactEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ContactEntity _item;
            final String _tmpPublicId;
            if (_cursor.isNull(_cursorIndexOfPublicId)) {
              _tmpPublicId = null;
            } else {
              _tmpPublicId = _cursor.getString(_cursorIndexOfPublicId);
            }
            final String _tmpAlias;
            if (_cursor.isNull(_cursorIndexOfAlias)) {
              _tmpAlias = null;
            } else {
              _tmpAlias = _cursor.getString(_cursorIndexOfAlias);
            }
            final String _tmpPhotoBase64;
            if (_cursor.isNull(_cursorIndexOfPhotoBase64)) {
              _tmpPhotoBase64 = null;
            } else {
              _tmpPhotoBase64 = _cursor.getString(_cursorIndexOfPhotoBase64);
            }
            _item = new ContactEntity(_tmpPublicId,_tmpAlias,_tmpPhotoBase64);
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

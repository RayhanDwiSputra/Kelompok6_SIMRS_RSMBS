package com.rsmbs.simrs.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.rsmbs.simrs.data.entity.Obat;
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
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ObatDao_Impl implements ObatDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Obat> __insertionAdapterOfObat;

  private final SharedSQLiteStatement __preparedStmtOfKurangiStok;

  public ObatDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfObat = new EntityInsertionAdapter<Obat>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `obat` (`idObat`,`namaObat`,`satuan`,`stok`,`hargaSatuan`,`stokMinimum`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Obat entity) {
        statement.bindLong(1, entity.getIdObat());
        statement.bindString(2, entity.getNamaObat());
        statement.bindString(3, entity.getSatuan());
        statement.bindLong(4, entity.getStok());
        statement.bindLong(5, entity.getHargaSatuan());
        statement.bindLong(6, entity.getStokMinimum());
      }
    };
    this.__preparedStmtOfKurangiStok = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE obat SET stok = stok - ? WHERE idObat = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertAll(final List<Obat> obatList, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfObat.insert(obatList);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object kurangiStok(final long idObat, final int jumlah,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfKurangiStok.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, jumlah);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, idObat);
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
          __preparedStmtOfKurangiStok.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Obat>> getAllObat() {
    final String _sql = "SELECT * FROM obat ORDER BY namaObat ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"obat"}, new Callable<List<Obat>>() {
      @Override
      @NonNull
      public List<Obat> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdObat = CursorUtil.getColumnIndexOrThrow(_cursor, "idObat");
          final int _cursorIndexOfNamaObat = CursorUtil.getColumnIndexOrThrow(_cursor, "namaObat");
          final int _cursorIndexOfSatuan = CursorUtil.getColumnIndexOrThrow(_cursor, "satuan");
          final int _cursorIndexOfStok = CursorUtil.getColumnIndexOrThrow(_cursor, "stok");
          final int _cursorIndexOfHargaSatuan = CursorUtil.getColumnIndexOrThrow(_cursor, "hargaSatuan");
          final int _cursorIndexOfStokMinimum = CursorUtil.getColumnIndexOrThrow(_cursor, "stokMinimum");
          final List<Obat> _result = new ArrayList<Obat>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Obat _item;
            final long _tmpIdObat;
            _tmpIdObat = _cursor.getLong(_cursorIndexOfIdObat);
            final String _tmpNamaObat;
            _tmpNamaObat = _cursor.getString(_cursorIndexOfNamaObat);
            final String _tmpSatuan;
            _tmpSatuan = _cursor.getString(_cursorIndexOfSatuan);
            final int _tmpStok;
            _tmpStok = _cursor.getInt(_cursorIndexOfStok);
            final long _tmpHargaSatuan;
            _tmpHargaSatuan = _cursor.getLong(_cursorIndexOfHargaSatuan);
            final int _tmpStokMinimum;
            _tmpStokMinimum = _cursor.getInt(_cursorIndexOfStokMinimum);
            _item = new Obat(_tmpIdObat,_tmpNamaObat,_tmpSatuan,_tmpStok,_tmpHargaSatuan,_tmpStokMinimum);
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
  public Object getById(final long idObat, final Continuation<? super Obat> $completion) {
    final String _sql = "SELECT * FROM obat WHERE idObat = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, idObat);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Obat>() {
      @Override
      @Nullable
      public Obat call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdObat = CursorUtil.getColumnIndexOrThrow(_cursor, "idObat");
          final int _cursorIndexOfNamaObat = CursorUtil.getColumnIndexOrThrow(_cursor, "namaObat");
          final int _cursorIndexOfSatuan = CursorUtil.getColumnIndexOrThrow(_cursor, "satuan");
          final int _cursorIndexOfStok = CursorUtil.getColumnIndexOrThrow(_cursor, "stok");
          final int _cursorIndexOfHargaSatuan = CursorUtil.getColumnIndexOrThrow(_cursor, "hargaSatuan");
          final int _cursorIndexOfStokMinimum = CursorUtil.getColumnIndexOrThrow(_cursor, "stokMinimum");
          final Obat _result;
          if (_cursor.moveToFirst()) {
            final long _tmpIdObat;
            _tmpIdObat = _cursor.getLong(_cursorIndexOfIdObat);
            final String _tmpNamaObat;
            _tmpNamaObat = _cursor.getString(_cursorIndexOfNamaObat);
            final String _tmpSatuan;
            _tmpSatuan = _cursor.getString(_cursorIndexOfSatuan);
            final int _tmpStok;
            _tmpStok = _cursor.getInt(_cursorIndexOfStok);
            final long _tmpHargaSatuan;
            _tmpHargaSatuan = _cursor.getLong(_cursorIndexOfHargaSatuan);
            final int _tmpStokMinimum;
            _tmpStokMinimum = _cursor.getInt(_cursorIndexOfStokMinimum);
            _result = new Obat(_tmpIdObat,_tmpNamaObat,_tmpSatuan,_tmpStok,_tmpHargaSatuan,_tmpStokMinimum);
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

  @Override
  public Object countObat(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM obat";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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

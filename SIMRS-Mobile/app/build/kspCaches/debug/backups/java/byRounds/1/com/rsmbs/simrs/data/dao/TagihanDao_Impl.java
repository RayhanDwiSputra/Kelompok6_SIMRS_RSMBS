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
import com.rsmbs.simrs.data.entity.Tagihan;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TagihanDao_Impl implements TagihanDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Tagihan> __insertionAdapterOfTagihan;

  private final SharedSQLiteStatement __preparedStmtOfBayar;

  public TagihanDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTagihan = new EntityInsertionAdapter<Tagihan>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `tagihan` (`idTagihan`,`noRegistrasi`,`biayaJasaDokter`,`biayaObat`,`totalBiaya`,`metodeBayar`,`statusBayar`,`tglBayar`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Tagihan entity) {
        statement.bindLong(1, entity.getIdTagihan());
        statement.bindLong(2, entity.getNoRegistrasi());
        statement.bindLong(3, entity.getBiayaJasaDokter());
        statement.bindLong(4, entity.getBiayaObat());
        statement.bindLong(5, entity.getTotalBiaya());
        if (entity.getMetodeBayar() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getMetodeBayar());
        }
        statement.bindString(7, entity.getStatusBayar());
        if (entity.getTglBayar() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getTglBayar());
        }
      }
    };
    this.__preparedStmtOfBayar = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE tagihan SET statusBayar = ?, metodeBayar = ?, tglBayar = ?\n"
                + "        WHERE noRegistrasi = ?\n"
                + "        ";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Tagihan tagihan, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTagihan.insertAndReturnId(tagihan);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object bayar(final long noRegistrasi, final String status, final String metode,
      final String tglBayar, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfBayar.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, status);
        _argIndex = 2;
        _stmt.bindString(_argIndex, metode);
        _argIndex = 3;
        _stmt.bindString(_argIndex, tglBayar);
        _argIndex = 4;
        _stmt.bindLong(_argIndex, noRegistrasi);
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
          __preparedStmtOfBayar.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getByRegistrasi(final long noRegistrasi,
      final Continuation<? super Tagihan> $completion) {
    final String _sql = "SELECT * FROM tagihan WHERE noRegistrasi = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, noRegistrasi);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Tagihan>() {
      @Override
      @Nullable
      public Tagihan call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdTagihan = CursorUtil.getColumnIndexOrThrow(_cursor, "idTagihan");
          final int _cursorIndexOfNoRegistrasi = CursorUtil.getColumnIndexOrThrow(_cursor, "noRegistrasi");
          final int _cursorIndexOfBiayaJasaDokter = CursorUtil.getColumnIndexOrThrow(_cursor, "biayaJasaDokter");
          final int _cursorIndexOfBiayaObat = CursorUtil.getColumnIndexOrThrow(_cursor, "biayaObat");
          final int _cursorIndexOfTotalBiaya = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBiaya");
          final int _cursorIndexOfMetodeBayar = CursorUtil.getColumnIndexOrThrow(_cursor, "metodeBayar");
          final int _cursorIndexOfStatusBayar = CursorUtil.getColumnIndexOrThrow(_cursor, "statusBayar");
          final int _cursorIndexOfTglBayar = CursorUtil.getColumnIndexOrThrow(_cursor, "tglBayar");
          final Tagihan _result;
          if (_cursor.moveToFirst()) {
            final long _tmpIdTagihan;
            _tmpIdTagihan = _cursor.getLong(_cursorIndexOfIdTagihan);
            final long _tmpNoRegistrasi;
            _tmpNoRegistrasi = _cursor.getLong(_cursorIndexOfNoRegistrasi);
            final long _tmpBiayaJasaDokter;
            _tmpBiayaJasaDokter = _cursor.getLong(_cursorIndexOfBiayaJasaDokter);
            final long _tmpBiayaObat;
            _tmpBiayaObat = _cursor.getLong(_cursorIndexOfBiayaObat);
            final long _tmpTotalBiaya;
            _tmpTotalBiaya = _cursor.getLong(_cursorIndexOfTotalBiaya);
            final String _tmpMetodeBayar;
            if (_cursor.isNull(_cursorIndexOfMetodeBayar)) {
              _tmpMetodeBayar = null;
            } else {
              _tmpMetodeBayar = _cursor.getString(_cursorIndexOfMetodeBayar);
            }
            final String _tmpStatusBayar;
            _tmpStatusBayar = _cursor.getString(_cursorIndexOfStatusBayar);
            final String _tmpTglBayar;
            if (_cursor.isNull(_cursorIndexOfTglBayar)) {
              _tmpTglBayar = null;
            } else {
              _tmpTglBayar = _cursor.getString(_cursorIndexOfTglBayar);
            }
            _result = new Tagihan(_tmpIdTagihan,_tmpNoRegistrasi,_tmpBiayaJasaDokter,_tmpBiayaObat,_tmpTotalBiaya,_tmpMetodeBayar,_tmpStatusBayar,_tmpTglBayar);
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
  public Flow<Tagihan> getByRegistrasiFlow(final long noRegistrasi) {
    final String _sql = "SELECT * FROM tagihan WHERE noRegistrasi = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, noRegistrasi);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tagihan"}, new Callable<Tagihan>() {
      @Override
      @Nullable
      public Tagihan call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdTagihan = CursorUtil.getColumnIndexOrThrow(_cursor, "idTagihan");
          final int _cursorIndexOfNoRegistrasi = CursorUtil.getColumnIndexOrThrow(_cursor, "noRegistrasi");
          final int _cursorIndexOfBiayaJasaDokter = CursorUtil.getColumnIndexOrThrow(_cursor, "biayaJasaDokter");
          final int _cursorIndexOfBiayaObat = CursorUtil.getColumnIndexOrThrow(_cursor, "biayaObat");
          final int _cursorIndexOfTotalBiaya = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBiaya");
          final int _cursorIndexOfMetodeBayar = CursorUtil.getColumnIndexOrThrow(_cursor, "metodeBayar");
          final int _cursorIndexOfStatusBayar = CursorUtil.getColumnIndexOrThrow(_cursor, "statusBayar");
          final int _cursorIndexOfTglBayar = CursorUtil.getColumnIndexOrThrow(_cursor, "tglBayar");
          final Tagihan _result;
          if (_cursor.moveToFirst()) {
            final long _tmpIdTagihan;
            _tmpIdTagihan = _cursor.getLong(_cursorIndexOfIdTagihan);
            final long _tmpNoRegistrasi;
            _tmpNoRegistrasi = _cursor.getLong(_cursorIndexOfNoRegistrasi);
            final long _tmpBiayaJasaDokter;
            _tmpBiayaJasaDokter = _cursor.getLong(_cursorIndexOfBiayaJasaDokter);
            final long _tmpBiayaObat;
            _tmpBiayaObat = _cursor.getLong(_cursorIndexOfBiayaObat);
            final long _tmpTotalBiaya;
            _tmpTotalBiaya = _cursor.getLong(_cursorIndexOfTotalBiaya);
            final String _tmpMetodeBayar;
            if (_cursor.isNull(_cursorIndexOfMetodeBayar)) {
              _tmpMetodeBayar = null;
            } else {
              _tmpMetodeBayar = _cursor.getString(_cursorIndexOfMetodeBayar);
            }
            final String _tmpStatusBayar;
            _tmpStatusBayar = _cursor.getString(_cursorIndexOfStatusBayar);
            final String _tmpTglBayar;
            if (_cursor.isNull(_cursorIndexOfTglBayar)) {
              _tmpTglBayar = null;
            } else {
              _tmpTglBayar = _cursor.getString(_cursorIndexOfTglBayar);
            }
            _result = new Tagihan(_tmpIdTagihan,_tmpNoRegistrasi,_tmpBiayaJasaDokter,_tmpBiayaObat,_tmpTotalBiaya,_tmpMetodeBayar,_tmpStatusBayar,_tmpTglBayar);
          } else {
            _result = null;
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

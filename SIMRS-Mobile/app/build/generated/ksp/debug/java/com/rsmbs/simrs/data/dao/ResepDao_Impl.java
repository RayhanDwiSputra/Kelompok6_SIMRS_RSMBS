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
import com.rsmbs.simrs.data.entity.DetailResep;
import com.rsmbs.simrs.data.entity.EResep;
import com.rsmbs.simrs.data.entity.ResepQueueItem;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
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
public final class ResepDao_Impl implements ResepDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<EResep> __insertionAdapterOfEResep;

  private final EntityInsertionAdapter<DetailResep> __insertionAdapterOfDetailResep;

  private final SharedSQLiteStatement __preparedStmtOfUpdateStatus;

  public ResepDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEResep = new EntityInsertionAdapter<EResep>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `e_resep` (`idResep`,`idRME`,`noRegistrasi`,`tglResep`,`statusObat`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EResep entity) {
        statement.bindLong(1, entity.getIdResep());
        statement.bindLong(2, entity.getIdRME());
        statement.bindLong(3, entity.getNoRegistrasi());
        statement.bindString(4, entity.getTglResep());
        statement.bindString(5, entity.getStatusObat());
      }
    };
    this.__insertionAdapterOfDetailResep = new EntityInsertionAdapter<DetailResep>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `detail_resep` (`idDetail`,`idResep`,`idObat`,`namaObat`,`dosis`,`jumlah`,`hargaSatuan`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DetailResep entity) {
        statement.bindLong(1, entity.getIdDetail());
        statement.bindLong(2, entity.getIdResep());
        statement.bindLong(3, entity.getIdObat());
        statement.bindString(4, entity.getNamaObat());
        statement.bindString(5, entity.getDosis());
        statement.bindLong(6, entity.getJumlah());
        statement.bindLong(7, entity.getHargaSatuan());
      }
    };
    this.__preparedStmtOfUpdateStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE e_resep SET statusObat = ? WHERE idResep = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertResep(final EResep resep, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfEResep.insertAndReturnId(resep);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertDetail(final List<DetailResep> detail,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDetailResep.insert(detail);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateStatus(final long idResep, final String status,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateStatus.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, status);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, idResep);
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
          __preparedStmtOfUpdateStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getDetail(final long idResep,
      final Continuation<? super List<DetailResep>> $completion) {
    final String _sql = "SELECT * FROM detail_resep WHERE idResep = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, idResep);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<DetailResep>>() {
      @Override
      @NonNull
      public List<DetailResep> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdDetail = CursorUtil.getColumnIndexOrThrow(_cursor, "idDetail");
          final int _cursorIndexOfIdResep = CursorUtil.getColumnIndexOrThrow(_cursor, "idResep");
          final int _cursorIndexOfIdObat = CursorUtil.getColumnIndexOrThrow(_cursor, "idObat");
          final int _cursorIndexOfNamaObat = CursorUtil.getColumnIndexOrThrow(_cursor, "namaObat");
          final int _cursorIndexOfDosis = CursorUtil.getColumnIndexOrThrow(_cursor, "dosis");
          final int _cursorIndexOfJumlah = CursorUtil.getColumnIndexOrThrow(_cursor, "jumlah");
          final int _cursorIndexOfHargaSatuan = CursorUtil.getColumnIndexOrThrow(_cursor, "hargaSatuan");
          final List<DetailResep> _result = new ArrayList<DetailResep>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DetailResep _item;
            final long _tmpIdDetail;
            _tmpIdDetail = _cursor.getLong(_cursorIndexOfIdDetail);
            final long _tmpIdResep;
            _tmpIdResep = _cursor.getLong(_cursorIndexOfIdResep);
            final long _tmpIdObat;
            _tmpIdObat = _cursor.getLong(_cursorIndexOfIdObat);
            final String _tmpNamaObat;
            _tmpNamaObat = _cursor.getString(_cursorIndexOfNamaObat);
            final String _tmpDosis;
            _tmpDosis = _cursor.getString(_cursorIndexOfDosis);
            final int _tmpJumlah;
            _tmpJumlah = _cursor.getInt(_cursorIndexOfJumlah);
            final long _tmpHargaSatuan;
            _tmpHargaSatuan = _cursor.getLong(_cursorIndexOfHargaSatuan);
            _item = new DetailResep(_tmpIdDetail,_tmpIdResep,_tmpIdObat,_tmpNamaObat,_tmpDosis,_tmpJumlah,_tmpHargaSatuan);
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
  public Flow<List<DetailResep>> getDetailFlow(final long idResep) {
    final String _sql = "SELECT * FROM detail_resep WHERE idResep = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, idResep);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"detail_resep"}, new Callable<List<DetailResep>>() {
      @Override
      @NonNull
      public List<DetailResep> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdDetail = CursorUtil.getColumnIndexOrThrow(_cursor, "idDetail");
          final int _cursorIndexOfIdResep = CursorUtil.getColumnIndexOrThrow(_cursor, "idResep");
          final int _cursorIndexOfIdObat = CursorUtil.getColumnIndexOrThrow(_cursor, "idObat");
          final int _cursorIndexOfNamaObat = CursorUtil.getColumnIndexOrThrow(_cursor, "namaObat");
          final int _cursorIndexOfDosis = CursorUtil.getColumnIndexOrThrow(_cursor, "dosis");
          final int _cursorIndexOfJumlah = CursorUtil.getColumnIndexOrThrow(_cursor, "jumlah");
          final int _cursorIndexOfHargaSatuan = CursorUtil.getColumnIndexOrThrow(_cursor, "hargaSatuan");
          final List<DetailResep> _result = new ArrayList<DetailResep>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DetailResep _item;
            final long _tmpIdDetail;
            _tmpIdDetail = _cursor.getLong(_cursorIndexOfIdDetail);
            final long _tmpIdResep;
            _tmpIdResep = _cursor.getLong(_cursorIndexOfIdResep);
            final long _tmpIdObat;
            _tmpIdObat = _cursor.getLong(_cursorIndexOfIdObat);
            final String _tmpNamaObat;
            _tmpNamaObat = _cursor.getString(_cursorIndexOfNamaObat);
            final String _tmpDosis;
            _tmpDosis = _cursor.getString(_cursorIndexOfDosis);
            final int _tmpJumlah;
            _tmpJumlah = _cursor.getInt(_cursorIndexOfJumlah);
            final long _tmpHargaSatuan;
            _tmpHargaSatuan = _cursor.getLong(_cursorIndexOfHargaSatuan);
            _item = new DetailResep(_tmpIdDetail,_tmpIdResep,_tmpIdObat,_tmpNamaObat,_tmpDosis,_tmpJumlah,_tmpHargaSatuan);
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
  public Flow<List<ResepQueueItem>> getAntrianFarmasi() {
    final String _sql = "\n"
            + "        SELECT er.idResep, er.noRegistrasi, ps.namaPasien, p.poliTujuan, er.statusObat, er.tglResep,\n"
            + "               t.statusBayar\n"
            + "        FROM e_resep er\n"
            + "        INNER JOIN pendaftaran p ON er.noRegistrasi = p.noRegistrasi\n"
            + "        INNER JOIN pasien ps ON p.noRekamMedis = ps.noRekamMedis\n"
            + "        LEFT JOIN tagihan t ON t.noRegistrasi = p.noRegistrasi\n"
            + "        WHERE er.statusObat != 'DISERAHKAN'\n"
            + "        ORDER BY er.idResep ASC\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"e_resep", "pendaftaran", "pasien",
        "tagihan"}, new Callable<List<ResepQueueItem>>() {
      @Override
      @NonNull
      public List<ResepQueueItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdResep = 0;
          final int _cursorIndexOfNoRegistrasi = 1;
          final int _cursorIndexOfNamaPasien = 2;
          final int _cursorIndexOfPoliTujuan = 3;
          final int _cursorIndexOfStatusObat = 4;
          final int _cursorIndexOfTglResep = 5;
          final int _cursorIndexOfStatusBayar = 6;
          final List<ResepQueueItem> _result = new ArrayList<ResepQueueItem>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ResepQueueItem _item;
            final long _tmpIdResep;
            _tmpIdResep = _cursor.getLong(_cursorIndexOfIdResep);
            final long _tmpNoRegistrasi;
            _tmpNoRegistrasi = _cursor.getLong(_cursorIndexOfNoRegistrasi);
            final String _tmpNamaPasien;
            _tmpNamaPasien = _cursor.getString(_cursorIndexOfNamaPasien);
            final String _tmpPoliTujuan;
            _tmpPoliTujuan = _cursor.getString(_cursorIndexOfPoliTujuan);
            final String _tmpStatusObat;
            _tmpStatusObat = _cursor.getString(_cursorIndexOfStatusObat);
            final String _tmpTglResep;
            _tmpTglResep = _cursor.getString(_cursorIndexOfTglResep);
            final String _tmpStatusBayar;
            if (_cursor.isNull(_cursorIndexOfStatusBayar)) {
              _tmpStatusBayar = null;
            } else {
              _tmpStatusBayar = _cursor.getString(_cursorIndexOfStatusBayar);
            }
            _item = new ResepQueueItem(_tmpIdResep,_tmpNoRegistrasi,_tmpNamaPasien,_tmpPoliTujuan,_tmpStatusObat,_tmpTglResep,_tmpStatusBayar);
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
  public Object getById(final long idResep, final Continuation<? super EResep> $completion) {
    final String _sql = "SELECT * FROM e_resep WHERE idResep = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, idResep);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<EResep>() {
      @Override
      @Nullable
      public EResep call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdResep = CursorUtil.getColumnIndexOrThrow(_cursor, "idResep");
          final int _cursorIndexOfIdRME = CursorUtil.getColumnIndexOrThrow(_cursor, "idRME");
          final int _cursorIndexOfNoRegistrasi = CursorUtil.getColumnIndexOrThrow(_cursor, "noRegistrasi");
          final int _cursorIndexOfTglResep = CursorUtil.getColumnIndexOrThrow(_cursor, "tglResep");
          final int _cursorIndexOfStatusObat = CursorUtil.getColumnIndexOrThrow(_cursor, "statusObat");
          final EResep _result;
          if (_cursor.moveToFirst()) {
            final long _tmpIdResep;
            _tmpIdResep = _cursor.getLong(_cursorIndexOfIdResep);
            final long _tmpIdRME;
            _tmpIdRME = _cursor.getLong(_cursorIndexOfIdRME);
            final long _tmpNoRegistrasi;
            _tmpNoRegistrasi = _cursor.getLong(_cursorIndexOfNoRegistrasi);
            final String _tmpTglResep;
            _tmpTglResep = _cursor.getString(_cursorIndexOfTglResep);
            final String _tmpStatusObat;
            _tmpStatusObat = _cursor.getString(_cursorIndexOfStatusObat);
            _result = new EResep(_tmpIdResep,_tmpIdRME,_tmpNoRegistrasi,_tmpTglResep,_tmpStatusObat);
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
  public Object getTotalBiayaObat(final long noRegistrasi,
      final Continuation<? super Long> $completion) {
    final String _sql = "\n"
            + "        SELECT COALESCE(SUM(dr.hargaSatuan * dr.jumlah), 0)\n"
            + "        FROM detail_resep dr\n"
            + "        INNER JOIN e_resep er ON dr.idResep = er.idResep\n"
            + "        WHERE er.noRegistrasi = ?\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, noRegistrasi);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Long _result;
          if (_cursor.moveToFirst()) {
            final long _tmp;
            _tmp = _cursor.getLong(0);
            _result = _tmp;
          } else {
            _result = 0L;
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

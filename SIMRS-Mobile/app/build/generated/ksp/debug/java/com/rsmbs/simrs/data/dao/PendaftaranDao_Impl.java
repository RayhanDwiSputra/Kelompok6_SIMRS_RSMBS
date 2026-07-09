package com.rsmbs.simrs.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.rsmbs.simrs.data.entity.AntrianItem;
import com.rsmbs.simrs.data.entity.Pendaftaran;
import com.rsmbs.simrs.data.entity.TagihanQueueItem;
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
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PendaftaranDao_Impl implements PendaftaranDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Pendaftaran> __insertionAdapterOfPendaftaran;

  private final EntityDeletionOrUpdateAdapter<Pendaftaran> __updateAdapterOfPendaftaran;

  private final SharedSQLiteStatement __preparedStmtOfUpdateStatus;

  public PendaftaranDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPendaftaran = new EntityInsertionAdapter<Pendaftaran>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `pendaftaran` (`noRegistrasi`,`noRekamMedis`,`tglKunjungan`,`poliTujuan`,`noAntrian`,`noSEP`,`status`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Pendaftaran entity) {
        statement.bindLong(1, entity.getNoRegistrasi());
        statement.bindString(2, entity.getNoRekamMedis());
        statement.bindString(3, entity.getTglKunjungan());
        statement.bindString(4, entity.getPoliTujuan());
        statement.bindLong(5, entity.getNoAntrian());
        if (entity.getNoSEP() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNoSEP());
        }
        statement.bindString(7, entity.getStatus());
      }
    };
    this.__updateAdapterOfPendaftaran = new EntityDeletionOrUpdateAdapter<Pendaftaran>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `pendaftaran` SET `noRegistrasi` = ?,`noRekamMedis` = ?,`tglKunjungan` = ?,`poliTujuan` = ?,`noAntrian` = ?,`noSEP` = ?,`status` = ? WHERE `noRegistrasi` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Pendaftaran entity) {
        statement.bindLong(1, entity.getNoRegistrasi());
        statement.bindString(2, entity.getNoRekamMedis());
        statement.bindString(3, entity.getTglKunjungan());
        statement.bindString(4, entity.getPoliTujuan());
        statement.bindLong(5, entity.getNoAntrian());
        if (entity.getNoSEP() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNoSEP());
        }
        statement.bindString(7, entity.getStatus());
        statement.bindLong(8, entity.getNoRegistrasi());
      }
    };
    this.__preparedStmtOfUpdateStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE pendaftaran SET status = ? WHERE noRegistrasi = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Pendaftaran pendaftaran,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfPendaftaran.insertAndReturnId(pendaftaran);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Pendaftaran pendaftaran,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPendaftaran.handle(pendaftaran);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateStatus(final long noRegistrasi, final String status,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateStatus.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, status);
        _argIndex = 2;
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
          __preparedStmtOfUpdateStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getById(final long noRegistrasi,
      final Continuation<? super Pendaftaran> $completion) {
    final String _sql = "SELECT * FROM pendaftaran WHERE noRegistrasi = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, noRegistrasi);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Pendaftaran>() {
      @Override
      @Nullable
      public Pendaftaran call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfNoRegistrasi = CursorUtil.getColumnIndexOrThrow(_cursor, "noRegistrasi");
          final int _cursorIndexOfNoRekamMedis = CursorUtil.getColumnIndexOrThrow(_cursor, "noRekamMedis");
          final int _cursorIndexOfTglKunjungan = CursorUtil.getColumnIndexOrThrow(_cursor, "tglKunjungan");
          final int _cursorIndexOfPoliTujuan = CursorUtil.getColumnIndexOrThrow(_cursor, "poliTujuan");
          final int _cursorIndexOfNoAntrian = CursorUtil.getColumnIndexOrThrow(_cursor, "noAntrian");
          final int _cursorIndexOfNoSEP = CursorUtil.getColumnIndexOrThrow(_cursor, "noSEP");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final Pendaftaran _result;
          if (_cursor.moveToFirst()) {
            final long _tmpNoRegistrasi;
            _tmpNoRegistrasi = _cursor.getLong(_cursorIndexOfNoRegistrasi);
            final String _tmpNoRekamMedis;
            _tmpNoRekamMedis = _cursor.getString(_cursorIndexOfNoRekamMedis);
            final String _tmpTglKunjungan;
            _tmpTglKunjungan = _cursor.getString(_cursorIndexOfTglKunjungan);
            final String _tmpPoliTujuan;
            _tmpPoliTujuan = _cursor.getString(_cursorIndexOfPoliTujuan);
            final int _tmpNoAntrian;
            _tmpNoAntrian = _cursor.getInt(_cursorIndexOfNoAntrian);
            final String _tmpNoSEP;
            if (_cursor.isNull(_cursorIndexOfNoSEP)) {
              _tmpNoSEP = null;
            } else {
              _tmpNoSEP = _cursor.getString(_cursorIndexOfNoSEP);
            }
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            _result = new Pendaftaran(_tmpNoRegistrasi,_tmpNoRekamMedis,_tmpTglKunjungan,_tmpPoliTujuan,_tmpNoAntrian,_tmpNoSEP,_tmpStatus);
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
  public Object getNomorAntrianTerakhir(final String poli, final String tanggal,
      final Continuation<? super Integer> $completion) {
    final String _sql = "\n"
            + "        SELECT COALESCE(MAX(noAntrian), 0) FROM pendaftaran\n"
            + "        WHERE poliTujuan = ? AND tglKunjungan = ?\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, poli);
    _argIndex = 2;
    _statement.bindString(_argIndex, tanggal);
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

  @Override
  public Flow<List<AntrianItem>> getAntrianHariIni(final String tanggal) {
    final String _sql = "\n"
            + "        SELECT p.noRegistrasi, p.noRekamMedis, ps.namaPasien, p.poliTujuan, p.noAntrian,\n"
            + "               p.status, ps.jenisPenjamin, p.tglKunjungan\n"
            + "        FROM pendaftaran p\n"
            + "        INNER JOIN pasien ps ON p.noRekamMedis = ps.noRekamMedis\n"
            + "        WHERE p.tglKunjungan = ?\n"
            + "        ORDER BY p.noAntrian ASC\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, tanggal);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"pendaftaran",
        "pasien"}, new Callable<List<AntrianItem>>() {
      @Override
      @NonNull
      public List<AntrianItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfNoRegistrasi = 0;
          final int _cursorIndexOfNoRekamMedis = 1;
          final int _cursorIndexOfNamaPasien = 2;
          final int _cursorIndexOfPoliTujuan = 3;
          final int _cursorIndexOfNoAntrian = 4;
          final int _cursorIndexOfStatus = 5;
          final int _cursorIndexOfJenisPenjamin = 6;
          final int _cursorIndexOfTglKunjungan = 7;
          final List<AntrianItem> _result = new ArrayList<AntrianItem>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AntrianItem _item;
            final long _tmpNoRegistrasi;
            _tmpNoRegistrasi = _cursor.getLong(_cursorIndexOfNoRegistrasi);
            final String _tmpNoRekamMedis;
            _tmpNoRekamMedis = _cursor.getString(_cursorIndexOfNoRekamMedis);
            final String _tmpNamaPasien;
            _tmpNamaPasien = _cursor.getString(_cursorIndexOfNamaPasien);
            final String _tmpPoliTujuan;
            _tmpPoliTujuan = _cursor.getString(_cursorIndexOfPoliTujuan);
            final int _tmpNoAntrian;
            _tmpNoAntrian = _cursor.getInt(_cursorIndexOfNoAntrian);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpJenisPenjamin;
            _tmpJenisPenjamin = _cursor.getString(_cursorIndexOfJenisPenjamin);
            final String _tmpTglKunjungan;
            _tmpTglKunjungan = _cursor.getString(_cursorIndexOfTglKunjungan);
            _item = new AntrianItem(_tmpNoRegistrasi,_tmpNoRekamMedis,_tmpNamaPasien,_tmpPoliTujuan,_tmpNoAntrian,_tmpStatus,_tmpJenisPenjamin,_tmpTglKunjungan);
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
  public Flow<List<AntrianItem>> getAntrianDokter(final String tanggal) {
    final String _sql = "\n"
            + "        SELECT p.noRegistrasi, p.noRekamMedis, ps.namaPasien, p.poliTujuan, p.noAntrian,\n"
            + "               p.status, ps.jenisPenjamin, p.tglKunjungan\n"
            + "        FROM pendaftaran p\n"
            + "        INNER JOIN pasien ps ON p.noRekamMedis = ps.noRekamMedis\n"
            + "        WHERE p.tglKunjungan = ? AND p.status = 'DIPANGGIL'\n"
            + "        ORDER BY p.noAntrian ASC\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, tanggal);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"pendaftaran",
        "pasien"}, new Callable<List<AntrianItem>>() {
      @Override
      @NonNull
      public List<AntrianItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfNoRegistrasi = 0;
          final int _cursorIndexOfNoRekamMedis = 1;
          final int _cursorIndexOfNamaPasien = 2;
          final int _cursorIndexOfPoliTujuan = 3;
          final int _cursorIndexOfNoAntrian = 4;
          final int _cursorIndexOfStatus = 5;
          final int _cursorIndexOfJenisPenjamin = 6;
          final int _cursorIndexOfTglKunjungan = 7;
          final List<AntrianItem> _result = new ArrayList<AntrianItem>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AntrianItem _item;
            final long _tmpNoRegistrasi;
            _tmpNoRegistrasi = _cursor.getLong(_cursorIndexOfNoRegistrasi);
            final String _tmpNoRekamMedis;
            _tmpNoRekamMedis = _cursor.getString(_cursorIndexOfNoRekamMedis);
            final String _tmpNamaPasien;
            _tmpNamaPasien = _cursor.getString(_cursorIndexOfNamaPasien);
            final String _tmpPoliTujuan;
            _tmpPoliTujuan = _cursor.getString(_cursorIndexOfPoliTujuan);
            final int _tmpNoAntrian;
            _tmpNoAntrian = _cursor.getInt(_cursorIndexOfNoAntrian);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpJenisPenjamin;
            _tmpJenisPenjamin = _cursor.getString(_cursorIndexOfJenisPenjamin);
            final String _tmpTglKunjungan;
            _tmpTglKunjungan = _cursor.getString(_cursorIndexOfTglKunjungan);
            _item = new AntrianItem(_tmpNoRegistrasi,_tmpNoRekamMedis,_tmpNamaPasien,_tmpPoliTujuan,_tmpNoAntrian,_tmpStatus,_tmpJenisPenjamin,_tmpTglKunjungan);
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
  public Flow<List<TagihanQueueItem>> getAntrianKasir(final String tanggal) {
    final String _sql = "\n"
            + "        SELECT p.noRegistrasi, ps.namaPasien, p.poliTujuan, ps.jenisPenjamin,\n"
            + "               p.status, t.statusBayar AS statusBayarTagihan\n"
            + "        FROM pendaftaran p\n"
            + "        INNER JOIN pasien ps ON p.noRekamMedis = ps.noRekamMedis\n"
            + "        LEFT JOIN tagihan t ON t.noRegistrasi = p.noRegistrasi\n"
            + "        WHERE p.tglKunjungan = ?\n"
            + "          AND p.status = 'SELESAI_PERIKSA'\n"
            + "          AND (t.statusBayar IS NULL OR t.statusBayar != 'LUNAS')\n"
            + "        ORDER BY p.noAntrian ASC\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, tanggal);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"pendaftaran", "pasien",
        "tagihan"}, new Callable<List<TagihanQueueItem>>() {
      @Override
      @NonNull
      public List<TagihanQueueItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfNoRegistrasi = 0;
          final int _cursorIndexOfNamaPasien = 1;
          final int _cursorIndexOfPoliTujuan = 2;
          final int _cursorIndexOfJenisPenjamin = 3;
          final int _cursorIndexOfStatus = 4;
          final int _cursorIndexOfStatusBayarTagihan = 5;
          final List<TagihanQueueItem> _result = new ArrayList<TagihanQueueItem>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TagihanQueueItem _item;
            final long _tmpNoRegistrasi;
            _tmpNoRegistrasi = _cursor.getLong(_cursorIndexOfNoRegistrasi);
            final String _tmpNamaPasien;
            _tmpNamaPasien = _cursor.getString(_cursorIndexOfNamaPasien);
            final String _tmpPoliTujuan;
            _tmpPoliTujuan = _cursor.getString(_cursorIndexOfPoliTujuan);
            final String _tmpJenisPenjamin;
            _tmpJenisPenjamin = _cursor.getString(_cursorIndexOfJenisPenjamin);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpStatusBayarTagihan;
            if (_cursor.isNull(_cursorIndexOfStatusBayarTagihan)) {
              _tmpStatusBayarTagihan = null;
            } else {
              _tmpStatusBayarTagihan = _cursor.getString(_cursorIndexOfStatusBayarTagihan);
            }
            _item = new TagihanQueueItem(_tmpNoRegistrasi,_tmpNamaPasien,_tmpPoliTujuan,_tmpJenisPenjamin,_tmpStatus,_tmpStatusBayarTagihan);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

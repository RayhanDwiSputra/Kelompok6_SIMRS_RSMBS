package com.rsmbs.simrs.data.dao;

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
import com.rsmbs.simrs.data.entity.Pasien;
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
public final class PasienDao_Impl implements PasienDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Pasien> __insertionAdapterOfPasien;

  public PasienDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPasien = new EntityInsertionAdapter<Pasien>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `pasien` (`noRekamMedis`,`nik`,`namaPasien`,`tanggalLahir`,`jenisKelamin`,`jenisPenjamin`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Pasien entity) {
        statement.bindString(1, entity.getNoRekamMedis());
        statement.bindString(2, entity.getNik());
        statement.bindString(3, entity.getNamaPasien());
        statement.bindString(4, entity.getTanggalLahir());
        statement.bindString(5, entity.getJenisKelamin());
        statement.bindString(6, entity.getJenisPenjamin());
      }
    };
  }

  @Override
  public Object insert(final Pasien pasien, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPasien.insert(pasien);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object cariByNik(final String nik, final Continuation<? super Pasien> $completion) {
    final String _sql = "SELECT * FROM pasien WHERE nik = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, nik);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Pasien>() {
      @Override
      @Nullable
      public Pasien call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfNoRekamMedis = CursorUtil.getColumnIndexOrThrow(_cursor, "noRekamMedis");
          final int _cursorIndexOfNik = CursorUtil.getColumnIndexOrThrow(_cursor, "nik");
          final int _cursorIndexOfNamaPasien = CursorUtil.getColumnIndexOrThrow(_cursor, "namaPasien");
          final int _cursorIndexOfTanggalLahir = CursorUtil.getColumnIndexOrThrow(_cursor, "tanggalLahir");
          final int _cursorIndexOfJenisKelamin = CursorUtil.getColumnIndexOrThrow(_cursor, "jenisKelamin");
          final int _cursorIndexOfJenisPenjamin = CursorUtil.getColumnIndexOrThrow(_cursor, "jenisPenjamin");
          final Pasien _result;
          if (_cursor.moveToFirst()) {
            final String _tmpNoRekamMedis;
            _tmpNoRekamMedis = _cursor.getString(_cursorIndexOfNoRekamMedis);
            final String _tmpNik;
            _tmpNik = _cursor.getString(_cursorIndexOfNik);
            final String _tmpNamaPasien;
            _tmpNamaPasien = _cursor.getString(_cursorIndexOfNamaPasien);
            final String _tmpTanggalLahir;
            _tmpTanggalLahir = _cursor.getString(_cursorIndexOfTanggalLahir);
            final String _tmpJenisKelamin;
            _tmpJenisKelamin = _cursor.getString(_cursorIndexOfJenisKelamin);
            final String _tmpJenisPenjamin;
            _tmpJenisPenjamin = _cursor.getString(_cursorIndexOfJenisPenjamin);
            _result = new Pasien(_tmpNoRekamMedis,_tmpNik,_tmpNamaPasien,_tmpTanggalLahir,_tmpJenisKelamin,_tmpJenisPenjamin);
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
  public Object cariByNrm(final String noRekamMedis,
      final Continuation<? super Pasien> $completion) {
    final String _sql = "SELECT * FROM pasien WHERE noRekamMedis = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, noRekamMedis);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Pasien>() {
      @Override
      @Nullable
      public Pasien call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfNoRekamMedis = CursorUtil.getColumnIndexOrThrow(_cursor, "noRekamMedis");
          final int _cursorIndexOfNik = CursorUtil.getColumnIndexOrThrow(_cursor, "nik");
          final int _cursorIndexOfNamaPasien = CursorUtil.getColumnIndexOrThrow(_cursor, "namaPasien");
          final int _cursorIndexOfTanggalLahir = CursorUtil.getColumnIndexOrThrow(_cursor, "tanggalLahir");
          final int _cursorIndexOfJenisKelamin = CursorUtil.getColumnIndexOrThrow(_cursor, "jenisKelamin");
          final int _cursorIndexOfJenisPenjamin = CursorUtil.getColumnIndexOrThrow(_cursor, "jenisPenjamin");
          final Pasien _result;
          if (_cursor.moveToFirst()) {
            final String _tmpNoRekamMedis;
            _tmpNoRekamMedis = _cursor.getString(_cursorIndexOfNoRekamMedis);
            final String _tmpNik;
            _tmpNik = _cursor.getString(_cursorIndexOfNik);
            final String _tmpNamaPasien;
            _tmpNamaPasien = _cursor.getString(_cursorIndexOfNamaPasien);
            final String _tmpTanggalLahir;
            _tmpTanggalLahir = _cursor.getString(_cursorIndexOfTanggalLahir);
            final String _tmpJenisKelamin;
            _tmpJenisKelamin = _cursor.getString(_cursorIndexOfJenisKelamin);
            final String _tmpJenisPenjamin;
            _tmpJenisPenjamin = _cursor.getString(_cursorIndexOfJenisPenjamin);
            _result = new Pasien(_tmpNoRekamMedis,_tmpNik,_tmpNamaPasien,_tmpTanggalLahir,_tmpJenisKelamin,_tmpJenisPenjamin);
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
  public Flow<List<Pasien>> getAllPasien() {
    final String _sql = "SELECT * FROM pasien ORDER BY namaPasien ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"pasien"}, new Callable<List<Pasien>>() {
      @Override
      @NonNull
      public List<Pasien> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfNoRekamMedis = CursorUtil.getColumnIndexOrThrow(_cursor, "noRekamMedis");
          final int _cursorIndexOfNik = CursorUtil.getColumnIndexOrThrow(_cursor, "nik");
          final int _cursorIndexOfNamaPasien = CursorUtil.getColumnIndexOrThrow(_cursor, "namaPasien");
          final int _cursorIndexOfTanggalLahir = CursorUtil.getColumnIndexOrThrow(_cursor, "tanggalLahir");
          final int _cursorIndexOfJenisKelamin = CursorUtil.getColumnIndexOrThrow(_cursor, "jenisKelamin");
          final int _cursorIndexOfJenisPenjamin = CursorUtil.getColumnIndexOrThrow(_cursor, "jenisPenjamin");
          final List<Pasien> _result = new ArrayList<Pasien>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Pasien _item;
            final String _tmpNoRekamMedis;
            _tmpNoRekamMedis = _cursor.getString(_cursorIndexOfNoRekamMedis);
            final String _tmpNik;
            _tmpNik = _cursor.getString(_cursorIndexOfNik);
            final String _tmpNamaPasien;
            _tmpNamaPasien = _cursor.getString(_cursorIndexOfNamaPasien);
            final String _tmpTanggalLahir;
            _tmpTanggalLahir = _cursor.getString(_cursorIndexOfTanggalLahir);
            final String _tmpJenisKelamin;
            _tmpJenisKelamin = _cursor.getString(_cursorIndexOfJenisKelamin);
            final String _tmpJenisPenjamin;
            _tmpJenisPenjamin = _cursor.getString(_cursorIndexOfJenisPenjamin);
            _item = new Pasien(_tmpNoRekamMedis,_tmpNik,_tmpNamaPasien,_tmpTanggalLahir,_tmpJenisKelamin,_tmpJenisPenjamin);
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
  public Object countPasien(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM pasien";
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

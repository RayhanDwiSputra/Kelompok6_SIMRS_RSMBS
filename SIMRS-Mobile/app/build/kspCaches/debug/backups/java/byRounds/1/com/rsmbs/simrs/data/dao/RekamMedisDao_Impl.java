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
import com.rsmbs.simrs.data.entity.AsesmenMedis;
import com.rsmbs.simrs.data.entity.RekamMedis;
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
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class RekamMedisDao_Impl implements RekamMedisDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AsesmenMedis> __insertionAdapterOfAsesmenMedis;

  private final EntityInsertionAdapter<RekamMedis> __insertionAdapterOfRekamMedis;

  public RekamMedisDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAsesmenMedis = new EntityInsertionAdapter<AsesmenMedis>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `asesmen_medis` (`idAsesmen`,`noRegistrasi`,`tekananDarah`,`nadi`,`suhuTubuh`,`beratBadan`,`keluhanUtama`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AsesmenMedis entity) {
        statement.bindLong(1, entity.getIdAsesmen());
        statement.bindLong(2, entity.getNoRegistrasi());
        statement.bindString(3, entity.getTekananDarah());
        statement.bindLong(4, entity.getNadi());
        statement.bindDouble(5, entity.getSuhuTubuh());
        statement.bindDouble(6, entity.getBeratBadan());
        statement.bindString(7, entity.getKeluhanUtama());
      }
    };
    this.__insertionAdapterOfRekamMedis = new EntityInsertionAdapter<RekamMedis>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `rekam_medis` (`idRME`,`noRegistrasi`,`kodeICD10`,`namaDiagnosa`,`anamnesis`,`rencanaTatalaksana`,`tglPeriksa`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RekamMedis entity) {
        statement.bindLong(1, entity.getIdRME());
        statement.bindLong(2, entity.getNoRegistrasi());
        statement.bindString(3, entity.getKodeICD10());
        statement.bindString(4, entity.getNamaDiagnosa());
        statement.bindString(5, entity.getAnamnesis());
        statement.bindString(6, entity.getRencanaTatalaksana());
        statement.bindString(7, entity.getTglPeriksa());
      }
    };
  }

  @Override
  public Object insertAsesmen(final AsesmenMedis asesmen,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfAsesmenMedis.insertAndReturnId(asesmen);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertRekamMedis(final RekamMedis rekamMedis,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfRekamMedis.insertAndReturnId(rekamMedis);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAsesmenTerakhir(final long noRegistrasi,
      final Continuation<? super AsesmenMedis> $completion) {
    final String _sql = "SELECT * FROM asesmen_medis WHERE noRegistrasi = ? ORDER BY idAsesmen DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, noRegistrasi);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<AsesmenMedis>() {
      @Override
      @Nullable
      public AsesmenMedis call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdAsesmen = CursorUtil.getColumnIndexOrThrow(_cursor, "idAsesmen");
          final int _cursorIndexOfNoRegistrasi = CursorUtil.getColumnIndexOrThrow(_cursor, "noRegistrasi");
          final int _cursorIndexOfTekananDarah = CursorUtil.getColumnIndexOrThrow(_cursor, "tekananDarah");
          final int _cursorIndexOfNadi = CursorUtil.getColumnIndexOrThrow(_cursor, "nadi");
          final int _cursorIndexOfSuhuTubuh = CursorUtil.getColumnIndexOrThrow(_cursor, "suhuTubuh");
          final int _cursorIndexOfBeratBadan = CursorUtil.getColumnIndexOrThrow(_cursor, "beratBadan");
          final int _cursorIndexOfKeluhanUtama = CursorUtil.getColumnIndexOrThrow(_cursor, "keluhanUtama");
          final AsesmenMedis _result;
          if (_cursor.moveToFirst()) {
            final long _tmpIdAsesmen;
            _tmpIdAsesmen = _cursor.getLong(_cursorIndexOfIdAsesmen);
            final long _tmpNoRegistrasi;
            _tmpNoRegistrasi = _cursor.getLong(_cursorIndexOfNoRegistrasi);
            final String _tmpTekananDarah;
            _tmpTekananDarah = _cursor.getString(_cursorIndexOfTekananDarah);
            final int _tmpNadi;
            _tmpNadi = _cursor.getInt(_cursorIndexOfNadi);
            final double _tmpSuhuTubuh;
            _tmpSuhuTubuh = _cursor.getDouble(_cursorIndexOfSuhuTubuh);
            final double _tmpBeratBadan;
            _tmpBeratBadan = _cursor.getDouble(_cursorIndexOfBeratBadan);
            final String _tmpKeluhanUtama;
            _tmpKeluhanUtama = _cursor.getString(_cursorIndexOfKeluhanUtama);
            _result = new AsesmenMedis(_tmpIdAsesmen,_tmpNoRegistrasi,_tmpTekananDarah,_tmpNadi,_tmpSuhuTubuh,_tmpBeratBadan,_tmpKeluhanUtama);
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
  public Object getRekamMedisByRegistrasi(final long noRegistrasi,
      final Continuation<? super RekamMedis> $completion) {
    final String _sql = "SELECT * FROM rekam_medis WHERE noRegistrasi = ? ORDER BY idRME DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, noRegistrasi);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<RekamMedis>() {
      @Override
      @Nullable
      public RekamMedis call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdRME = CursorUtil.getColumnIndexOrThrow(_cursor, "idRME");
          final int _cursorIndexOfNoRegistrasi = CursorUtil.getColumnIndexOrThrow(_cursor, "noRegistrasi");
          final int _cursorIndexOfKodeICD10 = CursorUtil.getColumnIndexOrThrow(_cursor, "kodeICD10");
          final int _cursorIndexOfNamaDiagnosa = CursorUtil.getColumnIndexOrThrow(_cursor, "namaDiagnosa");
          final int _cursorIndexOfAnamnesis = CursorUtil.getColumnIndexOrThrow(_cursor, "anamnesis");
          final int _cursorIndexOfRencanaTatalaksana = CursorUtil.getColumnIndexOrThrow(_cursor, "rencanaTatalaksana");
          final int _cursorIndexOfTglPeriksa = CursorUtil.getColumnIndexOrThrow(_cursor, "tglPeriksa");
          final RekamMedis _result;
          if (_cursor.moveToFirst()) {
            final long _tmpIdRME;
            _tmpIdRME = _cursor.getLong(_cursorIndexOfIdRME);
            final long _tmpNoRegistrasi;
            _tmpNoRegistrasi = _cursor.getLong(_cursorIndexOfNoRegistrasi);
            final String _tmpKodeICD10;
            _tmpKodeICD10 = _cursor.getString(_cursorIndexOfKodeICD10);
            final String _tmpNamaDiagnosa;
            _tmpNamaDiagnosa = _cursor.getString(_cursorIndexOfNamaDiagnosa);
            final String _tmpAnamnesis;
            _tmpAnamnesis = _cursor.getString(_cursorIndexOfAnamnesis);
            final String _tmpRencanaTatalaksana;
            _tmpRencanaTatalaksana = _cursor.getString(_cursorIndexOfRencanaTatalaksana);
            final String _tmpTglPeriksa;
            _tmpTglPeriksa = _cursor.getString(_cursorIndexOfTglPeriksa);
            _result = new RekamMedis(_tmpIdRME,_tmpNoRegistrasi,_tmpKodeICD10,_tmpNamaDiagnosa,_tmpAnamnesis,_tmpRencanaTatalaksana,_tmpTglPeriksa);
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
  public Flow<List<RekamMedis>> getRiwayatRekamMedis(final String noRekamMedis) {
    final String _sql = "\n"
            + "        SELECT rm.* FROM rekam_medis rm\n"
            + "        INNER JOIN pendaftaran p ON rm.noRegistrasi = p.noRegistrasi\n"
            + "        WHERE p.noRekamMedis = ?\n"
            + "        ORDER BY rm.idRME DESC\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, noRekamMedis);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"rekam_medis",
        "pendaftaran"}, new Callable<List<RekamMedis>>() {
      @Override
      @NonNull
      public List<RekamMedis> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdRME = CursorUtil.getColumnIndexOrThrow(_cursor, "idRME");
          final int _cursorIndexOfNoRegistrasi = CursorUtil.getColumnIndexOrThrow(_cursor, "noRegistrasi");
          final int _cursorIndexOfKodeICD10 = CursorUtil.getColumnIndexOrThrow(_cursor, "kodeICD10");
          final int _cursorIndexOfNamaDiagnosa = CursorUtil.getColumnIndexOrThrow(_cursor, "namaDiagnosa");
          final int _cursorIndexOfAnamnesis = CursorUtil.getColumnIndexOrThrow(_cursor, "anamnesis");
          final int _cursorIndexOfRencanaTatalaksana = CursorUtil.getColumnIndexOrThrow(_cursor, "rencanaTatalaksana");
          final int _cursorIndexOfTglPeriksa = CursorUtil.getColumnIndexOrThrow(_cursor, "tglPeriksa");
          final List<RekamMedis> _result = new ArrayList<RekamMedis>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RekamMedis _item;
            final long _tmpIdRME;
            _tmpIdRME = _cursor.getLong(_cursorIndexOfIdRME);
            final long _tmpNoRegistrasi;
            _tmpNoRegistrasi = _cursor.getLong(_cursorIndexOfNoRegistrasi);
            final String _tmpKodeICD10;
            _tmpKodeICD10 = _cursor.getString(_cursorIndexOfKodeICD10);
            final String _tmpNamaDiagnosa;
            _tmpNamaDiagnosa = _cursor.getString(_cursorIndexOfNamaDiagnosa);
            final String _tmpAnamnesis;
            _tmpAnamnesis = _cursor.getString(_cursorIndexOfAnamnesis);
            final String _tmpRencanaTatalaksana;
            _tmpRencanaTatalaksana = _cursor.getString(_cursorIndexOfRencanaTatalaksana);
            final String _tmpTglPeriksa;
            _tmpTglPeriksa = _cursor.getString(_cursorIndexOfTglPeriksa);
            _item = new RekamMedis(_tmpIdRME,_tmpNoRegistrasi,_tmpKodeICD10,_tmpNamaDiagnosa,_tmpAnamnesis,_tmpRencanaTatalaksana,_tmpTglPeriksa);
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

package com.rsmbs.simrs.data;

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
import com.rsmbs.simrs.data.dao.ObatDao;
import com.rsmbs.simrs.data.dao.ObatDao_Impl;
import com.rsmbs.simrs.data.dao.PasienDao;
import com.rsmbs.simrs.data.dao.PasienDao_Impl;
import com.rsmbs.simrs.data.dao.PendaftaranDao;
import com.rsmbs.simrs.data.dao.PendaftaranDao_Impl;
import com.rsmbs.simrs.data.dao.RekamMedisDao;
import com.rsmbs.simrs.data.dao.RekamMedisDao_Impl;
import com.rsmbs.simrs.data.dao.ResepDao;
import com.rsmbs.simrs.data.dao.ResepDao_Impl;
import com.rsmbs.simrs.data.dao.TagihanDao;
import com.rsmbs.simrs.data.dao.TagihanDao_Impl;
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
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile PasienDao _pasienDao;

  private volatile PendaftaranDao _pendaftaranDao;

  private volatile RekamMedisDao _rekamMedisDao;

  private volatile ObatDao _obatDao;

  private volatile ResepDao _resepDao;

  private volatile TagihanDao _tagihanDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `pasien` (`noRekamMedis` TEXT NOT NULL, `nik` TEXT NOT NULL, `namaPasien` TEXT NOT NULL, `tanggalLahir` TEXT NOT NULL, `jenisKelamin` TEXT NOT NULL, `jenisPenjamin` TEXT NOT NULL, PRIMARY KEY(`noRekamMedis`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `pendaftaran` (`noRegistrasi` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `noRekamMedis` TEXT NOT NULL, `tglKunjungan` TEXT NOT NULL, `poliTujuan` TEXT NOT NULL, `noAntrian` INTEGER NOT NULL, `noSEP` TEXT, `status` TEXT NOT NULL, FOREIGN KEY(`noRekamMedis`) REFERENCES `pasien`(`noRekamMedis`) ON UPDATE NO ACTION ON DELETE NO ACTION )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_pendaftaran_noRekamMedis` ON `pendaftaran` (`noRekamMedis`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `asesmen_medis` (`idAsesmen` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `noRegistrasi` INTEGER NOT NULL, `tekananDarah` TEXT NOT NULL, `nadi` INTEGER NOT NULL, `suhuTubuh` REAL NOT NULL, `beratBadan` REAL NOT NULL, `keluhanUtama` TEXT NOT NULL, FOREIGN KEY(`noRegistrasi`) REFERENCES `pendaftaran`(`noRegistrasi`) ON UPDATE NO ACTION ON DELETE NO ACTION )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_asesmen_medis_noRegistrasi` ON `asesmen_medis` (`noRegistrasi`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `rekam_medis` (`idRME` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `noRegistrasi` INTEGER NOT NULL, `kodeICD10` TEXT NOT NULL, `namaDiagnosa` TEXT NOT NULL, `anamnesis` TEXT NOT NULL, `rencanaTatalaksana` TEXT NOT NULL, `tglPeriksa` TEXT NOT NULL, FOREIGN KEY(`noRegistrasi`) REFERENCES `pendaftaran`(`noRegistrasi`) ON UPDATE NO ACTION ON DELETE NO ACTION )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_rekam_medis_noRegistrasi` ON `rekam_medis` (`noRegistrasi`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `obat` (`idObat` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `namaObat` TEXT NOT NULL, `satuan` TEXT NOT NULL, `stok` INTEGER NOT NULL, `hargaSatuan` INTEGER NOT NULL, `stokMinimum` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `e_resep` (`idResep` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `idRME` INTEGER NOT NULL, `noRegistrasi` INTEGER NOT NULL, `tglResep` TEXT NOT NULL, `statusObat` TEXT NOT NULL, FOREIGN KEY(`idRME`) REFERENCES `rekam_medis`(`idRME`) ON UPDATE NO ACTION ON DELETE NO ACTION , FOREIGN KEY(`noRegistrasi`) REFERENCES `pendaftaran`(`noRegistrasi`) ON UPDATE NO ACTION ON DELETE NO ACTION )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_e_resep_idRME` ON `e_resep` (`idRME`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_e_resep_noRegistrasi` ON `e_resep` (`noRegistrasi`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `detail_resep` (`idDetail` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `idResep` INTEGER NOT NULL, `idObat` INTEGER NOT NULL, `namaObat` TEXT NOT NULL, `dosis` TEXT NOT NULL, `jumlah` INTEGER NOT NULL, `hargaSatuan` INTEGER NOT NULL, FOREIGN KEY(`idResep`) REFERENCES `e_resep`(`idResep`) ON UPDATE NO ACTION ON DELETE NO ACTION , FOREIGN KEY(`idObat`) REFERENCES `obat`(`idObat`) ON UPDATE NO ACTION ON DELETE NO ACTION )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_detail_resep_idResep` ON `detail_resep` (`idResep`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_detail_resep_idObat` ON `detail_resep` (`idObat`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `tagihan` (`idTagihan` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `noRegistrasi` INTEGER NOT NULL, `biayaJasaDokter` INTEGER NOT NULL, `biayaObat` INTEGER NOT NULL, `totalBiaya` INTEGER NOT NULL, `metodeBayar` TEXT, `statusBayar` TEXT NOT NULL, `tglBayar` TEXT, FOREIGN KEY(`noRegistrasi`) REFERENCES `pendaftaran`(`noRegistrasi`) ON UPDATE NO ACTION ON DELETE NO ACTION )");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_tagihan_noRegistrasi` ON `tagihan` (`noRegistrasi`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '7a53a0d73b7f8c905caa822e3a086fa4')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `pasien`");
        db.execSQL("DROP TABLE IF EXISTS `pendaftaran`");
        db.execSQL("DROP TABLE IF EXISTS `asesmen_medis`");
        db.execSQL("DROP TABLE IF EXISTS `rekam_medis`");
        db.execSQL("DROP TABLE IF EXISTS `obat`");
        db.execSQL("DROP TABLE IF EXISTS `e_resep`");
        db.execSQL("DROP TABLE IF EXISTS `detail_resep`");
        db.execSQL("DROP TABLE IF EXISTS `tagihan`");
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
        final HashMap<String, TableInfo.Column> _columnsPasien = new HashMap<String, TableInfo.Column>(6);
        _columnsPasien.put("noRekamMedis", new TableInfo.Column("noRekamMedis", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPasien.put("nik", new TableInfo.Column("nik", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPasien.put("namaPasien", new TableInfo.Column("namaPasien", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPasien.put("tanggalLahir", new TableInfo.Column("tanggalLahir", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPasien.put("jenisKelamin", new TableInfo.Column("jenisKelamin", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPasien.put("jenisPenjamin", new TableInfo.Column("jenisPenjamin", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPasien = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPasien = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPasien = new TableInfo("pasien", _columnsPasien, _foreignKeysPasien, _indicesPasien);
        final TableInfo _existingPasien = TableInfo.read(db, "pasien");
        if (!_infoPasien.equals(_existingPasien)) {
          return new RoomOpenHelper.ValidationResult(false, "pasien(com.rsmbs.simrs.data.entity.Pasien).\n"
                  + " Expected:\n" + _infoPasien + "\n"
                  + " Found:\n" + _existingPasien);
        }
        final HashMap<String, TableInfo.Column> _columnsPendaftaran = new HashMap<String, TableInfo.Column>(7);
        _columnsPendaftaran.put("noRegistrasi", new TableInfo.Column("noRegistrasi", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendaftaran.put("noRekamMedis", new TableInfo.Column("noRekamMedis", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendaftaran.put("tglKunjungan", new TableInfo.Column("tglKunjungan", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendaftaran.put("poliTujuan", new TableInfo.Column("poliTujuan", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendaftaran.put("noAntrian", new TableInfo.Column("noAntrian", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendaftaran.put("noSEP", new TableInfo.Column("noSEP", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendaftaran.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPendaftaran = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysPendaftaran.add(new TableInfo.ForeignKey("pasien", "NO ACTION", "NO ACTION", Arrays.asList("noRekamMedis"), Arrays.asList("noRekamMedis")));
        final HashSet<TableInfo.Index> _indicesPendaftaran = new HashSet<TableInfo.Index>(1);
        _indicesPendaftaran.add(new TableInfo.Index("index_pendaftaran_noRekamMedis", false, Arrays.asList("noRekamMedis"), Arrays.asList("ASC")));
        final TableInfo _infoPendaftaran = new TableInfo("pendaftaran", _columnsPendaftaran, _foreignKeysPendaftaran, _indicesPendaftaran);
        final TableInfo _existingPendaftaran = TableInfo.read(db, "pendaftaran");
        if (!_infoPendaftaran.equals(_existingPendaftaran)) {
          return new RoomOpenHelper.ValidationResult(false, "pendaftaran(com.rsmbs.simrs.data.entity.Pendaftaran).\n"
                  + " Expected:\n" + _infoPendaftaran + "\n"
                  + " Found:\n" + _existingPendaftaran);
        }
        final HashMap<String, TableInfo.Column> _columnsAsesmenMedis = new HashMap<String, TableInfo.Column>(7);
        _columnsAsesmenMedis.put("idAsesmen", new TableInfo.Column("idAsesmen", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAsesmenMedis.put("noRegistrasi", new TableInfo.Column("noRegistrasi", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAsesmenMedis.put("tekananDarah", new TableInfo.Column("tekananDarah", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAsesmenMedis.put("nadi", new TableInfo.Column("nadi", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAsesmenMedis.put("suhuTubuh", new TableInfo.Column("suhuTubuh", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAsesmenMedis.put("beratBadan", new TableInfo.Column("beratBadan", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAsesmenMedis.put("keluhanUtama", new TableInfo.Column("keluhanUtama", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAsesmenMedis = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysAsesmenMedis.add(new TableInfo.ForeignKey("pendaftaran", "NO ACTION", "NO ACTION", Arrays.asList("noRegistrasi"), Arrays.asList("noRegistrasi")));
        final HashSet<TableInfo.Index> _indicesAsesmenMedis = new HashSet<TableInfo.Index>(1);
        _indicesAsesmenMedis.add(new TableInfo.Index("index_asesmen_medis_noRegistrasi", false, Arrays.asList("noRegistrasi"), Arrays.asList("ASC")));
        final TableInfo _infoAsesmenMedis = new TableInfo("asesmen_medis", _columnsAsesmenMedis, _foreignKeysAsesmenMedis, _indicesAsesmenMedis);
        final TableInfo _existingAsesmenMedis = TableInfo.read(db, "asesmen_medis");
        if (!_infoAsesmenMedis.equals(_existingAsesmenMedis)) {
          return new RoomOpenHelper.ValidationResult(false, "asesmen_medis(com.rsmbs.simrs.data.entity.AsesmenMedis).\n"
                  + " Expected:\n" + _infoAsesmenMedis + "\n"
                  + " Found:\n" + _existingAsesmenMedis);
        }
        final HashMap<String, TableInfo.Column> _columnsRekamMedis = new HashMap<String, TableInfo.Column>(7);
        _columnsRekamMedis.put("idRME", new TableInfo.Column("idRME", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRekamMedis.put("noRegistrasi", new TableInfo.Column("noRegistrasi", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRekamMedis.put("kodeICD10", new TableInfo.Column("kodeICD10", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRekamMedis.put("namaDiagnosa", new TableInfo.Column("namaDiagnosa", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRekamMedis.put("anamnesis", new TableInfo.Column("anamnesis", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRekamMedis.put("rencanaTatalaksana", new TableInfo.Column("rencanaTatalaksana", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRekamMedis.put("tglPeriksa", new TableInfo.Column("tglPeriksa", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRekamMedis = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysRekamMedis.add(new TableInfo.ForeignKey("pendaftaran", "NO ACTION", "NO ACTION", Arrays.asList("noRegistrasi"), Arrays.asList("noRegistrasi")));
        final HashSet<TableInfo.Index> _indicesRekamMedis = new HashSet<TableInfo.Index>(1);
        _indicesRekamMedis.add(new TableInfo.Index("index_rekam_medis_noRegistrasi", false, Arrays.asList("noRegistrasi"), Arrays.asList("ASC")));
        final TableInfo _infoRekamMedis = new TableInfo("rekam_medis", _columnsRekamMedis, _foreignKeysRekamMedis, _indicesRekamMedis);
        final TableInfo _existingRekamMedis = TableInfo.read(db, "rekam_medis");
        if (!_infoRekamMedis.equals(_existingRekamMedis)) {
          return new RoomOpenHelper.ValidationResult(false, "rekam_medis(com.rsmbs.simrs.data.entity.RekamMedis).\n"
                  + " Expected:\n" + _infoRekamMedis + "\n"
                  + " Found:\n" + _existingRekamMedis);
        }
        final HashMap<String, TableInfo.Column> _columnsObat = new HashMap<String, TableInfo.Column>(6);
        _columnsObat.put("idObat", new TableInfo.Column("idObat", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsObat.put("namaObat", new TableInfo.Column("namaObat", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsObat.put("satuan", new TableInfo.Column("satuan", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsObat.put("stok", new TableInfo.Column("stok", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsObat.put("hargaSatuan", new TableInfo.Column("hargaSatuan", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsObat.put("stokMinimum", new TableInfo.Column("stokMinimum", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysObat = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesObat = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoObat = new TableInfo("obat", _columnsObat, _foreignKeysObat, _indicesObat);
        final TableInfo _existingObat = TableInfo.read(db, "obat");
        if (!_infoObat.equals(_existingObat)) {
          return new RoomOpenHelper.ValidationResult(false, "obat(com.rsmbs.simrs.data.entity.Obat).\n"
                  + " Expected:\n" + _infoObat + "\n"
                  + " Found:\n" + _existingObat);
        }
        final HashMap<String, TableInfo.Column> _columnsEResep = new HashMap<String, TableInfo.Column>(5);
        _columnsEResep.put("idResep", new TableInfo.Column("idResep", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEResep.put("idRME", new TableInfo.Column("idRME", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEResep.put("noRegistrasi", new TableInfo.Column("noRegistrasi", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEResep.put("tglResep", new TableInfo.Column("tglResep", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEResep.put("statusObat", new TableInfo.Column("statusObat", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEResep = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysEResep.add(new TableInfo.ForeignKey("rekam_medis", "NO ACTION", "NO ACTION", Arrays.asList("idRME"), Arrays.asList("idRME")));
        _foreignKeysEResep.add(new TableInfo.ForeignKey("pendaftaran", "NO ACTION", "NO ACTION", Arrays.asList("noRegistrasi"), Arrays.asList("noRegistrasi")));
        final HashSet<TableInfo.Index> _indicesEResep = new HashSet<TableInfo.Index>(2);
        _indicesEResep.add(new TableInfo.Index("index_e_resep_idRME", false, Arrays.asList("idRME"), Arrays.asList("ASC")));
        _indicesEResep.add(new TableInfo.Index("index_e_resep_noRegistrasi", false, Arrays.asList("noRegistrasi"), Arrays.asList("ASC")));
        final TableInfo _infoEResep = new TableInfo("e_resep", _columnsEResep, _foreignKeysEResep, _indicesEResep);
        final TableInfo _existingEResep = TableInfo.read(db, "e_resep");
        if (!_infoEResep.equals(_existingEResep)) {
          return new RoomOpenHelper.ValidationResult(false, "e_resep(com.rsmbs.simrs.data.entity.EResep).\n"
                  + " Expected:\n" + _infoEResep + "\n"
                  + " Found:\n" + _existingEResep);
        }
        final HashMap<String, TableInfo.Column> _columnsDetailResep = new HashMap<String, TableInfo.Column>(7);
        _columnsDetailResep.put("idDetail", new TableInfo.Column("idDetail", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDetailResep.put("idResep", new TableInfo.Column("idResep", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDetailResep.put("idObat", new TableInfo.Column("idObat", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDetailResep.put("namaObat", new TableInfo.Column("namaObat", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDetailResep.put("dosis", new TableInfo.Column("dosis", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDetailResep.put("jumlah", new TableInfo.Column("jumlah", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDetailResep.put("hargaSatuan", new TableInfo.Column("hargaSatuan", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDetailResep = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysDetailResep.add(new TableInfo.ForeignKey("e_resep", "NO ACTION", "NO ACTION", Arrays.asList("idResep"), Arrays.asList("idResep")));
        _foreignKeysDetailResep.add(new TableInfo.ForeignKey("obat", "NO ACTION", "NO ACTION", Arrays.asList("idObat"), Arrays.asList("idObat")));
        final HashSet<TableInfo.Index> _indicesDetailResep = new HashSet<TableInfo.Index>(2);
        _indicesDetailResep.add(new TableInfo.Index("index_detail_resep_idResep", false, Arrays.asList("idResep"), Arrays.asList("ASC")));
        _indicesDetailResep.add(new TableInfo.Index("index_detail_resep_idObat", false, Arrays.asList("idObat"), Arrays.asList("ASC")));
        final TableInfo _infoDetailResep = new TableInfo("detail_resep", _columnsDetailResep, _foreignKeysDetailResep, _indicesDetailResep);
        final TableInfo _existingDetailResep = TableInfo.read(db, "detail_resep");
        if (!_infoDetailResep.equals(_existingDetailResep)) {
          return new RoomOpenHelper.ValidationResult(false, "detail_resep(com.rsmbs.simrs.data.entity.DetailResep).\n"
                  + " Expected:\n" + _infoDetailResep + "\n"
                  + " Found:\n" + _existingDetailResep);
        }
        final HashMap<String, TableInfo.Column> _columnsTagihan = new HashMap<String, TableInfo.Column>(8);
        _columnsTagihan.put("idTagihan", new TableInfo.Column("idTagihan", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTagihan.put("noRegistrasi", new TableInfo.Column("noRegistrasi", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTagihan.put("biayaJasaDokter", new TableInfo.Column("biayaJasaDokter", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTagihan.put("biayaObat", new TableInfo.Column("biayaObat", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTagihan.put("totalBiaya", new TableInfo.Column("totalBiaya", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTagihan.put("metodeBayar", new TableInfo.Column("metodeBayar", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTagihan.put("statusBayar", new TableInfo.Column("statusBayar", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTagihan.put("tglBayar", new TableInfo.Column("tglBayar", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTagihan = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysTagihan.add(new TableInfo.ForeignKey("pendaftaran", "NO ACTION", "NO ACTION", Arrays.asList("noRegistrasi"), Arrays.asList("noRegistrasi")));
        final HashSet<TableInfo.Index> _indicesTagihan = new HashSet<TableInfo.Index>(1);
        _indicesTagihan.add(new TableInfo.Index("index_tagihan_noRegistrasi", true, Arrays.asList("noRegistrasi"), Arrays.asList("ASC")));
        final TableInfo _infoTagihan = new TableInfo("tagihan", _columnsTagihan, _foreignKeysTagihan, _indicesTagihan);
        final TableInfo _existingTagihan = TableInfo.read(db, "tagihan");
        if (!_infoTagihan.equals(_existingTagihan)) {
          return new RoomOpenHelper.ValidationResult(false, "tagihan(com.rsmbs.simrs.data.entity.Tagihan).\n"
                  + " Expected:\n" + _infoTagihan + "\n"
                  + " Found:\n" + _existingTagihan);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "7a53a0d73b7f8c905caa822e3a086fa4", "98ab96e3e2e214a85756f5c67c6ddd20");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "pasien","pendaftaran","asesmen_medis","rekam_medis","obat","e_resep","detail_resep","tagihan");
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
      _db.execSQL("DELETE FROM `asesmen_medis`");
      _db.execSQL("DELETE FROM `e_resep`");
      _db.execSQL("DELETE FROM `rekam_medis`");
      _db.execSQL("DELETE FROM `tagihan`");
      _db.execSQL("DELETE FROM `pendaftaran`");
      _db.execSQL("DELETE FROM `pasien`");
      _db.execSQL("DELETE FROM `detail_resep`");
      _db.execSQL("DELETE FROM `obat`");
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
    _typeConvertersMap.put(PasienDao.class, PasienDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PendaftaranDao.class, PendaftaranDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(RekamMedisDao.class, RekamMedisDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ObatDao.class, ObatDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ResepDao.class, ResepDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TagihanDao.class, TagihanDao_Impl.getRequiredConverters());
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
  public PasienDao pasienDao() {
    if (_pasienDao != null) {
      return _pasienDao;
    } else {
      synchronized(this) {
        if(_pasienDao == null) {
          _pasienDao = new PasienDao_Impl(this);
        }
        return _pasienDao;
      }
    }
  }

  @Override
  public PendaftaranDao pendaftaranDao() {
    if (_pendaftaranDao != null) {
      return _pendaftaranDao;
    } else {
      synchronized(this) {
        if(_pendaftaranDao == null) {
          _pendaftaranDao = new PendaftaranDao_Impl(this);
        }
        return _pendaftaranDao;
      }
    }
  }

  @Override
  public RekamMedisDao rekamMedisDao() {
    if (_rekamMedisDao != null) {
      return _rekamMedisDao;
    } else {
      synchronized(this) {
        if(_rekamMedisDao == null) {
          _rekamMedisDao = new RekamMedisDao_Impl(this);
        }
        return _rekamMedisDao;
      }
    }
  }

  @Override
  public ObatDao obatDao() {
    if (_obatDao != null) {
      return _obatDao;
    } else {
      synchronized(this) {
        if(_obatDao == null) {
          _obatDao = new ObatDao_Impl(this);
        }
        return _obatDao;
      }
    }
  }

  @Override
  public ResepDao resepDao() {
    if (_resepDao != null) {
      return _resepDao;
    } else {
      synchronized(this) {
        if(_resepDao == null) {
          _resepDao = new ResepDao_Impl(this);
        }
        return _resepDao;
      }
    }
  }

  @Override
  public TagihanDao tagihanDao() {
    if (_tagihanDao != null) {
      return _tagihanDao;
    } else {
      synchronized(this) {
        if(_tagihanDao == null) {
          _tagihanDao = new TagihanDao_Impl(this);
        }
        return _tagihanDao;
      }
    }
  }
}

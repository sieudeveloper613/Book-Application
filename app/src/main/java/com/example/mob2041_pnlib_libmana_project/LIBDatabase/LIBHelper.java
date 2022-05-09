package com.example.mob2041_pnlib_libmana_project.LIBDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

    // 1 : kế thừa SQLiteOpenHelper
public class LIBHelper extends SQLiteOpenHelper {

    // 2 : đặt tên database
     static final String dbName = "PNLIB";

     // 3 : thiết lập phiên bản ứng dụng cho những lần cật nhật tiếp theo
     static final int dbVersion = 1;

     // 4 : tạo phương thức hàm tạo( hàm dừng )

        public LIBHelper(Context context){
            super(context, dbName, null, dbVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // tạo bản thành viên
            String createTableThanhVien =
                    "create table ThanhVien (" + "maTV INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                 "hoTen TEXT NOT NULL," +
                                                 "namSinh TEXT NOT NULL)";
            db.execSQL(createTableThanhVien);

            // tạo bản Thủ Thư
            String createTableThuThu =
                    "create table ThuThu (" + "maTT TEXT PRIMARY KEY ," +
                                              "hoTen TEXT NOT NULL," +
                                              "matKhau TEXT NOT NULL)";
            db.execSQL(createTableThuThu);

            // tạo bản Loại Sách
            String createTableLoaiSach =
                    "create table LoaiSach (" + "maLoai INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "tenLoai TEXT NOT NULL)";
            db.execSQL(createTableLoaiSach);

            // tạo bản Sách
            String createTableSach =
                    "create table Sach (" + "maSach INTEGER PRIMARY KEY AUTOINCREMENT," +
                                            "tenSach TEXT NOT NULL," +
                                            "giaThue INTEGER NOT NULL," +
                                            "maLoai INTEGER REFERENCES LoaiSach(maLoai))";
            db.execSQL(createTableSach);

            //tạo bản Phiếu Mượn
            String createTablePhieuMuon =
                    "create table PhieuMuon (" + "maPM INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                 "maTT TEXT REFERENCES ThuThu(maTT)," +
                                                 "maTV INTEGER REFERENCES ThanhVien(maTV)," +
                                                 "maSach INTEGER REFERENCES Sach(loaiSach)," +
                                                 "Ngay DATE NOT NULL," +
                                                 "traSach INTEGER NOT NULL," +
                                                 "tienThue INTEGER NOT NULL)";
            db.execSQL(createTablePhieuMuon);
        }

        // nếu code sai dữ liệu hay muốn nâng cấp dữ liệu, thay thế dữ liệu, ta sẽ sử dựng phương thức onUpgrade
        // chú ý khi nâng cấp nhớ nâng phiên bản dbVersion lên


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String dropTableThanhVien = "drop table if exists ThanhVien";
            db.execSQL(dropTableThanhVien);

            String dropTableThuThu = "drop table if exists ThuThu";
            db.execSQL(dropTableThuThu);

            String dropTableLoaiSach = "drop table if exists LoaiSach";
            db.execSQL(dropTableLoaiSach);

            String dropTableSach = "drop table if exists Sach";
            db.execSQL(dropTableSach);

            String dropTablePhieuMuon = "drop table if exists PhieuMuon";
            db.execSQL(dropTablePhieuMuon);

            onCreate(db);
        }
    }

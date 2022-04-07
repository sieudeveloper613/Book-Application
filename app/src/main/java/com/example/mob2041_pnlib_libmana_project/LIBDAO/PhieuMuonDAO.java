package com.example.mob2041_pnlib_libmana_project.LIBDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mob2041_pnlib_libmana_project.LIBDatabase.LIBHelper;
import com.example.mob2041_pnlib_libmana_project.Model.PhieuMuon;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDAO {
    SQLiteDatabase db;

    public PhieuMuonDAO(Context context){
        LIBHelper libHelper = new LIBHelper(context);
        db = libHelper.getWritableDatabase();
    }

    public long insert(PhieuMuon obj){
        ContentValues contentValues = new ContentValues();
        //contentValues.put("maPM", obj.maPM);
        contentValues.put("maTT", obj.maTT);
        contentValues.put("maTV", obj.maTV);
        contentValues.put("maSach", obj.maSach);
        contentValues.put("tienThue", obj.tienThue);
        contentValues.put("traSach", obj.traSach);
        contentValues.put("ngay", String.valueOf(obj.ngay));

        return db.insert("PhieuMuon", null, contentValues);
    }

    public int update(PhieuMuon obj){
        ContentValues  values = new ContentValues();
        //values.put("maPM", obj.maPM);
        values.put("maTT", obj.maTT);
        values.put("maTV", obj.maTV);
        values.put("maSach", obj.maSach);
        values.put("tienThue", obj.tienThue);
        values.put("traSach", obj.traSach);
        values.put("ngay", String.valueOf(obj.ngay));

        return db.update("PhieuMuon", values, "maPM=?", new String []{String.valueOf(obj.maPM)});
    }

    public  int delete(String id){
        return db.delete("PhieuMuon", "maPM=?", new String[]{id});
    }

    //lấy tất cả dữ liệu
    public List<PhieuMuon> getAll(){
        String sql = "SELECT * FROM PhieuMuon";
        return getData(sql);
    }

    //lấy dữ liệu theo id
    public PhieuMuon getID(String id){
        String sql = "SELECT * FROM PhieuMuon WHERE maPM=?";
        List<PhieuMuon> list  = getData(sql, id);
        return list.get(0);
    }

    // lấy dữ liệu nhiều tham số
    private List<PhieuMuon> getData(String sql, String...selectionArgs){
        List<PhieuMuon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            PhieuMuon obj = new PhieuMuon();
            obj.maPM = Integer.parseInt(c.getString(c.getColumnIndex("maPM")));
            obj.maTT = c.getString(c.getColumnIndex("maTT"));
            obj.maTV = Integer.parseInt(c.getString(c.getColumnIndex("maTV")));
            obj.maSach = Integer.parseInt(c.getString(c.getColumnIndex("maSach")));
            obj.tienThue = Integer.parseInt(c.getString(c.getColumnIndex("tienThue")));
            obj.traSach = Integer.parseInt(c.getString(c.getColumnIndex("traSach")));
            obj.ngay = Date.valueOf(c.getString(c.getColumnIndex("Ngay")));
            list.add(obj);
        }
        return list;
    }
}

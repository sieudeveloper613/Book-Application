package com.example.mob2041_pnlib_libmana_project.LIBDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mob2041_pnlib_libmana_project.LIBDatabase.LIBHelper;
import com.example.mob2041_pnlib_libmana_project.Model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO {
    SQLiteDatabase db;

    public ThanhVienDAO(Context context){
        LIBHelper libHelper = new LIBHelper(context);
        db = libHelper.getWritableDatabase();
        //checked
    }

    public long insert(ThanhVien obj){
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoTen", obj.hoTen);
        contentValues.put("namSinh", obj.namSinh);

        return db.insert("ThanhVien", null, contentValues);
        //checked
    }

    public int update(ThanhVien obj){
    ContentValues  values = new ContentValues();
    values.put("hoTen", obj.hoTen);
    values.put("namSinh", obj.namSinh);

    return db.update("ThanhVien", values, "maTV=?", new String []{String.valueOf(obj.maTV)});
    //checked
    }

    public  int delete(String id){
        return db.delete("ThanhVien", "maTV=?", new String[]{id});
        //checked
    }

    //lấy tất cả dữ liệu
    public List<ThanhVien> getAll(){
    String sql = "SELECT * FROM ThanhVien";
    return getData(sql);
        //checked
    }

    //lấy dữ liệu theo id
    public ThanhVien getID(String id){
    String sql = "SELECT * FROM ThanhVien WHERE maTV=?";
    List<ThanhVien> list  = getData(sql, id);
    return list.get(0);
        //checked
    }

    // lấy dữ liệu nhiều tham số
    private List<ThanhVien> getData(String sql, String...selectionArgs){
        List<ThanhVien> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            ThanhVien obj = new ThanhVien();
            obj.maTV = Integer.parseInt(c.getString(c.getColumnIndex("maTV")));
            obj.hoTen = c.getString(c.getColumnIndex("hoTen"));
            obj.namSinh = c.getString(c.getColumnIndex("namSinh"));
            list.add(obj);
        }
        return list;
        //checked
    }
}

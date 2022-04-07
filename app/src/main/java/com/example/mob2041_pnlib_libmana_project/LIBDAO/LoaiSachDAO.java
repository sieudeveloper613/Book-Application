package com.example.mob2041_pnlib_libmana_project.LIBDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mob2041_pnlib_libmana_project.LIBDatabase.LIBHelper;
import com.example.mob2041_pnlib_libmana_project.Model.LoaiSach;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDAO {
    static SQLiteDatabase db;

    public LoaiSachDAO(Context context){
        LIBHelper libHelper = new LIBHelper(context);
        db = libHelper.getWritableDatabase();
    }

    public long insert(LoaiSach obj){
        ContentValues contentValues = new ContentValues();
       // contentValues.put("maLoai", obj.maLoai);
        contentValues.put("tenLoai", obj.tenLoai);

        return db.insert("LoaiSach", null, contentValues);
    }

    public int update(LoaiSach obj){
        ContentValues  values = new ContentValues();
        //values.put("maLoai", obj.maLoai);
        values.put("tenLoai", obj.tenLoai);

        return db.update("LoaiSach", values, "maLoai=?", new String []{String.valueOf(obj.maLoai)});
    }

    public  int delete(String id){
        return db.delete("LoaiSach", "maLoai=?", new String[]{id});
    }

    //lấy tất cả dữ liệu
    public List<LoaiSach> getAll(){
        String sql = "SELECT * FROM LoaiSach";
        return getData(sql);
    }

    //lấy dữ liệu theo id
    public static LoaiSach getID(String id){
        String sql = "SELECT * FROM LoaiSach WHERE maLoai=?";
        List<LoaiSach> list  = getData(sql, id);
        return list.get(0);
    }

    // lấy dữ liệu nhiều tham số
    private static List<LoaiSach> getData(String sql, String... selectionArgs){
        List<LoaiSach> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            LoaiSach obj = new LoaiSach();
            obj.maLoai = Integer.parseInt(c.getString(c.getColumnIndex("maLoai")));
            obj.tenLoai = c.getString(c.getColumnIndex("tenLoai"));
            list.add(obj);
        }
        return list;
    }
}

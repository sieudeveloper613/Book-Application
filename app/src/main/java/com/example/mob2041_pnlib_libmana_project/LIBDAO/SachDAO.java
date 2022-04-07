package com.example.mob2041_pnlib_libmana_project.LIBDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mob2041_pnlib_libmana_project.LIBDatabase.LIBHelper;
import com.example.mob2041_pnlib_libmana_project.Model.Sach;
import com.example.mob2041_pnlib_libmana_project.Model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    SQLiteDatabase db;

    public SachDAO(Context context){
        LIBHelper libHelper = new LIBHelper(context);
        db = libHelper.getWritableDatabase();
    }
    // masach ten sach giathue maloai
    public long insert(Sach obj){
        ContentValues contentValues = new ContentValues();
        //contentValues.put("maSach", obj.maSach);
        contentValues.put("tenSach", obj.tenSach);
        contentValues.put("giaThue", obj.giaThue);
        contentValues.put("maLoai", obj.maLoai);
        return db.insert("Sach", null, contentValues);
    }

    public int update(Sach obj){
        ContentValues  values = new ContentValues();
        //values.put("maSach", obj.maSach);
        values.put("tenSach", obj.tenSach);

        return db.update("Sach", values, "maSach=?", new String []{String.valueOf(obj.maSach)});
    }

    public  int delete(String id){
        return db.delete("Sach", "maSach=?", new String[]{id});
    }

    //lấy tất cả dữ liệu
    public List<Sach> getAll(){
        String sql = "SELECT * FROM Sach";
        return getData(sql);
    }

    //lấy dữ liệu theo id
    public Sach getID(String id){
        String sql = "SELECT * FROM Sach WHERE maSach=?";
        List<Sach> list  = getData(sql, id);
        return list.get(0);
    }

    // lấy dữ liệu nhiều tham số
    private List<Sach> getData(String sql, String...selectionArgs){
        List<Sach> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            Sach obj = new Sach();
            obj.maSach = Integer.parseInt(c.getString(c.getColumnIndex("maSach")));
            obj.tenSach = c.getString(c.getColumnIndex("tenSach"));
            obj.giaThue = Integer.parseInt(c.getString(c.getColumnIndex("giaThue")));
            obj.maLoai = Integer.parseInt(c.getString(c.getColumnIndex("maLoai")));
            list.add(obj);
        }
        return list;
    }
}

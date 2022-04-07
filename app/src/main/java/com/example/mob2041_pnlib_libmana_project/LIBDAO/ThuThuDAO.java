package com.example.mob2041_pnlib_libmana_project.LIBDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mob2041_pnlib_libmana_project.LIBDatabase.LIBHelper;
import com.example.mob2041_pnlib_libmana_project.Model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO {
    SQLiteDatabase db;
    private String sql;
    private String[] selectionArgs;

    public ThuThuDAO(Context context){
        LIBHelper libHelper = new LIBHelper(context);
        db = libHelper.getWritableDatabase();
    }

    public long insert(ThuThu obj){
        ContentValues contentValues = new ContentValues();
        contentValues.put("maTT", obj.maTT);
        contentValues.put("hoTen", obj.hoTen);
        contentValues.put("matKhau", obj.matKhau);

        return db.insert("ThuThu", null, contentValues);
    }

    public int update(ThuThu obj){
        ContentValues  values = new ContentValues();
        values.put("hoTen", obj.hoTen);
        values.put("matKhau", obj.matKhau);

        return db.update("ThuThu", values, "maTT=?", new String []{obj.maTT});
    }

    public  int delete(String id){
        return db.delete("ThuThu", "maTT=?", new String[]{id});
    }

    //lấy tất cả dữ liệu
    public List<ThuThu> getAll(){
        String sql = "SELECT * FROM ThuThu";
        return getData(sql);
    }

    //lấy dữ liệu theo id
    public ThuThu getID(String id){
        String sql = "SELECT * FROM ThuThu WHERE maTT=?";
        List<ThuThu> list  = getData(sql, id);
        return list.get(0);
    }

    // lấy dữ liệu nhiều tham số
    private List<ThuThu> getData(String sql, String...selectionArgs){
        this.sql = sql;
        this.selectionArgs = selectionArgs;
        List<ThuThu> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            ThuThu obj = new ThuThu();
            obj.maTT = c.getString(c.getColumnIndex("maTT"));
            obj.hoTen = c.getString(c.getColumnIndex("hoTen"));
            obj.matKhau = c.getString(c.getColumnIndex("matKhau"));
            list.add(obj);
        }
        return list;
    }

    public int checkLogin(String id, String passWord){
        String sql = "SELECT * FROM ThuThu WHERE maTT=? AND matKhau=?";
        List<ThuThu> list = getData(sql, id, passWord);
        if (list.size() == 0)
            return -1;
        return 1;
    }
    // checked course 4 -video demo
}

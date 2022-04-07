package com.example.mob2041_pnlib_libmana_project.LIBDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import com.example.mob2041_pnlib_libmana_project.LIBDatabase.LIBHelper;
import com.example.mob2041_pnlib_libmana_project.Model.Sach;
import com.example.mob2041_pnlib_libmana_project.Model.TopBook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {

    private SQLiteDatabase db;
    private Context context;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ThongKeDAO(Context context){
        this.context = context;
        LIBHelper helper = new LIBHelper(context);
        db = helper.getWritableDatabase();
    }

    // thong ke top 10
    public List<TopBook> getTop(){
    String sqlTop = "SELECT maSach, count(maSach) as soLuong FROM PhieuMuon GROUP BY maSach ORDER BY soLuong DESC LIMIT 10";
    List<TopBook> list  = new ArrayList<TopBook>();
    SachDAO sachDAO = new SachDAO(context);
        Cursor c = db.rawQuery(sqlTop, null);
        while(c.moveToNext()){
            TopBook top =  new TopBook();
            Sach sach = sachDAO.getID(c.getString(c.getColumnIndex("maSach")));
            top.Book = sach.tenSach;
            top.Mount = Integer.parseInt(c.getString(c.getColumnIndex("soLuong")));
            list.add(top);
        }
        return list;
        //checked with course 3 - video 3.3
    }

    //
    public int getDoanhThu(String DayFrom, String DayTo){
    String sqlDoanhThu = "SELECT SUM(tienThue) as doanhThu FROM PhieuMuon WHERE ngay BETWEEN ? AND ?";
    List<Integer> list = new ArrayList<>();
    Cursor cursor = db.rawQuery(sqlDoanhThu, new String[]{DayFrom , DayTo});

    while (cursor.moveToNext()){
        try {
        list.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("doanhThu"))));
        } catch (Exception e){
            list.add(0);
        }
    }
    return list.get(0);
        //checked with course 3 - video 3.3
    }
}

package com.example.mob2041_pnlib_libmana_project.LIBAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mob2041_pnlib_libmana_project.Model.LoaiSach;
import com.example.mob2041_pnlib_libmana_project.R;

import java.util.ArrayList;

public class BookTypeSpinnerAdapter extends ArrayAdapter<LoaiSach> {
    private Context context;
    private ArrayList<LoaiSach> lists;
    TextView tvBookType, tvBookName;

    public BookTypeSpinnerAdapter (Context context, ArrayList<LoaiSach> lists){
        super(context, 0, lists);
            this.context = context;
            this.lists = lists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.booktype_spinner_dialog, null);
        }

        final LoaiSach item = lists.get(position);
        if(item != null){
            tvBookType = view.findViewById(R.id.tv_bookType);
            tvBookType.setText(item.maLoai + ". ");
            tvBookName = view.findViewById(R.id.tv_bookName);
            tvBookName.setText(item.tenLoai);
        }
        return view;
        // checked
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.booktype_spinner_dialog, null);
        }

        final LoaiSach item = lists.get(position);
        if(item != null){
            tvBookType = view.findViewById(R.id.tv_bookType);
            tvBookType.setText(item.maLoai + ". ");
            tvBookName = view.findViewById(R.id.tv_bookName);
            tvBookName.setText(item.tenLoai);
        }
        return view;
        // checked course 6 - video 6.1.1
    }
}

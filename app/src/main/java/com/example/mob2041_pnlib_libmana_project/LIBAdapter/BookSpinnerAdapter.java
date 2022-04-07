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
import com.example.mob2041_pnlib_libmana_project.Model.Sach;
import com.example.mob2041_pnlib_libmana_project.R;

import java.util.ArrayList;

public class BookSpinnerAdapter extends ArrayAdapter<Sach> {
    private Context context;
    private ArrayList<Sach> lists;
    TextView tvBookId, tvBookName;

    public BookSpinnerAdapter(Context context, ArrayList<Sach> lists){
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
            view = inflater.inflate(R.layout.book_item_spinner, null);

        }
        final Sach item = lists.get(position);
        if(item != null){
            tvBookId = view.findViewById(R.id.tv_bookId);
            tvBookId.setText(item.maSach + ". ");
            tvBookName = view.findViewById(R.id.tv_bookName);
            tvBookName.setText(item.tenSach);
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.book_item_spinner, null);

        }
        final Sach item = lists.get(position);
        if(item != null){
            tvBookId = view.findViewById(R.id.tv_bookId);
            tvBookId.setText(item.maSach + ". ");
            tvBookName = view.findViewById(R.id.tv_bookName);
            tvBookName.setText(item.tenSach);
        }
        return view;
        // checked course 6 - video 6.2.1
    }
}

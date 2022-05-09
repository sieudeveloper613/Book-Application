package com.example.mob2041_pnlib_libmana_project.LIBAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mob2041_pnlib_libmana_project.LIBFragment.BookTypeManagerFragment;
import com.example.mob2041_pnlib_libmana_project.Model.LoaiSach;
import com.example.mob2041_pnlib_libmana_project.R;

import java.util.ArrayList;

public class BookTypeManagerAdapter extends ArrayAdapter<LoaiSach> {
    private Context context;
    BookTypeManagerFragment fragment;
    private ArrayList<LoaiSach> lists;
    TextView tvCategoryId, tvCategoryName;
    ImageView imgDelete;

    public BookTypeManagerAdapter(Context context, BookTypeManagerFragment fragment, ArrayList<LoaiSach> lists){
        super(context, 0, lists);
        this.context = context;
        this.fragment = fragment;
        this.lists = lists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_category, null);
        }
        final LoaiSach item = lists.get(position);
        if(item != null){
            tvCategoryId = view.findViewById(R.id.tv_item_category_id);
            tvCategoryId.setText(String.valueOf(item.maLoai));

            tvCategoryName = view.findViewById(R.id.tv_item_category_name);
            tvCategoryName.setText(item.tenLoai);

            imgDelete = view.findViewById(R.id.img_item_delete_category);
        }
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.Delete(String.valueOf(item.maLoai));
            }
        });
        return view;

    }
}

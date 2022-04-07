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

import com.example.mob2041_pnlib_libmana_project.LIBFragment.TopBookFragment;
import com.example.mob2041_pnlib_libmana_project.Model.TopBook;
import com.example.mob2041_pnlib_libmana_project.R;

import java.util.ArrayList;

public class TopBookAdapter extends ArrayAdapter<TopBook> {
    private Context context;
    TopBookFragment fragment;
    private ArrayList<TopBook> lists;
    TextView tvBook, tvMount;
    ImageView imgDel;

    public TopBookAdapter(Context context, TopBookFragment fragment, ArrayList<TopBook> lists){
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
            view = inflater.inflate(R.layout.top_book_item, null);
        }
        final TopBook item =  lists.get(position);
        if (item != null){
            tvBook = view.findViewById(R.id.tv_book_name);
            tvBook.setText("Book Name : " + item.Book);
            tvMount = view.findViewById(R.id.tv_mount);
            tvMount.setText("Mount : " + item.Mount);
        }

        return view;
        // checked course 7 - unit
    }
}

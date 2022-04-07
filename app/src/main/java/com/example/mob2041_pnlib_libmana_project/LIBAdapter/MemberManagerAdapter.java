package com.example.mob2041_pnlib_libmana_project.LIBAdapter;

import android.content.ContentResolver;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mob2041_pnlib_libmana_project.LIBFragment.MemberManagerFragment;
import com.example.mob2041_pnlib_libmana_project.Model.ThanhVien;
import com.example.mob2041_pnlib_libmana_project.R;

import java.util.ArrayList;

public class MemberManagerAdapter extends ArrayAdapter<ThanhVien> {
    private Context context;
    MemberManagerFragment fragment;
    private ArrayList<ThanhVien> lists;
    TextView maTV, tenTV, namSinh;
    ImageView imgDelete;

    public MemberManagerAdapter(Context context, MemberManagerFragment fragment, ArrayList<ThanhVien> lists){
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
            view = inflater.inflate(R.layout.member_item_listview, null);
        }
        final ThanhVien item = lists.get(position);
        if(item != null){
            maTV = view.findViewById(R.id.tv_maTV);
            maTV.setText("ID of Member : " + item.maTV);

            tenTV = view.findViewById(R.id.tv_tenTV);
            tenTV.setText("Name of Member : " + item.hoTen);

            namSinh = view.findViewById(R.id.tv_namSinh);
            namSinh.setText("Year of Birth : " + item.namSinh);

            imgDelete = view.findViewById(R.id.img_del);
        }
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.Delete(String.valueOf(item.maTV));
            }
        });
        return view;
         // checked course 5 - video 5.2.1
    }




}

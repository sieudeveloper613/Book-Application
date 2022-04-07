package com.example.mob2041_pnlib_libmana_project.LIBAdapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mob2041_pnlib_libmana_project.Model.ThanhVien;
import com.example.mob2041_pnlib_libmana_project.R;

import java.util.ArrayList;

public class MemberSpinnerAdapter extends ArrayAdapter<ThanhVien> {
    Context context;
    ArrayList<ThanhVien> lists;
    TextView tvMemberId, tvMemberName;

    public MemberSpinnerAdapter(Context context, ArrayList<ThanhVien> lists){
        super(context, 0, lists);
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view  == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.member_item_spinner, null);
        }
        final ThanhVien item  = lists.get(position);
        if(item != null){
            tvMemberId = view.findViewById(R.id.tv_memberId);
            tvMemberId.setText(item.maTV + "");
            tvMemberName = view.findViewById(R.id.tv_memberName);
            tvMemberName.setText(item.hoTen);

        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.member_item_spinner, null);

        }
        final ThanhVien item = lists.get(position);
        if(item != null){
            tvMemberId = view.findViewById(R.id.tv_memberId);
            tvMemberId.setText(item.maTV + ". ");
            tvMemberName = view.findViewById(R.id.tv_memberName);
            tvMemberName.setText(item.hoTen);
        }
        return view;
        //checked course 6 - video 6.2.1
    }
}

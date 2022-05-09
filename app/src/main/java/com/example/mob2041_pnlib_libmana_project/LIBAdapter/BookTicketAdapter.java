package com.example.mob2041_pnlib_libmana_project.LIBAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mob2041_pnlib_libmana_project.LIBDAO.SachDAO;
import com.example.mob2041_pnlib_libmana_project.LIBDAO.ThanhVienDAO;
import com.example.mob2041_pnlib_libmana_project.LIBFragment.TicketBookManagerFragMent;
import com.example.mob2041_pnlib_libmana_project.Model.PhieuMuon;
import com.example.mob2041_pnlib_libmana_project.Model.Sach;
import com.example.mob2041_pnlib_libmana_project.Model.ThanhVien;
import com.example.mob2041_pnlib_libmana_project.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BookTicketAdapter extends ArrayAdapter<PhieuMuon> {
    Context context;
    TicketBookManagerFragMent fragment;
    ArrayList<PhieuMuon> lists;
    TextView tvBookTicketId, tvMemberName, tvBookName, tvPrice, tvDay, tvBookReturn;
    ImageView imgDel, imgFix;
    SachDAO sachDAO;
    ThanhVienDAO thanhVienDAO;
    ThanhVien thanhVien;
    TicketBookManagerFragMent ticketBookManagerFragMent;
    PhieuMuon item;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public BookTicketAdapter(Context context, TicketBookManagerFragMent fragment, ArrayList<PhieuMuon> lists){
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
            view = inflater.inflate(R.layout.item_ticket, null);
        }
        final PhieuMuon item = lists.get(position);
        if(item != null);
        tvBookTicketId = view.findViewById(R.id.tv_book_ticket_id);
        tvBookTicketId.setText("ID: PNL-TIC0000" + String.valueOf(item.maPM));
        sachDAO = new SachDAO(context);
        Sach sach = sachDAO.getID(String.valueOf(item.maSach));
        tvBookName = view.findViewById(R.id.tv_book_name);
        tvBookName.setText(sach.tenSach);
        thanhVienDAO = new ThanhVienDAO(context);
        ThanhVien thanhVien = thanhVienDAO.getID(String.valueOf(item.maTV));
        tvMemberName = view.findViewById(R.id.tv_member_name);
        tvMemberName.setText(thanhVien.hoTen);
        tvPrice = view.findViewById(R.id.tv_price);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvPrice.setText(decimalFormat.format(item.tienThue));
        tvDay = view.findViewById(R.id.tv_day);
        tvDay.setText(simpleDateFormat.format(item.ngay));
        tvBookReturn = view.findViewById(R.id.tv_book_return);
        if (item.traSach == 1){
            tvBookReturn.setTextColor(Color.BLUE);
            tvBookReturn.setText("Book's returned");
        } else {
            tvBookReturn.setTextColor(Color.RED);
            tvBookReturn.setText("No Return");
        }
        imgDel = view.findViewById(R.id.img_del);
        imgFix = view.findViewById(R.id.img_fix);
        imgFix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<PhieuMuon> list = new ArrayList<>();
            //item = list.get(position);

            }
        });

        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.Delete(String.valueOf(item.maPM));
            }
        });


        return view;
        // check course 6 - last video 6.2.1 and start video 6.2.2
    }


}

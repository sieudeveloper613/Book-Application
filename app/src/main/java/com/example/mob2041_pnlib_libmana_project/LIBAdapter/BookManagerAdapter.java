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

import com.example.mob2041_pnlib_libmana_project.LIBDAO.LoaiSachDAO;
import com.example.mob2041_pnlib_libmana_project.LIBFragment.BookManagerFragment;
import com.example.mob2041_pnlib_libmana_project.Model.LoaiSach;
import com.example.mob2041_pnlib_libmana_project.Model.Sach;
import com.example.mob2041_pnlib_libmana_project.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BookManagerAdapter extends ArrayAdapter<Sach> {
    private Context context;
    BookManagerFragment fragment;
    private ArrayList<Sach> lists;
    ImageView imgDel;
    TextView tvBookId, tvBookName, tvRentalHire, tvCategory;

    public BookManagerAdapter(Context context, BookManagerFragment fragment, ArrayList<Sach> lists){
        super(context, 0, lists);
        this.context = context;
        this.fragment = fragment;
        this.lists = lists;
    }

    @NonNull
    @Override
    public  View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View view = convertView;
       if(view == null){
           LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
           view = inflater.inflate(R.layout.item_book, null );
       }

       final Sach item = lists.get(position);
       if(item != null){
           LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
           LoaiSach loaiSach = LoaiSachDAO.getID(String.valueOf(item.maLoai));
           tvBookId = view.findViewById(R.id.tv_book_id);
           tvBookId.setText(String.valueOf(item.maSach));

           tvBookName = view.findViewById(R.id.tv_name_of_book);
           tvBookName.setText(item.tenSach);

           tvRentalHire = view.findViewById(R.id.tv_giaThue);
           DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
           tvRentalHire.setText("Rental: " + decimalFormat.format(item.giaThue) + "đ");

           tvCategory = view.findViewById(R.id.tv_category_of_book);
           tvCategory.setText(loaiSach.tenLoai);

           imgDel = view.findViewById(R.id.img_delete_book);
       }
       imgDel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               fragment.Delete(String.valueOf(item.maSach));
           }
       });
       return view;

    }


}

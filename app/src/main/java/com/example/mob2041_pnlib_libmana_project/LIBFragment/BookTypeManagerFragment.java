package com.example.mob2041_pnlib_libmana_project.LIBFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mob2041_pnlib_libmana_project.LIBAdapter.BookTypeManagerAdapter;
import com.example.mob2041_pnlib_libmana_project.LIBAdapter.MemberManagerAdapter;
import com.example.mob2041_pnlib_libmana_project.LIBDAO.LoaiSachDAO;
import com.example.mob2041_pnlib_libmana_project.LIBDAO.ThanhVienDAO;
import com.example.mob2041_pnlib_libmana_project.Model.LoaiSach;
import com.example.mob2041_pnlib_libmana_project.Model.ThanhVien;
import com.example.mob2041_pnlib_libmana_project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class BookTypeManagerFragment extends Fragment {

    ListView listView;
    ArrayList<LoaiSach> list;
    FloatingActionButton fab;
    Dialog dialog;
    TextView tvMaLoai;
    EditText edTenLoai;
    Button btnSave, btnCancel;

    static LoaiSachDAO dao;
    BookTypeManagerAdapter adapter;
    LoaiSach item;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booktype_manager_fragment, container, false);
        listView = view.findViewById(R.id.lv_bookTypeManager);
        fab = view.findViewById(R.id.fab_button_book_type);
        dao = new LoaiSachDAO(getActivity());

        updateLv();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(), 0);
            }
        });

        listView.setOnItemLongClickListener((parent, view1, position, id) -> {
            item = list.get(position);
            openDialog(getActivity(), 1);
            return false;
        });
        return view;
    }

    public void openDialog(final Context context, final int type){
        // custom dialog
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.booktype_dialog);
        tvMaLoai = dialog.findViewById(R.id.tv_maLoai);
        edTenLoai = dialog.findViewById(R.id.ed_tenLoaiSach);
        btnSave = dialog.findViewById(R.id.btn_save_book_type);
        btnCancel = dialog.findViewById(R.id.btn_cancel_book_type);

        // kiem tra type insert 0 hay update 1
            //tvMaLoai.setEnabled(true);
        if(type != 0){
//            tvMaLoai.setText(String.valueOf(item.maLoai));
            edTenLoai.setText(item.tenLoai);

        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new LoaiSach();
                item.tenLoai = edTenLoai.getText().toString();
                //item.maLoai = edMaLoai.getText().toString();
                if (validate() > 0) {
                    if (type == 0) {
                        // type = 0 insert
                        if (dao.insert(item) > 0) {
                            Toast.makeText(context, "Success Added", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Fail Added", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        // type = 1 (update)
                        //item.maLoai = Integer.parseInt(edMaLoai.getText().toString());
                        if (dao.update(item) > 0) {
                            Toast.makeText(context, "Success Fixed", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Fail Fixed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                updateLv();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void Delete(final String Id){
        // su dung Alert
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Do you want to delete it?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.delete(Id);
                updateLv();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alert = builder.create();
        builder.show();
    }

    void updateLv(){
        list = (ArrayList<LoaiSach>) dao.getAll();
        adapter = new BookTypeManagerAdapter(getActivity(), this, list);
        listView.setAdapter(adapter);
    }

    public int validate(){
        int check = 1;
        if (edTenLoai.getText().length() == 0){
            Toast.makeText(getContext(), "You must type all INFORMATION", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
    // checked course 5 - video 5.3 -> next course 6
}
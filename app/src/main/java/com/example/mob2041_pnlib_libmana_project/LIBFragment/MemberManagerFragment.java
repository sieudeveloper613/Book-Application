package com.example.mob2041_pnlib_libmana_project.LIBFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.mob2041_pnlib_libmana_project.LIBAdapter.MemberManagerAdapter;
import com.example.mob2041_pnlib_libmana_project.LIBDAO.ThanhVienDAO;
import com.example.mob2041_pnlib_libmana_project.Model.ThanhVien;
import com.example.mob2041_pnlib_libmana_project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;


public class MemberManagerFragment extends Fragment {
    ListView listView;
    ArrayList<ThanhVien> list;
    FloatingActionButton fab;
    Dialog dialog;
    TextView edMaTV;
    EditText edTenTV, edNamSinh;
    Button btnSave, btnCancel;

    static ThanhVienDAO dao;
    MemberManagerAdapter adapter;
    ThanhVien item;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_manager_fragment, container, false);

        listView = view.findViewById(R.id.lv_memberManager);
        fab = view.findViewById(R.id.fab_button_member);
        dao = new ThanhVienDAO(getActivity());

        updateLv();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(), 0);
            }
        });

        listView.setOnItemLongClickListener((parent, view1, position, id) -> {
            item = list.get(position);
            openDialog(getActivity(), 1); //update
            return false;
        });
        return view;
        //checked
    }

    public void openDialog(final Context context, final int type){
        // custom dialog
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.member_dialog);
        edMaTV = dialog.findViewById(R.id.ed_maTV);
        edTenTV = dialog.findViewById(R.id.ed_tenTV);
        edNamSinh = dialog.findViewById(R.id.ed_namSinh);
        btnSave = dialog.findViewById(R.id.btn_save_member);
        btnCancel = dialog.findViewById(R.id.btn_cancel_member);

        // kiem tra type insert 0 hay update 1
        if(type != 0){
            //edMaTV.setText(String.valueOf(item.maTV));
            edTenTV.setText(item.hoTen);
            edNamSinh.setText(item.namSinh);
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
                item = new ThanhVien();
                item.hoTen = edTenTV.getText().toString();
                item.namSinh = edNamSinh.getText().toString();
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
                        //item.maTV = Integer.parseInt(edMaTV.getText().toString());
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
        //checked
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
        //checked
    }

    void updateLv(){
        list = (ArrayList<ThanhVien>) dao.getAll();
        adapter = new MemberManagerAdapter(getActivity(), this, list);
        listView.setAdapter(adapter);
        //checked
    }

    public int validate(){
        int check = 1;
        if (edTenTV.getText().length() == 0 || edNamSinh.getText().length() == 0){
            Toast.makeText(getContext(), "You must type all INFORMATION", Toast.LENGTH_SHORT).show();
            check = -1;
        }
         return check;
        //checked course 5 - video 5.2.3
    }
}
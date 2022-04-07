package com.example.mob2041_pnlib_libmana_project.LIBFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mob2041_pnlib_libmana_project.LIBAdapter.BookManagerAdapter;
import com.example.mob2041_pnlib_libmana_project.LIBAdapter.BookTypeSpinnerAdapter;
import com.example.mob2041_pnlib_libmana_project.LIBDAO.LoaiSachDAO;
import com.example.mob2041_pnlib_libmana_project.LIBDAO.SachDAO;
import com.example.mob2041_pnlib_libmana_project.Model.LoaiSach;
import com.example.mob2041_pnlib_libmana_project.Model.Sach;
import com.example.mob2041_pnlib_libmana_project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class BookManagerFragment extends Fragment {
    ListView listView;
    ArrayList<Sach> list;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edBookName, edPrice; // có thể có edBookID nếu sai;
    TextView edBookID;
    Spinner spinner;
    Button btnSave, btnCancel;
    static SachDAO dao;
    Sach item;
    BookManagerAdapter adapter;
    BookTypeSpinnerAdapter spinnerAdapter;
    ArrayList<LoaiSach> listLoaiSach;
    LoaiSachDAO loaiSachDAO;
    LoaiSach loaiSach;
    int maLoaiSach, position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_manager_fragment, container, false);
        listView = view.findViewById(R.id.lv_bookManager);
        fab = view.findViewById(R.id.fab_button_book);
        dao = new SachDAO(getActivity());

        UpdateLv();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(), 0);
                //Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemLongClickListener((parent, view1, position1, id) -> {
            item = list.get(position);
            openDialog(getActivity(), 1);
            return false;
        });
        return view;
    }

    protected void openDialog(final Context context, final int type) {
        // custom dialog
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.book_dialog);
        edBookID = dialog.findViewById(R.id.tv_maSach);
        edBookName = dialog.findViewById(R.id.ed_tenSach);
        edPrice = dialog.findViewById(R.id.ed_giaThue);
        spinner = dialog.findViewById(R.id.spinner_loaiSach);
        btnSave = dialog.findViewById(R.id.btn_save_book);
        //btnSave.setBackgroundColor(Color.RED);
        btnCancel = dialog.findViewById(R.id.btn_cancel_book);
        //btnCancel.setBackgroundColor(Color.RED);
        listLoaiSach = new ArrayList<LoaiSach>();
        loaiSachDAO = new LoaiSachDAO(context);
        listLoaiSach = (ArrayList<LoaiSach>) loaiSachDAO.getAll();
        spinnerAdapter = new BookTypeSpinnerAdapter(context, listLoaiSach);
        spinner.setAdapter(spinnerAdapter);

        // get book id
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoaiSach = listLoaiSach.get(position).maLoai;
                Toast.makeText(context, "Choose : " + listLoaiSach.get(position).tenLoai, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // check type insert 0 or 1
        //edBookID.setEnabled(false);
        if (type != 0) {
            //edBookID.setText(String.valueOf(item.maSach));
            edBookName.setText(item.tenSach);
            edPrice.setText(String.valueOf(item.giaThue));
            for (int i = 0; i < listLoaiSach.size(); i++) {
                if (item.maLoai == (listLoaiSach.get(i).maLoai)) {
                    position = i;
                }
                Log.i("demo", "posSach" + position);
                spinner.setSelection(position);
            }
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
                item = new Sach();
                item.tenSach = edBookName.getText().toString();
                item.giaThue = Integer.parseInt(edPrice.getText().toString());
                item.maLoai = maLoaiSach;
                if (validate() > 0) {
                    if (type == 0) {
                        // type == 0 (insert)
                        if (dao.insert(item) > 0) {
                            Toast.makeText(context, "Success Added", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Fail Added", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // type == 1
                        item.maSach = Integer.parseInt(edBookID.getText().toString());
                        if (dao.update(item) > 0) {
                            Toast.makeText(context, "Success Fixed", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Fail Fixed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    UpdateLv();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public void Delete(final String Id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Do you want to delete it ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.delete(Id);
                UpdateLv();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert  = builder.create();
        builder.show();
    }

    void UpdateLv(){
    list = (ArrayList<Sach>) dao.getAll();
    adapter = new BookManagerAdapter(getActivity(), this, list);
    listView.setAdapter(adapter);
    }

    public int validate(){
        int check = 1;
        if (edBookName.getText().length() == 0 ||  edPrice.getText().length() == 0){
            Toast.makeText(getContext(), "You must type all of Information", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
        //checked course 6 - video 6.1.2
    }
}

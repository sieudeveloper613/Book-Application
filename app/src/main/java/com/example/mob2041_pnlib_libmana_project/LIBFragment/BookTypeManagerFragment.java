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
import com.example.mob2041_pnlib_libmana_project.LIBDAO.LoaiSachDAO;
import com.example.mob2041_pnlib_libmana_project.Model.LoaiSach;
import com.example.mob2041_pnlib_libmana_project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class BookTypeManagerFragment extends Fragment implements View.OnClickListener{
    // View and ViewGroup
    ListView listView;
    FloatingActionButton fab;
    Dialog dialog;
    TextView tvCategoryId;
    EditText edCategoryName;
    Button btnCreate, btnCancel;

    // Object and References
    ArrayList<LoaiSach> list;
    static LoaiSachDAO dao;
    BookTypeManagerAdapter adapter;
    LoaiSach item;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booktype_manager_fragment, container, false);

        // Define id for view
        initView(view);


        // Define event for view
        initControl();


        // Define method
        dao = new LoaiSachDAO(getActivity());
        updateListview();

        listView.setOnItemLongClickListener((parent, view1, position, id) -> {
            item = list.get(position);
            openDialog(getActivity(), 1);
            return false;
        });


        return view;
    }

    private void initView(View view) {
        listView = view.findViewById(R.id.lv_bookTypeManager);
        fab = view.findViewById(R.id.fab_create_new_category);
    }

    private void initControl(){
        fab.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_create_new_category:
                openDialog(getActivity(), 0);
                break;
        }
    }

    public void openDialog(final Context context, final int type){
        // custom dialog
        dialog = new Dialog(context, R.style.MyAlertDialogTheme);
        dialog.setContentView(R.layout.booktype_dialog);
        edCategoryName = dialog.findViewById(R.id.ed_category_name);
        btnCreate = dialog.findViewById(R.id.btn_create_category);
        btnCancel = dialog.findViewById(R.id.btn_cancel_category);

        // Check Type == 0 : insert or Type == 1 : update
        if(type != 0){
            tvCategoryId.setText(String.valueOf(item.maLoai));
            edCategoryName.setText(item.tenLoai);

        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new LoaiSach();
                item.tenLoai = edCategoryName.getText().toString();
                if (validate() > 0) {
                    // Type == 0 : insert
                    if (type == 0) {
                        // type = 0 insert
                        if (dao.insert(item) > 0) {
                            Toast.makeText(context, "Create new category successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Create new category failed!", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        // Type == 1 : update
                        item.maLoai = Integer.parseInt(tvCategoryId.getText().toString());
                        if (dao.update(item) > 0) {
                            Toast.makeText(context, "Update category successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Update category failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                updateListview();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void Delete(final String Id){
        // using Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete Category");
        builder.setMessage("Do you want to delete it?");
        builder.setCancelable(true);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.delete(Id);
                updateListview();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.cancel();
        });
        builder.create();
        builder.show();
    }

    void updateListview(){
        list = (ArrayList<LoaiSach>) dao.getAll();
        adapter = new BookTypeManagerAdapter(getActivity(), this, list);
        listView.setAdapter(adapter);
    }

    public int validate(){
        int check = 1;
        if (edCategoryName.getText().length() == 0){
            Toast.makeText(getContext(), "Name of Category must not empty!", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

}
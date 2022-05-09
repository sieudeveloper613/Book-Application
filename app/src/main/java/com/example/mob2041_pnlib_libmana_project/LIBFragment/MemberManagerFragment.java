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

import com.example.mob2041_pnlib_libmana_project.LIBAdapter.MemberManagerAdapter;
import com.example.mob2041_pnlib_libmana_project.LIBDAO.ThanhVienDAO;
import com.example.mob2041_pnlib_libmana_project.Model.ThanhVien;
import com.example.mob2041_pnlib_libmana_project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MemberManagerFragment extends Fragment implements View.OnClickListener{
    // View and ViewGroup
    ListView listView;
    Dialog dialog;
    TextView tvClientId;
    EditText edClientName, edClientDob;
    Button btnCreate, btnCancel;
    FloatingActionButton fab;

    // Object and References
    ArrayList<ThanhVien> list;
    ThanhVienDAO dao;
    MemberManagerAdapter adapter;
    ThanhVien item;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_manager_fragment, container, false);
        // Define id for view
        initView(view);


        // Define event for view
        initControl();


        // Define method
        dao = new ThanhVienDAO(getActivity());
        updateListview();

        listView.setOnItemLongClickListener((parent, view1, position, id) -> {
            item = list.get(position);
            openDialog(getActivity(), 1); //update
            return false;
        });

        return view;
    }


    private void initView(View view) {
        listView = view.findViewById(R.id.lv_client);
        fab = view.findViewById(R.id.fab_create_new_client);
    }

    private void initControl() {
        fab.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_create_new_client:
                openDialog(getActivity(), 0);
                break;
        }
    }

    public void openDialog(final Context context, final int type){
        // create custom dialog
        dialog = new Dialog(context, R.style.MyAlertDialogTheme);
        dialog.setContentView(R.layout.create_new_client_dialog);
        tvClientId = dialog.findViewById(R.id.tv_client_id);
        edClientName = dialog.findViewById(R.id.ed_client_name);
        edClientDob = dialog.findViewById(R.id.ed_client_date_of_birth);
        btnCreate = dialog.findViewById(R.id.btn_create_client);
        btnCancel = dialog.findViewById(R.id.btn_cancel_client);

        // check type == 0 : insert or type == 1 : update
        if(type != 0){
            tvClientId.setText(String.valueOf(item.maTV));
            edClientName.setText(item.hoTen);
            edClientDob.setText(item.namSinh);
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
                item = new ThanhVien();
                item.hoTen = edClientName.getText().toString();
                item.namSinh = edClientDob.getText().toString();
                if (validate() > 0) {
                    if (type == 0) {
                        // type == 0 : insert
                        if (dao.insert(item) > 0) {
                            Toast.makeText(context, "Create new client successful", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(context, "Create new client failed!", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        // type == 1 : update
                        item.maTV = Integer.parseInt(tvClientId.getText().toString());
                        if (dao.update(item) > 0) {
                            Toast.makeText(context, "Update client successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Update client failed!", Toast.LENGTH_SHORT).show();
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
        builder.setTitle("Delete client");
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

    private void updateListview(){
        list = (ArrayList<ThanhVien>) dao.getAll();
        adapter = new MemberManagerAdapter(getActivity(), this, list);
        listView.setAdapter(adapter);
        //checked
    }

    // validate client form
    public int validate(){
        int check = 1;
        if (edClientName.getText().length() == 0 || edClientDob.getText().length() == 0){
            Toast.makeText(getContext(), "Information of client must not empty!", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }


}
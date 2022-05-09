package com.example.mob2041_pnlib_libmana_project.LIBFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mob2041_pnlib_libmana_project.FormatPrice;
import com.example.mob2041_pnlib_libmana_project.LIBAdapter.BookManagerAdapter;
import com.example.mob2041_pnlib_libmana_project.LIBAdapter.BookTypeSpinnerAdapter;
import com.example.mob2041_pnlib_libmana_project.LIBDAO.LoaiSachDAO;
import com.example.mob2041_pnlib_libmana_project.LIBDAO.SachDAO;
import com.example.mob2041_pnlib_libmana_project.Model.LoaiSach;
import com.example.mob2041_pnlib_libmana_project.Model.Sach;
import com.example.mob2041_pnlib_libmana_project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class BookManagerFragment extends Fragment implements View.OnClickListener{
    // View and ViewGroup
    ListView listView;
    EditText edBookName, edRentalPrice; // có thể có edBookID nếu sai;
    TextView edBookID;
    Spinner spinner;
    Button btnCreate, btnCancel;
    Dialog dialog;
    FloatingActionButton fabCreateBook;


    // Object and References
    ArrayList<Sach> list;
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

        // Define id for view
        initView(view);

        // Define event for view
        initControl();

        // Define method
        dao = new SachDAO(getActivity());
        updateListView();



        listView.setOnItemLongClickListener((parent, view1, position1, id) -> {
            item = list.get(position);
            openDialog(getActivity(), 1);
            return false;
        });
        return view;
    }



    private void initView(View view){
        listView = view.findViewById(R.id.lv_bookManager);
        fabCreateBook = view.findViewById(R.id.fab_create_new_book);
    }

    private void initControl() {
        fabCreateBook.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_create_new_book:
                openDialog(getActivity(), 0);
                break;
        }
    }

    protected void openDialog(final Context context, final int type) {
        // custom dialog
        dialog = new Dialog(context, R.style.MyAlertDialogTheme);
        dialog.setContentView(R.layout.book_dialog);
        View v = dialog.getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);
        edBookName = dialog.findViewById(R.id.ed_name_of_book);
        edRentalPrice = dialog.findViewById(R.id.ed_rental_price);
        spinner = dialog.findViewById(R.id.spinner_type_of_book);
        btnCreate = dialog.findViewById(R.id.btn_create_new_book);
        btnCancel = dialog.findViewById(R.id.btn_cancel_book);
        listLoaiSach = new ArrayList<>();
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
            edBookID.setText(String.valueOf(item.maSach));
            edBookName.setText(item.tenSach);
            edRentalPrice.setText(new FormatPrice().formatNumber(item.giaThue));
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

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
                item = new Sach();
                item.tenSach = edBookName.getText().toString();
                item.giaThue = Integer.parseInt(edRentalPrice.getText().toString());
                item.maLoai = maLoaiSach;
                if (validate() > 0) {
                    if (type == 0) {
                        // type == 0 (insert)
                        if (dao.insert(item) > 0) {
                            Toast.makeText(context, "Create new book successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Create new book failed!", Toast.LENGTH_SHORT).show();
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
                    updateListView();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public void Delete(final String Id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete book");
        builder.setMessage("Do you want to delete this book ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.delete(Id);
                updateListView();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create();
        builder.show();
    }

    // reset listview if it is updated
    void updateListView(){
    list = (ArrayList<Sach>) dao.getAll();
    adapter = new BookManagerAdapter(getActivity(), this, list);
    listView.setAdapter(adapter);
    }

    // validate input form
    public int validate(){
        int check = 1;
        if (edBookName.getText().length() == 0 ||  edRentalPrice.getText().length() == 0){
            Toast.makeText(getContext(), "Information of Book must not empty", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }


}

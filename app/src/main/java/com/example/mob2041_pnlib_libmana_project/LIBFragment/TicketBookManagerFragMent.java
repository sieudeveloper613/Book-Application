package com.example.mob2041_pnlib_libmana_project.LIBFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mob2041_pnlib_libmana_project.LIBAdapter.BookSpinnerAdapter;
import com.example.mob2041_pnlib_libmana_project.LIBAdapter.BookTicketAdapter;
import com.example.mob2041_pnlib_libmana_project.LIBAdapter.MemberSpinnerAdapter;
import com.example.mob2041_pnlib_libmana_project.LIBDAO.PhieuMuonDAO;
import com.example.mob2041_pnlib_libmana_project.LIBDAO.SachDAO;
import com.example.mob2041_pnlib_libmana_project.LIBDAO.ThanhVienDAO;
import com.example.mob2041_pnlib_libmana_project.Model.PhieuMuon;
import com.example.mob2041_pnlib_libmana_project.Model.Sach;
import com.example.mob2041_pnlib_libmana_project.Model.ThanhVien;
import com.example.mob2041_pnlib_libmana_project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static java.time.LocalDate.now;
import static java.time.LocalDate.of;


public class TicketBookManagerFragMent extends Fragment {
    ListView listView;
    ArrayList<PhieuMuon> list;
    FloatingActionButton fab;
    Dialog dialog;
    TextView edBookTicketId;
    Spinner spinnerMember, spinnerBook;
    TextView tvDay, tvPrice;
    CheckBox checkboxBookReturn;
    Button btnSave, btnCancel;
    static PhieuMuonDAO dao;
    BookTicketAdapter adapter;
    PhieuMuon item;
    MemberSpinnerAdapter memberSpinnerAdapter;
    ArrayList<ThanhVien> listThanhVien;
    ThanhVienDAO thanhVienDAO;
    ThanhVien thanhVien;
    int memberId;
    BookSpinnerAdapter bookSpinnerAdapter;
    ArrayList<Sach> listSach;
    SachDAO sachDAO;
    Sach sach;
    int BookId, Price;
    int positionMember, positionBook;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bookticket_manager_fragment, container, false);
        listView = view.findViewById(R.id.lv_book_ticket_Manager);
        fab = view.findViewById(R.id.fab_button_ticket);
        dao = new PhieuMuonDAO(getActivity());
        UpdateLv();

        fab.setOnClickListener(v -> {
            openDialog(getActivity(), 0);
            Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
        });

        listView.setOnItemLongClickListener((parent, view1, position, id) -> {
            item = list.get(position);
            openDialog(getActivity(), 1);
            return false;
        });
        return view;
    }

    protected void openDialog(final Context context, final int type) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.book_ticket_dialog);
        edBookTicketId = dialog.findViewById(R.id.ed_book_ticket_id);
        spinnerBook = dialog.findViewById(R.id.spinner_book_id);
        spinnerMember = dialog.findViewById(R.id.spinner_member_id);
        tvDay = dialog.findViewById(R.id.tv_day);
        tvPrice = dialog.findViewById(R.id.tv_price);
        checkboxBookReturn = dialog.findViewById(R.id.chk_book_return);
        btnCancel = dialog.findViewById(R.id.btn_cancel_ticket);
        btnSave = dialog.findViewById(R.id.btn_save_ticket);
        thanhVienDAO = new ThanhVienDAO(context);
        listThanhVien = new ArrayList<ThanhVien>();
        listThanhVien = (ArrayList<ThanhVien>) thanhVienDAO.getAll();
        memberSpinnerAdapter = new MemberSpinnerAdapter(context, listThanhVien);
        spinnerMember.setAdapter(memberSpinnerAdapter);

        // get book type id
        spinnerMember.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                memberId = listThanhVien.get(position).maTV;
                Toast.makeText(context, "Choose : " + listThanhVien.get(position).hoTen, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        sachDAO = new SachDAO(context);
        listSach = new ArrayList<Sach>();
        listSach = (ArrayList<Sach>) sachDAO.getAll();
        bookSpinnerAdapter = new BookSpinnerAdapter(context, listSach);
        spinnerBook.setAdapter(bookSpinnerAdapter);

        //get book id
        spinnerBook.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BookId = listSach.get(position).maSach;
                Price = listSach.get(position).giaThue;
                Toast.makeText(context, "Choose : " + listSach.get(position).tenSach, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        //edBookTicketId.setEnabled(false);
        if (type != 0) {
            //edBookTicketId.setText(String.valueOf(item.maPM));
            for (int i = 0; i < listThanhVien.size(); i++) {
                if (item.maTV == (listThanhVien.get(i).maTV)) {
                    positionMember = i;
                }
                spinnerMember.setSelection(positionMember);

                for (int j = 0; j < listSach.size(); j++) {
                    if (item.maSach == (listSach.get(j).maSach)) {
                        positionBook = j;
                    }
                    spinnerBook.setSelection(positionBook);

                    tvDay.setText("Hired Day : " + simpleDateFormat.format(item.ngay));
                    tvPrice.setText("Price : " + item.tienThue);
                    if (item.traSach == 1) {
                        checkboxBookReturn.setChecked(true);
                    } else {
                        checkboxBookReturn.setChecked(false);
                    }

                }
            }
            }

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(context, "cancel", Toast.LENGTH_SHORT).show();
                    }
                });


                btnSave.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
                        item = new PhieuMuon();
                        item.maSach = BookId;
                        item.maTV = memberId;
                        //item.ngay = java.util.Date.valueOf(String.valueOf(NewDate));
                        item.ngay = java.sql.Date.valueOf(String.valueOf(now()));
                        item.tienThue = Price;
                        if (checkboxBookReturn.isChecked()) {
                            item.traSach = 1;
                        } else {
                            item.traSach = 0;
                        }

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
                                //item.maPM = Integer.parseInt(edBookTicketId.getText().toString());
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
        //checked course 6 - video 6.2.2
            }




    public void Delete(final String Id){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Delete");
            builder.setMessage("Do you want to delete ?");
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
            AlertDialog alertDialog = builder.create();
            builder.show();

    }

    void UpdateLv(){
    list =  (ArrayList<PhieuMuon>) dao.getAll();
    adapter = new BookTicketAdapter(getActivity(),this, list);
    listView.setAdapter(adapter);
    }

    public int validate(){
        int check  = 1;
        return check;
    }
    //checked course 6 - video 6.2.3
}
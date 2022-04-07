package com.example.mob2041_pnlib_libmana_project.LIBFragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mob2041_pnlib_libmana_project.LIBDAO.ThuThuDAO;
import com.example.mob2041_pnlib_libmana_project.Model.ThuThu;
import com.example.mob2041_pnlib_libmana_project.R;
import com.google.android.material.textfield.TextInputEditText;

import static android.content.Context.MODE_PRIVATE;

public class CreateUserFragment extends Fragment {
    EditText edUserName, edPassWord, edReType, edFullName;
    Button btnChange, btnCancel;
    ThuThuDAO dao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.create_user_fragment, container, false);
        edUserName = view.findViewById(R.id.ed_create_user_name);
        edFullName = view.findViewById(R.id.ed_create_full_name);
        edPassWord = view.findViewById(R.id.ed_create_password);
        edReType = view.findViewById(R.id.ed_retype_password);
        btnChange = view.findViewById(R.id.btn_change);
        btnChange.setBackgroundColor(Color.RED);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnCancel.setBackgroundColor(Color.RED);

        dao = new ThuThuDAO(getActivity());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edUserName.setText("");
                edFullName.setText("");
                edPassWord.setText("");
                edReType.setText("");
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThuThu thuThu = new ThuThu();
                thuThu.maTT = edUserName.getText().toString();
                thuThu.hoTen = edFullName.getText().toString();
                thuThu.matKhau = edPassWord.getText().toString();

                if (validate() > 0){
                    if (dao.insert(thuThu) > 0){
                        Toast.makeText(getActivity(), "Create User Success", Toast.LENGTH_SHORT).show();
                        edUserName.setText("");
                        edFullName.setText("");
                        edPassWord.setText("");
                        edReType.setText("");
                    } else {
                        Toast.makeText(getActivity(), "Create User Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        return view;
    }

    public int validate(){
        int check = 1;
        if (edUserName.getText().length() == 0 || edPassWord.getText().length() == 0 || edFullName.getText().length() == 0 || edReType.getText().length() == 0){
            Toast.makeText(getContext(), "You have to type all INFORMATION", Toast.LENGTH_SHORT).show();
            check = -1;
        } else{
            String pass = edPassWord.getText().toString();
            String repass = edReType.getText().toString();
                if (!pass.equals(repass)){
                    Toast.makeText(getContext(), "The PassWord is not Same One", Toast.LENGTH_SHORT).show();
                }
        }
        return check;
    }


}
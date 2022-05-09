package com.example.mob2041_pnlib_libmana_project.LIBFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mob2041_pnlib_libmana_project.LIBDAO.ThuThuDAO;
import com.example.mob2041_pnlib_libmana_project.Model.ThuThu;
import com.example.mob2041_pnlib_libmana_project.R;

public class CreateUserFragment extends Fragment implements View.OnClickListener{
    // View and ViewGroup
    EditText edNewAccount, edNewName, edNewPassword, edEnterNewPassword;
    Button btnCreate, btnDelete;
    LinearLayout layout;

    // Object and References
    ThuThuDAO dao;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_user_fragment, container, false);

        // Define id for view
        initView(view);

        // Define method
        dao = new ThuThuDAO(getActivity());
        btnDelete.setOnClickListener(this::onClick);
        btnCreate.setOnClickListener(this::onClick);

        return view;
    }




    private void initView(View view) {
        edNewAccount = view.findViewById(R.id.ed_new_user);
        edNewName = view.findViewById(R.id.ed_new_name);
        edNewPassword = view.findViewById(R.id.ed_new_password);
        edEnterNewPassword = view.findViewById(R.id.ed_enter_new_password);
        btnCreate = view.findViewById(R.id.btn_create_new_user);
        btnDelete = view.findViewById(R.id.btn_delete_all_information);
        layout = view.findViewById(R.id.linear_container);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_delete_all_information:
                deleteAll();
                break;

            case R.id.btn_create_new_user:
                createNewUser();
                break;
        }
    }


    private void deleteAll(){
        edNewAccount.setText("");
        edNewName.setText("");
        edNewPassword.setText("");
        edEnterNewPassword.setText("");
    }


    private void createNewUser(){
        ThuThu thuThu = new ThuThu();
        thuThu.maTT = edNewAccount.getText().toString();
        thuThu.hoTen = edNewName.getText().toString();
        thuThu.matKhau = edNewPassword.getText().toString();

        if (validate() > 0){
            if (dao.insert(thuThu) > 0){
                Toast.makeText(getActivity(), "Create new user successful", Toast.LENGTH_SHORT).show();
                edNewAccount.setText("");
                edNewName.setText("");
                edNewPassword.setText("");
                edEnterNewPassword.setText("");
            } else {
                Toast.makeText(getActivity(), "Create new user Failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public int validate(){
        int check = 1;
        if (edNewAccount.getText().length() == 0 || edNewName.getText().length() == 0 || edNewPassword.getText().length() == 0 || edEnterNewPassword.getText().length() == 0){
            Toast.makeText(getActivity(), "Information must not empty", Toast.LENGTH_SHORT).show();
            check = -1;
        } else{
            String password = edNewPassword.getText().toString();
            String rePassword = edEnterNewPassword.getText().toString();
            if (!password.equals(rePassword)){
                Toast.makeText(getActivity(), "Password are not same!", Toast.LENGTH_SHORT).show();
            }
        }
        return check;
    }

}
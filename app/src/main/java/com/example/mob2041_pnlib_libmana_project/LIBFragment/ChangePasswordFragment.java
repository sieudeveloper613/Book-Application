package com.example.mob2041_pnlib_libmana_project.LIBFragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
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

public class ChangePasswordFragment extends Fragment implements View.OnClickListener{
    // View and ViewGroup
    EditText edOldPassword, edNewPassword, edEnterNewPassword;
    Button btnSubmit, btnCancel;

    // Object and References
    ThuThuDAO dao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_password_fragment, container, false);
        // Define id for view
        initView(view);

        // Define event for view
        initControl();

        // Define method
        dao = new ThuThuDAO(getActivity());

        return view;
    }

    private void initView(View view){
        edOldPassword = view.findViewById(R.id.ed_old_password);
        edNewPassword = view.findViewById(R.id.ed_change_new_password);
        edEnterNewPassword = view.findViewById(R.id.ed_enter_change_new_password);
        btnSubmit = view.findViewById(R.id.btn_confirm_change_password);
        btnCancel = view.findViewById(R.id.btn_cancel_change_password);

    }

    private void initControl(){
        btnSubmit.setOnClickListener(this::onClick);
        btnCancel.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm_change_password:
                performSubmit();
                break;

            case R.id.btn_cancel_change_password:
                performCancel();
                break;
        }
    }

    private void performSubmit(){
        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = pref.getString("USERNAME", "");
        if(validate() > 0){
            ThuThu thuThu = dao.getID(user);
            thuThu.matKhau = edNewPassword.getText().toString();

            if (dao.update(thuThu) > 0 ){
                Toast.makeText(getActivity(), "Password is changed successful", Toast.LENGTH_SHORT).show();
                edOldPassword.setText("");
                edNewPassword.setText("");
                edEnterNewPassword.setText("");
            } else {
                Toast.makeText(getActivity(), "Password is changed failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void performCancel(){
        edOldPassword.setText("");
        edNewPassword.setText("");
        edEnterNewPassword.setText("");
    }

    public int validate() {
        int check = 1;
        if (edOldPassword.getText().length() == 0 || edNewPassword.getText().length() == 0 || edEnterNewPassword.getText().length() == 0) {
            Toast.makeText(getActivity(), "The Information of password must not empty!", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            // read user, pass in SharedPreferences
            SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
            String oldPassWord = pref.getString("PASSWORD", "");
            String newPassWord = edNewPassword.getText().toString();
            String rePassword = edEnterNewPassword.getText().toString();
            if (!oldPassWord.equals(edOldPassword.getText().toString())) {
                Toast.makeText(getActivity(), "Recent password went wrong!", Toast.LENGTH_SHORT).show();
                check = -1;

            }
            if (!newPassWord.equals(rePassword)) {
                Toast.makeText(getActivity(), "Password are not same!", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }


}
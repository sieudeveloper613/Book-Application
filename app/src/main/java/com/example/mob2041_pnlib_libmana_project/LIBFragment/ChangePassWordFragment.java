package com.example.mob2041_pnlib_libmana_project.LIBFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mob2041_pnlib_libmana_project.LIBDAO.ThuThuDAO;
import com.example.mob2041_pnlib_libmana_project.Model.ThuThu;
import com.example.mob2041_pnlib_libmana_project.R;
import com.google.android.material.textfield.TextInputEditText;

import static android.content.Context.MODE_PRIVATE;

public class ChangePassWordFragment extends Fragment {
    TextInputEditText edOldPassWord, edNewPassWord, edRetype;
    Button BtnChange, BtnCancel;
    ThuThuDAO dao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_change_pass_word_fragment, container, false);

            edOldPassWord = view.findViewById(R.id.ed_oldPassWord);
            edNewPassWord = view.findViewById(R.id.ed_newPassWord);
            edRetype = view.findViewById(R.id.ed_reTypePassWord);
            BtnChange = view.findViewById(R.id.btn_change);
            BtnChange.setBackgroundColor(Color.RED);
            BtnCancel = view.findViewById(R.id.btn_cancel);
            BtnCancel.setBackgroundColor(Color.RED);

            dao = new ThuThuDAO(getActivity());

            BtnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edOldPassWord.setText("");
                    edNewPassWord.setText("");
                    edRetype.setText("");
                }
            });

            BtnChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // read userName and password in SharedPreferences
                    SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
                    String user = pref.getString("USERNAME", "");
                    if(validate() > 0){
                        ThuThu thuThu = dao.getID(user);
                        thuThu.matKhau = edNewPassWord.getText().toString();

                        if (dao.update(thuThu) > 0 ){
                            Toast.makeText(getActivity(), "The password is changed success", Toast.LENGTH_SHORT).show();
                            edOldPassWord.setText("");
                            edNewPassWord.setText("");
                            edRetype.setText("");
                        } else {
                            Toast.makeText(getActivity(), "PassWord is Changed FAILED", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });
        return view;
    }

    public int validate() {
        int check = 1;
        if (edOldPassWord.getText().length() == 0 || edNewPassWord.getText().length() == 0 || edRetype.getText().length() == 0) {
            Toast.makeText(getActivity(), "The Information must not be EMPTY", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            // read user, pass in SharedPreferences
            SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
            String oldPassWord = pref.getString("PASSWORD", "");
            String newPassWord = edNewPassWord.getText().toString();
            String reType = edRetype.getText().toString();
            if (!oldPassWord.equals(edOldPassWord.getText().toString())) {
                Toast.makeText(getActivity(), "The old pass word is Wrong", Toast.LENGTH_SHORT).show();
                check = -1;

            }
            if (!newPassWord.equals(reType)) {
                Toast.makeText(getActivity(), "the Pass Word is not same", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;

        // checked course 4 - video 4.2.2
    }
}
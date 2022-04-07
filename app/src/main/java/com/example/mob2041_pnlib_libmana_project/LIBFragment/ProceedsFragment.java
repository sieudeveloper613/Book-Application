package com.example.mob2041_pnlib_libmana_project.LIBFragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mob2041_pnlib_libmana_project.LIBDAO.ThongKeDAO;
import com.example.mob2041_pnlib_libmana_project.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class ProceedsFragment extends Fragment {
    Button btnDayFrom, btnDayTo, btnGetProceeds;
    EditText edFrom, edTo;
    TextView tvResultOfProceeds;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    int mYear, mMonth, mDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.proceeds_fragment, container, false);

        btnDayFrom = view.findViewById(R.id.btn_day_from);
        btnDayFrom.setBackgroundColor(Color.RED);
        btnDayTo = view.findViewById(R.id.btn_day_to);
        btnDayTo.setBackgroundColor(Color.RED);
        btnGetProceeds = view.findViewById(R.id.btn_get_proceeds);
        btnGetProceeds.setBackgroundColor(Color.RED);
        tvResultOfProceeds = view.findViewById(R.id.tv_result_proceeds);
        edFrom = view.findViewById(R.id.ed_day_from);
        edFrom.setTextColor(Color.BLACK);
        edTo = view.findViewById(R.id.ed_day_to);
        edTo.setTextColor(Color.BLACK);


        DatePickerDialog.OnDateSetListener DayFrom = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;
                GregorianCalendar c =  new GregorianCalendar(mYear, mMonth, mDay);
                edFrom.setText(simpleDateFormat.format(c.getTime()));
            }
        };

        DatePickerDialog.OnDateSetListener DayTo = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;
                GregorianCalendar c =  new GregorianCalendar(mYear, mMonth, mDay);
                edTo.setText(simpleDateFormat.format(c.getTime()));
            }
        };

        btnDayFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(), 0,
                            DayFrom, mYear, mMonth, mDay);
                d.show();
            }
        });

        btnDayTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(), 0,
                        DayTo, mYear, mMonth, mDay);
                d.show();
            }
        });

        btnGetProceeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String DayFrom = edFrom.getText().toString();
                String DayTo = edTo.getText().toString();
                ThongKeDAO thongKeDAO = new ThongKeDAO(getActivity());
                tvResultOfProceeds.setText("Proceeds : " + thongKeDAO.getDoanhThu(DayFrom,DayTo) + " VNĐ");
            }
        });


        return view;

        // checked course 7 - video 7.3 unit
    }
}
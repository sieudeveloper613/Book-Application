package com.example.mob2041_pnlib_libmana_project.LIBFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.mob2041_pnlib_libmana_project.LIBAdapter.TopBookAdapter;
import com.example.mob2041_pnlib_libmana_project.LIBDAO.ThongKeDAO;
import com.example.mob2041_pnlib_libmana_project.Model.TopBook;
import com.example.mob2041_pnlib_libmana_project.R;

import java.util.ArrayList;


public class TopBookFragment extends Fragment {
    ListView listView;
    ArrayList<TopBook> list;
    TopBookAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_book_fragment, container, false);
        listView = view.findViewById(R.id.lv_top_book);
        ThongKeDAO thongKeDAO = new ThongKeDAO(getActivity());
        list = (ArrayList<TopBook>) thongKeDAO.getTop();
        adapter = new TopBookAdapter(getActivity(), this, list);
        listView.setAdapter(adapter);

        return view;
        // checked course 7 - unit
    }
}
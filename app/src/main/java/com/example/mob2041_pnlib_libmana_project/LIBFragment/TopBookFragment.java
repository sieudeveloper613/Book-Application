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
    // View and ViewGroup
    ListView listView;

    // Object and References
    ArrayList<TopBook> list;
    TopBookAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_book_fragment, container, false);

        // Define id for view
        initView(view);

        // Define event for view
        initControl();

        // Define method
        ThongKeDAO thongKeDAO = new ThongKeDAO(getActivity());
        list = (ArrayList<TopBook>) thongKeDAO.getTop();
        adapter = new TopBookAdapter(getActivity(), this, list);
        listView.setAdapter(adapter);

        return view;
    }

    private void initView(View view) {
        listView = view.findViewById(R.id.lv_top_book);
    }

    private void initControl(){
        // write something here!
    }


}
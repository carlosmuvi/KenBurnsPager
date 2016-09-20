package com.minube.kenburnspager.sample;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.minube.kenburnspager.sample.adapter.SimpleRecyclerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlosmuvi on 19/09/16.
 */

public class SampleFragment extends Fragment {

    int items;
    SimpleRecyclerAdapter adapter;

    public SampleFragment() {
    }

    @SuppressLint("ValidFragment") public SampleFragment(int items) {
        this.items = items;
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample, container, false);

        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.dummyfrag_bg);
        frameLayout.setBackgroundColor(Color.LTGRAY);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dummyfrag_scrollableview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < items; i++) {
            list.add("hola");
        }

        adapter = new SimpleRecyclerAdapter(list);
        recyclerView.setAdapter(adapter);

        return view;
    }
}

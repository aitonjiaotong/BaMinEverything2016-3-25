package com.example.zjb.bamin.Cdachezuche.DaCheZuCheFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zjb.bamin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment implements View.OnClickListener {


    private View mInflate;

    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_more2, null);
            findID();
            initUI();
            setListener();
        }
        return mInflate;
    }

    private void setListener() {
        mInflate.findViewById(R.id.rela_usedAddress).setOnClickListener(this);
    }

    private void initUI() {

    }

    private void findID() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rela_usedAddress:

                break;
        }
    }
}

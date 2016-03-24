package com.example.zjb.bamin.Cdachezuche.DaCheZuCheFragment.OrderViewPagerFagment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.zjb.bamin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaiJiaOrderFragment extends Fragment {


    private View mInflate;
    private ListView listView_daijia;

    public DaiJiaOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate==null){
            mInflate = inflater.inflate(R.layout.fragment_dai_jia, null);
            findID();
            initUI();
        }
        return mInflate;
    }
    private void initUI() {
        View headANDfoot = getActivity().getLayoutInflater().inflate(R.layout.listhead_foot_null, null);
        listView_daijia.addHeaderView(headANDfoot);
        listView_daijia.addFooterView(headANDfoot);
        listView_daijia.setAdapter(new MyAdapter());
    }

    private void findID() {
        listView_daijia = (ListView) mInflate.findViewById(R.id.listView_daijia);
    }
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 8;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = getLayoutInflater(getArguments()).inflate(R.layout.dache_order_listitem, null);
            return inflate;
        }
    }
}

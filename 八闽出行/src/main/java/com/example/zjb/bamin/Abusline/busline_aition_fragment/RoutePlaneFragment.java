package com.example.zjb.bamin.Abusline.busline_aition_fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zjb.bamin.R;
import com.example.zjb.bamin.Abusline.busline_aition_constants.MyConstants;
import com.example.zjb.bamin.Abusline.busline_aiton.InPutLocAllActivity;
import com.example.zjb.bamin.Abusline.busline_aiton.InputLocActivity;
import com.example.zjb.bamin.Abusline.busline_aiton.TransitRouteActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoutePlaneFragment extends Fragment implements View.OnClickListener
{
    private View mInflate;
    private String[] loc01 = {"我的位置", "我的位置"};
    private String[] loc02 = {"梅阳花园", "干休二所"};
    private RelativeLayout mMylocation_rela;
    private RelativeLayout mInput_end_rela;
    private RelativeLayout mTransAdress;
    private TextView mTv_curr_loction;
    private TextView mTv_ending_station;
    private ImageView mIv_curr_loction;
    private ImageView mIv_ending_station;
    private boolean isTrans = false;
    private RelativeLayout mRl_search;

    public RoutePlaneFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        if (mInflate == null)
        {
            mInflate = inflater.inflate(R.layout.fragment_routeplane, container, false);
            initUI();
            setListener();
        }
        return mInflate;
    }

    private void setListener ()
    {
        mMylocation_rela.setOnClickListener(this);
        mInput_end_rela.setOnClickListener(this);
        mTransAdress.setOnClickListener(this);
        mInflate.findViewById(R.id.ll_for_search_all).setOnClickListener(this);
        mRl_search.setOnClickListener(this);
    }

    private void initUI ()
    {
        mMylocation_rela = (RelativeLayout) mInflate.findViewById(R.id.mylocation_rela);
        mInput_end_rela = (RelativeLayout) mInflate.findViewById(R.id.input_end_rela);

        mTransAdress = (RelativeLayout) mInflate.findViewById(R.id.rl_trans_adress);

        mTv_curr_loction = (TextView) mInflate.findViewById(R.id.tv_curr_loction);
        mIv_curr_loction = (ImageView) mInflate.findViewById(R.id.iv_curr_loction);

        mTv_ending_station = (TextView) mInflate.findViewById(R.id.tv_ending_station);
        mIv_ending_station = (ImageView) mInflate.findViewById(R.id.iv_ending_station);

        mRl_search = (RelativeLayout) mInflate.findViewById(R.id.rl_search);


        ListView transfer_listView = (ListView) mInflate.findViewById(R.id.transfer_listView);
        View transfer_foot = getLayoutInflater(getArguments()).inflate(R.layout.transfer_foot, null);
        transfer_listView.addFooterView(transfer_foot);
        transfer_listView.setAdapter(new MyAdapter());
    }

    @Override
    public void onClick (View v)
    {
        Intent intent = new Intent();
        switch (v.getId())
        {
            case R.id.mylocation_rela:
                intent.setClass(getActivity(), InputLocActivity.class);
                if(isTrans)
                {
                    intent.putExtra(MyConstants.IntentKey.INPUT_TYPE_KEY, "end");
                }else
                {
                    intent.putExtra(MyConstants.IntentKey.INPUT_TYPE_KEY, "myLoc");
                }
                startActivityForResult(intent, MyConstants.RequestAndResultCode.CHOOSE_START);
                break;
            case R.id.input_end_rela:
                intent.setClass(getActivity(), InputLocActivity.class);
                if(isTrans)
                {
                    intent.putExtra(MyConstants.IntentKey.INPUT_TYPE_KEY, "myLoc");
                }else
                {
                    intent.putExtra(MyConstants.IntentKey.INPUT_TYPE_KEY, "end");
                }
                startActivityForResult(intent, MyConstants.RequestAndResultCode.CHOOSE_ARRIVE);
                break;
            case R.id.rl_trans_adress:
                isTrans = !isTrans;
                TransLoc(isTrans);
                break;
            case R.id.ll_for_search_all:
                intent.setClass(getActivity(), InPutLocAllActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_search:
                intent.setClass(getActivity(), TransitRouteActivity.class);
                intent.putExtra("choosed_start", mTv_curr_loction.getText().toString());
                intent.putExtra("choosed_end",mTv_ending_station.getText().toString());
                startActivity(intent);
                break;
        }
    }

    class MyAdapter extends BaseAdapter
    {

        @Override
        public int getCount ()
        {
            return loc01.length;
        }

        @Override
        public Object getItem (int position)
        {
            return null;
        }

        @Override
        public long getItemId (int position)
        {
            return 0;
        }

        @Override
        public View getView (int position, View convertView, ViewGroup parent)
        {
            View inflate = getLayoutInflater(getArguments()).inflate(R.layout.transfer_listitem, null);
            TextView loc01_tv = (TextView) inflate.findViewById(R.id.loc01_tv);
            TextView loc02_tv = (TextView) inflate.findViewById(R.id.loc02_tv);
            loc01_tv.setText(loc01[position]);
            loc02_tv.setText(loc02[position]);
            return inflate;
        }
    }


    public void TransLoc(boolean is_trans)
    {
        String curr_loction = mTv_curr_loction.getText().toString();
        String end_loction = mTv_ending_station.getText().toString();
        if(!is_trans)
        {
            mTv_curr_loction.setText(end_loction);//当前位置
            mIv_curr_loction.setImageResource(R.mipmap.icon_poi_mylocation);

            mTv_ending_station.setText(curr_loction);//目的地位置
            mIv_ending_station.setImageResource(R.mipmap.icon_poi_input);
        }else
        {
            mTv_curr_loction.setText(end_loction);//目的地位置
            mIv_curr_loction.setImageResource(R.mipmap.icon_poi_input);

            mTv_ending_station.setText(curr_loction);//当前位置
            mIv_ending_station.setImageResource(R.mipmap.icon_poi_mylocation);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null)
        {
            switch (requestCode)
            {
                case MyConstants.RequestAndResultCode.CHOOSE_START:
                    if(resultCode == MyConstants.RequestAndResultCode.CHOOSED)
                    {
                        String choosed = data.getStringExtra("choosed");
                        mTv_curr_loction.setText(choosed);
                    }
                    break;
                case MyConstants.RequestAndResultCode.CHOOSE_ARRIVE:
                    if(resultCode == MyConstants.RequestAndResultCode.CHOOSED)
                    {
                        String choosed = data.getStringExtra("choosed");
                        mTv_ending_station.setText(choosed);
                    }
                    break;
            }
        }
    }
}

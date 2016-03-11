package com.example.zjb.bamin.Cdachezuche;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zjb.bamin.R;

public class ZuCheChooseCarTypeActivity extends AppCompatActivity implements View.OnClickListener
{

    private ImageView mIv_back;
    private ListView mLv_zuche_choose_car_type;
    private CarTypeAdapter mCarTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_car_type);

        findViewID();
        initUI();
        setListener();
    }

    private void findViewID()
    {
        mIv_back = (ImageView) findViewById(R.id.iv_zuche_choose_car_type_back);
        mLv_zuche_choose_car_type = (ListView) findViewById(R.id.lv_zuche_choose_car_type);
    }

    private void initUI()
    {
        mCarTypeAdapter = new CarTypeAdapter();
        mLv_zuche_choose_car_type.setAdapter(mCarTypeAdapter);
    }

    private void setListener()
    {
        mIv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_zuche_choose_car_type_back:
                finish();
                break;
        }
    }

    class CarTypeAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return 6;
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            //sss
            View layout = getLayoutInflater().inflate(R.layout.dachezuche_choose_car_type_list_item, null);
            ImageView iv_car_img = (ImageView) layout.findViewById(R.id.iv_car_img);//车型图片
            TextView tv_car_name = (TextView) layout.findViewById(R.id.tv_car_name);//车型名称
            TextView tv_car_price = (TextView) layout.findViewById(R.id.tv_car_price);//租金
            TextView tv_carriage_count = (TextView) layout.findViewById(R.id.tv_carriage_count);//厢数(三厢)
            TextView tv_displacement = (TextView) layout.findViewById(R.id.tv_displacement);//排量(1.4自动)
            TextView tv_car_seat_count = (TextView) layout.findViewById(R.id.tv_car_seat_count);//可乘坐位数(乘坐5人)

            return layout;
        }
    }
}

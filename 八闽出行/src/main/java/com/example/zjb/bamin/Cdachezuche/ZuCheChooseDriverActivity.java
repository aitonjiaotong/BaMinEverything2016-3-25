package com.example.zjb.bamin.Cdachezuche;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.administrator.shane_library.shane.utils.GsonUtils;
import com.example.administrator.shane_library.shane.utils.HTTPUtils;
import com.example.administrator.shane_library.shane.utils.UILUtils;
import com.example.administrator.shane_library.shane.utils.VolleyListener;
import com.example.zjb.bamin.Cdachezuche.constant_dachezuche.ConstantDaCheZuChe;
import com.example.zjb.bamin.Cdachezuche.models.DriverInfo;
import com.example.zjb.bamin.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZuCheChooseDriverActivity extends AppCompatActivity implements View.OnClickListener
{

    private ListView mLv_dachezuche_choose_driver;
    private ImageView mIv_dache_jg_back;
    private DriverListAdapter mDriverListAdapter;
    private List<DriverInfo.ContainsEntity> mDriverInfoData = new ArrayList<>();
    private TextView mTv_driver_more;
    private int mPage = 0;
    private Map<String, String> mParams = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zu_che_choose_driver);

        findVieId();
        initUI();
        setListener();
        initData();
    }

    private void initData()
    {
        mParams.put("page", mPage + "");
        HTTPUtils.post(ZuCheChooseDriverActivity.this, ConstantDaCheZuChe.Url.DRIVER_LIST, mParams, new VolleyListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {

            }

            @Override
            public void onResponse(String s)
            {
                DriverInfo driverInfo = GsonUtils.parseJSON(s, DriverInfo.class);
                if (driverInfo.getNum() == (mPage + 1))
                {
                    mTv_driver_more.setVisibility(View.GONE);
                } else
                {
                    mTv_driver_more.setVisibility(View.VISIBLE);
                }
                mDriverInfoData.addAll(driverInfo.getContains());
                mDriverListAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setListener()
    {
        mIv_dache_jg_back.setOnClickListener(this);
        mTv_driver_more.setOnClickListener(this);
    }

    private void initUI()
    {
        mDriverListAdapter = new DriverListAdapter();
        mLv_dachezuche_choose_driver.setAdapter(mDriverListAdapter);

    }

    private void findVieId()
    {
        mIv_dache_jg_back = (ImageView) findViewById(R.id.iv_dache_jg_back);
        mLv_dachezuche_choose_driver = (ListView) findViewById(R.id.lv_dachezuche_choose_driver);
        mTv_driver_more = (TextView) findViewById(R.id.tv_driver_more);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_dache_jg_back:
                finish();
                break;
            case R.id.tv_driver_more:
                mPage++;
                initData();
                break;
        }
    }

    class DriverListAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return mDriverInfoData.size();
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
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            View layout = getLayoutInflater().inflate(R.layout.layout_dachezuche_driver_list_item, null);
            ImageView iv_Ratingbar1 = (ImageView) layout.findViewById(R.id.iv_Ratingbar1);
            ImageView iv_Ratingbar2 = (ImageView) layout.findViewById(R.id.iv_Ratingbar2);
            ImageView iv_Ratingbar3 = (ImageView) layout.findViewById(R.id.iv_Ratingbar3);
            ImageView iv_Ratingbar4 = (ImageView) layout.findViewById(R.id.iv_Ratingbar4);
            ImageView iv_Ratingbar5 = (ImageView) layout.findViewById(R.id.iv_Ratingbar5);
            ImageView iv_driver_img = (ImageView) layout.findViewById(R.id.iv_driver_img);
            ImageView iv_driver_sex_img = (ImageView) layout.findViewById(R.id.iv_driver_sex_img);
            TextView tv_driver_name = (TextView) layout.findViewById(R.id.tv_driver_name);
            TextView tv_driver_driving_years = (TextView) layout.findViewById(R.id.tv_driver_driving_years);
            ImageView iv_driver_send_msg = (ImageView) layout.findViewById(R.id.iv_driver_send_msg);
            ImageView iv_driver_call_phone = (ImageView) layout.findViewById(R.id.iv_driver_call_phone);
            //TODO 跳转到给司机留言的界面
//            iv_driver_send_msg.setOnClickListener();
            //给司机打电话
            iv_driver_call_phone.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (mDriverInfoData.get(position).getPhone() != null && !"".equals(mDriverInfoData.get(position).getPhone()))
                    {
                        if (ActivityCompat.checkSelfPermission(ZuCheChooseDriverActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                        {
                            ActivityCompat.requestPermissions(ZuCheChooseDriverActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 0);
                        }
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mDriverInfoData.get(position).getPhone()));
                        startActivity(intent);
                    }
                }
            });

            if (mDriverInfoData != null && mDriverInfoData.size() > 0)
            {
                if (mDriverInfoData.get(position).getImage() != null && !"".equals(mDriverInfoData.get(position).getImage()))
                {
                    UILUtils.displayImageNoAnim(mDriverInfoData.get(position).getImage(), iv_driver_img);
                }
                tv_driver_name.setText(mDriverInfoData.get(position).getName());
                tv_driver_driving_years.setText(mDriverInfoData.get(position).getDrivingYear() + "");
                setRatingBarNum((int) mDriverInfoData.get(position).getStar(), iv_Ratingbar1, iv_Ratingbar2, iv_Ratingbar3, iv_Ratingbar4, iv_Ratingbar5);
                if ("男".equals(mDriverInfoData.get(position).getSex()))
                {
                    iv_driver_sex_img.setImageResource(R.mipmap.xingbienan_2x);
                } else
                {
                    iv_driver_sex_img.setImageResource(R.mipmap.xingbienv_2x);
                }
            }
            return layout;
        }
    }

    private void setRatingBarNum(int i, ImageView ratingbar1, ImageView ratingbar2, ImageView ratingbar3, ImageView ratingbar4, ImageView ratingbar5)
    {
        switch (i)
        {
            case 1:
                ratingbar1.setImageResource(R.mipmap.xinxinghuang_2x);
                break;
            case 2:
                ratingbar1.setImageResource(R.mipmap.xinxinghuang_2x);
                ratingbar2.setImageResource(R.mipmap.xinxinghuang_2x);
                break;
            case 3:
                ratingbar1.setImageResource(R.mipmap.xinxinghuang_2x);
                ratingbar2.setImageResource(R.mipmap.xinxinghuang_2x);
                ratingbar3.setImageResource(R.mipmap.xinxinghuang_2x);
                break;
            case 4:
                ratingbar1.setImageResource(R.mipmap.xinxinghuang_2x);
                ratingbar2.setImageResource(R.mipmap.xinxinghuang_2x);
                ratingbar3.setImageResource(R.mipmap.xinxinghuang_2x);
                ratingbar4.setImageResource(R.mipmap.xinxinghuang_2x);
                break;
            case 5:
                ratingbar1.setImageResource(R.mipmap.xinxinghuang_2x);
                ratingbar2.setImageResource(R.mipmap.xinxinghuang_2x);
                ratingbar3.setImageResource(R.mipmap.xinxinghuang_2x);
                ratingbar4.setImageResource(R.mipmap.xinxinghuang_2x);
                ratingbar5.setImageResource(R.mipmap.xinxinghuang_2x);
                break;
        }
    }
}

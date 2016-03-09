package com.example.zjb.bamin.changtukepiao;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.administrator.shane_library.shane.utils.GsonUtils;
import com.example.administrator.shane_library.shane.utils.HTTPUtils;
import com.example.administrator.shane_library.shane.utils.VolleyListener;
import com.example.zjb.bamin.R;
import com.example.zjb.bamin.constant.Constant;
import com.example.zjb.bamin.customView.MyGridView;
import com.example.zjb.bamin.models.about_companysubzone.CompanySubZone;
import com.example.zjb.bamin.models.about_companysubzone.SubZone_;
import com.example.zjb.bamin.models.about_sites.Sites;
import com.example.zjb.bamin.sql.MySqLite;
import com.example.zjb.bamin.utils.DialogShow;
import com.example.zjb.bamin.utils.GetLastWordUtil;
import com.example.zjb.bamin.utils.LogUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectStationArriveActivity extends AppCompatActivity implements View.OnClickListener
{
    //数据库相关----------start
    private String TAB_NAME = "arrive";
    private int mVersion = 1;
    private SQLiteDatabase mDb;
    private ContentValues mValues;
    //数据库相关----------end

    //控制子gridview的开关------start
    private int mIsOpen = -1;
    private int mShiPostion = 0;
    //控制子gridview的开关------end

    // 到达目的地相关的列表------start
    private List<CompanySubZone> parent_list_data = new ArrayList<CompanySubZone>();//省份的所有数据List列表
    private List<String> parent_list_name = new ArrayList<String>();//省份的所有数据List列表(省份名称的字符串)
    private List<SubZone_> parent_list_xianshi_name = new ArrayList<>();
    private Map<String, List<String>> map = new HashMap<String, List<String>>();//关联省份与省份下一级的市
    // 到达目的地相关的列表------start

    //常用地址相关------start
    private ListView mLv_commonly_used_address;
    private List<String> mComUsedAddrData = new ArrayList<String>();
    private CommuonUsedAddrAdapter mAdapter = new CommuonUsedAddrAdapter();
    private String mCenterCityName;
    //常用地址相关------end

    //搜索所有站点的列表-----start
    private List<Sites> mSitesData = new ArrayList<Sites>();
    private List<String> mUserSearchSitesData = new ArrayList<String>();

    private String mUser_input;
    private boolean isCommonlyAddr = true;
    private ListView mLv_search_addr;

    private SearchAddrAdapter mSearchAdapter = new SearchAddrAdapter();
    //搜索所有站点的列表-----end


    private ImageView mIv_back;
    private EditText mSearch_editText;
    private ImageView mIv_search_clear;


    private ListView mArrive_listView;
    private MyArriveAdapter mMyArriveAdapter;

    private MyGridViewAdapter mMyGridViewAdapter;

    private RelativeLayout mXianshi_rela;

    private String mPhoneNum;
    private ProgressBar mRefreash_arrive;
    private GridView mGridView_xianshi;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initSharedPreferences();
        initDB();
        findViewId();
        initUI();
        setOnclick();
        initData();

    }

    private void initSharedPreferences()
    {
        //获取登陆状态
        SharedPreferences sp = getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        mPhoneNum = sp.getString("phoneNum", "");
    }

    private void initData()
    {
        //初始化数据
        initBaseData();

        //初始化用户搜索地址
        initSitesData();

    }

    private void initBaseData()
    {
        mRefreash_arrive.setVisibility(View.VISIBLE);
        mArrive_listView.setVisibility(View.GONE);
        mXianshi_rela.setVisibility(View.GONE);
        HTTPUtils.get(SelectStationArriveActivity.this, Constant.URLFromAiTon.GET_ZONE_STREE, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                DialogShow.setDialog(SelectStationArriveActivity.this, "网络连接异常或正在维护", "确认");
            }

            @Override
            public void onResponse(String s) {
                /**--------解析Json------------------*/
                Type type = new TypeToken<ArrayList<CompanySubZone>>() {
                }.getType();
                ArrayList<CompanySubZone> o = GsonUtils.parseJSONArray(s, type);
                //加载解析Json得到各省份数据
                parent_list_data.clear();
                parent_list_data.addAll(o);
                for (int i = 0; i < parent_list_data.size(); i++) {
                    //获取各省份名称，放置于List容器中，用于适配器中更新相关数据
                    parent_list_name.add(i, parent_list_data.get(i).getZoneName());
                    List<String> list1 = new ArrayList<String>();//保存省份下一级的各市地区名称(字符串)
                    for (int j = 0; j < parent_list_data.get(i).getSubZones().size(); j++) {
                        list1.add(parent_list_data.get(i).getSubZones().get(j).getZoneName());
                    }
                    map.put(parent_list_data.get(i).getZoneName(), list1);
                }
                mMyArriveAdapter.notifyDataSetChanged();
                mRefreash_arrive.setVisibility(View.GONE);
                mArrive_listView.setVisibility(View.VISIBLE);
                mXianshi_rela.setVisibility(View.GONE);
            }
        });
    }

    /*初始化本地数据库内是否有保存常用地址数据     */
    private void initDB()
    {
        //数据库查询相关-------------start
        MySqLite mySqLite = new MySqLite(SelectStationArriveActivity.this, mVersion);
        mDb = mySqLite.getWritableDatabase();
        mValues = new ContentValues();
        //数据库查询相关-------------end
    }

    private void initSitesData()
    {
        HTTPUtils.get(SelectStationArriveActivity.this, Constant.URLFromAiTon.GET_SITE, new VolleyListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
                DialogShow.setDialog(SelectStationArriveActivity.this, "网络连接异常或正在维护", "确认");
            }

            @Override
            public void onResponse(String s)
            {
                Type type = new TypeToken<ArrayList<Sites>>()
                {
                }.getType();
                mSitesData = GsonUtils.parseJSONArray(s, type);
                Log.e("onResponse:mSitesData ", mSitesData.size() + "");
            }
        });
    }


    private void findViewId()
    {
        mIv_back = (ImageView) findViewById(R.id.iv_back);
        mSearch_editText = (EditText) findViewById(R.id.et_search_city);
        mIv_search_clear = (ImageView) findViewById(R.id.iv_clear);
        mArrive_listView = (ListView) findViewById(R.id.arrive_listView);
        mXianshi_rela = (RelativeLayout) findViewById(R.id.xianshi_rela);
        mLv_commonly_used_address = (ListView) findViewById(R.id.lv_commonly_used_address);
        mRefreash_arrive = (ProgressBar) findViewById(R.id.refreash_arrive);
        mGridView_xianshi = (GridView) findViewById(R.id.gridView_xianshi);
    }

    private void initUI()
    {
        initEdiText();

        //初始化用户搜索列表相关
        initUserSearchAddr();


        //到达目地的ListView
        mMyArriveAdapter = new MyArriveAdapter();
        mArrive_listView.setAdapter(mMyArriveAdapter);

        //****初始化常用地址ListView列表****-----start
        if (!"".equals(mPhoneNum))
        {
            //登陆状态下 查询本地数据库中是否有保存常用地址
            queryDB();
            mAdapter.notifyDataSetChanged();
            Log.e("initUI ", "initUI " + mComUsedAddrData.toString());
        }
        mLv_commonly_used_address.setAdapter(mAdapter);
        if (!"".equals(mPhoneNum))
        {
            mLv_commonly_used_address.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    //判断数据库中是否有保存过的数据------start
                    Cursor mCursor_query = mDb.query(TAB_NAME, new String[]{"addr_name"}, "addr_name=?", new String[]{mComUsedAddrData.get(position)}, null, null, null);
                    if (!mCursor_query.moveToNext())
                    {
                        mValues.put("addr_name", mComUsedAddrData.get(position));
                        mDb.insert(TAB_NAME, null, mValues);
                    } else
                    {
                        mDb.delete(TAB_NAME, "addr_name = ?", new String[]{mComUsedAddrData.get(position)});
                        mValues.put("addr_name", mComUsedAddrData.get(position));
                        mDb.insert(TAB_NAME, null, mValues);
                    }
                    mCursor_query.close();
                    //判断数据库中是否有保存过的数据------end
                    Intent intent = new Intent();
                    if ("沙县".equals(mComUsedAddrData.get(position)))
                    {
                        intent.putExtra(Constant.IntentKey.KEY_ARRIVE_ZONE_NAME, mComUsedAddrData.get(position));
                    } else
                    {
                        intent.putExtra(Constant.IntentKey.KEY_ARRIVE_ZONE_NAME, GetLastWordUtil.GetRidOfLastWord(mComUsedAddrData.get(position)));
                    }
                    setResult(Constant.RequestAndResultCode.RESULT_CODE_ARRIVE_COMMONLY_USED_ADDR, intent);
                    finish();
                    animFromBigToSmallOUT();
                }
            });
        }
        //****初始化常用地址ListView列表****-----end


        //各县市地区
        mMyGridViewAdapter = new MyGridViewAdapter();
        mGridView_xianshi.setAdapter(mMyGridViewAdapter);
        mRefreash_arrive = (ProgressBar) findViewById(R.id.refreash_arrive);

    }

    /**
     * 从大到小结束动画
     */
    private void animFromBigToSmallOUT() {
        overridePendingTransition(R.anim.fade_in, R.anim.big_to_small_fade_out);
    }

    //初始化用户搜索列表ListView
    private void initUserSearchAddr()
    {
        mLv_search_addr = (ListView) findViewById(R.id.lv_search_address);
        mLv_search_addr.setAdapter(mSearchAdapter);
        mLv_search_addr.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent data = new Intent();
                if ("沙县".equals(mUserSearchSitesData.get(position)))
                {
                    data.putExtra(Constant.IntentKey.KEY_ARRIVE_ZONE_NAME, mUserSearchSitesData.get(position));
                } else
                {
                    data.putExtra(Constant.IntentKey.KEY_ARRIVE_ZONE_NAME, GetLastWordUtil.GetRidOfLastWord(mUserSearchSitesData.get(position)));
                    Log.e("-->onItemClick ", mUserSearchSitesData.get(position));
                }
                setResult(Constant.RequestAndResultCode.RESULT_CODE_ARRIVE_SEARCH_ADDR, data);

                //保存用户选择后的地址到本地，储存为常用地址---start
                if (!"".equals(mPhoneNum))
                {
                    //判断数据库中是否有保存过的数据------start
                    Cursor mCursor_query = mDb.query(TAB_NAME, new String[]{"addr_name"}, "addr_name=?", new String[]{mUserSearchSitesData.get(position)}, null, null, null);
                    if (!mCursor_query.moveToNext())
                    {
                        mValues.put("addr_name", mUserSearchSitesData.get(position));
                        mDb.insert(TAB_NAME, null, mValues);
                    } else
                    {
                        mDb.delete(TAB_NAME, "addr_name = ?", new String[]{mUserSearchSitesData.get(position)});
                        mValues.put("addr_name", mUserSearchSitesData.get(position));
                        mDb.insert(TAB_NAME, null, mValues);
                    }
                    mCursor_query.close();
                    //判断数据库中是否有保存过的数据------end
                }
                //保存用户选择后的地址到本地，储存为常用地址---end
                finish();
                animFromBigToSmallOUT();
            }
        });
    }


    private void initEdiText()
    {
        /**---------初始化用户搜索列表ListView----------*/
        mSearch_editText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

                mUser_input = s.toString();
                if (count == 0)
                {
                    mArrive_listView.setVisibility(View.VISIBLE);//到达列表
                    mLv_search_addr.setVisibility(View.GONE);//用户搜索列表
                    mXianshi_rela.setVisibility(View.GONE);//县市列表
                    mIv_search_clear.setVisibility(View.GONE);
                } else
                {
                    mArrive_listView.setVisibility(View.GONE);//到达列表
                    mLv_search_addr.setVisibility(View.VISIBLE);//搜索列表
                    mXianshi_rela.setVisibility(View.GONE);//县市列表
                    mIv_search_clear.setVisibility(View.VISIBLE);

                    mUserSearchSitesData.clear();
                    mSearchAdapter.notifyDataSetChanged();
                    //比对用户输入的内容，并提取更新显示相关控件
                    for (int i = 0; i < mSitesData.size(); i++)
                    {
                        String siteName = mSitesData.get(i).getSiteName();
                        if (mSitesData.get(i).getSiteName().startsWith(mUser_input.trim()) || mSitesData.get(i).getSiteCode().toLowerCase().startsWith(mUser_input.trim().toLowerCase()))
                        {
                            mUserSearchSitesData.add(siteName);
                            mSearchAdapter.notifyDataSetChanged();
                        }
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
    }


    private void setOnclick()
    {
        mIv_back.setOnClickListener(this);
        mIv_search_clear.setOnClickListener(this);
        findViewById(R.id.back_to_shengshi).setOnClickListener(this);
        mGridView_xianshi.setOnItemClickListener(new MyGridViewOnItemClickListener());

    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_back:
                finish();
                animFromBigToSmallOUT();
                break;

            case R.id.iv_clear:
                mSearch_editText.setText("");
                mArrive_listView.setVisibility(View.VISIBLE);//到达列表
                mLv_search_addr.setVisibility(View.GONE);//用户搜索列表
                mXianshi_rela.setVisibility(View.GONE);//到达县市列表
                break;
            case R.id.back_to_shengshi:
                mArrive_listView.setVisibility(View.VISIBLE);//到达列表
                mLv_search_addr.setVisibility(View.GONE);//用户搜索列表
                mXianshi_rela.setVisibility(View.GONE);//到达县市列表
                break;
        }
    }


    class MyArriveAdapter extends BaseAdapter implements View.OnClickListener
    {

        private MyGridView mShi_gridView;
        private MyGridAdapter mMyGridAdapter;

        @Override
        public int getCount()
        {
            return parent_list_data.size();
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
            View inflate = getLayoutInflater().inflate(R.layout.arrive_listitem, null);
            LinearLayout shi_linear = (LinearLayout) inflate.findViewById(R.id.shi_linear);
            TextView listTv = (TextView) inflate.findViewById(R.id.listTv);
            listTv.setText(parent_list_data.get(position).getZoneName());
            listTv.setTag(position);
            listTv.setOnClickListener(this);
            mShi_gridView = (MyGridView) inflate.findViewById(R.id.shi_gridView);
            mMyGridAdapter = new MyGridAdapter();
            mShi_gridView.setAdapter(mMyGridAdapter);
            mShi_gridView.setOnItemClickListener(new MyShiGridViewItemListener());
            if (position == mIsOpen)
            {
                mMyGridAdapter.notifyDataSetChanged();
                shi_linear.setVisibility(View.VISIBLE);
            } else
            {
                shi_linear.setVisibility(View.GONE);
            }
            return inflate;
        }

        class MyShiGridViewItemListener implements AdapterView.OnItemClickListener
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                parent_list_xianshi_name.clear();
                parent_list_xianshi_name.addAll(parent_list_data.get(mShiPostion).getSubZones().get(position).getSubZones());
                LogUtil.show("onClick 各市的下标:", position + "");
                LogUtil.show("onClick 各市的下标:", parent_list_data.get(mShiPostion).getSubZones().get(position).getZoneName());
                mCenterCityName = parent_list_data.get(mShiPostion).getSubZones().get(position).getZoneName();
                mMyGridViewAdapter.notifyDataSetChanged();
                mArrive_listView.setVisibility(View.GONE);//到达列表
                mXianshi_rela.setVisibility(View.VISIBLE);//到达县市列表
                mLv_search_addr.setVisibility(View.GONE);//用户搜索列表


            }
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.listTv:
                    int position = (int) v.getTag();
                    if (position == mIsOpen)
                    {
                        mIsOpen = -1;
                    } else
                    {
                        mIsOpen = position;
                        mShiPostion = position;
                        LogUtil.show("onClick mShiPostion:", mShiPostion + "");
                    }
                    mMyArriveAdapter.notifyDataSetChanged();
                    break;
            }
        }

        class MyGridAdapter extends BaseAdapter
        {

            @Override
            public int getCount()
            {
                return parent_list_data.get(mShiPostion).getSubZones().size();
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
                View inflate = getLayoutInflater().inflate(R.layout.list_item_city_set_out, null);
                TextView shi_tv = (TextView) inflate.findViewById(R.id.tv_city);
                shi_tv.setText(parent_list_data.get(mShiPostion).getSubZones().get(position).getZoneName());
                return inflate;
            }
        }
    }

    class CommuonUsedAddrAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {

            if (mComUsedAddrData.size() > 5)
            {
                return 5;
            } else
            {
                return mComUsedAddrData.size();
            }
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
            View layout = getLayoutInflater().inflate(R.layout.list_item_commonly_used_address, null);
            TextView tv_com_used_addr = (TextView) layout.findViewById(R.id.tv_commonly_used_address);
            if (mComUsedAddrData != null && mComUsedAddrData.size() > 0)
            {
                tv_com_used_addr.setText(mComUsedAddrData.get(position));
            } else
            {
                tv_com_used_addr.setText("没有查找到常用地址数据！");
            }
            return layout;
        }
    }

    class MyGridViewAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return parent_list_xianshi_name.size();
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
            View inflate = getLayoutInflater().inflate(R.layout.list_item_city_set_out, null);
            TextView tv_city = (TextView) inflate.findViewById(R.id.tv_city);
            if ("市区".equals(parent_list_xianshi_name.get(position).getZoneName()))
            {
                tv_city.setText(mCenterCityName);
            } else
            {
                tv_city.setText(parent_list_xianshi_name.get(position).getZoneName());
            }
            return inflate;
        }
    }

    /**
     * 查询本地数据库  常用地址
     */
    public void queryDB()
    {
        Cursor mCursor_query = mDb.query(TAB_NAME, null, null, null, null, null, null);
        mComUsedAddrData.clear();
        boolean moveToFirst = mCursor_query.moveToFirst();
        while (moveToFirst)
        {
            String addr_name = mCursor_query.getString(mCursor_query.getColumnIndex("addr_name"));
            Log.e("queryDB ", "queryDB " + addr_name);
            mComUsedAddrData.add(addr_name);
            moveToFirst = mCursor_query.moveToNext();
        }
        Collections.reverse(mComUsedAddrData);
        mCursor_query.close();
    }


    private void AnimFromRightToLeft()
    {
        overridePendingTransition(R.anim.fade_in, R.anim.push_left_out);
    }

    public boolean onKeyDown(int keyCode, android.view.KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            finish();
            AnimFromRightToLeft();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * //     * 用户搜索时显示列表的适配器
     * //
     */
    class SearchAddrAdapter extends BaseAdapter
    {
        public int getCount()
        {
            return mUserSearchSitesData.size();
        }

        public Object getItem(int position)
        {
            return null;
        }

        public long getItemId(int position)
        {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            View layout = getLayoutInflater().inflate(R.layout.list_item_search_city, null);
            TextView tv_city_search = (TextView) layout.findViewById(R.id.tv_city);
            if (mUserSearchSitesData != null && mUserSearchSitesData.size() > 0)
            {
                tv_city_search.setText(mUserSearchSitesData.get(position));
                Log.e("SearchSitesData", mUserSearchSitesData.get(position));
            }
            return layout;
        }
    }


    class MyGridViewOnItemClickListener implements AdapterView.OnItemClickListener
    {

        @Override
        protected Object clone() throws CloneNotSupportedException
        {
            return super.clone();
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Intent intent = new Intent();
            if ("沙县".equals(parent_list_xianshi_name.get(position).getZoneName()))
            {
                intent.putExtra(Constant.IntentKey.KEY_ARRIVE_ZONE_NAME, parent_list_xianshi_name.get(position).getZoneName());
                //保存用户选择后的地址到本地，储存为常用地址---start
                if (!"".equals(mPhoneNum))
                {
                    //判断数据库中是否有保存过的数据
                    Cursor mCursor_query = mDb.query(TAB_NAME, new String[]{"addr_name"}, "addr_name=?", new String[]{parent_list_xianshi_name.get(position).getZoneName()}, null, null, null);
                    if (!mCursor_query.moveToNext())
                    {
                        mValues.put("addr_name", parent_list_xianshi_name.get(position).getZoneName());
                        mDb.insert(TAB_NAME, null, mValues);
                    } else
                    {
                        mDb.delete(TAB_NAME, "addr_name = ?", new String[]{parent_list_xianshi_name.get(position).getZoneName()});
                        mValues.put("addr_name", parent_list_xianshi_name.get(position).getZoneName());
                        mDb.insert(TAB_NAME, null, mValues);
                    }
                    mCursor_query.close();
                }
                //保存用户选择后的地址到本地，储存为常用地址---end
            } else
            {
                if ("市区".equals(parent_list_xianshi_name.get(position).getZoneName()))
                {
                    intent.putExtra(Constant.IntentKey.KEY_ARRIVE_ZONE_NAME, GetLastWordUtil.GetRidOfLastWord(mCenterCityName));
                    //保存用户选择后的地址到本地，储存为常用地址---start
                    if (!"".equals(mPhoneNum))
                    {
                        //判断数据库中是否有保存过的数据
                        Cursor mCursor_query = mDb.query(TAB_NAME, new String[]{"addr_name"}, "addr_name=?", new String[]{mCenterCityName}, null, null, null);
                        if (!mCursor_query.moveToNext())
                        {
                            mValues.put("addr_name", mCenterCityName);
                            mDb.insert(TAB_NAME, null, mValues);
                        } else
                        {
                            mDb.delete(TAB_NAME, "addr_name = ?", new String[]{mCenterCityName});
                            mValues.put("addr_name", mCenterCityName);
                            mDb.insert(TAB_NAME, null, mValues);
                        }
                        mCursor_query.close();
                    }
                    //保存用户选择后的地址到本地，储存为常用地址---end
                } else
                {
                    intent.putExtra(Constant.IntentKey.KEY_ARRIVE_ZONE_NAME, GetLastWordUtil.GetRidOfLastWord(parent_list_xianshi_name.get(position).getZoneName()));
                    //保存用户选择后的地址到本地，储存为常用地址---start
                    if (!"".equals(mPhoneNum))
                    {
                        //判断数据库中是否有保存过的数据
                        Cursor mCursor_query = mDb.query(TAB_NAME, new String[]{"addr_name"}, "addr_name=?", new String[]{parent_list_xianshi_name.get(position).getZoneName()}, null, null, null);
                        if (!mCursor_query.moveToNext())
                        {
                            mValues.put("addr_name", parent_list_xianshi_name.get(position).getZoneName());
                            mDb.insert(TAB_NAME, null, mValues);
                        } else
                        {
                            mDb.delete(TAB_NAME, "addr_name = ?", new String[]{parent_list_xianshi_name.get(position).getZoneName()});
                            mValues.put("addr_name", parent_list_xianshi_name.get(position).getZoneName());
                            mDb.insert(TAB_NAME, null, mValues);
                        }
                        mCursor_query.close();
                    }
                    //保存用户选择后的地址到本地，储存为常用地址---end
                }
            }
            setResult(Constant.RequestAndResultCode.RESULT_CODE_ARRIVE_ADDR, intent);

            finish();
            animFromBigToSmallOUT();
        }
    }
}

package com.example.zjb.bamin.Bchangtukepiao.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.administrator.shane_library.shane.utils.GsonUtils;
import com.example.administrator.shane_library.shane.utils.HTTPUtils;
import com.example.administrator.shane_library.shane.utils.VolleyListener;
import com.example.zjb.bamin.Bchangtukepiao.activity.OrderDeatilActivity;
import com.example.zjb.bamin.Bchangtukepiao.activity.PayActivity;
import com.example.zjb.bamin.Bchangtukepiao.constant.Constant;
import com.example.zjb.bamin.Bchangtukepiao.models.about_order.AccountOrder;
import com.example.zjb.bamin.Bchangtukepiao.models.about_order.QueryOrder;
import com.example.zjb.bamin.R;
import com.example.zjb.bamin.Zutils.DialogShow;
import com.example.zjb.bamin.Zutils.TimeAndDateFormate;
import com.umeng.analytics.MobclickAgent;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;


public class Fragment02 extends Fragment implements WaveSwipeRefreshLayout.OnRefreshListener{

    private View mInflate;
    private TextView mNoneOrder;
    private WaveSwipeRefreshLayout mSwipe;
    private ListView mOrderListview;
    private String mId;
    private MyAdapter mMyAdapter;
    private List<AccountOrder.OrdersEntity> mAccountOrderEntityList = new ArrayList<>();
    private List<QueryOrder> mQueryOrderList = new ArrayList<>();
    private List<String> orderStateList = new ArrayList<>();
    private boolean mIsupdata = false;
    //请求订单的页数
    private int orderPageCount = 0;
    private AccountOrder mAccountOrder;
    //订单总页数
    private int mPages;
    private TextView mTextView_moreOrder;
    private View mOrder_list_foot;

    public Fragment02() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_fragment02, null);
            /**
             * 获取用户id
             */
            SharedPreferences sp = getActivity().getSharedPreferences("isLogin", Context.MODE_PRIVATE);
            mId = sp.getString("id", "");
            initUI();
            /**
             * 根据用户id查询所有订单号
             */
            clearData();
            queryAccountIdToOrder();
        }
//缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) mInflate.getParent();
        if (parent != null) {
            parent.removeView(mInflate);
        }
        return mInflate;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sp = getActivity().getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        mId = sp.getString("id", "");
    }

    @Override
    public void onStop() {
        super.onStop();
        mSwipe.setRefreshing(false);
    }
    //查询所有有订单号
    private void queryAccountIdToOrder() {
        mNoneOrder.setVisibility(View.GONE);
        mSwipe.setRefreshing(true);
        mOrderListview.setVisibility(View.INVISIBLE);
        String url = Constant.URLFromAiTon.HOST + "front/ladorderbyuser";
        Map<String, String> map = new HashMap<>();
        map.put("account_id", mId);
        map.put("page", orderPageCount + "");
        HTTPUtils.post(getActivity(), url, map, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                DialogShow.setDialog(getActivity(), "网络连接异常或正在维护", "确认");
            }

            @Override
            public void onResponse(String s) {
                //每查询一次页数要+1
                orderPageCount++;
                /**
                 *刷新时先清除，重新获取。
                 */
//                mAccountOrderList.clear();
//                mQueryOrderList.clear();
//                orderStateList.clear();
//                Type type = new TypeToken<ArrayList<AccountOrder>>() {
//                }.getType();
//                mAccountOrderList = GsonUtils.parseJSONArray(s, type);
                mAccountOrder = GsonUtils.parseJSON(s, AccountOrder.class);
//                /**
//                 * 翻转容器，让最近的排在最前面
//                 */
//                Collections.reverse(mAccountOrder.getOrders());
                mAccountOrderEntityList.addAll(mAccountOrder.getOrders());
                mPages = mAccountOrder.getPages();
                /**
                 * 暂无订单显示与否
                 */
                if (mAccountOrderEntityList.size() > 0) {
                    mNoneOrder.setVisibility(View.GONE);
                    mOrder_list_foot.setVisibility(View.VISIBLE);
                    /**
                     * 将所有订单对象和状态都实例化
                     */
                    QueryOrder queryOrder = new QueryOrder("1", 1, null, null, null, null, null, 1, false, false, null, null, null, null, null, 1.1, null, null, "正在生成");
                    for (int i = 0; i < mAccountOrder.getOrders().size(); i++) {
                        mQueryOrderList.add(queryOrder);
                        orderStateList.add("正在生成");
                    }
                    /**
                     * 查询所有订单号的对象和状态
                     */
                    for (int i = 0; i < mAccountOrder.getOrders().size(); i++) {
                        queryAllOrderInfo((orderPageCount - 1) * 8 + i);
                        queryAllOrderState((orderPageCount - 1) * 8 + i);
                    }
                } else {
                    mNoneOrder.setVisibility(View.VISIBLE);
                    mSwipe.setRefreshing(false);
                    mOrder_list_foot.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 查询所有订单状态
     *
     * @param i
     */
    private void queryAllOrderState(final int i) {
        String url_web = Constant.URL.HOST +
                "SellTicket_Other_NoBill_GetBookStateAndMinuteToConfirm?scheduleCompanyCode=" + "YongAn" + "" +
                "&bookLogID=" + mAccountOrder.getOrders().get(i-(orderPageCount-1)*8).getBookLogAID();
        HTTPUtils.get(getActivity(), url_web, new VolleyListener() {
            public void onErrorResponse(VolleyError volleyError) {
            }

            public void onResponse(String s) {
                Document doc = null;
                try {
                    doc = DocumentHelper.parseText(s);
                    Element testElement = doc.getRootElement();
                    String testxml = testElement.asXML();
                    String result = testxml.substring(testxml.indexOf(">") + 1, testxml.lastIndexOf("<"));
                    String state = result.substring(2, 5);
                    orderStateList.remove(i);
                    orderStateList.add(i, state);
                    /**
                     * 防止刷新不一致崩掉
                     */
                    if (!("正在生成".equals(orderStateList.get(mQueryOrderList.size() - 1))) && !("正在生成".equals(mQueryOrderList.get(mAccountOrder.getOrders().size() - 1).getMyStateDesc()))) {
                        mIsupdata = true;
                        mMyAdapter.notifyDataSetChanged();
                        mSwipe.setRefreshing(false);
                        mOrderListview.setVisibility(View.VISIBLE);
                    }
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 查询单个订单状态
     *
     * @param i
     */
    private void queryOrderState(final int i) {
        String url_web = Constant.URL.HOST +
                "SellTicket_Other_NoBill_GetBookStateAndMinuteToConfirm?scheduleCompanyCode=" + "YongAn" + "" +
                "&bookLogID=" + mAccountOrderEntityList.get(i).getBookLogAID();
        HTTPUtils.get(getActivity(), url_web, new VolleyListener() {
            public void onErrorResponse(VolleyError volleyError) {
            }

            public void onResponse(String s) {
                Document doc = null;
                try {
                    doc = DocumentHelper.parseText(s);
                    Element testElement = doc.getRootElement();
                    String testxml = testElement.asXML();
                    String result = testxml.substring(testxml.indexOf(">") + 1, testxml.lastIndexOf("<"));
                    String state = result.substring(2, 5);
                    if ("已确认".equals(state)) {
                        Intent intent = new Intent();
                        intent.putExtra("BookLogAID", mAccountOrderEntityList.get(i).getBookLogAID());
                        intent.putExtra("isSure", "isSure");
                        intent.setClass(getActivity(), OrderDeatilActivity.class);
                        startActivity(intent);
                        animFromSmallToBigIN();
                    } else if ("未确认".equals(state)) {
                        Intent intent = new Intent();
                        intent.putExtra("BookLogAID", mAccountOrderEntityList.get(i).getBookLogAID());
                        intent.setClass(getActivity(), PayActivity.class);
                        startActivity(intent);
                        animFromSmallToBigIN();
                    } else if ("已撤销".equals(state)) {
                        DialogShow.setDialog(getActivity(), "订单已撤销", "确认");
                    } else if ("已取票".equals(state)) {
                        DialogShow.setDialog(getActivity(), "已取票", "确认");
                    }

                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 查询所有订单号的对象
     */
    private void queryAllOrderInfo(final int i) {
        String url = Constant.URL.HOST +
                "QueryBookLog?getTicketCodeOrAID=" + mAccountOrder.getOrders().get(i - (orderPageCount - 1) * 8).getBookLogAID();
        HTTPUtils.get(getActivity(), url, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }

            @Override
            public void onResponse(String s) {
                Log.e("onResponse ", "orderPageCount" + orderPageCount);
                Document doc = null;
                try {
                    doc = DocumentHelper.parseText(s);
                    Element testElement = doc.getRootElement();
                    String testxml = testElement.asXML();
                    String result = testxml.substring(testxml.indexOf(">") + 1, testxml.lastIndexOf("<"));
                    QueryOrder queryOrder = GsonUtils.parseJSON(result, QueryOrder.class);
                    mQueryOrderList.remove(i);
                    mQueryOrderList.add(i, queryOrder);
                    /**
                     * 防止刷新不一致崩掉
                     */
                    if (!("正在生成".equals(orderStateList.get(mAccountOrder.getOrders().size() - 1))) && !("正在生成".equals(mQueryOrderList.get(mAccountOrder.getOrders().size() - 1).getMyStateDesc()))) {
                        mIsupdata = true;
                        mMyAdapter.notifyDataSetChanged();
                        mSwipe.setRefreshing(false);
                        mOrderListview.setVisibility(View.VISIBLE);
                    }

                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initUI() {

        mNoneOrder = (TextView) mInflate.findViewById(R.id.noneOrder);
        mSwipe = (WaveSwipeRefreshLayout) mInflate.findViewById(R.id.main_swipe);
        initWaveSwipeRefreshLayout();
        mOrder_list_foot = getActivity().getLayoutInflater().inflate(R.layout.order_list_foot, null);
        mOrderListview = (ListView) mInflate.findViewById(R.id.order_listView);
        mOrderListview.addFooterView(mOrder_list_foot);
//        mOrder_list_foot.findViewById(R.id.getMoreOrder).setOnClickListener(this);
        mTextView_moreOrder = (TextView) mOrder_list_foot.findViewById(R.id.textView_MoreOrder);
        mMyAdapter = new MyAdapter();
        mOrderListview.setAdapter(mMyAdapter);
        mOrderListview.setOnItemClickListener(new MyItemClickListener());

    }

    class MyItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position==mAccountOrderEntityList.size()){
                if (orderPageCount<mPages){
                    queryAccountIdToOrder();
                }else{
                    mTextView_moreOrder.setText("没有更多订单了");
                }
            }else{
                queryOrderState(position);
            }
        }
    }

    /**
     * 从小到大打开动画
     */
    private void animFromSmallToBigIN() {
        getActivity().overridePendingTransition(R.anim.magnify_fade_in, R.anim.fade_out);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mQueryOrderList.size();
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
            View inflate = getLayoutInflater(getArguments()).inflate(R.layout.order_listitem, null);
            TextView textView_startPlace = (TextView) inflate.findViewById(R.id.textView_startPlace);
            TextView textView_endPlace = (TextView) inflate.findViewById(R.id.textView_endPlace);
            TextView textView_startTime = (TextView) inflate.findViewById(R.id.textView_startTime);
            TextView textView_ticketCount = (TextView) inflate.findViewById(R.id.textView_ticketCount);
            TextView textView_ticketState = (TextView) inflate.findViewById(R.id.textView_ticketState);
            TextView textView_orderPrice = (TextView) inflate.findViewById(R.id.textView_orderPrice);
            if (mIsupdata) {
                textView_startPlace.setText(mQueryOrderList.get(position).getStartSiteName());
                textView_endPlace.setText(mQueryOrderList.get(position).getEndSiteName());
                textView_startTime.setText("出发日期：" + TimeAndDateFormate.dateFormate(mQueryOrderList.get(position).getSetoutTime()));
                textView_ticketCount.setText("票数：" + mQueryOrderList.get(position).getFullTicket());
                /**
                 * 修改返回状态字眼
                 */
                if ("已确认".equals(orderStateList.get(position))) {
                    textView_ticketState.setText("已支付");
                } else if ("未确认".equals(orderStateList.get(position))) {
                    textView_ticketState.setText("未支付");
                } else {
                    textView_ticketState.setText(orderStateList.get(position));
                }
                textView_orderPrice.setText(mQueryOrderList.get(position).getPrice() + "");
            }
            return inflate;
        }
    }

    private void initWaveSwipeRefreshLayout() {
        mSwipe.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mSwipe.setOnRefreshListener(this);
        mSwipe.setWaveColor(getResources().getColor(R.color.title_bar));
        mSwipe.setMaxDropHeight(mSwipe.getHeight() / 3);
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    private void refresh() {
        clearData();
        queryAccountIdToOrder();
        // 更新が終了したらインジケータ非表示
    }
    //清除订单页面数据
    private void clearData() {
        orderPageCount=0;
        mAccountOrderEntityList.clear();
        mQueryOrderList.clear();
        orderStateList.clear();
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }
}

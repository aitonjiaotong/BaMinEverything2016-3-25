package com.example.zjb.bamin.Bchangtukepiao.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.andview.refreshview.XRefreshView;
import com.example.administrator.shane_library.shane.utils.GsonUtils;
import com.example.administrator.shane_library.shane.utils.HTTPUtils;
import com.example.administrator.shane_library.shane.utils.VolleyListener;
import com.example.zjb.bamin.Bchangtukepiao.activity.OrderDeatilActivity;
import com.example.zjb.bamin.Bchangtukepiao.activity.PayActivity;
import com.example.zjb.bamin.Bchangtukepiao.constant.Constant;
import com.example.zjb.bamin.Bchangtukepiao.models.about_order.AccountOrder;
import com.example.zjb.bamin.Bchangtukepiao.models.about_order.QueryOrder;
import com.example.zjb.bamin.R;
import com.example.zjb.bamin.ZcustomView.CustomerFooter;
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


public class Fragment0201 extends Fragment {

    private View mInflate;
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
    private XRefreshView mCustom_view;
    private TextView mNoneOrder;
    private boolean isItemClickED = false;

    public Fragment0201() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_fragment0201, null);
            /**
             * 获取用户id
             */
            SharedPreferences sp = getActivity().getSharedPreferences("isLogin", Context.MODE_PRIVATE);
            mId = sp.getString("id", "");
            initUI();
//            clearData();
//            queryAccountIdToOrder();
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
    }

    /**
     * 根据用户id查询所有订单号
     */
    //查询所有有订单号
    private void queryAccountIdToOrder() {
        String url = Constant.URLFromAiTon.HOST + "front/ladorderbyuser";
        Map<String, String> map = new HashMap<>();
        map.put("account_id", mId);
        map.put("page", orderPageCount + "");
        HTTPUtils.post(getActivity(), url, map, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mCustom_view.stopRefresh();
                mCustom_view.stopLoadMore();
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
                    mCustom_view.stopRefresh();
                    mCustom_view.stopLoadMore();
                    mNoneOrder.setVisibility(View.VISIBLE);
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
                "&bookLogID=" + mAccountOrder.getOrders().get(i - (orderPageCount - 1) * 8).getBookLogAID();
        HTTPUtils.get(getActivity(), url_web, new VolleyListener() {
            public void onErrorResponse(VolleyError volleyError) {
                mCustom_view.stopRefresh();
                mCustom_view.stopLoadMore();
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
                        mCustom_view.stopRefresh();
                        mCustom_view.stopLoadMore();
                        mMyAdapter.notifyDataSetChanged();
                    }
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IndexOutOfBoundsException e) {
                    Log.e("onResponse ", "不要乱点" + "不要乱点");
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
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.setClass(getActivity(), OrderDeatilActivity.class);
                        startActivity(intent);
                        animFromSmallToBigIN();
                    } else if ("未确认".equals(state)) {
                        Intent intent = new Intent();
                        intent.putExtra("BookLogAID", mAccountOrderEntityList.get(i).getBookLogAID());
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.setClass(getActivity(), PayActivity.class);
                        startActivity(intent);
                        animFromSmallToBigIN();
                    } else if ("已撤销".equals(state)) {
                        DialogShow.setDialog(getActivity(), "订单已撤销", "确认");
                    } else if ("已取票".equals(state)) {
                        DialogShow.setDialog(getActivity(), "已取票", "确认");
                    }
                    isItemClickED = false;
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IndexOutOfBoundsException e) {
                    Log.e("onResponse ", "不要乱点" + "不要乱点");
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
                mCustom_view.stopRefresh();
                mCustom_view.stopLoadMore();
            }

            @Override
            public void onResponse(String s) {
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
                        mCustom_view.stopRefresh();
                        mCustom_view.stopLoadMore();
                        mMyAdapter.notifyDataSetChanged();
                    }

                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IndexOutOfBoundsException e) {
                    Log.e("onResponse ", "不要乱点" + "不要乱点");
                }
            }
        });
    }

    private void initUI() {
        mNoneOrder = (TextView) mInflate.findViewById(R.id.noneOrder);
        mCustom_view = (XRefreshView) mInflate.findViewById(R.id.custom_view);
        mOrderListview = (ListView) mInflate.findViewById(R.id.order_listView);
        mMyAdapter = new MyAdapter();
        mOrderListview.setAdapter(mMyAdapter);
        mOrderListview.setOnItemClickListener(new MyItemClickListener());
        initXRefreshView();
    }

    private void initXRefreshView() {
        mCustom_view.setPullLoadEnable(true);
        mCustom_view.setPinnedTime(1000);
        mCustom_view.setAutoLoadMore(true);
        mCustom_view.setMoveForHorizontal(true);
        mCustom_view.setCustomFooterView(new CustomerFooter(getActivity()));
        mCustom_view.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh() {
                mNoneOrder.setVisibility(View.GONE);
                clearData();
                queryAccountIdToOrder();
            }

            @Override
            public void onLoadMore(boolean isSlience) {
                if (orderPageCount < mPages) {
                    queryAccountIdToOrder();
                } else {
                    mCustom_view.stopLoadMore();
                    Toast.makeText(getActivity(), "没有更多订单了", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**
         * 根据用户id查询所有订单号
         */
        mCustom_view.startRefresh();
    }

    class MyItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            isItemClickED = !isItemClickED;
            Log.e("onItemClick ", "isItemClickED " + isItemClickED);
            if (isItemClickED) {
                if (mAccountOrderEntityList.get(position).getFlag() == 1) {
                    DialogShow.setDialog(getActivity(), "订单出现异常，请联系客服", "确认");
                } else {

                    queryOrderState(position);
                }
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


    //清除订单页面数据
    private void clearData() {
        orderPageCount = 0;
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

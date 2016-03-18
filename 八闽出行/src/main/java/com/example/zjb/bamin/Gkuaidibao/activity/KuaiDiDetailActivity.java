package com.example.zjb.bamin.Gkuaidibao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zjb.bamin.Gkuaidibao.model.KuaiDiInfo;
import com.example.zjb.bamin.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class KuaiDiDetailActivity extends AppCompatActivity {

    private ListView mListView_kuaidi;
    private KuaiDiInfo mKuaiDiInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuai_di_detail);
        initIntent();
        findID();
    }

    private void initIntent() {
        Intent intent = getIntent();
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        mKuaiDiInfo = (KuaiDiInfo) intent.getSerializableExtra("kuaiDiInfo");
        List<KuaiDiInfo.TracesEntity> traces = mKuaiDiInfo.getTraces();
        if (traces.size()==0){
            traces.add(new KuaiDiInfo.TracesEntity(sdf.format(System.currentTimeMillis()),"此单无物流信息"));
        }
    }

    private void findID() {
        mListView_kuaidi = (ListView) findViewById(R.id.listView_kuaidi);
        mListView_kuaidi.setAdapter(new MyAdapter());
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mKuaiDiInfo.getTraces().size();
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
            View inflate = getLayoutInflater().inflate(R.layout.kuaidi_item, null);
            TextView textView_detial = (TextView) inflate.findViewById(R.id.textView_detial);
            TextView textView_time = (TextView) inflate.findViewById(R.id.textView_time);
            List<KuaiDiInfo.TracesEntity> traces = mKuaiDiInfo.getTraces();
            textView_detial.setText(traces.get(position).getAcceptStation());
            textView_time.setText(traces.get(position).getAcceptTime());
            return inflate;
        }
    }

    public void back(View view) {
        finish();
        AnimFromRightToLeftOUT();
    }
    /**
     * 从右往左结束动画
     */
    private void AnimFromRightToLeftOUT() {
        overridePendingTransition(R.anim.fade_in, R.anim.push_left_out);
    }
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            AnimFromRightToLeftOUT();
        }
        return super.onKeyDown(keyCode, event);
    }
}

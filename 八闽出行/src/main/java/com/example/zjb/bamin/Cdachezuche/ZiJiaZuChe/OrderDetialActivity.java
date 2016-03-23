package com.example.zjb.bamin.Cdachezuche.ZiJiaZuChe;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zjb.bamin.R;

public class OrderDetialActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detial);
        setListener();
    }

    private void setListener() {
        findViewById(R.id.button_cancle_order).setOnClickListener(this);
        findViewById(R.id.imageView_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_cancle_order:
                View doublebuttondialog = getLayoutInflater().inflate(R.layout.doublebuttondialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                Button ISee = (Button) doublebuttondialog.findViewById(R.id.ISee);
                Button button_cancle = (Button) doublebuttondialog.findViewById(R.id.button_cancle);
                TextView message = (TextView) doublebuttondialog.findViewById(R.id.message);
                message.setText("您确认取消订单？");
                ISee.setText("确认取消");
                button_cancle.setText("暂不取消");
                builder.setView(doublebuttondialog);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                ISee.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 2016/3/23 确认取消订单操作
                    }
                });
                button_cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                break;
            case R.id.imageView_back:
                finish();
                break;
        }
    }
}

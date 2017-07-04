package com.sf.bocfinancialsoftware.activity.user;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.util.SetHintTextSize;

public class SuggestionActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etSuggestion, etContact;
    private Button btnSend;
    private ImageView ivBack;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        mContext = this;
        initView();
        initLister();
    }

    private void initView() {
        TextView textView = (TextView) findViewById(R.id.tvTitle);
        textView.setText(R.string.tv_user_suggestion);
        etSuggestion = (EditText) findViewById(R.id.etSuggestion);
        etContact = (EditText) findViewById(R.id.etContact);
        btnSend = (Button) findViewById(R.id.btnSend);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        //设置隐藏字体大小
        SetHintTextSize.setHintTextSize(etSuggestion,getString(R.string.et_suggestion),12);
        SetHintTextSize.setHintTextSize(etContact,getString(R.string.et_contact),12);
    }


    private void initLister() {
        btnSend.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.btnSend:
                send();
                break;
        }
    }

    private void send() {
        String suggestion = etSuggestion.getText().toString();
        String contents = etContact.getText().toString();
        if (TextUtils.isEmpty(suggestion)) {
            Toast.makeText(mContext, R.string.et_suggestion, Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(contents)) {
            Toast.makeText(mContext, R.string.et_contact, Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(mContext, R.string.toast_get_suggestion, Toast.LENGTH_LONG).show();
        finish();
    }

}

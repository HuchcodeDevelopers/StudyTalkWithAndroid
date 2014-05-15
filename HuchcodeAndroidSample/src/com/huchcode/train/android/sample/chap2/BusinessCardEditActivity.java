package com.huchcode.train.android.sample.chap2;

import java.io.Serializable;

import com.huchcode.train.android.sample.R;
import com.huchcode.train.android.sample.chap2.domain.BusinessCard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BusinessCardEditActivity extends Activity {

    private EditText etName;
    private EditText etCompany;
    private EditText etPhoneNo;
    private EditText etEmail;

    private BusinessCard card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.businesscard_edit_layout);

        etName = (EditText) findViewById(R.id.etName);
        etCompany = (EditText) findViewById(R.id.etCompany);
        etPhoneNo = (EditText) findViewById(R.id.etPhoneNo);
        etEmail = (EditText) findViewById(R.id.etEmail);

        Button btnOk = (Button) findViewById(R.id.btnOk);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(this.clickListener);
        btnCancel.setOnClickListener(this.clickListener);

        initialize();
    }

    private void initialize() {
        Intent intent = getIntent();
        Serializable serializableExtra = intent.getSerializableExtra(ContentProviderBusinessCardListActivity.INTENT_KEY_CARD);

        // 전달된 card가 없을 경우는 생성, 있을 경우는 수정
        if (serializableExtra == null) {
            card = new BusinessCard();
        }
        else {
            card = (BusinessCard) serializableExtra;
            etName.setText(card.getName());
            etCompany.setText(card.getCompany());
            etPhoneNo.setText(card.getPhoneNo());
            etEmail.setText(card.getEmail());
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
            case R.id.btnOk:
                card.setName(etName.getText().toString());
                card.setCompany(etCompany.getText().toString());
                card.setPhoneNo(etPhoneNo.getText().toString());
                card.setEmail(etEmail.getText().toString());

                Intent intent = new Intent();
                intent.putExtra(ContentProviderBusinessCardListActivity.INTENT_KEY_CARD, card);
                setResult(RESULT_OK, intent);
                finish();
                break;

            case R.id.btnCancel:
                setResult(RESULT_CANCELED);
                finish();
                break;

            }

            finish();
        }
    };
}

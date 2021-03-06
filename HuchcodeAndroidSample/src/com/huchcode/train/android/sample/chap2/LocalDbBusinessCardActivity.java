package com.huchcode.train.android.sample.chap2;

import java.util.List;

import com.huchcode.train.android.sample.R;
import com.huchcode.train.android.sample.chap2.domain.BusinessCard;
import com.huchcode.train.android.sample.chap2.provider.LocalDbBusinessCardProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class LocalDbBusinessCardActivity extends Activity {

    private static final int MENU_ADD = 1;
    private static final int MENU_EDIT = 2;
    private static final int MENU_REMOVE = 3;

    private ListView lvCards;

    private BusinessCardAdapter adapter;

    private LocalDbBusinessCardProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.businesscard_list_layout);

        lvCards = (ListView) findViewById(R.id.list);
        provider = new LocalDbBusinessCardProvider(this);

        // 데이터 조회
        List<BusinessCard> cards = provider.findAllBusinessCard();

        // 어댑터를 생성하여 listView에 주입
        adapter = new BusinessCardAdapter(this, R.layout.businesscard_list_layout_row, cards);
        lvCards.setAdapter(adapter);

        registerForContextMenu(lvCards);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_ADD, 0, "추가").setIcon(android.R.drawable.ic_menu_add);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
        case MENU_ADD: {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            final View view = inflater.inflate(R.layout.businesscard_dialog, null);

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("명함추가");
            dialog.setView(view);
            dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO 1.dialog의 뷰에 입력된 값을 가져온다.
                    EditText etName = (EditText) view.findViewById(R.id.etName);
                    EditText etCompany = (EditText) view.findViewById(R.id.etCompany);
                    EditText etPhoneNo = (EditText) view.findViewById(R.id.etPhoneNo);
                    EditText etEmail = (EditText) view.findViewById(R.id.etEmail);

                    String name = etName.getText().toString();
                    String company = etCompany.getText().toString();
                    String phoneNo = etPhoneNo.getText().toString();
                    String email = etEmail.getText().toString();

                    // TODO 2.BusinessCard 객체를 만든다.
                    BusinessCard card = new BusinessCard();
                    card.setName(name);
                    card.setCompany(company);
                    card.setPhoneNo(phoneNo);
                    card.setEmail(email);

                    // TODO 3.db에 추가
                    provider.registerBusinessCard(card);
                    adapter.setItems(provider.findAllBusinessCard());
                    adapter.notifyDataSetChanged();

                    Toast.makeText(getBaseContext(), "추가합니다.", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 할거 없음.
                }
            });

            dialog.show();
            break;
        }
        }

        return false;
    }

    // 컨텍스트 메뉴 생성
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, MENU_EDIT, Menu.NONE, "수정");
        menu.add(0, MENU_REMOVE, Menu.NONE, "삭제");
    }

    // 컨텍스트 메뉴 이벤트 처리
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        super.onContextItemSelected(item);
        final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
        case MENU_EDIT: {
            LayoutInflater inflater = getLayoutInflater();
            final View view = inflater.inflate(R.layout.businesscard_dialog, null);
            final EditText etName = (EditText) view.findViewById(R.id.etName);
            final EditText etCompany = (EditText) view.findViewById(R.id.etCompany);
            final EditText etPhoneNo = (EditText) view.findViewById(R.id.etPhoneNo);
            final EditText etEmail = (EditText) view.findViewById(R.id.etEmail);

            BusinessCard card = provider.getBusinessCard(info.id);
            etName.setText(card.getName());
            etCompany.setText(card.getCompany());
            etPhoneNo.setText(card.getPhoneNo());
            etEmail.setText(card.getEmail());

            new AlertDialog.Builder(this).setTitle("명함수정").setView(view).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    BusinessCard card = new BusinessCard();
                    card.setId(info.id);
                    card.setName(etName.getText().toString());
                    card.setCompany(etCompany.getText().toString());
                    card.setPhoneNo(etPhoneNo.getText().toString());
                    card.setEmail(etEmail.getText().toString());

                    provider.modifyBusinessCard(card);
                    adapter.setItems(provider.findAllBusinessCard());
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getBaseContext(), "수정합니다.", Toast.LENGTH_SHORT).show();

                }
            }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }

            }).show();
            return true;
        }
        case MENU_REMOVE: {
            provider.removeBusinessCard(info.id);
            adapter.setItems(provider.findAllBusinessCard());
            adapter.notifyDataSetChanged();
            Toast.makeText(getBaseContext(), "삭제합니다.", Toast.LENGTH_SHORT).show();
            return true;
        }
        }

        return false; // 어떤 context메뉴도 처리하지 않으면 false리턴하여 작업 중단.
    }

    @SuppressWarnings("unused")
    private void hideKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

}

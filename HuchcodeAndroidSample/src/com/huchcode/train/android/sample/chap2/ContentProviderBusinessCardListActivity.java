package com.huchcode.train.android.sample.chap2;

import java.util.List;

import com.huchcode.train.android.sample.R;
import com.huchcode.train.android.sample.chap2.domain.BusinessCard;
import com.huchcode.train.android.sample.chap2.provider.ContentProviderBusinessCardProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ContentProviderBusinessCardListActivity extends Activity {
    public static final String INTENT_KEY_CARD = "card";

    private static final int REQUEST_CODE_ADD = 1;
    private static final int REQUEST_CODE_EDIT = 2;

    private static final int MENU_ADD = 1;
    private static final int MENU_EDIT = 2;
    private static final int MENU_REMOVE = 3;

    private ListView lvCards;

    /** 어댑터뷰에 데이터를 제공하는 어댑터 객체 */
    private BusinessCardAdapter adapter;

    /** 데이터 관리를 담당하는 객체 */
    private ContentProviderBusinessCardProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.businesscard_list_layout);

        lvCards = (ListView) findViewById(R.id.list);

        provider = new ContentProviderBusinessCardProvider(this);

        // 데이터 조회
        List<BusinessCard> cards = provider.findAllBusinessCard();

        // 어댑터를 생성하여 listView에 주입
        adapter = new BusinessCardAdapter(this, R.layout.businesscard_list_layout_row, cards);
        lvCards.setAdapter(adapter);

        // 컨텍스트 메뉴 등록
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
            Intent intent = new Intent(getBaseContext(), BusinessCardEditActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD);
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
            BusinessCard card = provider.getBusinessCard(info.id);

            Intent intent = new Intent(getBaseContext(), BusinessCardEditActivity.class);
            intent.putExtra(INTENT_KEY_CARD, card);
            startActivityForResult(intent, REQUEST_CODE_EDIT);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            BusinessCard card = null;
            switch (requestCode) {
            case REQUEST_CODE_ADD:
                card = (BusinessCard) data.getSerializableExtra(INTENT_KEY_CARD);
                this.provider.registerBusinessCard(card);
                break;

            case REQUEST_CODE_EDIT:
                card = (BusinessCard) data.getSerializableExtra(INTENT_KEY_CARD);
                this.provider.modifyBusinessCard(card);
                break;

            default:
                break;
            }

            this.adapter.setItems(this.provider.findAllBusinessCard());
            this.adapter.notifyDataSetChanged();

        }
        else if (resultCode == RESULT_CANCELED) {
            // 취소일 때는 별다른 처리 없음.
        }
        else {
            Toast.makeText(getBaseContext(), "응답코드가 올바르지 않습니다. " + resultCode, Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressWarnings("unused")
    private void hideKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

}

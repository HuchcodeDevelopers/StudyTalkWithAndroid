package com.huchcode.train.android.sample.chap2;

import java.util.List;

import com.huchcode.train.android.sample.R;
import com.huchcode.train.android.sample.chap2.domain.BusinessCard;
import com.huchcode.train.android.sample.chap2.provider.BusinessCardProvider;

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

public class BusinessCardActivity extends Activity {
    private static final int MENU_ADD = 1;
    private static final int MENU_EDIT = 2;
    private static final int MENU_REMOVE = 3;

    private ListView lvCards;

    /** The object which is providing data to adapter view */
    private BusinessCardAdapter adapter;

    /** The object which is managing data */
    private BusinessCardProvider provider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.businesscard_list_layout);

        lvCards = (ListView) findViewById(R.id.list);

        provider = BusinessCardProvider.getInstance();

        // Retrieving data
        List<BusinessCard> cards = provider.findAllBusinessCard();

        // Inject adapter to listView after creation
        adapter = new BusinessCardAdapter(this, R.layout.businesscard_list_layout_row, cards);
        lvCards.setAdapter(adapter);

        // Register to context menu
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
            dialog.setTitle("Add name card");
            dialog.setView(view);
            dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO 1. to take the input values on dialog
                    EditText etName = (EditText) view.findViewById(R.id.etName);
                    EditText etCompany = (EditText) view.findViewById(R.id.etCompany);
                    EditText etPhoneNo = (EditText) view.findViewById(R.id.etPhoneNo);
                    EditText etEmail = (EditText) view.findViewById(R.id.etEmail);

                    String name = etName.getText().toString();
                    String company = etCompany.getText().toString();
                    String phoneNo = etPhoneNo.getText().toString();
                    String email = etEmail.getText().toString();

                    // TODO 2.Create the object for BusinessCard
                    BusinessCard card = new BusinessCard();
                    card.setName(name);
                    card.setCompany(company);
                    card.setPhoneNo(phoneNo);
                    card.setEmail(email);

                    // TODO 3.Add on db
                    provider.registerBusinessCard(card);
                    adapter.setItems(provider.findAllBusinessCard());
                    adapter.notifyDataSetChanged();

                    Toast.makeText(getBaseContext(), "It will be added.", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO nothing 다이얼로그 버튼 이벤트에 아무런 작업도 처리하지 않으면 기본동작인 다이얼로그를
                    // 닫는다.
                }
            });

            dialog.show();
            break;
        }
        }

        return false;
    }

    // Create the Context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, MENU_EDIT, Menu.NONE, "Modify");
        menu.add(0, MENU_REMOVE, Menu.NONE, "Delete");
    }

    // Event handling for the Context menu
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

            new AlertDialog.Builder(this).setTitle("Modify name card").setView(view).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
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
                    Toast.makeText(getBaseContext(), "It will be modified.", Toast.LENGTH_SHORT).show();

                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                }

            }).show();
            return true;
        }
        case MENU_REMOVE: {
            provider.removeBusinessCard(info.id);
            adapter.setItems(provider.findAllBusinessCard());
            adapter.notifyDataSetChanged();
            Toast.makeText(getBaseContext(), "It will be deleted.", Toast.LENGTH_SHORT).show();
            return true;
        }
        }

        return false; // Stop any job without doing any context with returning false 
    }

    @SuppressWarnings("unused")
    private void hideKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}

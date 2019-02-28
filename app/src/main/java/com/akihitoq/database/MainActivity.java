package com.akihitoq.database;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText edit_username;
    private EditText edit_password;
    private Button btn_AddUser;
    private ListView lv_listUser;

    AlertDialog.Builder abd;

    private dbManage db = new dbManage(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit_username = (EditText) findViewById(R.id.username_input);
        edit_password = (EditText) findViewById(R.id.password_input);
        btn_AddUser = (Button) findViewById(R.id.btn_add);
        lv_listUser = (ListView) findViewById(R.id.listview_user);

        abd = new AlertDialog.Builder(this);
        ArrayList<String> listUser = new ArrayList<>();
        ArrayAdapter<String> adapterUser = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,listUser);
        lv_listUser.setAdapter(adapterUser);
        getUserToListView();
        btn_AddUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               clickAddUser();
            }
        });

        lv_listUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                abd.setTitle("Confirm");
                abd.setMessage("Please Confirm");
                abd.setPositiveButton("Cancel",null);
                abd.setNegativeButton("OK", new  AlertDialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            removeUser(pos);
                    }
                });
                abd.show();

            }
        });
    }

    private void clickAddUser(){
        if(!edit_username.getText().toString().equals("")){
            if(!edit_password.getText().toString().equals("")){
                int r ;
                r = db.insertUser(edit_username.getText().toString(),edit_password.getText().toString());
                Log.d("App OS DB",String.valueOf(r));
            }else{
                Toast toast = Toast.makeText(this,"Please Enter Password", Toast.LENGTH_SHORT);
                toast.show();
            }
        }else{
            Toast toast = Toast.makeText(this,"Please Enter Username", Toast.LENGTH_SHORT);
            toast.show();
        }

        getUserToListView();

    }

    private void getUserToListView(){
        ArrayList<ArrayList<String>> allUser = db.selectUser();
        ArrayList<String> listUser = new ArrayList<>();
        for(int i = 0; i < allUser.size(); i++) {
            listUser.add(allUser.get(i).get(0));
        }
        ArrayAdapter<String> adapterUser = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,listUser);
        lv_listUser.setAdapter(adapterUser);
    }

    private int removeUser(int position){
        ArrayList<ArrayList<String>> allUser = db.selectUser();
        ArrayList<String> listUser = new ArrayList<>();
        for(int i = 0; i < allUser.size(); i++) {
            listUser.add(allUser.get(i).get(0));
        }

        Toast toast = Toast.makeText(this,listUser.get(position),Toast.LENGTH_SHORT);
        toast.show();

        int result = db.deleteUser(listUser.get(position));
        if (result != 0) getUserToListView();
        return result;
    }
}

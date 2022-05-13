package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button bringbutton, editbutton;
    EditText edittext;
    LoadJsp connectDB;
    ArrayList<Data> menuList;
    ListView listView;
    MyAdapter myAdapter;

    String beName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bringbutton = (Button) findViewById(R.id.bringbutton);
        editbutton = (Button) findViewById(R.id.editbutton);
        edittext = (EditText) findViewById(R.id.edittext);
        listView = (ListView)findViewById(R.id.sysmenu_list);
        menuList = new ArrayList<Data>();

        myAdapter = new MyAdapter(this, menuList);

        // 불러오기 버튼시
        bringbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallBringServer();
            }
        });
        //리스트 뷰 클릭시
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
           public void onItemClick(AdapterView parent, View v, int position, long id){
               //Toast.makeText(getApplicationContext(), myAdapter.getItem(position).getTname(), Toast.LENGTH_LONG).show();
               edittext.setText(myAdapter.getItem(position).getTname());
               beName = myAdapter.getItem(position).getTname();
           }
        });
        // 수정 버튼 클릭시
        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), edittext.getText(), Toast.LENGTH_LONG).show();
                //리스트를 초기화 하지 않으면 리스트 아이템들이 겹친다
                menuList.clear();
                CallEditServer();
            }
        });
    }
    //리스트 조회
    public void CallBringServer(){
        String jspname = "Bring.jsp";

// LoadJSP 생성
        connectDB = new LoadJsp(jspname, new LoadJsp.AsyncResponse() {
            @Override
            public void processFinish(JSONObject result) {
                try {
                    //리스트형 JSON배열 생성
                    JSONArray array = new JSONArray(result.getString("List"));

                    String strMenu = "";
                    String strCode = "";
                    String strName = "";
                    String strIdx = "";
                    String strGbn = "";
                    String strIcon = "";
                    String strValid = "";

                    for(int i=0; i < array.length(); i++) {

                        //첫번째 배열 가져오기
                        JSONObject jsonObject = array.getJSONObject(i);

                        strMenu = jsonObject.getString("S_MENU");
                        strCode = jsonObject.getString("M_CODE");
                        strName = jsonObject.getString("M_NAME");
                        strIdx = jsonObject.getString("IDX");
                        strGbn = jsonObject.getString("M_GBN");
                        strIcon = jsonObject.getString("ICON");
                        strValid = jsonObject.getString("VALID");

                        Log.d("ABC", "결과 : " + strMenu + " " + strCode + " " + strName + " " + strIdx + " " + strGbn + " " + strIcon + " " + strValid);
                        menuList.add(new Data(strMenu, strCode, strName, strIdx, strGbn, strIcon, strValid));

                        listView.setAdapter(myAdapter);
                    }

                } catch (Exception e){
                    Toast.makeText(getApplicationContext(),"불러올 수 없습니다.\n관리자에게 문의하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        connectDB.execute();
    }
    // 리스트 수정
    public void CallEditServer(){
        String jspname = "Edit.jsp";
        String Values = String.format("M_NAME = '%s' where M_NAME = '%s'", edittext.getText(), beName);
        Log.d("Values", Values);
// LoadJSP 생성
        connectDB = new LoadJsp(Values, jspname, new LoadJsp.AsyncResponse() {
            @Override
            public void processFinish(JSONObject result) {
                try {
                    //리스트형 JSON배열 생성
                    JSONArray array = new JSONArray(result.getString("List"));

                    String strMenu = "";
                    String strCode = "";
                    String strName = "";
                    String strIdx = "";
                    String strGbn = "";
                    String strIcon = "";
                    String strValid = "";

                    for(int i=0; i < array.length(); i++) {

                        //첫번째 배열 가져오기
                        JSONObject jsonObject = array.getJSONObject(i);

                        strMenu = jsonObject.getString("S_MENU");
                        strCode = jsonObject.getString("M_CODE");
                        strName = jsonObject.getString("M_NAME");
                        strIdx = jsonObject.getString("IDX");
                        strGbn = jsonObject.getString("M_GBN");
                        strIcon = jsonObject.getString("ICON");
                        strValid = jsonObject.getString("VALID");

                        Log.d("ABC", "결과 : " + strMenu + " " + strCode + " " + strName + " " + strIdx + " " + strGbn + " " + strIcon + " " + strValid);
                        menuList.add(new Data(strMenu, strCode, strName, strIdx, strGbn, strIcon, strValid));

                        myAdapter.notifyDataSetChanged();
                    }

                } catch (Exception e){
                    Toast.makeText(getApplicationContext(),"불러올 수 없습니다.\n관리자에게 문의하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        connectDB.execute();
    }

}
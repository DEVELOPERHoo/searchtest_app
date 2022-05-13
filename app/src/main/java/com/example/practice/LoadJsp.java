package com.example.practice;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class LoadJsp extends AsyncTask<String, String, JSONObject> {

    String loadUrl =  "http://218.232.186.19:29000/Practice/";//"http://218.232.186.14:29000/test/";//

    ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
    private AsyncResponse delegate = null;

    public interface AsyncResponse {
        void processFinish(JSONObject result);
    }
    // 조회 LoadJsp
    public LoadJsp(String jspname, AsyncResponse delegate) {
        this.delegate = delegate;
        this.loadUrl += jspname;
    }
    // 수정 LoadJsp
    public LoadJsp(String param1, String jspname, AsyncResponse delegate) {
        this.delegate = delegate;
        params.add(new BasicNameValuePair("PARAM1", param1));
        this.loadUrl += jspname;
    }

    public LoadJsp(String param1, String param2, String jspname, AsyncResponse delegate) {
        this.delegate = delegate;
        params.add(new BasicNameValuePair("PARAM1", param1));
        params.add(new BasicNameValuePair("PARAM2", param2));
        this.loadUrl += jspname;
    }

    public LoadJsp(String param1, String param2, String param3, String param4, String jspname, AsyncResponse delegate) {
        this.delegate = delegate;
        params.add(new BasicNameValuePair("PARAM1", param1));
        params.add(new BasicNameValuePair("PARAM2", param2));
        params.add(new BasicNameValuePair("PARAM3", param3));
        params.add(new BasicNameValuePair("PARAM4", param4));
        this.loadUrl += jspname;
    }

    public LoadJsp(String param1, String param2, String param3, String param4, String param5, String param6, String jspname, AsyncResponse delegate) {
        this.delegate = delegate;
        params.add(new BasicNameValuePair("PARAM1", param1));
        params.add(new BasicNameValuePair("PARAM2", param2));
        params.add(new BasicNameValuePair("PARAM3", param3));
        params.add(new BasicNameValuePair("PARAM4", param4));
        params.add(new BasicNameValuePair("PARAM5", param5));
        params.add(new BasicNameValuePair("PARAM6", param6));

        this.loadUrl += jspname;
    }

    // background스레드를 실행하기 전 준비  단계, 변수의 초기화나, 네트워크 통신전 셋팅해야할 것들을 작성
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    // background스레드가 일을 끝마치고 리턴값으로 result를 넘겨준다. 그 값을 매개변수로 받은 후 받은 데이터를 토데로 UI스레드에 일처리를 시킬때 쓴다.
    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        delegate.processFinish(jsonObject);
    }

    // doinbackground메서드에서 중간중간에 UI스레드에게 일처리를 맡겨야 하는 상황에 작성
    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    // background스레드로 일처리를 해주는 곳, 네트워크, 병행 일처리 작성, 비동기적으로 작동
    @Override
    protected JSONObject doInBackground(String... strings) {

        try{
            //Log.d("URL",loadUrl+ params.get(1));
            //구글이 빼버림 따로 라이브러리 추가해줘야함
            HttpClient client = new DefaultHttpClient();
            int useTimeout = 60;
            //HttpClient timeout 셋팅
            if(useTimeout>0){
                //client.getParams().setParameter("http.protocol.expect-continue", false);//HttpClient POST 요청시 Expect 헤더정보 사용 x
                client.getParams().setParameter("http.connection.timeout", useTimeout * 1000);// 원격 호스트와 연결을 설정하는 시간
                client.getParams().setParameter("http.socket.timeout",  useTimeout * 1000);//데이터를 기다리는 시간
                client.getParams().setParameter("http.connection-manager.timeout",  useTimeout * 1000);// 연결 및 소켓 시간 초과
                client.getParams().setParameter("http.protocol.head-body-timeout",  useTimeout * 1000);
            }

            HttpPost post = new HttpPost(loadUrl);

            //인코더시키기(스트링 데이터 전송)
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            post.setEntity(ent);
            // 전송 및 응답
            HttpResponse responsePost = client.execute(post);
            HttpEntity resEntity = responsePost.getEntity();

            if(resEntity != null){

//                   Log.d("Response", EntityUtils.toString(resEntity));
                return test(resEntity);
            }

        }catch (IOException e){
            Log.d("ERROR", e.toString());
            e.printStackTrace();
        }

        return null;
    }

    private JSONObject test(HttpEntity response) {
        StringBuffer buffer = new StringBuffer();
        JSONObject jsonObjectList = null;
        try {
            BufferedReader bufreader = new BufferedReader(new InputStreamReader(response.getContent(), "utf-8"));

            //읽은 데이터를 저장한 StringBuffer 생성

            // bufreader 초기화
            String line = "";
            while((line = bufreader.readLine()) != null){
                buffer.append(line);
            }

            Log.d("Response", buffer.toString());

            //안드로이드쪽에서 JSON객체로 만들기
            jsonObjectList = new JSONObject(buffer.toString());


        } catch (Exception e) {
            jsonObjectList = new JSONObject();
            JSONObject jObject = new JSONObject();
            try {
                jObject.put("ERROR", buffer.toString());
                jsonObjectList.put("Error", jObject);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }


            e.printStackTrace();
        }

        return jsonObjectList;
    }

}//end of Class
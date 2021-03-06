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
    // ์กฐํ LoadJsp
    public LoadJsp(String jspname, AsyncResponse delegate) {
        this.delegate = delegate;
        this.loadUrl += jspname;
    }
    // ์์  LoadJsp
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

    // background์ค๋ ๋๋ฅผ ์คํํ๊ธฐ ์  ์ค๋น  ๋จ๊ณ, ๋ณ์์ ์ด๊ธฐํ๋, ๋คํธ์ํฌ ํต์ ์  ์ํํด์ผํ  ๊ฒ๋ค์ ์์ฑ
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    // background์ค๋ ๋๊ฐ ์ผ์ ๋๋ง์น๊ณ  ๋ฆฌํด๊ฐ์ผ๋ก result๋ฅผ ๋๊ฒจ์ค๋ค. ๊ทธ ๊ฐ์ ๋งค๊ฐ๋ณ์๋ก ๋ฐ์ ํ ๋ฐ์ ๋ฐ์ดํฐ๋ฅผ ํ ๋ฐ๋ก UI์ค๋ ๋์ ์ผ์ฒ๋ฆฌ๋ฅผ ์ํฌ๋ ์ด๋ค.
    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        delegate.processFinish(jsonObject);
    }

    // doinbackground๋ฉ์๋์์ ์ค๊ฐ์ค๊ฐ์ UI์ค๋ ๋์๊ฒ ์ผ์ฒ๋ฆฌ๋ฅผ ๋งก๊ฒจ์ผ ํ๋ ์ํฉ์ ์์ฑ
    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    // background์ค๋ ๋๋ก ์ผ์ฒ๋ฆฌ๋ฅผ ํด์ฃผ๋ ๊ณณ, ๋คํธ์ํฌ, ๋ณํ ์ผ์ฒ๋ฆฌ ์์ฑ, ๋น๋๊ธฐ์ ์ผ๋ก ์๋
    @Override
    protected JSONObject doInBackground(String... strings) {

        try{
            //Log.d("URL",loadUrl+ params.get(1));
            //๊ตฌ๊ธ์ด ๋นผ๋ฒ๋ฆผ ๋ฐ๋ก ๋ผ์ด๋ธ๋ฌ๋ฆฌ ์ถ๊ฐํด์ค์ผํจ
            HttpClient client = new DefaultHttpClient();
            int useTimeout = 60;
            //HttpClient timeout ์ํ
            if(useTimeout>0){
                //client.getParams().setParameter("http.protocol.expect-continue", false);//HttpClient POST ์์ฒญ์ Expect ํค๋์ ๋ณด ์ฌ์ฉ x
                client.getParams().setParameter("http.connection.timeout", useTimeout * 1000);// ์๊ฒฉ ํธ์คํธ์ ์ฐ๊ฒฐ์ ์ค์ ํ๋ ์๊ฐ
                client.getParams().setParameter("http.socket.timeout",  useTimeout * 1000);//๋ฐ์ดํฐ๋ฅผ ๊ธฐ๋ค๋ฆฌ๋ ์๊ฐ
                client.getParams().setParameter("http.connection-manager.timeout",  useTimeout * 1000);// ์ฐ๊ฒฐ ๋ฐ ์์ผ ์๊ฐ ์ด๊ณผ
                client.getParams().setParameter("http.protocol.head-body-timeout",  useTimeout * 1000);
            }

            HttpPost post = new HttpPost(loadUrl);

            //์ธ์ฝ๋์ํค๊ธฐ(์คํธ๋ง ๋ฐ์ดํฐ ์ ์ก)
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            post.setEntity(ent);
            // ์ ์ก ๋ฐ ์๋ต
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

            //์ฝ์ ๋ฐ์ดํฐ๋ฅผ ์ ์ฅํ StringBuffer ์์ฑ

            // bufreader ์ด๊ธฐํ
            String line = "";
            while((line = bufreader.readLine()) != null){
                buffer.append(line);
            }

            Log.d("Response", buffer.toString());

            //์๋๋ก์ด๋์ชฝ์์ JSON๊ฐ์ฒด๋ก ๋ง๋ค๊ธฐ
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
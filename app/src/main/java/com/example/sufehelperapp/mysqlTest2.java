package com.example.sufehelperapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;


public class mysqlTest2 extends AppCompatActivity {

    private static final String url = "jdbc:mysql://101.94.5.73:3306/sufehelper";
    private static final String user ="test123";
    private static final String pass = "1234";

    private TextView textView;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysql_test2);

        textView = (TextView) findViewById(R.id.textView);
        editText1 = (EditText) findViewById(R.id.et1);
        editText2 = (EditText) findViewById(R.id.et2);
        editText3 = (EditText) findViewById(R.id.et3);
        editText4 = (EditText) findViewById(R.id.et4);

    }

    public void btnSend(View view){

        Send objSend = new Send();
        objSend.execute("");
    }

    private class Send extends AsyncTask<String,String,String>{
        String msg = "";
        String text1 = editText1.getText().toString();
        String text2 = editText2.getText().toString();
        String text3 = editText3.getText().toString();
        String text4 = editText4.getText().toString();

        @Override
        protected void onPreExecute(){
            textView.setText("Please Wait Inserting Data");
        }

        @Override
        protected String doInBackground(String...strings){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(url,user,pass);
                if(conn == null){
                    msg = "Connection goes wrong";
                }else{
                    String query = "INSERT INTO user (myName, password, phonenumber, sex) VALUES('"+text1+"','"+text2+"','"+text3+"','"+text4+"')";
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate(query);
                    msg = "Connection Successful!";
                }
                conn.close();
            }catch(Exception e) {
                msg = "Connection goes wrong";
                e.printStackTrace();
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg){
           textView.setText(msg);
        }

    }


}

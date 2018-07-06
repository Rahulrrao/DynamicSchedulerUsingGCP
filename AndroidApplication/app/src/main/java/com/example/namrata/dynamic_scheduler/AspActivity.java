package com.example.namrata.dynamic_scheduler;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AspActivity extends AppCompatActivity {
    EditText eText;
    Button btn;
    View curr;
    ProgressBar pb;
    String SERVER_URL_POST = "http://35.233.145.174/run?format=txt";
    String SERVER_URL_GET = "http://35.233.145.174/";
    List<String> aspAtoms = new ArrayList<>();
    String ProgramID = "";
    Boolean bResult = false;
    //Context mContext = curr.getContext();
    String sResult = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asp);
//        eText = findViewById(R.id.editText);
//        btn = findViewById(R.id.button);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        String str = getIntent().getStringExtra("mytext");
        AspActivity.schedulerService s = new AspActivity.schedulerService();
        s.execute(str);
    }
    public void onClick(View v) {
        //String str = "asp..," + eText.getText().toString();
        String str = getIntent().getStringExtra("mytext");
        AspActivity.schedulerService s = new AspActivity.schedulerService();
        curr = v;
        s.execute(str);
    }

    private class schedulerService extends AsyncTask<String, List<String>, List<String>> {

        @Override
        protected void onPreExecute() {
            pb.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected List<String> doInBackground(String... strings) {
            try {
                ProgramID = POST_DATA(strings[0]);
                //delete the file and ping to get data
                if(!ProgramID.isEmpty()) {
                    //delete the file in file location
                    File fdelete = new File(strings[0]);
                    if (fdelete.exists()) {
                        if (fdelete.delete()) {
                            System.out.println("file Deleted :" + strings[0]);
                        } else {
                            System.out.println("file not Deleted :" + strings[0]);
                        }
                    }
                }
                while(!bResult) {
                    aspAtoms = GET_DATA(ProgramID);
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return aspAtoms;
        }

        @Override
        protected void onPostExecute(List<String> aspAtoms) {
            pb.setVisibility(ProgressBar.INVISIBLE);
            goToSecondActivity(aspAtoms);
        }
    }

    private void goToSecondActivity(List<String> aspAtoms) {
        Intent myIntent = new Intent(this, result_page.class);
        ArrayList<String> arrlistofOptions = new ArrayList<String>(aspAtoms);
        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST",(Serializable)arrlistofOptions);
        myIntent.putExtra("BUNDLE",args);
        startActivity(myIntent);
    }

    private String POST_DATA(String filepath) throws Exception{
        System.out.println("Posting data");
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        String boundary =  "*****"+Long.toString(System.currentTimeMillis())+"*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        String[] q = filepath.split("/");
        int idx = q.length - 1;
        File file = new File(filepath);
        FileInputStream fileInputStream = new FileInputStream(file);
        URL url = new URL(SERVER_URL_POST);
        connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("User-Agent", "Android Multipart HTTP Client 1.0");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary="+boundary);
        outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes("--" + boundary + "\r\n");
        outputStream.writeBytes("Content-Disposition: form-data; name=\"" + "code" + "\"; filename=\"" + q[idx] +"\"" + "\r\n");
        outputStream.writeBytes("Content-Type: text/plain" + "\r\n");
        outputStream.writeBytes("\r\n");
        bytesAvailable = fileInputStream.available();
        bufferSize = Math.min(bytesAvailable, 1048576);
        buffer = new byte[bufferSize];
        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        while(bytesRead > 0) {
            outputStream.write(buffer, 0, bytesRead);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, 1048576);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }
        outputStream.writeBytes("\r\n");
        outputStream.writeBytes("--" + boundary + "--" + "\r\n");
        outputStream.flush();
        outputStream.close();
        fileInputStream.close();
        int status = connection.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            String result = "";
            if ((inputLine = in.readLine()) != null) {
                result = inputLine;
                connection.disconnect();
            } else {
                System.out.println("No Program ID returned");
            }
            return result;
        } else {
            throw new Exception("Non ok response returned");
        }
    }

    private List<String> GET_DATA(String ProgramID) throws Exception {
        System.out.println("Getting results");
        URL reqURL = new URL(SERVER_URL_GET + ProgramID + "/status?format=txt");
        HttpURLConnection connection = (HttpURLConnection) (reqURL.openConnection());
        connection.setRequestMethod("GET");
        connection.connect();
        int httpStatus = connection.getResponseCode();
        if (httpStatus == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            if ((inputLine = in.readLine()) != null) {
                if (inputLine.equals("done")) {
                    URL resultURL = new URL(SERVER_URL_GET + ProgramID + "/result?format=txt");
                    HttpURLConnection conn = (HttpURLConnection) (resultURL.openConnection());
                    conn.setRequestMethod("GET");
                    conn.connect();
                    int HTTPStatus = conn.getResponseCode();
                    if (HTTPStatus == HttpURLConnection.HTTP_OK) {
                        BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder sSchedule = new StringBuilder();
                        String lineReader;
                        while ((lineReader = bf.readLine()) != null) {
                            sSchedule.append(lineReader);
                        }
                        //parse Json
                        JSONObject obj = new JSONObject(sSchedule.toString());


                        for(int i = 0; i<obj.getJSONArray("answers").length(); i++) {
                            int index = obj.getJSONArray("answers").get(i).toString().indexOf("assigned");
                            if(index >= 0){
                                if(index-1 < 0) {
                                    aspAtoms.add(obj.getJSONArray("answers").get(i).toString());
                                }
                            }
                        }

                        //loop through and add to list (remove timeslots)

                        //send it to parseData

                        bResult = true;
                        connection.disconnect();
                        conn.disconnect();
                        return aspAtoms;
                    } else {
                        return aspAtoms;
                    }
                }
            }
        }
        return aspAtoms;
    }
}


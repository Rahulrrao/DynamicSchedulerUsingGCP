package com.example.namrata.dynamic_scheduler;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class result_page extends AppCompatActivity {
    TableLayout t1;
    TextView tr_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);
        Bundle args = getIntent().getBundleExtra("BUNDLE");
        ArrayList<String> data = (ArrayList<String>) args.getSerializable("ARRAYLIST");
        //String[] data = Arrays.copyOf(obj, obj.length, String[].class);
        populateTable(data);
    }
    private void populateTable(ArrayList<String> parse){
        try {
            String name;
            for (String line : parse) {
                if (line.contains("assigned")){
                    line = line.replace(")", "");
                    String[] ele = line.split(",");
                    int start = Integer.parseInt(ele[2]);
                    int []end = new int [Integer.parseInt(ele[3])];
                    for(int j =0; j<end.length;j++){
                        end[j] = start+j;
                    }
                    name = ele[1];
                    for(int j=0;j<end.length;j++){
                        switch (end[j]) {
                            case 24:
                                tr_head = findViewById(R.id.e1);
                                tr_head.setText(name);
                                break;
                            case 1:
                                tr_head = findViewById(R.id.e2);
                                tr_head.setText(name);
                                break;
                            case 2:
                                tr_head = findViewById(R.id.e3);
                                tr_head.setText(name);
                                break;
                            case 3:
                                tr_head = findViewById(R.id.e4);
                                tr_head.setText(name);
                                break;
                            case 4:
                                tr_head = findViewById(R.id.e5);
                                tr_head.setText(name);
                                break;
                            case 5:
                                tr_head = findViewById(R.id.e6);
                                tr_head.setText(name);
                                break;
                            case 6:
                                tr_head = findViewById(R.id.e7);
                                tr_head.setText(name);
                                break;
                            case 7:
                                tr_head = findViewById(R.id.e8);
                                tr_head.setText(name);
                                break;
                            case 8:
                                tr_head = findViewById(R.id.e9);
                                tr_head.setText(name);
                                break;
                            case 9:
                                tr_head = findViewById(R.id.e10);
                                tr_head.setText(name);
                                break;
                            case 10:
                                tr_head = findViewById(R.id.e11);
                                tr_head.setText(name);
                                break;
                            case 11:
                                tr_head = findViewById(R.id.e12);
                                tr_head.setText(name);
                                break;
                            case 12:
                                tr_head = findViewById(R.id.e13);
                                tr_head.setText(name);
                                break;
                            case 13:
                                tr_head = findViewById(R.id.e14);
                                tr_head.setText(name);
                                break;
                            case 14:
                                tr_head = findViewById(R.id.e15);
                                tr_head.setText(name);
                                break;
                            case 15:
                                tr_head = findViewById(R.id.e16);
                                tr_head.setText(name);
                                break;
                            case 16:
                                tr_head = findViewById(R.id.e17);
                                tr_head.setText(name);
                                break;
                            case 17:
                                tr_head = findViewById(R.id.e18);
                                tr_head.setText(name);
                                break;
                            case 18:
                                tr_head = findViewById(R.id.e19);
                                tr_head.setText(name);
                                break;
                            case 19:
                                tr_head = findViewById(R.id.e20);
                                tr_head.setText(name);
                                break;
                            case 20:
                                tr_head = findViewById(R.id.e21);
                                tr_head.setText(name);
                                break;
                            case 21:
                                tr_head = findViewById(R.id.e22);
                                tr_head.setText(name);
                                break;
                            case 22:
                                tr_head = findViewById(R.id.e23);
                                tr_head.setText(name);
                                break;
                            case 23:
                                tr_head = findViewById(R.id.e24);
                                tr_head.setText(name);
                                break;
                        }
                    }
                }
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

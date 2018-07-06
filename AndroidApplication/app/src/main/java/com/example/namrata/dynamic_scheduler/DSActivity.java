package com.example.namrata.dynamic_scheduler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class DSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds);
    }
    public void onSubmit(View v) {
        Context context = v.getContext();
        StringBuilder finalMsg = new StringBuilder("ds..," );
        EditText name1 = (EditText) findViewById(R.id.name1);
        EditText name2 = (EditText) findViewById(R.id.name2);
        EditText name3 = (EditText) findViewById(R.id.name3);
        EditText name4 = (EditText) findViewById(R.id.name4);
        EditText name5 = (EditText) findViewById(R.id.name5);
        EditText desc1 = findViewById(R.id.desc1);
        EditText desc2 = findViewById(R.id.desc2);
        EditText desc3 = findViewById(R.id.desc3);
        EditText desc4 = findViewById(R.id.desc4);
        EditText desc5 = findViewById(R.id.desc5);
        Spinner type1 = findViewById(R.id.spinner1);
        Spinner type2 = findViewById(R.id.spinner2);
        Spinner type3 = findViewById(R.id.spinner3);
        Spinner type4 = findViewById(R.id.spinner4);
        Spinner type5 = findViewById(R.id.spinner5);
        Spinner assgn1 = findViewById(R.id.spinnert1);
        Spinner assgn2 = findViewById(R.id.spinnert2);
        Spinner assgn3 = findViewById(R.id.spinnert3);
        Spinner assgn4 = findViewById(R.id.spinnert4);
        Spinner assgn5 = findViewById(R.id.spinnert5);
        EditText to1 = (EditText) findViewById(R.id.to1);
        EditText to2 = (EditText) findViewById(R.id.to2);
        EditText to3 = (EditText) findViewById(R.id.to3);
        EditText to4 = (EditText) findViewById(R.id.to4);
        EditText to5 = (EditText) findViewById(R.id.to5);
        EditText from1 = (EditText) findViewById(R.id.from1);
        EditText from2 = (EditText) findViewById(R.id.from2);
        EditText from3 = (EditText) findViewById(R.id.from3);
        EditText from4 = (EditText) findViewById(R.id.from4);
        EditText from5 = (EditText) findViewById(R.id.from5);
        String comma = ",";
        String n1 = name1.getText().toString();
        String n2 = name2.getText().toString();
        String n3 = name3.getText().toString();
        String n4 = name4.getText().toString();
        String n5 = name5.getText().toString();
        if(!n1.equals("")){
            String t = type1.getSelectedItem().toString();
            String a = assgn1.getSelectedItem().toString();
            finalMsg = finalMsg.append("Name,").append(n1).append(comma).append(t).append(comma);
            if(t.equals("Weekly")) {
                EditText days = findViewById(R.id.day1);
                String day = days.getText().toString();
                finalMsg = finalMsg.append(day).append(comma);
            }
            if (a.equals("Assigned")) {
                String time1 = to1.getText().toString();
                String time2 = from1.getText().toString();
                if (!time1.equals("")) {
                    finalMsg = finalMsg.append(a).append(comma).append(time1).append(comma).append(time2).append(comma);
                }
            } else if (a.equals("Goal")) {
                EditText goal = findViewById(R.id.ch1);
                String g = goal.getText().toString();
                finalMsg = finalMsg.append(a).append(comma).append(g).append(comma);
            }

        }
        if(!n2.equals("")){
            String t = type2.getSelectedItem().toString();
            String a = assgn2.getSelectedItem().toString();
            finalMsg = finalMsg.append("Name,").append(n2).append(comma).append(t).append(comma);
            if(t.equals("Weekly")) {
                EditText days = findViewById(R.id.day2);
                String day = days.getText().toString();
                finalMsg = finalMsg.append(day).append(comma);
            }
            if (a.equals("Assigned")) {
                String time1 = to2.getText().toString();
                String time2 = from2.getText().toString();
                if (!time1.equals("")) {
                    finalMsg = finalMsg.append(a).append(comma).append(time1).append(comma).append(time2).append(comma);
                }
            } else if (a.equals("Goal")) {
                EditText goal = findViewById(R.id.ch2);
                String g = goal.getText().toString();
                finalMsg = finalMsg.append(a).append(comma).append(g).append(comma);
            }

        }
        if(!n3.equals("")){
            String t = type3.getSelectedItem().toString();
            String a = assgn3.getSelectedItem().toString();
            finalMsg = finalMsg.append("Name,").append(n3).append(comma).append(t).append(comma);
            if(t.equals("Weekly")) {
                EditText days = findViewById(R.id.day3);
                String day = days.getText().toString();
                finalMsg = finalMsg.append(day).append(comma);
            }
            if (a.equals("Assigned")) {
                String time1 = to3.getText().toString();
                String time2 = from3.getText().toString();
                if (!time1.equals("")) {
                    finalMsg = finalMsg.append(a).append(comma).append(time1).append(comma).append(time2).append(comma);
                }
            } else if (a.equals("Goal")) {
                EditText goal = findViewById(R.id.ch3);
                String g = goal.getText().toString();
                finalMsg = finalMsg.append(a).append(comma).append(g).append(comma);
            }

        }
        if(!n4.equals("")){
            String t = type4.getSelectedItem().toString();
            String a = assgn4.getSelectedItem().toString();
            finalMsg = finalMsg.append("Name,").append(n4).append(comma).append(t).append(comma);
            if(t.equals("Weekly")) {
                EditText days = findViewById(R.id.day4);
                String day = days.getText().toString();
                finalMsg = finalMsg.append(day).append(comma);
            }
            if (a.equals("Assigned")) {
                String time1 = to4.getText().toString();
                String time2 = from4.getText().toString();
                if (!time1.equals("")) {
                    finalMsg = finalMsg.append(a).append(comma).append(time1).append(comma).append(time2).append(comma);
                }
            } else if (a.equals("Goal")) {
                EditText goal = findViewById(R.id.ch4);
                String g = goal.getText().toString();
                finalMsg = finalMsg.append(a).append(comma).append(g).append(comma);
            }

        }
        if(!n5.equals("")){
            String t = type5.getSelectedItem().toString();
            String a = assgn5.getSelectedItem().toString();
            finalMsg = finalMsg.append("Name,").append(n5).append(comma).append(t).append(comma);
            if(t.equals("Weekly")) {
                EditText days = findViewById(R.id.day5);
                String day = days.getText().toString();
                finalMsg = finalMsg.append(day).append(comma);
            }
            if (a.equals("Assigned")) {
                String time1 = to5.getText().toString();
                String time2 = from5.getText().toString();
                if (!time1.equals("")) {
                    finalMsg = finalMsg.append(a).append(comma).append(time1).append(comma).append(time2).append(comma);
                }
            } else if (a.equals("Goal")) {
                EditText goal = findViewById(R.id.ch5);
                String g = goal.getText().toString();
                finalMsg = finalMsg.append(a).append(comma).append(g).append(comma);
            }

        }
        goToResultActivity(finalMsg.toString(), v);
    }

    private void goToResultActivity(String msg, View v) {
        try {
            Intent myIntent = new Intent(v.getContext(), AspActivity.class);
            StringBuilder builder = new StringBuilder();
            List<String> parse = Arrays.asList(msg.split(","));
            String name = "";
            int i = 1;
            while(i < parse.size()) {
                if (parse.get(i).equals("Name")) {
                    name = parse.get(i + 1).toLowerCase();
                    builder.append("types(" + parse.get(i + 1).toLowerCase() + ").\n");
                    i += 2;
                } else if (parse.get(i).equals("Weekly")) {
                    if (parse.get(i + 2).equals("Goal")){
                        builder.append("weekly_task_length(" + name + "," + parse.get(i + 3) + ").\n");
                        i += 4;
                    }
                    else if (parse.get(i + 2).equals("Assigned")) {
                        char[] days = parse.get(i + 1).toCharArray();
                        int startTime = Integer.parseInt(parse.get(i + 3));
                        int endTime = Integer.parseInt(parse.get(i + 4));
                        int Length = endTime - startTime;
                        for (char c : days) {
                            builder.append("assigned(" + c + "," + name + "," + parse.get(i + 3) + "," + String.valueOf(Length) + ").\n");
                        }
                        i += 5;
                    }
                } else if (parse.get(i).equals("Daily")) {
                    if (parse.get(i + 1).equals("Assigned")) {
                        int startTime = Integer.parseInt(parse.get(i + 2));
                        int endTime = Integer.parseInt(parse.get(i + 3));
                        int Length = endTime - startTime;
                        builder.append("daily_assigned(" + name + "," + parse.get(i + 2) + "," + String.valueOf(Length) + ").\n");
                        i += 4;
                    } else {
                        builder.append("daily_task_length(" + name + "," + parse.get(i + 2) + ").\n");
                        i += 3;
                    }
                }
                else{
                    i++;
                }

            }
            builder.append("day(1).\n");
//            File aspcode = new File(v.getContext().getFilesDir(), "aspcode");
//            FileOutputStream fos = openFileOutput("aspcode", Context.MODE_PRIVATE);
            File f = new File(v.getContext().getFilesDir(), "dsshell");
            InputStream ins = getResources().openRawResource(
                    getResources().getIdentifier("dsshell",
                            "raw", getPackageName()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            ins.close();
            FileOutputStream fileout=openFileOutput("aspcode", Context.MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(builder.toString());
            outputWriter.close();


//            File test_file = new File(v.getContext().getFilesDir() + "/" + "aspcode");
//            FileInputStream fis = new FileInputStream(test_file);
//            InputStreamReader isr = new InputStreamReader(fis);
//            BufferedReader bufferedReader = new BufferedReader(isr);
//            StringBuilder sb = new StringBuilder();
//            String line2;
//            while ((line2 = bufferedReader.readLine()) != null) {
//                sb.append(line2);
//            }
            myIntent.putExtra("mytext", v.getContext().getFilesDir() + "/" + "aspcode");
            startActivity(myIntent);
        }
        catch(FileNotFoundException f){
            f.printStackTrace();;
        }
        catch (UnsupportedEncodingException u){
            u.printStackTrace();
        }
        catch(IOException i){
            i.printStackTrace();
        }
    }
}

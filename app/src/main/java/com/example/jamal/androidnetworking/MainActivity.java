package com.example.jamal.androidnetworking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button xmlParser , jsonParser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xmlParser=(Button) findViewById(R.id.xmlParser);
        jsonParser=(Button) findViewById(R.id.jsonParser);
        xmlParser.setOnClickListener(this);
        jsonParser.setOnClickListener(this);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return super.onRetainCustomNonConfigurationInstance();
    }

    @Override
    public Object getLastCustomNonConfigurationInstance() {
        return super.getLastCustomNonConfigurationInstance();
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.xmlParser :
                startActivity(new Intent(this,XMLParserActivity.class));
                break;
            case R.id.jsonParser :
        }
    }
}

package com.elitetechnocrats.dev.youtube;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home_Activity extends Activity implements View.OnClickListener {

    Button v1, v2, v3;
    String video1 = "fmHh4Hm7uNA", video2 = "dJpiGXKmFxI", video3 = "4khjDb40uLw";//video3="2Ur1yEv_yWw";;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        v1 = (Button) findViewById(R.id.video1);
        v2 = (Button) findViewById(R.id.video2);
        v3 = (Button) findViewById(R.id.video3);
        v1.setOnClickListener(this);
        v2.setOnClickListener(this);
        v3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, WebViewPlayer.class);
        if (v.getId() == R.id.video1) {
            intent.putExtra("Id", video1);
        } else if (v.getId() == R.id.video2) {
            intent.putExtra("Id", video2);
        } else if (v.getId() == R.id.video3) {
            intent.putExtra("Id", video3);
        }
        startActivity(intent);
    }
}

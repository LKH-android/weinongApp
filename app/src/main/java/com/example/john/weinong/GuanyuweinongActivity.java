package com.example.john.weinong;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by john on 2018/10/18.
 */

public class GuanyuweinongActivity extends AppCompatActivity {
    private ImageView backimageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guanyuweinong);
        backimageView=(ImageView)findViewById(R.id.guanyuweinongbiaotiimageview_person_back);
        backimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

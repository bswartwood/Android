package com.bds.cameraintenttutorial;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class BarcodeDetail extends AppCompatActivity {
    private ImageView barcodeImageView;
    private Button barcodeDoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.barcodeImageView = (ImageView) findViewById(R.id.barcodeImageView);
        this.barcodeDoneButton = (Button) findViewById(R.id.barcodeDoneButton);

        setImageViewPhoto(getIntent().getParcelableExtra(CameraIntentActivity.PHOTO_TAKEN));
        setButtonOnClickListener();
    }

    private void setButtonOnClickListener() {
        this.barcodeDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void setImageViewPhoto(Parcelable parcelablePhoto) {
        Bitmap photo = (Bitmap) parcelablePhoto;
        this.barcodeImageView.setImageBitmap(photo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_camera_intent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

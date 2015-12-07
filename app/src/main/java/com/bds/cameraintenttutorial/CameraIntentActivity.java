package com.bds.cameraintenttutorial;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class CameraIntentActivity extends AppCompatActivity {
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private static final int BARCODE_DETAIL_ACTIVITY = 1;
    public static final String PHOTO_TAKEN = "photoTaken";

    private ImageView capturedImageView;
    private Button takePhotoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_intent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.capturedImageView = (ImageView) findViewById(R.id.capturedImageView);
        this.takePhotoButton = (Button) findViewById(R.id.takePhotoButton);

    }

    public void takePhoto(View view) {
        //Toast.makeText(this, "Photo Taken", Toast.LENGTH_SHORT).show();
        Intent cameraAppIntent = new Intent();
        cameraAppIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraAppIntent, ACTIVITY_START_CAMERA_APP);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If we take a picture and get a successful return code
        if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {
            displayImageTaken(data);
            setButtonToLaunchBarcodeDetail(data);
        }

        // If BarcodeDetail is OK or Cancelled
        if (requestCode == BARCODE_DETAIL_ACTIVITY &&
                resultCode == RESULT_OK || resultCode == RESULT_CANCELED) {
            resetButtonTextAndClickListener();
            this.capturedImageView.setImageResource(android.R.color.transparent);
        }
    }

    private void resetButtonTextAndClickListener() {
        // Reset Button to launch image caputer
        this.takePhotoButton.setText("Take Photo");
        this.takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(v);
            }
        });
    }

    private void displayImageTaken(Intent data) {
        //Toast.makeText(this, "Picture Taken Successfully", Toast.LENGTH_SHORT).show();

        // Get the camera data
        Bundle extras = data.getExtras();
        final Bitmap photoCaputredBitmap = (Bitmap) extras.get("data");

        // Set the image view with the bitmap
        this.capturedImageView.setImageBitmap(photoCaputredBitmap);
    }

    private void setButtonToLaunchBarcodeDetail(Intent data) {
        // Change the title of the button
        this.takePhotoButton.setText("Next Screen");

        // Get the image bitmap to pass
        final Bitmap photoCaputredBitmap = (Bitmap) data.getExtras().get("data");

        // Set the button event to launch BarcodeDetail intent
        this.takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.bds.cameraintenttutorial.BarcodeDetail");
                // Add the image as an extra
                intent.putExtra(PHOTO_TAKEN, photoCaputredBitmap);
                startActivityForResult(intent, BARCODE_DETAIL_ACTIVITY);
            }
        });
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

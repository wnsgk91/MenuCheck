package com.example.user.drawer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class Camera extends AppCompatActivity {

    //OCR
    Bitmap image;
    private TessBaseAPI mTess;
    String datapath = "";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference food_name = database.getReference("food_detail");

    private static final int ACTION_TAKE_PHOTO_S = 2;

    private ImageView mImageView;
    private Bitmap mImageBitmap;

    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.camera);

        super.onCreate(savedInstanceState);

        datapath = getFilesDir()+"/tesseract/";
        checkFile(new File(datapath+"tessdata/"));
        String lang = "kor";

        mTess = new TessBaseAPI();
        mTess.init(datapath, lang);

        //camera
        mImageView = (ImageView) findViewById(R.id.imageview);
        mImageBitmap = null;


        Button picSBtn = (Button) findViewById(R.id.btnIntendS);
        setBtnLinstenrOrDisable(
                picSBtn,
                mTakePicSOnClickListener,
                MediaStore.ACTION_IMAGE_CAPTURE
        );

        Button button_show = (Button) findViewById(R.id.button);
        button_show.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                processImage(view);
            }
        });



        Button button1=(Button) findViewById(R.id.search);
        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText OCRTextView = (EditText) findViewById(R.id.OCRTextView);
                String OCRresult = OCRTextView.getText().toString();


                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/search?tbm=isch&source=hp&ei=w6QGXOGgGIXb8QXrxZ2gCQ&q=" +OCRresult+
                                "&oq="+OCRresult+"&gs_l=mobile-gws-wiz-img.3..0l5.5717.6565..6738...1.0..0.129.735.0j6......" +
                                "0....1.......0.fNepLKaW6Js#imgrc=cHdh5OaO7nAYbM"));
                startActivity(intent);

            }
        });

    }

    private void setimage(Intent intent){
        Bundle extras = intent.getExtras();
        image = (Bitmap) extras.get("data");
    }


    public static boolean isIntentAvailable(Context context, String action){
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private void setBtnLinstenrOrDisable(
            Button btn,
            Button.OnClickListener onClickListener,
            String intentName
    ){
        if (isIntentAvailable(this, intentName)){
            btn.setOnClickListener(onClickListener);
        }else{
            btn.setText(
                    getText(R.string.cannot).toString() +" " + btn.getText());
            btn.setClickable(false);
        }
    }

    Button.OnClickListener mTakePicSOnClickListener =
            new Button.OnClickListener(){

                @Override
                public void onClick(View v) {
                    dispatchTakePicutreIntent(ACTION_TAKE_PHOTO_S);
                }
            };


    private void dispatchTakePicutreIntent(int actionCode){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, actionCode);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){

            case ACTION_TAKE_PHOTO_S :{
                if(resultCode == RESULT_OK){
                    handleSmallCameraPhoto(data);
                    setimage(data);
                }
                break;
            }
        }
    }

    private void handleSmallCameraPhoto(Intent intent){
        Bundle extras = intent.getExtras();
        mImageBitmap = (Bitmap) extras.get("data");
        mImageView.setImageBitmap(mImageBitmap);
        mImageView.setVisibility(View.VISIBLE);
    }


    //OCR
    public void processImage(View view){
        String OCRresult = null;
        mTess.setImage(image);
        OCRresult = mTess.getUTF8Text();
        EditText OCRTextView = (EditText) findViewById(R.id.OCRTextView);
        OCRTextView.setText(OCRresult);

    }

    private void copyFiles(){
        try{
            String filepath = datapath + "/tessdata/kor.traineddata";
            AssetManager assetManager = getAssets();
            InputStream instream = assetManager.open("tessdata/kor.traineddata");
            OutputStream outstream = new FileOutputStream(filepath);
            byte[] buffer = new byte[1024];
            int read;
            while((read = instream.read(buffer))!=-1){
                outstream.write(buffer, 0, read);
            }
            outstream.flush();
            outstream.close();
            instream.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void checkFile(File dir){
        if(!dir.exists() && dir.mkdirs()){
            copyFiles();
        }

        if(dir.exists()){
            String datafilepath = datapath + "/tessdata/kor.traineddata";
            File datafile = new File(datafilepath);
            if(!datafile.exists()){
                copyFiles();
            }
        }
    }


}

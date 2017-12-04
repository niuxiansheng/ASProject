package com.example.myapplication;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.baselibrary.MyThreadPool;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.concurrent.ThreadPoolExecutor;

public class MainActivity extends AppCompatActivity {

    private static final String sImageUrl = "http://fashion.qqread.com/ArtImage/20110225/0083_13.jpg";
    private static final int MSG_LOAD_SUCCESS = 0;
    private static final int MSG_LOAD_FAILURE = 1;

    public Button mLoadButton;
    private ProgressDialog mProgressBar;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = new ProgressDialog(this);
        mProgressBar.setCancelable(true);
        mProgressBar.setMessage("Image downloading ...");
        mProgressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressBar.setMax(100);

        mImageView = (ImageView) findViewById(R.id.ImageView);
        mLoadButton = (Button) findViewById(R.id.LoadButton);

        mLoadButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mProgressBar.setProgress(0);
                mProgressBar.show();

                ThreadPoolExecutor threadPoolExecutor = MyThreadPool.instance().getThreadPoolExecutor();

                for (int i = 0; i < 5; i++) {
                    threadPoolExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println(Thread.currentThread().getName());
                        }
                    });
                }

                threadPoolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("Load thread", ">>run");
                        Bitmap bitmap = loadImageFromUrl(sImageUrl);
                        if (bitmap != null) {
                            Message msg = mHandler.obtainMessage(MSG_LOAD_SUCCESS, bitmap);
                            mHandler.sendMessage(msg);
                        }else {
                            Message msg = mHandler.obtainMessage(MSG_LOAD_FAILURE, null);
                            mHandler.sendMessage(msg);
                        }
                    }
                });
            }
        });
    }

    private Bitmap loadImageFromUrl(String uil) {
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(sImageUrl).openStream();
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_LOAD_SUCCESS:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    mImageView.setImageBitmap(bitmap);

                    mProgressBar.setProgress(100);
                    mProgressBar.setMessage("ImageDownLing success");
                    mProgressBar.dismiss();
                    break;
                case MSG_LOAD_FAILURE:
                    mProgressBar.setMessage("Image downloading failure !");
                    mProgressBar.dismiss();
                    break;
                default:
                    break;
            }
        }
    };
}

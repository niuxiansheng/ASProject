package com.example.myapplication4;

import android.content.Context;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button;
    private List<String> PathList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
//                getAllExterSDCardPath();
//                Log.d("","TTT_-->>> = " + PathList.toString());
                ArrayList list = (ArrayList) listAvaliableStorage(MainActivity.this);
                Log.d("", "TTT--->>> = " + list.toString());
                break;
        }
    }

    public List<String> getAllExterSDCardPath(){

        PathList = new ArrayList<>();
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("mount");
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine())!=null){
                Log.d("","TTT--->> = " + line);

                if (line.contains("proc") || line.contains("tmpfs") || line.contains("media") || line.contains("asec") || line.contains("secure") || line.contains("system") || line.contains("cache")
                        || line.contains("sys") || line.contains("data") || line.contains("shell") || line.contains("root") || line.contains("acct") || line.contains("misc") || line.contains("obb")) {
                    continue;
                }

                // 下面是可能的
                if (line.contains("fat") || line.contains("fuse") || line.contains("ntfs")) {
                    //将mount命令获取的列表分割，第二个为挂载路径
                    String items[] = line.split(" ");
                    if (items!=null&&items.length>1) {
                        String path = items[1].toLowerCase(Locale.getDefault());
                        // 具体判断，确保是SD卡
                        if (path != null && !PathList.contains(path) && path.contains("sd")) {
                            PathList.add(items[1]);
                        }
                    }
                }

            }

        } catch (IOException e){
            e.printStackTrace();
        }
        return PathList;
    }

    public static List listAvaliableStorage(Context context){
        ArrayList storagges = new ArrayList();
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            Class<?>[] paramClasses = {};
            Method getVolumeList = StorageManager.class.getMethod("getVolumeList", paramClasses);
            getVolumeList.setAccessible(true);
            Object[] params = {};
            Object[] invokes = (Object[]) getVolumeList.invoke(storageManager, params);
            if (invokes != null) {
                StorageInfo info = null;
                for (int i = 0; i < invokes.length; i++) {
                    Object obj = invokes[i];
                    Method getPath = obj.getClass().getMethod("getPath", new Class[0]);
                    String path = (String) getPath.invoke(obj, new Object[0]);
                    info = new StorageInfo(path);
                    File file = new File(info.path);
                    if (file.exists() && file.isDirectory()) {
                        Method isRemovable = obj.getClass().getMethod("isRemovable", new Class[0]);
                        String state = null;
                        try {
                            Method getVolumeState = StorageManager.class.getMethod("getVolumeState", String.class);
                            state = (String) getVolumeState.invoke(storageManager,info.path);
                            info.state = state;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (info.isMounted()) {
                            info.isRemoveable = (boolean) isRemovable.invoke(obj, new Objects[0]);
                            storagges.add(info);
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        storagges.trimToSize();
        return storagges;
    }

}

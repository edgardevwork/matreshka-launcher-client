package ru.edgar.launcher.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Build;

import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.*;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.content.Intent;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.animation.AnimationUtils;
import android.view.animation.Animation;

import kotlin.jvm.internal.Intrinsics;
import ru.edgar.matrp.Contacts;
import ru.edgar.matrp.R;
import ru.edgar.launcher.fragment.DownloadFragment;
import ru.edgar.launcher.fragment.MainFragment;
import ru.edgar.launcher.fragment.ServerSelectFragment;
import ru.edgar.launcher.fragment.SettingsFragment;
import ru.edgar.launcher.fragment.SplashFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.rpc.context.AttributeContext;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.units.qual.A;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    private static MainActivity instance;

    public SplashFragment splashFragment;
    public ServerSelectFragment serverSelectFragment;
    public SettingsFragment settingsFragment;
    public MainFragment mainFragment;
    public DownloadFragment downloadFragment;
    public FrameLayout front_ui_layout;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public int Auth = 28540117;
    public int AuthCheck = 7;
    private String courseName;
    public String text3, text4, text6;
    Context pone;
    Activity activity;
    Timer t;
    File file = new File(Environment.getExternalStorageDirectory() + "/Edgar/SAMP/localsettings.ini");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
		
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.button_click);

        instance = this;

        front_ui_layout = (FrameLayout) findViewById(R.id.front_ui_layout);
		
		splashFragment = new SplashFragment();
        serverSelectFragment = new ServerSelectFragment();
        settingsFragment = new SettingsFragment();
        mainFragment = new MainFragment();
        downloadFragment = new DownloadFragment();
        t = new Timer();

        /*ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {});
        launcher.launch(Manifest.permission.POST_NOTIFICATIONS);*/

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {
                startTimer();
                mHandler.postDelayed(new AnimClose(), 7800);
                Toast.makeText(getApplicationContext(), "Загрузка!", Toast.LENGTH_SHORT).show();
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NOTIFICATION_POLICY, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
            } else {
                startTimerSpeed();
                mHandler.postDelayed(new AnimClose(), 1800);
            }
        } else {
            startTimerSpeed();
            mHandler.postDelayed(new AnimClose(), 1800);
        }
        if (PermissionUtils.hasPermissions(this)) {
            try {
                //startTimer(); mHandler.postDelayed(new AnimClose(), 7800);
                overridePendingTransition(0, 0);
            } catch (Exception e) {

            }
        } else if (!PermissionUtils.hasPermissions(this)) {
            PermissionUtils.requestPermissions(this, 101);
        }

        //startTimer();

	    replaceFragment(splashFragment);
    }


    public class AnimClose implements Runnable {
        public AnimClose() {
        }

        public final void run() {
            SplashFragment.getSplash().j();
        }
    }
    public void Auhtch(String text, String text2, String text5)
    {
            text3 = text;
            text4 = text2;
            text6 = text5;
            if(text == null){
            }else {System.out.println(text);}
            if(text2 == null){
            }else {System.out.println(text2);}
            if(text5 == null){
            }else {System.out.println(text5);}
    }

    public static MainActivity getMainActivity() {
        return instance;
    }
	
	public void onClickPlay() {
        if(IsGameInstalled()) {
            t.cancel();
            startActivity(new Intent(getApplicationContext(), ru.edgar.matrp.core.GTASA.class));
		} else {
            t.cancel();
            if(DownloadFragment.nick == null) {
                Toast.makeText(getApplicationContext(), "Установите ник!", Toast.LENGTH_SHORT).show();
                replaceSettings();
            }else{
                replaceFragment(downloadFragment);
            }
		}
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.grow_fade_in_from_bottom, R.anim.mtrl_bottom_sheet_slide_out);
        beginTransaction.replace(R.id.front_ui_layout, fragment);
        beginTransaction.commit();
    }
    public void replaceServers() {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.fade_out);
        beginTransaction.replace(R.id.front_ui_layout, serverSelectFragment);
        beginTransaction.commit();
    }
    public void replaceSettings(){
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.fade_out);
        beginTransaction.replace(R.id.front_ui_layout, settingsFragment);
        beginTransaction.commit();
    }
    public void closeServers() {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.fade_in, R.anim.slide_out_bottom);
        beginTransaction.replace(R.id.front_ui_layout, mainFragment);
        beginTransaction.commit();
    }
	
	public boolean isRecordAudioPermissionGranted() {
        if (Build.VERSION.SDK_INT < 23 || checkSelfPermission("android.permission.RECORD_AUDIO") == 0) {
            return true;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.RECORD_AUDIO"}, 2);
        return false;
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT < 23 || checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            return true;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
        return false;
    }
    public void onRequestPermissionsResultBr(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            replaceFragment(mainFragment);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && Build.VERSION.SDK_INT >= 30 && !PermissionUtils.hasPermissions(this)) {
            Toast.makeText(getApplicationContext(), "Дайте разрешение!", Toast.LENGTH_SHORT).show();
        } else replaceFragment(mainFragment);
        super.onActivityResult(requestCode, resultCode, data);
    }
	
	private boolean IsGameInstalled()
    {
        String CheckFile = Environment.getExternalStorageDirectory() + "/Edgar/texdb/gta3.img";
        File file = new File(CheckFile);
        return file.exists();
    }

	private void startTimer()
    {
        t.schedule(new TimerTask(){
            @Override
            public void run() {
                try {
                    //Connect();
                    File theDir = new File(Environment.getExternalStorageDirectory() + "/Edgar/SAMP");
                    if (!theDir.exists()){
                       theDir.mkdirs();
                    }
                    if (file.exists()) {
                        //file.delete();
                    }else {
                        file.createNewFile();
                        FileWriter writer = new FileWriter (file);
                        writer.write("[server]\n");
                        writer.write("server = 0\n");
                        writer.write("name = 0\n");
                        writer.write("color = 0\n");
                        writer.write("maxonline = 0\n");
                        writer.write("online = 0\n");
                        writer.write("edgar_host = 0\n");
                        writer.write("edgar_port = 0\n");
                        writer.write("id = 0\n");
                        writer.close();
                        System.out.println("File is created!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                replaceFragment(mainFragment);
            }
        }, 8000L);
    }
    private void startTimerSpeed()
    {
        t.schedule(new TimerTask(){
            @Override
            public void run() {
                try {
                    File theDir = new File(Environment.getExternalStorageDirectory() + "/Edgar/SAMP");
                    if (!theDir.exists()){
                      theDir.mkdirs();
                    }
                    if (file.exists()) {
                        //file.delete();
                    }else {
                        file.createNewFile();
                        FileWriter writer = new FileWriter (file);
                        writer.write("[server]\n");
                        writer.write("server = 0\n");
                        writer.write("name = 0\n");
                        writer.write("color = 0\n");
                        writer.write("maxonline = 0\n");
                        writer.write("online = 0\n");
                        writer.write("edgar_host = 0\n");
                        writer.write("edgar_port = 0\n");
                        writer.write("id = 0\n");
                        writer.close();
                        System.out.println("File is created!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                replaceFragment(mainFragment);
            }
        }, 2000L);
    }

    public static class PermissionUtils {
        public static boolean hasPermissions(Context context) {
            if (Build.VERSION.SDK_INT >= 30) {
                return Environment.isExternalStorageManager();
            }
            if (Build.VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                return true;
            }
            return false;
        }

        public static void requestPermissions(Activity activity, int requestCode) {
            if (Build.VERSION.SDK_INT >= 30) {
                try {
                    Intent intent = new Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION");
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", new Object[]{activity.getPackageName()})));
                    activity.startActivityForResult(intent, requestCode);
                } catch (Exception e) {
                    Intent intent2 = new Intent();
                    intent2.setAction("android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION");
                    activity.startActivityForResult(intent2, requestCode);
                }
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, requestCode);
            }
        }
    }
    public void PermissionPon(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
            } else {
                Toast.makeText(this, "Дайте разрешение или вы не сможите пользоваться лаунчером!!!", Toast.LENGTH_SHORT).show();
                startTimer();
            }
        } else startTimer();
    }


	
	public void onDestroy() {
        super.onDestroy();
        
    }

    public void onRestart() {
        super.onRestart();
        
    }
	
	public boolean checkValidNick(){
		EditText nick = (EditText) findViewById(R.id.account_text);
		if(nick.getText().toString().isEmpty()) {
			Toast.makeText(this, "Введите ник", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(!(nick.getText().toString().contains("_"))){
			Toast.makeText(this, "Ник должен содержать символ \"_\"", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(nick.getText().toString().length() < 4){
			Toast.makeText(this, "Длина ника должна быть не менее 4 символов", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
} 
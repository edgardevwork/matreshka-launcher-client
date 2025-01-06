package ru.edgar.matrp.gui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import ru.edgar.matrp.R;
import ru.edgar.matrp.gui.util.Utils;
import com.nvidia.devtech.NvEventQueueActivity;

import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

public class ChooseServer {

    FrameLayout serverLayout;
    TextView percentText;
    Activity aactivity;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public TextView lm_status;
    int loading = 0;
    int type, size;
    String host;
    int port;
    int i = 0;
    //
    public ImageView[] loadingsImage;

    public int idImage;

    public boolean boolImage;

    public TimeSupport mTimeSupport;
    public TimeSupport TimeSupportt;

    public ChooseServer(Activity activity){
        aactivity = activity;
        serverLayout = activity.findViewById(R.id.edgar_loagin_kranin);
        type = NvEventQueueActivity.getInstance().getLastServer();
        try{
            Wini w = new Wini(new File(Environment.getExternalStorageDirectory() + "/Edgar/SAMP/localsettings.ini"));
            host = w.get("server", "edgar_host");
            port = Integer.parseInt(w.get("server", "edgar_port"));
            Log.e("блять", "вот чему равен порт" + port + "хост = "+ host);
        } catch (IOException e) {
            e.printStackTrace();
        }
        lm_status = (TextView) activity.findViewById(R.id.lm_status);
        //

        ImageView[] imageViewArr2 = new ImageView[5];
        loadingsImage = imageViewArr2;
        imageViewArr2[0] = (ImageView) activity.findViewById(R.id.lm_candle_1);
        loadingsImage[1] = (ImageView) activity.findViewById(R.id.lm_candle_2);
        loadingsImage[2] = (ImageView) activity.findViewById(R.id.lm_candle_3);
        loadingsImage[3] = (ImageView) activity.findViewById(R.id.lm_candle_4);
        loadingsImage[4] = (ImageView) activity.findViewById(R.id.lm_candle_5);
        idImage = 0;
        boolImage = false;
        TimeSupport ponE = new TimeSupport(0.0f, 1.0f);
        mTimeSupport = ponE;
        ponE.Long = 300;
        ponE.Update1();
        TimeSupport pon = new TimeSupport(0.0f, 1.0f);
        this.TimeSupportt = pon;
        pon.TimeC = new SetAlpha();
        pon.TimeB = new mTimeSupportB();
        pon.Long = 500;
        pon.Update1();

        Utils.HideLayout(serverLayout, false);
    }

    public void Update(int percent, int pon) {
        i = pon;
        if (percent <= 100) {
            lm_status.setText("Загрузка игры...");
        } else {
            lm_status.setText("Соединение к игре...");
            // TODO: Соединение к игре... BY EDGAR 3.0 EDGAR 3.0
        }
        if(i == 2){
            lm_status.setText("Соединено. Подготовка к игре...");
            mHandler.postDelayed(new Nv(), 500);
        }if (i == 3){
            lm_status.setText("Сервер полон. Переподключение к игре...");
        }if (i == 4){
            lm_status.setText("Проблемы с сетью, переподключение...");
        }if (i == 1){
            NvEventQueueActivity.getInstance().EdgarConnect(host, port);
        }
    }



    public class SetAlpha implements TimeSupport.TimeSup {
        public SetAlpha() {
        }

        public final void a(TimeSupport Time) {
            loadingsImage[idImage].setAlpha(Time.Alpha4());
        }
    }

    public class mTimeSupportB implements TimeSupport.b {
        public mTimeSupportB() {
        }

        public final void a() {
            int i10 = boolImage ? idImage - 1 : idImage + 1;
            idImage = i10;
            if (i10 == -1) {
                boolImage = false;
                TimeSupport pon = TimeSupportt;
                pon.Alpha2 = 1.0f;
                idImage = 0;
            }
            if (idImage == 5) {
                boolImage = true;
                TimeSupport pon2 = TimeSupportt;
                pon2.Alpha3 = 1.0f;
                pon2.Alpha2 = 0.0f;
                idImage = 4;
            }
            TimeSupportt.Update1();
        }

        public final void b() {
        }
    }


    public class Nv implements Runnable {
        public Nv() {
        }

        public final void run() {
            serverLayout.animate().setDuration(400).setListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    serverLayout.setVisibility(View.GONE);
                    super.onAnimationEnd(animation);
                }
            }).alpha(0.0f);
        }
    }


    public static final class TimeSupport {

        public float Alpha3;

        public float Alpha2;
        public long Long;

        public float Alpha1;

        public long longL;

        public boolean mBool;
        public TimeSup TimeC;

        public TimeSupport.b TimeB;

        public TimeInterpolator mTimeInterpolator;
        public Handler mHandler = new Handler();

        public class Update2m implements Runnable {
            public Update2m() {
            }

            public final void run() {
                TimeSupport.this.Update2();
            }
        }

        public interface b {
            void a();

            void b();
        }

        public interface TimeSup {
            void a(TimeSupport eVar);
        }

        public TimeSupport(float f10, float f11) {
            this.Alpha3 = f10;
            this.Alpha2 = f11;
            this.Long = 1000;
            this.Alpha1 = 0.0f;
            this.mBool = false;
            this.mTimeInterpolator = new LinearInterpolator();
        }


        public float Alpha4() {
            float f10 = this.Alpha3;
            return ep(this.Alpha2, this.Alpha3, this.mTimeInterpolator.getInterpolation(this.Alpha1), f10);
        }
        public float ep(float f10, float f11, float f12, float f13) {
            return ((f10 - f11) * f12) + f13;
        }

        public void c() {
            TimeSupport.b bVar = this.TimeB;
            if (bVar != null) {
                bVar.a();
            }
        }

        public final void Update1() {
            if (!this.mBool) {
                this.longL = System.currentTimeMillis();
                this.mBool = true;
                TimeSupport.b TimeB = this.TimeB;
                if (TimeB != null) {
                    TimeB.b();
                }
                Update2();
            }
        }

        public final void Update2() {
            if (this.mBool) {
                float currentTimeMillis = ((float) (System.currentTimeMillis() - this.longL)) / ((float) this.Long);
                if (currentTimeMillis >= 1.0f) {
                    this.Alpha1 = 1.0f;
                    this.mBool = false;
                } else {
                    this.Alpha1 = currentTimeMillis;
                    this.mHandler.post(new Update2m());
                }
                TimeSup TimeCSupport = this.TimeC;
                if (TimeCSupport != null) {
                    TimeCSupport.a(this);
                }
                if (!this.mBool) {
                    c();
                }
            }
        }
    }


    public void Show() {
        Utils.ShowLayout(serverLayout, false);
    }

}
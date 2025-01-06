package ru.edgar.launcher.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ru.edgar.matrp.R;
import ru.edgar.matrp.gui.HudManager;
import ru.edgar.launcher.activity.MainActivity;
import ru.edgar.launcher.adapter.NewsAdapter;
import ru.edgar.launcher.model.News;

import ru.edgar.launcher.adapter.ServersAdapter;
import ru.edgar.launcher.model.Servers;

import ru.edgar.launcher.other.Lists;
import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.firebase.database.*;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

public class MainFragment extends Fragment {

    ConstraintLayout select_server_layout;

	int pon, lite;
	String name;
	String hex;
	int max;
	String online;
	int id;
    //
	String name_client;

	public ImageView settings;

	TextView textserver, name_launcher, serverinfo_onlinee;
	CircularProgressBar progressBar;
	ImageView server_background;

	RecyclerView recyclerNews;
	DatabaseReference databaseNews;
	NewsAdapter newsAdapter;
	ArrayList<News> nlist;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.fragment_main, container, false);
    	Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.button_click);

		select_server_layout = inflate.findViewById(R.id.select_server_layout);
		textserver = inflate.findViewById(R.id.serverinfo_name);
		progressBar = inflate.findViewById(R.id.serverinfo_online_bar);
		server_background = inflate.findViewById(R.id.server_background);
		name_launcher = inflate.findViewById(R.id.serverinfo_person_name);
		serverinfo_onlinee = inflate.findViewById(R.id.serverinfo_online);

		try{
			if(IsGameInstalled()) {
				Wini client = new Wini(new File(Environment.getExternalStorageDirectory() + "/Edgar/SAMP/settings.ini"));
				//
				Wini w = new Wini(new File(Environment.getExternalStorageDirectory() + "/Edgar/SAMP/localsettings.ini"));
				pon = Integer.parseInt(w.get("server", "server"));
				name = w.get("server", "name");
				hex = w.get("server", "color");
				max = Integer.parseInt(w.get("server", "maxonline"));
				online = w.get("server", "online");
				lite = Integer.parseInt(w.get("server", "online"));
				id = Integer.parseInt(w.get("server", "id"));
				//
				name_client = client.get("client", "name");
				w.store();
				//
				if(pon == 0){
					inflate.findViewById(R.id.serverinfo_layout).setVisibility(8);
					inflate.findViewById(R.id.bonus_layout).setVisibility(8);
					inflate.findViewById(R.id.select_layout).setVisibility(0);
				}else{
					inflate.findViewById(R.id.serverinfo_layout).setVisibility(0);
					inflate.findViewById(R.id.bonus_layout).setVisibility(0);
					inflate.findViewById(R.id.select_layout).setVisibility(8);
					name_launcher.setText(name_client);
					textserver.setText(name);
					progressBar.setProgressBarColor(Color.parseColor("#" + hex));
					progressBar.setProgressBarColorEnd(Color.parseColor("#" + hex));
					progressBar.setProgress(lite);
					progressBar.setProgressMax(max);
					server_background.setColorFilter(Color.parseColor("#" + hex), PorterDuff.Mode.SRC_ATOP);
					serverinfo_onlinee.setText(online);
				}
				client.store();


			}else{
				Wini w = new Wini(new File(Environment.getExternalStorageDirectory() + "/Edgar/SAMP/localsettings.ini"));
				pon = Integer.parseInt(w.get("server", "server"));
				name = w.get("server", "name");
				hex = w.get("server", "color");
				max = Integer.parseInt(w.get("server", "maxonline"));
				online = w.get("server", "online");
				lite = Integer.parseInt(w.get("server", "online"));
				id = Integer.parseInt(w.get("server", "id"));
				w.store();
				Log.e("бля", "pon ==== " + pon);
				if(pon == 0){
					inflate.findViewById(R.id.serverinfo_layout).setVisibility(8);
					inflate.findViewById(R.id.bonus_layout).setVisibility(8);
					inflate.findViewById(R.id.select_layout).setVisibility(0);
				}else{
					inflate.findViewById(R.id.serverinfo_layout).setVisibility(0);
					inflate.findViewById(R.id.bonus_layout).setVisibility(0);
					inflate.findViewById(R.id.select_layout).setVisibility(8);
					name_launcher.setText("Установите игру");
					textserver.setText(name);
					progressBar.setProgressBarColor(Color.parseColor("#" + hex));
					progressBar.setProgressBarColorEnd(Color.parseColor("#" + hex));
					progressBar.setProgress(lite);
					progressBar.setProgressMax(max);
					server_background.setColorFilter(Color.parseColor("#" + hex), PorterDuff.Mode.SRC_ATOP);
					serverinfo_onlinee.setText(online);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		select_server_layout.setOnClickListener(view -> {
			select_server_layout.startAnimation(animation);
			MainActivity.getMainActivity().replaceServers();
		});

		settings = inflate.findViewById(R.id.btn_settings);

		settings.setOnClickListener(v ->{
			v.startAnimation(animation);
			MainActivity.getMainActivity().replaceSettings();
		});

		inflate.findViewById(R.id.btn_play).setOnClickListener(v -> {
			v.startAnimation(animation);
			if(pon == 1)
			{
				MainActivity.getMainActivity().onClickPlay();
			}else{
				Toast.makeText(getContext(), "Выберите сервер!", Toast.LENGTH_SHORT).show();
			}

		});

		inflate.findViewById(R.id.btn_bonus).setOnClickListener(v -> {
			v.startAnimation(animation);
			startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://t.me/edgar_sliv")));

		});
		 inflate.findViewById(R.id.btn_cabinet).setOnClickListener(v -> {
			 v.startAnimation(animation);
			 startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://t.me/edgar_sliv")));
		 });

		recyclerNews = inflate.findViewById(R.id.story_recycler);
		recyclerNews.setHasFixedSize(true);
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
		recyclerNews.setLayoutManager(layoutManager);
		
		this.nlist = Lists.nlist;
		newsAdapter = new NewsAdapter(getContext(), this.nlist);
		recyclerNews.setAdapter(newsAdapter);

        return inflate;
    }
	private boolean IsGameInstalled()
	{
		String CheckFile = Environment.getExternalStorageDirectory() + "/Edgar/texdb/gta3.img";
		File file = new File(CheckFile);
		return file.exists();
	}


}
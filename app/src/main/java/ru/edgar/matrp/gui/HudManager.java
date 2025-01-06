package ru.edgar.matrp.gui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.nvidia.devtech.NvEventQueueActivity;

import ru.edgar.launcher.activity.MainActivity;
import ru.edgar.matrp.R;
import ru.edgar.matrp.gui.util.Utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Formatter;

public class HudManager {
    public Activity activity;
    public FrameLayout hud_layout, btn_shop;
    public TextView hud_money, quest_btn_optional, edgar;
    public LinearLayout hud_layou, quest_layout, quest_btn_hide;

    public FrameLayout btn_quest, btn_bp, btn_1;

    public ImageView hud_weapon;

    public ProgressBar progressHP;
    int q = 0;

    public HudManager(Activity aactivity) {
        activity = aactivity;
        hud_layout = aactivity.findViewById(R.id.hud_main);
        hud_layou = aactivity.findViewById(R.id.hud_layout);

        progressHP = aactivity.findViewById(R.id.stat_progress);

        edgar = aactivity.findViewById(R.id.quest_text);
        //edgar.setText("мое авторство)");

        hud_money = aactivity.findViewById(R.id.money_text);
        quest_btn_optional = aactivity.findViewById(R.id.quest_btn_optional);
        quest_btn_hide = aactivity.findViewById(R.id.quest_btn_hide);
        hud_weapon = aactivity.findViewById(R.id.weapon_melee_image);

        btn_quest = aactivity.findViewById(R.id.btn_quest);
        quest_layout = aactivity.findViewById(R.id.quest_layout);
        btn_shop = aactivity.findViewById(R.id.btn_shop);
        btn_bp = aactivity.findViewById(R.id.btn_bp);
        btn_1 = aactivity.findViewById(R.id.btn_1);

        btn_shop.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
            NvEventQueueActivity.getInstance().sendCommand("/mm".getBytes(StandardCharsets.UTF_8));

        });

        btn_bp.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
            NvEventQueueActivity.getInstance().sendCommand("/mm".getBytes(StandardCharsets.UTF_8));
        });

        btn_1.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
            NvEventQueueActivity.getInstance().sendCommand("/mm".getBytes(StandardCharsets.UTF_8));
            //NvEventQueueActivity.getInstance().togglePlayer(1);
        });

        quest_btn_hide.setOnClickListener(v -> {
            hud_layou.setVisibility(View.VISIBLE);
            quest_layout.setVisibility(View.GONE);
        });

        quest_btn_optional.setOnClickListener(v -> {
            hud_layou.setVisibility(View.VISIBLE);
            quest_layout.setVisibility(View.GONE);
        });

        btn_quest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hud_layou.setVisibility(View.GONE);
                quest_layout.setVisibility(View.VISIBLE);
            }
        });

        Utils.HideLayout(hud_layout, true);
    }

    public void UpdateHudInfo(int health, int armour, int hunger, int weaponidweik, int ammo, int playerid, int money, int wanted)
    {
        progressHP.setProgress(health);

        DecimalFormat formatter=new DecimalFormat();
        DecimalFormatSymbols symbols= DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator(' ');
        formatter.setDecimalFormatSymbols(symbols);
        String s= formatter.format(money);
        hud_money.setText(s);

        int id = activity.getResources().getIdentifier(new Formatter().format("weapon_%d", Integer.valueOf(weaponidweik)).toString(), "drawable", activity.getPackageName());
        hud_weapon.setImageResource(id);

        hud_weapon.setOnClickListener(v -> NvEventQueueActivity.getInstance().onWeaponChanged());

    }

    public void ShowGps()
    {

    }

    public void HideGps()
    {

    }

    public void ShowX2()
    {

    }

    public void HideX2()
    {

    }

    public void ShowZona()
    {

    }

    public void HideZona()
    {

    }

    public void ShowRadar()
    {

    }

    public void HideRadar()
    {

    }

    public void ShowHud()
    {
        Utils.ShowLayout(hud_layout, true);
        //Utils.ShowLayout(hud_micro, false);
    }

    public void HideHud()
    {
        Utils.HideLayout(hud_layout, true);
        //Utils.HideLayout(hud_micro, false);
    }

}

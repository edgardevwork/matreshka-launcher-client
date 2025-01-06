package ru.edgar.launcher.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import ru.edgar.matrp.R;
import ru.edgar.launcher.activity.MainActivity;

import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class SettingsFragment extends Fragment {

    Animation animation;
    public EditText nickname;
    String nickName;
    TextView faq_text, account_not_auth_text;
    Timer i;
    public static SettingsFragment in;

    public static SettingsFragment getInSettings(){return in;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_settings, container, false);
        in = this;
        i = new Timer();

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.button_click);

        inflate.findViewById(R.id.btn_close).setOnClickListener(v -> {
            v.startAnimation(animation);
            //i.cancel();
            MainActivity.getMainActivity().closeServers();
        });

        nickname = (EditText) inflate.findViewById(R.id.account_text);
        faq_text = (TextView) inflate.findViewById(R.id.faq_text);
        account_not_auth_text = (TextView) inflate.findViewById(R.id.account_not_auth_text);

        faq_text.setText("                          " + "\nПроблемы? Мы можем вам помочь!");
        //account_not_auth_text.setText("Укажите ник в окно ниже");

        InitLogic();

        ((FrameLayout) inflate.findViewById(R.id.btn_reinstall_data))
                .setOnClickListener(
                        new View.OnClickListener() {
                            public void onClick(View v) {
                                v.startAnimation(animation);
                                Toast.makeText(v.getContext(),"Временно недоступно by EDGAR 3.0", Toast.LENGTH_SHORT).show();
                               /* File gameDirectory = (new File(Environment.getExternalStorageDirectory() + "/Edgar"));
                                Utils.delete(gameDirectory);*/
                                //startActivity(new Intent(getActivity(), LoaderActivity.class));
                            }
                        });
        ((FrameLayout) inflate.findViewById(R.id.btn_reinstall_client))
                .setOnClickListener(
                        new View.OnClickListener() {
                            public void onClick(View v) {
                                v.startAnimation(animation);
                                Toast.makeText(v.getContext(),"Временно недоступно by EDGAR 3.0", Toast.LENGTH_SHORT).show();
                            }
                        });

        /*((ImageView) inflate.findViewById(R.id.instagramButton))
                .setOnClickListener(
                        new View.OnClickListener() {
                            public void onClick(View v) {
                                v.startAnimation(animation);
                                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("")));
                            }
        });*/

        ((EditText) nickname)
                .setOnEditorActionListener(
                        new EditText.OnEditorActionListener() {
                            @Override
                            public boolean onEditorAction(
                                    TextView v, int actionId, KeyEvent event) {
                                if (actionId == EditorInfo.IME_ACTION_SEARCH
                                        || actionId == EditorInfo.IME_ACTION_DONE
                                        || event.getAction() == KeyEvent.ACTION_DOWN
                                                && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                                    try {
                                        File f =
                                                new File(
                                                        Environment.getExternalStorageDirectory()
                                                                + "/Edgar/SAMP/settings.ini");
                                        if (!f.exists()) {
                                            f.createNewFile();
                                            f.mkdirs();

                                        }
                                        Wini w =
                                                new Wini(
                                                        new File(
                                                                Environment.getExternalStorageDirectory()
                                 + "/Edgar/SAMP/settings.ini"));
								 if(checkValidNick(inflate)){
									 w.put("client", "name", nickname.getText().toString());
                                        Toast.makeText(
                                                getActivity(),
                                                "Ваш новый никнейм успешно сохранен!",
                                                Toast.LENGTH_SHORT).show();
                                     DownloadFragment.nick = nickname.getText().toString();
								 } else {
									 checkValidNick(inflate);
								 }
                                        w.store();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        DownloadFragment.nick = nickname.getText().toString();
										//Toast.makeText(getActivity(), "Установите игру!", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(
                                                getActivity(),
                                                "Ваш новый никнейм успешно сохранен!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                                return false;
                            }
        });
        return inflate;
    }

    private void InitLogic() {
        try {
            Wini w = new Wini(new File(Environment.getExternalStorageDirectory() + "/Edgar/SAMP/settings.ini"));
            nickname.setText(w.get("client", "name"));
            w.store();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public boolean checkValidNick(View inflate){
		EditText nick = (EditText) inflate.findViewById(R.id.account_text);
		if(nick.getText().toString().isEmpty()) {
			Toast.makeText(getActivity(), "Введите ник", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(!(nick.getText().toString().contains("_"))){
			Toast.makeText(getActivity(), "Ник должен содержать символ \"_\"", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(nick.getText().toString().length() < 4){
			Toast.makeText(getActivity(), "Длина ника должна быть не менее 4 символов", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
}
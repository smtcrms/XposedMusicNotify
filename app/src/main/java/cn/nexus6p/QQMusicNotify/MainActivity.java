package cn.nexus6p.QQMusicNotify;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Keep;

@Keep
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

}
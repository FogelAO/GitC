package makarglavanar.com.github.gitc.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

public class UserActivity extends Activity {

	private SharedPreferences sharedPreferences;
	public static final String TAG = UserActivity.class.getSimpleName();
	public static final String PREFERENCE = "github_prefs";

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
		super.onCreate(savedInstanceState, persistentState);
	}
}

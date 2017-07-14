package makarglavanar.com.github.gitc.libs.git_auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Manage access token and user name. Uses shared preferences to store access
 * token and user name.
 *
 * @author Thiago Locatelli <thiago.locatelli@gmail.com>
 * @author Lorensius W. L T <lorenz@londatiga.net>
 */
public class GithubSession {

	private SharedPreferences sharedPref;
	private Editor editor;

	public static final String SHARED = "Github_Preferences";
	public static final String API_USERNAME = "username";
	public static final String API_ID = "id";
	public static final String API_ACCESS_TOKEN = "access_token";

	public GithubSession(Context context) {
		sharedPref = context.getSharedPreferences(SHARED, Context.MODE_PRIVATE);
	}

	public void storeAccessToken(String accessToken, String id, String username) {
		editor = sharedPref.edit();
		editor.putString(API_ID, id);
		editor.putString(API_ACCESS_TOKEN, accessToken);
		editor.putString(API_USERNAME, username);
		editor.commit();
	}

	public void storeAccessToken(String accessToken) {
		editor = sharedPref.edit();
		editor.putString(API_ACCESS_TOKEN, accessToken);
		editor.commit();
	}

	/**
	 * Reset access token and user name
	 */
	public void resetAccessToken() {
		editor = sharedPref.edit();
		editor.putString(API_ID, null);
		editor.putString(API_ACCESS_TOKEN, null);
		editor.putString(API_USERNAME, null);
		editor.commit();
	}

	/**
	 * Get user name
	 *
	 * @return Username
	 */
	public String getUsername() {
		return sharedPref.getString(API_USERNAME, null);
	}

	/**
	 * Get access token
	 *
	 * @return Access token
	 */
	public String getAccessToken() {
		return sharedPref.getString(API_ACCESS_TOKEN, null);
	}

}
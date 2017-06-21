package makarglavanar.com.github.gitc.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.hardikgoswami.oauthLibGithub.GithubOauth
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_loign.*
import makarglavanar.com.github.gitc.BuildConfig
import makarglavanar.com.github.gitc.R

class LoginActivity : AppCompatActivity() {
    val GITHUB_ID: String = BuildConfig.GITHUB_ID
    val GITHUB_SECRET: String = BuildConfig.GITHUB_SECRET

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loign)

        val packageN = packageName
        val mainActivity = "$packageN.ui.${MainActivity.TAG}"

        Log.d("TAG", packageN)
        Log.d("TAG", mainActivity)

        RxView.clicks(buttonLogin)
                .subscribe {
                    GithubOauth
                            .Builder()
                            .withClientId(GITHUB_ID)
                            .withClientSecret(GITHUB_SECRET)
                            .withContext(applicationContext)
                            .withScopeList(ArrayList())
                            .packageName(packageN)
                            .nextActivity(mainActivity)
                            .debug(true)
                            .execute()
                }

        RxView.clicks(buttonSkip)
                .subscribe {
                    start()
                }
    }

    private fun start() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
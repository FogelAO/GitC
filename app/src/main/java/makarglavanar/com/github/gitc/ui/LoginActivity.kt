package makarglavanar.com.github.gitc.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.crashlytics.android.Crashlytics
import com.jakewharton.rxbinding2.view.RxView
import io.fabric.sdk.android.Fabric
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_loign.*
import makarglavanar.com.github.gitc.BuildConfig
import makarglavanar.com.github.gitc.R
import makarglavanar.com.github.gitc.libs.git_auth.GithubApp
import makarglavanar.com.github.gitc.libs.git_auth.GithubSession
import makarglavanar.com.github.gitc.toast

class LoginActivity : AppCompatActivity() {
    private val CLIENT_ID: String = BuildConfig.CLIENT_ID
    private val CLIENT_SECRET: String = BuildConfig.CLIENT_SECRET
    private val CALLBACK_URL: String = BuildConfig.CALLBACK_URL

    private val subscriptions = CompositeDisposable()
    private lateinit var app: GithubApp
    private lateinit var preferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        setContentView(R.layout.activity_loign)
        preferences = getSharedPreferences(GithubSession.SHARED, Context.MODE_PRIVATE)

        app = GithubApp(
                this,
                CLIENT_ID,
                CLIENT_SECRET,
                CALLBACK_URL)

        app.setListener(listener)

        if (app.hasAccessToken()) {
            start()
        }

        subscriptions.add(
                RxView.clicks(buttonLogin)
                        .subscribe {
                            if (app.hasAccessToken()) {
                                val builder = AlertDialog.Builder(this@LoginActivity)
                                builder.setMessage("Disconnect from Github?")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes", { _, _ ->
                                            app.resetAccessToken()
                                            buttonLogin.text = "Disconnected"
                                        })
                                        .setNegativeButton("No", { dialog, _ ->
                                            dialog.cancel()
                                        })
                                val alert = builder.create()
                                alert.show()
                            } else {
                                app.authorize()
                            }
                        })

        subscriptions.add(
                RxView.clicks(buttonSkip)
                        .subscribe {
                            start()
                        })
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.dispose()
    }

    private fun start() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private val listener: GithubApp.OAuthAuthenticationListener = object : GithubApp.OAuthAuthenticationListener {

        override fun onSuccess() {
            toast("Success")
            start()
        }

        override fun onFail(error: String) {
            toast(error)
        }
    }
}
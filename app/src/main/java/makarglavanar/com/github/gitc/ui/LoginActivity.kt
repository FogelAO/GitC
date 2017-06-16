package makarglavanar.com.github.gitc.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_loign.*
import makarglavanar.com.github.gitc.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loign)

//        RxTextView
//                .textChanges(loginEditText)
//                .filter({ text -> text.length > 1 })
//                .subscribe(buttonLogin.e)

        RxView.clicks(buttonLogin)
                .subscribe({ start() })
    }


    private fun check(): Boolean {
        return loginEditText.text.trim().length > 1 &&
                passwordEditText.text.trim().length > 1
    }

    private fun start() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("login", loginEditText.text)
        startActivity(intent)
    }
}
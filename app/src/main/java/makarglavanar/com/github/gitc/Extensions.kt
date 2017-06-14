package makarglavanar.com.github.gitc

import android.content.Context
import android.support.v4.app.Fragment
import android.widget.Toast


fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Fragment.toast(text: String) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}
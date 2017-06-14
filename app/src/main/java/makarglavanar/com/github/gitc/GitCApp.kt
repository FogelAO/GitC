package makarglavanar.com.github.gitc

import android.app.Application


class GitCApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        val appComponent: AppComponent = DaggerAppComponent.create()
    }
}

package makarglavanar.com.github.gitc

import android.app.Application
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import makarglavanar.com.github.gitc.web.GitHubService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class GitCApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
       val gitService: GitHubService = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(GitHubService::class.java)
    }
}

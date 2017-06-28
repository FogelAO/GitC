package makarglavanar.com.github.gitc.web

import android.support.annotation.NonNull
import io.reactivex.Single
import makarglavanar.com.github.gitc.BuildConfig
import makarglavanar.com.github.gitc.entities.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GitHubService {

    @NonNull
    @GET("users/{login}")
    fun getUserByUserLogin(@Path("login") login: String, @Query("client_id") clientId: String, @Query("client_secret") clientSecret: String): Single<User>

    @GET("search/users?client_id=$CLIENT_ID&client_secret=$CLIENT_SECRET")
    fun getUsersByName(@Query("q") name: String): Single<UsersResponse>

    @GET("search/repositories?client_id=$CLIENT_ID&client_secret=$CLIENT_SECRET")
    fun getReposByName(@Query("q") name: String): Single<RepositoriesResponse>

    @GET("users/{user}/repos?client_id=$CLIENT_ID&client_secret=$CLIENT_SECRET")
    fun getUserRepos(@Path("user") login: String): Single<List<Repository>?>

    @GET("repos/{url}?client_id=$CLIENT_ID&client_secret=$CLIENT_SECRET")
    fun getRepoByUrl(@Path(value = "url", encoded = true) url: String): Single<Repository>

    @GET("search/issues?client_id=$CLIENT_ID&client_secret=$CLIENT_SECRET")
    fun getIssuesByTerms(@Query("q") terms: String): Single<IssuesResponse>

    @GET("repos/{url}?client_id=$CLIENT_ID&client_secret=$CLIENT_SECRET")
    fun getIssueByUrl(@Path(value = "url", encoded = true) url: String): Single<Issue>

    @GET("repos/{url}?client_id=$CLIENT_ID&client_secret=$CLIENT_SECRET")
    fun getIssueCommentsByUrl(@Path(value = "url", encoded = true) url: String): Single<List<IssueComment>?>

    @GET("repos/{login}/{repo}/contents/{path}?client_id=$CLIENT_ID&client_secret=$CLIENT_SECRET")
    fun getRepoFiles(@Path("login") name: String, @Path("repo") repo: String, @Path("path") path: String): Single<List<File>>

    @GET("repos/{url}?ref=master")
    fun getFileByUrl(@Path(value = "url", encoded = true) url: String): Single<File>

    @GET("users/{login}/followers")
    fun getUserFollowers(@Path("login") login: String): Single<List<User>>

    companion object {
        const val CLIENT_ID = BuildConfig.CLIENT_ID
        const val CLIENT_SECRET = BuildConfig.CLIENT_SECRET
    }
}
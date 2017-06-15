package makarglavanar.com.github.gitc.web

import android.support.annotation.NonNull
import io.reactivex.Single
import makarglavanar.com.github.gitc.entities.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GitHubService {

    @NonNull
    @GET("users/{login}")
    fun getUserByUserLogin(@Path("login") login: String): Single<User>

    @GET("search/users")
    fun getUsersByName(@Query("q") name: String): Single<UsersResponse>

    @GET("search/repositories")
    fun getReposByName(@Query("q") name: String): Single<RepositoriesResponse>

    @GET("repos/{url}")
    fun getRepoByUrl(@Path(value = "url", encoded = true) url: String): Single<Repository>

    @GET("search/issues")
    fun getIssuesByTerms(@Query("q") terms: String): Single<IssuesResponse>

    @GET("repos/{url}")
    fun getIssueByUrl(@Path(value = "url", encoded = true) url: String): Single<Issue>

    @GET("repos/{url}")
    fun getIssueCommentsByUrl(@Path(value = "url", encoded = true) urk: String): Single<IssueCommentsResponse>
}
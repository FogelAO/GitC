package makarglavanar.com.github.gitc.web

import android.support.annotation.NonNull
import io.reactivex.Single
import makarglavanar.com.github.gitc.entities.IssuesResponse
import makarglavanar.com.github.gitc.entities.RepositoriesResponse
import makarglavanar.com.github.gitc.entities.User
import makarglavanar.com.github.gitc.entities.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GitHubService {

    @NonNull
    @GET("users/{login}")
    fun getUserByUserLogin(@Path("login") login: String): Single<User>

//    @GET("users/{user}")
//    abstract fun getUsersByName(@Path("user") name: String): Single<User>

    @GET("search/users")
    fun getUsersByName(@Query("q") name: String): Single<UsersResponse>

    @GET("search/repositories")
    fun getReposByName(@Query("q") name: String): Single<RepositoriesResponse>

    @GET("search/issues")
    fun getIssuesByTerms(@Query("q") terms: String): Single<IssuesResponse>
}
package makarglavanar.com.github.gitc.ui.repos.user_repos

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.fragment_repos.*
import makarglavanar.com.github.gitc.GitCApp
import makarglavanar.com.github.gitc.R
import makarglavanar.com.github.gitc.entities.Repository
import makarglavanar.com.github.gitc.entities.User
import makarglavanar.com.github.gitc.toast
import makarglavanar.com.github.gitc.ui.repos.ReposAdapter
import makarglavanar.com.github.gitc.ui.repos.ReposAdapter.OnRepositoryClickListener
import makarglavanar.com.github.gitc.ui.repos.repo_info.RepositoryInfoActivity
import makarglavanar.com.github.gitc.ui.repos.user_repos.UserReposScreenCotract.Presenter
import makarglavanar.com.github.gitc.ui.repos.user_repos.UserReposScreenCotract.View
import makarglavanar.com.github.gitc.web.GitHubService
import javax.inject.Inject


class UserReposActivity : AppCompatActivity(), View, OnRepositoryClickListener {
    private lateinit var presenter: Presenter
    @Inject lateinit var gitHubService: GitHubService
    private lateinit var user: User
    lateinit var adapter: ReposAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_repos)
        GitCApp.appComponent.inject(this)
        presenter = UserReposPresenter(this, gitHubService)
        user = intent.getSerializableExtra("user") as User

        val layoutManager = LinearLayoutManager(this)
        val decorator = DividerItemDecoration(this, layoutManager.orientation)
        reposListView.layoutManager = layoutManager
        reposListView.addItemDecoration(decorator)
        adapter = ReposAdapter(this)
        reposListView.adapter = adapter

        presenter.loadRepos(user.login)
    }


    override fun showError(t: Throwable) {
        Log.w(TAG, "Loading repositories error", t)
        toast("Error occurred while loading repos")

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.deattach()
    }

    override fun showRepositories(repos: List<Repository>?) {
        if (repos != null) {
            adapter.add(repos)
        }
    }

    override fun onClick(repository: Repository) {
        RepositoryInfoActivity.Companion.startReposInfoActivity(this, repository)
//        val intent = Intent(this, RepositoryInfoActivity::class.java)
//        intent.putExtra("repo", repository)
//        startActivity(intent)
    }

    companion object {
        val TAG: String = UserReposActivity::class.java.simpleName
    }

}
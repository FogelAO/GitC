package makarglavanar.com.github.gitc.ui.repos.repo_info

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.activity_repo_info.*
import makarglavanar.com.github.gitc.GitCApp
import makarglavanar.com.github.gitc.R
import makarglavanar.com.github.gitc.entities.Repository
import makarglavanar.com.github.gitc.toast
import makarglavanar.com.github.gitc.ui.repos.repo_info.RepoInfoScreenContract.Presenter
import makarglavanar.com.github.gitc.ui.repos.repo_info.RepoInfoScreenContract.View
import makarglavanar.com.github.gitc.web.GitHubService
import javax.inject.Inject

class RepositoryInfoActivity : AppCompatActivity(), View {
    @Inject lateinit var gitHubService: GitHubService
    private lateinit var presenter: Presenter
    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GitCApp.appComponent.inject(this)
        presenter = RepoInfoPresenter(this, gitHubService)
        setContentView(R.layout.activity_repo_info)
        repository = intent.getSerializableExtra("repo") as Repository
        Log.d(TAG, repository.url)
        presenter.loadRepo(repository.getFormattedUrl())
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.deattach()
    }

    override fun showError(t: Throwable) {
        Log.w(TAG, "Loading repository error", t)
        toast("Error loading repository")
        onBackPressed()
    }

    override fun showRepository(repository: Repository) {
        name.text = repository.name
        ownerLogin.text = repository.owner.login
        createdDay.text = repository.created_at
        watchers.text = repository.watchers
        stargazersCount.text = repository.stargazers_count
        if (repository.description != null) {
            description.visibility = VISIBLE
            description.text = repository.description
        }
        if (repository.language != null) {
            language.visibility = VISIBLE
            language.text = repository.language
        }
    }


    companion object {
        val TAG: String = RepositoryInfoActivity::class.java.simpleName
    }
}
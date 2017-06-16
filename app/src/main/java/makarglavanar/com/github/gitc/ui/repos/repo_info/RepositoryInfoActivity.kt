package makarglavanar.com.github.gitc.ui.repos.repo_info

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Base64
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_repo_info.*
import makarglavanar.com.github.gitc.GitCApp
import makarglavanar.com.github.gitc.R
import makarglavanar.com.github.gitc.entities.File
import makarglavanar.com.github.gitc.entities.Repository
import makarglavanar.com.github.gitc.toast
import makarglavanar.com.github.gitc.ui.repos.repo_info.RepoInfoScreenContract.Presenter
import makarglavanar.com.github.gitc.ui.repos.repo_info.RepoInfoScreenContract.View
import makarglavanar.com.github.gitc.web.GitHubService
import javax.inject.Inject


class RepositoryInfoActivity : AppCompatActivity(), View, FilesAdapter.OnFileClickListener {
    @Inject lateinit var gitHubService: GitHubService
    private lateinit var presenter: Presenter
    private lateinit var repository: Repository
    lateinit var adapter: FilesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GitCApp.appComponent.inject(this)
        presenter = RepoInfoPresenter(this, gitHubService)
        setContentView(R.layout.activity_repo_info)
        repository = intent.getSerializableExtra("repo") as Repository
        Log.d(TAG, repository.url)
        presenter.loadRepo(repository.getFormattedUrl())
        Log.d(TAG, "presenter.loadRepo()")

        val layoutManager = LinearLayoutManager(this)
        val decorator = DividerItemDecoration(this, layoutManager.orientation)
        reposInfoListView.layoutManager = layoutManager
        reposInfoListView.addItemDecoration(decorator)
        adapter = FilesAdapter(this)
        reposInfoListView.adapter = adapter

        RxView.clicks(backView)
                .subscribe({ onBackPressed() })
    }

    override fun onBackPressed() {
        super.onBackPressed()
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


    override fun showContents(contents: List<File>) {
        reposInfoListView.visibility = VISIBLE
        fileContent.visibility = GONE
        Log.d(TAG, contents.toString() + contents.size)
        adapter.add(contents)
    }

    override fun showFile(file: File) {
        reposInfoListView.visibility = GONE
        val valueDecoded = Base64.decode(file.content, Base64.DEFAULT)
        fileContent.text = String(valueDecoded)
        fileContent.visibility = VISIBLE
    }

    override fun onClick(file: File) {
        Log.d(TAG, file.toString())
        if (file.isFile()) {
            presenter.loadFile(file.getFormattedFileUrl())
        } else {
            presenter.loadRepo(file.getFormattedDirUrl())
        }
    }

//    override fun showRepository(repository: Repository) {
//        name.text = repository.name
//        ownerLogin.text = repository.owner.login
//        createdDay.text = repository.created_at
//        watchers.text = repository.watchers
//        stargazersCount.text = repository.stargazers_count
//        if (repository.description != null) {
//            description.visibility = VISIBLE
//            description.text = repository.description
//        }
//        if (repository.language != null) {
//            language.visibility = VISIBLE
//            language.text = repository.language
//        }
//    }


    companion object {
        val TAG: String = RepositoryInfoActivity::class.java.simpleName
    }
}
package makarglavanar.com.github.gitc.ui.repos

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_repos.*
import makarglavanar.com.github.gitc.GitCApp
import makarglavanar.com.github.gitc.R
import makarglavanar.com.github.gitc.entities.Repository
import makarglavanar.com.github.gitc.ui.base_tab.BaseMainFragment
import makarglavanar.com.github.gitc.ui.repos.repo_info.RepositoryInfoActivity
import makarglavanar.com.github.gitc.web.GitHubService
import javax.inject.Inject

class ReposFragment : BaseMainFragment<ReposView, ReposPresenter>(), ReposView, ReposAdapter.OnRepositoryClickListener {
    private val adapter = ReposAdapter(this)
    @Inject lateinit var gitHubService: GitHubService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        GitCApp.appComponent.inject(this)
        return inflater.inflate(R.layout.fragment_repos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        val decorator = DividerItemDecoration(context, layoutManager.orientation)
        reposListView.layoutManager = layoutManager
        reposListView.addItemDecoration(decorator)
        reposListView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.deattach()
    }


    override fun getTabItemId() = R.id.repos

    override fun createPresenter() = ReposPresenter(gitHubService)

    override fun load(request: String) {
        showLoading(true)
        presenter.loadRepos(request)
    }

    override fun showRepos(repos: List<Repository>) {
        showLoading(false)
        adapter.add(repos)
    }

    override fun showError(t: Throwable) {
        showLoading(false)
        Log.w(TAG, "Loading repos error", t)
        Toast.makeText(context, "Error Loading repositories", Toast.LENGTH_LONG).show()

    }

    override fun onClick(repository: Repository) {
        val intent = Intent(context, RepositoryInfoActivity::class.java)
        intent.putExtra("repo", repository)
        startActivity(intent)
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            progressBar.visibility = View.VISIBLE
            reposListView.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
            reposListView.visibility = View.VISIBLE
        }
    }

    companion object {
        val TAG: String = ReposFragment::class.java.simpleName
        fun newInstance() = ReposFragment()
    }

}
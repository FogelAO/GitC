package makarglavanar.com.github.gitc.ui.issues

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_issues.*
import makarglavanar.com.github.gitc.GitCApp
import makarglavanar.com.github.gitc.R
import makarglavanar.com.github.gitc.entities.Issue
import makarglavanar.com.github.gitc.toast
import makarglavanar.com.github.gitc.ui.base_tab.BaseMainFragment
import makarglavanar.com.github.gitc.ui.issues.issue_info.IssueInfoActivity
import makarglavanar.com.github.gitc.web.GitHubService
import javax.inject.Inject

class IssuesFragment : BaseMainFragment<IssuesView, IssuesPresenter>(), IssuesView, IssuesAdapter.OnIssueClickListener {
    @Inject lateinit var gitHubService: GitHubService
    private val adapter: IssuesAdapter = IssuesAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        GitCApp.appComponent.inject(this)
        return inflater.inflate(R.layout.fragment_issues, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        val decorator = DividerItemDecoration(context, layoutManager.orientation)
        issuesRecyclerView.layoutManager = layoutManager
        issuesRecyclerView.addItemDecoration(decorator)
        issuesRecyclerView.adapter = adapter
    }

    override fun getTabItemId() = R.id.issues

    override fun createPresenter() = IssuesPresenter(gitHubService)

    override fun load(request: String) {
        showLoading(true)
        presenter.loadIssues(request)
    }

    override fun showIssues(issues: List<Issue>) {
        showLoading(false)
        adapter.add(issues)
    }

    override fun showError(t: Throwable) {
        showLoading(false)
        Log.w(TAG, "Loading issues error", t)
        toast("Error loading issues")
    }

    override fun onclick(issue: Issue) {
        val intent = Intent(context, IssueInfoActivity::class.java)
        intent.putExtra("issue", issue)
        startActivity(intent)
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            issuesProgressBar.visibility = View.VISIBLE
            issuesRecyclerView.visibility = View.GONE
        } else {
            issuesProgressBar.visibility = View.GONE
            issuesRecyclerView.visibility = View.VISIBLE
        }
    }

    companion object {
        val TAG: String = IssuesFragment::class.java.simpleName
        fun newInstance() = IssuesFragment()
    }
}
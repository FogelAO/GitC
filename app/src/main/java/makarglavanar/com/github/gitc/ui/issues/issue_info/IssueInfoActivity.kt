package makarglavanar.com.github.gitc.ui.issues.issue_info

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import makarglavanar.com.github.gitc.GitCApp
import makarglavanar.com.github.gitc.R
import makarglavanar.com.github.gitc.entities.Issue
import makarglavanar.com.github.gitc.toast
import makarglavanar.com.github.gitc.ui.issues.issue_info.IssueInfoContract.Presenter
import makarglavanar.com.github.gitc.ui.issues.issue_info.IssueInfoContract.View
import makarglavanar.com.github.gitc.web.GitHubService
import javax.inject.Inject


class IssueInfoActivity : AppCompatActivity(), View {
    private lateinit var presenter: Presenter
    @Inject lateinit var gitHubService: GitHubService
    lateinit var issue: Issue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_info)
        GitCApp.appComponent.inject(this)
        presenter = IssueInfoPresenter(this, gitHubService)
        issue = intent.getSerializableExtra("issue") as Issue
        presenter.loadIssue(issue.getFormattedUrl())
    }


    override fun showError(t: Throwable) {
        Log.w(TAG, "Loading issue error", t)
        toast("Error loading issue")
        onBackPressed()
    }

    override fun showIssue(issue: Issue) {

    }

    companion object {
        val TAG: String = IssueInfoActivity::class.java.simpleName
    }
}
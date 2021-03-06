package makarglavanar.com.github.gitc.ui.issues.issue_info

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_issue_info.*
import makarglavanar.com.github.gitc.GitCApp
import makarglavanar.com.github.gitc.R
import makarglavanar.com.github.gitc.entities.Issue
import makarglavanar.com.github.gitc.entities.IssueComment
import makarglavanar.com.github.gitc.entities.User
import makarglavanar.com.github.gitc.toast
import makarglavanar.com.github.gitc.ui.issues.issue_info.IssueCommentsAdapter.OnUserClickListener
import makarglavanar.com.github.gitc.ui.issues.issue_info.IssueInfoContract.Presenter
import makarglavanar.com.github.gitc.ui.issues.issue_info.IssueInfoContract.View
import makarglavanar.com.github.gitc.ui.users.user_info.UserInfoActivity
import makarglavanar.com.github.gitc.web.GitHubService
import javax.inject.Inject


class IssueInfoActivity : AppCompatActivity(), View, OnUserClickListener {
    private lateinit var presenter: Presenter
    @Inject lateinit var gitHubService: GitHubService
    private lateinit var issue: Issue
    private lateinit var adapter: IssueCommentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_info)
        GitCApp.appComponent.inject(this)
        presenter = IssueInfoPresenter(this, gitHubService)

        issue = intent.getSerializableExtra("issue") as Issue
        presenter.loadIssue(issue.getFormattedUrl())
        Log.d(TAG, issue.comments)

        RxView.clicks(backView).subscribe({ onBackPressed() })
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.deattach()
    }


    override fun showError(t: Throwable) {
        Log.w(TAG, "Loading issue error", t)
        toast("Error loading issue")
        onBackPressed()
    }

    override fun showIssue(issue: Issue) {
        Log.d(TAG, issue.body)
        issueBodyView.text = issue.body
    }

    override fun showComments(comments: List<IssueComment>?) {
        if (comments == null) {
            toast(getString(R.string.no_comments_info))
            adapter.clear()
            return
        }
        adapter.add(issue, comments)
    }

    override fun onClick(user: User) {
        val intent = Intent(this, UserInfoActivity::class.java)
        intent.putExtra("user", user)
    }

    companion object {
        val TAG: String = IssueInfoActivity::class.java.simpleName
    }
}
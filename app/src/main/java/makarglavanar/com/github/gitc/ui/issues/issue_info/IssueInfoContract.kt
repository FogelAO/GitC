package makarglavanar.com.github.gitc.ui.issues.issue_info

import makarglavanar.com.github.gitc.entities.Issue
import makarglavanar.com.github.gitc.entities.IssueComment
import makarglavanar.com.github.gitc.ui.base_tab.MvpView

interface IssueInfoContract {

    interface View : MvpView {
        fun showIssue(issue: Issue)
        fun showComments(comments: List<IssueComment>?)
    }

    interface Presenter {
        fun deattach()
        fun loadIssue(url: String)
        fun loadIssueComments(url: String?)
    }
}
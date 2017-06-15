package makarglavanar.com.github.gitc.ui.issues.issue_info

import makarglavanar.com.github.gitc.entities.Issue
import makarglavanar.com.github.gitc.ui.base_tab.MvpView

interface IssueInfoContract {

    interface View : MvpView {
        fun showIssue(issue: Issue)
    }

    interface Presenter {
        fun deattach()
        fun loadIssue(url: String)
    }
}
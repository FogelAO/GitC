package makarglavanar.com.github.gitc.ui.issues

import makarglavanar.com.github.gitc.entities.Issue
import makarglavanar.com.github.gitc.ui.base_tab.MvpView

interface IssuesView : MvpView {

    fun showIssues(issues: List<Issue>)
    fun showError(t: Throwable)
}
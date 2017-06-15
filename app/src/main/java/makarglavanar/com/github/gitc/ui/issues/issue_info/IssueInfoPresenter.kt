package makarglavanar.com.github.gitc.ui.issues.issue_info

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import makarglavanar.com.github.gitc.ui.issues.issue_info.IssueInfoContract.Presenter
import makarglavanar.com.github.gitc.ui.issues.issue_info.IssueInfoContract.View
import makarglavanar.com.github.gitc.web.GitHubService

class IssueInfoPresenter(val view: View, val gitHubService: GitHubService) : Presenter {
    val subscriptions = CompositeDisposable()

    override fun deattach() {
        subscriptions.dispose()
    }

    override fun loadIssue(url: String) {
        subscriptions.add(
                gitHubService
                        .getIssueByUrl(url)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { issue -> view.showIssue(issue) },
                                { t -> view.showError(t) }
                        )
        )
    }
}
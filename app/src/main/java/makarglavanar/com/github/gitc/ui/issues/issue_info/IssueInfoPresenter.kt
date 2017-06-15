package makarglavanar.com.github.gitc.ui.issues.issue_info

import android.util.Log
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

    override fun loadIssueComments(url: String?) {
        Log.d("IssueInfoPresenter", url)
        if (url != null)
            subscriptions.add(
                    gitHubService.getIssueCommentsByUrl(url)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    { comments -> view.showComments(comments) },
                                    { t -> view.showError(t) })
            )
    }
}
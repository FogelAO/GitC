package makarglavanar.com.github.gitc.ui.issues

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import makarglavanar.com.github.gitc.ui.base_tab.MvpPresenter
import makarglavanar.com.github.gitc.web.GitHubService

class IssuesPresenter(val gitHubService: GitHubService) : MvpPresenter<IssuesView>() {
    val subscriptions = CompositeDisposable()

    override fun deattach() {
        subscriptions.dispose()
    }

    fun loadIssues(terms: String) {
        subscriptions
                .add(gitHubService
                        .getIssuesByTerms(terms)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError({ t -> view.showError(t) })
                        .subscribe({ issuesResponse -> view.showIssues(issuesResponse.items) }))
    }
}
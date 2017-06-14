package makarglavanar.com.github.gitc.ui.repos

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import makarglavanar.com.github.gitc.ui.base_tab.MvpPresenter
import makarglavanar.com.github.gitc.web.GitHubService


class ReposPresenter(val gitHubService: GitHubService) : MvpPresenter<ReposView>() {
    val subscriptions = CompositeDisposable()

    fun loadRepos(name: String) {
        subscriptions.add(
                gitHubService
                        .getReposByName(name)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { reposResponse -> view.showRepos(reposResponse.items) },
                                { t -> view.showError(t) }
                        ))
    }

    override fun deattach() {
        subscriptions.dispose()
    }
}
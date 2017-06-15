package makarglavanar.com.github.gitc.ui.repos.user_repos

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import makarglavanar.com.github.gitc.ui.repos.user_repos.UserReposScreenCotract.Presenter
import makarglavanar.com.github.gitc.ui.repos.user_repos.UserReposScreenCotract.View
import makarglavanar.com.github.gitc.web.GitHubService


class UserReposPresenter(val view: View, val gitHubService: GitHubService) : Presenter {
    val subscriptions = CompositeDisposable()

    override fun deattach() {
        subscriptions.dispose()
    }

    override fun loadRepos(url: String) {
        subscriptions.add(
                gitHubService.getUserRepos(url)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { repos -> view.showRepositories(repos) },
                                { t -> view.showError(t) }
                        )
        )
    }
}
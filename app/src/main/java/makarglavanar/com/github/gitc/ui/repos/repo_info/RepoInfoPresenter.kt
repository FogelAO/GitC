package makarglavanar.com.github.gitc.ui.repos.repo_info

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import makarglavanar.com.github.gitc.ui.repos.repo_info.RepoInfoScreenContract.Presenter
import makarglavanar.com.github.gitc.ui.repos.repo_info.RepoInfoScreenContract.View
import makarglavanar.com.github.gitc.web.GitHubService


class RepoInfoPresenter(val view: View, val gitHubService: GitHubService) : Presenter {
    val subscriptions = CompositeDisposable()

    override fun deattach() {
        subscriptions.dispose()
    }

    override fun loadRepo(url: String) {
        subscriptions.add(
                gitHubService
                        .getRepoByUrl(url)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { repository -> view.showRepository(repository) },
                                {t -> view.showError(t) }
                        ))
    }
}
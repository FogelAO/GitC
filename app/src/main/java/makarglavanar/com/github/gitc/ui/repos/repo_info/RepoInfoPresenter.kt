package makarglavanar.com.github.gitc.ui.repos.repo_info

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import makarglavanar.com.github.gitc.ui.repos.repo_info.RepoInfoScreenContract.Presenter
import makarglavanar.com.github.gitc.ui.repos.repo_info.RepoInfoScreenContract.View
import makarglavanar.com.github.gitc.web.GitHubService


class RepoInfoPresenter(var view: View?, val gitHubService: GitHubService) : Presenter {
    val subscriptions = CompositeDisposable()

    override fun deattach() {
        subscriptions.dispose()
        view = null
    }

    override fun loadRepo(login: String, repo: String, path: String) {
        subscriptions.add(
                gitHubService
                        .getRepoFiles(login, repo, path)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { files -> Log.d("TAG", files.toString()); view?.showContents(files) },
                                { t -> view?.showError(t) }
                        ))
    }

    override fun loadFile(url: String) {
        Log.d("TAG", url)
        subscriptions.add(
                gitHubService
                        .getFileByUrl(url)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    file ->
                                    view?.showFile(file)
                                },
                                { t -> view?.showError(t) }
                        )
        )
    }

    companion object {
        val TAG: String = RepoInfoPresenter::class.java.simpleName
    }
}
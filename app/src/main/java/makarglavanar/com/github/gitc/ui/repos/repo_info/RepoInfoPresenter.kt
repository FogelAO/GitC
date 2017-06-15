package makarglavanar.com.github.gitc.ui.repos.repo_info

import android.util.Log
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
        Log.d(TAG, url)
        subscriptions.add(
                gitHubService
                        .getRepoFilesByUrl(url)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { files -> Log.d("TAG", files.toString()); view.showContents(files) },
                                { t -> view.showError(t) }
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
                                    file -> view.showFile(file)
                                },
                                { t -> view.showError(t) }
                        )
        )
    }

//    override fun loadRepoFiles(url: String) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }

    companion object {
        val TAG: String = RepoInfoPresenter::class.java.simpleName
    }
}
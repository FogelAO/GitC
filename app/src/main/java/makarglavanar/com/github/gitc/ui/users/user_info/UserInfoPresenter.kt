package makarglavanar.com.github.gitc.ui.users.user_info

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import makarglavanar.com.github.gitc.BuildConfig
import makarglavanar.com.github.gitc.entities.User
import makarglavanar.com.github.gitc.ui.users.user_info.UserInfoScreenContract.Presenter
import makarglavanar.com.github.gitc.ui.users.user_info.UserInfoScreenContract.View
import makarglavanar.com.github.gitc.web.GitHubService


class UserInfoPresenter(var view: View?, val gitHubService: GitHubService) : Presenter {
    val subscriptions = CompositeDisposable()

    override fun deattach() {
        subscriptions.dispose()
        view = null
    }

    override fun loadUser(login: String) {
        subscriptions.add(gitHubService
                .getUserByUserLogin(login, BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { user: User -> view?.showUser(user) },
                        { t -> view?.showError(t) }
                ))
    }
}
package makarglavanar.com.github.gitc.ui.users.user_info

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import makarglavanar.com.github.gitc.entities.User
import makarglavanar.com.github.gitc.ui.users.user_info.UserInfoScreenContract.Presenter
import makarglavanar.com.github.gitc.ui.users.user_info.UserInfoScreenContract.View
import makarglavanar.com.github.gitc.web.GitHubService


class UserInfoPresenter(val gitHubService: GitHubService) : Presenter {

    val subscriptions = CompositeDisposable()
    lateinit var view: View

    override fun attach(view: View) {
        this.view = view
    }

    override fun deattach() {
        subscriptions.dispose()
    }

    override fun loadUser(login: String) {
        subscriptions.add(gitHubService
                .getUserByUserLogin(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError({ t -> view.showError(t) })
                .subscribe({ user: User -> view.showUser(user) }))
    }
}
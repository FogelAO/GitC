package makarglavanar.com.github.gitc.ui.users

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import makarglavanar.com.github.gitc.ui.base_tab.MvpPresenter
import makarglavanar.com.github.gitc.web.GitHubService


class UsersPresenter(val gitHubService: GitHubService) : MvpPresenter<UsersView>() {
    val subscriptions = CompositeDisposable()

    fun loadUsers(name: String) {
        subscriptions.add(gitHubService
                .getUsersByName(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { usersResponse -> view.showUsers(usersResponse.items) },
                        { t -> view.showError(t) }
                ))
    }

    override fun deattach() {
        subscriptions.dispose()
    }
}
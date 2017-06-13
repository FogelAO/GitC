package makarglavanar.com.github.gitc.ui.users

import makarglavanar.com.github.gitc.entities.User
import makarglavanar.com.github.gitc.ui.base_tab.MvpView


interface UsersView : MvpView {
    fun showUsers(users: List<User>)

    fun showError(t: Throwable)
}
package makarglavanar.com.github.gitc.ui.users.user_info

import makarglavanar.com.github.gitc.entities.User
import makarglavanar.com.github.gitc.ui.base_tab.MvpView


interface UserInfoScreenContract {

    interface View : MvpView {
        fun showUser(user: User)
    }

    interface Presenter {
        fun deattach()
        fun loadUser(login: String)
    }
}
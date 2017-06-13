package makarglavanar.com.github.gitc.ui.users.user_info

import makarglavanar.com.github.gitc.entities.User


interface UserInfoScreenContract {

    interface View {
        fun showUser(user: User)
        fun showError(t: Throwable)
    }

    interface Presenter {
        fun attach(view: View)
        fun deattach()
        fun loadUser(login: String)
    }
}
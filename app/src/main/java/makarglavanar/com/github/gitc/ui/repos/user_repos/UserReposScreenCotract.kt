package makarglavanar.com.github.gitc.ui.repos.user_repos

import makarglavanar.com.github.gitc.entities.Repository
import makarglavanar.com.github.gitc.ui.base_tab.MvpView


interface UserReposScreenCotract {

    interface View : MvpView {
        fun showRepositories(repos: List<Repository>?)
    }

    interface Presenter {
        fun deattach()
        fun loadRepos(url: String)
    }
}
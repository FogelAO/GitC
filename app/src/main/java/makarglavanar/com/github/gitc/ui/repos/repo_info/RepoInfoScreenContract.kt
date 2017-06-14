package makarglavanar.com.github.gitc.ui.repos.repo_info

import makarglavanar.com.github.gitc.entities.Repository
import makarglavanar.com.github.gitc.ui.base_tab.MvpView


interface RepoInfoScreenContract {

    interface View : MvpView {
        fun showRepository(repository: Repository)
    }

    interface Presenter {
        fun deattach()
        fun loadRepo(url: String)
    }
}
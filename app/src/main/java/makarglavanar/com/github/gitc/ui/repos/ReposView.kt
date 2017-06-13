package makarglavanar.com.github.gitc.ui.repos

import makarglavanar.com.github.gitc.entities.Repository
import makarglavanar.com.github.gitc.ui.base_tab.MvpView


interface ReposView : MvpView {

    fun showRepos(repos: List<Repository>)
    fun showError(t: Throwable)
}
package makarglavanar.com.github.gitc.ui.repos.repo_info

import makarglavanar.com.github.gitc.entities.File
import makarglavanar.com.github.gitc.ui.base_tab.MvpView


interface RepoInfoScreenContract {

    interface View : MvpView {
        fun showContents(contents: List<File>)
        fun showFile(file: File)
    }

    interface Presenter {
        fun deattach()
        fun loadRepo(url: String)
        fun loadFile(url: String)
    }
}
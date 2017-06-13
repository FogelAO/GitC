package makarglavanar.com.github.gitc.ui.repos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import makarglavanar.com.github.gitc.R
import makarglavanar.com.github.gitc.ui.base_tab.BaseMainFragment

class ReposFragment : BaseMainFragment<ReposView, ReposPresenter>(), ReposView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_repos, container, false)


    override fun getTabItemId() = R.id.repos

    override fun createPresenter() = ReposPresenter()

    override fun load(request: String?) {
    }

    companion object {
        fun newInstance() = ReposFragment()
    }

}
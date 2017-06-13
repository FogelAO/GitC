package makarglavanar.com.github.gitc.ui.gists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import makarglavanar.com.github.gitc.R
import makarglavanar.com.github.gitc.ui.base_tab.BaseMainFragment

class GistsFragment : BaseMainFragment<GistsView, GistsPresenter>(), GistsView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_gists, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun load(request: String) {

    }

    override fun getTabItemId() = R.id.gists

    override fun createPresenter() = GistsPresenter()


    companion object {
        fun newInstance() = GistsFragment()
    }
}
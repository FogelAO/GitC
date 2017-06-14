package makarglavanar.com.github.gitc.ui.users.user_info

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View.VISIBLE
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_user_info.*
import makarglavanar.com.github.gitc.GitCApp
import makarglavanar.com.github.gitc.R
import makarglavanar.com.github.gitc.entities.User
import makarglavanar.com.github.gitc.toast
import makarglavanar.com.github.gitc.ui.users.user_info.UserInfoScreenContract.Presenter
import makarglavanar.com.github.gitc.ui.users.user_info.UserInfoScreenContract.View
import makarglavanar.com.github.gitc.web.GitHubService
import javax.inject.Inject


class UserInfoActivity : AppCompatActivity(), View {
    @Inject lateinit var gitHubService: GitHubService
    private lateinit var presenter: Presenter
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GitCApp.appComponent.inject(this)
        presenter = UserInfoPresenter(this, gitHubService)

        setContentView(R.layout.activity_user_info)
        user = intent.getSerializableExtra("user") as User
        presenter.loadUser(user.login)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.deattach()
    }

    override fun showUser(user: User) {
        Log.d(TAG, user.toString())
        Glide.with(this)
                .load(user.avatar_url)
                .into(avatarView)
        toolbar.title = user.login
        nameView.text = user.name
        followersView.text = user.followers
        createdDayView.text = user.getFormattedDate()

        if (user.email != null) {
            emailIcon.visibility = VISIBLE
            emailView.visibility = VISIBLE
            emailView.text = user.email
            emailView.visibility = VISIBLE
        }

        if (user.company != null) {
            dividerView.visibility = VISIBLE
            companyView.visibility = VISIBLE
            companyView.text = user.company
            companyIcon.visibility = VISIBLE
        }

        if (user.location != null) {
            dividerView.visibility = VISIBLE
            locationView.visibility = VISIBLE
            locationView.text = user.location
            locationIcon.visibility = VISIBLE
        }

    }

    override fun showError(t: Throwable) {
        Log.w("Loading user error", t)
        toast(R.string.error_loading_user_by_login.toString())
        onBackPressed()
    }

    companion object {
        val TAG: String = UserInfoActivity::class.java.simpleName
    }
}
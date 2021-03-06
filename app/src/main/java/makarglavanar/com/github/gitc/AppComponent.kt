package makarglavanar.com.github.gitc

import dagger.Component
import makarglavanar.com.github.gitc.ui.issues.IssuesFragment
import makarglavanar.com.github.gitc.ui.issues.issue_info.IssueInfoActivity
import makarglavanar.com.github.gitc.ui.repos.ReposFragment
import makarglavanar.com.github.gitc.ui.repos.repo_info.RepositoryInfoActivity
import makarglavanar.com.github.gitc.ui.repos.user_repos.UserReposActivity
import makarglavanar.com.github.gitc.ui.users.UsersFragment
import makarglavanar.com.github.gitc.ui.users.user_info.UserInfoActivity
import makarglavanar.com.github.gitc.web.GitServiceModule
import javax.inject.Singleton

@Component(modules = arrayOf(GitServiceModule::class))
@Singleton
interface AppComponent {
    fun inject(usersFragment: UsersFragment)
    fun inject(userInfoActivity: UserInfoActivity)
    fun inject(reposFragment: ReposFragment)
    fun inject(issuesFragment: IssuesFragment)
    fun inject(repositoryInfoActivity: RepositoryInfoActivity)
    fun inject(issueInfoActivity: IssueInfoActivity)
    fun inject(userReposActivity: UserReposActivity)
}
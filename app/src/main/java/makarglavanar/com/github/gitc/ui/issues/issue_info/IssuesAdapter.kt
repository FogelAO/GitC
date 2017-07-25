package makarglavanar.com.github.gitc.ui.issues.issue_info

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_issue_comment.view.*
import kotlinx.android.synthetic.main.item_issue_header_layout.view.*
import makarglavanar.com.github.gitc.R
import makarglavanar.com.github.gitc.entities.Issue
import makarglavanar.com.github.gitc.entities.IssueComment


class IssuesAdapter(
        val issue: Issue,
        val comments: List<IssueComment>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val header = 0
    private val comment = 1

    override fun getItemViewType(position: Int): Int {
        when (position) {
            header -> return header
            else -> return comment
        }
    }


    override fun getItemCount() = comments.size + 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            header -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_issue_header_layout, parent, false)
                return IssueHeaderViewHolder(view)
            }
            comment -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_issue_comment, parent, false)
                return CommentsViewHolder(view)
            }
            else -> throw RuntimeException("There is no type that matches the type $viewType + make sure your using types correctly")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is IssueHeaderViewHolder) holder.bind(issue)

        if (holder is CommentsViewHolder) {
            val comment = comments[position - 1]
            holder.bind(comment)
        }
    }


    class IssueHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView = itemView.issueTitleView
        private val bodyView = itemView.issueBodyView
        private val issueStateView = itemView.issueStateView
        private val userLoginView = itemView.issueOwnerLoginView

        fun bind(issue: Issue) {
            titleView.text = issue.title
            bodyView.text = issue.body
            issueStateView.text = issue.state
            userLoginView.text = issue.user.login
        }
    }


    class CommentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userLoginView = itemView.userLogin
        private val bodyView = itemView.commentBodyView
        private val createdDayView = itemView.createdDay
        private val userAvatarView = itemView.userAvatarView

        fun bind(comment: IssueComment) {
            userLoginView.text = comment.user.login
            bodyView.text = comment.body
            createdDayView.text = comment.created_at
        }
    }
}
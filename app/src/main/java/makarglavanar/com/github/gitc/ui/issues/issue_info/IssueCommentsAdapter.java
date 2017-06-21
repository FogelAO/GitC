package makarglavanar.com.github.gitc.ui.issues.issue_info;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.devspark.robototextview.widget.RobotoTextView;

import java.util.ArrayList;
import java.util.List;

import makarglavanar.com.github.gitc.R;
import makarglavanar.com.github.gitc.entities.Issue;
import makarglavanar.com.github.gitc.entities.IssueComment;
import makarglavanar.com.github.gitc.entities.User;

public class IssueCommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> comments;
    private final RequestManager requestManager;
    private final OnUserClickListener listener;

    public IssueCommentsAdapter(Issue issue, RequestManager requestManager, OnUserClickListener listener) {
        this.requestManager = requestManager;
        this.listener = listener;
        comments = new ArrayList<>();
        comments.add(issue);
    }

    public void add(Issue issue, List<Object> comments) {
        comments.clear();
        comments.add(issue);
        this.comments.addAll(1, comments);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return 0;
        return 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder == null) {
            Log.d("TAG", "Holder is null");
            return;
        }
        if (position == 0) {
            Issue issue = (Issue) comments.get(position);
            ((IssueViewHolder) holder).body.setText(issue.getBody());
            return;
        }
        IssueComment comment = (IssueComment) comments.get(position);
        ((IssueCommentsViewHolder) holder).bind(comment);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_issue_body_layout, parent, false);
                return new IssueViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_issue_comment, parent, false);
                return new IssueCommentsViewHolder(view);
            default:
                throw new IllegalArgumentException("Illegal viewType");
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void clear() {
        Issue issue = (Issue) comments.get(0);
        comments.clear();
        comments.add(issue);
    }

    private class IssueCommentsViewHolder extends RecyclerView.ViewHolder {
        private IssueComment comment;
        private RobotoTextView loginView;
        private RobotoTextView bodyView;
        private RobotoTextView dateView;
        private ImageView userAvatarView;

        IssueCommentsViewHolder(View itemView) {
            super(itemView);
            loginView = (RobotoTextView) itemView.findViewById(R.id.userLogin);
            loginView.setOnClickListener(v -> listener.onClick(comment.getUser()));
            bodyView = (RobotoTextView) itemView.findViewById(R.id.commentBodyView);
            dateView = (RobotoTextView) itemView.findViewById(R.id.createdDay);
            userAvatarView = (ImageView) itemView.findViewById(R.id.userAvatarView);
        }

        void bind(IssueComment comment) {
            this.comment = comment;
            loginView.setText(comment.getUser().getLogin());
            bodyView.setText(comment.getBody());
            dateView.setText(comment.getDate());
            requestManager
                    .load(comment.getUser().getAvatar_url())
                    .into(userAvatarView);
        }
    }

    private class IssueViewHolder extends RecyclerView.ViewHolder {
        RobotoTextView body;

        IssueViewHolder(View itemView) {
            super(itemView);
            body = (RobotoTextView) itemView.findViewById(R.id.issueBodyView);
        }
    }

    interface OnUserClickListener {
        void onClick(User user);
    }
}

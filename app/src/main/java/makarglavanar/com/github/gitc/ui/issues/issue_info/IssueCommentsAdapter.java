package makarglavanar.com.github.gitc.ui.issues.issue_info;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.devspark.robototextview.widget.RobotoTextView;

import java.util.ArrayList;
import java.util.List;

import makarglavanar.com.github.gitc.R;
import makarglavanar.com.github.gitc.entities.IssueComment;
import makarglavanar.com.github.gitc.entities.User;

public class IssueCommentsAdapter extends RecyclerView.Adapter<IssueCommentsAdapter.ViewHolder> {
    private List<IssueComment> comments;
    private final RequestManager requestManager;
    private final OnUserClickListener listener;

    public IssueCommentsAdapter(RequestManager requestManager, OnUserClickListener listener) {
        this.requestManager = requestManager;
        this.listener = listener;
        comments = new ArrayList<>();
    }

    public void add(List<IssueComment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_issue_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        IssueComment comment = comments.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void clear() {
        comments.clear();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private IssueComment comment;
        private RobotoTextView loginView;
        private RobotoTextView bodyView;
        private RobotoTextView dateView;
        private ImageView userAvatarView;

        ViewHolder(View itemView) {
            super(itemView);
            loginView = (RobotoTextView) itemView.findViewById(R.id.userLogin);
            loginView.setOnClickListener(v -> listener.onClick(comment.getUser()));
            bodyView = (RobotoTextView) itemView.findViewById(R.id.body);
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

    interface OnUserClickListener {
        void onClick(User user);
    }
}

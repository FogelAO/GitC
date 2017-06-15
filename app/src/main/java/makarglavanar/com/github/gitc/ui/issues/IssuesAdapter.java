package makarglavanar.com.github.gitc.ui.issues;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devspark.robototextview.widget.RobotoTextView;

import java.util.ArrayList;
import java.util.List;

import makarglavanar.com.github.gitc.R;
import makarglavanar.com.github.gitc.entities.Issue;

public class IssuesAdapter extends RecyclerView.Adapter<IssuesAdapter.ViewHolder> {
    private List<Issue> issues;

    public IssuesAdapter() {
        this.issues = new ArrayList<>();
    }

    public void add(List<Issue> issues) {
        this.issues = issues;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_issue_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Issue issue = issues.get(position);
        holder.bind(issue);
    }

    @Override
    public int getItemCount() {
        return issues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private Issue issue;
        private RobotoTextView title;
        private RobotoTextView userLoginView;
        private RobotoTextView createdDayView;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (RobotoTextView) itemView.findViewById(R.id.titleView);
            userLoginView = (RobotoTextView) itemView.findViewById(R.id.userLogin);
            createdDayView = (RobotoTextView) itemView.findViewById(R.id.createdDay);
        }

        void bind(Issue issue) {
            this.issue = issue;
            title.setText(issue.getTitle());
            userLoginView.setText(issue.getUser().getLogin());
            createdDayView.setText(issue.getDate());
        }
    }
}

package makarglavanar.com.github.gitc.ui.repos;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devspark.robototextview.widget.RobotoTextView;

import java.util.ArrayList;
import java.util.List;

import makarglavanar.com.github.gitc.R;
import makarglavanar.com.github.gitc.entities.Repository;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ViewHolder> {
	private List<Repository> repos;
	private OnRepositoryClickListener listener;

	public ReposAdapter(OnRepositoryClickListener listener) {
		this.listener = listener;
		this.repos = new ArrayList<>();
	}

	public void add(List<Repository> repos) {
		this.repos = repos;
		notifyDataSetChanged();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo_layout, parent, false);
		return new ViewHolder(view, listener);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Repository repository = repos.get(position);
		holder.bind(repository);
	}

	@Override
	public int getItemCount() {
		return repos.size();
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		private RobotoTextView nameView;
		private RobotoTextView updatedView;
		Repository repository;

		ViewHolder(View itemView, OnRepositoryClickListener listener) {
			super(itemView);
			nameView = (RobotoTextView) itemView.findViewById(R.id.nameView);
			nameView.setOnClickListener(v -> listener.onClick(repository));
			updatedView = (RobotoTextView) itemView.findViewById(R.id.updatedView);
		}

		void bind(Repository repository) {
			this.repository = repository;
			nameView.setText(repository.getName());
			updatedView.setText(repository.getFormattedUpdated());
		}
	}

	public interface OnRepositoryClickListener {
		void onClick(Repository repository);
	}
}

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

	public ReposAdapter() {
		this.repos = new ArrayList<>();
	}

	public void add(List<Repository> repos) {
		this.repos = repos;
		notifyDataSetChanged();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo_layout, parent, false);
		return new ViewHolder(view);
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
		RobotoTextView nameView;
		Repository repository;

		public ViewHolder(View itemView) {
			super(itemView);
			nameView = (RobotoTextView) itemView.findViewById(R.id.nameView);
		}

		void bind(Repository repository) {
			this.repository = repository;
			nameView.setText(repository.getName());
		}
	}
}

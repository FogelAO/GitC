package makarglavanar.com.github.gitc.ui.repos.repo_info;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.devspark.robototextview.widget.RobotoTextView;

import java.util.ArrayList;
import java.util.List;

import makarglavanar.com.github.gitc.R;
import makarglavanar.com.github.gitc.entities.File;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.FilesViewHolder> {
	private List<File> files;
	private final OnFileClickListener listener;

	public FilesAdapter(OnFileClickListener listener) {
		this.listener = listener;
		files = new ArrayList<>();
	}

	void add(List<File> files) {
		if (files.get(0).isRoot()) {
			this.files = files;
		} else {
			File file = new File("...", "dir", files.get(0).getRootPath(), "0", "", files.get(0).getRootPath());
			this.files.clear();
			this.files.add(file);
			this.files.addAll(1, files);
		}
		notifyDataSetChanged();
	}

	@Override
	public FilesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View vew = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.item_file_layout, parent, false);
		return new FilesViewHolder(vew, listener);
	}

	@Override
	public void onBindViewHolder(FilesViewHolder holder, int position) {
		File file = files.get(position);
		holder.bind(file);
	}

	@Override
	public int getItemCount() {
		return files.size();
	}

	class FilesViewHolder extends RecyclerView.ViewHolder {
		private File file;
		private RobotoTextView nameView;
		private ImageView iconView;

		FilesViewHolder(View itemView, OnFileClickListener listener) {
			super(itemView);
			nameView = (RobotoTextView) itemView.findViewById(R.id.nameView);
			iconView = (ImageView) itemView.findViewById(R.id.iconView);
			itemView.setOnClickListener(v -> listener.onClick(file));
		}

		void bind(File file) {
			this.file = file;
			nameView.setText(file.getName());
			if (file.isFile()) {
				iconView.setImageResource(R.drawable.ic_insert_drive_file_black_24dp);
			} else {
				iconView.setImageResource(R.drawable.ic_folder_black_24dp);
			}
		}
	}

	interface OnFileClickListener {
		void onClick(File file);
	}
}

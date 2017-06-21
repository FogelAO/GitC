package makarglavanar.com.github.gitc.ui.users;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.List;

import makarglavanar.com.github.gitc.R;
import makarglavanar.com.github.gitc.entities.User;

class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {
	private List<User> users;
	private final OnUserClickListener listener;
	private final RequestManager imageRequestManager;

	UsersAdapter(RequestManager imageRequestManager, OnUserClickListener listener) {
		this.listener = listener;
		this.imageRequestManager = imageRequestManager;
		users = new ArrayList<>();
	}

	void add(@NonNull List<User> user) {
		this.users = user;
		notifyDataSetChanged();
	}

	@Override
	public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater
				.from(parent.getContext())
				.inflate(R.layout.item_user_layout, parent, false);
		return new UsersViewHolder(view, listener);
	}

	@Override
	public void onBindViewHolder(UsersViewHolder holder, int position) {
		User user = users.get(position);
		holder.bind(user);
	}

	@Override
	public int getItemCount() {
		return users.size();
	}

	class UsersViewHolder extends RecyclerView.ViewHolder {
		private TextView loginView;
		private ImageView imageView;
		User user;

		UsersViewHolder(View itemView, OnUserClickListener listener) {
			super(itemView);

			loginView = (TextView) itemView.findViewById(R.id.loginView);
			imageView = (ImageView) itemView.findViewById(R.id.avatarView);

			itemView.setOnClickListener(v -> listener.onClick(user));
		}

		void bind(User user) {
			this.user = user;
			loginView.setText(user.getLogin());
			imageRequestManager.load(user.getAvatar_url()).into(imageView);
		}
	}

	interface OnUserClickListener {
		void onClick(User user);
	}
}

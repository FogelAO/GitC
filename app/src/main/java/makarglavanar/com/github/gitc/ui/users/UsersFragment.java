package makarglavanar.com.github.gitc.ui.users;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import makarglavanar.com.github.gitc.GitCApp;
import makarglavanar.com.github.gitc.R;
import makarglavanar.com.github.gitc.entities.User;
import makarglavanar.com.github.gitc.ui.base_tab.BaseMainFragment;
import makarglavanar.com.github.gitc.ui.users.user_info.UserInfoActivity;
import makarglavanar.com.github.gitc.web.GitHubService;

public class UsersFragment extends BaseMainFragment<UsersView, UsersPresenter> implements UsersView, UsersAdapter.OnUserClickListener {
	private static final String TAG = UsersFragment.class.getSimpleName();
	private GitHubService gitHubService = GitCApp.Companion.getGitService();
	RecyclerView recyclerView;
	private UsersAdapter adapter;

	public static UsersFragment newInstance() {
		return new UsersFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_users, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		recyclerView = (RecyclerView) view.findViewById(R.id.usersListView);

		LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
		DividerItemDecoration decoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());

		recyclerView.addItemDecoration(decoration);
		recyclerView.setLayoutManager(layoutManager);
		RequestManager imageRequestManager = Glide.with(getContext());
		adapter = new UsersAdapter(imageRequestManager, this);
		recyclerView.setAdapter(adapter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		presenter.deattach();
	}

	@Override
	public int getTabItemId() {
		return R.id.users;
	}

	@Override
	protected UsersPresenter createPresenter() {
		return new UsersPresenter(gitHubService);
	}

	@Override
	public void showUsers(@NotNull List<User> users) {
		adapter.add(users);
	}

	@Override
	public void load(String request) {
		presenter.loadUsers(request);
	}

	@Override
	public void showError(@NonNull Throwable t) {
		Log.w(TAG, "Loading users error", t);
		Toast.makeText(getContext(), R.string.error_loading_users_by_login, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onClick(User user) {
		Intent intent = new Intent(getContext(), UserInfoActivity.class);
		intent.putExtra("user", user);
		startActivity(intent);
	}
}

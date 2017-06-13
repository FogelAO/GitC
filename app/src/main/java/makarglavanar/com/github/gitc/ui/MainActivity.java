package makarglavanar.com.github.gitc.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import makarglavanar.com.github.gitc.R;
import makarglavanar.com.github.gitc.ui.base_tab.BaseMainFragment;
import makarglavanar.com.github.gitc.ui.base_tab.Tab;

public class MainActivity extends AppCompatActivity {
	private static final String TAG = MainActivity.class.getSimpleName();
	private final CompositeDisposable subscriptions = new CompositeDisposable();
	private BaseMainFragment currentFragment;
	Toolbar toolbar;
	FrameLayout contentView;
	SearchView searchView;
	BottomNavigationView bottomNavigationView;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		contentView = (FrameLayout) findViewById(R.id.content);
		bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
		bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
		bottomNavigationView.setOnNavigationItemReselectedListener(this::onNavigationItemSelected);

		BaseMainFragment fragment = (BaseMainFragment) getSupportFragmentManager().findFragmentById(R.id.content);
		if (fragment == null) {
			replaceOrSetContentFragment(BaseMainFragment.newInstance(Tab.USERS));
			bottomNavigationView.setSelectedItemId(R.id.users);
		} else {
			currentFragment = fragment;
			bottomNavigationView.setSelectedItemId(fragment.getTabItemId());
		}

		searchView = (SearchView) findViewById(R.id.searchView);
		subscriptions.add(RxSearchView.queryTextChanges(searchView)
				.debounce(300, TimeUnit.MILLISECONDS)
				.filter(charSequence -> charSequence.length() > 1)
				.doOnError(throwable -> Log.w(TAG, "Error occurred while searching", throwable))
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(charSequence -> currentFragment.load(charSequence.toString())));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		subscriptions.dispose();
	}

	private boolean onNavigationItemSelected(MenuItem menuItem) {
		Tab selectedTab;

		switch (menuItem.getItemId()) {
			case R.id.users:
				selectedTab = Tab.USERS;
				break;
			case R.id.repos:
				selectedTab = Tab.REPOS;
				break;
			case R.id.gists:
				selectedTab = Tab.GISTS;
				break;
			default:
				throw new IllegalStateException("Unsupported tab " + menuItem.getTitle());
		}

		currentFragment = BaseMainFragment.newInstance(selectedTab);
		replaceOrSetContentFragment(currentFragment);
		return true;
	}

	private void replaceOrSetContentFragment(BaseMainFragment fragment) {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content, fragment)
				.commit();
		currentFragment = fragment;
	}
}
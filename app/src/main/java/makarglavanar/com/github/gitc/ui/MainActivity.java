package makarglavanar.com.github.gitc.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import makarglavanar.com.github.gitc.R;
import makarglavanar.com.github.gitc.api.GithubSession;
import makarglavanar.com.github.gitc.ui.base_tab.BaseMainFragment;
import makarglavanar.com.github.gitc.ui.base_tab.Tab;

public class MainActivity extends AppCompatActivity {
	public static final String TAG = MainActivity.class.getSimpleName();

	private final CompositeDisposable subscriptions = new CompositeDisposable();
	private BaseMainFragment currentFragment;
	Toolbar toolbar;
	BottomSheetBehavior bottomSheetBehavior;
	FrameLayout contentView;
	SearchView searchView;
	BottomNavigationView bottomNavigationView;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SharedPreferences sharedPreferences = getSharedPreferences(GithubSession.SHARED, Context.MODE_PRIVATE);
		String token = sharedPreferences.getString(GithubSession.API_ACCESS_TOKEN, null);

		Toast.makeText(this, token, Toast.LENGTH_LONG).show();

		searchView = (SearchView) findViewById(R.id.searchView);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		contentView = (FrameLayout) findViewById(R.id.content);
		bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
		bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
		bottomNavigationView.setOnNavigationItemReselectedListener(this::onNavigationItemSelected);

		findViewById(R.id.loginView).setOnClickListener(this::showBottomSheet);
		findViewById(R.id.backView).setOnClickListener(this::hideBottomSheet);

		View bottomSheet = findViewById(R.id.bottom_sheet);
		bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
		bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
		bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
			@Override
			public void onStateChanged(@NonNull View bottomSheet, int newState) {
				if (newState != BottomSheetBehavior.STATE_HIDDEN) {
					bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
				}
			}

			@Override
			public void onSlide(@NonNull View bottomSheet, float slideOffset) {
				if (slideOffset >= 0.9)
					toolbar.setVisibility(View.GONE);
				else
					toolbar.setVisibility(View.VISIBLE);
			}
		});

		subscriptions.add(
				RxView.clicks(findViewById(R.id.loginView))
						.subscribe(this::showBottomSheet));

		BaseMainFragment fragment = (BaseMainFragment) getSupportFragmentManager().findFragmentById(R.id.content);
		if (fragment == null) {
			replaceOrSetContentFragment(BaseMainFragment.newInstance(Tab.USERS));
			bottomNavigationView.setSelectedItemId(R.id.users);
		} else {
			currentFragment = fragment;
			bottomNavigationView.setSelectedItemId(fragment.getTabItemId());
		}

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

	private void showBottomSheet(Object o) {
		bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
		bottomNavigationView.setVisibility(View.GONE);
	}

	private void hideBottomSheet(Object o) {
		bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
		bottomNavigationView.setVisibility(View.VISIBLE);
		toolbar.setVisibility(View.VISIBLE);
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
			case R.id.issues:
				selectedTab = Tab.ISSUES;
				break;
			default:
				throw new IllegalStateException("Unsupported tab " + menuItem.getTitle());
		}
		searchView.setIconified(true);
		searchView.clearFocus();
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

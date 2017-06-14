package makarglavanar.com.github.gitc.ui.base_tab;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import makarglavanar.com.github.gitc.ui.issues.IssuesFragment;
import makarglavanar.com.github.gitc.ui.repos.ReposFragment;
import makarglavanar.com.github.gitc.ui.users.UsersFragment;

public abstract class BaseMainFragment<V extends MvpView, P extends MvpPresenter> extends Fragment {
    protected P presenter;

    public static BaseMainFragment newInstance(Tab tab) {
        switch (tab) {
            case USERS:
                return UsersFragment.newInstance();
            case REPOS:
                return ReposFragment.Companion.newInstance();
            case ISSUES:
                return IssuesFragment.Companion.newInstance();
        }
        throw new IllegalArgumentException("Unknown tab : " + tab.name());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = createPresenter();
        presenter.setView((V) this);
    }

    @IdRes
    public abstract int getTabItemId();

    protected abstract P createPresenter();

    public abstract void load(@NotNull String request);
}

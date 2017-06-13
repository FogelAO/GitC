package makarglavanar.com.github.gitc.ui.base_tab;

public abstract class MvpPresenter<V extends MvpView> {
	private V view;

	public V getView() {
		return view;
	}

	protected void setView(V view) {
		this.view = view;
	}

	public abstract void deattach();
}

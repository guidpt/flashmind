package br.com.nome.flashmind.logic.presenter;

import br.com.nome.flashmind.ui.view.IBaseView;

/**
 * Created by Alessandro Pryds on 20/12/2016.
 */
public class MainPresenter {
    private IMainView mView;

    public MainPresenter(IMainView view) {
        this.mView = view;
        this.mView.setPresenter(this);
    }

    public void start(){
        mView.initToolbar();
        mView.setupViewPager();
        mView.setupBottomNavigation();
    }

    public interface IMainView extends IBaseView {

        void setPresenter(MainPresenter presenter);

        void initToolbar();

        void setupViewPager();

        void setupBottomNavigation();
    }
}

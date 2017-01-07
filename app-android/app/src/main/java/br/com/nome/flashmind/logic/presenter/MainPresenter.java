package br.com.nome.flashmind.logic.presenter;

import br.com.nome.flashmind.application.FlashMindApplication;
import br.com.nome.flashmind.logic.rxbus.RxQueues;
import br.com.nome.flashmind.logic.rxbus.events.ListScrolledEvent;
import br.com.nome.flashmind.ui.view.IBaseView;
import rx.Observer;
import rx.Subscription;

/**
 * Created by Alessandro Pryds on 20/12/2016.
 */
public class MainPresenter {
    private IMainView mView;
    private Subscription mScrollSubscription;

    public MainPresenter(IMainView view) {
        this.mView = view;
        this.mView.setPresenter(this);
    }

    public void start(){
        mView.initToolbar();
        mView.setupViewPager();
        mView.setupBottomNavigation();
        createSubscriptions();
    }

    private void createSubscriptions() {
        mScrollSubscription = FlashMindApplication.getRxBus().subscribe(RxQueues.LIST_SCROLLED_EVENT_QUEUE, new Observer<ListScrolledEvent>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ListScrolledEvent listScrolledEvent) {
                if(listScrolledEvent.shouldHide()){
                    mView.hideBottomNavigation();
                    return;
                }
                mView.showBottomNavigation();
            }
        });
    }

    public interface IMainView extends IBaseView {

        void setPresenter(MainPresenter presenter);

        void initToolbar();

        void setupViewPager();

        void setupBottomNavigation();

        void hideBottomNavigation();

        void showBottomNavigation();
    }
}

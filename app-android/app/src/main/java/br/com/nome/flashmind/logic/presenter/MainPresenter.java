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
    private static final int HOME_MENU_INDEX = 2;

    private static final int MY_DECKS_INDEX = 0;
    private static final int HOME_INDEX = 1;
    private static final int DISCOVER_INDEX = 2;

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

    public void onDestroy(){
        if(mScrollSubscription != null && !mScrollSubscription.isUnsubscribed()){
            mScrollSubscription.unsubscribe();
        }
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

    public void onMenuListTouched() {
        
    }

    public void onCenterButtonTouched() {
        mView.invalidateOptionsMenu(HOME_MENU_INDEX);
        mView.moveToPage(HOME_INDEX);
    }

    public void onPageSwipe(int position) {
        mView.invalidateOptionsMenu(position);
        mView.setTitleForPage(position);
    }

    public void onPageSelected(int position) {
        mView.invalidateOptionsMenu(position);
        mView.setTitleForPage(position);
        switch (position){
            case 0:
                mView.moveToPage(MY_DECKS_INDEX);
                break;
            case 1:
                mView.moveToPage(DISCOVER_INDEX);
                break;
        }
    }

    public interface IMainView extends IBaseView {

        void setPresenter(MainPresenter presenter);

        void initToolbar();

        void setupViewPager();

        void setupBottomNavigation();

        void hideBottomNavigation();

        void showBottomNavigation();

        void invalidateOptionsMenu(int position);

        void moveToPage(int page);

        void setTitleForPage(int position);
    }
}

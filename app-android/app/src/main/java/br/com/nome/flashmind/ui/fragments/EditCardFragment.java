package br.com.nome.flashmind.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import br.com.nome.flashmind.R;
import br.com.nome.flashmind.application.FlashMindApplication;
import br.com.nome.flashmind.application.FlashMindConstants;
import br.com.nome.flashmind.logic.model.Card;
import br.com.nome.flashmind.logic.rxbus.RxQueues;
import br.com.nome.flashmind.logic.rxbus.events.FlipCardEvent;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;

public class EditCardFragment extends Fragment {


    @BindView(R.id.flipView)
    protected EasyFlipView flipView;
    @BindView(R.id.etBack)
    protected EditText etBack;
    @BindView(R.id.etFront)
    protected EditText etFront;
    @BindView(R.id.tvBackLabel)
    protected TextView tvBackLabel;
    @BindView(R.id.ivImage)
    protected ImageView ivImage;
    @BindView(R.id.tvFrontLabel)
    protected TextView tvFrontLabel;
    private Subscription mFlipCardSubscription;

    private int mPosition;
    private boolean isBack;
    private Subscription mSubscriptionFront, mSubscriptionBack;

    public EditCardFragment() {
        // Required empty public constructor
    }

    public static EditCardFragment newInstance(int position, Card card) {
        EditCardFragment fragment = new EditCardFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FlashMindConstants.BUNDLE_TAG_POSITION, position);
        bundle.putParcelable(FlashMindConstants.BUNDLE_TAG_CARD, card);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt(FlashMindConstants.BUNDLE_TAG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_card, container, false);
        ButterKnife.bind(this, view);

        tvBackLabel.setVisibility(View.GONE);
        tvFrontLabel.setVisibility(View.GONE);
        Observable<CharSequence> observableBack = RxTextView.textChanges(etBack);
        Observable<CharSequence> observableFront = RxTextView.textChanges(etFront);
        mSubscriptionFront = observableFront.map(new Func1<CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence) {
                return charSequence.length() > 0;
            }
        }).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Boolean contains) {
                Transition fade;
                if(contains) {
                    fade = new Fade(Fade.IN);
                    TransitionManager.beginDelayedTransition(flipView, fade);
                    tvFrontLabel.setVisibility(View.VISIBLE);
                    return;
                }else{
                    fade = new Fade(Fade.OUT);
                    TransitionManager.beginDelayedTransition(flipView, fade);
                    tvFrontLabel.setVisibility(View.GONE);
                }
            }
        });

        mSubscriptionBack = observableBack.map(new Func1<CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence) {
                return charSequence.length() > 0;
            }
        }).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Boolean contains) {
                Transition fade;
                if(contains) {
                    fade = new Fade(Fade.IN);
                    TransitionManager.beginDelayedTransition(flipView, fade);
                    tvBackLabel.setVisibility(View.VISIBLE);
                    return;
                }else{
                    fade = new Fade(Fade.OUT);
                    TransitionManager.beginDelayedTransition(flipView, fade);
                    tvBackLabel.setVisibility(View.GONE);
                }
            }
        });

        mFlipCardSubscription = FlashMindApplication.getRxBus().subscribe(RxQueues.FLIP_CARD_EVENT, new Observer<FlipCardEvent>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(FlipCardEvent flipCardEvent) {
                if (flipCardEvent.getPosition() != mPosition)
                    return;
                isBack = !isBack;
                etBack.setVisibility(isBack ? View.VISIBLE : View.GONE);
                etFront.setVisibility(!isBack ? View.VISIBLE : View.GONE);
                flipView.flipTheView();
                hideKeyboard();
            }

            private void hideKeyboard() {
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        if (mFlipCardSubscription != null && !mFlipCardSubscription.isUnsubscribed()) {
            mFlipCardSubscription.unsubscribe();
        }
        super.onDestroyView();
    }

    //region
    //endregion
}

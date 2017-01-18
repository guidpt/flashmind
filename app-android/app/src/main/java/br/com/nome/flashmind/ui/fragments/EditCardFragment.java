package br.com.nome.flashmind.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.wajahatkarim3.easyflipview.EasyFlipView;

import br.com.nome.flashmind.R;
import br.com.nome.flashmind.application.FlashMindApplication;
import br.com.nome.flashmind.application.FlashMindConstants;
import br.com.nome.flashmind.logic.model.Card;
import br.com.nome.flashmind.logic.rxbus.RxQueues;
import br.com.nome.flashmind.logic.rxbus.events.FlipCardEvent;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;

public class EditCardFragment extends Fragment {


    @BindView(R.id.flipView)
    protected EasyFlipView flipView;
    @BindView(R.id.etBack)
    protected EditText etBack;
    @BindView(R.id.etFront)
    protected EditText etFront;
    private Subscription mFlipCardSubscription;

    private int mPosition;
    private boolean isBack;

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

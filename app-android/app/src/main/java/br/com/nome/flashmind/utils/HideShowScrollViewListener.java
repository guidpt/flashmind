package br.com.nome.flashmind.utils;

import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

/**
 * Created by Alessandro Pryds on 03/01/2017.
 */
public abstract class HideShowScrollViewListener implements ViewTreeObserver.OnScrollChangedListener {
    private static final int HIDE_THRESHOLD = 20;
    private final ScrollView scrollView;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;
    private int oldDy = 0;

    public HideShowScrollViewListener(ScrollView scrollView) {
        this.scrollView = scrollView;
    }

    @Override
    public void onScrollChanged() {
        int dy = scrollView.getScrollY() - oldDy;
        Log.i("Scroll", "scroll:" + scrollView.getScrollY());
        if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
            onHide();
            controlsVisible = false;
            scrolledDistance = 0;
        } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
            onShow();
            controlsVisible = true;
            scrolledDistance = 0;
        }

        if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
            scrolledDistance += dy;
        }

        oldDy = scrollView.getScrollY();
    }

    public abstract void onHide();

    public abstract void onShow();

}
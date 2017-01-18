package br.com.nome.flashmind.utils;

import android.view.View;

import com.transitionseverywhere.Transition;

/**
 * Created by Alessandro Pryds on 17/01/2017.
 */
public class DisableWhenAnimatingListener implements Transition.TransitionListener {
    private final View view;

    public DisableWhenAnimatingListener(View view) {
        this.view = view;
    }


    @Override
    public void onTransitionStart(Transition transition) {
        view.setEnabled(false);
    }

    @Override
    public void onTransitionEnd(Transition transition) {
        view.setEnabled(true);
    }

    @Override
    public void onTransitionCancel(Transition transition) {

    }

    @Override
    public void onTransitionPause(Transition transition) {

    }

    @Override
    public void onTransitionResume(Transition transition) {

    }
}

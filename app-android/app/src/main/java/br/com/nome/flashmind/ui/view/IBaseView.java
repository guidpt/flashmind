package br.com.nome.flashmind.ui.view;

/**
 * Created by Alessandro Pryds on 20/12/2016.
 */

public interface IBaseView {

    void showProgress();

    void showProgress(String message);

    void hideProgress();

    void showToastMessage(String message);

    void showSnackMessage(String message);

    void showNoConnectionSnackMessage();

    void showFixedSnackMessage(String message);

    void hideFixedSnackMessage();

    boolean isOnline();

}
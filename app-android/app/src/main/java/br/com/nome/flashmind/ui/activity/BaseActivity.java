package br.com.nome.flashmind.ui.activity;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import br.com.nome.flashmind.R;
import br.com.nome.flashmind.ui.view.IBaseView;
import br.com.nome.flashmind.utils.NetworkStatsUtil;

/**
 * Created by Alessandro Pryds on 20/12/2016.
 */

public class BaseActivity extends AppCompatActivity implements IBaseView {

    private ProgressDialog progressDialog;
    private Snackbar fixedSnackbar;

    @Override
    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getText(R.string.loading_message));
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    @Override
    public void showProgress(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSnackMessage(String message) {
        try {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.root), message, Snackbar.LENGTH_LONG);
            snackbar.show();
        }catch(NullPointerException e){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showNoConnectionSnackMessage() {
        try{
            Snackbar snackbar = Snackbar.make(findViewById(R.id.root), getText(R.string.ERROR_MESSAGE_NO_CONNECTION), Snackbar.LENGTH_LONG);
            TextView txtMessage = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
            txtMessage.setTextColor(ContextCompat.getColor(this, R.color.no_connection_yellow));
            snackbar.show();
        }catch(NullPointerException e){
            Toast.makeText(this, getText(R.string.ERROR_MESSAGE_NO_CONNECTION), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showFixedSnackMessage(String message) {
        fixedSnackbar = Snackbar.make(findViewById(R.id.root), message, Snackbar.LENGTH_INDEFINITE);
        fixedSnackbar.show();
    }

    @Override
    public void hideFixedSnackMessage() {
        if(fixedSnackbar == null)
            return;
        fixedSnackbar.dismiss();
        fixedSnackbar = null;
    }
    @Override
    public boolean isOnline() {
        return NetworkStatsUtil.isConnected(this);
    }
}

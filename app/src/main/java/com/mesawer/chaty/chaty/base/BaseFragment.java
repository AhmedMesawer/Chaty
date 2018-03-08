package com.mesawer.chaty.chaty.base;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.mesawer.chaty.chaty.R;

/**
 * Created by ilias on 08/03/2018.
 */

public class BaseFragment extends Fragment implements IView {

    public View view;
    private Dialog loadingIndicator = null;

    @Override
    public void showLoadingIndicator() {
        if (loadingIndicator == null && isAdded()) {
            loadingIndicator = new Dialog(getActivity());
            loadingIndicator.requestWindowFeature(Window.FEATURE_NO_TITLE);
            loadingIndicator.setContentView(R.layout.partial_blocking_loading_indicator);
            loadingIndicator.setCancelable(false);
            loadingIndicator.getWindow()
                    .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            loadingIndicator.show();
        }
    }

    @Override
    public void hideLoadingIndicator() {
        if (loadingIndicator != null) {
            loadingIndicator.dismiss();
            loadingIndicator = null;
        }
    }

    @Override
    public void showErrorMessage(String errMsg) {
        if (view != null) {
            Snackbar snackbar = Snackbar.make(view, errMsg, Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(getResources().getColor(R.color.secondaryTextColor));
            sbView.setBackgroundColor(
                    ContextCompat.getColor(getActivity(), android.R.color.holo_red_dark));
            snackbar.show();
        }
    }
}

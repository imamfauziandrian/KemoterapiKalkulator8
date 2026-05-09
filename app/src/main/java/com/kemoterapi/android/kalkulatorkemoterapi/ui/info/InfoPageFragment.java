package com.kemoterapi.android.kalkulatorkemoterapi.ui.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import com.kemoterapi.android.kalkulatorkemoterapi.R;

public class InfoPageFragment extends Fragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_BODY = "body";

    public static InfoPageFragment newInstance(String title, String body) {
        InfoPageFragment fragment = new InfoPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_BODY, body);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auc_info_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        String title = args != null ? args.getString(ARG_TITLE, "") : "";
        String body = args != null ? args.getString(ARG_BODY, "") : "";

        TextView titleView = view.findViewById(R.id.pageTitle);
        TextView bodyView = view.findViewById(R.id.pageBody);

        titleView.setText(title);
        bodyView.setText(HtmlCompat.fromHtml(body.replace("\n", "<br>"), HtmlCompat.FROM_HTML_MODE_LEGACY));
    }
}

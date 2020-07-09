package com.example.covid19status;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InternacionalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InternacionalFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    WebView webView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InternacionalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InternacionalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InternacionalFragment newInstance(String param1, String param2) {
        InternacionalFragment fragment = new InternacionalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        //nueva implementacion

        View view = inflater.inflate(R.layout.fragment_internacional,container, false);
        webView = (WebView)view.findViewById(R.id.covidweb);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.loadUrl("https://leosalgueiro.github.io/tpi/#");
        return view;
        //fin nueva implementacion

        // Inflate the layout for this fragment
/*
        String url="https://leosalgueiro.github.io/tpi/#";
        WebView view=(WebView) Objects.requireNonNull(getActivity()).findViewById(R.id.covidweb);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
        return view;*/
        //return inflater.inflate(R.layout.fragment_internacional, container, false);


    }
}

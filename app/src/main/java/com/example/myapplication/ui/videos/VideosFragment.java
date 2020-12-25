package com.example.myapplication.ui.videos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.myapplication.R;

public class VideosFragment extends Fragment {

    private VideosViewModel mViewModel;

    public static VideosFragment newInstance() {
        return new VideosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View videos = inflater.inflate(R.layout.videos_fragment, container, false);
        WebView browser = (WebView) videos.findViewById(R.id.webview);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.loadUrl("https://www.youtube.com/results?search_query=tourist+spots");
        browser.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });
        return videos;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VideosViewModel.class);
        // TODO: Use the ViewModel
    }

}
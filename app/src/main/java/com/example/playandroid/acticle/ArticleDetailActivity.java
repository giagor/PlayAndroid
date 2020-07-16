package com.example.playandroid.acticle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.widget.Toolbar;

import com.example.playandroid.R;

import static com.example.playandroid.util.Constants.ArticleDetailConstant.TITLE;
import static com.example.playandroid.util.Constants.ArticleDetailConstant.URL;

/**
 * 展示文章详情的活动，主要使用WebView.
 */
public class ArticleDetailActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        initView();
        setActionBar();
        setAttribute();

        mWebView.loadUrl(getIntent().getStringExtra(URL));
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mProgressBar = findViewById(R.id.progress_bar);
        mWebView = findViewById(R.id.web_view);
    }
    
    /**
     * 设置顶部标题栏的信息.
     */
    private void setActionBar() {
        mToolbar.setTitle(getIntent().getStringExtra(TITLE));
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * 设置WebView属性.
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void setAttribute() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mProgressBar.setProgress(newProgress);
                if (newProgress >= 100) {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(getIntent().getStringExtra(URL));
                return true;
            }
        });
    }

    /**
     * 启动该活动.
     */
    public static void actionStart(Context context, String title, String url) {
        Intent intent = new Intent(context,ArticleDetailActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(URL, url);
        context.startActivity(intent);
    }
}

package com.example.playandroid.acticle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.widget.Toolbar;

import com.example.playandroid.R;

import static android.view.KeyEvent.KEYCODE_BACK;
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

        //获取网页的加载进度并且显示
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mProgressBar.setProgress(newProgress);
                if (newProgress >= 100) {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });

        //打开另一个网页时，不调用系统浏览器，而是在该WebView界面打开
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(getIntent().getStringExtra(URL));
                return true;
            }
        });
    }

    /**
     * 消化物理按键的BACK，使其返回上一个网页界面.
     *
     * @return 返回true阻止 按键事件 被传播的更远；返回false表明你并没有控制该事件，可以继续传播.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 用户点击标题栏的"返回键"时，销毁WebView所在的活动.
     * */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return true;
    }

    /**
     * 启动该活动.
     */
    public static void actionStart(Context context, String title, String url) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(URL, url);
        context.startActivity(intent);
    }
}

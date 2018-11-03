package com.example.john.weinong;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by john on 2018/9/12.
 */

public class newsweb extends Activity {
    private WebView webView;
    private String geturl;
    private ProgressBar pg1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsweb);
        geturl=getIntent().getStringExtra("weburl");
        init(geturl);

    }
    private void init(String url){
        webView = (WebView) findViewById(R.id.webview);
        pg1=(ProgressBar) findViewById(R.id.progressBar1);
        //WebView加载web资源
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(url);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient()
                                    {
                                        public void onProgressChanged(WebView view, int progress)
                                        {
                                            if(progress==100){
                                                pg1.setVisibility(View.GONE);//加载完网页进度条消失
                                            }
                                            else{
                                                pg1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                                                pg1.setProgress(progress);//设置进度值
                                            }
                                        }
                                    }
        );

    }
}

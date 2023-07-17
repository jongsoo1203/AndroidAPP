package com.androidhanaroadcom.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.androidhanaroadcom.myapplication.R;

public class MainActivity extends AppCompatActivity {
    private WebView webView; // Declare a WebView variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // layout에 있는 activity_main.xml파일을 가져온다
        webView = findViewById(R.id.webview); // 여기있는 webview를 layout에 있는 webview로 업데이트
        webView.setWebViewClient(new MywebClient()); // webview에 이벤트들을 담을 MywebClient만듬
        webView.loadUrl("https://hanaro12.cafe24.com/"); // website 주소 가져오기
        WebSettings webSettings = webView.getSettings(); // Get the WebSettings for the WebView
        webSettings.setJavaScriptEnabled(true); // Enable JavaScript in the WebView
    }

    private class MywebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon); // 웹 들어갔을때 팝업창들 뛰어준다.
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            /*
            return super.shouldOverrideUrlLoading(view, request);
            이 shouldOverrideUrlLoading API에 있는 아래 코드들 다 없에고 위에있는 return statement만
            써도 괜찮음. 근데 이렇게 길게 쓴 이유는 좀더 세세하게 관리하기 위해서 직접 쓴거임.
             */
            String url = request.getUrl().toString();
            // 들어갈 url에 특별한 condition이 있는지 확인. 있으면 따로 실행
            if (url.contains("webzine1.jsp")) {
                // Open the URL in a new browser tab.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            } else {
                // Load the URL in the WebView.
                view.loadUrl(url);
                return false;
            }
        }
    }

    // 뒤로가기 버튼
    @Override
    public void onBackPressed() {
        // 뒤로가기 눌렀으면 뒤로가
        if(webView.isFocused() && webView.canGoBack()) {
            webView.goBack();
        } else {
            // 아니면 닫아.
            super.onBackPressed();
        }
    }
}
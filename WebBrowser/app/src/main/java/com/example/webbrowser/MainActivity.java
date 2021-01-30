package com.example.webbrowser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private History currentUrl = new History("https://usu.edu");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout mainLayout = new LinearLayout(this);
        final LinearLayout browserBarLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        final WebView webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(currentUrl.getUrl());


        // ADDRESS BAR
        final AppCompatEditText browserEditText = new AppCompatEditText(this);
        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        browserEditText.setLayoutParams(params);
        browserEditText.setText(currentUrl.getUrl(), TextView.BufferType.EDITABLE);

        // BACK BUTTON
        AppCompatButton backButton = new AppCompatButton(this);
        backButton.setText("<");
        backButton.setLayoutParams(new LinearLayout.LayoutParams(100, ViewGroup.LayoutParams.MATCH_PARENT));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUrl.getBack() != null) {
                    currentUrl = currentUrl.getBack();
                    webView.loadUrl(currentUrl.getUrl());
                    browserEditText.setText(currentUrl.getUrl(), TextView.BufferType.EDITABLE);
                }
            }
        });

        // FORWARD BUTTON
        AppCompatButton forwardButton = new AppCompatButton(this);
        forwardButton.setText(">");
        forwardButton.setLayoutParams(new LinearLayout.LayoutParams(100, ViewGroup.LayoutParams.MATCH_PARENT));
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUrl.getForward() != null) {
                    currentUrl = currentUrl.getForward();
                    webView.loadUrl(currentUrl.getUrl());
                    browserEditText.setText(currentUrl.getUrl(), TextView.BufferType.EDITABLE);
                }
            }
        });

        // GO BUTTON
        AppCompatButton goButton = new AppCompatButton(this);
        goButton.setText("GO");
        goButton.setLayoutParams(new LinearLayout.LayoutParams(150, ViewGroup.LayoutParams.MATCH_PARENT));
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentUrl = new History(currentUrl, browserEditText.getText().toString());
                webView.loadUrl(currentUrl.getUrl());
                browserEditText.setText(currentUrl.getUrl(), TextView.BufferType.EDITABLE);
            }
        });

        browserBarLayout.addView(backButton);
        browserBarLayout.addView(forwardButton);
        browserBarLayout.addView(browserEditText);
        browserBarLayout.addView(goButton);

        mainLayout.addView(browserBarLayout);
        mainLayout.addView(webView);

        setContentView(mainLayout);













    }
}
package com.example.virtuallibrary;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.wang.avi.AVLoadingIndicatorView;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;

public class PdfViewActivity extends Activity implements DownloadFile.Listener {
    PDFPagerAdapter adapter;
    RemotePDFViewPager remotePDFViewPager;
    AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_content);
        avi = (AVLoadingIndicatorView) findViewById(R.id.pdf_avi);
        avi.show();
        Intent intent = this.getIntent();
        String url = intent.getStringExtra("url");
        remotePDFViewPager = new RemotePDFViewPager(this, url, this);
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        avi.hide();
        String[] snaps = destinationPath.split("/");
        String fileName = snaps[snaps.length - 1];
        adapter = new PDFPagerAdapter(this, fileName);
        remotePDFViewPager.setAdapter(adapter);
        setContentView(remotePDFViewPager);
    }

    @Override
    public void onFailure(Exception e) {
        System.out.println("fail");
    }

    @Override
    public void onProgressUpdate(int progress, int total) {
        System.out.println("on progress");
//        if (avi != null)
//            avi.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.close();
        }
    }
}

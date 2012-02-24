package com.github.browep.lfcp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.io.*;

public class Main extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // push some files to the files dir as an example
        try {

            for (String fileName : getAssets().list("to_copy")) {
                File outputFile = new File(getFilesDir().getPath() + "/" + fileName);

                FileOutputStream out = new FileOutputStream(outputFile);
                InputStream in = getAssets().open( "to_copy/" +fileName);
                // Transfer bytes from the input file to the output file
                copy(in,out);
                out.close();
                in.close();

            }
        } catch (Exception e) {
            Log.e("Main", "error with copying files", e);
        }

        setContentView(R.layout.main);
        WebView webView = (WebView) findViewById(R.id.webview);

        // js includes will not happen unless we enable JS
        webView.getSettings().setJavaScriptEnabled(true);

        // this allows for js alert windows
        webView.setWebChromeClient(new WebChromeClient());

        webView.loadUrl("file:///android_asset/main.html");

    }

    private static final int IO_BUFFER_SIZE = 8 * 1024;

    private static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] b = new byte[IO_BUFFER_SIZE];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

}

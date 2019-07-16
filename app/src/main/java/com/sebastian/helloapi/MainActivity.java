package com.sebastian.helloapi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.sebastian.helloapi.api.ApiController;
import com.sebastian.helloapi.api.ApiInterface;
import com.sebastian.helloapi.model.Films;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements ApiInterface.ApiListener {

    RecyclerView rvFilms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicio la cola de peticiones
        ApiController.getInstance().initRequestQueue(getApplicationContext());
        ApiInterface.getInstance().setApiListener(this, getApplicationContext());
        handleSSLHandshake();

        ApiInterface.getInstance().getFilms();
        //ApiInterface.getInstance().getVehicles();

        rvFilms = findViewById(R.id.rvFilms);

        rvFilms.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


    }

    @Override
    public void onResponse(Films films) {
        Log.e("", "");

        FilmAdapter mAdapter = new FilmAdapter(films);

        rvFilms.setAdapter(mAdapter);
    }

    @Override
    public void onErrorResponse(Object tag, String error) {

    }

    /**
     * Enables https connections
     */
    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    try {
                        certs[0].checkValidity();
                    } catch (Exception e) {
                        try {
                            throw new CertificateException("Certificate not valid or trusted.");
                        } catch (CertificateException e1) {
                            e1.printStackTrace();
                        }
                    }
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    try {
                        certs[0].checkValidity();
                    } catch (Exception e) {
                        try {
                            throw new CertificateException("Certificate not valid or trusted.");
                        } catch (CertificateException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession arg1) {

                    return true;
                }
            });
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }


}

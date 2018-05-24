package com.example.achton.outlooktest3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.aad.adal.PromptBehavior;

import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

public class MainActivity extends AppCompatActivity {
    private static final String CLIENT_ID = "eb444b37-d24e-4938-a14c-9c4502507aad";
    private static final String REDIRECT_URI = "[YOUR REDIRECT URI]";
    private static final String GRAPH_RESOURCE = "https://graph.microsoft.com";
    private static final String AUTHORITY = "https://login.microsoftonline.com/[YOUR DOMAIN]";
    private static final String LOG_TAG = "AUTH";

    private AuthenticationContext authenticationContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // Create the authentication context.
            authenticationContext = new AuthenticationContext(MainActivity.this,
                    AUTHORITY, true);

            // Acquire tokens using necessary UI.
            authenticationContext.acquireToken(MainActivity.this, GRAPH_RESOURCE, CLIENT_ID, REDIRECT_URI,
                    PromptBehavior.Always, new AuthenticationCallback<AuthenticationResult>() {
                        @Override
                        public void onSuccess(AuthenticationResult result) {
                            String idToken = result.getIdToken();
                            String accessToken = result.getAccessToken();

                            // Print tokens.
                            Log.d(LOG_TAG, "ID Token: " + idToken);
                            Log.d(LOG_TAG, "Access Token: " + accessToken);
                        }

                        @Override
                        public void onError(Exception exc) {
                            // TODO: Handle error
                        }
                    });

        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the authentication context.
        if (authenticationContext != null) {
            authenticationContext.onActivityResult(requestCode, resultCode, data);
        }
    }
}
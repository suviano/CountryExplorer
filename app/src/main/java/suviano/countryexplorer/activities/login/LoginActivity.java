package suviano.countryexplorer.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import suviano.countryexplorer.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private CallbackManager callbackManager;

    private ProfilePictureView profilePicture;
    private TextView username;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        configureActionBar();

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_btn);
        profilePicture = (ProfilePictureView) findViewById(R.id.profile_picture);
        profilePicture.setCropped(true);
        profilePicture.setPresetSize(-4);

        username = (TextView) findViewById(R.id.profile_username);
        email = (TextView) findViewById(R.id.profile_email);

        callbackManager = CallbackManager.Factory.create();

        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.wtf(TAG, "Success");
                updateUi();
            }

            @Override
            public void onCancel() {
                Log.wtf(TAG, "Cancelled");
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.wtf(TAG, String.format("%s\n%s", e.getCause(), e.getCause()));
            }
        });

        if (isLoggedIn()) {
            updateUi();
        }

        new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    final Profile oldProfile,
                    final Profile currentProfile) {
                updateUi();
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void configureActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private boolean isLoggedIn() {
        AccessToken accesstoken = AccessToken.getCurrentAccessToken();
        return !(accesstoken == null || accesstoken.getPermissions().isEmpty());
    }

    private void updateUi() {
        Profile profile = Profile.getCurrentProfile();

        if (profile != null) {

            GraphRequest graphRequest = GraphRequest.newMeRequest(
                    AccessToken.getCurrentAccessToken(), (jsonObject, graphResponse) -> {
                        if (graphResponse.getError() == null) {
                            try {
                                email.setText(graphResponse.getJSONObject().get("email").toString());
                            } catch (JSONException e) {
                                Log.wtf(TAG, e.getCause() + ":" + e.getMessage());
                            }
                        }
                    }
            );
            Bundle params = new Bundle();
            params.putString("fields", "email");
            graphRequest.setParameters(params);
            graphRequest.executeAsync();

            profilePicture.setProfileId(profile.getId());
            username.setText(String.format("%s %s", profile.getFirstName(), profile.getLastName()));
        } else {
            profilePicture.setProfileId(null);
            username.setText(R.string.not_logged);
            email.setText("");
        }
    }

    private Bundle facebookData(@NonNull JSONObject object) {
        try {
            Bundle bundle = new Bundle();
            if (object.has("email")) {
                bundle.putString("email", object.getString("email"));
            }
            return bundle;
        } catch (JSONException e) {
            Log.w(TAG, e.getCause() + ":" + e.getLocalizedMessage());
        }

        return null;
    }
}

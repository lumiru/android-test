package in.turp.persistancetp.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.turp.persistancetp.R;
import in.turp.persistancetp.dao.DAO;
import in.turp.persistancetp.dao.DatabaseHelper;
import in.turp.persistancetp.data.Magasin;
import in.turp.persistancetp.data.ReleveProduit;
import in.turp.persistancetp.data.Visite;
import in.turp.persistancetp.service.AuthService;
import in.turp.persistancetp.service.ExportService;
import in.turp.persistancetp.service.ImportService;
import in.turp.persistancetp.service.data.VisiteSyncObject;
import in.turp.persistancetp.util.DateTypeAdapter;
import in.turp.persistancetp.util.SimpleDate;
import in.turp.persistancetp.util.SimpleDateTypeAdapter;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity implements LoaderCallbacks<Cursor> {
    private static final byte AUTHENTICATION_ERROR = 0,
                              AUTHENTICATION_SUCCESS = 1,
                              AUTHENTICATION_FAILED = 2;
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), MODE_PRIVATE
        );
        String username = prefs.getString(getString(R.string.username_key), "");

        setContentView(R.layout.activity_login);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        if(username != null && username.equals("")) {
            init();
        }
        else {
            String password = prefs.getString(getString(R.string.password_key), "");
            // Si les identifiants sont incorrects, on demande les nouveaux identifiants à l'utilisateur
            if(authenticate(username, password) == AUTHENTICATION_FAILED) {
                init();
            }
            // S'il y a une erreur (ou si c'est ok),
            // on considère que l'utilisateur peut continuer sur les données locales
            else {
                showProgress(true);
                mAuthTask = new UserLoginTask(username, password);
                mAuthTask.execute(true);
                //startMainActivity();
                //init();
            }
        }
    }

    private void init() {
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute();
        }
    }

    private boolean isEmailValid(String email) {
        return email.length() > 4;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MagasinListActivity.class);
        startActivity(intent);
        finish();
    }

    private <T> T getRestService(Class<T> klass) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(SimpleDate.class, new SimpleDateTypeAdapter())
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(getString(R.string.webserver_url))
                .setConverter(new GsonConverter(gson))
                .build();

        return adapter.create(klass);
    }

    private byte authenticate(String login, String password) {
        AuthService service = getRestService(AuthService.class);
        String token;

        try {
            AuthService.AccessToken response = service.login(new AuthService.Utilisateur(login, password));
            token = "token " + response.getToken();
        }
        catch (RetrofitError e) {
            if(e.getResponse() != null && e.getResponse().getStatus() == 400) {
                return AUTHENTICATION_FAILED;
            }
            else {
                e.printStackTrace();
                return AUTHENTICATION_ERROR;
            }
        }

        SharedPreferences prefs = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), MODE_PRIVATE
        );
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.username_key), login);
        editor.putString(getString(R.string.password_key), password);
        editor.putString(getString(R.string.access_token), token);

        // FIXME truc
        /*/
        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        helper.onUpgrade(db, 0, 0);
        /**/

        String lastUpdate =
            /**/ prefs.getString(getString(R.string.last_update_key), "1901-01-01T00:00:00");
            /*/ "1901-01-01T00:00:00"; /**/
        if(updateDatabase(lastUpdate, token)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd\'T\'hh:mm:ss", Locale.FRANCE);
            editor.putString(getString(R.string.last_update_key), format.format(new Date()));
        }

        editor.apply();

        return AUTHENTICATION_SUCCESS;
    }

    private boolean updateDatabase(String lastUpdate, String token) {
        ExportService exportService = getRestService(ExportService.class);
        ImportService importService = getRestService(ImportService.class);
        String sqlLastUpdate = lastUpdate.replace('T', ' ');

        try {
            // Get all data from last update
            DAO<Magasin> magasinDAO = new DAO<>(getApplicationContext(), Magasin.class);
            List<Magasin> magasins = magasinDAO.get("date_modification", ">", sqlLastUpdate);
            if(magasins.size() > 0) {
                exportService.save(token, magasins);
            }

            DAO<Visite> visiteDAO = new DAO<>(getApplicationContext(), Visite.class, false);
            DAO<ReleveProduit> releveProduitDAO = new DAO<>(getApplicationContext(), ReleveProduit.class, false);
            List<Visite> visites = visiteDAO.get("date_modification", ">", sqlLastUpdate);
            List<ReleveProduit> relevesProduit;
            int wid;
            List<Visite> exportedVisites = new ArrayList<>();

            for (Visite visite : visites) {
                wid = visite.getWid();
                if(wid != Visite.WID_SYNC_ONCE) {
                    wid = visite.getId();
                }

                visite = exportService.save(token, new VisiteSyncObject(visite));

                // On conserve les visites nouvellement importées pour mettre à jour les liens avec
                // les relevés pour l'export.
                if(wid != Visite.WID_SYNC_ONCE) {
                    visite.setWid(wid);
                    exportedVisites.add(visite);
                }
            }

            // On modifie les liens en partant de la fin pour éviter les conflits
            for (int i = exportedVisites.size() - 1; i >= 0; --i) {
                Visite visite = exportedVisites.get(i);

                // Inutile de faire quoi que ce soit si l'ID n'a pas changé
                if (visite.getWid() != visite.getId()) {
                    // Une requête SQL, des fois, c'est plus simple :'°
                    relevesProduit = releveProduitDAO.get("visite", visite.getWid());
                    for (ReleveProduit releve : relevesProduit) {
                        releve.setVisite(visite.getId());
                        releveProduitDAO.save(releve);
                    }

                    wid = visite.getWid();
                    visite.setWid(Visite.WID_SYNC_ONCE);
                    visiteDAO.save(visite, wid);
                }
            }


            relevesProduit = releveProduitDAO.get("date_modification", ">", sqlLastUpdate);

            int oldId;
            for (ReleveProduit releveProduit : relevesProduit) {
                if(releveProduit.getWid() == ReleveProduit.WID_NEW) {
                    releveProduit.setWid(releveProduit.getId());
                }
                oldId = releveProduit.getId();
                if(releveProduit.getWid() != ReleveProduit.WID_SYNC_ONCE) {
                    releveProduit.setId(0);
                }
                wid = releveProduit.getWid();

                releveProduit = exportService.save(token, releveProduit);

                if(wid != ReleveProduit.WID_SYNC_ONCE) {
                    releveProduit.setWid(ReleveProduit.WID_SYNC_ONCE);
                }
                if(oldId == 0) {
                    oldId = releveProduit.getId();
                }

                releveProduitDAO.save(releveProduit, oldId);
            }

            // Import all data
            DAO.save(getApplicationContext(), importService.getLastMagasins(token, lastUpdate));
            DAO.save(getApplicationContext(), importService.getLastVisites(token, lastUpdate));
            DAO.save(getApplicationContext(), importService.getLastAssortiments(token, lastUpdate));
            DAO.save(getApplicationContext(), importService.getLastClients(token, lastUpdate));
            DAO.save(getApplicationContext(), importService.getLastEnseignes(token, lastUpdate));
            DAO.save(getApplicationContext(), importService.getLastFamilles(token, lastUpdate));
            DAO.save(getApplicationContext(), importService.getLastGammes(token, lastUpdate));
            DAO.save(getApplicationContext(), importService.getLastGroupes(token, lastUpdate));
            DAO.save(getApplicationContext(), importService.getLastProduits(token, lastUpdate));
            DAO.save(getApplicationContext(), importService.getLastRelevesProduit(token, lastUpdate));
        }
        catch (RetrofitError e) {
            // OK, retry later
            TypedInput input = e.getResponse().getBody();
            if(input instanceof TypedByteArray) {
                String strInput = new String(((TypedByteArray) input).getBytes());
                Log.i("WebService Error", strInput, e);
            }
            return false;
        }

        // TODO Remove all deleted data from local database

        return true;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Boolean, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Boolean... params) {
            // Si on n'a pas de réponse positive du serveur, on ne peut pas continuer
            byte authentication = authenticate(mEmail, mPassword);

            if(params.length > 0 && params[0]) {
                return authentication != AUTHENTICATION_FAILED;
            }
            else {
                return authentication == AUTHENTICATION_SUCCESS;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                startMainActivity();
            } else {
                if(mPasswordView == null) {
                    init();
                }
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}


package br.com.developen.pdv.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import br.com.developen.pdv.R;
import br.com.developen.pdv.jersey.CompanyBean;
import br.com.developen.pdv.jersey.ConfigurationBean;
import br.com.developen.pdv.jersey.CompanyDeviceDatasetBean;
import br.com.developen.pdv.jersey.DeviceBean;
import br.com.developen.pdv.jersey.StringBean;
import br.com.developen.pdv.jersey.TokenBean;
import br.com.developen.pdv.room.UserModel;
import br.com.developen.pdv.task.ConfigureAsyncTask;
import br.com.developen.pdv.task.GetCompaniesAsyncTask;
import br.com.developen.pdv.task.LocalAuthenticationAsyncTask;
import br.com.developen.pdv.task.RetrieveDeviceAliasBySerialNumberAsyncTask;
import br.com.developen.pdv.task.ServerAuthenticationAsyncTask;
import br.com.developen.pdv.task.ServerSwitchCompanyAsyncTask;
import br.com.developen.pdv.utils.App;
import br.com.developen.pdv.utils.Constants;
import br.com.developen.pdv.utils.DB;
import br.com.developen.pdv.utils.DBSync;
import br.com.developen.pdv.utils.Messaging;

public class ConfigurationActivity extends AppCompatActivity implements
        RetrieveDeviceAliasBySerialNumberAsyncTask.Listener,
        ServerAuthenticationAsyncTask.Listener,
        ServerSwitchCompanyAsyncTask.Listener,
        LocalAuthenticationAsyncTask.Listener,
        GetCompaniesAsyncTask.Listener,
        ViewPager.OnPageChangeListener,
        ConfigureAsyncTask.Listener,
        View.OnClickListener {


    private static final int WELCOME_STEP = 0;

    private static final int USER_STEP = 1;

    private static final int COMPANY_STEP = 2;

    private static final int DEVICE_STEP = 3;

    private static final int FINISH_STEP = 4;


    private ViewPager viewPager;

    private LinearLayout dotsLayout;

    private Button previewButton, nextButton;

    private View progressView;

    private View configurationFormView;

    private ConfigurationBean configurationBean;

    private boolean spinnerInitialized = false;

    private String userLogin;

    private String decryptedPassword;

    private Integer loggedInCompany;

    private int[] layouts;


    @SuppressLint("HardwareIds")
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_configuration);


        //INICIALIZA COMPONENTES DA INTERFACE
        viewPager = findViewById(R.id.activity_configuration_view_pager);

        dotsLayout = findViewById(R.id.activity_configuration_layout_dots);

        previewButton = findViewById(R.id.activity_account_preview_button);

        nextButton = findViewById(R.id.activity_account_next_button);

        progressView = findViewById(R.id.activity_configuration_progress);

        configurationFormView = findViewById(R.id.activity_configuration_body);

        layouts = new int[]{
                R.layout.activity_configuration_welcome_step,
                R.layout.activity_configuration_user_step,
                R.layout.activity_configuration_company_step,
                R.layout.activity_configuration_device_step,
                R.layout.activity_configuration_finish_step};

        addBottomDots(0);

        viewPager.setAdapter(new MyViewPagerAdapter());

        viewPager.addOnPageChangeListener(this);

        previewButton.setOnClickListener(this);

        nextButton.setOnClickListener(this);


        //INICIALIZA A VARIAVEL DISPOSITIVO
        getConfigurationBean().setDeviceSerialNumber(Settings.Secure.getString(
                getBaseContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));

        getConfigurationBean().setDeviceManufacturer(Build.MANUFACTURER.toUpperCase());

        getConfigurationBean().setDeviceModel(Build.MODEL);


    }


    public ConfigurationBean getConfigurationBean() {

        if (configurationBean==null)

            configurationBean = new ConfigurationBean();

        return configurationBean;

    }


    // VIEW ////////////////////////////////////////////////////


    private void showProgress(final boolean show) {

        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        configurationFormView.setVisibility(show ? View.GONE : View.VISIBLE);

        configurationFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {

            public void onAnimationEnd(Animator animation) {

                configurationFormView.setVisibility(show ? View.GONE : View.VISIBLE);

            }

        });

        progressView.setVisibility(show ? View.VISIBLE : View.GONE);

        progressView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {

            public void onAnimationEnd(Animator animation) {

                progressView.setVisibility(show ? View.VISIBLE : View.GONE);

            }

        });

    }


    private void addBottomDots(int currentPage) {

        TextView[] dotsTextView = new TextView[layouts.length];

        dotsLayout.removeAllViews();

        for (int i = 0; i < dotsTextView.length; i++) {

            dotsTextView[i] = new TextView(this);

            dotsTextView[i].setText(Html.fromHtml("&#8226;"));

            dotsTextView[i].setTextSize(35);

            dotsTextView[i].setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorGreenDark));

            dotsLayout.addView(dotsTextView[i]);

        }

        int selectedDot;

        switch (currentPage){

            case USER_STEP:

                selectedDot = 1;

                break;

            case COMPANY_STEP:

                selectedDot = 2;

                break;

            case DEVICE_STEP:

                selectedDot = 3;

                break;

            case FINISH_STEP:

                selectedDot = 4;

                break;

            default:

                selectedDot = currentPage;

                break;

        }

        dotsTextView[selectedDot].setTextColor(
                ContextCompat.getColor(getBaseContext(),
                        R.color.colorWhite));

    }



    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        MyViewPagerAdapter() {}

        @NonNull
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            assert layoutInflater != null;

            View view = layoutInflater.inflate(layouts[position], container, false);

            container.addView(view);

            return view;

        }

        public int getCount() {

            return layouts.length;

        }

        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {

            return view == obj;

        }

        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

            View view = (View) object;

            container.removeView(view);

        }

    }


    // VALIDATORS ////////////////////////////////////////////////////////////////////////////////////


    private void validateLoginStep() {

        EditText loginEditText = findViewById(R.id.activity_configuration_user_login_edittext);

        EditText passwordEditText = findViewById(R.id.activity_configuration_user_password_edittext);

        loginEditText.setError(null);

        passwordEditText.setError(null);

        String login = loginEditText.getText().toString();

        String password = passwordEditText.getText().toString();

        boolean cancel = false;

        View focusView = null;

        if (TextUtils.isEmpty(password)) {

            passwordEditText.setError(getString(R.string.error_field_required));

            focusView = passwordEditText;

            cancel = true;

        } else {

            if(isValidPassword(password)){

                passwordEditText.setError(getString(R.string.error_invalid_password));

                focusView = passwordEditText;

                cancel = true;

            }

        }

        if (TextUtils.isEmpty(login)) {

            loginEditText.setError(getString(R.string.error_field_required));

            focusView = loginEditText;

            cancel = true;

        } else {

            if(isValidEmail(login)){

                loginEditText.setError(getString(R.string.error_invalid_email));

                focusView = loginEditText;

                cancel = true;

            }

        }

        if (cancel) {

            focusView.requestFocus();

        } else {

            userLogin = login;

            decryptedPassword = password;

            new ServerAuthenticationAsyncTask<>(this).execute(login, password);

        }

    }


    private void validateDeviceStep() {

        EditText aliasEditText = findViewById(R.id.activity_configuration_device_alias_edittext);

        aliasEditText.setError(null);

        String alias = aliasEditText.getText().toString();

        boolean cancel = false;

        View focusView = null;

        if (TextUtils.isEmpty(alias)) {

            aliasEditText.setError(getString(R.string.error_field_required));

            focusView = aliasEditText;

            cancel = true;

        } else {

            if(isValidAlias(alias)){

                aliasEditText.setError(getString(R.string.error_invalid_alias));

                focusView = aliasEditText;

                cancel = true;

            }

        }

        if (cancel) {

            focusView.requestFocus();

        } else {

            getConfigurationBean().setDeviceAlias(alias);

            new ConfigureAsyncTask<>(this).execute(getConfigurationBean());

        }

    }


    private boolean isValidEmail(String email) {

        return !email.contains("@") || !email.contains(".");

    }


    private boolean isValidPassword(String passoword) {

        return passoword.trim().length() < 5;

    }


    private boolean isValidAlias(String deviceAlias) {

        return deviceAlias.trim().length() < 5;

    }


    public void onClick(View v) {

        switch (v.getId()){

            //BOTAO VOLTAR
            case R.id.activity_account_preview_button: {

                int currentPage = viewPager.getCurrentItem();

                switch (currentPage){

                    case WELCOME_STEP:

                        break;

                    case USER_STEP:

                        viewPager.setCurrentItem(WELCOME_STEP, true);

                        break;

                    case COMPANY_STEP:

                        viewPager.setCurrentItem(USER_STEP, true);

                        break;

                    case DEVICE_STEP:

                        viewPager.setCurrentItem(COMPANY_STEP, true);

                        break;

                }

                break;

            }

            //BOTAO AVANCAR
            case R.id.activity_account_next_button: {

                switch (viewPager.getCurrentItem()) {

                    case WELCOME_STEP:

                        viewPager.setCurrentItem(USER_STEP, true);

                        break;

                    case USER_STEP:

                        validateLoginStep();

                        break;

                    case COMPANY_STEP:

                        viewPager.setCurrentItem(DEVICE_STEP, true);

                        break;

                    case DEVICE_STEP:

                        validateDeviceStep();

                        break;

                    case FINISH_STEP:

                        new LocalAuthenticationAsyncTask<>(this).
                                execute(userLogin, decryptedPassword);

                        break;

                }

                break;

            }

        }

    }


    //PAGINACAO
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    public void onPageSelected(int position) {

        addBottomDots(position);

        switch (position){

            case USER_STEP:

                final EditText loginPasswordEditText = findViewById(R.id.activity_configuration_user_password_edittext);

                loginPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                        if (i == EditorInfo.IME_ACTION_DONE) {

                            InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                            Objects.requireNonNull(imm).hideSoftInputFromWindow(textView.getWindowToken(), 0);

                            nextButton.callOnClick();

                            return true;

                        }

                        return false;

                    }

                });

                break;

            case COMPANY_STEP:

                spinnerInitialized = false;

                new GetCompaniesAsyncTask<>(this).execute();

                break;

            case DEVICE_STEP:

                final EditText aliasEditText = findViewById(R.id.activity_configuration_device_alias_edittext);

                aliasEditText.setText(getConfigurationBean().getDeviceAlias());

                aliasEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                        if (i == EditorInfo.IME_ACTION_DONE) {

                            InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                            Objects.requireNonNull(imm).hideSoftInputFromWindow(textView.getWindowToken(), 0);

                            nextButton.callOnClick();

                            return true;

                        }

                        return false;

                    }

                });

                new RetrieveDeviceAliasBySerialNumberAsyncTask<>(this).
                        execute(getConfigurationBean().getDeviceSerialNumber());

                break;

            case FINISH_STEP:

                break;

        }

        if (position == WELCOME_STEP || position == FINISH_STEP)

            previewButton.setVisibility(View.INVISIBLE);

        else

            previewButton.setVisibility(View.VISIBLE);

        if (position == layouts.length - 1) {

            nextButton.setText(getString(R.string.finish));

        } else {

            if (position == WELCOME_STEP)

                nextButton.setText(getString(R.string.start));

            else

                nextButton.setText(getString(R.string.next));

        }

    }


    public void onPageScrollStateChanged(int state) {}


    public void onServerAuthenticationPreExecute() {

        showProgress(true);

    }


    public void onServerAuthenticationSuccess(TokenBean tokenBean) {


        SharedPreferences preferences = getSharedPreferences(
                Constants.SHARED_PREFERENCES_NAME, 0);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(Constants.TOKEN_PROPERTY, tokenBean.getIdentifier());

        editor.apply();

        loggedInCompany = tokenBean.getCompany().getIdentifier();

        viewPager.setCurrentItem(COMPANY_STEP, true);

        showProgress(false);


    }


    public void onServerAuthenticationFailure(Messaging messaging) {

        showProgress(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(configurationFormView.getContext());

        builder.setMessage(TextUtils.join("\n", messaging.getMessages()));

        builder.setTitle(R.string.dlg_title_login_failure);

        builder.setPositiveButton(R.string.try_again,

                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }

                });

        AlertDialog alert = builder.create();

        alert.show();

    }


    public void onGetCompanyPreExecute() {}


    public void onGetCompanySuccess(List<CompanyBean> list) {

        Spinner companySpinner = findViewById(R.id.activity_configuration_company_spinner);

        companySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!spinnerInitialized){

                    spinnerInitialized = true;

                } else {

                    new ServerSwitchCompanyAsyncTask<>(ConfigurationActivity.this)
                            .execute(((CompanyBean) parent.getItemAtPosition(position)).getIdentifier());

                }

            }

            public void onNothingSelected(AdapterView<?> parent) {}

        });

        ArrayAdapter<CompanyBean> adapter =
                new ArrayAdapter<>(ConfigurationActivity.this, R.layout.activity_configuration_company_spinner, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        companySpinner.setAdapter(adapter);

        if (loggedInCompany != null){

            CompanyBean companyBean = new CompanyBean();

            companyBean.setIdentifier(loggedInCompany);

            int index = adapter.getPosition(companyBean);

            if (index != -1)

                companySpinner.setSelection(index, false);

        }

        showProgress(false);

    }


    public void onGetCompanyFailure(Messaging messaging) {

        showProgress(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(configurationFormView.getContext());

        builder.setMessage(TextUtils.join("\n", messaging.getMessages()));

        builder.setTitle(R.string.dlg_title_request_failure);

        builder.setPositiveButton(R.string.try_again,

                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        new GetCompaniesAsyncTask<>(ConfigurationActivity.this).execute();

                    }

                });

        AlertDialog alert = builder.create();

        alert.show();

    }


    public void onServerSwitchCompanyPreExecute() {

        showProgress(true);

    }


    public void onServerSwitchCompanySuccess(TokenBean tokenBean) {


        loggedInCompany = tokenBean.getCompany().getIdentifier();

        showProgress(false);


    }


    public void onServerSwitchCompanyFailure(Messaging messaging) {


        showProgress(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(configurationFormView.getContext());

        builder.setMessage(TextUtils.join("\n", messaging.getMessages()));

        builder.setTitle(R.string.dlg_title_request_failure);

        builder.setPositiveButton(R.string.try_again,

                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }

                });

        AlertDialog alert = builder.create();

        alert.show();


    }


    public void onRetrieveDeviceAliasBySerialNumberPreExecute() {

        showProgress(true);

    }


    public void onRetrieveDeviceAliasBySerialNumberSuccess(StringBean stringBean) {


        getConfigurationBean().setDeviceAlias(stringBean.getValue());

        EditText aliasEditText = findViewById(R.id.activity_configuration_device_alias_edittext);

        aliasEditText.setText(getConfigurationBean().getDeviceAlias());

        showProgress(false);


    }


    public void onRetrieveDeviceAliasBySerialNumberFailure(Messaging messaging) {


        String alias = getConfigurationBean().getDeviceManufacturer() + "/" + getConfigurationBean().getDeviceModel();

        getConfigurationBean().setDeviceAlias(alias);

        EditText aliasEditText = findViewById(R.id.activity_configuration_device_alias_edittext);

        aliasEditText.setText(getConfigurationBean().getDeviceAlias());

        showProgress(false);


    }


    public void onConfigurePreExecute() {

        showProgress(true);

    }


    public void onConfigureSuccess(CompanyDeviceDatasetBean companyDeviceDatasetBean) {


        CompanyBean companyBean = companyDeviceDatasetBean.getCompany();

        SharedPreferences preferences = getSharedPreferences(
                Constants.SHARED_PREFERENCES_NAME, 0);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(Constants.CURRENT_SALE_NUMBER_PROPERTY, 0);

        editor.putInt(Constants.COMPANY_IDENTIFIER_PROPERTY, companyBean.getIdentifier());

        editor.putBoolean(Constants.COMPANY_ACTIVE_PROPERTY, companyBean.getActive());

        editor.putString(Constants.COMPANY_DENOMINATION_PROPERTY, companyBean.getDenomination());

        editor.putString(Constants.COMPANY_FANCYNAME_PROPERTY, companyBean.getFancyName());

        if (companyBean.getCouponTitle() != null)

            switch (companyBean.getCouponTitle()){

                case Constants.COMPANY_FANCYNAME_PROPERTY:

                    editor.putString(Constants.COUPON_TITLE_PROPERTY, companyBean.getFancyName());

                    break;

                default:

                    editor.putString(Constants.COUPON_TITLE_PROPERTY, companyBean.getCouponTitle());

            }

        if (companyBean.getCouponSubtitle() != null)

            switch (companyBean.getCouponSubtitle()){

                case Constants.COMPANY_FANCYNAME_PROPERTY:

                    editor.putString(Constants.COUPON_SUBTITLE_PROPERTY, companyBean.getFancyName());

                    break;

                default:

                    editor.putString(Constants.COUPON_SUBTITLE_PROPERTY, companyBean.getCouponSubtitle());

            }

        DeviceBean deviceBean = companyDeviceDatasetBean.getDevice();

        editor.putInt(Constants.DEVICE_IDENTIFIER_PROPERTY, deviceBean.getIdentifier());

        editor.putBoolean(Constants.DEVICE_ACTIVE_PROPERTY, deviceBean.getActive() && companyDeviceDatasetBean.getAllow());

        editor.putString(Constants.DEVICE_ALIAS_PROPERTY, companyDeviceDatasetBean.getAlias());

        editor.putBoolean(Constants.DEVICE_CONFIGURED_PROPERTY, true);

        editor.apply();


        DB database = DB.getInstance(getBaseContext());

        new DBSync(database).syncDataset(companyDeviceDatasetBean);


        editor = preferences.edit();

        editor.putInt(Constants.CURRENT_SALE_NUMBER_PROPERTY, database.saleDAO().getCurrentSaleNumber());

        editor.commit();


        viewPager.setCurrentItem(FINISH_STEP, true);

        showProgress(false);


    }


    public void onConfigureFailure(Messaging messaging) {


        showProgress(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(configurationFormView.getContext());

        builder.setMessage(TextUtils.join("\n", messaging.getMessages()));

        builder.setTitle(R.string.dlg_title_request_failure);

        builder.setPositiveButton(R.string.try_again,

                (dialog, id) -> dialog.cancel());

        AlertDialog alert = builder.create();

        alert.show();


    }


    public void onLocalAuthenticationPreExecute() {

        showProgress(true);

    }


    public void onLocalAuthenticationSuccess(UserModel userModel) {


        SharedPreferences preferences = getSharedPreferences(
                Constants.SHARED_PREFERENCES_NAME, 0);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(Constants.USER_IDENTIFIER_PROPERTY, userModel.getIdentifier());

        editor.putString(Constants.USER_NAME_PROPERTY, userModel.getName());

        editor.putString(Constants.USER_LOGIN_PROPERTY, userModel.getLogin());

        editor.putString(Constants.USER_NUMERIC_PASSWORD_PROPERTY, userModel.getNumericPassword());

        editor.putString(Constants.USER_LEVEL_PROPERTY, userModel.getLevel().toString());

        editor.apply();

        //INICIALIZA AS VARIAVEIS DO SISTEMA
        App.getInstance().initialize();

        Intent intent = new Intent(ConfigurationActivity.this, MainActivity.class);

        startActivity(intent);

        finish();


    }


    public void onLocalAuthenticationFailure(Messaging messaging) {


        showProgress(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(configurationFormView.getContext());

        builder.setMessage(TextUtils.join("\n", messaging.getMessages()));

        builder.setTitle(R.string.dlg_title_login_failure);

        builder.setPositiveButton(
                R.string.try_again,
                (dialog, id) -> {

                    Intent intent = new Intent(ConfigurationActivity.this, LoginActivity.class);

                    startActivity(intent);

                    finish();

                });

        AlertDialog alert = builder.create();

        alert.show();


    }


}
package umn.ac.id.a33081_teofilusyohaneswowiling;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText etUserName, etPassword;
    Button btnLoginLogin;
    Dialog erorMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btnLoginLogin = findViewById(R.id.btnLoginLogin);
        erorMessages = new Dialog(this);

        btnLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String un = "uasmobile";
                String pw = "uasmobilegenap";
                String username = etUserName.getText().toString();
                String password = etPassword.getText().toString();
                if (username.isEmpty() || password.isEmpty()){
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.erorlogin, (ViewGroup) findViewById(R.id.cLayoutErrorLogin));
                    TextView tvErrorMsg = (TextView) layout.findViewById(R.id.tvErrorMsg);
                    if (username.isEmpty()){
                        tvErrorMsg.setText("Username Kosong!");
                    } else if (password.isEmpty()){
                        tvErrorMsg.setText("Password Kosong!");
                    } else {
                        tvErrorMsg.setText("Username dan Password Kosong");
                    }
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }

                else if (username.contentEquals(un) && password.contentEquals(pw) ){
                    Intent loginLogin = new Intent(LoginActivity.this, ListLaguActivity.class);
                    startActivity(loginLogin);

                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.successlogin, (ViewGroup) findViewById(R.id.cLayoutSuccessLogin));

                    TextView tvSuccessMsg = (TextView) layout.findViewById(R.id.tvSuccesMsg);
                    tvSuccessMsg.setText("Selamat Datang");
                    TextView tvSuccessMsg2 = (TextView) layout.findViewById(R.id.tvSuccesMsg2);
                    tvSuccessMsg2.setText("Teofilus Yohanes Wowiling");
                    TextView tvSuccessMsg3 = (TextView) layout.findViewById(R.id.tvSuccesMsg3);
                    tvSuccessMsg3.setText("00000033081");

                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
                
                else{
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.erorlogin, (ViewGroup) findViewById(R.id.cLayoutErrorLogin));

                    TextView tvErrorMsg = (TextView) layout.findViewById(R.id.tvErrorMsg);
                    tvErrorMsg.setText("Username atau Password Salah!");
                    tvErrorMsg.setTextSize(10);
                    
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });
    }
}
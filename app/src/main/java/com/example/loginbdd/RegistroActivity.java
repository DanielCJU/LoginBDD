package com.example.loginbdd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginbdd.DB.DBUserAdapter;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        TextView Existente = findViewById(R.id.yaTengoCuenta);
        Button Registrar = findViewById(R.id.btnReg);
        final EditText txtUsername = findViewById(R.id.usuarioReg);
        final EditText txtPassword = findViewById(R.id.contraseReg);
        final EditText txtCpassword = findViewById(R.id.confirmContraseReg);

        Existente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (RegistroActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Registrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                String cpassword = txtCpassword.getText().toString();

                try{
                    if(username.length() > 0 && password.length() >0){
                        if(password.equals(cpassword)){
                            DBUserAdapter dbUser = new DBUserAdapter(RegistroActivity.this);
                            dbUser.open();
                            if(dbUser.Login(username, password)){
                                Toast.makeText(RegistroActivity.this,"Cuenta existente", Toast.LENGTH_LONG).show();
                            } else{
                                dbUser.AddUser(username,password);
                                Toast.makeText(RegistroActivity.this,"Cuenta creada, ahora puede iniciar sesión", Toast.LENGTH_LONG).show();
                                dbUser.close();
                                finish();
                            }
                        }
                    } else {
                        Toast.makeText(RegistroActivity.this,"Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                    }
                } catch(Exception e){
                    Toast.makeText(RegistroActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
package com.beatandbit.sagd.contadorauth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



import java.util.Arrays;
import java.util.List;

public class inicio extends AppCompatActivity {
    
    private Button btnIniciar;
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        btnIniciar= (Button)findViewById(R.id.btn_iniciar);


        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser usuarioActual = mAuth.getCurrentUser();

                if(usuarioActual == null) {

                    // Choose authentication providers
                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                            new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

                    // Create and launch sign-in intent
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    .setIsSmartLockEnabled(false)
                                    .setAllowNewEmailAccounts(false)
                                    .build(),
                            RC_SIGN_IN);
                }
                else{
                    //mandar al usuario a la actividad de sesion iniciada
                    startActivity(  new Intent(inicio.this, sesionIniciada.class ));
                    finish();
                }

            }
        });

/*
// ...
*/
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) { //ya sabemos que venimos de la actividad firebase UI
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                startActivity(new Intent( inicio.this, sesionIniciada.class));
                finish();

                // ...
            } else {
                // Sign in failed, check response for error code
                // ...

                Toast.makeText(inicio.this, "La sesion no fue iniciada exitosamente", Toast.LENGTH_SHORT).show();

            }
        }
    }

}

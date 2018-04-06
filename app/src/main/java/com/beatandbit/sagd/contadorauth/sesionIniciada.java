package com.beatandbit.sagd.contadorauth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class sesionIniciada extends AppCompatActivity {


    private Button btnSumar, btnReiniciar, btnSalir;
    private TextView tvNumero;
    private int numero;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion_iniciada);

        btnReiniciar= (Button)findViewById(R.id.btn_reiniciar);
        btnSalir=(Button)findViewById(R.id.btn_salir);
        btnSumar=(Button) findViewById(R.id.btn_sumar);
        tvNumero=(TextView)findViewById(R.id.tv_numero);
        numero=0;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference FBnumero = database.getReference("contador");

        FBnumero.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                numero= Integer.valueOf(  dataSnapshot.getValue().toString()    );
                tvNumero.setText(  dataSnapshot.getValue().toString() );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnSumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numero++;//opciaonal
                FBnumero.setValue(numero);

            }
        });

        btnReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numero=0;//opcional
                FBnumero.setValue(numero);
            }
        });


        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AuthUI.getInstance()
                        .signOut(sesionIniciada.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {

                                startActivity(new Intent(sesionIniciada.this, inicio.class  ));
                                finish();

                            }
                        });

            }
        });










    }
}

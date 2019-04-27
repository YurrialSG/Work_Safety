package com.example.worksafetymobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnSave;
    EditText edtName;
    DatabaseReference databaseReference;
    ListView listViewUsers;
    List<Funcionario> funcionarios;
    public static String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        funcionarios = new ArrayList<Funcionario>();
        //crud com o firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("funcionarios");

        btnSave = (Button)findViewById(R.id.btnSave);
        edtName = (EditText) findViewById(R.id.edtName);
        listViewUsers = (ListView) findViewById(R.id.listViewUsers);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();

                if(TextUtils.isEmpty(userId)) {
                    //cadastrar dados
                    String id = databaseReference.push().getKey();
                    Funcionario funcionario = new Funcionario(id, name);
                    databaseReference.child(id).setValue(funcionario);
                    Toast.makeText(MainActivity.this, "Funcionário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    //alterar dados
                    databaseReference.child(userId).child("name").setValue(name);
                    Toast.makeText(MainActivity.this, "Funcionário alterado com sucesso!", Toast.LENGTH_SHORT).show();
                    userId = "";
                }

                edtName.setText(null);
                edtName.requestFocus();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                funcionarios.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Funcionario funcionario = postSnapshot.getValue(Funcionario.class);
                    funcionarios.add(funcionario);
                }

                FuncList userAdapter = new FuncList(MainActivity.this, funcionarios, databaseReference, edtName);
                listViewUsers.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
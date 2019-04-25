package com.example.worksafetymobile;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class FuncList extends ArrayAdapter<Funcionario> {

    private Activity context;
    private List<Funcionario> funcionarios;
    DatabaseReference databaseReference;
    EditText edtName;

    public FuncList(@NonNull Activity context, List<Funcionario> funcionarios, DatabaseReference databaseReference, EditText edtName) {
        super(context, R.layout.layout_user_list, funcionarios);
        this.context = context;
        this.funcionarios = funcionarios;
        this.databaseReference = databaseReference;
        this.edtName = edtName;
    }

    public View getView(int pos, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_user_list, null, true);

        TextView txtName = (TextView) listViewItem.findViewById(R.id.txtName);
        Button btnDelete = (Button) listViewItem.findViewById(R.id.btnDelete);
        Button btnUpdate = (Button) listViewItem.findViewById(R.id.btnUpdate);

        final Funcionario funcionario = funcionarios.get(pos);
        txtName.setText(funcionario.getName());

        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                databaseReference.child(funcionario.getId()).removeValue();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                edtName.setText(funcionario.getName());
                MainActivity.userId = funcionario.getId();
            }
        });

        return listViewItem;
    }
}

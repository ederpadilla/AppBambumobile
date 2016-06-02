package eder.padilla.personal.works.appbambumobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import eder.padilla.personal.works.appbambumobile.Modelo.Checks;
import eder.padilla.personal.works.appbambumobile.Modelo.Empleado;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import android.support.v4.app.DialogFragment;

/**
 * Created by Eder on 20/01/2016.
 */
public class Cambiarcontraseña extends DialogFragment {
    Button cambiar;
    ImageButton cerrar;
    Bundle a;
    Realm rea;
    EditText contraseñaparaagregar;
    TextView textoeliminar;
    Empleado empleado;
    public Cambiarcontraseña(){
    }
    private RealmResults<Empleado> allEmpleados;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cambiarcontrasena, container);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        rea = Realm.getInstance(getContext());
        a = getActivity().getIntent().getExtras();
        a.getInt("id");
        allEmpleados = rea.where(Empleado.class).findAll();
        empleado = allEmpleados.get(a.getInt("id"));

        contraseñaparaagregar = (EditText) view.findViewById(R.id.passwordparacambiar);
        cambiar=(Button) view.findViewById(R.id.cambiarocntraseña);
        cerrar=(ImageButton) view.findViewById(R.id.cerrar);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rea.beginTransaction();
                empleado.setContraseña(contraseñaparaagregar.getText().toString());
                System.out.println("La nueva contraseña para " + empleado + " es " + empleado.getContraseña());
                rea.commitTransaction();

                    ((Perfil_de_Empleados) getActivity()).fragmentfinalparacambiar();
                    dismiss();
           }

        });


        return view;
    }

}

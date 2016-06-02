package eder.padilla.personal.works.appbambumobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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

/**
 * Created by Eder on 21/12/2015.
 */
public class Eliminar_empleado extends DialogFragment {
    String pass2;
    String pass="123";
    Button siguientepaso;
    ImageButton cerrar;
    Bundle a;
    Realm rea;
    EditText contraseñaparaagregar;
    TextView textoeliminar;
    Empleado empleado;
    Checks  checks;
    public Eliminar_empleado(){
    }
    private RealmResults<Empleado> allEmpleados;
    private RealmResults<Checks> allchecks;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.agregar_empleado, container);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        rea = Realm.getInstance(getContext());
        a = getActivity().getIntent().getExtras();
        a.getInt("id");
        allEmpleados = rea.where(Empleado.class).findAll();
        allchecks=rea.where(Checks.class).findAll();
        empleado = allEmpleados.get(a.getInt("id"));

        contraseñaparaagregar = (EditText) view.findViewById(R.id.passwordparaagregarempleado);
        siguientepaso=(Button) view.findViewById(R.id.siguientepasodeagregar);
        siguientepaso.setText(getResources().getString(R.string.Aceptar));
        cerrar=(ImageButton) view.findViewById(R.id.cerrar);
        textoeliminar=(TextView) view.findViewById(R.id.dialogoagregareliminar);
        textoeliminar.setText(getResources().getString(R.string.borrar_empleado));
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        siguientepaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validatePass()){
                    eliminar();
                  //  eliminarChecks();

               ((Perfil_de_Empleados)getActivity()).fragmentfinalparaeliminar();
                    dismiss();}
                else contraseñaparaagregar.setError("Contraseña incorrecta");


            }

        });


        return view;
    }

    public boolean validatePass(){

        pass2=contraseñaparaagregar.getText().toString();
        if(pass2.equals(pass)){
            startActivity(new Intent(getActivity(), MainActivity.class));
            return true;
        }
        else
        contraseñaparaagregar.setError("Contraseña incorrecta");
            System.out.println("CONTRASEÑA INCORRECTA");
        Toast.makeText(getActivity(),
                "CONTRASEÑA INCORRECTA!!!!", Toast.LENGTH_SHORT);

        return false;    }
    public void eliminar() {

        RealmQuery<Empleado> query = rea.where(Empleado.class);
        query.equalTo("correo", empleado.getCorreo());
        RealmResults<Empleado> results = query.findAll();
        rea.beginTransaction();
        Empleado currentEmpleado = results.first();
        currentEmpleado.removeFromRealm();
        results.clear();
        rea.commitTransaction();
    }
    public void eliminarChecks(){
        RealmQuery<Checks> query = rea.where(Checks.class);
        query.equalTo("correo", checks.getCorreo());
        RealmResults<Checks> results = query.findAll();

        for (Checks checks2 : results) {
            rea.beginTransaction();
            checks2.removeFromRealm();
            rea.commitTransaction();
        }
    }







}



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
import android.widget.Toast;

/**
 * Created by Eder on 10/12/2015.
 */
public class Agregar_Empleado extends DialogFragment {
    String pass2;
    String pass="123";
    Button  siguientepaso;
    ImageButton cerrar;
    public Agregar_Empleado(){

    }
    EditText contraseñaparaagregar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.agregar_empleado, container);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        contraseñaparaagregar = (EditText) view.findViewById(R.id.passwordparaagregarempleado);
        siguientepaso=(Button) view.findViewById(R.id.siguientepasodeagregar);
        cerrar=(ImageButton) view.findViewById(R.id.cerrar);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        siguientepaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatePass();
                dismiss();

            }

        });


        return view;
    }
    public boolean validatePass(){

        pass2=contraseñaparaagregar.getText().toString();
        if(pass2.equals(pass)){
            startActivity(new Intent(getActivity(), Datos_paraAgregarPersonal.class));
            Toast toast1 =
                    Toast.makeText(getActivity(),
                            "Entro", Toast.LENGTH_SHORT);
            System.out.println("Entra");
            toast1.show();


            //MainActivity a= (MainActivity)getActivity();


            //a.agregar_personal();
            return true;
        }
        else
            contraseñaparaagregar.setError("Contraseña incorrecta");
            System.out.println("CONTRASEÑA INCORRECTA");
        Toast.makeText(getActivity(),
                "CONTRASEÑA INCORRECTA!!!!", Toast.LENGTH_SHORT);

        return false;    }



}

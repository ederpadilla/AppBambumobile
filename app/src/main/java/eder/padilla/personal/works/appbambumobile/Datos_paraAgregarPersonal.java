package eder.padilla.personal.works.appbambumobile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import eder.padilla.personal.works.appbambumobile.Modelo.Checks;
import eder.padilla.personal.works.appbambumobile.Modelo.Empleado;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Eder on 12/12/2015.
 */
public class Datos_paraAgregarPersonal extends AppCompatActivity {

    EditText name, lastname, workspace, password, correo;
    Button agregar;
    ImageButton cerrar;
    CircleImageView foto;
    TextView  tomarfoto;
    Bitmap thumbnail;
    Realm realm;
    Empleado e = new Empleado();
    private static final int CAM_REQUEST = 1313;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datosparaagregarempleado);
        realm = Realm.getInstance(getApplicationContext());
        //tv  = (TextView) findViewById(R.id.textview);
        name = (EditText) findViewById(R.id.asignarnombre);
        name.setBackgroundColor(Color.TRANSPARENT);
        lastname = (EditText) findViewById(R.id.asignarapellidos);
        lastname.setBackgroundColor(Color.TRANSPARENT);
        correo = (EditText) findViewById(R.id.pedircorreoelectronico);
        correo.setBackgroundColor(Color.TRANSPARENT);
        tomarfoto = (TextView) findViewById(R.id.tomarfoto);
        foto = (CircleImageView) findViewById(R.id.botonparaagregarfoto);

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraintent, CAM_REQUEST);
            }
        });
        workspace = (EditText) findViewById(R.id.asignararea);
        workspace.setBackgroundColor(Color.TRANSPARENT);
        password = (EditText) findViewById(R.id.asignarcontraseña);
        password.setBackgroundColor(Color.TRANSPARENT);
        agregar = (Button) findViewById(R.id.botonfinalparaagregar);
        cerrar = (ImageButton) findViewById(R.id.cerrar);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (faltanDatosPorllenar()) {
                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "Faltan datos para agregar empleado", Toast.LENGTH_SHORT);
                    toast1.show();
                } else {
                    createUser(e);

                    showEditDialog();
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAM_REQUEST) {
            if(resultCode == Activity.RESULT_OK ){
                if(null != data.getExtras().get("data")){

                thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.PNG, 100, stream);
            foto.setImageBitmap(thumbnail);
            e.setBytes(stream.toByteArray());
            tomarfoto.setVisibility(View.INVISIBLE);
            }}
        }




    }

    public boolean faltanDatosPorllenar() {
        boolean m = false;
        System.out.println("Tamaño del nombre: " + name.getText().toString().length() + "\n" + "Tamaño del apellido: " + lastname.getText().toString().length() + "\n" + "Tamaño del area: " + workspace.getText().toString().length() + "\n" + "Tamaño de la contraseña: " + password.getText().toString().length() + "\n");

        if (name.getText().toString().length() == 0) {
            name.setError("Se necesita un nombre");
            m = true;

        } else {
            name.setError(null);   // do async task
            e.setNombre(name.getText().toString());

        }
        if (thumbnail == null) {
            Toast toast1 = Toast.makeText(getApplicationContext(),
                    "Faltan fotografia", Toast.LENGTH_SHORT);
            toast1.show();
            m = true;

        }
        if (lastname.getText().toString().length() == 0) {
            lastname.setError("Se necesita un apellido");
            m = true;
        } else {
            lastname.setError(null);   // do async task
            e.setApellido(lastname.getText().toString());

        }
        if (workspace.getText().toString().length() == 0) {
            workspace.setError("Se necesita asignar un area");
            m = true;
        } else {
            workspace.setError(null);   // do async task
            e.setArea(workspace.getText().toString());

        }
        if (correo.getText().toString().length() == 0) {
            correo.setError("Se necesita asignar un correo electrónico");
            m = true;
        } else {
            correo.setError(null);   // do async task
            e.setCorreo(correo.getText().toString());


        }
        if (password.getText().toString().length() == 0) {
            password.setError("Se necesita asignar una contraseña");
            m = true;
        } else {
            password.setError(null);   // do async task
            e.setContraseña(password.getText().toString());
        }

        System.out.println(m);
        return m;
    }


    public void createUser(Empleado empleado) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(empleado);
        realm.commitTransaction();
    }


    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentFInalParaAgregar editNameDialog = new FragmentFInalParaAgregar();
        editNameDialog.show(fm, "fragment_edit_name");
    }


}
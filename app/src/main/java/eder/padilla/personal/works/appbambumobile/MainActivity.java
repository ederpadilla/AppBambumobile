package eder.padilla.personal.works.appbambumobile;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import de.hdodenhof.circleimageview.CircleImageView;
import eder.padilla.personal.works.appbambumobile.Modelo.Empleado;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CircleImageView fabButton;
    public static ImageView checkk;
    LinearLayout linearLayout;
    Realm r;
    private RealmResults<Empleado> allEmpleados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkk = (ImageView) findViewById(R.id.checad);
        fabButton = (CircleImageView) findViewById(R.id.agregar);
        linearLayout = (LinearLayout) findViewById(R.id.linearlay);
        fabButton.setOnClickListener(this);
        r = Realm.getInstance(getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();

        agregar_personal();
    }

    private boolean cointains(int id) {
        for (int k = 0; k < linearLayout.getChildCount(); k++) {
            if (linearLayout.getChildAt(k).getId() == id) {
                return true;
            }
        }
        return false;
    }


    public void agregar_personal() {
        allEmpleados = r.where(Empleado.class).findAll();
        for (int i = 0; i < allEmpleados.size(); i++) {
            int m = i;
            if (!this.cointains(m)) {
                LayoutInflater inflater = LayoutInflater.from(this);
                final View laViewInflada = inflater.inflate(R.layout.boton_personal, null);
                final CircleImageView botondeempleados = (CircleImageView) laViewInflada.findViewById(R.id.empleado_btn);
                Bitmap bitmap = BitmapFactory.decodeByteArray(allEmpleados.get(i).getBytes(), 0, allEmpleados.get(i).getBytes().length);
                botondeempleados.setImageBitmap(bitmap);
                final ImageView state=(ImageView) laViewInflada.findViewById(R.id.checad);
                boolean e = allEmpleados.get(i).getEstado();
                System.out.println("Main activity estado: "+e);
                if (e) {
                    state.setBackground(getResources().getDrawable(R.drawable.checked));
                } else {
                    state.setBackground(getResources().getDrawable(R.drawable.boton_f));
                }




                laViewInflada.setId(m);
                botondeempleados.setId(m);


                botondeempleados.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,Perfil_de_Empleados.class);
                        intent.putExtra("id",v.getId());
                        onStop();
                        startActivity(intent);
                        finish();
                    }
                });
                linearLayout.addView(laViewInflada);
            }

        }
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        Agregar_Empleado editNameDialog = new Agregar_Empleado();
        editNameDialog.show(fm, "fragment_edit_name");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.agregar:
                showEditDialog();
                break;
        }


    }


}

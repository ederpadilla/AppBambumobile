
package eder.padilla.personal.works.appbambumobile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import eder.padilla.personal.works.appbambumobile.Modelo.Checks;
import eder.padilla.personal.works.appbambumobile.Modelo.Empleado;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Eder on 16/01/2016.
 */
public class Perfil_de_Empleados extends AppCompatActivity implements View.OnClickListener,RealmChangeListener {


    TextView nombredeperfil, apellidos, area, borrar, correo,cambiarcontraseña;
    CircleImageView fotodeperfil;
    EditText passperfil;
    Button check;
    ImageButton cerrar;
    Calendar calendario = Calendar.getInstance();
    Realm realm;
    LinearLayout tabla;
    private RealmResults<Empleado> allEmpleados;
    private RealmResults<Checks> checkses;

    Empleado empleado;
    Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.perfil_de_empleados);
        this.initViewEmpledao();
        this.initDataEmpleado();
        this.initCheckers();
        this.initViewChecks();
        this.initLisener();
        this.asignarColordeboton();
        this.getChecks();
        System.out.println("Todos los cheks:" + checkses);

    }

    private void initLisener(){
        /////////////////////////////Asignamos clicklisteners//////////////////////////////
        cerrar.setOnClickListener(this);
        check.setOnClickListener(this);
        this.checkses.addChangeListener(this);
        borrar.setOnClickListener(this);
        cambiarcontraseña.setOnClickListener(this);
    }

    private void initViewEmpledao(){
        ////////Instanciamos objetos!!!///////
        /////////TextViews/////////
        nombredeperfil = (TextView) findViewById(R.id.nombre_de_empleado);
        apellidos = (TextView) findViewById(R.id.apellido);
        area = (TextView) findViewById(R.id.area);
        borrar = (TextView) findViewById(R.id.borrarempleado);
        cambiarcontraseña=(TextView)findViewById(R.id.cambiarcontraseña);
        correo = (TextView) findViewById(R.id.correoelectronico);
        ///////Foto de perfil//////
        fotodeperfil = (CircleImageView) findViewById(R.id.fotodeperfil);
        ///////Edit Text//////////
        passperfil = (EditText) findViewById(R.id.passwordparacheckin);
        //////Button/////////////
        check = (Button) findViewById(R.id.btncheckinorout);

        cerrar = (ImageButton) findViewById(R.id.cerrar);
        tabla = (LinearLayout) findViewById(R.id.tabla_orarios);
    }

    private void initDataEmpleado(){
        //////Realm//////////
        realm = Realm.getInstance(getApplicationContext());
        ///////Todos los empleados y todos los checks///////////
        allEmpleados = realm.where(Empleado.class).findAll();
        //////Bundle//////
        bundle = getIntent().getExtras();
        bundle.getInt("id");
        //////Objetos empleado y checks/////////
        empleado = allEmpleados.get(bundle.getInt("id"));

        nombredeperfil.setText(empleado.getNombre());
        apellidos.setText(empleado.getApellido());
        area.setText(empleado.getArea());
        correo.setText(empleado.getCorreo());
        Bitmap bitmap = BitmapFactory.decodeByteArray(empleado.getBytes(), 0, empleado.getBytes().length);
        fotodeperfil.setImageBitmap(bitmap);
    }

    private void initCheckers (){
        RealmQuery<Checks> query = realm.where(Checks.class);
        query.equalTo("correo", empleado.getCorreo());
       checkses = query.findAll();
    }

    private void initViewChecks(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btncheckinorout:
                this.makeChecks();
                break;
            case R.id.cerrar:
                finish();
                startActivity(new Intent(Perfil_de_Empleados.this, MainActivity.class));
                break;
            case R.id.borrarempleado:
                this.dialogoParaEliminar();
                break;
            case R.id.cambiarcontraseña:
                dialogoAdmin();
                break;

        }
    }

    private void makeChecks(){
        Checks checks=null;
        if(validatePass()){
        if (empleado.getEstado()){
            realm.beginTransaction();
            empleado.setEstado(false);
            realm.copyToRealmOrUpdate(empleado);
            realm.commitTransaction();
            this.realm.beginTransaction();
            checks = this.checkses.last();
            checks.setCheckout(chekout());///////////////////guardamos checkout

          //this.realm.copyToRealmOrUpdate(checks);
            this.realm.commitTransaction();
            makeCheckout();
            dialogodeDespedida();
            check.setText(getResources().getString(R.string.check_in));
            check.setBackgroundColor(getResources().getColor(R.color.fab_color));
            passperfil.setText("");
        }else {
            realm.beginTransaction();
            empleado.setEstado(true);
            realm.copyToRealmOrUpdate(empleado);
            realm.commitTransaction();
            this.realm.beginTransaction();

            checks = realm.createObject(Checks.class);
            checks.setCorreo(empleado.getCorreo());
            checks.setFecha(date());////////////////////guargamos fecha
            checks.setCheckin(checkin());///////////////////guardamos checkin
            Random rnd = new Random();
            checks.setIdd((int)(rnd.nextDouble() * 1000000 + 0));
            System.out.println("El id del chck es: " + checks.getIdd());
            this.realm.commitTransaction();
            makeCheckIn();
            dialogoDeSaludo();
            check.setText(getResources().getString(R.string.check_out));
            check.setBackgroundColor(Color.BLACK);
            passperfil.setText("");
        }}
        else passperfil.setError("Contraseña incorrecta");
    }

    public String checkin(){
        String cin;
        int hora, minutos;
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);


        if(minutos<10){
            cin = hora + ":" + "0"+minutos + " hrs";
        }
        else cin = hora + ":" +minutos + " hrs";
        return cin;
    }

    public String date(){
        String date;
        int dia, mes, año;
        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mes = calendario.get(Calendar.MONTH);
        mes = + 1;
        año = calendario.get(Calendar.YEAR);
        date = dia + "/" + mes + "/" + año;
        return date;
    }
    public String chekout(){
        String cout;
        int hora, minutos,AM_PM;
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);

        if(minutos<10){
            cout = hora + ":" + "0"+minutos + " hrs";
        }
        else cout = hora + ":" +minutos + " hrs";

        return cout;
    }
    public void asignarColordeboton() {

        if (empleado.getEstado()) {
            check.setText(getResources().getString(R.string.check_out));
            check.setBackgroundColor(Color.BLACK);
        } else {
            if (empleado.getEstado()) {
                check.setText(getResources().getString(R.string.check_in));
                check.setBackgroundColor(getResources().getColor(R.color.fab_color));
            }
        }

    }
    public boolean validatePass() {
        boolean e = true;
        realm = Realm.getInstance(getApplicationContext());
        allEmpleados = realm.where(Empleado.class).findAll();
        bundle = getIntent().getExtras();
        bundle.getInt("id");
        empleado = allEmpleados.get(bundle.getInt("id"));

        if (passperfil.getText().toString().equals(empleado.getContraseña())) {
            Toast toast1 = Toast.makeText(getApplicationContext(),
                    "La contraseña es correcta", Toast.LENGTH_SHORT);
            toast1.show();


        } else {
            passperfil.setText("");
            passperfil.setError("Contraseña Incorrecta");

            System.out.println("CONTRASEÑA INCORRECTA");
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Esta mal la contraseña", Toast.LENGTH_SHORT);
            toast1.show();
            e = false;
        }

        return e;
    }
    public void getChecks(){
        RealmQuery<Checks> query = realm.where(Checks.class);
        query.equalTo("correo", empleado.getCorreo());
        RealmResults<Checks> results = query.findAll();

        for (Checks checks2 : results) {
            TextView fechaanterioires, checkinanteriores, checkoutsanteriores;
            View inflatedView = View.inflate(getBaseContext(), R.layout.reglon, null);
            tabla.addView(inflatedView, 0);
            fechaanterioires = (TextView) inflatedView.findViewById(R.id.tv_fecha);
            checkinanteriores = (TextView) inflatedView.findViewById(R.id.checkin);
            checkoutsanteriores = (TextView) inflatedView.findViewById(R.id.checkout);
            fechaanterioires.setText(checks2.getFecha());
            checkinanteriores.setText(checks2.getCheckin());
            checkoutsanteriores.setText(checks2.getCheckout());
        }
    }
    public void makeCheckIn(){
        TextView fecha, checkin;
        View inflatedView = View.inflate(getBaseContext(), R.layout.reglon, null);
        tabla.addView(inflatedView, 0);
        fecha = (TextView) inflatedView.findViewById(R.id.tv_fecha);
        checkin = (TextView) inflatedView.findViewById(R.id.checkin);
        fecha.setText(date());
        checkin.setText(checkin());


    }
    public void makeCheckout(){
        TextView checkout;
        View renglon = tabla.getChildAt(0);
        checkout = (TextView) renglon.findViewById(R.id.checkout);
        checkout.setText(chekout());
    }
    private void dialogoParaEliminar() {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        Eliminar_empleado editNameDialog = new Eliminar_empleado();
        editNameDialog.show(fm, "fragment_edit_name");
    }

    public void fragmentfinalparaeliminar() {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        Fragment_final_paraeliminar editNameDialog = new Fragment_final_paraeliminar();
        editNameDialog.show(fm, "fragment_edit_name");
    }

    public void fragmentfinalparacambiar() {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        FragmentFinalDeContraseña editNameDialog = new FragmentFinalDeContraseña();
        editNameDialog.show(fm, "fragment_edit_name");
    }
    public void dialogoDeSaludo() {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        Buen_Dia editNameDialog = new Buen_Dia();
        editNameDialog.show(fm, "fragment_edit_name");
    }

    public void dialogodeDespedida() {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        Hasta_Luego editNameDialog = new Hasta_Luego();
        editNameDialog.show(fm, "fragment_edit_name");
    }
    public void dialogodeCambiar() {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        Cambiarcontraseña editNameDialog = new Cambiarcontraseña();
        editNameDialog.show(fm, "fragment_edit_name");
    }
    public void dialogoAdmin() {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        AdminCambiarpass editNameDialog = new AdminCambiarpass();
        editNameDialog.show(fm, "fragment_edit_name");
    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(Perfil_de_Empleados.this, MainActivity.class));
    }

    @Override
    public void onChange() {
        Log.e("Perfil","Cambiando la data");
    }
}

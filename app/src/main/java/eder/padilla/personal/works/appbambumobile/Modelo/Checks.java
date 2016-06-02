package eder.padilla.personal.works.appbambumobile.Modelo;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Eder on 05/01/2016.
 */
public class Checks extends RealmObject {
    private String fecha,checkin,checkout,correo;
    @PrimaryKey
    private int idd;
    public Checks(String fecha, String checkin, String checkout,String correo,int idd) {
        this.fecha = fecha;
        this.checkin = checkin;
        this.checkout = checkout;
        this.correo=correo;
        this.idd=idd;
    }
    public Checks(){
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public int getIdd() {
        return idd;
    }

    public void setIdd(int idd) {
        this.idd = idd;
    }


}

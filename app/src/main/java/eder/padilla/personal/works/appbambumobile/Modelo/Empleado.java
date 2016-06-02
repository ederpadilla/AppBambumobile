package eder.padilla.personal.works.appbambumobile.Modelo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Eder on 15/12/2015.
 */
public class Empleado extends RealmObject {

   private String nombre,apellido,area;
    private boolean estado;

    private String contraseña;
    private byte[]bytes;
    @PrimaryKey
    private String correo;



    public Empleado(String nombre, String apellido, String area, byte[] bytes, String contraseña,boolean estado,String correo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.area = area;

        this.bytes = bytes;
        this.contraseña = contraseña;
        this.estado=estado;
        this.correo=correo;
    }
    public Empleado (){
    }
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}

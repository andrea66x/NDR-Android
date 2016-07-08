package fiec.ndr;

/**
 * Created by Andrea on 07/07/2016.
 */
public class Formulario {
    private String nombres;
    private String apellidos;
    private String codigo;

    public Formulario() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
}

package fiec.ndr.Formularios;

/**
 * Created by Andrea on 27/07/2016.
 */
public class Frm_Preparacion {
    private boolean ayunas;
    private String código, lugar_enc, fecha_enc, ruta_consent;

    public Frm_Preparacion() {
    }

    public boolean isAyunas() {
        return ayunas;
    }

    public void setAyunas(boolean ayunas) {
        this.ayunas = ayunas;
    }

    public String getCódigo() {
        return código;
    }

    public void setCódigo(String código) {
        this.código = código;
    }

    public String getLugar_enc() {
        return lugar_enc;
    }

    public void setLugar_enc(String lugar_enc) {
        this.lugar_enc = lugar_enc;
    }

    public String getFecha_enc() {
        return fecha_enc;
    }

    public void setFecha_enc(String fecha_enc) {
        this.fecha_enc = fecha_enc;
    }

    public String getRuta_consent() {
        return ruta_consent;
    }

    public void setRuta_consent(String ruta_consent) {
        this.ruta_consent = ruta_consent;
    }
}

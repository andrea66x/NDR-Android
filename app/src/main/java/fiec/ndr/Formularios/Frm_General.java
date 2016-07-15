package fiec.ndr.Formularios;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;


public class Frm_General {

    //Personales
    private String nombres, apellidos, fecha_nac, sexo,  telefono;
    private int edad, estado_cvl, etnia;

    //Vivienda
    private int provincia ,canton,ubc_vivienda;
    private String  direccion, num_personas_vive, tpo_agua;
    private boolean b_cloacas;

    //Economia Familiar
    private boolean b_cabeza_fam, b_ingresos_pro, b_llega_fin;
    private String cargo;
    private int ocupacion, estudios;

    //Salud
    private boolean  b_seguros, b_chequeos, b_diabetes, b_presion, b_enf_renal, b_enf_otra;
    private String seguros,chequeo_mes, chequeo_frec, diabetes_anios, enf_renal, enf_otra;

    //Medicamentos
    private boolean b_insulina, b_hipo_orales, b_med_presion,b_analgesicos, b_otro_medc;
    private int medc_esp;
    private String hipo_orales, med_presion, analgesicos, otro_medc;

    //Antecedentes
    private boolean b_gluco_alta, b_dbt_fam_directos, b_dbt_fam_otros, b_dbt_embarazo, b_bebes_peso, b_presion_fam, b_renal_fam;
    private boolean renal_fam;

    //Habitos

    private boolean b_tabaco, b_alcohol, b_hab_otros;
    private int frc_tabaco_dia, frc_alcohol_sem, ejercicios;
    private String hab_otros;


     public Frm_General(){

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

    public String getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(String fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getEstado_cvl() {
        return estado_cvl;
    }

    public void setEstado_cvl(int estado_cvl) {
        this.estado_cvl = estado_cvl;
    }

    public int getEtnia() {
        return etnia;
    }

    public void setEtnia(int etnia) {
        this.etnia = etnia;
    }

    public int getProvincia() {
        return provincia;
    }

    public void setProvincia(int provincia) {
        this.provincia = provincia;
    }

    public int getCanton() {
        return canton;
    }

    public void setCanton(int canton) {
        this.canton = canton;
    }

    public int getUbc_vivienda() {
        return ubc_vivienda;
    }

    public void setUbc_vivienda(int ubc_vivienda) {
        this.ubc_vivienda = ubc_vivienda;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNum_personas_vive() {
        return num_personas_vive;
    }

    public void setNum_personas_vive(String num_personas_vive) {
        this.num_personas_vive = num_personas_vive;
    }

    public String getTpo_agua() {
        return tpo_agua;
    }

    public void setTpo_agua(String tpo_agua) {
        this.tpo_agua = tpo_agua;
    }

    public boolean isB_cloacas() {
        return b_cloacas;
    }

    public void setB_cloacas(boolean b_cloacas) {
        this.b_cloacas = b_cloacas;
    }

    public boolean isB_cabeza_fam() {
        return b_cabeza_fam;
    }

    public void setB_cabeza_fam(boolean b_cabeza_fam) {
        this.b_cabeza_fam = b_cabeza_fam;
    }

    public boolean isB_ingresos_pro() {
        return b_ingresos_pro;
    }

    public void setB_ingresos_pro(boolean b_ingresos_pro) {
        this.b_ingresos_pro = b_ingresos_pro;
    }

    public boolean isB_llega_fin() {
        return b_llega_fin;
    }

    public void setB_llega_fin(boolean b_llega_fin) {
        this.b_llega_fin = b_llega_fin;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public int getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(int ocupacion) {
        this.ocupacion = ocupacion;
    }

    public int getEstudios() {
        return estudios;
    }

    public void setEstudios(int estudios) {
        this.estudios = estudios;
    }

    public boolean isB_seguros() {
        return b_seguros;
    }

    public void setB_seguros(boolean b_seguros) {
        this.b_seguros = b_seguros;
    }

    public boolean isB_chequeos() {
        return b_chequeos;
    }

    public void setB_chequeos(boolean b_chequeos) {
        this.b_chequeos = b_chequeos;
    }

    public boolean isB_diabetes() {
        return b_diabetes;
    }

    public void setB_diabetes(boolean b_diabetes) {
        this.b_diabetes = b_diabetes;
    }

    public boolean isB_presion() {
        return b_presion;
    }

    public void setB_presion(boolean b_presion) {
        this.b_presion = b_presion;
    }

    public boolean isB_enf_renal() {
        return b_enf_renal;
    }

    public void setB_enf_renal(boolean b_enf_renal) {
        this.b_enf_renal = b_enf_renal;
    }

    public boolean isB_enf_otra() {
        return b_enf_otra;
    }

    public void setB_enf_otra(boolean b_enf_otra) {
        this.b_enf_otra = b_enf_otra;
    }

    public String getSeguros() {
        return seguros;
    }

    public void setSeguros(String seguros) {
        this.seguros = seguros;
    }

    public String getChequeo_mes() {
        return chequeo_mes;
    }

    public void setChequeo_mes(String chequeo_mes) {
        this.chequeo_mes = chequeo_mes;
    }

    public String getChequeo_frec() {
        return chequeo_frec;
    }

    public void setChequeo_frec(String chequeo_frec) {
        this.chequeo_frec = chequeo_frec;
    }

    public String getDiabetes_anios() {
        return diabetes_anios;
    }

    public void setDiabetes_anios(String diabetes_anios) {
        this.diabetes_anios = diabetes_anios;
    }

    public String getEnf_renal() {
        return enf_renal;
    }

    public void setEnf_renal(String enf_renal) {
        this.enf_renal = enf_renal;
    }

    public String getEnf_otra() {
        return enf_otra;
    }

    public void setEnf_otra(String enf_otra) {
        this.enf_otra = enf_otra;
    }

    public boolean isB_insulina() {
        return b_insulina;
    }

    public void setB_insulina(boolean b_insulina) {
        this.b_insulina = b_insulina;
    }

    public boolean isB_hipo_orales() {
        return b_hipo_orales;
    }

    public void setB_hipo_orales(boolean b_hipo_orales) {
        this.b_hipo_orales = b_hipo_orales;
    }

    public boolean isB_med_presion() {
        return b_med_presion;
    }

    public void setB_med_presion(boolean b_med_presion) {
        this.b_med_presion = b_med_presion;
    }

    public boolean isB_analgesicos() {
        return b_analgesicos;
    }

    public void setB_analgesicos(boolean b_analgesicos) {
        this.b_analgesicos = b_analgesicos;
    }

    public boolean isB_otro_medc() {
        return b_otro_medc;
    }

    public void setB_otro_medc(boolean b_otro_medc) {
        this.b_otro_medc = b_otro_medc;
    }

    public int getMedc_esp() {
        return medc_esp;
    }

    public void setMedc_esp(int medc_esp) {
        this.medc_esp = medc_esp;
    }

    public String getHipo_orales() {
        return hipo_orales;
    }

    public void setHipo_orales(String hipo_orales) {
        this.hipo_orales = hipo_orales;
    }

    public String getMed_presion() {
        return med_presion;
    }

    public void setMed_presion(String med_presion) {
        this.med_presion = med_presion;
    }

    public String getAnalgesicos() {
        return analgesicos;
    }

    public void setAnalgesicos(String analgesicos) {
        this.analgesicos = analgesicos;
    }

    public String getOtro_medc() {
        return otro_medc;
    }

    public void setOtro_medc(String otro_medc) {
        this.otro_medc = otro_medc;
    }

    public boolean isB_gluco_alta() {
        return b_gluco_alta;
    }

    public void setB_gluco_alta(boolean b_gluco_alta) {
        this.b_gluco_alta = b_gluco_alta;
    }

    public boolean isB_dbt_fam_directos() {
        return b_dbt_fam_directos;
    }

    public void setB_dbt_fam_directos(boolean b_dbt_fam_directos) {
        this.b_dbt_fam_directos = b_dbt_fam_directos;
    }

    public boolean isB_dbt_fam_otros() {
        return b_dbt_fam_otros;
    }

    public void setB_dbt_fam_otros(boolean b_dbt_fam_otros) {
        this.b_dbt_fam_otros = b_dbt_fam_otros;
    }

    public boolean isB_dbt_embarazo() {
        return b_dbt_embarazo;
    }

    public void setB_dbt_embarazo(boolean b_dbt_embarazo) {
        this.b_dbt_embarazo = b_dbt_embarazo;
    }

    public boolean isB_bebes_peso() {
        return b_bebes_peso;
    }

    public void setB_bebes_peso(boolean b_bebes_peso) {
        this.b_bebes_peso = b_bebes_peso;
    }

    public boolean isB_presion_fam() {
        return b_presion_fam;
    }

    public void setB_presion_fam(boolean b_presion_fam) {
        this.b_presion_fam = b_presion_fam;
    }

    public boolean isB_renal_fam() {
        return b_renal_fam;
    }

    public void setB_renal_fam(boolean b_renal_fam) {
        this.b_renal_fam = b_renal_fam;
    }

    public boolean isRenal_fam() {
        return renal_fam;
    }

    public void setRenal_fam(boolean renal_fam) {
        this.renal_fam = renal_fam;
    }

    public boolean isB_tabaco() {
        return b_tabaco;
    }

    public void setB_tabaco(boolean b_tabaco) {
        this.b_tabaco = b_tabaco;
    }

    public boolean isB_alcohol() {
        return b_alcohol;
    }

    public void setB_alcohol(boolean b_alcohol) {
        this.b_alcohol = b_alcohol;
    }

    public boolean isB_hab_otros() {
        return b_hab_otros;
    }

    public void setB_hab_otros(boolean b_hab_otros) {
        this.b_hab_otros = b_hab_otros;
    }

    public int getFrc_tabaco_dia() {
        return frc_tabaco_dia;
    }

    public void setFrc_tabaco_dia(int frc_tabaco_dia) {
        this.frc_tabaco_dia = frc_tabaco_dia;
    }

    public int getFrc_alcohol_sem() {
        return frc_alcohol_sem;
    }

    public void setFrc_alcohol_sem(int frc_alcohol_sem) {
        this.frc_alcohol_sem = frc_alcohol_sem;
    }

    public int getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(int ejercicios) {
        this.ejercicios = ejercicios;
    }

    public String getHab_otros() {
        return hab_otros;
    }

    public void setHab_otros(String hab_otros) {
        this.hab_otros = hab_otros;
    }
}
package fiec.ndr.inf_general;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {

            case 0: return new DatosFragment();
            case 1: return new ViviendaFragment();
            case 2: return new EconomiaFragment();
            case 3: return new SaludFragment();
            case 4: return new MedicamentosFragment();
            case 5: return new AntecedentesFragment();
            case 6: return new HabitosFragment();
            default: return new DatosFragment();
        }
    }

    @Override
    public int getCount() {
        // Devuelve el total de tabs o fragments que existen.
        return 7;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Datos Personales";
            case 1:
                return "Vivienda";
            case 2:
                return "Economía Familiar";
            case 3:
                return "Salud";
            case 4:
                return "Medicamentos";
            case 5:
                return "Antecedentes";
            case 6:
                return "Hábitos";
        }
        return null;
    }
}

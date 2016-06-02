package eder.padilla.personal.works.appbambumobile;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Created by Eder on 21/12/2015.
 */
public class Fragment_final_paraeliminar extends DialogFragment {

    public Fragment_final_paraeliminar(){
}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.borrado, container);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);


        return view;
    }
}



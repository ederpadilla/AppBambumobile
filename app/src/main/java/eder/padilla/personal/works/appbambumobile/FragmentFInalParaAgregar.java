package eder.padilla.personal.works.appbambumobile;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import eder.padilla.personal.works.appbambumobile.R;

/**
 * Created by Eder on 16/12/2015.
 */
public class FragmentFInalParaAgregar extends DialogFragment {

    public FragmentFInalParaAgregar(){


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.agregado, container);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);


        return view;
    }
}

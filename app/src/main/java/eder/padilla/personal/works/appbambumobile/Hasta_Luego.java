package eder.padilla.personal.works.appbambumobile;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;

/**
 * Created by Eder on 31/12/2015.
 */
public class Hasta_Luego extends DialogFragment {
    ImageButton cerrar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.hasta_luego, container);
        cerrar=(ImageButton) view.findViewById(R.id.cerrar);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return view;
    }
}

package com.minube.kenburnspager.sample;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.minube.kenburnspager.KenBurnsPagerActivity;
import com.minube.kenburnspager.listener.AppBarStateChangeListener;

public class MainActivity extends KenBurnsPagerActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHeaderImage(
            "http://imgsv.imaging.nikon.com/lineup/lens/zoom/normalzoom/af-s_dx_18-140mmf_35-56g_ed_vr/img/sample"
                + "/sample1_l.jpg");
        addTabFragment("hola1", new SampleFragment(100));
        addTabFragment("hola2", new SampleFragment(1));
        addTabFragment("hola5", new SampleFragment(100));
        addTabFragment("hola4", new SampleFragment(1));

        addCustomHeaderView();
    }

    private void addCustomHeaderView() {
        LayoutInflater inflater =
            (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View inflate = inflater.inflate(R.layout.custom_header, null);
        replaceHeaderTitleByHeaderLayout(inflate);
    }

    @Override protected void onCollapsingStateChanged(AppBarStateChangeListener.State state) {
        if (state != AppBarStateChangeListener.State.HALF_COLLAPSED) {
            hideToolbarTitle();
        } else {
            showToolbarTitle();
        }
    }

    @Override protected String getHeaderTitle() {
        return "Iglesia de Santa María de Nuestra Señora de la Cabeza";
    }
}

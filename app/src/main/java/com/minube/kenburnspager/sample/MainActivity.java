package com.minube.kenburnspager.sample;

import android.os.Bundle;
import com.minube.kenburnspager.KenBurnsPagerActivity;

public class MainActivity extends KenBurnsPagerActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHeaderImage(
            "http://imgsv.imaging.nikon.com/lineup/lens/zoom/normalzoom/af-s_dx_18-140mmf_35-56g_ed_vr/img/sample"
                + "/sample1_l.jpg");
        addTabFragment("hola1", new SampleFragment(getResources().getColor(android.R.color.background_dark)));
        addTabFragment("hola2", new SampleFragment(getResources().getColor(android.R.color.background_dark)));
        addTabFragment("hola5", new SampleFragment(getResources().getColor(android.R.color.background_dark)));
        addTabFragment("hola4", new SampleFragment(getResources().getColor(android.R.color.background_dark)));
    }
}
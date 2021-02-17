package com.goregoblin.atombuilder.gui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.goregoblin.atombuilder.R;
import com.goregoblin.atombuilder.logic.AtomActivityListener;
import com.goregoblin.atombuilder.logic.LoadAtomList;
import com.goregoblin.atombuilder.models.Nucleon;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AtomActivity extends AppCompatActivity {

    public AtomActivityListener atomActivityListener;

    public List<String[]> elementList;

    public RelativeLayout mainAtomBuilder;
    public ImageView ddProton;
    public ImageView ddNeutron;
    public FrameLayout ddAtomCore;
    public FrameLayout ddLeptones;

    public ArrayList<Nucleon> coreList;
    // to show Nucleon amount
    public TextView txtShowCoreAmount;
    public TextView txtShowNeutronAmount;
    public TextView txtShowProtonAmount;
    // Description
    public TextView txtElement;
    public TextView txtSymbol;
    // to show Mass
    public TextView txtMassSingle;
    public TextView txtMassEmpiric;

    public Button btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_build_atom);

        mainAtomBuilder = findViewById(R.id.mainAtomBuilder);

        // belegen der GUI-Elemente
        txtElement = findViewById(R.id.txtElement);
        txtSymbol = findViewById(R.id.txtSymbol);
        txtMassSingle = findViewById(R.id.txtMassSingle);
        txtMassEmpiric = findViewById(R.id.txtMassEmpiric);

        ddProton = findViewById(R.id.ddProton);
        ddNeutron = findViewById(R.id.ddNeutron);
        ddAtomCore = findViewById(R.id.ddAtomCore);
        ddLeptones = findViewById(R.id.ddLeptones);

        txtShowCoreAmount = findViewById(R.id.txtShowCoreAmount);
        txtShowNeutronAmount = findViewById(R.id.txtShowNeutronAmount);
        txtShowProtonAmount = findViewById(R.id.txtShowProtonAmount);

        btnClear = findViewById(R.id.btnClear);

        atomActivityListener = new AtomActivityListener(this);

        ddProton.setOnTouchListener(atomActivityListener);
        ddNeutron.setOnTouchListener(atomActivityListener);

        ddAtomCore.setOnDragListener(atomActivityListener);

        btnClear.setOnClickListener(atomActivityListener);

        // element-Liste laden
        elementList = loadElementList();

        // remove headers from List
        elementList.remove(0);

    }

    private List<String[]> loadElementList(){
        /*
        AtomListe laden (ist in R.raw)
        just for orientation...
        atomic number,symbol,atomic mass,name,metal or nonmetal
            ListEntry[0] : atomic number (use to identfy)
            ListEntry[1] : symbol (show)
            ListEntry[2] : atomic mass (show and show diff )
            ListEntry[3] : name (show also?!)
            ListEntry[4] : metal or nonmetal (maybe, screen is alread very full of stuff ^^')
         */
        List<String[]> getList;
        InputStream inputStream = getResources().openRawResource(R.raw.listatomicelements);
        LoadAtomList csvFile = new LoadAtomList(inputStream);
        getList = csvFile.read();
        return getList;
    }
}

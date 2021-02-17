package com.goregoblin.atombuilder.logic;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.goregoblin.atombuilder.R;
import com.goregoblin.atombuilder.gui.AtomActivity;
import com.goregoblin.atombuilder.models.Neutron;
import com.goregoblin.atombuilder.models.Proton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AtomActivityListener implements View.OnTouchListener, View.OnDragListener, View.OnClickListener {

    AtomActivity atomActivity;
    private  FrameLayout.LayoutParams neutronLayout;
    private  FrameLayout.LayoutParams protonLayout;
    private FrameLayout.LayoutParams layoutForCore;
    private FrameLayout.LayoutParams layoutForElectron;
    private RelativeLayout.LayoutParams paramsCore;

    private int shellId;

    float yPosElecStart;
    float xPosElecStart;
    float yPosElecEnd;
    float xPosElecEnd;

    private boolean isNeutron;
    private boolean noShell;
    private ViewGroup viewGroup;
    private  int shellVolumeTotal;

    public AtomActivityListener(AtomActivity atomActivity) {

        this.atomActivity = atomActivity;

        atomActivity.coreList = new ArrayList<>();

        //Layout-Settings for Neutron
        neutronLayout = new FrameLayout.LayoutParams(
                          FrameLayout.LayoutParams.WRAP_CONTENT,
                          FrameLayout.LayoutParams.WRAP_CONTENT);
        neutronLayout.gravity=Gravity.CENTER_VERTICAL| Gravity.LEFT;
        neutronLayout.leftMargin = 125;
        neutronLayout.height = 120;
        neutronLayout.width = 120;

        //Layout-Settings for Proton
        protonLayout = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        protonLayout.gravity=Gravity.CENTER_VERTICAL| Gravity.RIGHT;
        protonLayout.rightMargin = 125; // ?!?!?
        protonLayout.height = 120;
        protonLayout.width = 120;

        layoutForElectron = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);

        // Layout-Settings für Nucleonen im Kern (die Gravity ändert sich)
        layoutForCore = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutForCore.gravity=Gravity.CENTER_VERTICAL| Gravity.CENTER_HORIZONTAL;
        layoutForCore.height = 120;
        layoutForCore.width = 120;

        viewGroup = atomActivity.ddAtomCore;

        // LayoutParams of core
        paramsCore = (RelativeLayout.LayoutParams) atomActivity.ddAtomCore.getLayoutParams();

        // for first shell
        noShell = true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent e) {

        switch (v.getId()){
            case R.id.ddNeutron:
                // wenn gedrückt wird (ist noch kein Drag)
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDragAndDrop(data, shadowBuilder, v,0);
                }

                break;
            case R.id.ddProton:

                // wenn gedrückt wird (ist noch kein Drag)
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDragAndDrop(data, shadowBuilder, v,0);
                    return true;
                }

        }
        return true;
    }


    @Override
    public boolean onDrag(View v, DragEvent event) {

                   switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:

                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:

                        break;
                    case DragEvent.ACTION_DRAG_EXITED:

                        break;
                    case DragEvent.ACTION_DRAG_LOCATION:

                        break;
                    case DragEvent.ACTION_DROP:
                        ImageView view = (ImageView)event.getLocalState();

                        // für neues Lepton in Auswahl-Container
                        if (view.getId() == R.id.ddNeutron){
                            isNeutron = true;
                        }

                        if (view.getId() == R.id.ddProton){
                            isNeutron = false;
                        }

                        ViewGroup owner = (ViewGroup) view.getParent();
                        owner.removeView(view);

                        view.setLayoutParams(layoutForCore);

                        FrameLayout container = (FrameLayout) v;
                        container.addView(view);
                        view.setVisibility(View.VISIBLE);
                        // den Touch Listener wieder entfernen wenn die Objekte im Kern sind
                        view.setOnTouchListener(null);

                        break;
                    case DragEvent.ACTION_DRAG_ENDED:

                        ImageView newNucleon = new ImageView(atomActivity);

                        // layout kann für Neutron oder Proton sein
                        if(isNeutron){ // Neutron
                            newNucleon.setImageResource(R.drawable.neutron_shape);
                            newNucleon.setLayoutParams(neutronLayout);
                            newNucleon.setId(R.id.ddNeutron);

                            // wir brauchen eine neuen Touch-Listener an der ImageView
                            newNucleon.setOnTouchListener((args, e) -> {
                                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                                    ClipData data = ClipData.newPlainText("", "");
                                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(args);
                                    args.startDragAndDrop(data, shadowBuilder, args,0);
                                }
                                return false;
                            });

                        } else { // Proton
                            newNucleon.setImageResource(R.drawable.proton_shape);
                            newNucleon.setLayoutParams(protonLayout);
                            newNucleon.setId(R.id.ddProton);

                            newNucleon.setOnTouchListener((args, e) -> {
                                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                                    ClipData data = ClipData.newPlainText("", "");
                                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(args);
                                    args.startDragAndDrop(data, shadowBuilder, args,0);
                                }
                                return false;
                            });


                        }

                         atomActivity.ddLeptones.addView(newNucleon);

                         // nach jedem Drop checken was im Core ist
                         getNucleonsFromCore();

                         // nach Drop TextFelder aktualisieren
                         int coreAmount = atomActivity.coreList.size();
                         atomActivity.txtShowCoreAmount.setText(Integer.toString(coreAmount));
                         atomActivity.txtShowNeutronAmount.setText(Integer.toString(getNeutronAmount()));
                         atomActivity.txtShowProtonAmount.setText(Integer.toString(getProtonAmount()));

                         // eventuell neue AtomSchale einfügen
                            addNewShell2();

                         // testen wie groß der Kern und die Atomschalen sind
                        checkSizeOfCore();
                        checkSizeOfShell();

                        // identifiy Element from List
                        // hier Felder aktualisieren
                        updateFields();

                        break;
                    default:
                        break;
                }

        return true;
    }



    /* die Anzahl der elektronen hängt von der Anzahl der Protonen ab
        die Anzahl der elektronen wiederum bestimmt die Größe/Typ des Atom-Orbitals
            ab dem ersten Proton brauchen wir eine Hüllen-Darstellung
            Formel für menge elektronen pro Hülle n:
            elektronen = 2*n² (n = Grad der Hülle)
     */

    // update List of Nucleons in Core
    private void getNucleonsFromCore(){

        // erst Liste löschen (sonst zählen wir doppelt)
        atomActivity.coreList.clear();

        // Liste mit Protonen bzw. Neutronen füllen (Core wird bei jedem Drop ausgelesen)
        for(int index = 0; index < viewGroup.getChildCount(); index++) {
            View nextChild = viewGroup.getChildAt(index);

            if (nextChild.getId() == R.id.ddNeutron){
                 atomActivity.coreList.add(new Neutron());
            } else if(nextChild.getId() == R.id.ddProton) {
                atomActivity.coreList.add(new Proton());
            }
        }
    }

    private int getProtonAmount(){
        int protons = (int)atomActivity.coreList.stream().filter(e -> e.getName() == "Proton").count();
        return protons;
    }

    private int getNeutronAmount(){
        int neutrons = (int)atomActivity.coreList.stream().filter(e -> e.getName() == "Neutron").count();
        return neutrons;
    }


    private void checkSizeOfCore(){

        RelativeLayout.LayoutParams coreParams =
                (RelativeLayout.LayoutParams) atomActivity.ddAtomCore.getLayoutParams();

        int currentHeight = coreParams.height;
        int currentWidth = coreParams.width;

        // Wenn Kern zu groß zurechstutzen
         if (currentHeight > 516 && currentWidth > 516){
             zoomOut();
         }
    }


    @SuppressLint("ResourceType")
    private void checkSizeOfShell(){
        // nur core checken reicht nicht wir müssen auch die Hüllen testen
        // Schalen-Menge bzw. Typ checken
        for(int index = 0; index < viewGroup.getChildCount(); index++) {
            View nextChild = viewGroup.getChildAt(index);
            System.out.println("nextChild: " + nextChild.getId());
            if(nextChild.getId() < 1000) { // nicht sehr elegant aber geht ^^'

                // hier größen der Shells checken
                ViewGroup.LayoutParams paramsCurrShell =  viewGroup.getChildAt(index).getLayoutParams();
                int currentShellHeight = paramsCurrShell.height;
                int currentShellWidth = paramsCurrShell.width;

                if (currentShellHeight > 516 && currentShellWidth > 516){
                    zoomOut();
                }

            }
        }
    }


    private void zoomOut(){

        // to zoom out AtomCore if more Shells are added
        RelativeLayout.LayoutParams params =
                (RelativeLayout.LayoutParams) atomActivity.ddAtomCore.getLayoutParams();

        // hier absolut-werte --> besser eine relation verwenden?!
        params.height = 516;
        params.width = 516;
        atomActivity.ddAtomCore.setLayoutParams(params);

        List<ViewGroup.LayoutParams> paramsList = new ArrayList<>();

        for(int index = 0; index < viewGroup.getChildCount(); index++) {
            View nextChild = viewGroup.getChildAt(index);
            paramsList.add(nextChild.getLayoutParams());
        }

        for(int index = 0; index < viewGroup.getChildCount(); index++) {
           int height =  viewGroup.getChildAt(index).getHeight();
           int width =  viewGroup.getChildAt(index).getWidth();

           if (height > 400){ // the shells
               ViewGroup.LayoutParams paramsCurrent = viewGroup.getChildAt(index).getLayoutParams();
               paramsCurrent.height = paramsCurrent.height/2;
               paramsCurrent.width = paramsCurrent.width/2;
               viewGroup.getChildAt(index).setLayoutParams(paramsCurrent);
           } else if(height == 120){
               ViewGroup.LayoutParams paramsCurrent = viewGroup.getChildAt(index).getLayoutParams();
               paramsCurrent.height = 80;
               paramsCurrent.width = 80;
               viewGroup.getChildAt(index).setLayoutParams(paramsCurrent);

           }
        }
    }


    private void addAtomOrbital(int height, int width, int id){

        // größe des Kern-Layouts als Ausgangs-Größe für erste Orbital
        RelativeLayout.LayoutParams paramsCore =
                (RelativeLayout.LayoutParams) atomActivity.ddAtomCore.getLayoutParams();

        ImageView atomShell = new ImageView(atomActivity);
        atomShell.setId(id); // das orbital liegt auch im Kern, also mit core-Liste aufpassen!!
        atomShell.setImageResource(R.drawable.atom_orbital_shape);

        FrameLayout.LayoutParams layoutForShell = new FrameLayout.LayoutParams(
                                                      FrameLayout.LayoutParams.WRAP_CONTENT,
                                                      FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutForShell.gravity=Gravity.CENTER_VERTICAL| Gravity.CENTER_HORIZONTAL;

        layoutForShell.height = height;
        layoutForShell.width = width;

        atomShell.setLayoutParams(layoutForShell);
        atomActivity.ddAtomCore.addView(atomShell);

    }


    private void updateFields(){

        // Protonen-menge bestimmt Ordungszahl (damit läst sich das Element bestimmen)
       int elemNum = getProtonAmount();
       List<String[]> strList = atomActivity.elementList.stream()
                               .filter(e -> e[0].equalsIgnoreCase(Integer.toString(elemNum)))
                                .collect(Collectors.toList());

       if(strList.size() > 0) {

           atomActivity.txtElement.setText(strList.get(0)[3]);
           atomActivity.txtSymbol.setText(strList.get(0)[1]);

           // Masse mal mit 2 decimal-stellen
           double massEmpiric = Double.parseDouble(strList.get(0)[2]);
           String strMassEmpiric = String.format("%.2f", massEmpiric);
           atomActivity.txtMassEmpiric.setText(strMassEmpiric);

           // hierfür müssen wir die Masse aller Nucleonen aus der coreList bestimmen
           double atomWeight = atomActivity.coreList.stream().mapToDouble(o -> o.getMassU()).sum();
           String strMassSingle = String.format("%.2f", atomWeight);
           atomActivity.txtMassSingle.setText(strMassSingle);
       }

    }

    @SuppressLint("ResourceType")
    private void addNewShell2(){

        /* die Menge der Elektronen richtet sich nach Anzahl der Protonen im Kern
         die Verteilung der Elektronen auf den Schalen hängt von der Aufnahme-Fähigkeit der Schalen ab
         je höher die Schalen-Ordung desto mehr elektronen kann diese Aufnehmen
         die Formel ist: 2*n²
         eigentlich muss man auch noch die Orbital-Typen (s,p,d,f..-Orbital) beachten
         aber die Form ändert nichts an der Aufnahme-fähigkeit für elektronen (also das Volumen das aufgenommen werden kann)
         */

        int sizeShellBase = 100;
        // Protonen-Menge checken
        int electronsWant = getProtonAmount();
        System.out.println("Current electronsWant is: " + electronsWant);

        // erste AtomHülle
        if(electronsWant == 1 && noShell ){
            addAtomOrbital(paramsCore.height+sizeShellBase,paramsCore.width+sizeShellBase, ++shellId);
            paramsCore.width+=sizeShellBase+10;
            paramsCore.height+=sizeShellBase+10;
            // wenn erste Hülle existiert (damit er nicht auf Neutronen reagiert)
            noShell = false;

            // wenn mehr als 2 Protonen (bzw. Elektronen)vorhanden sind
        } else if(electronsWant > 2){
            int totalPossElectrons = checkFullShellVolume();
            if (electronsWant > totalPossElectrons) {

                // nächstes Orbital etwas kleiner
                // (eigentlich größer aber die Größe aller Views im Kern wird ja nochmal geändert)
                int nextRad = sizeShellBase - sizeShellBase / 2;

                addAtomOrbital(paramsCore.height + nextRad, paramsCore.width + nextRad, ++shellId);

                paramsCore.width += nextRad + 50;
                paramsCore.height += nextRad + 50;
            }

        }

    }


    private int checkFullShellVolume(){
        // gibt Menge ALLER Elektronen die die Schalen ZUSAMMEN aufnehmen können
        // ist man darüber --> neue Schale reinpacken

        shellVolumeTotal = 0;
        int shellAmount = checkAmountShells(); // menge der vorhandene Schalen checken

        // hier potenzielles Volumen der einzelnen vorhandenen Schalen addieren
        for (int i = 1; i <= shellAmount;i++){
            shellVolumeTotal += (int) (2*Math.pow(i,2));
        }

        return  shellVolumeTotal;
    }

    @SuppressLint("ResourceType")
    private int checkAmountShells(){

        List<Integer> shellList = new ArrayList<>();

        // Schalen-Menge bzw. Typ checken
        for(int index = 0; index < viewGroup.getChildCount(); index++) {
            View nextChild = viewGroup.getChildAt(index);
            System.out.println("nextChild: " + nextChild.getId());
            if(nextChild.getId() < 1000) { // nicht sehr elegant aber geht ^^'
                shellList.add(nextChild.getId());
            }
        }
        // wert der größten shell
        return shellList.stream().max(Comparator.comparing(Integer::valueOf)).get();
    }

    // for clear Button
    @Override
    public void onClick(View v) {

        viewGroup.removeAllViews();
        atomActivity.coreList.clear();

        atomActivity.txtElement.setText("");
        atomActivity.txtSymbol.setText("");
        atomActivity.txtMassEmpiric.setText("");
        atomActivity.txtMassSingle.setText("");

        atomActivity.txtShowCoreAmount.setText("0");
        atomActivity.txtShowNeutronAmount.setText("0");
        atomActivity.txtShowProtonAmount.setText("0");

        atomActivity.ddAtomCore.setLayoutParams(paramsCore);
        // reset für shell adding
        noShell = true;


        // reset Layouts
        //Layout-Settings for Neutron
        neutronLayout = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        neutronLayout.gravity=Gravity.CENTER_VERTICAL| Gravity.LEFT;
        neutronLayout.leftMargin = 125;
        neutronLayout.height = 120;
        neutronLayout.width = 120;

        //Layout-Settings for Proton
        protonLayout = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        protonLayout.gravity=Gravity.CENTER_VERTICAL| Gravity.RIGHT;
        protonLayout.rightMargin = 125; // ?!?!?
        protonLayout.height = 120;
        protonLayout.width = 120;

        layoutForElectron = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);

        // Layout-Settings für Nucleonen im Kern (die Gravity ändert sich)
        layoutForCore = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutForCore.gravity=Gravity.CENTER_VERTICAL| Gravity.CENTER_HORIZONTAL;
        layoutForCore.height = 120;
        layoutForCore.width = 120;

        viewGroup = atomActivity.ddAtomCore;

        // LayoutParams of core
        paramsCore = (RelativeLayout.LayoutParams) atomActivity.ddAtomCore.getLayoutParams();




    }


    /* TODO: hier animation für electronen
        die sollen auf ihrer zugehörigen Schale random ab und wann auftauchen
        --> elektronen animation später (erst Liste mit Elementen!!)
         hier besser Thread?? mit ca. 8 fixen positionen?
         Animation hilft nicht wirklich weil wir ja kein kontinuierlich bewegtes Objekt wollen
     */
    @SuppressLint("ResourceType")
    private void getCurrentShell(){
        List<View> viewList = new ArrayList<>();

        for(int index = 0; index < viewGroup.getChildCount(); index++) {
            View nextChild = viewGroup.getChildAt(index);
            System.out.println("currentViewID is: " + nextChild.getId());

            if(nextChild.getId() < 100) { // nicht sehr elegant aber geht ^^'
                viewList.add(nextChild);
            }
        }

        viewList.stream().forEach(System.out::println);

        View currentView = new View(atomActivity);

        if (viewList.size() == 1) {
            currentView = viewList.get(0);
        } else if(viewList.size() == 2){
            currentView = viewList.get(1);
        }

        System.out.println("currentView: " + currentView.getLayoutParams().height);


        float shellHeight = currentView.getLayoutParams().height;
        float shellWidth = currentView.getLayoutParams().width;

        System.out.println("shellHeight: " + shellHeight + ", shellWidth: " + shellWidth);

        int electrSize = 20;

        //float yWidthHalf = shellHeight/2+electrSize;
        //float xPos = shellWidth/2+electrSize;
        // float xPos = electrSize;

        yPosElecStart = shellHeight/2+electrSize;
        xPosElecStart = electrSize;

        yPosElecEnd = shellHeight/2+electrSize;
        xPosElecEnd = electrSize;


    }

    @SuppressLint("ResourceType")
    private void animatedElectrons(){

        List<View> viewList = new ArrayList<>();

        for(int index = 0; index < viewGroup.getChildCount(); index++) {
            View nextChild = viewGroup.getChildAt(index);
            System.out.println("currentViewID is: " + nextChild.getId());

            if(nextChild.getId() < 100) { // nicht sehr elegant aber geht ^^'
                viewList.add(nextChild);
            }
        }

        viewList.stream().forEach(System.out::println);

        View currentView = new View(atomActivity);

        if (viewList.size() == 1) {
             currentView = viewList.get(0);
        } else if(viewList.size() == 2){
             currentView = viewList.get(1);
        }

        System.out.println("currentView: " + currentView.getLayoutParams().height);


        float shellHeight = currentView.getLayoutParams().height;
        float shellWidth = currentView.getLayoutParams().width;

        System.out.println("shellHeight: " + shellHeight + ", shellWidth: " + shellWidth);

        int electrSize = 20;

        //float yWidthHalf = shellHeight/2+electrSize;
        //float xPos = shellWidth/2+electrSize;
        // float xPos = electrSize;

        yPosElecStart = shellHeight/2+electrSize;
        xPosElecStart = electrSize;

        yPosElecEnd = shellHeight/2+electrSize;
        xPosElecEnd = electrSize;
        // Math.toDegrees(Math.atan2(x - xc, yc - y));
       // float x = (float)Math.toDegrees(Math.cos(50));
       // float y = (float)Math.toDegrees(Math.sin(50));


        TranslateAnimation translateAnimation = new TranslateAnimation(xPosElecStart,xPosElecEnd, yPosElecStart,yPosElecEnd);
      //  translateAnimation.setStartOffset(500);
        translateAnimation.setDuration(1000);
       // translateAnimation.setInterpolator(new BounceInterpolator());
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                    // changeX();
            }

        });

        translateAnimation.setRepeatCount(ObjectAnimator.INFINITE);
        translateAnimation.setRepeatMode(ObjectAnimator.RESTART);

        ImageView electron = new ImageView(atomActivity);
        electron.setImageResource(R.drawable.electron_shape);
        electron.setLayoutParams(layoutForElectron);
        atomActivity.ddAtomCore.addView(electron);



        electron.startAnimation(translateAnimation);
    }

}

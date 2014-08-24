package com.artoos.racing.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by jstevenunez on 024, 8/24/2014.
 */
public class GridPagerAdapter  extends FragmentGridPagerAdapter {
    private final Context mContext;

    Map dataMap;

    public GridPagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        mContext = ctx;
        dataMap = new TreeMap<Integer, HashMap<String, Object>>();

        Map racer = new HashMap<String, Object>();
        racer.put("name", "Steve");
        racer.put("speed", 50.0);
        racer.put("pace", 60.0);
        racer.put("distance", 23.0);
        racer.put("steps", 1234);
        racer.put("cadence", 90);
        racer.put("points", 12578);
        racer.put("position", 1);
        dataMap.put(12578, racer);

        racer = new HashMap<String, Object>();
        racer.put("name", "Jeff");
        racer.put("speed", 20.0);
        racer.put("pace", 10.0);
        racer.put("distance", 33.0);
        racer.put("steps", 1234);
        racer.put("cadence", 90);
        racer.put("points", 6324);
        racer.put("position", 2);
        dataMap.put(6324, racer);
    }

    @Override
    public Fragment getFragment(int row, int col) {
        int index = getRowCount()-1-row;
        int intRow = (Integer)dataMap.keySet().toArray()[index];
        Object o = dataMap.get(intRow);
        Map racerDataMap = (HashMap<Integer, Object>) o;

        String name = (String)racerDataMap.get("name");
        double speed = (Double)racerDataMap.get("speed");
        double pace = (Double)racerDataMap.get("pace");
        double distance = (Double)racerDataMap.get("distance");
        int steps = (Integer)racerDataMap.get("steps");
        int cadence = (Integer)racerDataMap.get("cadence");
        int points = (Integer)racerDataMap.get("points");
        int position = (Integer)racerDataMap.get("position");
        String positionStr = ""+position;
        if(position==1)
            positionStr+="st";
        else if(position==2)
            positionStr+="nd";
        else if(position==3)
            positionStr+="rd";
        else
            positionStr+="th";
        String title = positionStr+": "+name;
        String text = "";
        if(col==0) {
            text = "\nPoints: "+points;
        } else if(col==1) {
            text = "\nSpeed: "+(int)speed+"\nPace: "+(int)pace;
        } else if(col==2) {
            text = "\nDistance: "+(int)distance;
        } else if(col==3) {
            text = "\nSteps: "+(int)steps+"\nCadence: "+(int)cadence;
        }

        CardFragment fragment = CardFragment.create(title, text, 0);
        // Advanced settings
        fragment.setCardGravity(2);
        fragment.setExpansionEnabled(false);
        fragment.setExpansionDirection(CardFragment.EXPAND_DOWN);
        fragment.setExpansionFactor(1.0f);
        return fragment;
    }

//    @Override
//    public ImageReference getBackground(int row, int column) {
//        return ImageReference.forDrawable(BG_IMAGES[row % BG_IMAGES.length]);
//    }

    @Override
    public int getRowCount() {
        return dataMap.keySet().size();
    }

    @Override
    public int getColumnCount(int rowNum) {
        return 4;
    }
}

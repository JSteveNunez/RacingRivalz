package com.artoos.racing.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
        dataMap = new TreeMap();
    }

//    static final int[] BG_IMAGES = new int[] {
//            R.drawable.debug_background_1,
//            R.drawable.debug_background_2,
//            R.drawable.debug_background_3,
//            R.drawable.debug_background_4,
//            R.drawable.debug_background_5
//    };

//    /** A simple container for static data in each page */
//    private static class Page {
//        int titleRes;
//        int textRes;
//        int iconRes;
//        int cardGravity = Gravity.BOTTOM;
//        boolean expansionEnabled = true;
//        float expansionFactor = 1.0f;
//        int expansionDirection = CardFragment.EXPAND_DOWN;
//
//        public Page(int titleRes, int textRes, boolean expansion) {
//            this(titleRes, textRes, 0);
//            this.expansionEnabled = expansion;
//        }
//
//        public Page(int titleRes, int textRes, boolean expansion, float expansionFactor) {
//            this(titleRes, textRes, 0);
//            this.expansionEnabled = expansion;
//            this.expansionFactor = expansionFactor;
//        }
//
//        public Page(int titleRes, int textRes, int iconRes) {
//            this.titleRes = titleRes;
//            this.textRes = textRes;
//            this.iconRes = iconRes;
//        }
//
//        public Page(int titleRes, int textRes, int iconRes, int gravity) {
//            this.titleRes = titleRes;
//            this.textRes = textRes;
//            this.iconRes = iconRes;
//            this.cardGravity = gravity;
//        }
//    }
//
//
//
//    private final Page[][] PAGES = {
//            {
//                    new Page(R.string.welcome_title, R.string.welcome_text, R.drawable.bugdroid,
//                            Gravity.CENTER_VERTICAL),
//            },
//            {
//                    new Page(R.string.about_title, R.string.about_text, false),
//            },
//            {
//                    new Page(R.string.cards_title, R.string.cards_text, true, 2),
//                    new Page(R.string.expansion_title, R.string.expansion_text, true, 10),
//            },
//            {
//                    new Page(R.string.backgrounds_title, R.string.backgrounds_text, true, 2),
//                    new Page(R.string.columns_title, R.string.columns_text, true, 2)
//            },
//            {
//                    new Page(R.string.dismiss_title, R.string.dismiss_text, R.drawable.bugdroid,
//                            Gravity.CENTER_VERTICAL),
//            },
//
//    };

    @Override
    public Fragment getFragment(int row, int col) {
        Map racerDataMap = (HashMap<String, Object>)dataMap.keySet().toArray()[row];

        String title = (String)racerDataMap.get("name");
        String text = "";
        if(col==0) {
            title = ""
            text = (String) racerDataMap.get("name");
        } else if(col==1) {
            text = (String) racerDataMap.get("name");
        } else if(col==2) {
            text = (String) racerDataMap.get("name");
        } else if(col==3) {

        }

        Page page = PAGES[row][col];
        String title = page.titleRes != 0 ? mContext.getString(page.titleRes) : null;
        String text = page.textRes != 0 ? mContext.getString(page.textRes) : null;
        CardFragment fragment = CardFragment.create(title, text, page.iconRes);
        // Advanced settings
        fragment.setCardGravity(page.cardGravity);
        fragment.setExpansionEnabled(page.expansionEnabled);
        fragment.setExpansionDirection(page.expansionDirection);
        fragment.setExpansionFactor(page.expansionFactor);
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

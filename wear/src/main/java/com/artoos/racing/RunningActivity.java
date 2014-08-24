package com.artoos.racing;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.wearable.view.GridViewPager;
import android.widget.TextView;

import com.artoos.racing.adapter.GridPagerAdapter;


public class RunningActivity extends Activity {

    private TextView mTextView;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        GridViewPager pager = (GridViewPager) findViewById(R.id.pager123);
//        pager.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
//            @Override
//            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
//                // Adjust page margins:
//                //   A little extra horizontal spacing between pages looks a bit
//                //   less crowded on a round display.
////                final boolean round = insets.isRound();
//                int rowMargin = res.getDimensionPixelOffset(R.dimen.page_row_margin);
//                int colMargin = res.getDimensionPixelOffset(R.dimen.page_column_margin);
//                pager.setPageMargins(rowMargin, colMargin);
//                return insets;
//            }
//        });
        pager.setAdapter(new GridPagerAdapter(context, getFragmentManager()));




    }
}

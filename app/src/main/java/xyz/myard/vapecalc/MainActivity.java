package xyz.myard.vapecalc;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends AppCompatActivity {
    public PagerAdapter adapter;
    public ViewPager pager;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        setContentView(R.layout.main);

        adapter = new PagerAdapter(
            getSupportFragmentManager()
        );

        pager = (ViewPager) findViewById(R.id.container);
        pager.setAdapter(adapter);

        pager.addOnPageChangeListener(
            new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    InputMethodManager manager = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    manager.hideSoftInputFromWindow(pager.getWindowToken(), 0);
                }

                @Override
                public void onPageScrolled(int position, float offset, int offsetPixels) {
                    // pass
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    // pass
                }
            }

        );
    }
}

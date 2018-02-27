package dekd.intern.bosstanayot.interndekd.activity;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import dekd.intern.bosstanayot.interndekd.R;
import dekd.intern.bosstanayot.interndekd.view.ListFragment;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            showListFrag();
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this);
        shouldDisplayHomeUp();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    public void shouldDisplayHomeUp(){
        boolean canback = getSupportFragmentManager().getBackStackEntryCount()>0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void showListFrag(){
        String tag = ListFragment.class.getSimpleName();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_contianer, ListFragment.newInstance(), tag)
                .commit();
    }
}

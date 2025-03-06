package fpoly.longlt.assignment.screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import fpoly.longlt.assignment.R;
import fpoly.longlt.assignment.fragment.CartFragment;
import fpoly.longlt.assignment.fragment.HomeFragment;
import fpoly.longlt.assignment.fragment.LoveFragment;
import fpoly.longlt.assignment.fragment.OrderFragment;

public class HomeScreen extends AppCompatActivity {
    FrameLayout frameLayout;
    BottomNavigationView navigationView;
    public  static String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            userid = bundle.getString("userid");
        }
        frameLayout = findViewById(R.id.frame_layout);
        navigationView = findViewById(R.id.bottom_nav);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, HomeFragment.newInstance())
                .commit();
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment frag = null;
                if (menuItem.getItemId() == R.id.nav_home) {
                    frag = HomeFragment.newInstance();
                } else if (menuItem.getItemId() == R.id.nav_cart) {
                    frag = CartFragment.newInstance();
                } else if (menuItem.getItemId() == R.id.nav_love) {
                    frag = LoveFragment.newInstance();
                } else if (menuItem.getItemId() == R.id.nav_order) {
                    frag = OrderFragment.newInstance();
                } else if (menuItem.getItemId() == R.id.nav_logout) {
                    finish();
                    startActivity(new Intent(HomeScreen.this, LoginScreen.class));
                    frag = HomeFragment.newInstance();
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, frag)
                        .commit();
                return true;
            }
        });
    }
}
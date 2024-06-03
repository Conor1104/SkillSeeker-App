package com.zybooks.skillseekerapp;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.zybooks.skillseekerapp.R;


public class Directory extends AppCompatActivity {
    private DiscoverFragment dfrag;
    private HomeFragment Hfrag;
    private MessagesFragment Mfrag;
    private PostFragment Pfrag;
    private SettingsFragment Sfrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directory_page);

        dfrag = new DiscoverFragment();
        Hfrag = new HomeFragment();
        Mfrag = new MessagesFragment();
        Pfrag = new PostFragment();
        Sfrag = new SettingsFragment();

        // Add DiscoverFragment to FrameLayout
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, dfrag)
                .commit();
    }
/*
public HomeFragment getHomeFragment() {
    return Hfrag;
}

public MessagesFragment getMessagesFragment() {
    return Mfrag;
}

public PostFragment getPostFragment() {
    return Pfrag;
}

public SettingsFragment getSettingsFragment() {
    return Sfrag;
} */
}

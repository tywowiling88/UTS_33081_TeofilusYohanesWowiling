package umn.ac.id.a33081_teofilusyohaneswowiling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {

    ImageButton ibtnIg, ibtnGit, ibtnLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ibtnIg = findViewById(R.id.ibtnIg);
        ibtnGit = findViewById(R.id.ibtnGit);
        ibtnLink = findViewById(R.id.ibtnLink);

        ibtnIg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String igUrl = "https://www.instagram.com/teoow/";
                Intent igPageIntent = new Intent(Intent.ACTION_VIEW);
                igPageIntent.setData(Uri.parse(igUrl));

                if (igPageIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(igPageIntent);
                }
            }
        });

        ibtnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String linkUrl = "https://www.linkedin.com/in/teofilus-yohanes-wowiling-16274a1b2/";
                Intent linkPageIntent = new Intent(Intent.ACTION_VIEW);
                linkPageIntent.setData(Uri.parse(linkUrl));

                if (linkPageIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(linkPageIntent);
                }
            }
        });

        ibtnGit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gitUrl = "https://github.com/tywowiling88";
                Intent gitPageIntent = new Intent(Intent.ACTION_VIEW);
                gitPageIntent.setData(Uri.parse(gitUrl));

                if (gitPageIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(gitPageIntent);
                }
            }
        });

    }
}
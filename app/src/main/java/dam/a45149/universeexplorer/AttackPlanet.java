package dam.a45149.universeexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class AttackPlanet extends AppCompatActivity {
    private TextView attackTitle;
    private ImageView planetIMG;
    private int index;
    private ImageButton bombimg, infectimg, laserimg, invadeimg, exitimg;
    private boolean bombbtn =false, infectbtn=false, laserbtn=false, invadebtn=false, exitbtn=false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack_planet);
        bombimg = findViewById(R.id.bombButton);
        infectimg = findViewById(R.id.infectButton);
        laserimg  = findViewById(R.id.laserButton);
        invadeimg = findViewById(R.id.invadeButton);
        exitimg   = findViewById(R.id.exitButton);

        attackTitle = findViewById(R.id.attackTitle);
        planetIMG = findViewById(R.id.attackImageView);

        setScreenPlanetInfo();
        buttonStateChanges();
    }

    @SuppressLint({"DefaultLocale", "ResourceType", "Recycle"})
    private void setScreenPlanetInfo(){
        Resources res = getResources();
        Bundle extras = getIntent().getExtras();

        TypedArray arrayPlanets = res.obtainTypedArray(R.array.planets);
        assert extras != null;
        index = extras.getInt("index");

        int idPlanet = arrayPlanets.getResourceId(index, -1);
         TypedArray planet = res.obtainTypedArray(idPlanet);

        //valores immutable
        attackTitle.setText( planet.getString(0));
        planetIMG.setImageResource(planet.getResourceId(3, 0));

    }

    private void buttonStateChanges(){
        bombimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bombbtn = !bombbtn;
                bombimg.setActivated(bombbtn);
            }
        });
        invadeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invadebtn = !invadebtn;
                view.setActivated(invadebtn);
            }
        });

        infectimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infectbtn = !infectbtn;
                view.setActivated(infectbtn);
            }
        });

        laserimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                laserbtn = !laserbtn;
                view.setActivated(laserbtn);
            }
        });




    }
    //botoes back

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

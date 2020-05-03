package dam.a45149.universeexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AttackPlanet extends AppCompatActivity {
    private TextView attackTitle;
    private ImageView planetIMG;
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack_planet);

        attackTitle = findViewById(R.id.attackTitle);
        planetIMG = findViewById(R.id.attackImageView);

        setScreenPlanetInfo();

    }

    @SuppressLint({"DefaultLocale", "ResourceType"})
    private void setScreenPlanetInfo(){
        Resources res = getResources();
        Bundle extras = getIntent().getExtras();

        TypedArray arrayPlanets = res.obtainTypedArray(R.array.planets);
        assert extras != null;
        index = extras.getInt("index");

        int idPlanet = arrayPlanets.getResourceId(index, -1);
        @SuppressLint("Recycle") TypedArray planet = res.obtainTypedArray(idPlanet);

        //valores immutable
        attackTitle.setText( planet.getString(0));
        planetIMG.setImageResource(planet.getResourceId(3, 0));

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

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

public class ImagePlanet extends AppCompatActivity {
    private int index;
    private TextView planetTitle;
    private ImageView planetImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_planet);
        planetTitle = findViewById(R.id.planetTitle);
        planetImg = findViewById(R.id.planetImg);

        setScreenPlanetInfo();
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n", "ResourceType"})
    private void setScreenPlanetInfo(){
        Resources res = getResources();
        Bundle extras = getIntent().getExtras();

        TypedArray arrayPlanets = res.obtainTypedArray(R.array.planets);
        index = extras.getInt("index");

        int idPlanet = arrayPlanets.getResourceId(index, -1);
        TypedArray planet = res.obtainTypedArray(idPlanet);

        planetTitle.setText( planet.getString(0));
        planetImg.setImageResource(planet.getResourceId(4, 0));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}

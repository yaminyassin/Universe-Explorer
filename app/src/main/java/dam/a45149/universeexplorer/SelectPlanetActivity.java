package dam.a45149.universeexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SelectPlanetActivity extends AppCompatActivity {

    private ArrayList<Planet> ColonizedPlanets, freePlanets;
    private ArrayList<Integer> planetIndex;

    private GridView gridView;
    private GridViewAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_planet);
        ColonizedPlanets = new ArrayList<>();
        freePlanets = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        assert extras != null;

        planetIndex = extras.getIntegerArrayList("colonizedPlanet");
        assert planetIndex != null;

        for (int i =0; i< 10; i++ ){
            if(!planetIndex.contains(i))
                freePlanets.add(Planet.getPlanetFromResource(i, getResources()));
        }


        gridView = findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, freePlanets);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    public void onClickGridViewPlanetsItem(View view){
        Planet planet = (Planet) view.getTag();
        int index = planet.getIndex();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("IndexPlanet", index);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuEditar:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

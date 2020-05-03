package dam.a45149.universeexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inspector.StaticInspectionCompanionProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

/****
 * bug quando altero valores e depois entro na imagem os valores fazem reset
 */

public class MainActivity extends AppCompatActivity {
    private LinearLayout planetLayout;
    private ArrayList<Planet> planets;
    boolean pressAgaintoExit = false;
    private final static int PLANET_ACTIVITY_CODE = 10;
    private final static int SELECT_PLANET_ACTIVITY_CODE = 20;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        planets = new ArrayList<>();
        planetLayout = findViewById(R.id.PlanetList);

        for (int i =0; i < 10; i++){
            planets.add(Planet.getPlanetFromResource(i, getResources()));
            addPlanetToUI(planets.get(i), i);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    void addPlanetToUI(final Planet planet, int i){

        //buscar o primeiro layout para levar os parametros
        ImageView imageView = findViewById(R.id.imageView);
        TextView txt = findViewById(R.id.textView);
        LinearLayout lyt = findViewById(R.id.BoxLyt);

        //copiar os parametros para aplicar a seguir
        LinearLayout.LayoutParams imgParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        LinearLayout.LayoutParams textParams = (LinearLayout.LayoutParams)txt.getLayoutParams();
        LinearLayout.LayoutParams planetLayoutParams = (LinearLayout.LayoutParams) lyt.getLayoutParams();

        if(i==0) {
            //alterar img do primeiro
            imageView.setImageResource(planet.getImgSmallResourceValues());
            txt.setText(planet.getName());
            txt.setTextColor(Color.parseColor("#000000"));
            txt.setTextSize(18);
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch(motionEvent.getAction()){
                        case MotionEvent.ACTION_UP:
                            callActivity(planet, ImagePlanet.class);
                            break;
                        default:break;
                    }
                    return true;
                }
            });
            txt.setOnTouchListener(new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch(motionEvent.getAction()){
                        case MotionEvent.ACTION_UP:
                            callActivity(planet, PlanetActivity.class);
                            break;
                        default:break;
                    }
                    return true;
                }
            });

        }
        else {

            //aplicar os parametros
            LinearLayout planetContainer = new LinearLayout(this);
            planetContainer.setLayoutParams(planetLayoutParams);
            planetContainer.setBackgroundColor(Color.parseColor("#FFC107"));
            planetContainer.setGravity(Gravity.CENTER);

            //customize img
            ImageView img = new ImageView(this);
            img.setImageResource(planet.getImgSmallResourceValues());
            img.setLayoutParams(imgParams);
            img.setBackgroundColor(Color.parseColor("#AF6F11"));

            //customize text
            TextView text = new TextView(this);
            text.setText(planet.getName());
            text.setLayoutParams(textParams);
            text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            text.setTextSize(18);
            text.setTextColor(Color.parseColor("#000000"));

            //adicionar img e texto ao LinearLayoutHozizontal
            planetContainer.addView(img);
            planetContainer.addView(text);

            //adicionar horizontal ao scrollview
            planetLayout.addView(planetContainer);

            //ontouchListener no planet container
            img.setOnTouchListener(new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch(motionEvent.getAction()){
                        case MotionEvent.ACTION_UP:
                            callActivity(planet, ImagePlanet.class);
                            break;
                        default:break;
                    }
                    return true;
                }
            });
            text.setOnTouchListener(new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch(motionEvent.getAction()){
                        case MotionEvent.ACTION_UP:
                            callActivity(planet, PlanetActivity.class);
                            break;
                        default:break;
                    }
                    return true;
                }
            });
        }


    }


    private void callActivity(Planet planet, Class c){
        Intent intent = new Intent(MainActivity.this, c);
        intent.putExtra("index", planet.getIndex());
        intent.putExtra("colonies", planet.getNumColonies());
        intent.putExtra("military", planet.getNumMilitaryBases());
        intent.putExtra("forcefield", planet.isForceField());

        startActivityForResult(intent, PLANET_ACTIVITY_CODE);

    }
    //chamar para gravar dados modificados no array de planetas
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode){
            case PLANET_ACTIVITY_CODE:
                if (resultCode == RESULT_OK) { // if OK return code
                    Bundle extras = data.getExtras();
                    assert extras != null;
                    int index = extras.getInt("index");
                    Objects.requireNonNull(extras);
                    planets.get(index).setForceField(extras.getBoolean("forcefield"));
                    planets.get(index).setNumColonies(extras.getInt("colonies"));
                    planets.get(index).setNumMilitaryBases(extras.getInt("military"));
                }else if(resultCode == RESULT_CANCELED){
                    getToast("coco", Toast.LENGTH_LONG);
                }
                break;
            case SELECT_PLANET_ACTIVITY_CODE:
                if (resultCode == RESULT_OK) { // if OK return code
                    Bundle extras = data.getExtras();
                    assert extras != null;
                    int index = extras.getInt("IndexPlanet");
                    Objects.requireNonNull(extras);
                    planets.get(index).setForceField(extras.getBoolean("forcefield"));
                    planets.get(index).setNumColonies(extras.getInt("colonies"));
                    planets.get(index).setNumMilitaryBases(extras.getInt("military"));
                }else if(resultCode == RESULT_CANCELED){
                    getToast("coco", Toast.LENGTH_LONG);
                }
                break;

            default:break;
        }
    }

    void getToast(String str, int lenght){
        Toast.makeText(this, str, lenght).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    /** opcoes do menu*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuEditar:
                getToast("Click on a planet first!", Toast.LENGTH_SHORT);
                return true;
            case R.id.colonizePlanetMenu:
                Intent intent = new Intent(this, SelectPlanetActivity.class);
                ArrayList<Integer> colonizedPlanet = new ArrayList<>();
                for (int i =0; i < planets.size(); i++){
                    if(planets.get(i).getNumColonies()>1) colonizedPlanet.add(planets.get(i).getIndex());
                }
                intent.putExtra("colonizedPlanet", colonizedPlanet);
                startActivityForResult(intent, SELECT_PLANET_ACTIVITY_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**BOTAO de voltar proprio do android este botao tem de ser alterado para  nao deixar sair da aplicacao por engano*/
    @Override
    public void onBackPressed() {
        if(pressAgaintoExit)
            super.onBackPressed();
        else
        {
            pressAgaintoExit=true;
            getToast("Press Back Again to Exit", Toast.LENGTH_SHORT);

            int intervalTime = 1000;
            new Handler().postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {pressAgaintoExit = false;}
                    }
                    ,intervalTime);
        }
    }



}

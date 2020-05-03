package dam.a45149.universeexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class PlanetActivity extends AppCompatActivity {
    private TextView planetTitle, massProps, gravityProps;
    private EditText coloniesProps, militaryProps;
    private Spinner forcefieldProps;
    private ImageView planetIMG;
    private Button saveButton;
    private boolean editMode =false, saved=false;
    private Intent finalIntent;
    private int index;
    private final String[] booleanos = {"true", "false"};

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        /* buscar os id do layout */
        planetTitle = findViewById(R.id.planetTitle);
        massProps = findViewById(R.id.massProps);
        gravityProps = findViewById(R.id.gravityProps);
        coloniesProps = findViewById(R.id.coloniesProps);
        militaryProps = findViewById(R.id.militaryProps);
        planetIMG = findViewById(R.id.planetImage);
        saveButton = findViewById(R.id.saveButton);

        //scroll Force Field
        forcefieldProps = findViewById(R.id.forcefieldProps);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(PlanetActivity.this,
                android.R.layout.simple_list_item_1, booleanos);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        forcefieldProps.setAdapter(myAdapter);

        //criar intent para enviar para MainActivity com os dados mutaveis (Colonies, military e forcefield)
        finalIntent = new Intent();

        //inserir novos dados no layout
        setScreenPlanetInfo();

        //save button escondido
        saveButton.setVisibility(View.INVISIBLE);
        saveButton.setClickable(editMode);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editMode = false;
                saved = true;
                enabling();
                finalIntent.putExtra("index", index);
                finalIntent.putExtra("colonies", Integer.parseInt(coloniesProps.getText().toString()));
                finalIntent.putExtra("military", Integer.parseInt(militaryProps.getText().toString()));
                finalIntent.putExtra("forcefield", Boolean.parseBoolean(forcefieldProps.getSelectedItem().toString()));
                setResult(RESULT_OK, finalIntent);
                getToast("dados guardados!", 1);
            }
        });

    }

    /** funcao que mete os atributos do planeta no layouts */
    @SuppressLint({"DefaultLocale", "SetTextI18n", "ResourceType"})
    private void setScreenPlanetInfo(){
        Resources res = getResources();
        Bundle extras = getIntent().getExtras();

        TypedArray arrayPlanets = res.obtainTypedArray(R.array.planets);
        index = extras.getInt("index");

        int idPlanet = arrayPlanets.getResourceId(index, -1);
        TypedArray planet = res.obtainTypedArray(idPlanet);

        //valores immutable
        planetTitle.setText( planet.getString(0));
        massProps.setText(String.format("%.2f", planet.getFloat(1, 0f)));
        gravityProps.setText(String.format("%.2f", planet.getFloat(2, 0f)));
        planetIMG.setImageResource(planet.getResourceId(3, 0));

        //valores mutable
        int colonies = extras.getInt("colonies");
        int military = extras.getInt("military");
        finalIntent.putExtra("colonies", colonies);
        finalIntent.putExtra("military", military);
        finalIntent.putExtra("forcefield", Boolean.parseBoolean(forcefieldProps.getSelectedItem().toString()));

        coloniesProps.setText(Integer.toString(colonies));
        militaryProps.setText(Integer.toString(military));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    /** funcao que ativa o Modo de edit */
    private void enabling(){
        saveButton.setClickable(editMode);

        coloniesProps.setFocusable(editMode);
        coloniesProps.setEnabled(editMode);
        coloniesProps.setClickable(editMode);
        coloniesProps.setFocusableInTouchMode(editMode);

        militaryProps.setFocusable(editMode);
        militaryProps.setEnabled(editMode);
        militaryProps.setClickable(editMode);
        militaryProps.setFocusableInTouchMode(editMode);

        forcefieldProps.setFocusable(editMode);
        forcefieldProps.setEnabled(editMode);
        forcefieldProps.setClickable(editMode);
        forcefieldProps.setFocusableInTouchMode(editMode);

        if(editMode){
            saveButton.setVisibility(View.VISIBLE);
            coloniesProps.setBackgroundColor(Color.parseColor("#d957db"));
            coloniesProps.setInputType(InputType.TYPE_CLASS_NUMBER);
            militaryProps.setBackgroundColor(Color.parseColor("#d957db"));
            militaryProps.setInputType(InputType.TYPE_CLASS_NUMBER);
            forcefieldProps.setBackgroundColor(Color.parseColor("#d957db"));

        }else{
            saveButton.setVisibility(View.INVISIBLE);
            coloniesProps.setBackgroundColor(Color.parseColor("#00B8D4"));
            militaryProps.setBackgroundColor(Color.parseColor("#00B8D4"));
            forcefieldProps.setBackgroundColor(Color.parseColor("#00B8D4"));

        }
    }


    void getToast(String str, int lenght){
        Toast.makeText(this, str, lenght).show();
    }



    //-------------------------------Handlers para voltar a atividade anterior---------------------

    /** botao de back de android, se tiver em viewMode pode sair, se nao salva primeiro. */
    @Override
    public void onBackPressed() {
        if(editMode)
            getToast("Save first", Toast.LENGTH_SHORT);
        else{
            if(!saved)
                setResult(RESULT_CANCELED, finalIntent);
            super.onBackPressed();
        }

    }

    /** funcao que mexe com os botoes do actionBar (MENU E BOTAO DE VOLTAR) */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuEditar:
                editMode = true;
                enabling();
                return true;
            case android.R.id.home:
                if(editMode){
                    getToast("save first", Toast.LENGTH_SHORT);
                }
                else{
                    if(!saved)
                        setResult(RESULT_CANCELED, finalIntent);
                    finish();
                }
                return true;
            case R.id.showSurface:
                if(editMode){
                    getToast("save First", Toast.LENGTH_SHORT);
                }else{
                    startActivity(new Intent(this, ShowPanetSurface.class));
                }
                return true;
            case R.id.AttackPlanet:
                if(editMode){
                    getToast("save First", Toast.LENGTH_LONG);
                }else{
                    Intent attackIntent = new Intent(this, AttackPlanet.class);
                    attackIntent.putExtra("index", index);
                    attackIntent.putExtra("colonies", Integer.parseInt(coloniesProps.getText().toString()));
                    attackIntent.putExtra("military", Integer.parseInt(militaryProps.getText().toString()));
                    attackIntent.putExtra("forcefield", Boolean.parseBoolean(forcefieldProps.getSelectedItem().toString()));
                    startActivity(attackIntent);

                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

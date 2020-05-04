package dam.a45149.universeexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class AttackPlanet extends AppCompatActivity {
    private TextView attackTitle;
    private ImageView planetIMG;
    private int index;
    private ImageButton bombimgbtn, infectimgbtn, laserimgbtn, invadeimgbtn, exitimgbtn;
    private boolean bombbtn =false, infectbtn=false, laserbtn=false, invadebtn=false, exitbtn=false ;
    private AnimationDrawable infectAnimation;
    private ImageView rocketImage;
    private Animation rotateBomb;

    //music vars
    private Intent musica;
    private SoundPool soundPool;
    private int sound1, sound2, sound3, sound4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack_planet);
        bombimgbtn = findViewById(R.id.bombButton);
        assert bombimgbtn!= null : "OnCreate (AttackPlanet): bombButton";
        infectimgbtn = findViewById(R.id.infectButton);
        laserimgbtn = findViewById(R.id.laserButton);
        invadeimgbtn = findViewById(R.id.invadeButton);
        exitimgbtn = findViewById(R.id.exitButton);

        rocketImage = (ImageView) findViewById(R.id.infectButton);
        rocketImage.setBackgroundResource(R.drawable.animation);
        infectAnimation = (AnimationDrawable) rocketImage.getBackground();

        rotateBomb = AnimationUtils.loadAnimation(this, R.anim.rotation);


        attackTitle = findViewById(R.id.attackTitle);
        planetIMG = findViewById(R.id.attackImageView);

        setScreenPlanetInfo();
        buttonStateChanges();

        musica = new Intent (this , MusicService . class );
        exitimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                stopService (musica);
            }
        });

        setSoundPool();
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
        bombimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bombbtn = !bombbtn;
                view.startAnimation(rotateBomb);
                soundPool.play(sound1, 1, 1, 0, 0, 1);
                /** animação dura 2 segundos */
                new CountDownTimer(2000, 1000) {
                    public void onTick(long millisUntilFinished) {}
                    public void onFinish() {
                        bombimgbtn.clearAnimation();
                    }
                }.start();
                view.setActivated(bombbtn);
            }
        });
        invadeimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invadebtn = !invadebtn;
                soundPool.play(sound2, 1, 1, 0, 0, 1);
                view.setActivated(invadebtn);
            }
        });

        infectimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infectbtn = !infectbtn;
                infectAnimation.start();
                soundPool.play(sound3, 1, 1, 0, 0, 1);
                /** animação dura 1 segundo */
                new CountDownTimer(1000, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }
                    public void onFinish() {
                        infectAnimation.stop();
                    }
                }.start();
                view.setActivated(infectbtn);
            }
        });

        laserimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                laserbtn = !laserbtn;
                soundPool.play(sound4, 1, 1, 0, 0, 1);
                view.setActivated(laserbtn);
            }
        });




    }



    private void setSoundPool(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
        }

        sound1 = soundPool.load(this, R.raw.explosion, 1);
        sound2 = soundPool.load(this, R.raw.transport, 1);
        sound3 = soundPool.load(this, R.raw.virus, 1);
        sound4 = soundPool.load(this, R.raw.laser, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //chamar musica
        startService(musica);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //chamar musica
        soundPool.release();
        soundPool = null;
        stopService (musica);
    }
    @Override
    protected void onStop() {
        super.onStop();
        //chamar musica
        stopService (musica);
    }
    /**
     * botoes back
     */
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

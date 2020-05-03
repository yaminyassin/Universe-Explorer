package dam.a45149.universeexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.Objects;

public class ShowPanetSurface extends AppCompatActivity {
    private VideoView videoPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_panet_surface);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        videoPlayer = findViewById(R.id.videoView);
        Uri planetFlyOverUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.flyover);
        videoPlayer.setVideoURI(planetFlyOverUri);
        // set mediaController
        MediaController videoTransport = new MediaController(this);
        videoTransport.setAnchorView(videoPlayer);
        videoPlayer.setMediaController(videoTransport);

        // set videoPlayer in looping mode
        videoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        // start play
        videoPlayer.start();

    }

    public void onBackPressed() {
        super.onBackPressed();

    }
}

package dam.a45149.universeexplorer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class MusicService extends Service {
    MediaPlayer musicPlayer ;
    @Override
    public IBinder onBind (Intent arg0 ) {
        return null ;
    }
    @Override
    public void onCreate () {
        Toast. makeText (this , " Music Service Created ", Toast . LENGTH_SHORT ). show ();
        musicPlayer = MediaPlayer . create (this , R . raw . music );
        musicPlayer . setLooping ( true );
    }
    @Override
    public int onStartCommand ( Intent intent , int flags , int startid ) {
        Toast . makeText (this , " Music Service Started ", Toast . LENGTH_SHORT ). show ();
        musicPlayer . start ();
        return super . onStartCommand ( intent , flags , startid );
    }
    @Override
    public void onDestroy () {
        Toast . makeText (this , " Music Service Stopped ", Toast . LENGTH_SHORT ). show ();
        musicPlayer . stop ();
    }
}

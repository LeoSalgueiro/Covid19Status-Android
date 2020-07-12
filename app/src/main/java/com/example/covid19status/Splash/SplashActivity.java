package com.example.covid19status.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.covid19status.IntermedioInicioActivity;
import com.example.covid19status.MainActivity;
import com.example.covid19status.R;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3500;

    //variables
    Animation topAnim, bottomAnim;
    ImageView imagen;
    TextView titulo, subtitulo;

    //variables sonido
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


        //Animacion
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks
        imagen = findViewById(R.id.inicioLogoImagen);
        titulo = findViewById(R.id.inicioLogoTitulo);
        subtitulo = findViewById(R.id.inicioLogoSubtitulo);

        imagen.setAnimation(topAnim);
        titulo.setAnimation(bottomAnim);
        subtitulo.setAnimation(bottomAnim);

        //sonido de inicio

        mp = MediaPlayer.create(getBaseContext(), R.raw.puerta_auto); /*Gets your
        soundfile from res/raw/sound.ogg */
        mp.start(); //Starts your sound



        //manejador para cambiar a la actividad principal
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, IntermedioInicioActivity.class);
                startActivity(intent);
                finish();

            }
        }, SPLASH_SCREEN);

    }



        protected void onStop() {
            super.onStop();
            if(mp.isPlaying()){ //Must check if it's playing, otherwise it may be a NPE
                mp.pause(); //Pauses the sound
                //ur.removeCallbacks(myRunnable);
            }
        }
}

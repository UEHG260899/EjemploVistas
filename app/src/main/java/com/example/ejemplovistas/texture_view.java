package com.example.ejemplovistas;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class texture_view extends AppCompatActivity implements TextureView.SurfaceTextureListener {

    private TextureView textureView;
    private MediaPlayer player;
    private AssetFileDescriptor fileDescriptor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texture_view);

        //Código del texture view
        textureView = (TextureView) findViewById(R.id.tv1);

        textureView.setSurfaceTextureListener(this);
        player = new MediaPlayer();
        try {
            fileDescriptor = getAssets().openFd("video_prueba.mp4");
        }catch (IOException e){
            e.printStackTrace();
        }

        Button btnSurface = findViewById(R.id.btn_surface_view);

        btnSurface.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), surface_view.class);
                startActivity(intent);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int width, int height) {
        Surface surfaceV = new Surface(surface);
        try{
            player.setDataSource(fileDescriptor);
            player.setSurface(surfaceV);
            player.prepareAsync();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    player.start();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surface) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(player != null){
            player.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(player != null && player.isPlaying()){
            player.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(player != null){
            player.stop();
            player.release();
            player = null;
        }
    }
}
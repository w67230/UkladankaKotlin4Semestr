package net.fryc.gra

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import net.fryc.gra.screen.startMenu

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        //startGame(4,Difficulty.EASY, this);
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        startMenu(this);
    }
}

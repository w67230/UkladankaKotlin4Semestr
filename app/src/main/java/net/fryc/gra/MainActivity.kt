package net.fryc.gra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.fryc.gra.ui.theme.GraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        startGame(4,this);
    }

}


fun redraw(board: Board, activity: MainActivity){
    activity.setContent {
        GraTheme {
            // A surface container using the 'background' color from the theme
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                draw(board, activity);
            }
        }
    }
}


fun startGame(size : Int = 4, activity: MainActivity){
    val board = Board(size);

    redraw(board, activity);
}

@Composable
fun draw(board : Board, activity: MainActivity, modifier: Modifier = Modifier){
    var y = 0;
    var x = 0;

    Column(modifier) {
        while(y < board.size){
            Row {
                while(x < board.size) {
                    board.getField(x, y)?.drawBox(activity = activity);

                    x++;
                }
            }

            x = 0;
            y++;
        }
    }

}

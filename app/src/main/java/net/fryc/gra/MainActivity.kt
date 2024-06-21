package net.fryc.gra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.fryc.gra.ui.theme.GraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        //startGame(4,Difficulty.EASY, this);
        startMenu(this);
    }

}

fun startMenu(activity: MainActivity){
    activity.setContent {
        GraTheme {
            // A surface container using the 'background' color from the theme
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                menu(activity);
            }
        }
    }
}

@Composable
fun menu(activity: MainActivity){
    var size by remember {
        mutableIntStateOf(4);
    }
    var difficulty by remember {
        mutableStateOf(Difficulty.EASY);
    }
    Column {
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Button(onClick = {
                startGame(size, difficulty, activity);
            }) {
                Text(text = "Start Game");
            }
        }

        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Button(enabled = size != 4, onClick = {
                size = 4;
            }) {
                Text(text = "4");
            }
            Button(enabled = size != 5, onClick = {
                size = 5;
            }) {
                Text(text = "5");
            }
            Button(enabled = size != 6, onClick = {
                size = 6;
            }) {
                Text(text = "6");
            }
        }

        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Button(enabled = difficulty != Difficulty.EASY, onClick = {
                difficulty = Difficulty.EASY;
            }) {
                Text(text = "Easy");
            }
            Button(enabled = difficulty != Difficulty.NORMAL, onClick = {
                difficulty = Difficulty.NORMAL;
            }) {
                Text(text = "Normal");
            }
            Button(enabled = difficulty != Difficulty.HARD, onClick = {
                difficulty = Difficulty.HARD;
            }) {
                Text(text = "Hard");
            }
            Button(enabled = difficulty != Difficulty.VERY_HARD, onClick = {
                difficulty = Difficulty.VERY_HARD;
            }) {
                Text(text = "Very hard");
            }
        }

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


fun startGame(size : Int = 4, difficulty : Difficulty = Difficulty.EASY, activity: MainActivity){
    val board = Board(size, difficulty);

    redraw(board, activity);
}

@Composable
fun draw(board : Board, activity: MainActivity, modifier: Modifier = Modifier){
    var y = 0;
    var x = 0;
    Column(modifier) {
        Spacer(modifier = Modifier.size(100.dp));
        while(y < board.size){
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                while(x < board.size) {
                    board.getField(x, y)?.drawBox(activity = activity);

                    x++;
                }
            }

            x = 0;
            y++;
        }

        val field = board.getField(y-1, y-1);
        if(field != null){
            if(field.value < 0){
                if(board.checkWin()){
                    Box {
                        Text(text = "Wygrales");
                    }
                }
            }
        }
    }

}

package net.fryc.gra.screen

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.fryc.gra.MainActivity
import net.fryc.gra.R
import net.fryc.gra.board.Board
import net.fryc.gra.board.Difficulty
import net.fryc.gra.ui.theme.GraTheme

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
    Column(modifier = Modifier.padding(start = 30.dp, end = 20.dp, top = 80.dp)) {
        Text(text = stringResource(R.string.app_name), modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(bottom = 50.dp), fontSize = 50.sp);
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = stringResource(R.string.size_info), fontSize = 20.sp, modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 17.dp));
            Slider(modifier = Modifier
                .width(100.dp)
                .padding(end = 7.dp),value = size.toFloat(),valueRange = 4f..6f, steps = 3, onValueChange = {
                size = it.toInt();
            })
            val sizeString = when(size){
                4 -> stringResource(R.string.size_4);
                5 -> stringResource(R.string.size_5);
                6 -> stringResource(R.string.size_6);
                else -> stringResource(R.string.size_6);
            }
            Text(text = sizeString, fontSize = 20.sp, modifier = Modifier
                .align(Alignment.CenterVertically)
                .fillMaxWidth(0.6f));
        }

        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = stringResource(R.string.difficulty_info), fontSize = 20.sp, modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 7.dp));
            Slider(modifier = Modifier
                .width(100.dp)
                .padding(end = 7.dp),value = difficulty.ordinal.toFloat(),valueRange = 0f..3f, steps = 4, onValueChange = {
                difficulty = when(it.toInt()){
                    0 -> Difficulty.EASY;
                    1 -> Difficulty.NORMAL;
                    2 -> Difficulty.HARD;
                    3 -> Difficulty.VERY_HARD;
                    else -> Difficulty.VERY_HARD;
                }
            });

            val diffString = when(difficulty){
                Difficulty.EASY -> stringResource(R.string.easy);
                Difficulty.NORMAL -> stringResource(R.string.normal);
                Difficulty.HARD -> stringResource(R.string.hard);
                Difficulty.VERY_HARD -> stringResource(R.string.v_hard);
            }

            Text(text = diffString, fontSize = 20.sp, modifier = Modifier
                .align(Alignment.CenterVertically)
                .fillMaxWidth(0.6f));

        }
        Row(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 20.dp)) {
            Button(onClick = {
                startGame(size, difficulty, activity);
            }) {
                Text(text = stringResource(R.string.start));
            }
        }
        Row(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 20.dp)) {
            Button(onClick = {
                // todo nowy ekran zrobic
            }) {
                Text(text = stringResource(R.string.h2p));
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

        Spacer(modifier = Modifier.size(90.dp));
        Button(onClick = {
            startMenu(activity);
        }) {
            Text(text = stringResource(R.string.menu));
        }

        val field = board.getField(y-1, y-1);
        if(field != null){
            if(field.value < 0){
                if(board.checkWin()){
                    AlertDialog(onDismissRequest = {
                        startMenu(activity);
                    }, confirmButton = {
                        TextButton(onClick = {
                            startMenu(activity);
                        }) {
                            Text(text = "Ok");
                        }
                    }, text = {
                        Text(text = stringResource(R.string.win));
                    });

                }
            }
        }
    }
}
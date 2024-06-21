package net.fryc.gra

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.math.abs


class Field(var y: Int, var x : Int, val color : Color, val value : Int, val board: Board) {

    @Composable
    fun drawBox(activity: MainActivity){
        Box(Modifier.height(70.dp).width(70.dp).padding(5.dp, 5.dp).background(this.color).clickable {
            val blackField = board.getBlackField();
            if(this.isNextToField(blackField)){
                var tempX = this.x;
                var tempY = this.y;
                this.x = blackField.x;
                this.y = blackField.y;
                blackField.x = tempX;
                blackField.y = tempY;

                redraw(this.board, activity);
            }

        }) {
            if(this@Field.value > 0){
                Text(text = this@Field.value.toString(), modifier = Modifier.align(Alignment.Center), fontWeight = FontWeight.Bold);
            }
        }
    }

    fun isNextToField(field: Field) : Boolean {
        val xDiff = abs(this.x - field.x);
        val yDiff = abs(this.y - field.y);
        return (xDiff == 1 && yDiff == 0) || (xDiff == 0 && yDiff == 1);
    }

    fun hasSamePositionAsOtherField() : Boolean {
        for(field in this.board.fields){
            if(field.x == this.x && field.y == this.y){
                return true;
            }
        }

        return false;
    }
}


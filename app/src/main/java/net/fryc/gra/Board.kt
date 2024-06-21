package net.fryc.gra

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

class Board(val size : Int) {

    val fields = ArrayList<Field>();
    var redFieldsCount = 0;
    var greenFieldsCount = 0;
    var blueFieldsCount = 0;
    var magentaFieldsCount = 0;
    var grayFieldsCount = 0;
    var yellowFieldsCount = 0;

    init{
        this.createFields();
    }

    private fun createFields(){
        this.fields.add(createBlackField());

        var i = this.fields.size;
        val numberOfFields = this.size*this.size;
        while(i < numberOfFields){
            this.fields.add(this.createRandomNonConflictingField());
            i++;
        }
    }

    private fun createBlackField() : Field {
        return Field(0,0, Color.Black,-1, this);
    }

    private fun createRandomNonConflictingField() : Field {
        val field = Field(Random.nextInt(0, this.size), Random.nextInt(0, this.size), this.getRandomNonConflictingColor(), Random.nextInt(1, 72), this);

        while(field.hasSamePositionAsOtherField()){
            field.x = Random.nextInt(0, this.size);
            field.y = Random.nextInt(0, this.size);
        }

        return field;
    }

    private fun getRandomNonConflictingColor() : Color {
        var number : Int;
        do{
            number = Random.nextInt(0,this.size);
        }while(!this.isColorAvailable(number));

        when(number){
            0 -> {
                redFieldsCount++;
                return Color.Red
            };
            1 -> {
                greenFieldsCount++;
                return Color.Green
            };
            2 -> {
                blueFieldsCount++;
                return Color.Blue
            };
            3 -> {
                magentaFieldsCount++;
                return Color.Magenta
            };
            4 -> {
                grayFieldsCount++;
                return Color.DarkGray
            };
            else -> {
                yellowFieldsCount++;
                return Color.Yellow
            };
        }
    }

    private fun isColorAvailable(number : Int) : Boolean {
        return when(number){
            0 -> redFieldsCount < this.size;
            1 -> greenFieldsCount < this.size;
            2 -> blueFieldsCount < this.size;
            3 -> if(this.size > 4) magentaFieldsCount < this.size else magentaFieldsCount < this.size-1;
            4 -> if(this.size > 5) grayFieldsCount < this.size else grayFieldsCount < this.size-1;
            else -> yellowFieldsCount < this.size-1;
        }
    }

    fun getBlackField() : Field {
        if(this.fields.isEmpty()){
            this.createFields();
        }
        for (field in this.fields){
            if(field.value < 0) return field;
        }

        throw Exception("Black field doesn't exist! It should never happen!");
    }

    fun getField(x : Int, y : Int) : Field? {
        for(field in this.fields){
            if(field.x == x && field.y == y){
                return field;
            }
        }

        return null;
    }
}
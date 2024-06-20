package net.fryc.gra

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

class Board(val size : Int) {

    val fields = ArrayList<Field>();

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

        // TODO

        if(Random.nextBoolean()){
            return Color.Red;
        }
        return Color.Gray;
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
package com.abhi.game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // Counter to determine turns. isYellow = true (Yellow's turn) else red's turn
    boolean isYellow = true;

    /*Cells Occupied:
    * 2 - Vacant
    * 0 - Yellow
    * 1 - Red
    * */
    int[] cellsOccupied = {2,2,2,2,2,2,2,2,2}; //What cells have been occupied

    /*
    * WINNING COMBINATIONS
    *
    * */
    int [][] win_combos = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}}; // referring to cells occupied by respective numbers.

    boolean isActive = true; // Checks if the game is on

    /*
    * On click method
    * */
    public void playThis(View v) {

        TextView chance = findViewById(R.id.chance); // Check who's turn is it
        TextView turn = findViewById(R.id.turn);    //Convert from turn to winner after the match
        Button restart = findViewById(R.id.restart);
        // Retrieves cell of the grid layout was selected.
        ImageView counter = (ImageView) v;
        //System.out.println(counter.getTag().toString()); // Displays what cell has been occupied
        /*Three Steps needed to be done:

        * 1. Picks the cell (out of the board)
        * 2. Set image to the respective cell (Red or yellow)
        * 3. Places  the cell back in the board*/

        //1. Picks the cell out of the board ( Translation plays a role here)

        /*
        Check if the respective cell is occupied*/

        int cellno = Integer.parseInt(counter.getTag().toString());

        if (cellsOccupied[cellno]==2 && isActive) {
            counter.setTranslationY(-1000f);

            if (isYellow) {
                cellsOccupied[cellno] = 0;
                //2. Set image to the respective cell (Yellow in this case)
                counter.setImageResource(R.drawable.yellow);
                isYellow = false;
                chance.setText("Red");
            } else {
                cellsOccupied[cellno] = 1;
                counter.setImageResource(R.drawable.red);
                isYellow = true;
                chance.setText("Yellow");
            }
            //3. Place that cell back in the board.
            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);

            /*
            /*Check if somebody won (Iterate through the winning combination)
            */

            for(int[] win_combo : win_combos){
                if(cellsOccupied[win_combo[0]] == cellsOccupied[win_combo[1]]&&
                        cellsOccupied[win_combo[1]] == cellsOccupied[win_combo[2]]&& cellsOccupied[win_combo[0]]!=2) {
                    isActive = false;
                            if(cellsOccupied[win_combo[0]]==1){
                                turn.setText("Red");
                                chance.setText("Wins!");
                                restart.setText("Play Again");

                            }
                            else if(cellsOccupied[win_combo[0]]==0){
                                turn.setText("Yellow");
                                chance.setText("Wins!");
                                restart.setText("Play Again");
                            }
                         }else{
                            boolean gameOver=true;
                            for(int draw_chances : cellsOccupied){
                                if(draw_chances == 2)
                                    gameOver = false;
                            }
                            if(gameOver){
                            turn.setText("It's A");
                            chance.setText("Draw!");
                        }

                }

            }
        }
    }

    /*
    * Restart
    * */
    public void reset(View v){

        //Starts from yellow again
        isYellow = true;
        isActive = true;

        //Resets message board below
        TextView chance = findViewById(R.id.chance); // Check who's turn is it
        TextView turn = findViewById(R.id.turn);
        turn.setText("Turn: ");
        chance.setText("Yellow");

        //vacants all occupied cells (2 is for vacancy)
        for(int i = 0; i < cellsOccupied.length;i++){
            cellsOccupied[i] = 2;
        }

        //Empty all the images (aka remove all coins from the board)
        GridLayout gl = (GridLayout) findViewById(R.id.gl);
        // We know grid layout has only imageviews so we can simply loop it

        for (int i=0;i<gl.getChildCount();i++){
            ((ImageView) gl.getChildAt(i)).setImageResource(0); //Gets Child node and parses it to image view which is followed by setting image resource to null (0)
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

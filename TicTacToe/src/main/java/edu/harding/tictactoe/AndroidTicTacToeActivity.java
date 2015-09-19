package edu.harding.tictactoe;


import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import edu.harding.tictactoe.DifficultyAlertDialog.AlertPositiveLevelListener;
import edu.harding.tictactoe.QuitAlertDialog.AlertPositiveQuitListener;

import java.util.Random;

public class AndroidTicTacToeActivity extends ActionBarActivity implements AlertPositiveLevelListener, AlertPositiveQuitListener {

    private TicTacToeGame mGame;

    private Button mBoardButtons[];
    private TextView mInfotextView;

    private TextView mAndroidScoretextView;
    private TextView mTiesScoretextView;
    private TextView mHumanScoretextView;

    private int totalTiesScore;
    private int totalHumanScore;
    private int totalAndroidScore;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_tic_tac_toe);

        totalAndroidScore = 0;
        totalHumanScore = 0;
        totalTiesScore = 0;

        position = 0;
        /*
        mBoardButtons = new Button[TicTacToeGame.BOARD_SIZE];
        mBoardButtons[0] = (Button) findViewById(R.id.one);
        mBoardButtons[1] = (Button) findViewById(R.id.two);
        mBoardButtons[2] = (Button) findViewById(R.id.three);
        mBoardButtons[3] = (Button) findViewById(R.id.four);
        mBoardButtons[4] = (Button) findViewById(R.id.five);
        mBoardButtons[5] = (Button) findViewById(R.id.six);
        mBoardButtons[6] = (Button) findViewById(R.id.seven);
        mBoardButtons[7] = (Button) findViewById(R.id.eight);
        mBoardButtons[8] = (Button) findViewById(R.id.nine);*/

        mInfotextView = (TextView) findViewById(R.id.information);

        mTiesScoretextView = (TextView) findViewById(R.id.score_ties);
        mHumanScoretextView = (TextView) findViewById(R.id.score_human);
        mAndroidScoretextView = (TextView) findViewById(R.id.score_android);

        mGame = new TicTacToeGame();

        this.startNewGame();
    }

    private void startNewGame() {
        Random random = new Random();
        mGame.clearBoard();

        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText(" ");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
        }
        if (random.nextInt(2) == 0) {
            mInfotextView.setText(R.string.first_turn_human);
        } else {
            mInfotextView.setText(R.string.first_turn_android);
            int move = mGame.getComputerMove();
            setMove(TicTacToeGame.COMPUTER_PLAYER, move);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_android_tic_tac_toe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        switch (item.getItemId()) {
            case R.id.new_game:
                startNewGame();
                break;
            case R.id.ai_difficulty:
                showDifficultyAlertDialog();
                break;
            case R.id.about:
                showAboutDialog();
                break;
            case R.id.quit:
                quitGame();
                break;
        }
        return true;
    }

    private void showAboutDialog() {
        FragmentManager manager = getFragmentManager();
        AboutAlertDialog aboutAlertDialog = new AboutAlertDialog();
        aboutAlertDialog.show(manager, "about");
    }

    public void quitGame() {
        FragmentManager manager = getFragmentManager();
        QuitAlertDialog quitAlertDialog = new QuitAlertDialog();
        quitAlertDialog.show(manager, "alert_quit");
    }

    @Override
    public void onPositiveQuitClick(boolean quit) {
        AndroidTicTacToeActivity.this.finish();
    }

    public void showDifficultyAlertDialog() {
        FragmentManager manager = getFragmentManager();
        DifficultyAlertDialog difficultyAlertDialog = new DifficultyAlertDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        difficultyAlertDialog.setArguments(bundle);
        difficultyAlertDialog.show(manager, "alert_dialog_radio");
    }

    @Override
    public void onPositiveLevelClick(int position) {
        this.position = position;
        switch (position) {
            case 0:
                mGame.setmDifficultyLevel(TicTacToeGame.DifficultyLevel.Easy);
                break;
            case 1:
                mGame.setmDifficultyLevel(TicTacToeGame.DifficultyLevel.Harder);
                break;
            case 2:
                mGame.setmDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert);
        }
        Toast.makeText(getApplicationContext(), String.valueOf(TicTacToeGame.difficultyLevels[position]), Toast.LENGTH_SHORT).show();
    }


    private void setMove(char player, int location) {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if (player == TicTacToeGame.HUMAN_PLAYER)
            mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0));
        else
            mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0));

    }


    //interface

    private class ButtonClickListener implements View.OnClickListener {
        int location;

        public ButtonClickListener(int location) {
            this.location = location;
        }

        @Override
        public void onClick(View v) {
            if (mBoardButtons[location].isEnabled()) {
                setMove(TicTacToeGame.HUMAN_PLAYER, location);

                int winner = mGame.checkForWinner();
                if (winner == 0) {
                    mInfotextView.setText(R.string.turn_computer);
                    int move = mGame.getComputerMove();
                    setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    winner = mGame.checkForWinner();
                }
                if (winner == 0) {
                    mInfotextView.setText(R.string.turn_human);
                } else if (winner == 1) {
                    totalTiesScore += 1;
                    mInfotextView.setText(R.string.result_tie);
                    mTiesScoretextView.setText(String.valueOf(totalTiesScore));


                } else if (winner == 2) {
                    totalHumanScore += 1;
                    mInfotextView.setText(R.string.result_human_win);
                    mHumanScoretextView.setText(String.valueOf(totalHumanScore));

                } else {
                    totalAndroidScore += 1;
                    mInfotextView.setText(R.string.result_computer_win);
                    mAndroidScoretextView.setText(String.valueOf(totalAndroidScore));

                }

            }
        }
    }
}

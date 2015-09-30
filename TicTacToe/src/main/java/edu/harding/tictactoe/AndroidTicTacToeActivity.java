package edu.harding.tictactoe;


import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import edu.harding.tictactoe.DifficultyAlertDialog.AlertPositiveLevelListener;
import edu.harding.tictactoe.QuitAlertDialog.AlertPositiveQuitListener;

import java.util.Random;

public class AndroidTicTacToeActivity extends ActionBarActivity implements AlertPositiveLevelListener, AlertPositiveQuitListener {

    private SharedPreferences mPrefs;

    private MediaPlayer mHumanMediaPlayer;
    private MediaPlayer mAndroidMediaPlayer;

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

    private BoardView mBoardView;

    private boolean mHumanGoFirst;

    private boolean mGameOver;
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int col = (int) event.getX() / mBoardView.getBoardCellWidth();
            int row = (int) event.getY() / mBoardView.getBoardVellHeight();
            int pos = row * 3 + col;

            if (!mGameOver && setMove(TicTacToeGame.HUMAN_PLAYER, pos)) {
                makeAndroidMove();
            }

            return false;
        }
    };

    private void makeAndroidMove() {
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
            mGameOver = true;

        } else if (winner == 2) {
            totalHumanScore += 1;
            mInfotextView.setText(R.string.result_human_win);
            mHumanScoretextView.setText(String.valueOf(totalHumanScore));
            mGameOver = true;

        } else {
            totalAndroidScore += 1;
            mInfotextView.setText(R.string.result_computer_win);
            mAndroidScoretextView.setText(String.valueOf(totalAndroidScore));
            mGameOver = true;

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharArray("board", mGame.getBoardState());
        outState.putBoolean("mGameOver", mGameOver);
        outState.putInt("totalHumanScore", Integer.valueOf(totalHumanScore));
        outState.putInt("totalAndroidScore", Integer.valueOf(totalAndroidScore));
        outState.putInt("totalTiesScore", Integer.valueOf(totalTiesScore));
        outState.putCharSequence("info", mInfotextView.getText());
        outState.putBoolean("mHumanGoFirst", mHumanGoFirst);
        outState.putString("difficulty", mGame.getmDifficultyLevel().name());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mGame.setBoardState(savedInstanceState.getCharArray("board"));
        mGameOver = savedInstanceState.getBoolean("mGameOver");
        totalHumanScore = savedInstanceState.getInt("totalHumanScore");
        totalAndroidScore = savedInstanceState.getInt("totalAndroidScore");
        totalTiesScore = savedInstanceState.getInt("totalTiesScore");
        mHumanGoFirst = savedInstanceState.getBoolean("mHumanGoFirst");
        mInfotextView.setText(savedInstanceState.getCharSequence("info"));
        mGame.setmDifficultyLevel(TicTacToeGame.DifficultyLevel.valueOf(savedInstanceState.getString("difficulty")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_tic_tac_toe);

        totalAndroidScore = 0;
        totalHumanScore = 0;
        totalTiesScore = 0;

        position = 0;


        mInfotextView = (TextView) findViewById(R.id.information);

        mTiesScoretextView = (TextView) findViewById(R.id.score_ties);
        mHumanScoretextView = (TextView) findViewById(R.id.score_human);
        mAndroidScoretextView = (TextView) findViewById(R.id.score_android);

        mGame = new TicTacToeGame();
        mBoardView = (BoardView) findViewById(R.id.board);
        mBoardView.setmGame(mGame);

        mBoardView.setOnTouchListener(mTouchListener);
        if (savedInstanceState == null) {
            startNewGame();
        } else {
            mGame.setBoardState(savedInstanceState.getCharArray("board"));
            mGameOver = savedInstanceState.getBoolean("mGameOver");
            totalHumanScore = savedInstanceState.getInt("totalHumanScore");
            totalAndroidScore = savedInstanceState.getInt("totalAndroidScore");
            totalTiesScore = savedInstanceState.getInt("totalTiesScore");
            mHumanGoFirst = savedInstanceState.getBoolean("mHumanGoFirst");
            mInfotextView.setText(savedInstanceState.getCharSequence("info"));
            mGame.setmDifficultyLevel(TicTacToeGame.DifficultyLevel.valueOf(savedInstanceState.getString("difficulty")));
        }
        displayScores();
        mPrefs = getSharedPreferences("ttt_prefs", MODE_PRIVATE);
    }


    private void displayScores() {
        mHumanScoretextView.setText(Integer.toString(totalHumanScore));
        mAndroidScoretextView.setText(Integer.toString(totalAndroidScore));
        mTiesScoretextView.setText(Integer.toString(totalTiesScore));
    }

    private void startNewGame() {

        mGame.clearBoard();
        mBoardView.invalidate();
        mGameOver = false;

        setTurn();

        if (mHumanGoFirst) {
            mInfotextView.setText(R.string.first_turn_human);
        } else {
            mInfotextView.setText(R.string.first_turn_android);
            int move = mGame.getComputerMove();
            setMove(TicTacToeGame.COMPUTER_PLAYER, move);
        }
    }

    private void setTurn() {
        Random random = new Random();
        if (random.nextInt(2) == 0) {
            mHumanGoFirst = true;
        } else {
            mHumanGoFirst = false;
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


    //interface

    private boolean setMove(char player, int location) {
        if (mGame.setMove(player, location)) {
            mBoardView.invalidate();
            if (player == TicTacToeGame.HUMAN_PLAYER) {
                mHumanMediaPlayer.start();
            }
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        mHumanMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.x_sound);
    }

    @Override
    public void onPause() {
        super.onPause();
        mHumanMediaPlayer.release();
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putInt("totalHumanScore", totalHumanScore);
        ed.putInt("totalAndroidScore", totalAndroidScore);
        ed.putInt("totalTiesScore", totalTiesScore);
        ed.commit();
    }

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
                    mGameOver = true;


                } else if (winner == 2) {
                    totalHumanScore += 1;
                    mInfotextView.setText(R.string.result_human_win);
                    mHumanScoretextView.setText(String.valueOf(totalHumanScore));
                    mGameOver = true;

                } else {
                    totalAndroidScore += 1;
                    mInfotextView.setText(R.string.result_computer_win);
                    mAndroidScoretextView.setText(String.valueOf(totalAndroidScore));
                    mGameOver = true;

                }

            }
        }
    }
}

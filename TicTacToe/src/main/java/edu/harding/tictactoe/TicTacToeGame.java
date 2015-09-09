package edu.harding.tictactoe;

/**
 * Created by mordreth on 9/9/15.
 */
/* TicTacToeConsole.java * By Frank McCown (Harding University) * * This is a tic-tac-toe game that runs in the console window. The human * is X and the computer is O. */

import android.widget.Button;
import android.widget.TextView;

import java.io.Console;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class TicTacToeGame {
    private char mBoard[] = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};


    private Random mRand;
    public static final char HUMAN_PLAYER = 'X';
    public static final char COMPUTER_PLAYER = 'O';
    public static final char OPEN_SPOT = ' ';
    public static final int BOARD_SIZE = 9;


    public TicTacToeGame() {
        // Seed the random number generator
        mRand = new Random();
    }

    private void displayBoard() {
        System.out.println();
        System.out.println(mBoard[0] + " | " + mBoard[1] + " | " + mBoard[2]);
        System.out.println("-----------");
        System.out.println(mBoard[3] + " | " + mBoard[4] + " | " + mBoard[5]);
        System.out.println("-----------");
        System.out.println(mBoard[6] + " | " + mBoard[7] + " | " + mBoard[8]);
        System.out.println();
    }

    /*
    // Check for a winner. Return
    // 0 if no winner or tie yet
    // 1 if it's a tie // 2 if X won // 3 if O won
    private int checkForWinner1() {
        // Check horizontal wins
        for (int i = 0; i <= 6; i += 3) {
            if (mBoard[i] == HUMAN_PLAYER && mBoard[i + 1] == HUMAN_PLAYER && mBoard[i + 2] == HUMAN_PLAYER) return 2;
            if (mBoard[i] == COMPUTER_PLAYER && mBoard[i + 1] == COMPUTER_PLAYER && mBoard[i + 2] == COMPUTER_PLAYER)
                return 3;
        }
        // Check vertical wins
        for (int i = 0; i <= 2; i++) {
            if (mBoard[i] == HUMAN_PLAYER && mBoard[i + 3] == HUMAN_PLAYER && mBoard[i + 6] == HUMAN_PLAYER) return 2;
            if (mBoard[i] == COMPUTER_PLAYER && mBoard[i + 3] == COMPUTER_PLAYER && mBoard[i + 6] == COMPUTER_PLAYER)
                return 3;
        }
        // Check for diagonal wins
        if ((mBoard[0] == HUMAN_PLAYER && mBoard[4] == HUMAN_PLAYER && mBoard[8] == HUMAN_PLAYER) || (mBoard[2] == HUMAN_PLAYER && mBoard[4] == HUMAN_PLAYER && mBoard[6] == HUMAN_PLAYER))
            return 2;
        if ((mBoard[0] == COMPUTER_PLAYER && mBoard[4] == COMPUTER_PLAYER && mBoard[8] == COMPUTER_PLAYER) || (mBoard[2] == COMPUTER_PLAYER && mBoard[4] == COMPUTER_PLAYER && mBoard[6] == COMPUTER_PLAYER))
            return 3;
        // Check for tie
        for (int i = 0; i < BOARD_SIZE; i++) {
            // If we find a number, then no one has won yet
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) return 0;
        }
        // If we make it through the previous loop, all places are taken, so it's a tie
        return 1;
    }
*/
/*
    private void getComputerMove1() {
        int move;
        // First see if there's a move O can make to win
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                char curr = mBoard[i];
                mBoard[i] = COMPUTER_PLAYER;
                if (checkForWinner() == 3) {
                    System.out.println("Computer is moving to " + (i + 1));
                    return;
                } else mBoard[i] = curr;
            }
        }
        // See if there's a move O can make to block X from winning
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                char curr = mBoard[i];
                // Save the current number
                mBoard[i] = HUMAN_PLAYER;
                if (checkForWinner() == 2) {
                    mBoard[i] = COMPUTER_PLAYER;
                    System.out.println("Computer is moving to " + (i + 1));
                    return;
                } else mBoard[i] = curr;
            }
        }
        // Generate random move
        do {
            move = mRand.nextInt(BOARD_SIZE);
        } while (mBoard[move] == HUMAN_PLAYER || mBoard[move] == COMPUTER_PLAYER);
        System.out.println("Computer is moving to " + (move + 1));
        mBoard[move] = COMPUTER_PLAYER;
    }
*/

    //Clear the board of all X' and 0' by setting all spots to OPEN_SPOT
    public void clearBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            mBoard[i] = OPEN_SPOT;
        }
    }

    //Set the given player at the given location on the game board
    //the location must be avaliable or the board will not change
    public void setMove(char player, int location) {
        if (this.mBoard[location] == this.OPEN_SPOT) {
            this.mBoard[location] = player;
        }
    }

    //return the best move for the computer to make. You must call setMove()
    // to make the computer move to that location
    public int getComputerMove() {
        int move;
        // First see if there's a move computer can make to win
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                char curr = mBoard[i];
                mBoard[i] = COMPUTER_PLAYER;
                if (checkForWinner() == 3) {
                    System.out.println("Computer is moving to " + (i + 1));
                    return i;
                } else mBoard[i] = curr;
            }
        }
        // See if there's a move O can make to block X from winning
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                char curr = mBoard[i];
                // Save the current number
                mBoard[i] = HUMAN_PLAYER;
                if (checkForWinner() == 2) {
                    mBoard[i] = COMPUTER_PLAYER;
                    System.out.println("Computer is moving to " + (i + 1));
                    return i;
                } else mBoard[i] = curr;
            }
        }
        // Generate random move
        do {
            move = mRand.nextInt(BOARD_SIZE);
        } while (mBoard[move] == HUMAN_PLAYER || mBoard[move] == COMPUTER_PLAYER);
        System.out.println("Computer is moving to " + (move + 1));
        mBoard[move] = COMPUTER_PLAYER;
        return move;
    }

    //Check for a winner and return a status value indicating who has won.
    //return 0 if not winner or tie yet, 1 if it's a tie, 2 if X won, or 3
    //if computer won.
    public int checkForWinner() {
        // Check horizontal wins
        for (int i = 0; i <= 6; i += 3) {
            if (mBoard[i] == HUMAN_PLAYER && mBoard[i + 1] == HUMAN_PLAYER && mBoard[i + 2] == HUMAN_PLAYER) return 2;
            if (mBoard[i] == COMPUTER_PLAYER && mBoard[i + 1] == COMPUTER_PLAYER && mBoard[i + 2] == COMPUTER_PLAYER)
                return 3;
        }
        // Check vertical wins
        for (int i = 0; i <= 2; i++) {
            if (mBoard[i] == HUMAN_PLAYER && mBoard[i + 3] == HUMAN_PLAYER && mBoard[i + 6] == HUMAN_PLAYER) return 2;
            if (mBoard[i] == COMPUTER_PLAYER && mBoard[i + 3] == COMPUTER_PLAYER && mBoard[i + 6] == COMPUTER_PLAYER)
                return 3;
        }
        // Check for diagonal wins
        if ((mBoard[0] == HUMAN_PLAYER && mBoard[4] == HUMAN_PLAYER && mBoard[8] == HUMAN_PLAYER) || (mBoard[2] == HUMAN_PLAYER && mBoard[4] == HUMAN_PLAYER && mBoard[6] == HUMAN_PLAYER))
            return 2;
        if ((mBoard[0] == COMPUTER_PLAYER && mBoard[4] == COMPUTER_PLAYER && mBoard[8] == COMPUTER_PLAYER) || (mBoard[2] == COMPUTER_PLAYER && mBoard[4] == COMPUTER_PLAYER && mBoard[6] == COMPUTER_PLAYER))
            return 3;
        // Check for tie
        for (int i = 0; i < BOARD_SIZE; i++) {
            // If we find a number, then no one has won yet
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) return 0;
        }
        // If we make it through the previous loop, all places are taken, so it's a tie
        return 1;
    }
}
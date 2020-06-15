package game;

import javax.swing.*;

public class Game {
    private GameBoard board;                                // Ссылка на игроыое полк
    private GamePlayer[] gamePlayers = new GamePlayer[2];   // Массив игроков
    private int playerTurn = 0;                             // Индекс текущего игрока

    public Game(){
            this.board = new GameBoard(this);
    }

    public void initGame(){
        gamePlayers[0] = new GamePlayer(true, 'X');
        gamePlayers[1] = new GamePlayer(false, 'O');
    }

    void passTurn(){
        if (playerTurn == 0)
            playerTurn = 1;
        else
            playerTurn = 0;
    }

    GamePlayer getCurrentPlayer(){ return gamePlayers[playerTurn];}

    void showMessage(String messageText){
        JOptionPane.showMessageDialog(board, messageText);
    }
}

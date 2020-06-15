package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameActionListener implements ActionListener{
    private int row;
    private int cell;
    private GameButton button;
    private boolean gameEnd = false;


    public GameActionListener(int row, int cell, GameButton gButton){
        this.row = row;
        this.cell = cell;
        this.button = gButton;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameBoard board = button.getBoard();
        gameEnd = false;                    // Обнуление окончания игры
        if (board.isTurnable(row, cell)){
            updateByPlayersData(board);
            if (gameEnd)return;             // Проверка на окончание игры
            if (board.isFull()){
                board.getGame().showMessage("Ничья!");
                board.emptyField();
                board.getGame().passTurn();
            }
            else {
                updateByAiData(board);
            }
        }
        else {
            board.getGame().showMessage("Некорректный ход");
        }


    }

    private void updateByAiData(GameBoard board) {
        int x = 0, y = 0;
        int badPoint = 0, goodPoint = 0, win_x = 0, win_y = 0, maxPoint = 0, emptyPoint = 0;
        boolean winComb = false;
        for (int i = 0; i < GameBoard.dimension; i++) {        //Проверка по строкам
            for (int j = 0; j < GameBoard.dimension; j++) {
                if (board.isHuman(i,j)) badPoint++;
                if (board.isComp(i,j)) goodPoint++;
                else if (board.isTurnable(i,j)){
                    win_x = i;
                    win_y = j;
                    emptyPoint++;
                }
            }
            if (badPoint >= maxPoint && emptyPoint > 0){
                maxPoint = badPoint;
                x = win_x;
                y = win_y;
            }
            if (goodPoint >= (GameBoard.dimension - 1) && emptyPoint > 0){  // Если есть выигрышная комбинация
                winComb = true;
                x = win_x;
                y = win_y;
                break;
            }
            badPoint = 0;
            goodPoint = 0;
            emptyPoint = 0;
        }
        if (!winComb){                                              // Если нет выигрышной комбинации
            for (int i = 0; i < GameBoard.dimension; i++) {        //Проверка по столбцам
                for (int j = 0; j < GameBoard.dimension; j++) {
                    if (board.isHuman(j,i)) badPoint++;
                    if (board.isComp(j,i)) goodPoint++;
                    else if (board.isTurnable(j,i)){
                        win_x = j;
                        win_y = i;
                        emptyPoint++;
                    }
                }
                if (badPoint >= maxPoint && emptyPoint > 0){
                    maxPoint = badPoint;
                    x = win_x;
                    y = win_y;
                }
                if (goodPoint >= (GameBoard.dimension - 1) && emptyPoint > 0){
                    winComb = true;
                    x = win_x;
                    y = win_y;
                    break;
                }
                goodPoint = 0;
                badPoint = 0;
                emptyPoint = 0;
            }
        }
        if (!winComb){
            for (int i = 0; i < GameBoard.dimension; i++) {        //Проверка по главной диагонали
                if (board.isHuman(i,i)) badPoint++;
                if (board.isComp(i,i)) goodPoint++;
                else if (board.isTurnable(i,i)){
                    win_x = i;
                    win_y = i;
                    emptyPoint++;
                }
            }
            if (badPoint >= maxPoint && emptyPoint > 0){
                maxPoint = badPoint;
                x = win_x;
                y = win_y;
            }
            if (goodPoint >= (GameBoard.dimension - 1) && emptyPoint > 0){
                winComb = true;
                x = win_x;
                y = win_y;
            }
            goodPoint = 0;
            badPoint = 0;
            emptyPoint = 0;
        }
        if (!winComb){
            for (int i = 0, j = (GameBoard.dimension - 1); i < GameBoard.dimension; i++, j--) {       //Проверка по побочной диагонали
                if (board.isHuman(i,j)) badPoint++;
                if (board.isComp(i,j)) goodPoint++;
                else if (board.isTurnable(i,j)){
                    win_x = i;
                    win_y = j;
                    emptyPoint++;
                }
            }
            if (badPoint >= maxPoint && emptyPoint > 0){
                maxPoint = badPoint;
                x = win_x;
                y = win_y;
            }
            if (goodPoint >= (GameBoard.dimension - 1) && emptyPoint > 0){
                winComb = true;
                x = win_x;
                y = win_y;
            }
        }

        if (board.isTurnable(GameBoard.dimension / 2,GameBoard.dimension / 2) && !winComb){      //Проверка центра поля
            x = y = GameBoard.dimension/2;
        }

        // Обновить матрицу
        board.updateGameField(x, y);

        int cellIndex = GameBoard.dimension * x + y;
        board.getButton(cellIndex).setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSing()));

        if (board.checkWin()){
            button.getBoard().getGame().showMessage("Компьютер выиграл");
            gameEnd = true;
            board.emptyField();
            board.getGame().passTurn();
        }
        else {
            board.getGame().passTurn();
        }
    }

    private void updateByPlayersData(GameBoard board) {
        board.updateGameField(row, cell);
        button.setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSing()));

        if (board.checkWin()){
            button.getBoard().getGame().showMessage("Вы выиграли!");
            gameEnd = true;
            board.emptyField();
        }
        else {
            board.getGame().passTurn();
        }
    }
}

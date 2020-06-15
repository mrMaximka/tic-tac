package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard extends JFrame {
    static int dimension = 3;           // Размерность
    static int cellSize = 150;          // Размер одной клетки
    private char[][] gameField;         // Матрица игры
    private GameButton[] gameButtons;   // Массив кнопок

    private Game game;                  // Ссылка на игру

    static char nullSymbol = '\u0000';

    public GameBoard(Game currentGame){
        this.game = currentGame;
        iniField();
    }

    private void iniField() {
        setBounds(cellSize * dimension, cellSize * dimension, 400, 300);
        setTitle("Кркстики-нолики");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        JButton newGameButton = new JButton("Новая игра");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emptyField();
            }
        });

        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(newGameButton);
        controlPanel.setSize(cellSize * dimension, 150);

        JPanel gameFielPanel = new JPanel();
        gameFielPanel.setLayout(new GridLayout(dimension, dimension));
        gameFielPanel.setSize(cellSize * dimension, cellSize * dimension);

        gameField = new char[dimension][dimension];
        gameButtons = new GameButton[dimension * dimension];

        for (int i = 0; i < (dimension * dimension); i++) {
            GameButton fieldButton = new GameButton(i, this);
            gameFielPanel.add(fieldButton);
            gameButtons[i] = fieldButton;
        }

        getContentPane().add(controlPanel, BorderLayout.NORTH);
        getContentPane().add(gameFielPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    void emptyField(){
        for (int i = 0; i < (dimension * dimension); i++) {
            gameButtons[i].setText("");

            int x = i / GameBoard.dimension;
            int y = i % GameBoard.dimension;

            gameField[x][y] = nullSymbol;
        }
    }

    Game getGame(){
        return game;
    }

    boolean isTurnable(int x, int y){
        if (gameField[y][x] == nullSymbol)
            return true;
        return false;
    }

    boolean isHuman(int x, int y){
        if (gameField[y][x] == 'X')
            return true;
        return false;
    }

    boolean isComp(int x, int y){
        if (gameField[y][x] == 'O')
            return true;
        return false;
    }

    void updateGameField(int x, int y){
        gameField[y][x] = game.getCurrentPlayer().getPlayerSing();
    }

    boolean isFull(){
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (gameField[i][j] == nullSymbol)
                    return false;
            }
        }
        return true;
    }
    
    boolean checkWin() {
        char playerSymbol = getGame().getCurrentPlayer().getPlayerSing();

        if (checkWinDiagonals(playerSymbol) || checkWinLines(playerSymbol)) {
            return true;
        }
        return false;
    }

    private boolean checkWinDiagonals(char playerSymbol) {
        boolean leftRight, rightLeft;

        leftRight = true;
        rightLeft = true;

        for (int i = 0; i < dimension; i++) {
            leftRight &= (gameField[i][i] == playerSymbol);
            rightLeft &= (gameField[dimension - i - 1][i] == playerSymbol);
        }

        if (leftRight || rightLeft)
            return true;

        return false;
    }

    private boolean checkWinLines(char playerSymbol) {
        boolean cols, rows;

        for (int col = 0; col < dimension ; col++) {
            cols = true;
            rows = true;

            for (int row = 0; row < dimension; row++) {
                cols &= (gameField[col][row] == playerSymbol);
                rows &= (gameField[row][col] == playerSymbol);
            }

            if (cols || rows){
                return true;
            }
        }
        return  false;
    }

    public GameButton getButton(int buttonIndex){
        return gameButtons[buttonIndex];
    }
}


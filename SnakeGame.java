package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class SnakeGame extends Game {

    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;

    private Snake snake;
    private int turnDelay; //переменная для установки продолжительности хода
    private Apple apple;// В классе SnakeGame должно существовать приватное поле Apple apple
    private boolean isGameStopped;//Для хранения состояния игры нам понадобится переменная. В классе SnakeGame должно существовать приватное поле boolean isGameStopped.

    public void initialize() {

        setScreenSize(WIDTH, HEIGHT);
        createGame();
/*
        showGrid(true);

        int with = getScreenWidth();
        int height = getScreenHeight();

        for (int x=0; x<with; x++) {
            for(int y=0; y<height; y++){
                setCellColor(y, x, Color.DARKSEAGREEN);
            }

        } */


    }


    private void createGame() {

        isGameStopped = false;//Когда игра проиграна, её нужно остановить и вывести сообщение об этом игроку. т.е. isGameStopped = true; а пока она false
        snake = new Snake(WIDTH / 2, HEIGHT / 2); // создаем новую змею в центре игрового поля
        turnDelay = 300; // установили скорость 300 мс/ход
//apple = new Apple (5,5);//apple необходимо инициализировать новым объектом типа Apple с параметрами 5, 5 перед вызовом метода drawScene()
        createNewApple();//должен вызываться метод createNewApple() перед методом drawScene()
        setTurnTimer(turnDelay);
        drawScene();

    }

    private void drawScene() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(y, x, Color.DARKSEAGREEN, "");// пустая строка нужна для того, чтобы удалять последний элемент змейки
            }
        }
        snake.draw(this);
        apple.draw(this);//В методе drawScene() необходимо вызвать у apple метод draw(Game) после отрисовки змеи. В качестве параметра передай в метод this.


    }

    @Override
    public void onTurn(int step) {
        snake.move(apple);//Теперь в качестве аргумента он должен принимать яблоко, и если окажется, что змейка "съела" яблоко,
        // состояние яблока должно устанавливаться в "неживое", а размер змейки — увеличиваться на 1 элемент.
        if (apple.isAlive == false) //В методе onTurn() перед вызовом метода drawScene(), если apple.isAlive == false,
            // необходимо вызвать метод createNewApple().
            createNewApple();
        if (snake.isAlive == false) //Если змея мертвая то вызывается метод GameOver и игра останавливается
            gameOver();
        drawScene();
    }

//Добавим возможность управления нашей змейкой. Для считывания клавиш переопредели метод onKeyPress(Key) родительского класса Game

    @Override
    public void onKeyPress(Key key) {
        if (key == Key.LEFT) {
            snake.setDirection(Direction.LEFT);//Если в метод onKeyPress(Key) пришел параметр Key.LEFT, необходимо вызвать у змейки метод setDirection(Direction) c параметром Direction.LEFT.
        } else if (key == Key.RIGHT) {
            snake.setDirection(Direction.RIGHT);
        } else if (key == Key.DOWN) {
            snake.setDirection(Direction.DOWN);
        } else if (key == Key.UP) {
            snake.setDirection(Direction.UP);
        }
    }
//До этого мы устанавливали координаты яблока вручную. Необходимо создать метод для генерации новых яблок.

    private void createNewApple() {

        int x = getRandomNumber(WIDTH); // случайная координата в пределах игрового поля
        int y = getRandomNumber(HEIGHT);

        apple = new Apple(x, y);//должен создаваться новый объект типа Apple. В качестве параметров передай результаты двух вызовов метода getRandomNumber(int).


    }

// Когда игра проиграна, её нужно остановить и вывести сообщение об этом игроку.
    private void gameOver() { // команды, которые выполняются при остановке игры (проигрыше)

        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "GAME OVER", Color.BROWN, 75);
    }

}
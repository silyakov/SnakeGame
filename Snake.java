package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;


public class Snake extends GameObject {

    GameObject one;
    GameObject two;
    GameObject three;
    public boolean isAlive; //переменная хранит состояние змейки живая или врезалась в край

    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";

    private List<GameObject> snakeParts = new ArrayList<GameObject>();
    private Direction direction = Direction.LEFT;

    private final String LOG_TAG = "Snake ";

    public Snake (int x1, int y1) {

        super(x1, y1);

        one = new GameObject(x, y);
        two = new GameObject(x+1, y);
        three = new GameObject(x+2,y);

        snakeParts.add(one);
        snakeParts.add(two);
        snakeParts.add(three);

        isAlive = true;



    }
// метод для отрисовки змеи
    public void draw(Game game) {

        if (isAlive)
            game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, Color.GREEN,75);
        else
            game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, Color.RED, 75);

        for (int i=1; i < snakeParts.size(); i++) { // отрисовываем змейку для каждого объекта GameObject
            if (isAlive) // если змейка живая, то ее рисуем черным цветом,
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.GREEN, 75); // для тела устанавливаем символ тела
            else // если змейка неживая, то рисуем красным
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.RED, 75); // для тела устанавливаем символ тела
        }

    }
// устанавливаем направление змейки

    public void setDirection(Direction direction) {

        if( direction == direction.RIGHT && this.direction == direction.LEFT)
            return;
        else if (direction == direction.UP && this.direction == direction.DOWN)
            return;
        else if (direction == direction.LEFT && this.direction == direction.RIGHT)
            return;
        else if (direction == direction.DOWN && this.direction == direction.UP)
            return;
        else
            this.direction = direction;
    }

// метод для перемещения змейки
    public void move(Apple apple) {
        GameObject newHead;// = createNewHead();//В методе move() класса Snake должен вызываться метод createNewHead()
//В методе move(), если метод createNewHead() вернул элемент, у которого координаты находятся вне игрового поля, необходимо установить полю isAlive значение false и больше ничего не делать
        newHead = createNewHead();

        if (newHead.x < 0 || newHead.y < 0 || newHead.x >= SnakeGame.WIDTH || newHead.y >= SnakeGame.HEIGHT) {
            isAlive = false;
//            return; // возвращаемя из метода, прерываем выполнение кода
        }
        else if (newHead.x == apple.x && newHead.y == apple.y)
            {
                apple.isAlive = false;// В методе move(Apple), если координаты новой головы змеи совпадают с координатами яблока,
                // необходимо установить яблоку isAlive=false и не удалять хвост змеи.
                snakeParts.add(0, newHead);//В методе move() результат вызова метода createNewHead() необходимо добавить в snakeParts на позицию
                // с индексом 0

            } else {
            snakeParts.add(0, newHead);
            removeTail();//В методе move() класса Snake после создания новой головы змеи должен вызываться метод removeTail()
        }


    }
// метод для прорисовки перемещения, где рисуется новая голова на одну клетку по направлению движения

    public GameObject createNewHead() {

        int headX =snakeParts.get(0).x; //координата головы змеи X
        int headY =snakeParts.get(0).y; //координата головы змеи Y

        if (direction == Direction.LEFT) {
            GameObject newHead = new GameObject(headX - 1, headY); //змейка движется влево
            return newHead;
        }
        else if (direction == Direction.RIGHT) {
            GameObject newHead = new GameObject(headX + 1, headY); //змейка движется вправо
            return newHead;
        }
        else if (direction == Direction.DOWN){
            GameObject newHead = new GameObject(headX, headY+1); //змейка движется вниз
            return newHead;
        }
        else if (direction == Direction.UP){
            GameObject newHead = new GameObject(headX, headY-1); //змейка движется вверх
            return newHead;
        }
        else return null;
    }
// в этом методе удаляется хвост на одну клетку

    public void removeTail() {

        int lastSnakeElement = snakeParts.size();
        System.out.println("Size before element removed" + lastSnakeElement);
        snakeParts.remove(lastSnakeElement-1);
        System.out.println("Size after element removed" + snakeParts.size());

    }

}

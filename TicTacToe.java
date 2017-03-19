import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



/**
 * Created by ������������� on 23.04.2016.
 */
public class TicTacToe extends Applet implements ActionListener {
    //создаем двумерный массив
    Button squares[][];
    Button newGameButton;
    Label score, winButton, loserButton;
    int emptySquaresLeft=9;
    int win, loser;


    //метод init() - это конструктор апплета
    public void init(){

     //Устанавливаем менеджер расположения апплета, шрифт и цвет
        this.setLayout(new BorderLayout());
        this.setBackground(Color.GREEN);

        // Изменяем шрифт апплета так, чтобы он был жирным
        // и имел размер 20
        Font appletFont = new Font("Monospased",Font.BOLD,20);
        this.setFont(appletFont);

        // Создаем кнопку “New Game” и регистрируем в ней
        // слушатель действия
        newGameButton = new Button("New Game");
        newGameButton.addActionListener(this);



         class Fon extends JPanel {
            Image min;

             Fon() {
                min = new ImageIcon("C:\\Documents and Settings\\Chessmaster\\Рабочий стол\\оля\\13.jpg").getImage();
            }

            public void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(min, 0, 0, null);
            }
        }



        //создаем панель
        Panel topPanel = new Panel();
        topPanel.add(newGameButton);

        this.add(topPanel,"North");

        //Создаем панель игрового поля
        Panel centerPanel = new Panel();
        centerPanel.setLayout(new GridLayout(3,3));
        this.add(centerPanel, "Center");
        score = new Label("Your turn!");
        this.add(score,"South");


        //панель подсчета выигрышей и проигрышей
        Panel winLos = new Panel();
        winLos.setLayout(new GridLayout(3,1));
        Font labelFont=new Font ("Monospaces", Font.BOLD, 17);
        winLos.setFont(labelFont);
        this.add(winLos, "North");
        winButton = new Label("Victory!! = "+win);
        winLos.add(winButton, "East");
        loserButton = new Label("You lost! = "+loser);
        winLos.add(loserButton,"North");
        winLos.add(newGameButton,"Center");


        //Создаем массив (двумерный) для хранения ссылок на 9 кнопок
        squares = new Button[3][3];

        // Создаем кнопки, сохраняем ссылки на них в массиве регистрируем в них слушатель,
        // красим их в оранжевый цвет и добавляем на панель
        for (int i=0;i<3;i++) {
            for (int j=0;j<3;j++) {
                squares[i][j] = new Button();
                squares[i][j].addActionListener(this);
                squares[i][j].setBackground(Color.ORANGE);
                centerPanel.add(squares[i][j]);

            }
        }
    }
    /**
     * Этот метод будет обрабатывать все события @paramActionEvent объект
     */

    public void actionPerformed(ActionEvent e) {
        Button theButton = (Button) e.getSource();
        //Это кнопка New Game?
        if(theButton == newGameButton){
            for (int i=0;i<3;i++) {
                for (int j=0;j<3;j++) {
                    squares[i][j].setEnabled(true);
                    squares[i][j].setLabel("");
                    squares[i][j].setBackground(Color.ORANGE);
                }
            }

            emptySquaresLeft=9;
            score.setText("Your turn!");
            newGameButton.setEnabled(false);
            return; //выходим из метода
        }
        String winner = "";
        //Это одна из клеток?
        for (int i=0;i<3;i++) {
            for (int j=0; j<3; j++) {
                if (theButton == squares[i][j]) {
                    squares[i][j].setLabel("X");
                    theButton.setEnabled(false);
                    winner = lookForWinner();
                    if (!"".equals(winner)) {
                        endTheGame();
                    } else {
                        computerMove();
                        winner = lookForWinner();
                        if (!"".equals(winner)) {
                            endTheGame();
                        }
                    }
                    break;
                }
            }
        } //конец цикла for

        if(winner.equals("X")){
            score.setText("The victory is yours!");
            while (true){
                win++;break; }
            winButton.setText("You Winner!!! = " + win);
        } else if(winner.equals("0")){
            score.setText("You lost! ");
           while (true){
               loser++;break;
           }
            loserButton.setText("You lost! = " + loser);

        } else if(winner.equals("T")){
            score.setText("It's a TIE!");
        }
    } //конец метода actionPerformed


    /**
     * Этот метод вызывается после каждого хода, чтобы узнать,
     * есть ли победитель. Он проверяет каждый ряд, колонку
     * и диагональ, чтобы найти три клетки с одинаковыми надписями
     * (не пустыми)
     * @return "X", "O", "T" – ничья, "" - еще нет победителя
     */
    String lookForWinner() {
        String theWinner = "";
        emptySquaresLeft--;

        //Проверяем ряд 1-элементы массива 0,1,2
        if (!squares[0][0].getLabel().equals("")
                &&
                squares[0][0].getLabel().equals(squares[0][1].getLabel())
                &&
                squares[0][0].getLabel().equals(squares[0][2].getLabel())) {
            theWinner = squares[0][0].getLabel();

            //так как массив двумерный, поэтому заносим индексы
            //из 6 цифр. Метод выделения выигрышных клеток
            highLightWinner(0, 0, 0, 1, 0, 2);

            //проверяем ряд 2-элементы массива 3,4,5

        } else if (!squares[1][0].getLabel().equals("")
                &&
                squares[1][0].getLabel().equals(squares[1][1].getLabel())
                &&
                squares[1][0].getLabel().equals(squares[1][2].getLabel())) {
            theWinner = squares[1][0].getLabel();
            highLightWinner(1, 0, 1, 1, 1, 2);

            //проверяем ряд 3- элементы массива 6,7,8
        } else if (!squares[2][0].getLabel().equals("")
                &&
                squares[2][0].getLabel().equals(squares[2][1].getLabel())
                &&
                squares[2][0].getLabel().equals(squares[2][2].getLabel())) {
            theWinner = squares[2][0].getLabel();
            highLightWinner(2, 0, 2, 1, 2, 2);

            //проверяем колонку 1 - элементы массива 0,3,6
        } else if (!squares[0][0].getLabel().equals("")
                &&
                squares[0][0].getLabel().equals(squares[1][0].getLabel())
                &&
                squares[0][0].getLabel().equals(squares[2][0].getLabel())) {
            theWinner = squares[0][0].getLabel();
            highLightWinner(0, 0, 1, 0, 2, 0);

            //проверяем колонку 2 - элементы массива 1,4,7
        } else if (!squares[0][1].getLabel().equals("")
                &&
                squares[0][1].getLabel().equals(squares[1][1].getLabel())
                &&
                squares[0][1].getLabel().equals(squares[2][1].getLabel())) {
            theWinner = squares[0][1].getLabel();
            highLightWinner(0, 1, 1, 1, 2, 1);

            //проверяем колонку 3 - элементы массива 2,5,8
        } else if (!squares[0][2].getLabel().equals("")
                &&
                squares[0][2].getLabel().equals(squares[1][2].getLabel())
                &&
                squares[0][2].getLabel().equals(squares[2][2].getLabel())) {
            theWinner = squares[0][2].getLabel();
            highLightWinner(0, 2, 1, 2, 2, 2);

            //проверяемм первую диагональ- элементы массива 0,4,8
        } else if (!squares[0][0].getLabel().equals("")
                &&
                squares[0][0].getLabel().equals(squares[1][1].getLabel())
                &&
                squares[0][0].getLabel().equals(squares[2][2].getLabel())) {
            theWinner = squares[0][0].getLabel();
            highLightWinner(0, 0, 1, 1, 2, 2);

            //проверяем вторую диагональ- элементы массива 2,4,6
        } else if (!squares[0][2].getLabel().equals("")
                &&
                squares[0][2].getLabel().equals(squares[1][1].getLabel())
                &&
                squares[0][2].getLabel().equals(squares[2][0].getLabel())) {
            theWinner = squares[0][2].getLabel();
            highLightWinner(0, 2, 1, 1, 2, 0);
        }

        if ((emptySquaresLeft == 0) && (theWinner.equals("X"))) {
            return theWinner; // победа на последнем ходу, и последний Х - присваивает победу
        }

        if (emptySquaresLeft == 0) {
            return "T"; // это ничья. T от английского слова tie
        }
        return theWinner;
    }

    /**
     * этот метод применяет набор правил,чтобы найти лучший компьютерный ход
     * Если хороший ход не найден, не найден, выбирается случайная клетка
     */

    void computerMove(){
        int selectedSquare;
        // Сначала компьютер пытается найти пустую клетку рядом с двумя клетками с ноликами, чтобы выиграть
        selectedSquare = findEmptySquare("0");

        // Если он не может найти два нолика, то хотя бы попытается не дать оппоненту сделать ряд из 3-х
        // крестиков,поместив нолик рядом с двумя крестиками
        if (selectedSquare == -1){
            selectedSquare = findEmptySquare("X");
        }

        // если selectedSquare все еще равен -1, то попытается занять центральную клетку
        if ((selectedSquare == -1)&&(squares[1][1].getLabel().equals(""))){
            selectedSquare=1;
            printMove(selectedSquare, selectedSquare);
        }
        // не повезло с центральной клеткой... просто занимаем случайную клетку
        if (selectedSquare==-1){
            selectedSquare=getRandomSquare();
        }

    }

    //печать ноликов компьютером, так как массив
    //кнопок имеет разные типы
    //нужен отдельный метод для вывода на экран ноликов
    int printMove(int x, int y){
        squares[x][y].setLabel("0");
        squares[x][y].setEnabled(false);
        return 0;
    }

    /**
     * Этот метод проверяет каждый ряд, колонку и диагональ чтобы узнать, есть ли в ней две клетки
     * с одинаковыми надписями и пустой клеткой. @param передается X – для пользователя и O – для компа
     * @return количество свободных клеток, или -1, если не найдено две клетки с одинаковыми надписями
     */

    int findEmptySquare(String player){
        int weight[][] = new int[3][3];
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                if (squares[i][j].getLabel().equals("0"))
                    weight[i][j] = -1;
                else if (squares[i][j].getLabel().equals("X"))
                    weight[i][j] = 1;
                else
                    weight[i][j] = 0;
            }
        }
        int twoWeights = player.equals("")? -2:2;
        // Проверим, есть ли в ряду 1 две одинаковые клетки и одна пустая.

        if (weight[0][0]+weight[0][1]+weight[0][2]==twoWeights){
            if (weight[0][0]==0)
            //присваиваем методу индексы клетки
            return printMove (0,0);
            else if (weight[0][1]==0)
                return printMove(0,1);
            else
                return printMove(0,2);
        }

        // Проверим, есть ли в ряду 2 две одинаковые клетки и одна пустая.

        if(weight[1][0]+weight[1][1]+weight[1][2]==twoWeights){
            if(weight[1][0]==0)
                return printMove(1,0);
            else if(weight[1][1]==0)
                return printMove(1,1);
            else
                return printMove(1,2) ;
        }

        // Проверим, есть ли в ряду 3 две одинаковые клетки и одна пустая.
        if (weight[2][0]+weight[2][1]+weight[2][2]==twoWeights){
            if(weight[2][0]==0)
                return printMove(2,0);
            else if(weight[2][1]==0)
                return printMove(2,1);
            else
                return printMove(2,2);
        }

        //Проверим, есть ли в колонке 1 две одинаковые клетки и одна пустая.
        if(weight[0][0]+weight[1][0]+weight[2][0]==twoWeights){
            if(weight[0][0]==0)
                return printMove(0,0);
            else if(weight[1][0]==0)
                return printMove(1,0);
            else
                return printMove(2,0);
        }

        //Проверим, есть ли в колонке 2 две одинаковые клетки и одна пустая.
        if(weight[0][1]+weight[1][1]+weight[2][1]==twoWeights){
            if(weight[0][1]==0)
                return printMove(0,1);
            else if(weight[1][1]==0)
                return printMove(1,1);
            else
                return printMove(2,1);
        }

        //Проверим, есть ли в колонке 3 две одинаковые клетки и одна пустая.
        if (weight[0][2]+weight[1][2]+weight[2][2]==twoWeights){
            if(weight[0][2]==0)
                return printMove(0,2);
            else if(weight[1][2]==0)
                return printMove(1,2);
            else
                return printMove(2,2);
        }

        // Проверим, есть ли в диагонали 1 две одинаковые клетки и одна пустая.
        if(weight[0][0]+weight[1][1]+weight[2][2]==twoWeights){
            if(weight[0][0]==0)
                return printMove(0,0);
            else if(weight[1][1]==0)
                return printMove(1,1);
            else
                return printMove(2,2);
        }

        // Проверим, есть ли в диагонали 2 две одинаковые клетки и одна пустая.
        if(weight[0][2]+weight[1][1]+weight[2][0]==twoWeights){
            if(weight[0][2]==0)
                return printMove(0,2);
            else if(weight[1][1]==0)
                return printMove(1,1);
            else
                return printMove(2,0);
        }

        // Не найдено двух одинаковых соседних клеток
        return -1;
    } //конец метода findEmptySquare()

    /**
     * Этот метод выбирает любую пустую клетку
     * @return случайно выбранный номер клетки
     */

    int getRandomSquare(){
        boolean gotEmptySquare = false;

        int selectedSquare = -1;
        int selectedSquare1 = 0;

        do {
            //так как у двумерного массива два индекса поэтому создаем два рандомных числа
            //с помощью которых будем искать пустую клетку

            selectedSquare = (int) (Math.random()*3);
            selectedSquare1 = (int) (Math.random()*3);
            if(squares[selectedSquare][selectedSquare1].getLabel().equals("")){
                gotEmptySquare = true;
                // чтобы закончить цикл
            }
        } while (!gotEmptySquare);
        //печатаем нолик
        return printMove(selectedSquare,selectedSquare1);
    } // конец метода getRandomSquare()

    /**
     * Этот метод выделяет выигравшую линию.
     * @paramпервая, вторая и третья клетки для выделения
     */
    void highLightWinner(int win1,int win2,int win3,int win4,int win5,int win6){
        squares[win1][win2].setBackground(Color.RED);
        squares[win3][win4].setBackground(Color.RED);
        squares[win5][win6].setBackground(Color.RED);
    }

    // Делаем недоступными клетки и доступной кнопку ”New Game”
    void endTheGame(){
        newGameButton.setEnabled(true);
        for (int i=0;i<3;i++) {
            for (int j=0; j<3; j++) {
                squares[i][j].setEnabled(false);
            }
        }
    }

} // конец класса

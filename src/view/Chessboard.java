package view;


import chessComponent.*;
import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * 这个类表示棋盘组建，其包含：
 * SquareComponent[][]: 4*8个方块格子组件
 */
public class Chessboard extends JComponent {


    private static final int ROW_SIZE = 8;
    private static final int COL_SIZE = 4;

    private final SquareComponent[][] squareComponents = new SquareComponent[ROW_SIZE][COL_SIZE];
    //todo: you can change the initial player
    private ChessColor currentColor = ChessColor.BLACK;

    //all chessComponents in this chessboard are shared only one model controller
    public final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;


    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width + 2, height);
        CHESS_SIZE = (height - 6) / 8;
        SquareComponent.setSpacingLength(CHESS_SIZE / 12);
        System.out.printf("chessboard [%d * %d], chess size = %d\n", width, height, CHESS_SIZE);

        initAllChessOnBoard();
    }

    public SquareComponent[][] getChessComponents() {
        return squareComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(ChessColor currentColor) {
        this.currentColor = currentColor;
    }

    /**
     * 将SquareComponent 放置在 ChessBoard上。里面包含移除原有的component及放置新的component
     * @param squareComponent
     */
    public void putChessOnBoard(SquareComponent squareComponent) {
        int row = squareComponent.getChessboardPoint().getX(), col = squareComponent.getChessboardPoint().getY();
        if (squareComponents[row][col] != null) {
            remove(squareComponents[row][col]);
        }
        add(squareComponents[row][col] = squareComponent);
    }

    /**
     * 交换chess1 chess2的位置
     * @param chess1
     * @param chess2
     */
    public void swapChessComponents(SquareComponent chess1, SquareComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        squareComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        squareComponents[row2][col2] = chess2;

        //只重新绘制chess1 chess2，其他不变
        chess1.repaint();
        chess2.repaint();
    }


    //FIXME:   Initialize chessboard for testing only.
    private void initAllChessOnBoard() {
        Random r1 = new Random();
        Random r2 = new Random();
        for (int i = 0; i < squareComponents.length; i++) {
            for (int j = 0; j < squareComponents[i].length; j++) {
                ChessColor color = r1.nextInt(2) == 0 ? ChessColor.RED : ChessColor.BLACK;

                int count0r=0;int count0b=0;
                int count1r=0;int count1b=0;
                int count2r=0;int count2b=0;
                int count3r=0;int count3b=0;
                int count4r=0;int count4b=0;
                int count5r=0;int count5b=0;
                int count6r=0;int count6b=0;//记录生成的每种棋子个数
                int m =r2.nextInt(7);
                boolean flag=true;
                while(flag){
                    if      (m==0 && count0r<1 && color.name()=="RED"){count0r++;flag=false;}
                    else if (m==1 && count1r<2 && color.name()=="RED"){count1r++;flag=false;}
                    else if (m==2 && count2r<2 && color.name()=="RED"){count2r++;flag=false;}
                    else if (m==3 && count3r<2 && color.name()=="RED"){count3r++;flag=false;}
                    else if (m==4 && count4r<2 && color.name()=="RED"){count4r++;flag=false;}
                    else if (m==5 && count5r<5 && color.name()=="RED"){count5r++;flag=false;}
                    else if (m==6 && count6r<2 && color.name()=="RED"){count6r++;flag=false;}
                    else if (m==0 && count0b<1 && color.name()=="BLACK"){count0b++;flag=false;}
                    else if (m==1 && count1b<2 && color.name()=="BLACK"){count1b++;flag=false;}
                    else if (m==2 && count2b<2 && color.name()=="BLACK"){count2b++;flag=false;}
                    else if (m==3 && count3b<2 && color.name()=="BLACK"){count3b++;flag=false;}
                    else if (m==4 && count4b<2 && color.name()=="BLACK"){count4b++;flag=false;}
                    else if (m==5 && count5b<5 && color.name()=="BLACK"){count5b++;flag=false;}
                    else if (m==6 && count6b<2 && color.name()=="BLACK"){count6b++;flag=false;}
                    else{
                        m =r2.nextInt(7);//重新取一个m
                    }
                }

                SquareComponent squareComponent;
                if (m == 0) {
                    squareComponent = new GeneralChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);}
                else if (m == 1) {
                    squareComponent = new AdvisorChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);}
                else if (m == 2) {
                    squareComponent = new MinisterChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);}
                else if (m == 3) {
                    squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);}
                else if (m == 4) {
                    squareComponent = new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);}
                else if (m == 5) {
                    squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);}
                else {
                    squareComponent = new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                }
                squareComponent.setVisible(true);
                putChessOnBoard(squareComponent);
            }
        }

    }

    /**
     * 绘制棋盘格子
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    /**
     * 将棋盘上行列坐标映射成Swing组件的Point
     * @param row 棋盘上的行
     * @param col 棋盘上的列
     * @return
     */
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE + 3, row * CHESS_SIZE + 3);
    }

    /**
     * 通过GameController调用该方法
     * @param chessData
     */
    public void loadGame(List<String> chessData) {
        chessData.forEach(System.out::println);
    }
}

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.ArrayList;


public class Controller {
    static Piece[][] points;
    static boolean isRedTurn=true;
    static Piece selectedPiece = null;

    @FXML
    private void initialize() {
        checkerBoard.getChildren().clear();
        for (Piece[] pieces : points = new Piece[9][10]) {
            pieces=null;
        }
        isRedTurn=true;
        selectedPiece=null;
        drawLines();
        initPutPieces();
    }

    private void initPutPieces() {//初始化摆放棋子

        final int UNIT = CheckerBoard.UNIT;
        final int RIGHT_X = CheckerBoard.RIGHT_X;
        final int LEFT_X = CheckerBoard.LEFT_X;
        final int TOP_Y = CheckerBoard.TOP_Y;
        final int BOTTOM_Y = CheckerBoard.BOTTOM_Y;
        ArrayList<Piece> allPiece = new ArrayList<>();
        //将
        Jiang redShuai = new Jiang(4,9,true);
        allPiece.add(redShuai);
        points[4][9]=redShuai;
        Jiang blackJiang = new Jiang(4,0,false);
        allPiece.add(blackJiang);
        points[4][0]=blackJiang;
        //马
        Ma redMa1 = new Ma(1,9,true);
        Ma redMa2 = new Ma(7,9,true);
        Ma blackMa1 = new Ma(1,0,false);
        Ma blackMa2 = new Ma(7,0,false);
        allPiece.add(redMa1);
        allPiece.add(redMa2);
        allPiece.add(blackMa1);
        allPiece.add(blackMa2);
        points[1][9] = redMa1;
        points[7][9] = redMa2;
        points[1][0] = blackMa1;
        points[7][0] = blackMa2;
        //象
        Xiang redXiang1 = new Xiang(2,9,true);
        Xiang redXiang2 = new Xiang(6,9,true);
        Xiang blackXiang1 = new Xiang(2,0,false);
        Xiang blackXiang2 = new Xiang(6,0,false);
        allPiece.add(redXiang1);
        allPiece.add(redXiang2);
        allPiece.add(blackXiang1);
        allPiece.add(blackXiang2);
        points[2][9] = redXiang1;
        points[6][9] = redXiang2;
        points[2][0] = blackXiang1;
        points[6][0] = blackXiang2;
        //炮
        Pao redPao1 = new Pao(1,7,true);
        Pao redPao2 = new Pao(7,7,true);
        Pao blackPao1 = new Pao(1,2,false);
        Pao blackPao2 = new Pao(7,2,false);
        allPiece.add(redPao1);
        allPiece.add(redPao2);
        allPiece.add(blackPao1);
        allPiece.add(blackPao2);
        points[1][2]=blackPao1;
        points[7][2]=blackPao2;
        points[1][7]=redPao1;
        points[7][7]=redPao2;
        //兵
        Bing redbing1 = new Bing(0,6,true);
        Bing redbing2 = new Bing(2,6,true);
        Bing redbing3 = new Bing(4,6,true);
        Bing redbing4 = new Bing(6,6,true);
        Bing redbing5 = new Bing(8,6,true);
        Bing blackbing1 = new Bing(0,3,false);
        Bing blackbing2 = new Bing(2,3,false);
        Bing blackbing3 = new Bing(4,3,false);
        Bing blackbing4 = new Bing(6,3,false);
        Bing blackbing5 = new Bing(8,3,false);
        allPiece.add(redbing1);
        allPiece.add(redbing2);
        allPiece.add(redbing3);
        allPiece.add(redbing4);
        allPiece.add(redbing5);
        allPiece.add(blackbing1);
        allPiece.add(blackbing2);
        allPiece.add(blackbing3);
        allPiece.add(blackbing4);
        allPiece.add(blackbing5);
        points[0][6]=redbing1;
        points[2][6]=redbing2;
        points[4][6]=redbing3;
        points[6][6]=redbing4;
        points[8][6]=redbing5;
        points[0][3]=blackbing1;
        points[2][3]=blackbing2;
        points[4][3]=blackbing3;
        points[6][3]=blackbing4;
        points[8][3]=blackbing5;
        //
        checkerBoard.getChildren().addAll(allPiece);

    }

    private void drawLines() {//绘制棋盘线
        final int UNIT = CheckerBoard.UNIT;
        final int RIGHT_X = CheckerBoard.RIGHT_X;
        final int LEFT_X = CheckerBoard.LEFT_X;
        final int TOP_Y = CheckerBoard.TOP_Y;
        final int BOTTOM_Y = CheckerBoard.BOTTOM_Y;
        for (int i = TOP_Y; i <= BOTTOM_Y; i += UNIT) {
            Line newLine = new Line();
            newLine.setStartX(LEFT_X);
            newLine.setStartY(i);
            newLine.setEndX(RIGHT_X);
            newLine.setEndY(i);
            checkerBoard.getChildren().add(newLine);
        }

        for (int i = LEFT_X; i <= RIGHT_X; i += UNIT) {
            Line newtopLine = new Line();
            newtopLine.setStartY(TOP_Y);
            newtopLine.setStartX(i);
            newtopLine.setEndY(TOP_Y + 4 * UNIT);
            newtopLine.setEndX(i);
            Line newbottomLine = new Line();
            newbottomLine.setStartY(TOP_Y + 5 * UNIT);
            newbottomLine.setStartX(i);
            newbottomLine.setEndY(BOTTOM_Y);
            newbottomLine.setEndX(i);
            checkerBoard.getChildren().addAll(newtopLine, newbottomLine);
        }

        Line line = new Line(LEFT_X, TOP_Y + 4 * UNIT, LEFT_X, TOP_Y + 5 * UNIT);
        checkerBoard.getChildren().add(line);
        line = new Line(RIGHT_X, TOP_Y + 4 * UNIT, RIGHT_X, TOP_Y + 5 * UNIT);
        checkerBoard.getChildren().add(line);

        Line crossLine1 = new Line(LEFT_X + 3 * UNIT, TOP_Y, LEFT_X + 5 * UNIT, TOP_Y + 2 * UNIT);
        Line crossLine2 = new Line(LEFT_X + 3 * UNIT, TOP_Y + 2 * UNIT, LEFT_X + 5 * UNIT, TOP_Y);
        checkerBoard.getChildren().addAll(crossLine1, crossLine2);
        crossLine1 = new Line(LEFT_X + 3 * UNIT, BOTTOM_Y, LEFT_X + 5 * UNIT, BOTTOM_Y - 2 * UNIT);
        crossLine2 = new Line(LEFT_X + 3 * UNIT, BOTTOM_Y - 2 * UNIT, LEFT_X + 5 * UNIT, BOTTOM_Y);
        checkerBoard.getChildren().addAll(crossLine1, crossLine2);
    }

    @FXML
    private Pane checkerBoard;    //棋盘画布对象

    @FXML
    private void checkerBoardOnPressed(MouseEvent e){
        if(selectedPiece!=null)
        {
            if(e.getButton().equals(MouseButton.SECONDARY)) { //右键取消选中
                selectedPiece.cancelSelected();
                return;
            }
            int targetX= CheckerBoard.pxToX(e.getX());
            int targetY= CheckerBoard.pxToY(e.getY()); //计算目标像素值
            if(selectedPiece.isRed==isRedTurn) {
                try {
                    selectedPiece.moveTo(targetX, targetY);
                    isRedTurn = !isRedTurn;                   //回合交替
                    selectedPiece.cancelSelected();         //移动后取消选定
                } catch (Piece.CanNotMoveToException ex) {
                    //selectedPiece.cancelSelected();//否则无法选中 执行完moveto后直接抛出异常cancel掉
                }
            }
            else {
                selectedPiece.cancelSelected();
            }
        }
    }

    @FXML
    private void restartButtonOnAction(ActionEvent e)   //重新开始按钮的响应函数
    {
        initialize();
    }

}

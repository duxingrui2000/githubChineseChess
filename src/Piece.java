import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import javax.sound.midi.ControllerEventListener;
import javax.swing.text.Element;
import javax.swing.text.html.ImageView;
import javax.swing.undo.CannotRedoException;
import java.awt.*;

abstract class Piece extends Rectangle
{

    Piece()
    {
    
    }

    Piece(int x, int y, boolean colorIsRed)
    {
        setWidth(4 * CheckerBoard.UNIT / 5);
        setHeight(4 * CheckerBoard.UNIT / 5);
        this.x = x;
        this.y = y;
        isRed = colorIsRed;
        setX(CheckerBoard.xToPx(x)-getWidth()/2);
        setY(CheckerBoard.yToPx(y)-getHeight()/2);
    } //所有棋子构造时都应调用父类的这个构造函数

    protected int x;
    protected int y; //x,y都是棋盘上的坐标
    boolean isRed; //true为红色
    boolean isSelected = false;

    protected void die() {

    }

    protected abstract void moveTo(int dstX, int dstY) throws CanNotMoveToException;

    protected abstract void loadImage();

    protected void beSelected() {
        isSelected = true;
        Controller.selectedPiece = this;
        loadImage();
    }

    protected void cancelSelected() {
        isSelected = false;
        Controller.selectedPiece = null;
        loadImage();
    }

    class CanNotMoveToException extends Exception {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
    }
}

class Jiang extends Piece
{
    Jiang(int x, int y, boolean colorIsRed)
    {
        super(x, y, colorIsRed);    //构造
        loadImage();                //加载图片
        setOnMousePressed(e -> {     //被点击的事件处理
            if (Controller.selectedPiece == null)
            {
                beSelected();
                this.getParent().requestFocus();
            }
        });
    }

    @Override
    protected void die() {
        super.die();
    }

    @Override
    protected void moveTo(int dstX, int dstY) throws CanNotMoveToException
    { //移动到目标点
        if (Math.abs(dstX - x) + Math.abs(dstY - y) != 1)
            throw new CanNotMoveToException();
        if (dstX < 3 || dstX > 5)
            throw new CanNotMoveToException();
        if (isRed && dstY < 7)
            throw new CanNotMoveToException();
        if (!isRed && dstY > 2)
            throw new CanNotMoveToException(); //判断能否移动，不能则抛出异常
    
        
        if (Controller.points[dstX][dstY] != null) {      //目标点是否有棋子
            if(Controller.points[dstX][dstY].isRed==this.isRed)
                throw new CanNotMoveToException();
            Controller.points[x][y].die();
        }//它死了
        Controller.points[x][y]= null;
        Controller.points[dstX][dstY] = this;           //目标点变成当前棋子
        x = dstX;
        y = dstY;
        this.setX(CheckerBoard.xToPx(dstX)-getWidth()/2);
        this.setY(CheckerBoard.yToPx(dstY)-getHeight()/2);
    }

    @Override
    protected void loadImage()
    {
        Image image;
        if (!isSelected) {
            if (isRed)
                image = new Image("RK.GIF");
            else
                image = new Image("BK.GIF");
        } else {
            if (isRed)
                image = new Image("RKS.GIF");
            else
                image = new Image("BKS.GIF");
        }
        setFill(new ImagePattern(image));
    }           //加载正确的图片

}

class Shi extends Piece {

    
    @Override
    protected void moveTo(int dstX, int dstY) {

    }

    @Override
    protected void loadImage() {

    }
}

class Xiang extends Piece {


    @Override
    protected void moveTo(int dstX, int dstY) {

    }

    @Override
    protected void loadImage() {

    }
}

class Ma extends Piece {
    Ma(int x,int y,boolean colorIsRed)
    {
        super(x,y,colorIsRed);
        loadImage();
        setOnMousePressed(e -> {
            if (Controller.selectedPiece == null)
            {
                beSelected();
                this.getParent().requestFocus();
            }
        });
    }
    
    @Override
    protected void die() {
        super.die();
    }
    
    @Override
    protected void moveTo(int dstX, int dstY) throws CanNotMoveToException
    {
        
        boolean ans = false;
        if(dstX < 0 || dstX > 8 ||  dstY<0 ||dstY > 9 )
        {
            throw new CanNotMoveToException();
        }
        else if(Controller.points[dstX][dstY] != null && Controller.points[dstX][dstY].isRed == isRed)
        {
            throw new CanNotMoveToException();
        }
        else if(((Math.abs(dstX - x)==2 && Math.abs(dstY - y)==1) ||
                (Math.abs(dstX - x)==1 && Math.abs(dstY - y)==2 ))) {
            if (Math.abs(dstX - x) == 2 && Math.abs(dstY - y) == 1) {
                if (dstX > x && Controller.points[x + 1][y] == null) {
                    ans = true;
                } else if (dstX < x && Controller.points[x - 1][y] == null) {
                    ans = true;
                }
            } else {
    
                if (dstY > y && Controller.points[x][y + 1] == null) {
                    ans = true;
                } else if (dstY < y && Controller.points[x][y - 1] == null) {
                    ans = true;
                }
            }
        }
        if(ans)
        {
            if(Controller.points[dstX][dstY] != null)
            {
                Controller.points[dstX][dstY].die();
            }
            Controller.points[x][y] = null;
            Controller.points[dstX][dstY] = this;
            x = dstX;
            y = dstY;
            this.setX(CheckerBoard.xToPx(dstX)-getWidth()/2);
            this.setY(CheckerBoard.yToPx(dstY)-getHeight()/2);
        }
        if(!ans)
        {
            throw new CanNotMoveToException();
        }
    }

    @Override
    protected void loadImage()
    {
        Image image;
        if (!isSelected) {
            if (isRed)
                image = new Image("RN.GIF");
            else
                image = new Image("BN.GIF");
        } else {
            if (isRed)
                image = new Image("RNS.GIF");
            else
                image = new Image("BNS.GIF");
        }
        setFill(new ImagePattern(image));
    }
}

class Ju extends Piece {


    @Override
    protected void moveTo(int dstX, int dstY) {

    }

    @Override
    protected void loadImage() {

    }
}

class Pao extends Piece {


    @Override
    protected void moveTo(int dstX, int dstY) {

    }

    @Override
    protected void loadImage() {

    }
}

class Bing extends Piece {

    @Override
    protected void moveTo(int dstX, int dstY) {

    }

    @Override
    protected void loadImage() {

    }
}

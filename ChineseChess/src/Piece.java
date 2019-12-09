import javafx.scene.PointLight;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import javax.naming.CannotProceedException;
import java.awt.image.PixelInterleavedSampleModel;

abstract class Piece extends Rectangle {

    Piece() {
    }

    Piece(int x, int y, boolean colorIsRed) {
        setWidth(4.0 * CheckerBoard.UNIT / 5.0);
        setHeight(4.0 * CheckerBoard.UNIT / 5.0);
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
        Controller.points[x][y]=null;
        setVisible(false);
       setDisable(true);
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

    static class CanNotMoveToException extends Exception {
    }
}

class Jiang extends Piece {


    Jiang(int x, int y, boolean colorIsRed) {
        super(x, y, colorIsRed);    //构造
        loadImage();                //加载图片
        setOnMousePressed(e -> {     //被点击的事件处理
            if (Controller.selectedPiece==null) {
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
    protected void moveTo(int dstX, int dstY) throws CanNotMoveToException {
        //移动到目标点
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
        Controller.points[dstX][dstY] = this;
        x = dstX;
        y = dstY;
        //目标点变成当前棋子
        this.setX(CheckerBoard.xToPx(dstX)-getWidth()/2);
        this.setY(CheckerBoard.yToPx(dstY)-getHeight()/2);
    }

    @Override
    protected void loadImage() {
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
    
    Xiang(int x,int y,boolean colorIsRed)
    {
        super(x,y,colorIsRed);
        loadImage();
        setOnMousePressed(e->{
            if (Controller.selectedPiece==null) {
                beSelected();
                this.getParent().requestFocus();
            }
        });
    }

    @Override
    protected void moveTo(int dstX, int dstY) throws CanNotMoveToException
    {
        int midx = (dstX + x) / 2;
        int midy = (dstY + y) / 2;
        if(dstX < 0 || dstX > 8 ||  dstY<0 ||dstY > 9 )//越界
        {
            throw new CanNotMoveToException();
        }
        else if(Controller.points[dstX][dstY] != null && Controller.points[dstX][dstY].isRed == isRed)//同色
        {
            throw new CanNotMoveToException();
        }//符合要求
        else if((Math.abs(dstX - x) == 2 ) && (Math.abs(dstY - y) == 2) && (Controller.points[midx][midy] == null)
        && ((isRed && dstY >= 5) || (!isRed && dstY <= 4)) )
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
        }//不符合要求
        else
        {
            throw  new CanNotMoveToException();
        }
    }

    @Override
    protected void loadImage()
    {
        Image image;
        if (!isSelected) {
            if (isRed)
                image = new Image("RB.GIF");
            else
                image = new Image("BB.GIF");
        } else {
            if (isRed)
                image = new Image("RBS.GIF");
            else
                image = new Image("BBS.GIF");
        }
        setFill(new ImagePattern(image));
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
    Pao(int x, int y, boolean colorIsRed) {
        super(x, y, colorIsRed);    //构造
        loadImage();                //加载图片
        setOnMousePressed(e -> {     //被点击的事件处理
            if (Controller.selectedPiece==null) {
                beSelected();
                this.getParent().requestFocus();
            }
        });
    }

    @Override
    protected void die() { super.die();}

    @Override
    protected void moveTo(int dstX, int dstY) throws CanNotMoveToException{
        int count=0;//记录两点之间棋子个数
        //下列为6种情况
        if(dstX==this.x && dstY==this.y)
            throw new CanNotMoveToException();
        if(dstX!=this.x && dstY!=this.y)
            throw new CanNotMoveToException();
        if(dstX == this.x && dstY>this.y){
            for(int tem = this.y+1;tem<dstY;tem++) {
                if(Controller.points[x][tem]!=null){
                    count++;
                }
            }
        }
        if(dstX == this.x && dstY<this.y){
            for(int tem = dstY+1;tem<this.y;tem++) {
                if(Controller.points[x][tem]!=null){
                    count++;
                }
            }
        }
        if(dstY == this.y && dstX>this.x){
            for(int tem = this.x+1;tem<dstX;tem++) {
                if(Controller.points[tem][y]!=null){
                    count++;
                }
            }

        }
        if(dstY == this.y && dstX<this.x){
            for(int tem = dstX+1;tem<this.x;tem++) {
                if(Controller.points[tem][y]!=null){
                    count++;
                }
            }
        }
        if(Controller.points[dstX][dstY]==null) {//目的地是否有棋子以及是否为己方棋子
            if (count != 0)
                throw new CanNotMoveToException();
        }
        else {
            if(count!=1 || Controller.points[dstX][dstY].isRed==this.isRed)
                throw  new CanNotMoveToException();
            else
                Controller.points[dstX][dstY].die();
        }
        count = 0;
        Controller.points[x][y] = null;
        Controller.points[dstX][dstY] = this;
        x = dstX;
        y = dstY;
        this.setX(CheckerBoard.xToPx(dstX)-getWidth()/2);
        this.setY(CheckerBoard.yToPx(dstY)-getHeight()/2);
    }
    @Override
    protected void loadImage() {
    Image image;
        if (!isSelected) {
            if (isRed)
                image = new Image("RC.GIF");
            else
                image = new Image("BC.GIF");
        } else {
            if (isRed)
                image = new Image("RCS.GIF");
            else
                image = new Image("BCS.GIF");
        }
        setFill(new ImagePattern(image));
    }           //加载正确的图片
}


class Bing extends Piece {
    Bing(int x, int y, boolean colorIsRed) {
        super(x, y, colorIsRed);    //构造
        loadImage();                //加载图片
        setOnMousePressed(e -> {     //被点击的事件处理
            if (Controller.selectedPiece==null) {
                beSelected();
                this.getParent().requestFocus();
            }
        });
    }

    @Override
    protected void die() { super.die();}

    @Override
    protected void moveTo(int dstX, int dstY) throws CanNotMoveToException {
        if (this.isRed) {//红兵
            if (this.y >= 5) {//没过河
                if (dstX != this.x)
                    throw new CanNotMoveToException();
                else if (dstY != this.y - 1)
                    throw new CanNotMoveToException();
                if (Controller.points[dstX][dstY] != null) {
                    if (Controller.points[dstX][dstY].isRed == this.isRed)
                        throw new CanNotMoveToException();
                    else
                        Controller.points[dstX][dstY].die();
                }
            }
            else {//过了河
                if (Math.abs(x - dstX) + Math.abs(y - dstY) != 1) {
                    throw new CanNotMoveToException();
                } else if (dstY == this.y + 1) {
                    throw new CanNotMoveToException();
                } else {
                    if (Controller.points[dstX][dstY] != null) {
                        if (Controller.points[dstX][dstY].isRed == this.isRed)
                            throw new CanNotMoveToException();
                        else
                            Controller.points[dstX][dstY].die();
                    }
                }
            }
        }
        else {//黑卒
            if(this.y<=4) {//没过河
                if (dstX != this.x)
                    throw new CanNotMoveToException();
                else if (dstY != this.y + 1)
                    throw new CanNotMoveToException();
                if (Controller.points[dstX][dstY] != null) {
                    if (Controller.points[dstX][dstY].isRed == this.isRed)
                        throw new CanNotMoveToException();
                    else
                        Controller.points[dstX][dstY].die();
                }
            }
            else {//过了河
                if (Math.abs(x - dstX) + Math.abs(y - dstY) != 1) {
                    throw new CanNotMoveToException();
                } else if (dstY == this.y - 1) {
                    throw new CanNotMoveToException();
                } else {
                    if (Controller.points[dstX][dstY] != null) {
                        if (Controller.points[dstX][dstY].isRed == this.isRed)
                            throw new CanNotMoveToException();
                        else
                            Controller.points[dstX][dstY].die();
                    }
                }
            }
        }
        Controller.points[x][y] = null;
        Controller.points[dstX][dstY] = this;
        x = dstX;
        y = dstY;
        this.setX(CheckerBoard.xToPx(dstX)-getWidth()/2);
        this.setY(CheckerBoard.yToPx(dstY)-getHeight()/2);
}


    @Override
    protected void loadImage() {
        Image image;
        if (!isSelected) {
            if (isRed)
                image = new Image("RP.GIF");
            else
                image = new Image("BP.GIF");
        } else {
            if (isRed)
                image = new Image("RPS.GIF");
            else
                image = new Image("BPS.GIF");
        }
        setFill(new ImagePattern(image));
    }           //加载正确的图片

}

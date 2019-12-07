import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;


public class Controller
{
    static Piece[][] points;
    static Piece selectedPiece = null;

    @FXML
    private void initialize()
    {
        for (Piece[] pieces : points = new Piece[9][10])
        {
            pieces=null;
        }
        drawLines();
        Ma redMa1 = new Ma(2,9,true);
        checkerBoard.getChildren().add(redMa1);
        points[2][9] = redMa1;
        
        Jiang redShuai=new Jiang(4,9,true);
        checkerBoard.getChildren().add(redShuai);
        points[4][9]=redShuai;
        
        
        //checkerBoard.requestFocus();
        // checkerBoard.getChildren().add(new Circle(50));
    }

    private void initPutPieces() {

        final int UNIT = CheckerBoard.UNIT;
        final int RIGHT_X = CheckerBoard.RIGHT_X;
        final int LEFT_X = CheckerBoard.LEFT_X;
        final int TOP_Y = CheckerBoard.TOP_Y;
        final int BOTTOM_Y = CheckerBoard.BOTTOM_Y;


    }

    private void drawLines() {
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
    private Pane checkerBoard;

    @FXML
    private void checkerBoardOnPressed(MouseEvent e)
    {
        if(selectedPiece!=null)
        {
            if(e.getButton().equals(MouseButton.SECONDARY))
            {
                selectedPiece.cancelSelected();
                /*  isSelected = false;
                    Controller.selectedPiece = null;
                    loadImage();*/
                return;
            }
            int targetX= CheckerBoard.pxToX(e.getX());
            int targetY= CheckerBoard.pxToY(e.getY());
            
            try{
                selectedPiece.moveTo(targetX,targetY);
                selectedPiece.cancelSelected();
            } catch (Piece.CanNotMoveToException ex){
                //selectedPiece.cancelSelected();
            }
        }
    }

}

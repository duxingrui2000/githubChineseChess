
public class CheckerBoard
{

	    //棋盘的一些常量
	    static final int UNIT = 50;
	    static final int LEFT_X = 30;
	    static final int RIGHT_X = LEFT_X + 8 * UNIT;
	    static final int TOP_Y = 30;
	    static final int BOTTOM_Y = TOP_Y + 9 * UNIT;

	    //将棋盘上的坐标转化为像素值
	   //@Contract(pure = true)
	    static double xToPx(int x) {
	        return LEFT_X + x * UNIT;
	    }

	    //@Contract(pure = true)
	    static double yToPx(int y) {
	        return TOP_Y + y * UNIT;
	    }

	    static int pxToX(double x_Px)
		{
	        Double t= (x_Px-LEFT_X)/UNIT;
			long ans= Math.round(t);
	        return (int)ans;
	    }

	    static int pxToY(double y_Px){
	        Double t= (y_Px-TOP_Y)/UNIT;
	        long ans= Math.round(t);
	        return (int)ans;
	    }
}

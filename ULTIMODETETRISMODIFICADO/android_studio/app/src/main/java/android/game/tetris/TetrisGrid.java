package android.game.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.tools.ArrayTools;

public class TetrisGrid implements ITetrisConstants  {
	
	//false when empty-true when occupied
    private boolean[] mCells;
    private int mTileW;
    private int mTileH;
    private int mLeft;
    private int mTop;
    private int mRight;
    private int mBottom;
    
    public TetrisGrid() {
		mCells = new boolean[PLAYFIELD_ROWS*PLAYFIELD_COLS];
		init();
	}

	public void init() {
		for(int i=0;i<mCells.length;i++)
		{
			mCells[i]=false;
		}
	}
	
	public void setBackGroundDimentions(int w, int h)
	{
		if( PLAYFIELD_USE_MARGINS )
		{
			mLeft = MARGIN_LEFT;
			mTop = MARGIN_TOP;
			w -=mLeft+MARGIN_RIGHT;
			h -=mTop+MARGIN_BOTTOM;
		}
		else
		{
			mTop=mLeft=0;
		}
		
		int cols = PLAYFIELD_COLS;
		int rows = PLAYFIELD_ROWS;
		mTileW = w / cols;
		mTileH = h / rows;

		mRight=mLeft+(mTileW*PLAYFIELD_COLS);
		mBottom=mTop+(mTileH*PLAYFIELD_ROWS);
	}

    public int getColumn(int index)
    {
    	if(index<0)
    		return -((Math.abs(index)%PLAYFIELD_COLS) + 1);
    	else
    		return (index%PLAYFIELD_COLS);
    }
    
    public int getRow(int index)
    {
    	if(index < 0)
    		return -((Math.abs(index)/PLAYFIELD_COLS) + 1);
    	else
    		return (index/PLAYFIELD_COLS);
    }
    
    public boolean IsCellValid( int index )
    {
    	return (index>=0 && index<mCells.length);
    }
    
	public boolean IsCellFree(int index)
	{
		if(IsCellValid(index))
			return !mCells[index];
		else
			return false;
	}
	
	private boolean checkForRunOff(int[] from, int[] to) {
	
		int[] testArray = to.clone();
		for (int i = testArray.length-1; i >= 0; i--) {
			testArray[i] -= testArray[0];
			testArray[i] += START_CELL;
		}
		
		//normalize the test array to test rowDiff
		for (int i = 0; i < to.length; i++) {
			if(getRow(to[i]) - getRow(to[0]) != getRow(testArray[i]) - getRow(testArray[0]))
				return false;
		}
		return true;
	}

	public boolean tryToMoveCells(int[] from, int[] to) {
		
		//test grid
		if(!checkForRunOff(from,to))
			return false;
		
		boolean validMove = false;
		for (int i = 0; i < to.length; i++) {
			boolean cellAboveGrid = to[i] < 0; //can happen on init
			if(!ArrayTools.IsInArray(to[i], from))
				if(IsCellFree(to[i]) || cellAboveGrid)
					validMove = true;
				else
					return false;
		}
		
		//write to grid
		if(validMove)
		{
			for (int i = 0; i < from.length; i++) {
				if(IsCellValid(from[i]))
					mCells[from[i]] = false;
			}
			for (int i = 0; i < to.length; i++) {
				boolean cellAboveGrid = to[i] < 0; //can happen on init
				if(!cellAboveGrid)
					mCells[to[i]] = true;
			}
		}
		
		return validMove;
	}
	

	public void paint(Canvas canvas, Paint paint) {
		//paint bg
		paint.setColor(Color.GRAY);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawRect(mLeft, mTop, mRight, mBottom, paint);

		
		//paint elems
		int l,t,r,b;
		for(int i=0;i<mCells.length;i++)
		{
			l = mLeft+(i%PLAYFIELD_COLS)*mTileW;
			t = mTop+(i/PLAYFIELD_COLS)*mTileH;
			r = l+mTileW;
			b = t+mTileH;
			paint.setColor(Color.YELLOW);
			paint.setStyle((mCells[i])?Paint.Style.FILL:Paint.Style.STROKE);
			canvas.drawRect(l, t, r, b, paint);
			//if(mCells[i])//more fancy graphics for occupied cells
			{
				//paint.setColor(Color.BLACK);
				//paint.setStyle(Paint.Style.STROKE);
				//canvas.drawRect(l+2, t+2, r-2, b-2, paint);
			}
		}

		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(mLeft, mTop, mRight, mBottom, paint);
	}

	public int update() {
		int points = 0;
		for (int row =  PLAYFIELD_ROWS-1; row >= 0; row--) {
			if(CheckRowForSame(row, false))
				break;
			if(CheckRowForSame(row, true))
			{
				points++;
				SetAllRowTo(row, false);
				MakeGridCollapse(row-1);
				row++;//we collapsed grid onto this row so we need to check it again by cancelling the upcomming decrement
			}
		}
		return points;
	}

	private void MakeGridCollapse(int row) {
		for (int r =  row; r >= 0; r--) {
			if(CheckRowForSame(r, false))
				break;
			ShiftRowBy(r,C_DOWN);
			SetAllRowTo(r, false);
		}
	}

	private void ShiftRowBy(int row, int down) {
		int index;
		for (int i = 0; i < PLAYFIELD_COLS; i++) {
			index = (row*PLAYFIELD_COLS)+i;
			mCells[index+ down] = mCells[index];
		}
		
	}

	private void SetAllRowTo(int row, boolean b) {
		for (int i = 0; i < PLAYFIELD_COLS; i++) {
			mCells[(row*PLAYFIELD_COLS)+i] = b;
		}
	}

	private boolean CheckRowForSame(int row, boolean b) {
		for (int i = 0; i < PLAYFIELD_COLS; i++) {
			if( mCells[(row*PLAYFIELD_COLS)+i] != b )
				return false;
		}
		return true;
	}

}

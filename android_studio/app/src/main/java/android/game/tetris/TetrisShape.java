package android.game.tetris;

import java.util.Random;


public class TetrisShape implements ITetrisConstants{

	public boolean isGameOver = false;
	public boolean isInited = false;
	
	private int mOrient;
	private int mType;
	private int mNextType;
	public int getNextType(){return mNextType;}
	private int mState;
	private int[] mElems;
	
	private Random mRand;
	private TetrisGrid mGrid;

	
	public TetrisShape(TetrisGrid g) {
		mGrid = g;
		mRand = new Random();
		mElems = new int[MAX_ELEMS];
		mNextType = mRand.nextInt(TYPE_MAX_TYPES);
	}
	
	public boolean spawn() {
		isGameOver = false;
		for (int i = 0; i < mElems.length; i++) {
			mElems[i] = DONT_CHECK_CELL;
		}
		mType = mNextType;
		mNextType = mRand.nextInt(TYPE_MAX_TYPES);
		mElems[ELEM_BASE] = START_CELL;
		mOrient = START_ORIENTATION;
		mState = STATE_USER;
		return alignFromOrientation(mOrient);
	}

	public void update(int currentAction) {
		if( mState == STATE_USER )
		{
			switch(currentAction)
			{
				case ACTION_STRAFE_LEFT:
				{
					shiftCells(C_LEFT);
					break;
				}
				case ACTION_STRAFE_RIGHT:
				{
					shiftCells(C_RIGHT);
					break;
				}
				case ACTION_ROTATE_L:
				{
					rotateShape(ROT_LEFT);
					break;
				}
				case ACTION_ROTATE_R:
				{
					rotateShape(ROT_RIGHT);
					break;
				}
				case ACTION_MAKE_FALL:
				{
					mState = STATE_FALLING;
					break;
				}
			}
		}
	}

	public boolean IsFalling()
	{
		return mState == STATE_FALLING;
	}
	
	//true when need to recheck playfield
	public boolean addGravity() {
		if(isInited)
		{
			//gravity
			boolean falling = shiftCells(C_DOWN);
			if(!falling)
			{
				mState = STATE_LOCKED;
				isInited = false;
				return true;
			}
		}
		else
		{
			isInited = spawn();
			
			if(!isInited)//this means no room to init = game over
			{
				isGameOver = true;
			}
		}
		return false;
	}
	
	private boolean rotateShape(int rotation)
	{
		int orient = mOrient;
		orient += rotation;
		if(orient < OR_NORTH)
			orient = OR_WEST;
		else if (orient>OR_WEST)
			orient = OR_NORTH;
		boolean hasRotated = alignFromOrientation(orient);
		if(hasRotated)
			mOrient = orient;
		
		return hasRotated;
	}
	
	
	private boolean alignFromOrientation(int orientation)
	{
		int[] newElemPos = mElems.clone();
		int typeOffset = (mType*SHAPE_TABLE_TYPE_OFFSET);
		orientation *= SHAPE_TABLE_ELEMS_PER_ROW;
		newElemPos[ELEM_1] = newElemPos[ELEM_BASE]+SHAPE_TABLE[typeOffset+orientation+SHAPE_TABLE_ELEMS_1];
		newElemPos[ELEM_2] = newElemPos[ELEM_BASE]+SHAPE_TABLE[typeOffset+orientation+SHAPE_TABLE_ELEMS_2];
		newElemPos[ELEM_3] = newElemPos[ELEM_BASE]+SHAPE_TABLE[typeOffset+orientation+SHAPE_TABLE_ELEMS_3];

		return tryToMove(newElemPos);
	}
	

	private boolean shiftCells( int cellscrool )
	{
		int[] tmpElems = mElems.clone();
		for(int i=0;i<mElems.length;i++)
		{
			if(mElems[i] != DONT_CHECK_CELL)
				tmpElems[i] += cellscrool;
		}
		
		if(cellscrool == C_LEFT || cellscrool == C_RIGHT)
			if(mGrid.getRow(mElems[ELEM_BASE]) != mGrid.getRow(tmpElems[ELEM_BASE]))
				return false;
		
		return tryToMove(tmpElems);
	}
	
	private boolean tryToMove(int[] newElemPos) {
		boolean hasMoved = mGrid.tryToMoveCells(mElems, newElemPos);
		if(hasMoved)
			mElems = newElemPos;
		return hasMoved;
	}

	

}

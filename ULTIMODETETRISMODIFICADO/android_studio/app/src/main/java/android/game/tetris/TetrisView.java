package android.game.tetris;

import android.app.Activity;
import android.game.score.ScoreManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.tools.AlertManager;
import android.view.KeyEvent;
import android.view.View;

public class TetrisView extends View implements ITetrisConstants {
	
	//members
	private boolean mHasFocus; 	//gameView is the focus and can be updated with setter below
	public  void setGameFocus( boolean hasFocus ) {	mHasFocus = hasFocus;	}
	private long mNextUpdate; 	//time stamp when next update can be called
	private long mLastGravity;	//allow updates of shape independently from gravity by checking this
	private int mTicks;			//number of ticks that have been calculated
	private int mSeconds;		//number of seconds of game play
    private Paint mPaint;		//paint object to use in draws.
    private Activity mActivityHandle; //save reference to activity to be able to quit from here
    private ScoreManager scoreManager;
    
    
    //game specific
    private TetrisGrid grid; 			//game play field/grid
    private TetrisShape currentShape;	//current shape controllable by the user
    private int currentAction;			//current game action fired by player
    
    /**
     * Constructor
     * 
     * Init View object
     * Instantiate objects
     * 
     * @param context - param needed for View superclass
     */
	public TetrisView(Activity context) {
		//init view obj
		super(context);
		mActivityHandle = context;
		setBackgroundColor(Color.BLACK);
		setFocusable(true);
		setFocusableInTouchMode(true);
		requestFocus();
		
		//inst objs
		grid = new TetrisGrid();
		currentShape = new TetrisShape(grid);
		mPaint = new Paint();
		scoreManager = new ScoreManager(context);
		
		//initialize
		init();
	}
	
	/**
     * Initialize members
	 */
	private void init() {

		//initialize members
		currentShape.isInited = false;
		currentAction = ACTION_NONE;
		mNextUpdate = 0;
		mTicks = 0;
		mSeconds = 0;
		mLastGravity = 1;

		grid.init();
		
		scoreManager.currentScore = 1;
		scoreManager.scoreWasSaved = false;

	}

	public void restartGame() {
		init();
		currentShape.isGameOver = false;
	}


	public void quitGame() {
		mActivityHandle.finish();
	}
	
	/**
	 * recalculate grid pixel size
	 * 
	 * @note this WILL be called at least once on init
	 * 
	 * @param are all fired by environment
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		grid.setBackGroundDimentions(w, h);
		super.onSizeChanged(w, h, oldw, oldh);
	}

	/**
	 * Handle key presses (make sure view is focusable)
	 * 
	 * @param are all fired by environment
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		//if DISREGARD_MULTIPLE_KEYPRESSED then check if its the first press of this key
		if(DISREGARD_MULTIPLE_KEYPRESSED && event.getRepeatCount() < 1)
		{
			switch(keyCode)
			{
				case KeyEvent.KEYCODE_DPAD_LEFT:
				case KeyEvent.KEYCODE_4:
				{
					currentAction = ACTION_STRAFE_LEFT;
					break;
				}
				case KeyEvent.KEYCODE_DPAD_RIGHT:
				case KeyEvent.KEYCODE_6:
				{
					currentAction = ACTION_STRAFE_RIGHT;
					break;
				}
				case KeyEvent.KEYCODE_DPAD_UP:
				case KeyEvent.KEYCODE_2:
				{
					currentAction = ACTION_ROTATE_L;
					break;
				}
				case KeyEvent.KEYCODE_DPAD_DOWN:
				case KeyEvent.KEYCODE_8:
				{
					currentAction = ACTION_ROTATE_R;
					break;
				}
				case KeyEvent.KEYCODE_5:
				case KeyEvent.KEYCODE_ENTER:
				case KeyEvent.KEYCODE_SPACE:
				case KeyEvent.KEYCODE_DPAD_CENTER:
				{
					currentAction = ACTION_MAKE_FALL;
					break;
				}
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * Update user actions
	 * Update engine actions (gravity and line check)
	 */
	public void update() {
		long time = System.currentTimeMillis();

		
		if( mHasFocus )
		{
			//manage gameOver
			if(currentShape.isGameOver)
			{
				if(!AlertManager.IsAlertActive())
				{
					int alertType = (scoreManager.isTopScore() && !scoreManager.scoreWasSaved)? AlertManager.TYPE_TOP_SCORE:AlertManager.TYPE_GAME_OVER;
					AlertManager.PushAlert(this, alertType);
				}

			}
			//normal state
			else if( time > mNextUpdate )
			{
				mNextUpdate = time + 1000 / FRAME_RATE;
				mTicks++;
				currentShape.update(currentAction);
				currentAction = ACTION_NONE;
				if(time - mLastGravity > GRAVITY_RATE || currentShape.IsFalling())
				{
					mLastGravity = time;
					boolean shapeIsLocked = currentShape.addGravity();
					if(shapeIsLocked)
					{
						int points = grid.update();
						if(points != 0)
							scoreManager.currentScore += points;
					}
				}
				
				if(mTicks/FRAME_RATE > mSeconds)
				{
					mSeconds = mTicks/FRAME_RATE;
				}
			}
		}
		else
		{
			//if paused you don't want to rush into a loop when exiting pause
			mNextUpdate = time + (1000 / OUT_OF_PAUSE_DELAY);
		}
		return;
	}
	
	/**
	 * Paint game
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		
		//so ugly to update in draw but i'm to lazy to implement a real game loop
		update(); //TODO Separate update from draw, and call both inside a clean game loop
		
		super.onDraw(canvas);
		
		//paint elements
		grid.paint(canvas, mPaint);
		
		//paint HUD
		TetrisHud.paintRightHud(canvas, mPaint, getRight(), getTop(), scoreManager, currentShape.getNextType());
		
		//make sure draw will be recalled.
		invalidate();
	}

	public void manageScoreSave(boolean saveToDB, String player) {
		scoreManager.scoreWasSaved = true;
		if(saveToDB && player != null )
				scoreManager.saveScoreIfTopScore(player);
	}

    public void manageEventsFromTap(int Action  ){
        switch (Action){
            case ACTION_ROTATE_L: currentAction = ACTION_ROTATE_L;break;
            case ACTION_ROTATE_R: currentAction = ACTION_ROTATE_R;break;
            case ACTION_STRAFE_LEFT: currentAction = ACTION_STRAFE_LEFT;break;
            case ACTION_STRAFE_RIGHT: currentAction = ACTION_STRAFE_RIGHT;break;
            case ACTION_MAKE_FALL: currentAction = ACTION_MAKE_FALL;break;
        };

    }


}

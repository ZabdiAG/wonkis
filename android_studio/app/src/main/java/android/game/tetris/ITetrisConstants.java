package android.game.tetris;

import android.graphics.Color;


public interface ITetrisConstants {

	//remember, hud is pixel aligned from top, bottom, left or right 
	//but center is dynamic depending on screen size
	
	//constants
	
	//GAME
	//frames per second
	public static final int FRAME_RATE = 48;
	//delay before reupdating
	public final static int OUT_OF_PAUSE_DELAY = FRAME_RATE*2;
	//only first keypress counts for game actions.
	public final static boolean DISREGARD_MULTIPLE_KEYPRESSED = true;

	//game actions
	public static final int ACTION_NONE = 0;
	public static final int ACTION_STRAFE_LEFT = 1;
	public static final int ACTION_STRAFE_RIGHT = 2;
	public static final int ACTION_ROTATE_L = 3;
	public static final int ACTION_ROTATE_R = 4;
	public static final int ACTION_MAKE_FALL = 5;
	
	//HUD
	//does playfield leave space for a margin
	public static final boolean PLAYFIELD_USE_MARGINS = true;
	//in px 
	public static final int MARGIN_TOP = 36;
	public static final int MARGIN_LEFT = 12;
	public static final int MARGIN_RIGHT = 64;
	public static final int MARGIN_BOTTOM = 36;
	//ANCHORED TO THE RIGHT!
	public static final int HUD_SCORE_TEXT_OFFSET = 5;
	public static final int HUD_SCORE_Y_START = 40;
	public static final int HUD_SCORE_INTERLINE = 20;
	public static final int HUD_SCORE_WORD_COLOR = Color.WHITE;
	public static final int HUD_SCORE_NUM_COLOR = Color.YELLOW;
	public static final int HUD_NEXT_WORD_Y_START = HUD_SCORE_Y_START+HUD_SCORE_INTERLINE+20;
	public static final int HUD_NEXT_TEXT_OFFSET = HUD_SCORE_TEXT_OFFSET;
	public static final int HUD_NEXT_SHAPE_X_START = 20;
	public static final int HUD_NEXT_SHAPE_Y_START = HUD_NEXT_WORD_Y_START+15;
	public static final int HUD_NEXT_SHAPE_CELL_SIZE = 5;
	public static final int HUD_NEXT_SHAPE_CELL_OFFSET = HUD_NEXT_SHAPE_CELL_SIZE+3;
	public static final int HUD_NEXT_WORD_COLOR = Color.WHITE;
	public static final int HUD_NEXT_SHAPE_COLOR = Color.GRAY;
	public static final int HUD_TOP_SCORES_Y_START = HUD_NEXT_WORD_Y_START+50;
	public static final int HUD_TOP_SCORES_TEXT_OFFSET = HUD_SCORE_TEXT_OFFSET;
	public static final int HUD_TOP_SCORES_TITLE_COLOR = Color.WHITE;
	public static final int HUD_TOP_SCORES_RANK_COLOR = Color.GREEN;
	public static final int HUD_TOP_SCORES_WORD_COLOR = Color.GREEN;
	public static final int HUD_TOP_SCORES_SCORE_COLOR = Color.WHITE;
	public static final int HUD_TOP_SCORES_RANK_OFFSET = 45;
	public static final int HUD_TOP_SCORES_WORD_OFFSET = 25;
	
	
	
	//PLAYFIELD
	//grid size
	public static final int PLAYFIELD_COLS = 12;
	public static final int PLAYFIELD_ROWS = 18;
	
	//ticks before engine calls gravity
	public static final int GRAVITY_RATE = (1000 / 2);

	//cell where new shapes apear
	public static final int START_CELL		= (PLAYFIELD_COLS/2)-1;
	//cellscroll directions
	public static final int C_CENTER = 0;
	public static final int C_LEFT = -1;
	public static final int C_UP = -PLAYFIELD_COLS;
	public static final int C_RIGHT = 1;
	public static final int C_DOWN = PLAYFIELD_COLS;

	
	//SHAPES
	//a value that lets playfield skip the test of shape cells
	public static final int DONT_CHECK_CELL = -1000;

	//states
	public static final int STATE_USER    = 0;
	public static final int STATE_LOCKED  = 1;
	public static final int STATE_FALLING = 2;
	
	//rotations
	public static final int ROT_LEFT  = -1;
	public static final int ROT_RIGHT =  1;

	//orientations
	public static final int OR_NORTH = 0;
	public static final int OR_EAST  = 1;
	public static final int OR_SOUTH = 2;
	public static final int OR_WEST  = 3;
	public static final int START_ORIENTATION = OR_EAST;
	
	
	//max elems
	public static final int ELEM_BASE = 0;
	public static final int ELEM_1 	  = 1;
	public static final int ELEM_2    = 2;
	public static final int ELEM_3    = 3;
	public static final int MAX_ELEMS = 4;
	//shape types
	public static final int TYPE_LONG 		= 0;
	public static final int TYPE_BL   		= 1;
	public static final int TYPE_L 	  		= 2;
	public static final int TYPE_SQUARE 	= 3;
	public static final int TYPE_S 			= 4;
	public static final int TYPE_BS 		= 5;
	public static final int TYPE_T 			= 6;
	public static final int TYPE_MAX_TYPES	= 7;
	
	//TABLES
	
	//this table contains all the shape types constructs for each orientation
	public static final int SHAPE_TABLE_ELEMS_1 	  = 0;
	public static final int SHAPE_TABLE_ELEMS_2 	  = 1;
	public static final int SHAPE_TABLE_ELEMS_3 	  = 2;
	public static final int SHAPE_TABLE_ELEMS_PER_ROW = 3;
	public static final int SHAPE_TABLE_ROWS_PER_TYPE = 4;
	public static final int SHAPE_TABLE_TYPE_OFFSET	  = SHAPE_TABLE_ROWS_PER_TYPE*SHAPE_TABLE_ELEMS_PER_ROW;
	public static final int[] SHAPE_TABLE =
	{
		//TYPE LONG = 0
		//OR_NORTH (elem1,2,3, mincol, maxcol)
		C_UP, C_DOWN, C_UP*2, //I
		//OR_EAST (elem1,2,3)
		C_LEFT, C_RIGHT, C_RIGHT*2, //---
		//OR_SOUTH (elem1,2,3)
		C_UP, C_DOWN, C_DOWN*2, //I
		//OR_WEST (elem1,2,3)
		C_LEFT, C_RIGHT, C_LEFT*2,//---
		
		//TYPE backwards L = 1
		//OR_NORTH (elem1,2,3, mincol, maxcol)
		C_UP, C_DOWN, C_UP+C_RIGHT,//backwards L
		//OR_EAST (elem1,2,3)
		C_LEFT, C_RIGHT, C_RIGHT+C_DOWN,//--|
		//OR_SOUTH (elem1,2,3)
		C_UP, C_DOWN, C_DOWN+C_LEFT,//inverse L
		//OR_WEST (elem1,2,3)
		C_LEFT, C_RIGHT, C_LEFT+C_UP,//|--

		//TYPE L = 2
		//OR_NORTH (elem1,2,3, mincol, maxcol)
		C_UP, C_DOWN, C_UP+C_LEFT,//L
		//OR_EAST (elem1,2,3)
		C_LEFT, C_RIGHT, C_RIGHT+C_UP,//--|
		//OR_SOUTH (elem1,2,3)
		C_UP, C_DOWN, C_DOWN+C_RIGHT,//L
		//OR_WEST (elem1,2,3)
		C_LEFT, C_RIGHT, C_LEFT+C_DOWN,//|--
		
		//TYPE SQUARE = 3
		//OR_NORTH (elem1,2,3, mincol, maxcol)
		C_RIGHT, C_DOWN, C_RIGHT+C_DOWN,//O
		//OR_EAST (elem1,2,3)
		C_RIGHT, C_DOWN, C_RIGHT+C_DOWN,//O
		//OR_SOUTH (elem1,2,3)
		C_RIGHT, C_DOWN, C_RIGHT+C_DOWN,//O
		//OR_WEST (elem1,2,3)
		C_RIGHT, C_DOWN, C_RIGHT+C_DOWN,//O

		//TYPE S = 4
		//OR_NORTH (elem1,2,3, mincol, maxcol)
		C_DOWN+C_LEFT, C_DOWN, C_RIGHT,//_|-
		//OR_EAST (elem1,2,3)
		C_UP, C_RIGHT, C_RIGHT+C_DOWN,//|-i
		//OR_SOUTH (elem1,2,3)
		C_DOWN+C_LEFT, C_DOWN, C_RIGHT,//_|-
		//OR_WEST (elem1,2,3)
		C_UP, C_RIGHT, C_RIGHT+C_DOWN,//|-i

		//TYPE backwardsS = 4
		//OR_NORTH (elem1,2,3, mincol, maxcol)
		C_LEFT, C_DOWN, C_DOWN+C_RIGHT,//-|_
		//OR_EAST (elem1,2,3)
		C_UP+C_RIGHT, C_RIGHT, C_DOWN,//i-|
		//OR_SOUTH (elem1,2,3)
		C_LEFT, C_DOWN, C_DOWN+C_RIGHT,//-|_
		//OR_WEST (elem1,2,3)
		C_UP+C_RIGHT, C_RIGHT, C_DOWN,//i-|
		
		//TYPE T = 5
		//OR_NORTH (elem1,2,3, mincol, maxcol)
		C_UP, C_DOWN, C_RIGHT,//T
		//OR_EAST (elem1,2,3)
		C_LEFT, C_DOWN, C_RIGHT,//T
		//OR_SOUTH (elem1,2,3)
		C_LEFT, C_DOWN, C_UP,//T
		//OR_WEST (elem1,2,3)
		C_LEFT, C_UP, C_RIGHT,//T
	};
	
}

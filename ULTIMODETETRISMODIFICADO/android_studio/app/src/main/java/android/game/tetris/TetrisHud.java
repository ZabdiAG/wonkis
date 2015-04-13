package android.game.tetris;

import android.database.Cursor;
import android.game.score.ScoreManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;

public class TetrisHud implements ITetrisConstants{

	public static void paintRightHud(Canvas canvas, Paint paint, int right, int top, ScoreManager s, int nextType ) {
		int x,y;
		
		//score
		x = right-HUD_SCORE_TEXT_OFFSET;
		y = top+MARGIN_TOP+HUD_SCORE_Y_START;
		paint.setTextAlign(Align.RIGHT);
		paint.setColor(HUD_SCORE_WORD_COLOR);
		canvas.drawText("Puntuaje", x, y, paint);
		y+=HUD_SCORE_INTERLINE;
		paint.setColor(HUD_SCORE_NUM_COLOR);
		canvas.drawText(""+s.currentScore, x, y, paint);
		
		//next shape
		displayNextShape(canvas, paint, right, top, Align.RIGHT, nextType);

		//top scores
		displayTopScores(canvas, paint, s.getTopScores(), right, top, Align.RIGHT);
		
		//normal align
		paint.setTextAlign(Align.LEFT);
	}
	
	
	public static void displayNextShape(Canvas canvas, Paint mPaint, int startX, int startY, Align a, int nextType ){
		int tmpX,tmpY,offset;
		int i = 0;
		int x = startX-HUD_NEXT_TEXT_OFFSET;
		int y = startY+MARGIN_TOP+HUD_NEXT_WORD_Y_START;

		mPaint.setTextAlign(a);
		mPaint.setColor(HUD_NEXT_WORD_COLOR);
		canvas.drawText("Next", x, y, mPaint);
		mPaint.setColor(HUD_NEXT_SHAPE_COLOR);
		x = startX - HUD_NEXT_SHAPE_X_START;
		y = startY+MARGIN_TOP+HUD_NEXT_SHAPE_Y_START;
		tmpX = x;
		tmpY = y;
		offset = (nextType*SHAPE_TABLE_TYPE_OFFSET)+START_ORIENTATION*SHAPE_TABLE_ELEMS_PER_ROW;
		do {
			canvas.drawRect(tmpX, tmpY, tmpX+HUD_NEXT_SHAPE_CELL_SIZE, tmpY+HUD_NEXT_SHAPE_CELL_SIZE, mPaint);
			switch(SHAPE_TABLE[offset+i])
			{
				case C_LEFT:
					tmpX=x-HUD_NEXT_SHAPE_CELL_OFFSET;
					tmpY=y;
					break;
				case C_RIGHT:
					tmpX=x+HUD_NEXT_SHAPE_CELL_OFFSET;
					tmpY=y;
					break;
				case C_UP:
					tmpX=x;
					tmpY=y-HUD_NEXT_SHAPE_CELL_OFFSET;
					break;
				case C_DOWN:
					tmpX=x;
					tmpY=y+HUD_NEXT_SHAPE_CELL_OFFSET;
					break;
				case C_LEFT+C_DOWN:
					tmpX=x-HUD_NEXT_SHAPE_CELL_OFFSET;
					tmpY=y+HUD_NEXT_SHAPE_CELL_OFFSET;
					break;
				case C_RIGHT+C_DOWN:
					tmpX=x+HUD_NEXT_SHAPE_CELL_OFFSET;
					tmpY=y+HUD_NEXT_SHAPE_CELL_OFFSET;
					break;
				case C_LEFT+C_UP:
					tmpX=x-HUD_NEXT_SHAPE_CELL_OFFSET;
					tmpY=y-HUD_NEXT_SHAPE_CELL_OFFSET;
					break;
				case C_RIGHT+C_UP:
					tmpX=x+HUD_NEXT_SHAPE_CELL_OFFSET;
					tmpY=y-HUD_NEXT_SHAPE_CELL_OFFSET;
					break;
				case C_RIGHT*2:
					tmpX=x-(HUD_NEXT_SHAPE_CELL_OFFSET*2);//i am cheating here and moving to the left for better hud display (anchor is on right)
					tmpY=y;
					break;
				default:
					//need to manage
					break;
			}
			i++;
		} while (i < MAX_ELEMS);
	}
	
	public static void displayTopScores(Canvas canvas, Paint p, Cursor c, int x, int y, Align a ){

		x -= HUD_TOP_SCORES_TEXT_OFFSET;
		y += MARGIN_TOP+HUD_TOP_SCORES_Y_START;
		String name;
		int score;
		int colScore = c.getColumnIndex(ScoreManager.DATABASE_TABLE_SCORES_SCORE);
		int colName  = c.getColumnIndex(ScoreManager.DATABASE_TABLE_SCORES_NAME);
		

		c.moveToLast(); 
		p.setTextAlign(a);
		//paint title
		p.setColor(HUD_TOP_SCORES_TITLE_COLOR);
		canvas.drawText("Record", x, y, p);
		y+=20;
		
		for (int i  = 0; i < c.getCount() && i < ScoreManager.TOP_SCORE_NB; i++,c.moveToPrevious(),y+=14) {

			score = c.getInt(colScore);
			name = c.getString(colName);
			p.setColor(HUD_TOP_SCORES_RANK_COLOR);
			canvas.drawText((i+1)+". "+name+": "+score, x, y, p);
		}
		c.close();

	}
}

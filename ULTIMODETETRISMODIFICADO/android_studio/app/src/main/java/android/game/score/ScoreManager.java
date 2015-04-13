package android.game.score;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScoreManager {

    private static final String DATABASE_TABLE_SCORES = "scores";
    private static final String DATABASE_TABLE_SCORES_ID = "id";
    public static final String DATABASE_TABLE_SCORES_NAME = "name";
    public static final String DATABASE_TABLE_SCORES_SCORE = "score";
    public static final int TOP_SCORE_NB = 10;
    
	public int currentScore;
	private Context ctx;
	private ScoreDBHelper helper;
	private SQLiteDatabase database;
	public boolean scoreWasSaved;
	
	public ScoreManager(Context context) {
		ctx = context;
		helper = new ScoreDBHelper(ctx);
    	database = helper.getWritableDatabase();
	}
	
    public Cursor getTopScores()
    {
    	return database.query(DATABASE_TABLE_SCORES, new String[]{DATABASE_TABLE_SCORES_NAME,DATABASE_TABLE_SCORES_SCORE}, null, null, null, null, DATABASE_TABLE_SCORES_SCORE);
    }
    

    public boolean isTopScore()
    {
    	if(currentScore < 1)
    		return false;
    	
    	boolean ret;
    	Cursor c = getTopScores();	
    	
    	if(c.getCount() >= TOP_SCORE_NB)
    		ret = currentScore > c.getInt(c.getColumnIndex(DATABASE_TABLE_SCORES_SCORE));
    	else
    		ret =  true;
		c.close();
		
		return ret;
    }
    
    public void saveScoreIfTopScore(String player)
    {
    	if(isTopScore())
    		saveScore(player);
    }
    
	private long saveScore(String player)
	{
        ContentValues initialValues = new ContentValues();
        initialValues.put(DATABASE_TABLE_SCORES_NAME, player);
        initialValues.put(DATABASE_TABLE_SCORES_SCORE, currentScore);
    	return database.insert(DATABASE_TABLE_SCORES, null, initialValues);
	}
	
	public class ScoreDBHelper extends SQLiteOpenHelper {

		private static final String DATABASE_NAME = "data";
		private static final int DATABASE_VERSION = 7;
	    
		public ScoreDBHelper( Context context ) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table "+DATABASE_TABLE_SCORES
            		+" ("+DATABASE_TABLE_SCORES_ID+" integer primary key autoincrement, "
            		+DATABASE_TABLE_SCORES_NAME+" TEXT, " 
            		+DATABASE_TABLE_SCORES_SCORE+" integer);");
		}

		@Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE_SCORES);
            onCreate(db);
		}
		
	}
}

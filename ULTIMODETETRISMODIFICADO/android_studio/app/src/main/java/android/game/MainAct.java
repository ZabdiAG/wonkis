package android.game;

import android.app.Activity;
import android.game.menu.GenericMenu;
import android.game.tetris.ITetrisConstants;
import android.game.tetris.SimpleGestureFilter;
import android.game.tetris.SimpleGestureFilter.SimpleGestureListener;
import android.game.tetris.TetrisView;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

//import com.android.swipe.R;

public class MainAct extends Activity implements SimpleGestureListener{

    private SimpleGestureFilter detector;
	
	TetrisView gV;
	GenericMenu gM;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Detect touched area
        gV = new TetrisView(this);
        setContentView(gV);

        // Detect touched area
        detector = new SimpleGestureFilter(this,this);

        MediaPlayer mediaPlayer;
        mediaPlayer = MediaPlayer.create(this, R.raw.tetris);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100,100);
        mediaPlayer.start();

    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
    	// TODO Auto-generated method stub
    	gV.setGameFocus(hasFocus);
    	super.onWindowFocusChanged(hasFocus);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	gM = new GenericMenu(menu);
    	gM.populate();
    	// TODO Auto-generated method stub
    	return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	if(item.getTitle().equals("De nuevo"))
    		gV.restartGame();
    	if(item.getTitle().equals("Salir"))
    		finish();//just close
    	return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }
    @Override
    public void onSwipe(int direction) {
        String str = "";

        switch (direction) {

            case SimpleGestureFilter.SWIPE_RIGHT :
                gV.manageEventsFromTap(ITetrisConstants.ACTION_STRAFE_RIGHT);
                break;
            case SimpleGestureFilter.SWIPE_LEFT :
                gV.manageEventsFromTap(ITetrisConstants.ACTION_STRAFE_LEFT);
                break;
            case SimpleGestureFilter.SWIPE_DOWN :
                gV.manageEventsFromTap(ITetrisConstants.ACTION_MAKE_FALL);;
                break;
            case SimpleGestureFilter.SWIPE_UP :
                gV.manageEventsFromTap(ITetrisConstants.ACTION_ROTATE_R);
                break;

        }
        //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDoubleTap() {
        gV.manageEventsFromTap(ITetrisConstants.ACTION_ROTATE_L);

    }


}
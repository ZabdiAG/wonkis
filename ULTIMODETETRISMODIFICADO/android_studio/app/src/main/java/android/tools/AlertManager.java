package android.tools;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.game.R;
import android.game.tetris.TetrisView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class AlertManager {

	public static final int TYPE_GAME_OVER = 0;
	public static final int GAME_OVER_RESTART = DialogInterface.BUTTON1;
	public static final int GAME_OVER_QUIT = DialogInterface.BUTTON2;
	public static final int TYPE_TOP_SCORE = 1;
	public static final int TOP_SCORE_SAVE = DialogInterface.BUTTON1;
	public static final int TOP_SCORE_DONT_SAVE = DialogInterface.BUTTON2;
	
	
	
	private static boolean alertActive = false;

	public static boolean IsAlertActive()
	{
		return alertActive ;
	}
	
	public static void PushAlert(TetrisView v, int type) {
		int title, msg, ok, cancel;
		View textEntryView=null;
		OnClickListener l;
		switch(type)
		{
			case TYPE_GAME_OVER:
				title = R.string.GameOver;
				msg = R.string.Lost;
				ok = R.string.Replay;
				cancel = R.string.QuitG;
				l = getGameOverClickListener(v);
				break;
			case TYPE_TOP_SCORE:
				title = R.string.Gratz;
				msg = R.string.Win;
				ok = R.string.SaveS;
				cancel = R.string.Cancel;
			    textEntryView = LayoutInflater.from(v.getContext()).inflate(R.layout.alert_dialog_text_entry, null); 
				l = getTopScoreClickListener(v,textEntryView);
				break;
			default: //SHOULD NEVER HAPPEN
				title = R.string.GameOver;
				msg = R.string.GameOver;
				ok = R.string.GameOver;
				cancel = R.string.GameOver;
				l = getGameOverClickListener(v);
				break;
		}
		
		
		alertActive = true;

		AlertDialog a = new AlertDialog.Builder(v.getContext())
	      .setInverseBackgroundForced(true)
	      .setCancelable(false)
	      .setTitle(title)
	      .setMessage(msg)
	      .setNegativeButton(cancel, l)
	      .setPositiveButton(ok, l)
	      .create();
		
		if(textEntryView!=null)//a runtime exception made me write like this. need to re-factor
		{
			a.setView(textEntryView);
		}
		
		a.show();
	}	

	public static void Resolve() {
		alertActive = false;
	}    
	
	private static OnClickListener getGameOverClickListener(final TetrisView v){
		return new  DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				// resolve alert
				switch(which)
				{
					case GAME_OVER_RESTART:
						v.restartGame();
						break;
					case GAME_OVER_QUIT:
						v.quitGame();
						break;
				}
				AlertManager.Resolve();
			}
		};
	} 
	
	private static OnClickListener getTopScoreClickListener(final TetrisView v, final View textEntryView){
		return new  DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
					// resolve alert
					switch(which)
					{
						case TOP_SCORE_SAVE:
							String player = null;
		                    EditText input = (EditText)textEntryView.findViewById(R.id.player_name); 
		                    if(input != null)
		                    {
		                    	player = input.getText().toString(); 
		            			if(player.getBytes().length > 0)
		            				v.manageScoreSave(true, player);
		                    }
							break;
						case TOP_SCORE_DONT_SAVE:
							v.manageScoreSave(false, null);
							break;
					}
						
					AlertManager.Resolve();
			}
		};
	}
}

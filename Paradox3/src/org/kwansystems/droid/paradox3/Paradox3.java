package org.kwansystems.droid.paradox3;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.*;

public class Paradox3 extends Activity {
	  public static boolean check(int[] Clock, int Start, int Branches, int[] Path) {
		    boolean[] Mark=new boolean[Clock.length];
		    int here=Start;
		    for (int i=0;i<Clock.length;i++) {
		      if(Mark[here]) return false;
		      Path[i]=here;
		      Mark[here]=true;
		      int jump=Clock[here];
		      if((Branches & 1)!=0) jump=-jump;
		      here+=jump;
		      if(here<0) here+=Clock.length;
		      here=here % Clock.length;
		      Branches=Branches/2;
		    }
		    return true;
		  }
		  
//		  public static void main(String[] args) {
//		    int[] Clock=new int[] {3,5,3,5,2,1,5,3,2,3,3};
//		  }
    EditText txtNumbers;
    TextView txtSolution;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paradox3); 
		txtNumbers=(EditText)findViewById(R.id.txtNumbers);
		txtSolution=(TextView)findViewById(R.id.txtSolution);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.paradox3, menu);
		return true;
	}

	public void clickNum(View view) {
      txtNumbers.setText(txtNumbers.getText().toString()+((Button)view).getText().toString());
	}

	public void clickSolve(View view) {
	  String S="";
	  String ClockString=txtNumbers.getText().toString();
      int[] Clock=new int[ClockString.length()];
      for(int i=0;i<Clock.length;i++) Clock[i]=ClockString.codePointAt(i)-48;
	  int[] Path=new int[Clock.length]; 
	  int acc=0;
	  boolean found=false;
	  int start=0;
   	  while(start<Clock.length && !found) {
   		int branches=0;
	    while(branches<(1 << Clock.length) && !found) {
	      acc++;
	      if(check(Clock,start,branches,Path)) {
	    	found=true;
	        S=S+"Found a path\n";
	        for(int step=0;step<Path.length;step++) {
	          S=S+String.format("Step %2d: %2d (%2d)\n",step+1,Path[step],Clock[Path[step]]);
	        }
	      }
	      branches++;
	    }
	    start++;
	  }
      if(!found) {
        S=S+"No path found\n";
      }
      S=S+String.format("Checked %d possibilities\n",acc);
      txtSolution.setText(S);
	}

	public void clickClear(View view) {
      txtNumbers.setText("");
	}
}

package ee.leettipper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;
import ee.hellow.R;

public class LeetTipper extends Activity {
	int minTipPercent = 10;
	int maxTipPercent = 30;
	int idealTipPercent = 18;
	TableLayout suggTable;
	int checkCents;
	boolean evenRow = false;
	int currentMenuId = -1;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final EditText check = (EditText) findViewById(R.id.edittext);
        suggTable = (TableLayout) findViewById(R.id.tipSuggestionsTable);
        
        check.setOnKeyListener(new OnKeyListener() {
        	public boolean onKey(View v, int keyCode, KeyEvent event) {
        		if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
        			(keyCode == KeyEvent.KEYCODE_ENTER)) {
        				// Replace previous results with new ones.
        	    		clearSuggestions();
        				generateSuggestions(Double.parseDouble(check.getText().toString()));
        				return true;
        		}
        		return false;
        	}
        });        
    }
    
    // converts the sum to a 2-decimal integer value (in cents)
    private static int convertToCents(double x)
    {
        return (int)(x*100);        
    }
    
    private int getTipPercent(int total) {
    	if (checkCents == 0) return 0;
    	return (int) (100 * (total - checkCents) / checkCents); 
    }
    
    private static boolean isPalindrome(int val) {
    	String s = Integer.toString(val);
    	String sRev = "";    	
    	for (int i = s.length() - 1; i >= 0; --i) {    		
    		sRev += s.charAt(i);
    	}
    	return sRev.compareTo(s) == 0;
    }

    static int digitAt(int val, int i) {
    	int tens = (int)Math.pow(10, i);
    	int trimUpper = val - (val / (tens * 10)) * (tens * 10);
    	return trimUpper / tens; 
    }
    
    static int maxDigitIndex(int val) {
    	int idx = 0;
    	while ((val /= 10) != 0) {
    		idx++;
    	}
    	return idx;
    }
    
    private void clearSuggestions() {
    	evenRow = false;
    	suggTable.removeViews(1, suggTable.getChildCount() - 1);
    }
    
    public void generateSuggestions(double check) {    	 
    	// Do all computations for integers (*100) and convert only final values back to [dollars].[cents]
    	checkCents = convertToCents(check);
    	if (checkCents == 0) return;
    	int minTotal = convertToCents((minTipPercent + 100) / 100.0 * check);
    	int maxTotal = convertToCents((maxTipPercent + 100) / 100.0 * check);    	
    	addSuggestion(minTotal);
    	// Generate palindromes in the suggested range
    	for (int total = minTotal + 1; total <= maxTotal;) {    		
    		if (isPalindrome(total)) {
    			addSuggestion(total);    			
    		}
    		total = getNextPalindrome(total + 1);
    	}
    	addSuggestion(maxTotal);
    }
    
    static int getNextPalindrome(int val) {
    	int i = maxDigitIndex(val);
    	int j = 0;
    	while (i > j) {
    		int di = digitAt(val, i);
    		int dj = digitAt(val, j);
    		if (di != dj) {
    			val += (di - dj) * Math.pow(10, j);
	    		// add the carry value
	    		if (di < dj) val += Math.pow(10, j + 1);
    		}
    		i--; j++;
    	}
    	return val;
    }
    
    private static String getRealValue(int val) {
    	String s = Integer.toString(val);
    	int len = s.length();
    	if (len < 3)
    		return "0." + ((len==1)?"0":"") + s;
    	return s.subSequence(0, len - 2) + "." + s.subSequence(len - 2, len);
    }
    
    private void addSuggestion(int totalVal) {
    	TableRow tr = new TableRow(this);
    	if (evenRow) {
    		tr.setBackgroundColor(getResources().getColor(R.color.even_row));
    	} else {
    		tr.setBackgroundColor(getResources().getColor(R.color.odd_row));
    	}
    	evenRow = !evenRow;
    		
    	// Percent
    	TextView percent = new TextView(this);
    	percent.setText(Integer.toString((getTipPercent(totalVal))) + "%");
    	// Tip
    	TextView tip = new TextView(this);
    	int tipVal = totalVal - checkCents;
    	if (isPalindrome(tipVal)) {
    		tip.setTextColor(getResources().getColor(R.color.palindrome));
    		tip.setTypeface(tip.getTypeface(), Typeface.BOLD);
    	}
    	tip.setText(getRealValue(tipVal));
    	// Total
    	TextView total = new TextView(this);
    	if (isPalindrome(totalVal)) {
    		total.setTextColor(getResources().getColor(R.color.palindrome));
    		total.setTypeface(total.getTypeface(), Typeface.BOLD);
    	}
    	total.setText(getRealValue(totalVal));
    	
    	tr.addView(percent);
    	tr.addView(tip);
    	tr.addView(total);
    	
    	// Leftover tip
    	int idealTip = (int)(idealTipPercent / 100.0 * checkCents);
    	if (idealTip > tipVal) {
    		TextView left = new TextView(this);
    		left.setText(getRealValue(idealTip - tipVal));
    		tr.addView(left);
    	}  	
    	
    	suggTable.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));    	
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    	// ignore orientation change
    	super.onConfigurationChanged(newConfig);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// Set the menu item that is currently accessed.
    	currentMenuId = item.getItemId();
    	
    	// Create draggable bar to alter value.
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Select new tip percent:");

		final EditText et = new EditText(getApplicationContext());
		et.setText(Integer.toString(getCurrentMenuTipValue()));
		et.setInputType(InputType.TYPE_CLASS_NUMBER);
		builder.setView(et);
		
		builder.setCancelable(true);
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				setCurrentMenuTipValue(Integer.parseInt(et.getText().toString()));				
			}
		});
				
		AlertDialog alert = builder.show();
		return true;
    }

    private int getCurrentMenuTipValue() {
		switch (currentMenuId) {
    	case R.id.mintip:    		
    		return minTipPercent;    		
    	case R.id.maxtip:
    		return maxTipPercent;    		
    	case R.id.idealtip:
    		return idealTipPercent;
    	default:
    		return -1;
    		// weird value!
    	}
    }
    
	private void setCurrentMenuTipValue(int val) {
		switch (currentMenuId) {
    	case R.id.mintip:    		
    		minTipPercent = val;
    		break;
    	case R.id.maxtip:
    		maxTipPercent = val;
    		break;
    	case R.id.idealtip:
    		idealTipPercent = val;    		
    		break;
    	default:
    		// weird value!
    	}		
	}
}

package org.kwansystems.navcalc;

import java.security.InvalidKeyException;

import org.kwansystems.encrypt.KwanCryptWrapper;
import org.kwansystems.encrypt.Twofish_Algorithm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class TwofishActivity extends Activity {
  @Override
  public void onTrimMemory(int level) {
    // TODO Auto-generated method stub
    super.onTrimMemory(level);
    if(level==TRIM_MEMORY_UI_HIDDEN) {
      TwofishActivity.this.finish();
    }
  }
  protected void save() {
    final AlertDialog.Builder alert = new AlertDialog.Builder(this);
    final EditText input1 = new EditText(this);
    final EditText input2 = new EditText(this);
    final LinearLayout view=new LinearLayout(getApplicationContext());
    view.setOrientation(1);
    input1.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_VARIATION_PASSWORD);
    input2.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_VARIATION_PASSWORD);
    view.addView(input1);
    view.addView(input2);
    alert.setView(view);    //edit text added to alert
//    alert.setView(input2);    //edit text added to alert
    alert.setTitle("Please enter passphrase");   //title setted
    alert.setCancelable(true);
    alert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog,int id) {
        // if this button is clicked, close
        // current activity
        String passphrase=input1.getText().toString();
        String passphrase2=input2.getText().toString();
        if (passphrase.equals(passphrase2)) {
          EditText TwofishNotes=(EditText) findViewById(R.id.txtTwofishNotes);
          SharedPreferences settings = getSharedPreferences("TwofishNote", 0);
          SharedPreferences.Editor edit=settings.edit();
          try {
            edit.putString("ciphertext", KwanCryptWrapper.encrypt(passphrase,TwofishNotes.getText().toString()));
          } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            Toast.makeText(getApplicationContext(), "Problem with save, nothing changed", Toast.LENGTH_SHORT).show();
          }
          edit.commit();
          TwofishActivity.this.finish();
        } else {
          Toast.makeText(getApplicationContext(), "Passwords don't match, nothing changed", Toast.LENGTH_SHORT).show();
        }
      }
    });
    alert.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog,int id) {
        
      }
    });
    alert.show();
  }
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    setContentView(R.layout.twofish);
    ((Button) findViewById(R.id.btnSave)).setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        TwofishActivity.this.save();
      }
    });
    ((Button) findViewById(R.id.btnDontSave)).setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        TwofishActivity.this.finish();
      }
    });

    SharedPreferences settings = getSharedPreferences("TwofishNote", 0);
    if(settings.contains("ciphertext")) {
      final String ciphertext = settings.getString("ciphertext", null);
      final AlertDialog.Builder alert = new AlertDialog.Builder(this);
      final EditText input1 = new EditText(this);
      final EditText input2 = new EditText(this);
      final LinearLayout view=new LinearLayout(getApplicationContext());
      view.setOrientation(1);
      input1.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_VARIATION_PASSWORD);
//      input2.setInputType(InputType.TYPE_CLASS_NUMBER);
      view.addView(input1);
//      view.addView(input2);
      alert.setView(view);    //edit text added to alert
      alert.setTitle("Please enter passphrase");   //title setted
      alert.setCancelable(true);
      alert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog,int id) {
        // if this button is clicked, close
        // current activity
        EditText TwofishNotes=(EditText) findViewById(R.id.txtTwofishNotes);
        String passphrase=input1.getText().toString();
//        String mode=input2.getText().toString();
        String plaintext;
        try {
          plaintext = KwanCryptWrapper.decrypt(passphrase, ciphertext);
          TwofishNotes.setText(plaintext);
        } catch (InvalidKeyException e) {
          // TODO Auto-generated catch block
          Toast.makeText(getApplicationContext(), "Problem with load", Toast.LENGTH_SHORT).show();
        }
        }
      });
      alert.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog,int id) {
        // if this button is clicked, just close
        // the dialog box and do nothing
        TwofishActivity.this.finish();
      }
    });
    alert.show();
    }  
  }
}

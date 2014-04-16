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
import android.widget.Toast;

public class TwofishActivity extends Activity {
  protected void save() {
    final AlertDialog.Builder alert = new AlertDialog.Builder(this);
    final EditText input = new EditText(this);

    input.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_VARIATION_PASSWORD);

    alert.setView(input);    //edit text added to alert
    alert.setTitle("Please enter passphrase");   //title setted
    alert.setCancelable(true);
    alert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog,int id) {
        // if this button is clicked, close
        // current activity
        String passphrase=input.getText().toString();
        EditText TwofishNotes=(EditText) findViewById(R.id.txtTwofishNotes);
        SharedPreferences settings = getSharedPreferences("TwofishNote", 0);
        SharedPreferences.Editor edit=settings.edit();
        try {
          edit.putString("ciphertext", KwanCryptWrapper.encrypt(passphrase,TwofishNotes.getText().toString()));
        } catch (InvalidKeyException e) {
          // TODO Auto-generated catch block
          Toast.makeText(getApplicationContext(), "Problem with save", Toast.LENGTH_SHORT).show();
        }
        edit.commit();
        TwofishActivity.this.finish();
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
      final EditText input = new EditText(this);

      input.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_VARIATION_PASSWORD);

      alert.setView(input);    //edit text added to alert
      alert.setTitle("Please enter passphrase");   //title setted
      alert.setCancelable(true);
      alert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog,int id) {
        // if this button is clicked, close
        // current activity
        EditText TwofishNotes=(EditText) findViewById(R.id.txtTwofishNotes);
        String passphrase=input.getText().toString();
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

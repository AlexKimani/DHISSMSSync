package org.addhen.smssync.util;


import org.addhen.smssync.Prefrences;
import org.addhen.smssync.R;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class LoginPreferenceDialog extends DialogPreference {

	private EditText loginUsername;
	private EditText loginPassword;
	private Context context;
	private String encodedLogin;

	public LoginPreferenceDialog(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Initiates the login view
	 */
	protected View onCreateDialogView() {
		LayoutInflater layoutInflater = LayoutInflater.from(getContext());
		
		Prefrences.loadPreferences(context);
		View view = layoutInflater.inflate(R.layout.preference_login_view, null);

		
		loginUsername = (EditText)view.findViewById(R.id.login_username);
		loginPassword = (EditText)view.findViewById(R.id.login_password);
		if(!Prefrences.dhisLoginPref.equals("")) {	
			// Get the previous username/password
			String decodedLogin = Util.base64decode(Prefrences.dhisLoginPref.getBytes());
			String[] loginInfo = decodedLogin.split(":");
			loginUsername.setText(loginInfo[0]);
			loginPassword.setText(loginInfo[1]);
		}
				
		return view;
		
	}
	
	/**
	 * Persists an Base64 encoded version of the username and password
	 */
	public void onClick(DialogInterface dialog, int which) {
		// if the positive button is clicked, we persist the value.
		if (which == DialogInterface.BUTTON_POSITIVE) {
			encodedLogin = Util.base64encode((loginUsername.getText()+":"+loginPassword.getText()).getBytes()); 
			Prefrences.loadPreferences(context);
			
			if(!encodedLogin.equals(Prefrences.dhisLoginPref)) {
				SharedPreferences settings = context.getSharedPreferences("SMS_SYNC_PREF", 0);
				Editor editor = settings.edit();
				editor.putString("dhisLoginPref",encodedLogin );
				Util.showToast(context, R.string.login_changed);
				editor.commit();
			}
			
		}

		super.onClick(dialog, which);
	}

}

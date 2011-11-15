package org.addhen.smssync.util;


import org.addhen.smssync.R;

import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class LoginPreferenceDialog extends DialogPreference {

	private EditText loginUsername;
	private EditText loginPassword;
	
	private String encodedLogin;

	public LoginPreferenceDialog(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Initiates the login view
	 */
	protected View onCreateDialogView() {
		LayoutInflater layoutInflater = LayoutInflater.from(getContext());
		
		View view = layoutInflater.inflate(R.layout.preference_login_view, null);
		
		loginUsername = (EditText)view.findViewById(R.id.login_username);
		loginPassword = (EditText)view.findViewById(R.id.login_password);
		
		return view;
		
	}
	
	/**
	 * Persists an Base64 encoded version of the username and password
	 */
	public void onClick(DialogInterface dialog, int which) {
		// if the positive button is clicked, we persist the value.
		if (which == DialogInterface.BUTTON_POSITIVE) {
			encodedLogin = Util.base64encode((loginUsername.getText()+":"+loginPassword.getText()).getBytes()); 
			persistString(encodedLogin);
		}

		super.onClick(dialog, which);
	}

	public String getText() {
		return encodedLogin;
	}

}

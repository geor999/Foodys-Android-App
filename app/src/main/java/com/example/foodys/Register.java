package com.example.foodys;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText username, password, repassword;
    Button signup;
    DBHelper DB;
    private View decorview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        username = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        repassword = (EditText) findViewById(R.id.password2);
        signup = (Button) findViewById(R.id.btnsignup);
        DB = new DBHelper(this);
        decorview = getWindow().getDecorView();
        decorview.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility==0)
                {
                    decorview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            |View.SYSTEM_UI_FLAG_FULLSCREEN);
                }
            }
        });
        if(savedInstanceState!=null)
        {
            String editTextString = savedInstanceState.getString("username");
            username.setText(editTextString);
            editTextString = savedInstanceState.getString("password");
            password.setText(editTextString);
            editTextString = savedInstanceState.getString("repassword");
            repassword.setText(editTextString);
        }
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                //Ελεγχω τα usernames και password και δημιουργώ τα απαραίτητα pop up
                if(user.equals("")||pass.equals("")||repass.equals(""))
                    Toast.makeText(Register.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(repass)){
                        Boolean checkuser = DB.checkusername(user);
                        if(checkuser==false){
                            Boolean insert = DB.insertData(user, pass);
                            if(insert==true){
                                Toast.makeText(Register.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),Login.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(Register.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Register.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                } }
        });
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if( hasFocus){
            decorview.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            |View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        // etc.
        savedInstanceState.putString("username", username.getText().toString());
        savedInstanceState.putString("password", password.getText().toString());
        savedInstanceState.putString("repassword",repassword.getText().toString());

    }
}

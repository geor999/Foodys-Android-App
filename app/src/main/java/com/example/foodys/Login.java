package com.example.foodys;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText username, password;
    Button btnlogin,btnredirect;
    DBHelper DB;
    private View decorview;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        setContentView(R.layout.login);
        image=(ImageView) findViewById(R.id.imageviewlogin);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        btnredirect= (Button) findViewById(R.id.btnredirect);
        DB = new DBHelper(this);
        if(savedInstanceState!=null)
        {
            String editTextString = savedInstanceState.getString("username");
            username.setText(editTextString);
            editTextString = savedInstanceState.getString("password");
            password.setText(editTextString);
        }
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();
                //Ελεγχω τα usernames και password και δημιουργώ τα απαραίτητα pop up
                if(user.equals("")||pass.equals(""))
                    Toast.makeText(Login.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                    if(checkuserpass==true){
                        Toast.makeText(Login.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnredirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Register.class);

                startActivity(intent);
            }
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

    }
}
package me.almalkawi.simpleui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    public void sendMessage(View view) {
        final EditText editMessage = (EditText) findViewById(R.id.edit_message);
        Toast.makeText(this, "You received: " + editMessage.getText(), Toast.LENGTH_SHORT).show();
    }
}
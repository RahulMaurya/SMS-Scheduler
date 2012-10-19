package view.test;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
        TextView contact=new TextView(this);
        RelativeLayout base=(RelativeLayout)findViewById(R.id.contact_list);
        base.setBackgroundColor(Color.CYAN);
        
        contact.setText("  Rahul  ");
        contact.setBackgroundResource(R.drawable.roundcorner);
        base.addView(contact);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}

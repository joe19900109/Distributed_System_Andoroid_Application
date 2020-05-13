/**
 * @author Derek Lin
 *
 * This is the main class to activate the android function. Moreover, it will initiate the
 * other working class and pass the reference. After the backend thread finished the whole task,
 * it will call ready funtion to finish the android part.
 */
package cmu.edu.yunanl;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MainActivity ma = this;
        Button submitButton = (Button) findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View viewParam) {
                String name = ((EditText) findViewById(R.id.searchTerm)).getText().toString();
                FetchingNBA fn = new FetchingNBA();
                fn.search(name, ma);
            }
        });
    }
    public void searchReady(String data) {
        TextView reply = (TextView) findViewById(R.id.reply);
        reply.setVisibility(View.VISIBLE);
        if (data == "") {
            data = "No this team abbreviation, please key in again.";
        }
        reply.setText(data);
    }
}

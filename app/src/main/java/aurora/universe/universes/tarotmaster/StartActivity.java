package aurora.universe.universes.tarotmaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void caution_on_click(View view)
    {
        Intent intent = new Intent(StartActivity.this, CautionListActivity.class);
        startActivity(intent);
    }

    public void start_on_click(View view)
    {
        Intent intent = new Intent(StartActivity.this, ReadytoStartActivity.class);
        startActivity(intent);
    }

    public void learn_on_click(View view)
    {
        Intent intent = new Intent(StartActivity.this, CardLearnListActivity.class);
        startActivity(intent);
    }
}

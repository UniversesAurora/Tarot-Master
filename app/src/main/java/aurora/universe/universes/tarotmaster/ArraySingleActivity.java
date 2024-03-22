package aurora.universe.universes.tarotmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by miguelduarte on 4/9/2020.
 */

public class ArraySingleActivity extends AppCompatActivity
{
    ArrayList<Card> card_array;
    ImageView iv1;
    TextView tv1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array_single);

        Intent intent = getIntent();
        boolean discard_ori = intent.getBooleanExtra("discard_ori", false);
        int pick_mode_num = intent.getIntExtra("pick_mode", 0);
        PickMode pick_mode = PickMode.values()[pick_mode_num];

        iv1 = (ImageView) findViewById(R.id.s_iv1);
        tv1 = (TextView) findViewById(R.id.s_tv1);

        card_array = Picker.pick(1, !discard_ori, pick_mode, Addition.NONE,
                null, ArraySingleActivity.this);

        iv1.setImageResource(Card.card_image_id[card_array.get(0).number]);
        iv1.setRotation(card_array.get(0).is_correct_orientation ? 0 : 180);
        tv1.setText(card_array.get(0).card_name);
    }

    public void single_iv1_on_click(View view)
    {
        CardDetailActivity.start_by_card(card_array.get(0), ArraySingleActivity.this, this);
    }
}

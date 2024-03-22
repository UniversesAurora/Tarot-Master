package aurora.universe.universes.tarotmaster;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by miguelduarte on 4/11/2020.
 */

public class CardLearnListActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Card> cards;
    ArrayList<String> card_name_array_list;
    ArrayList<Integer> card_image_ids;
    AppCompatActivity my_this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_learn_list);
        my_this = this;

        cards = Card.get_all_cards(CardLearnListActivity.this);
        card_name_array_list = new ArrayList<>();
        card_image_ids = new ArrayList<>();

        for (int i = 0; i < cards.size(); i++)
        {
            card_name_array_list.add(cards.get(i).card_name);
            card_image_ids.add(Card.card_image_id[i+1]);
        }

        ArrayList<HashMap<String, Object>> list_items = new ArrayList<HashMap<String, Object>>();
        for(int i=0; i < 78; i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("card_image", card_image_ids.get(i));
            map.put("card_name", card_name_array_list.get(i));
            list_items.add(map);
        }

        listView = (ListView)findViewById(R.id.card_learn_list);
        SimpleAdapter adapter = new SimpleAdapter(this, list_items,
                R.layout.card_learn_item, new String[]{"card_image", "card_name"},
                new int[]{R.id.learn_item_image, R.id.learn_item_name});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CardDetailActivity.start_by_card(cards.get(position),
                        CardLearnListActivity.this, my_this);
            }
        });
    }
}

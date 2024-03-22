package aurora.universe.universes.tarotmaster;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by miguelduarte on 4/9/2020.
 */

public class CardDetailActivity extends AppCompatActivity {
    private ExpandableListView listView;
    private Card card;
    private String[] group_title = {"牌面图画解释", "正位时的含义", "逆位时的含义"};
    private MyExpandableListViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);
        listView = (ExpandableListView)findViewById(R.id.detail_list);
        adapter = new MyExpandableListViewAdapter();
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                ImageView iv = (ImageView)v.findViewById(R.id.detail_title_iv);
                if (parent.isGroupExpanded(groupPosition))
                    iv.setImageResource(R.drawable.arrowdown);
                else
                    iv.setImageResource(R.drawable.arrowup);
                return false;
            }
        });
        listView.setAdapter(adapter);
        Intent intent = getIntent();
        int number = intent.getIntExtra("number", 1);
        card = new Card(number, 0, CardDetailActivity.this);
        ImageView iv_detail = (ImageView)findViewById(R.id.detail_iv);
        iv_detail.setImageResource(Card.card_image_id[number]);
        TextView tv_detail = (TextView) findViewById(R.id.detail_card_name_tv);
        tv_detail.setText(card.card_name);
    }

    private class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return 3;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        @Override
        public Object getGroup(int groupPosition) {

            return group_title[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            switch (groupPosition)
            {
                case 0:
                    return card.card_explanation;
                case 1:
                    return card.mean_correct_ori;
                case 2:
                    return card.mean_incorrect_ori;
                default:
                    return card.card_explanation;
            }
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) CardDetailActivity
                        .this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.card_detail_list, null);
            }
            convertView.setTag(R.layout.card_detail_list, groupPosition);
            convertView.setTag(R.layout.card_detail_item, -1);
            TextView text = (TextView) convertView.findViewById(R.id.detail_title_tv);
            text.setText(group_title[groupPosition]);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) CardDetailActivity
                        .this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.card_detail_item, null);
            }
            convertView.setTag(R.layout.card_detail_list, groupPosition);
            convertView.setTag(R.layout.card_detail_item, childPosition);
            TextView text = (TextView) convertView.findViewById(R.id.detail_child_tv);
            text.setText((String)getChild(groupPosition, childPosition));
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

    }

    public void ok_on_click(View view)
    {
        finish();
    }

    public static void start_by_card(Card card, Context context, AppCompatActivity activity)
    {
        Intent intent = new Intent(context, CardDetailActivity.class);
        intent.putExtra("number", card.number);
        activity.startActivity(intent);
    }
}



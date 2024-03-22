package aurora.universe.universes.tarotmaster;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by miguelduarte on 4/10/2020.
 */

public class ArrayChoosetActivity extends AppCompatActivity
{
    private LinearLayout layout_major;
    private ArrayList<Card> card_array;
    private int[] tvids = {
            R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4,
            R.id.tv5, R.id.tv6, R.id.tv7, R.id.tv8,
    };
    private int[] ivids = {
            R.id.iv1, R.id.iv2, R.id.iv3, R.id.iv4,
            R.id.iv5, R.id.iv6, R.id.iv7, R.id.iv8,
    };
    private LinearLayout res_layout;
    private Button detail_button;
    private AppCompatActivity my_this;
    private LinearLayout oc_layout;
    private TextView oc_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array_chooset);
        my_this = this;

        Intent intent = getIntent();
        boolean discard_ori = intent.getBooleanExtra("discard_ori", false);
        int pick_mode_num = intent.getIntExtra("pick_mode", 0);
        PickMode pick_mode = PickMode.values()[pick_mode_num];
        int addition_type = intent.getIntExtra("addition_type", 0);
        int indicator_number = 1;
        Card indicator_card = null;
        Addition addition = Addition.values()[addition_type];
        if (addition == Addition.INDICATOR)
        {
            indicator_number = intent.getIntExtra("indicator_number", 1);
            indicator_card = new Card(indicator_number, 0, ArrayChoosetActivity.this);
        }

        card_array = Picker.pick(4, !discard_ori, pick_mode,
                addition, indicator_card, ArrayChoosetActivity.this);

        layout_major = (LinearLayout)findViewById(R.id.layout_major);
        detail_button = (Button)findViewById(R.id.detail_button);
        oc_layout = (LinearLayout)findViewById(R.id.oc_layout);
        oc_tv = (TextView)findViewById(R.id.oc_tv);


        for (int i = 0; i < card_array.size(); i++)
        {
            ImageView imageView = (ImageView)findViewById(ivids[i]);
            TextView textView = (TextView)findViewById(tvids[i]);
            imageView.setImageResource(Card.card_image_id[card_array.get(i).number]);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < ivids.length; i++)
                    {
                        if (findViewById(ivids[i]) == v)
                        {
                            CardDetailActivity.start_by_card(card_array.get(i),
                                    ArrayChoosetActivity.this, my_this);
                            return;
                        }
                    }
                }
            });
            imageView.setRotation(card_array.get(i).is_correct_orientation ? 0 : 180);
            textView.setText(card_array.get(i).card_name);
        }

        Solver solver = new Solver(card_array);
        res_layout = solver.build_solve_result_layout(0, card_array.size(),
                0.5, 4, ArrayChoosetActivity.this);

        if (res_layout != null)
        {
            detail_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                    if (res_layout.getVisibility() == View.VISIBLE)
                    {
                        button.setText("展开总体分析");
                        res_layout.setVisibility(View.GONE);
                    }
                    else
                    {
                        button.setText("折叠总体分析");
                        res_layout.setVisibility(View.VISIBLE);
                    }
                }
            });
            res_layout.setVisibility(View.GONE);
            layout_major.addView(res_layout);
        }
        else
        {
            detail_button.setVisibility(View.GONE);
        }

        if (addition != Addition.NONE)
        {
            oc_tv.setText("1 " + (addition == Addition.INDICATOR ? "指示牌" : "切牌"));
        }
        else
        {
            oc_layout.setVisibility(View.GONE);
        }
    }

    public void detail_on_click(View view)
    {
        AlertDialog dialog = new AlertDialog.Builder(ArrayChoosetActivity.this).create();
        dialog.setTitle("关于二选一牌阵");
        dialog.setMessage("牌阵方位所代表的含义：\n" +
                "<1>  处于最下方位置的塔罗牌所指的是被占卜者目前的一个状态是什么样的，这其中有很多解释，不过具体的我们还是得按照实际所抽取出来的塔罗牌再做解读。\n" +
                "<2>  2号位置上的塔罗牌所表示的情况是做出第一种选择后即将出现的情况。\n" +
                "<3>  这个位置上的塔罗牌反应出来的现象是做出第二种选择后会出现什么样的情况。\n" +
                "<4>  4号位置上的牌与2号位置上的牌在某种程度上是密切关联的，因为它所反映的情况为做出第一种选择之后，将会造成什么样的影响。\n" +
                "<5>  相同的，5号位置的牌是与3号位置的牌意相通，所表达的意思为第二种选择过后所造成的影响。\n" +
                "\n" +
                "解牌顺序：\n" +
                "使用二选一塔罗牌牌阵时，也是需要按照解牌顺序来解读的，并不是说我们想怎么样玩就怎样玩。对牌组解释的时候，首先我们所要评估的是第一张塔罗牌，通过它我们可以基本判断被占卜者的现状。之后再去解读某一侧的塔罗牌，做出相应的解释。在所有牌都进行了解读以后，我们回过头来再以第一张塔罗牌为基础，统揽全局信息，最后为被占卜人提出合理的建议。");

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "知道了",
                (DialogInterface.OnClickListener) null);
        dialog.show();
    }
}


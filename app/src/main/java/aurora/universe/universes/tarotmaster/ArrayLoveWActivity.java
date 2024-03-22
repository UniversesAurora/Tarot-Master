package aurora.universe.universes.tarotmaster;

import android.content.Context;
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

public class ArrayLoveWActivity extends AppCompatActivity
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array_lovew);
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
            indicator_card = new Card(indicator_number, 0, ArrayLoveWActivity.this);
        }

        card_array = Picker.pick(8, !discard_ori, pick_mode,
                addition, indicator_card, ArrayLoveWActivity.this);

        layout_major = (LinearLayout)findViewById(R.id.layout_major);
        detail_button = (Button)findViewById(R.id.detail_button);


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
                                    ArrayLoveWActivity.this, my_this);
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
                0.5, 4, ArrayLoveWActivity.this);

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
    }

    public void detail_on_click(View view)
    {
        AlertDialog dialog = new AlertDialog.Builder(ArrayLoveWActivity.this).create();
        dialog.setTitle("关于爱情维纳斯");
        dialog.setMessage("塔罗牌中的维纳斯之爱牌阵是专门针对爱情问题进行占卜的牌阵，这个牌阵既可以对爱情的现状进行分析，又可以展望未来的趋势，是一个比较全面的牌阵。\n\n" +
                "适用类型：专门针对爱情问题进行占卜，既可以以爱情双方现在的情况进行比较精确的反映，又能对爱情双方未来可能出现的变化做出预测。\n\n" +
                "适用范围：56张小阿卡纳\n\n" +
                "占卜注意事项：维纳斯之爱牌阵的前四张牌会对第五张牌产生影响，第五张牌又会对第六张牌产生影响。\n\n" +
                "维纳斯牌阵不使用切牌或指示牌，同时也不使用大阿尔卡纳牌，只用56张小阿尔卡纳牌进行占卜。\n\n" +
                "序号1的牌代表的是问卜当事人对问题的看法；\n" +
                "序号2的牌表示爱情另一方对问卜者的心态；\n" +
                "序号3的牌表示问卜当事人对对方的影响；\n" +
                "序号4的牌表示对方对问卜当事人的影响；\n" +
                "序号5的牌表示双方之间的某些障碍；\n" +
                "序号6的牌表示恋情的结果；\n" +
                "序号7的牌表示问卜当事人以后回首这件事时的想法和心情；\n" +
                "序号8的牌表示对方以后的想法和心情。\n\n" +
                "解牌顺序：\n" +
                "先解释序号1至序号4的牌，然后解释序号7和序号8的牌，最后解释序号5和序号6的牌。在八张牌都解释完后，再综合所有信息，对问卜者提出建议。");

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "知道了",
                (DialogInterface.OnClickListener) null);
        dialog.show();
    }
}

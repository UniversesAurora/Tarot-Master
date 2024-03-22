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

public class ArrayWorksActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_array_works);
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
            indicator_card = new Card(indicator_number, 0, ArrayWorksActivity.this);
        }

        card_array = Picker.pick(6, !discard_ori, pick_mode,
                addition, indicator_card, ArrayWorksActivity.this);

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
                                    ArrayWorksActivity.this, my_this);
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
                0.5, 4, ArrayWorksActivity.this);

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
            oc_tv.setText(card_array.size() + " " +
                    (addition == Addition.INDICATOR ? "指示牌" : "切牌"));
        }
        else
        {
            oc_layout.setVisibility(View.INVISIBLE);
        }
    }

    public void detail_on_click(View view)
    {
        AlertDialog dialog = new AlertDialog.Builder(ArrayWorksActivity.this).create();
        dialog.setTitle("关于六芒星阵");
        dialog.setMessage("六芒星在西方的星座中意思是比较广泛的，在六芒星结构上的两端顶点则是分别代表着天、地，其余的四个顶点代表的是水、火、风、土象星座。它们分别代表着不同的意义：分别是过去、未来、爱以及力量。\n" +
                "\n" +
                "说到六芒星的来源，最早是印度宗教中的一种神秘的法阵，后来被西班牙人带至西方所衍生出了不同的含义。\n\n" +
                "六芒星牌阵占卜适用范围：\n" +
                "每组牌阵都会有它所擅长占卜的地方，六芒星牌阵最适合占卜的是工作、学业、投资以及理财。在这四种范围当中，使用六芒星牌阵测试的话是非常不错的。那么就只能占卜这些范围了吗？并不是这样的，只是说在这几种范围当中占卜出来的结果是比较准确的，至于其他的地方也同样是可以利用该牌阵去占卜的。\n\n" +
                "六芒星牌位意义：\n" +
                "1号：代表着过去，指已经发生过的事情\n" +
                "2号：代表着现在，目前正在进行当中的事\n" +
                "3号：代表着未来，事情并未发生的情况，多表预测。\n" +
                "4号：指具体事情将会出现的原因\n" +
                "5号：影响着占卜问题事情的周围环境因素\n" +
                "6号：事情的一些解决办法、对应办法\n" +
                "7号：该位代表指示牌，通过此牌询问当事人的想法以及特质\n" +
                "\n" +
                "解牌的顺序：\n" +
                "在这个塔罗牌六芒星牌阵当中，解牌是有它独特的顺序的，并不是说想从那边开始就从那边开始的。当一个优秀的塔罗师在六芒星解牌的时候，应该会先从7号牌开始解读、评估，通过对7号牌的分析得知该牌对整个牌阵的影响究竟有多大，这个第一步是非常重要的，直接关系到你后边的占卜结果。\n" +
                "接下来是从4号位置的塔罗牌解读，了解占卜人询问的事情原因，最后就是按照下方的的顺序一一解读占卜。1号位、2号位、5号位、3号位以及6号位。全部解读完成以后，塔罗师得综合目前所有的信息给出占卜人合理的建议。");

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "知道了",
                (DialogInterface.OnClickListener) null);
        dialog.show();
    }
}

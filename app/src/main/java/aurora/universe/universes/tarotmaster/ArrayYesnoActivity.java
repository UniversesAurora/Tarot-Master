package aurora.universe.universes.tarotmaster;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by miguelduarte on 4/10/2020.
 */

public class ArrayYesnoActivity extends AppCompatActivity
{
    private LinearLayout layout_major;
    private ArrayList<Card> card_array;
    private int[] tvids = {
            R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4,
            R.id.tv5, R.id.tv6, R.id.tv7, R.id.tv8, R.id.tv9,
    };
    private int[] ivids = {
            R.id.iv1, R.id.iv2, R.id.iv3, R.id.iv4,
            R.id.iv5, R.id.iv6, R.id.iv7, R.id.iv8, R.id.iv9,
    };
    private LinearLayout res_layout;
    private Button detail_button;
    private AppCompatActivity my_this;
    private LinearLayout oc_layout;
    private TextView oc_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array_yesno);
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
            indicator_card = new Card(indicator_number, 0, ArrayYesnoActivity.this);
        }

        card_array = Picker.pick(8, !discard_ori, pick_mode,
                addition, indicator_card, ArrayYesnoActivity.this);

        layout_major = (LinearLayout)findViewById(R.id.layout_major);
        detail_button = (Button)findViewById(R.id.detail_button);
        oc_layout = (LinearLayout)findViewById(R.id.oc_layout);
        oc_tv = (TextView)findViewById(R.id.oc_tv);


        for (int i = 0; i < card_array.size(); i++)
        {
            ImageView imageView = (ImageView)findViewById(ivids[i]);
            TextView textView = (TextView)findViewById(tvids[i]);
            Log.i("hello", Integer.toString(i));
            imageView.setImageResource(Card.card_image_id[card_array.get(i).number]);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < ivids.length; i++)
                    {
                        if (findViewById(ivids[i]) == v)
                        {
                            CardDetailActivity.start_by_card(card_array.get(i),
                                    ArrayYesnoActivity.this, my_this);
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
                0.5, 4, ArrayYesnoActivity.this);

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
            oc_tv.setText(addition == Addition.INDICATOR ? "指示牌" : "切牌");
        }
        else
        {
            oc_layout.setVisibility(View.GONE);
        }
    }

    public void detail_on_click(View view)
    {
        AlertDialog dialog = new AlertDialog.Builder(ArrayYesnoActivity.this).create();
        dialog.setTitle("关于是否牌阵");
        dialog.setMessage(
                "1-4代表“是”。5-8代表“否”\n" +
                "\n" +
                "卡位置的含义：哪一方有更积极的卡，这就是答案。如果正面和负面卡的数量相同，问题的答案是一半。");

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "知道了",
                (DialogInterface.OnClickListener) null);
        dialog.show();
    }
}


package aurora.universe.universes.tarotmaster;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * Created by miguelduarte on 4/9/2020.
 */

public class ArrayThreeActivity extends AppCompatActivity
{
    private LinearLayout layout_major;
    private ArrayList<Card> card_array;
    private ArrayList<ImageView> ivs;
    private ArrayList<Button> bts;
    private ArrayList<LinearLayout> lls;
    private AppCompatActivity my_this;
    private View.OnClickListener btn_on_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < bts.size(); i++)
            {
                Button cbt = bts.get(i);
                if (v == cbt)
                {
                    if (lls.get(i).getVisibility() == View.VISIBLE)
                    {
                        if (cbt.getText() == "折叠总体分析")
                            cbt.setText("展开总体分析");
                        else
                            cbt.setText("展开本组分析");

                        lls.get(i).setVisibility(View.GONE);
                    }
                    else
                    {
                        if (cbt.getText() == "展开总体分析")
                            cbt.setText("折叠总体分析");
                        else
                            cbt.setText("折叠本组分析");
                        lls.get(i).setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array_three);
        my_this = this;

        Intent intent = getIntent();
        boolean discard_ori = intent.getBooleanExtra("discard_ori", false);
        int pick_mode_num = intent.getIntExtra("pick_mode", 0);
        PickMode pick_mode = PickMode.values()[pick_mode_num];
        int group_number = intent.getIntExtra("group_number", 1);
        int addition_type = intent.getIntExtra("addition_type", 0);
        int indicator_number = 1;
        Card indicator_card = null;
        Addition addition = Addition.values()[addition_type];
        if (addition == Addition.INDICATOR)
        {
            indicator_number = intent.getIntExtra("indicator_number", 1);
            indicator_card = new Card(indicator_number, 0, ArrayThreeActivity.this);
        }

        card_array = Picker.pick(group_number*3, !discard_ori, pick_mode,
                addition, indicator_card, ArrayThreeActivity.this);

        layout_major = (LinearLayout)findViewById(R.id.layout_major_t);

        ivs = new ArrayList<>();
        bts = new ArrayList<>();
        lls = new ArrayList<>();
        for (Card card : card_array)
        {
            final ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(lp);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setImageResource(Card.card_image_id[card.number]);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < ivs.size(); i++)
                    {
                        if (ivs.get(i) == v)
                        {
                            CardDetailActivity.start_by_card(card_array.get(i),
                                    ArrayThreeActivity.this, my_this);
                            return;
                        }
                    }
                }
            });
            imageView.setRotation(card.is_correct_orientation ? 0 : 180);
            ivs.add(imageView);
        }


        if (addition != Addition.NONE)
        {
            String addi_text = addition == Addition.INDICATOR ? "指示牌" : "切牌";
            ArrayList<LinearLayout> al = new ArrayList<>();
            al.add(build_card_layout(card_array.size()-1,
                    true, false, addi_text, 0));
            layout_major.addView(build_group_layout(al));
        }

        Solver solver = new Solver(card_array);

        for (int current_group = 0; current_group < group_number; current_group++)
        {
            ArrayList<LinearLayout> current_cards = new ArrayList<>();
            for (int current_card = current_group*3; current_card < (current_group+1)*3; current_card++)
            {
                if (pick_mode == PickMode.ONLY_MAJOR)
                {
                    current_cards.add(build_card_layout(current_card,
                            false, true, null, 0));
                }
                else
                {
                    int priority = 0;
                    if (current_card % 3 == 1)
                        priority++;
                    if (card_array.get(current_card).is_major)
                        priority++;
                    current_cards.add(build_card_layout(current_card,
                            true, true, null, priority));
                }
            }
            layout_major.addView(build_group_layout(current_cards));

            if (pick_mode != PickMode.ONLY_MAJOR && group_number > 1)
            {
                LinearLayout solve_result_layout = solver.build_solve_result_layout(
                        current_group*3, 3, 0.5, 4, this
                );
                if (solve_result_layout != null)
                {
                    solve_result_layout.setVisibility(View.GONE);
                    lls.add(solve_result_layout);
                    Button tmp_bt = new Button(this);
                    tmp_bt.setText("展开本组分析");
                    tmp_bt.setOnClickListener(btn_on_click_listener);
                    bts.add(tmp_bt);
                    layout_major.addView(tmp_bt);
                    layout_major.addView(solve_result_layout);
                }
            }
        }

        if (pick_mode != PickMode.ONLY_MAJOR)
        {
            LinearLayout solve_result_layout = solver.build_solve_result_layout(
                    0, card_array.size(), 0.5, 4, this
            );
            if (solve_result_layout != null)
            {
                solve_result_layout.setVisibility(View.GONE);
                lls.add(solve_result_layout);
                Button tmp_bt = new Button(this);
                tmp_bt.setText("展开总体分析");
                tmp_bt.setOnClickListener(btn_on_click_listener);
                bts.add(tmp_bt);
                layout_major.addView(tmp_bt);
                layout_major.addView(solve_result_layout);
            }
        }



/*
        final Button button = new Button(this);
        button.setText("afjfasj");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == button)
                {
                    Toast.makeText(ArrayThreeActivity.this, "请选择指示牌",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        //l.addView(button);
        TextView tv;
        tv.setTextColor(0xff);
        */
    }

    private LinearLayout build_card_layout(int card_index,
                                           boolean has_addi, boolean use_default
            , String addi_text, int addi_color)
    {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(ivs.get(card_index));
        TextView tv_name = new TextView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv_name.setLayoutParams(lp);
        tv_name.setText(card_array.get(card_index).card_name);
        tv_name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv_name.setTextColor(0xffffffff);
        tv_name.setTextSize(COMPLEX_UNIT_SP, 16);
        linearLayout.addView(tv_name);
        if (has_addi)
        {
            TextView addi = new TextView(this);
            addi.setLayoutParams(lp);
            addi.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            if (addi_color == 2)
            {
                addi.setText(use_default ? "很重要" : addi_text);
                addi.setTextColor(0xffff0000);
            }
            else if (addi_color == 1)
            {
                addi.setText(use_default ? "重要" : addi_text);
                addi.setTextColor(0xffffff00);
            }
            else
            {
                addi.setText(use_default ? "辅助" : addi_text);
                addi.setTextColor(0xffffffff);
            }

            addi.setTextSize(COMPLEX_UNIT_SP, 14);
            linearLayout.addView(addi);
        }
        return linearLayout;
    }

    private LinearLayout build_group_layout(ArrayList<LinearLayout> card_layouts)
    {
        int card_num = card_layouts.size();
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams major_lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //major_lp.gravity = Gravity.CENTER_HORIZONTAL | Gravity.FILL;
        linearLayout.setLayoutParams(major_lp);
        LinearLayout.LayoutParams tv_lp = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        tv_lp.weight = 1;
        LinearLayout.LayoutParams ll_lp = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        ll_lp.leftMargin = dpToPx(10, ArrayThreeActivity.this);
        ll_lp.rightMargin = dpToPx(10, ArrayThreeActivity.this);
        if (card_num == 1)
        {
            TextView tv1 = new TextView(this);
            TextView tv2 = new TextView(this);
            tv1.setText("");
            tv2.setText("");
            tv_lp.leftMargin = dpToPx(10, ArrayThreeActivity.this);
            tv_lp.rightMargin = dpToPx(10, ArrayThreeActivity.this);
            tv1.setLayoutParams(tv_lp);
            tv2.setLayoutParams(tv_lp);
            LinearLayout ll1 = card_layouts.get(0);
            ll_lp.weight = 1;
            ll1.setLayoutParams(ll_lp);
            linearLayout.addView(tv1);
            linearLayout.addView(ll1);
            linearLayout.addView(tv2);
        }
        else if (card_num == 2)
        {
            TextView tv1 = new TextView(this);
            TextView tv2 = new TextView(this);
            tv1.setText("");
            tv2.setText("");
            tv_lp.leftMargin = dpToPx(5, ArrayThreeActivity.this);
            tv_lp.rightMargin = dpToPx(5, ArrayThreeActivity.this);
            tv1.setLayoutParams(tv_lp);
            tv2.setLayoutParams(tv_lp);
            LinearLayout ll1 = card_layouts.get(0);
            LinearLayout ll2 = card_layouts.get(1);
            ll_lp.weight = 2;
            ll1.setLayoutParams(ll_lp);
            ll2.setLayoutParams(ll_lp);
            linearLayout.addView(tv1);
            linearLayout.addView(ll1);
            linearLayout.addView(ll2);
            linearLayout.addView(tv2);
        }
        else if (card_num == 3)
        {
            LinearLayout ll1 = card_layouts.get(0);
            LinearLayout ll2 = card_layouts.get(1);
            LinearLayout ll3 = card_layouts.get(2);
            ll_lp.weight = 1;
            ll1.setLayoutParams(ll_lp);
            ll2.setLayoutParams(ll_lp);
            ll3.setLayoutParams(ll_lp);
            linearLayout.addView(ll1);
            linearLayout.addView(ll2);
            linearLayout.addView(ll3);
        }
        return linearLayout;
    }

    public void detail_on_click(View view)
    {
        AlertDialog dialog = new AlertDialog.Builder(ArrayThreeActivity.this).create();
        dialog.setTitle("关于无牌阵三张");
        dialog.setMessage("无牌阵三张使用22张大牌或者78张牌推测，可以适用于任何可占问题，每个位置不代表具体含义，但是要求占卜师能力水平较高，可以依据牌型给出自己的观点。\n" +
                "\n无牌阵三张以中间牌为主，两侧修正；以大牌为主，小牌修正\n" +
                "\n如果用到切牌还应该看是否和三张牌有互相加强或者削弱的情况，注意元素的增强和削弱。\n");

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "知道了",
                (DialogInterface.OnClickListener) null);
        dialog.show();
    }


    public static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}

package aurora.universe.universes.tarotmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;

/**
 * Created by miguelduarte on 4/9/2020.
 */

public class ReadytoStartActivity extends AppCompatActivity {
    private Spinner card_array_spinner;
    private LinearLayout layout_enable_muti_times;
    private CheckBox enable_multi_times;
    private Spinner multi_times_spinner;
    private RadioButton no_addition_rb;
    private RadioButton indicator_rb;
    private RadioButton cut_rb;
    private CheckBox discard_ori_cb;
    private RadioButton pm_rb1;
    private RadioButton pm_rb2;
    private RadioButton pm_rb3;

    private final int CODE_IND = 10;
    private final int CODE_NOR = 20;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_to_start);
        card_array_spinner = (Spinner)findViewById(R.id.card_array_spinner);
        layout_enable_muti_times = (LinearLayout) findViewById(R.id.layout_enable_multi_times);
        enable_multi_times = (CheckBox) findViewById(R.id.rd_cb_use_multi);
        multi_times_spinner = (Spinner)findViewById(R.id.rd_s_multi_times);
        no_addition_rb = (RadioButton) findViewById(R.id.rd_rb_1);
        indicator_rb = (RadioButton) findViewById(R.id.rd_rb_2);
        cut_rb = (RadioButton) findViewById(R.id.rd_rb_3);
        discard_ori_cb = (CheckBox) findViewById(R.id.rd_cb_discard_ori);
        pm_rb1 = (RadioButton) findViewById(R.id.rd_rbm_1);
        pm_rb2 = (RadioButton) findViewById(R.id.rd_rbm_2);
        pm_rb3 = (RadioButton) findViewById(R.id.rd_rbm_3);


        enable_multi_times.setEnabled(false);
        multi_times_spinner.setEnabled(false);
        indicator_rb.setEnabled(false);
        cut_rb.setEnabled(false);

        enable_multi_times.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    multi_times_spinner.setEnabled(true);
                else
                    multi_times_spinner.setEnabled(false);
            }
        });

        card_array_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 || position == 2)
                {
                    no_addition_rb.toggle();
                    indicator_rb.setEnabled(false);
                    cut_rb.setEnabled(false);
                }
                else
                {
                    indicator_rb.setEnabled(true);
                    cut_rb.setEnabled(true);
                }

                if (position == 1)
                {
                    layout_enable_muti_times.setVisibility(View.VISIBLE);
                    enable_multi_times.setEnabled(true);
                }
                else
                {
                    layout_enable_muti_times.setVisibility(View.GONE);
                    enable_multi_times.setEnabled(false);

                }

                if (position == 2)
                {
                    pm_rb3.toggle();
                    pm_rb1.setEnabled(false);
                    pm_rb2.setEnabled(false);
                }
                else
                {
                    pm_rb1.setEnabled(true);
                    pm_rb2.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void start_on_click(View view)
    {
        Addition addition = no_addition_rb.isChecked() ? Addition.NONE : (
                indicator_rb.isChecked() ? Addition.INDICATOR : Addition.CUT
                );
        if (addition == Addition.INDICATOR)
        {
            Intent intent = new Intent(ReadytoStartActivity.this, SelectIndicatorActivity.class);
            startActivityForResult(intent, CODE_IND);
        }
        else
        {
            onActivityResult(CODE_NOR, 1, null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int indicator_card_num = 1;
        if (requestCode == CODE_IND)
        {
            if (resultCode == 0)
                return;
            indicator_card_num = resultCode + 62;
        }

        Addition addition = no_addition_rb.isChecked() ? Addition.NONE : (
                indicator_rb.isChecked() ? Addition.INDICATOR : Addition.CUT
        );
        int selected_card_array = card_array_spinner.getSelectedItemPosition();
        boolean discard_ori = discard_ori_cb.isChecked();
        PickMode pick_mode = pm_rb1.isChecked() ? PickMode.ALL : (
                pm_rb2.isChecked() ? PickMode.ONLY_MAJOR : PickMode.ONLY_MINOR);
        Intent go_to_result = null;

        switch (selected_card_array)
        {
            case 0:
                go_to_result = new Intent(ReadytoStartActivity.this, ArraySingleActivity.class);
                break;
            case 1:
                go_to_result = new Intent(ReadytoStartActivity.this, ArrayThreeActivity.class);
                int group_num = enable_multi_times.isChecked() ?
                        multi_times_spinner.getSelectedItemPosition() + 2 : 1;
                go_to_result.putExtra("group_number", group_num);
                break;
            case 2:
                go_to_result = new Intent(ReadytoStartActivity.this, ArrayLoveWActivity.class);
                break;
            case 3:
                go_to_result = new Intent(ReadytoStartActivity.this, ArrayWorksActivity.class);
                break;
            case 4:
                go_to_result = new Intent(ReadytoStartActivity.this, ArrayChoosetActivity.class);
                break;
            case 5:
                go_to_result = new Intent(ReadytoStartActivity.this, ArrayYesnoActivity.class);
                break;
        }

        if (go_to_result != null)
        {
            go_to_result.putExtra("discard_ori", discard_ori);
            go_to_result.putExtra("pick_mode", pick_mode.ordinal());
            go_to_result.putExtra("addition_type", addition.ordinal());
            if (addition == Addition.INDICATOR)
                go_to_result.putExtra("indicator_number", indicator_card_num);
            startActivity(go_to_result);
        }
    }
}

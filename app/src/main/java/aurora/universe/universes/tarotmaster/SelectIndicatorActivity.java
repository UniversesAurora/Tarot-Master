package aurora.universe.universes.tarotmaster;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

/**
 * Created by miguelduarte on 4/8/2020.
 */

public class SelectIndicatorActivity extends AppCompatActivity
{
    private int[] image_id = new int[]
                {
                    R.drawable.c63, R.drawable.c64, R.drawable.c65, R.drawable.c66,
                    R.drawable.c67, R.drawable.c68, R.drawable.c69, R.drawable.c70,
                    R.drawable.c71, R.drawable.c72, R.drawable.c73, R.drawable.c74,
                    R.drawable.c75, R.drawable.c76, R.drawable.c77, R.drawable.c78,
                };
    private ImageSwitcher imageSwitcher;
    private Gallery gallery;
    private TextView card_name_tv;
    private String[] card_names = new String[]{
            "圣杯国王", "圣杯王后", "圣杯骑士", "圣杯侍者",
            "权杖国王", "权杖王后", "权杖骑士", "权杖侍者",
            "星币国王", "星币王后", "星币骑士", "星币侍者",
            "宝剑国王", "宝剑王后", "宝剑骑士", "宝剑侍者",
    };
    private float down_x;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_indicator);
        intent = getIntent();
        gallery = (Gallery)findViewById(R.id.g_si);
        imageSwitcher = (ImageSwitcher)findViewById(R.id.is_si);
        card_name_tv = (TextView)findViewById(R.id.si_card_name_tv);
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(SelectIndicatorActivity.this);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                return imageView;
            }
        });
        imageSwitcher.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        down_x = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        float upX = event.getX();
                        int cp = gallery.getSelectedItemPosition();
                        if (upX > down_x)
                        {
                            if (cp == 0)
                            {
                                cp = image_id.length - 1;
                            } else
                            {
                                --cp;
                            }
                        }
                        if (upX < down_x)
                        {
                            if (cp == image_id.length - 1)
                            {
                                cp = 0;
                            } else
                            {
                                ++cp;
                            }
                        }
                        imageSwitcher.setImageResource(image_id[cp]);
                        gallery.setSelection(cp, true);
                        card_name_tv.setText(card_names[cp]);
                        break;
                }
                return true;//返回true才能touch有效
            }
        });

        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return image_id.length;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ImageView imageView;
                if (convertView == null)
                {
                    imageView = new ImageView(SelectIndicatorActivity.this);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setLayoutParams(new Gallery.LayoutParams(63, 100));
                    TypedArray typedArray = obtainStyledAttributes(R.styleable.Gallery1);
                    imageView.setBackgroundResource(typedArray.getResourceId(
                            R.styleable.Gallery1_android_galleryItemBackground, 0));
                    typedArray.recycle();
                    imageView.setPadding(5, 0, 5, 0);
                }
                else
                {
                    imageView = (ImageView)convertView;
                }
                imageView.setImageResource(image_id[position]);
                return imageView;
            }
        };

        gallery.setAdapter(adapter);
        gallery.setSelection(0);
        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageSwitcher.setImageResource(image_id[position]);
                card_name_tv.setText(card_names[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Toast.makeText(SelectIndicatorActivity.this, "请选择指示牌",
                Toast.LENGTH_LONG).show();

    }

    public void si_ok_on_click(View view)
    {
        setResult(gallery.getSelectedItemPosition()+1, intent);
        finish();
    }

    public void si_detail_on_click(View view)
    {
        AlertDialog dialog = new AlertDialog.Builder(SelectIndicatorActivity.this).create();
        dialog.setTitle("关于指示牌的选择");
        dialog.setMessage("目前坊间的中文塔罗书籍似乎很少提到指示牌的概念，但是，指示牌是塔罗占卜中颇为重要的一环。要能熟练的使用指示牌并不是一蹴可几，而是需要经验的累积，不过，一旦学会使用指示牌，对于塔罗占卜可以如虎添翼。\n" +
                "\n" +
                "未必每个牌阵都用得到指示牌，然而，在某些牌阵（特别是塞尔特十字牌阵）指示牌显得特别重要，因为它能显示出当事人的基本个性，而我们知道，个性影响一个人特别重大，不同性格的人在同样的状况下，所采取的行动不会相同，他所期望的结果也未必一样。指示牌就是显示出当事人与他面临的状况之间的交互作用。\n" +
                "\n" +
                "　　举例来说，一位有钱币骑士性格的人来问事业，我们选择钱币骑士做为指示牌。在最终结果，得到圣杯十，看起来似乎不错，至少在情感上能获得极大的支持与满足，但那真的是他想要的吗？他是钱币骑士，他要的是庞大的事业与金钱，情感对他来说只是次要。这样一来，圣杯十这个结果反而不如大多数的钱币牌来得好。\n" +
                "\n" +
                " 　　在大部分的情况下，可以使用宫廷牌作为指示牌。困难的地方是，该如何在１６张宫廷牌中选择出最能代表当事人的那一张呢？人的性格是如此的复杂，一个人在不同的情境下，所扮演的角色又不尽相同，那么该如何选择？首先，我们必须对１６张的宫廷牌有透彻的了解。在日常生活中实际观察旁人的性格，并帮他们归类。想一想，你的父母亲分别属于哪一张宫廷牌？你的兄弟姊妹呢？再看看你的情人或朋友，应该不难猜出来。另外注意一点，宫廷牌是以性格来决定，而非性别。国王未必都是男的，皇后未必都是女的，只是国王阳性/刚性面明显，是男性的机率比较大，并非他一定是男性。反之亦然。\n" +
                "\n" +
                " 　　对宫廷牌较为熟悉后，可以更进一步观察身旁亲友在不同情境下又扮演哪些角色呢。假设有个温柔敏感的三十岁女性，在大部分情形下都扮演圣杯皇后的角色，然而其实她在感情上曾受过极大的伤害，因而对所有追求者都不假辞色的严拒，那么在为她做感情占卜时，选择宝剑皇后做为指示牌才适当。再举个例子，假设一位十九岁的大学男生，性情活泼外向，在家里扮演的是权杖侍者。然而他担任学校某社团的社长，在领导时展现出超龄的决断力、理智与专业，那么他在为社团的发展做占卜时，我们可以选择宝剑国王做为指示牌。\n" +
                "\n" +
                " 　　指示牌通常由占卜师在决定牌阵时一并为问卜者选出，而富有经验的占卜师可以在与客人完全不熟的情况下，凭着对人敏锐的观察力，选出正确的指示牌。然而，对大多数的塔罗玩家来说，要在第一时间内选出正确的指示牌并不容易，且选错的机率很高，因此，有些替代的方案。最简单的就是用切牌代替，若切出的是宫廷牌，很可以就代表当事人的个性，不过并不是每次都会切出宫廷牌，因此这个方式并不是最理想的。另一个方式是占卜师先选出较有可能的几张，再让问卜者抽出代表他的那张。这个方式的准确率还算理想，可以采行。\n\n\n" +
                "在占卜上，宫廷牌可代表：\n" +
                "\n" +
                "　　1. 某个人(可能是问卜者或他人)\n" +
                "　　2. 某个人的某特质(可能是问卜者或他人)\n" +
                "　　3. 某个状况\n" +
                "\n" +
                "　　伟特宫廷牌是国王、王后、骑士和侍者，克劳力扥特牌系统是骑士、王后、王子、公主，其他版本亦略有出入，但是基本意义大致类似，在此采用伟特系统解说。\n" +
                "\n" +
                "　　我们可以把国王、王后、骑士和侍者联想成一家人。国王是父亲，王后是母亲，骑士是儿子，侍者是女儿。他们都属于同一个家族，但是因为身份不同而表现出不同的特质。譬如圣杯家族一家人都具备水要素特质，情感丰富而感性，重视人际关系。圣杯父亲是一位成熟稳重的慈父，圣杯母亲对儿女无条件的牺牲奉献，圣杯儿子是个浪漫风流的小诗人，圣杯女儿则像清秀佳人安雪丽般的充满幻想。\n" +
                "\n" +
                "　　我们也可以将国王看做皇帝牌的小牌版本。四个国王都是成熟的男性，他们是王国的领导者，也是掌握大权的人。国王表现该牌组的阳性特质，积极且正面。由于已经成熟，所以他们尽管行动力强，却思虑周密，不像骑士那样急躁。\n" +
                "\n" +
                "　　王后是皇后和女祭司的小牌版本。四个王后都是成熟女性，她们是慈祥的抚育者，就像母亲一般，喜欢照顾别人，表现该牌组的阴柔特质。\n" +
                "\n" +
                "　　骑士是尚未达到心智成熟境界的成年男人，通常在30岁以下，有些系统则认为骑士大约是25-40岁。骑士表现该牌组的过度状况，可能是好，也可以是坏。例如钱币的土要素表现过度会过于谨慎或者物质主义太浓厚，但工作认真的他很有可能在事业上成功；圣杯的水要素表现过度会过于浪漫，不切实际，多愁善感，但也可能成为绝佳的诗人。有人认为骑士牌与战车类似。骑士们驾着马，行动快速，因此在占卜中也代表状况的改变，或是旅行。\n" +
                "\n" +
                "　　侍者是青少年男女或小孩，他们是牌组中的学生，喜欢探索新事物，表现该牌组最纯真原始的特质，就像刚踏上旅程的愚人。在占卜上，侍者的另一个重要意义是讯息。例如钱币侍者带来的是金钱方面的讯息，权杖侍者带来行动方面的讯息。\n" +
                "\n" +
                "　　需要特别注意的是，性别和年龄只是大致的参考，可能有例外。在占卜时应以当事人的性格为准。比方说，在极少数的状况下，国王可能代表一位成熟女性，但是那位女性必定表现出国王权威的领导特质。又例如某位男性的阴柔面多且从事护士工作，用王后牌形容他会比国王或骑士来得恰当。" +
                "综合以上四点，我们可以归纳出以下结论：\n" +
                "\n" +
                "表一\n" +
                "国王-成熟男性(40岁以上)-领导者。掌权者。 -阳性\n" +
                "王后-成熟女性(25岁以上)-看护者。抚育者。 -阴性\n" +
                "骑士-心智尚未成熟的男性(25-40岁)-激进份子。 -过度（特殊意义：改变 / 旅行）\n" +
                "侍者-年轻男女或小孩(25岁以下)-学生。孩童。 -原始（特殊意义：讯息 / 新开始）\n" +
                "\n" +
                "表二\n" +
                "权杖-火(阳)-热忱与行动-行动。精力。热情。创造。创意。积极。冒险。\n" +
                "圣杯-水(阴)-感情与直觉-情感。爱。直觉。想像力。人际关系。感性。内省。幻想。\n" +
                "宝剑-风(阳)-智力与思考-伤害。麻烦。困难。冲突。心智活动。沟通。思考。智慧。\n" +
                "钱币-土(阴)-物质与肉体-健康。工作。金钱。财产。现实。建设。天份。大自然。\n" +
                "\n" +
                "　　总归来说，十六张宫廷牌就是这四乘以四的组合。国王、王后、骑士、侍者和权杖、圣杯、宝剑、钱币互相组合之后，可以得出下表【注】。\n" +
                "\n" +
                "　　　　　　权杖（火）　圣杯（水）　宝剑（风）　钱币（土）\n" +
                "国王（火）　　火之火　　　水之火　　　　风之火　　　土之火\n" +
                "王后（水）　　火之水　　　水之水　　　　风之水　　　土之水\n" +
                "骑士（风）　　火之风　　　水之风　　　　风之风　　　土之风\n" +
                "侍者（土）　　火之土　　　水之土　　　　风之土　　　土之土\n" +
                "\n" +
                "　　经过以上的解释，就知道16张宫廷牌绝对是井然有序，条理分明的。在占卜上，宫廷牌通常代表某个对问题有影响力的人。也可能是某个人的某特质。譬如张三平常在家中是圣杯骑士，但是他跟女朋友吵架的时候，就变得激烈而冷酷，牌面上可能就会出现宝剑骑士来代表他这个特质。在少数的情形下，宫廷牌可能代表一个状况或事件。下表列出Golden Dawn的Book T系统，和其后的伟特、克劳力、卡斯(Paul Foster Case)所采用的牌义，仅供参考。\n" +
                "\n" +
                "【注】 由于宫廷牌与四要素对应系统流派众多，在此暂取Golden Dawn系统，但每个人都可以自行选择自己觉得最适切的系统。以下列出三种系统供参考：\n" +
                "系统一：国王（火）、王后（水）、骑士（风）、侍者（土）----(Golden Dawn系统)\n" +
                "系统二：国王（风）、王后（水）、骑士（火）、侍者（土）\n" +
                "系统三：国王（土）、王后（水）、骑士（火）、侍者（风）");

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "知道了",
                (DialogInterface.OnClickListener) null);
        dialog.show();
    }

}

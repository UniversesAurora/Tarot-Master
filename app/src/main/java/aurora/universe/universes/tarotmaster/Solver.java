package aurora.universe.universes.tarotmaster;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * Created by miguelduarte on 4/8/2020.
 */

public class Solver
{
    protected ArrayList<Card> card_array;

    public Solver(ArrayList<Card> card_array)
    {
        this.card_array = card_array;
    }


    public static boolean has_info(ArrayList<ArrayList> solve_res)
    {
        ArrayList<ArrayList> element_res = solve_res.get(0);
        ArrayList<Integer> number_res = solve_res.get(1);
        ArrayList<Element> element_max = element_res.get(0);
        ArrayList<Element> element_none = element_res.get(1);

        return !(number_res.size() == 0 && element_max.size() == 0 && element_none.size() == 0);
    }

    public static ArrayList<Boolean> has_info_all(ArrayList<ArrayList> solve_res)
    {
        ArrayList<ArrayList> element_res = solve_res.get(0);
        ArrayList<Integer> number_res = solve_res.get(1);
        ArrayList<Element> element_max = element_res.get(0);
        ArrayList<Element> element_none = element_res.get(1);
        ArrayList<Boolean> res = new ArrayList<>();

        res.add(!(element_max.size() == 0));
        res.add(!(element_none.size() == 0));
        res.add(!(number_res.size() == 0));

        return res;
    }

    public LinearLayout build_solve_result_layout(
            int from, int num, double persent, double min, Context context
    )
    {
        ArrayList<ArrayList> all_result = solve(from, num, persent, min);
        if (!has_info(all_result))
            return null;
        ArrayList<Boolean> all_has_info = has_info_all(all_result);
        ArrayList<ArrayList> element_res = all_result.get(0);
        ArrayList<Integer> number_res = all_result.get(1);
        ArrayList<Element> element_max = element_res.get(0);
        ArrayList<Element> element_none = element_res.get(1);

        LinearLayout major_ll = new LinearLayout(context);
        major_ll.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams major_lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        major_ll.setLayoutParams(major_lp);
        major_ll.setPadding(ArrayThreeActivity.dpToPx(5, context),
                ArrayThreeActivity.dpToPx(10, context), ArrayThreeActivity.dpToPx(5, context),
                ArrayThreeActivity.dpToPx(20, context));

        if (all_has_info.get(0) || all_has_info.get(1))
        {
            TextView tt1 = new TextView(context);
            LinearLayout.LayoutParams tt1_lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tt1.setLayoutParams(tt1_lp);
            tt1.setText("本组元素分析");
            tt1.setPadding(0, ArrayThreeActivity.dpToPx(10, context),
                    0, ArrayThreeActivity.dpToPx(5, context));
            tt1.setTextColor(0xffffffff);
            tt1.setTextSize(COMPLEX_UNIT_SP, 20);
            major_ll.addView(tt1);

            if (all_has_info.get(0))
            {
                TextView tt2 = new TextView(context);
                LinearLayout.LayoutParams tt2_lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tt2.setLayoutParams(tt2_lp);
                tt2.setText("主要元素");
                tt2.setTextColor(0xffffffff);
                tt2.setTextSize(COMPLEX_UNIT_SP, 18);
                major_ll.addView(tt2);

                for (Element element : element_max)
                {
                    major_ll.addView(build_show_info_layout(
                            element.get_name(), element.get_intro(), context
                    ));
                }

                if (element_max.size() == 2)
                {
                    TextView tt3 = new TextView(context);
                    LinearLayout.LayoutParams tt3_lp = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    tt3.setLayoutParams(tt3_lp);
                    tt3.setText("元素加成与抵消");
                    tt3.setTextColor(0xffffffff);
                    tt3.setTextSize(COMPLEX_UNIT_SP, 14);
                    major_ll.addView(tt3);

                    major_ll.addView(build_show_info_layout(
                            element_max.get(0).get_name() + element_max.get(1).get_name(),
                            Element.element_muti_solve(element_max.get(0), element_max.get(1)),
                            context
                    ));
                }
            }

            if (all_has_info.get(1))
            {
                TextView tt2 = new TextView(context);
                LinearLayout.LayoutParams tt2_lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tt2.setLayoutParams(tt2_lp);
                tt2.setText("缺失元素");
                tt2.setTextColor(0xffffffff);
                tt2.setTextSize(COMPLEX_UNIT_SP, 18);
                major_ll.addView(tt2);

                for (Element element : element_none)
                {
                    major_ll.addView(build_show_info_layout(
                            element.get_name(), element.get_intro(), context
                    ));
                }
            }
        }
        if (all_has_info.get(2))
        {
            TextView tt1 = new TextView(context);
            LinearLayout.LayoutParams tt1_lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tt1.setLayoutParams(tt1_lp);
            tt1.setText("本组数字分析");
            tt1.setPadding(0, ArrayThreeActivity.dpToPx(10, context),
                    0, ArrayThreeActivity.dpToPx(5, context));
            tt1.setTextColor(0xffffffff);
            tt1.setTextSize(COMPLEX_UNIT_SP, 20);
            major_ll.addView(tt1);

            for (int number : number_res)
            {
                major_ll.addView(build_show_info_layout(
                        Integer.toString(number), number_means(number), context
                ));
            }
        }

        return major_ll;
    }

    public LinearLayout build_show_info_layout(
            String title, String content, Context context)
    {
        LinearLayout major_ll = new LinearLayout(context);
        major_ll.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams major_lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        major_ll.setLayoutParams(major_lp);
        major_ll.setGravity(Gravity.START | Gravity.TOP | Gravity.FILL);
        major_ll.setPadding(0, 0, 0, ArrayThreeActivity.dpToPx(10, context));

        TextView title_tv = new TextView(context);
        TextView content_tv = new TextView(context);
        LinearLayout.LayoutParams tv_lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        title_tv.setLayoutParams(tv_lp);
        title_tv.setText(title);
        title_tv.setPadding(0, 0, ArrayThreeActivity.dpToPx(10, context), 0);
        title_tv.setTextColor(0xffffffff);
        title_tv.setTextSize(COMPLEX_UNIT_SP, 60);
        content_tv.setLayoutParams(tv_lp);
        content_tv.setText(content);
        content_tv.setTextColor(0xffffffff);
        content_tv.setTextSize(COMPLEX_UNIT_SP, 16);

        major_ll.addView(title_tv);
        major_ll.addView(content_tv);
        return major_ll;
    }


    public ArrayList<ArrayList> solve(int from, int num, double persent, double min)
    {
        ArrayList<ArrayList> res = new ArrayList<>();
        res.add(element_generic_solve(from, num));
        res.add(number_generic_solve(from, num, persent, min));
        return res;
    }

    public ArrayList<ArrayList> element_generic_solve(int from, int num)
    {
        int[] element = {0,0,0,0,0};
        int max_element_freq = 0;
        ArrayList<ArrayList> result = new ArrayList<>();
        ArrayList<Element> max_temp = new ArrayList<>();
        ArrayList<Element> none_temp = new ArrayList<>();

        for (int i = from; i < from + num; i++)
        {
            Card card = card_array.get(i);
            if (card.element != Element.NONE)
                element[Element.to_index(card.element)]++;
            if (card.palace_element != Element.NONE)
                element[Element.to_index(card.palace_element)]++;
        }

        for (int i = 1; i < element.length; i++)
        {
            if (element[i] > max_element_freq)
            {
                max_element_freq = element[i];
                max_temp.clear();
                max_temp.add(Element.from_index(i));
            }
            else if (element[i] == max_element_freq)
            {
                max_temp.add(Element.from_index(i));
            }

            if (element[i] == 0)
                none_temp.add(Element.from_index(i));
        }

        if (max_temp.size() != 2 && max_temp.size() != 1)
            max_temp.clear();

        if (none_temp.size() > 2)
            none_temp.clear();


        result.add(max_temp);
        result.add(none_temp);
        return result;
    }



    public ArrayList<Integer> number_generic_solve(int from, int num, double persent, double min)
    {
        int max = 1;
        int max_number_freq = 0;
        int max_freq = 0;
        int[] number = {0,0,0,0,0,0,0,0,0,0,0};
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = from; i < from + num; i++)
        {
            Card card = card_array.get(i);
            if (card.is_major && card.in_group_number > 0 && card.in_group_number < 11)
            {
                number[card.in_group_number]++;
            }
            else if (!card.is_palace && !card.is_major)
            {
                number[card.in_group_number]++;
            }
        }

        for (int i = 1; i < number.length; i++)
        {
            if (number[i] > max_number_freq)
            {
                max = i;
                max_number_freq = number[i];
                max_freq = 1;
            }
            else if (number[i] == max_number_freq)
            {
                max_freq++;
            }

            if (number[i] >= min || number[i] > (int)(num * persent))
            {
                result.add(i);
            }
        }

        if (result.size() == 0 && max_freq == 1)
            result.add(max);

        return result;
    }

    public static String number_means(int number)
    {
        switch (number)
        {
            case 1:
                return "数字1代表了神性，万物的开始和起源，创造的最最原始的力量。1表示统一、自我、一致、独特、完整和无限。就像种子一样，1也有自发主动、有意图的、有动机和不变的意思。Emerald Tablet of Hermes 有说道：“所有的事物都是从1开始的，通过对1的冥想就能得知，有1而生2，因2而生万物。”\n\n" +
                        "想象早上你起床时，突然冒出一个念头——想要寻求一些不寻常的刺激事情做——对身体具有挑战力的一日游——爬山。这就是Ace，如果是权杖，就代表你对自己的念头充满了热情和活力；若是圣杯，则是对外界处于一种内心溢出式的情感；宝剑就是指出了面对挑战的决心；星币则是呼吸新鲜空气和锻炼身体以改善肌肉质量。";
            case 2:
                return "数字2包含了“两点一线”的原理。同时表达了神性在镜子中的另外一面，这就是二元性，也就是分离和对立。毕达哥拉斯将二元理论的内容定义为互相对立（经常出现于大牌的图像中）。二元性表示平衡、选择和决定。他是对分离的想象和联系。基础的二元性包括：奇数和偶数、阴和阳、光和暗、上帝和魔鬼、主动和被动、生命和死亡、快乐和悲伤、健康和疾病等。\n\n" +
                        "在2中，你面对了一些选项，如去哪一座山、走哪一条路线（权杖）？独自去还是找个伴（圣杯）？第二个想法使你陷入进退两难的境地：你真的想要去面对这一挑战还是回到家里玩玩电脑上上网（宝剑）？然后就是实际层面的考虑，需要带什么去呢？水？食物？小用品？（星币）不管选择了哪一条路线——阳关道还是独木桥，对你来说是重要的，因为你必须在天黑前下山，而你让朋友开车送你去并让你在山下下车并在山的另一头接你那使你回来时方便一点。";
            case 3:
                return "三点构成一个三角，形成一个平面。作为第一个切实的形式，数字3是第一个象征真实的数字。数字3代表了多样性，有一些文明中只有1和2这两个数字。在数字3中我们可以发现多个“三位一体”：上帝的三面，月亮的三个阶段、基督教的三位一体、红黄蓝三原色、身心灵。代表事物的发展过程还有：理论－对立理论－综合理论。开始－中期－结束，主－谓－宾，这些都显示了经验的不可分割性和整体连续性。这些都引发一种综合和调和，当相对立的两个事物结合在一起，新生事物便得以创造。所以数字3通常被认为是创造、灵魂和精神。\n\n" +
                        "现在就来到了3，开始行动，开始爬山。权杖是乐观的，视野得到了开阔，你看到了即将到来的一天的冒险和成功的对策。圣杯里，你在途中翩翩起舞、感受山上的环境，陶醉在美丽之中。宝剑则将你带入荆棘丛中，使你磨破脚皮、痛苦的跌打滚爬。星币是沉稳的，你停下来拍照、赏花，赞美造物主的工作。";
            case 4:
                return "数字4在平面基础上增加了一个高度，所以形成了第一个稳定的立体结构——代表稳定和永久的四面体。数字4和物质世界和物质存在有关——4元素、四个方向、四个季节等。4是代表均衡的基础，因为4是（2＋2）的产物。代表上帝之名（Tetragrammaton）的四个字母YHVH代表了神秘和力量。另外我们还能找到与4相关的神秘学的四种美德、四福音、天堂的四条河。\n\n" +
                        "在4里，你到达了绿色的高原，开始了中午的休憩。权杖使你庆祝上午的成功，圣杯则是在你想要继续出发之前，倚靠在树旁休息。宝剑则是经历了坎坷之路使你精疲力竭，所以你需要躺下睡一觉来恢复。星币则使你紧贴舒适的高原环境，采集一些岩石和植物。";
            case 5:
                return "五芒星是一个能够被无止境的画下去的结构。代表了运动，时间和改变。数字5代表生命、活力和人性，因为我们手上有五根手指；我们身体伸展开时一个头、两只手和两只脚也是构成五个方向；另外还是我们的五官。5是耶稣的创伤，所以同时也代表了痛苦和受伤。同时也是数字4的稳定结构的改变打破，创造的一个转折点。作为一个典范，他超越了4元素，代表了生命力和新的可能性。\n\n" +
                        "来到了5，你必须继续走下去，这里需要攀岩，测是你能力的时候到了。在4的满足和安定之后，随之而来的拾危机，尤指精神和心灵上的。权杖的斗争在于哪一种方式是攀爬的最佳方式。圣杯则是你哀叹攀爬过程中意外洒落的水。宝剑是你开始忍不住责备给你错误指引的朋友，你的幸灾乐祸超越伤痛，而受你责备的人则想回家了。星币集中于身体方面，你觉得又冷又饿，但你仍然继续前行，寻找抵达山顶的直径。";
            case 6:
                return "数字6是毕达哥拉斯的第二个“完美数字”，1＋2＋3的相加是那一个完美的创造在6天得以完成。6代表意识的显露和净化。六芒星是由两个拥有互相对立特质（火和水）的三角形构成的，给身心实质带来整体化的认识，从而促成和谐。\n\n" +
                        "6是每一个牌组的最高点，你到达了顶点。在权杖里，你为自己的成功感到骄傲，受到朋友所预期的钦赞。圣杯里，随着成功的到来，你不由联系到自己的记忆，你为自己的所爱挑选了纪念品。在宝剑，你保持理智的客观性，你只是抵达了中点，后面还有危险的道路，而你仍需要去经历。星币则是你在衡量后面的路还需要花费你多大的精力；或者说你发现有人在少量的发放食物和水，但你不能依靠别人的施舍混日子，因为你必须在天黑前下山。";
            case 7:
                return "数字7是代表胜利和荣耀的数字，因为他无法被共同拥有。他同时代表了动机和机会——胜利与轻微的混乱。数字7也能够在很多地方找到：七个音符、七色彩虹、七行星、七元音、一周七天等，这些都是将不同的事物整合到一个整体。上帝创造世界后的第七天为休息日，圣三角（精神灵魂）的总和加上四重世界（物质身体），这些都使数字7成为一个神圣的数字，代表了开始和唤醒的状态。由于包含了七行星的力量，7也代表了智慧。7同时也能在七宗罪、七圣礼里找到。7代表完美与完成但同时也能破坏这种完美。\n\n" +
                        "经过致高点6之后，7的到来是在更深一步测试你价值。你面对的是一个不确定且令人疑惧的挑战，一条看似简单的下山路可能充满令人害怕的东西。权杖里，你对抗着来自内心许多的声音，这些声音都在说：“你不可能做到的。”但这些声音反而使你更加坚定获取胜利的决心。圣杯里，你想象自己在到达成功后会得到一些什么奖品，尽管这么做可能有迷失于自我幻想的危险，但你还是通过这个来激励自己。在宝剑影响下，你将登山用的钢钉钉在了悬崖表面上，以方便自己，你给山崖留下了伤疤，但没人知道这是你干的。星币里，你在仔细考虑形势和检视地形，以寻找一个让自己放松的立足之地。";
            case 8:
                return "8从数字的图形上就能看出两个区域：世俗元素与上天的界限。作为八度音（古希腊哲学家毕达哥拉斯发现，如果将一根发出特定音符的弦对分成两半，一直对分下去的话就会得到七个不同的音，而第八个音符恰恰就是第一个音，只不过比原来的音高了一阶。）审判日一共八天与数字8有关，审判日过后一个更高秩序的物质世界建立。8代表向完成和最后的变动前进的过程。\n\n" +
                        "虽然在7中获取了胜利，但那已花费了你好多时间。在8里，你应该加快回家的步伐，但你必须先做一下判断自己的方向，并对自己的计划作下重新评估。权杖仅仅利用快速的爆发力飞过地面、穿过障碍和鸿沟。圣杯里，你从幻想中醒来，发现夜幕降临，你必须起身走向未知的路途。在宝剑，你陷入了困难，你就像是掉入了一个裂缝，使你感觉受到围困和无助。星币中里就像一个逐步有序的下山过程，你只要缓慢重复的移动脚步即可。";
            case 9:
                return "9是3的立方，是3的完美形式。作为个位数最后一个数字，9代表一个限度和界限，而且是力量和智慧。虽然和10相比，9的完成看起来仍旧缺少点什么，但9也能表示是一个循环的终点和完成。\n\n" +
                        "9代表了完成，你已经在天黑前到达了山底。权杖中，你在停车场，疲惫并为自己骄傲的等待朋友开车来接你。圣杯里你找到了一个可供休息的餐厅，你点了些食物并心满意足的享用。宝剑有下列一些选项：a）那不过是一场恶梦，b）你受伤了并被送到了医院，c）由于之前你陷入了裂缝中，现在你仍然没有被人发现。星币是你缓慢的安全的下了山，现在正在一个漂亮的花园，边吃葡萄边和小鸟聊天。";
            case 10:
                return "10和1在神秘学上是相同的。代表无限的、更高一层的统一。同时代表了一个目标的实现和结束。毕达哥拉斯认为，10是一个完整的和完美的数字。在卡巴拉理论中，一共有10个“源质发散体”，作为数字10的戒律，10代表了公正清廉和切实执行。解决完善了数字9当中的缺陷。\n\n" +
                        "10是一个结局，所谓完成的效果和影响。经过了一整天，权杖中朋友忘了来接你，所以你只能带着沿途拣到的那些棍子而徒步走回家。圣杯里，你带着礼物、纪念品回到了家，在家里和亲人一同欢庆。宝剑中可不是那么好，可能以后再也再也不会去那里了。在星币的最后，你将自己的历程写成了故事，告诉了大家，你的事迹也将对下一代带来莫大的益处。";
            default:
                return "";
        }
    }
}

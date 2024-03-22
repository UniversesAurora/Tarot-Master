package aurora.universe.universes.tarotmaster;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by miguelduarte on 4/7/2020.
 */

public class Card
{
    public final int number;
    public final boolean is_major;
    public final Group group;
    public final Element element;
    public final int in_group_number;
    public final boolean is_palace;
    public final Palace palace;
    public final Element palace_element;

    public final boolean is_correct_orientation;

    public final String card_name;
    public final String card_explanation;
    public final String mean_correct_ori;
    public final String mean_incorrect_ori;


    public Card(int number, int ori, Context context)
    {
        if (number < 1 || number > 78)
            number = 1;
        this.number = number;

        if (number <= 22)
        {
            this.is_major = true;
            this.is_palace = false;

            this.group = Group.NONE;
            this.element = Element.NONE;
            this.in_group_number = number - 1;
            this.palace = Palace.NONE;
            this.palace_element = Element.NONE;
        }
        else if (number <= 62)
        {
            this.is_major = false;
            this.is_palace = false;

            int index = number - 23;
            int igroup = index / 10 + 1;

            this.group = Group.from_index(igroup);
            this.element = Element.from_group(this.group);
            this.in_group_number = index % 10 + 1;
            this.palace = Palace.NONE;
            this.palace_element = Element.NONE;
        }
        else
        {
            this.is_major = false;
            this.is_palace = true;

            int index = number - 63;
            int igroup = index / 4 + 1;
            int ipalace = index % 4 + 1;

            this.group = Group.from_index(igroup);
            this.element = Element.from_group(this.group);
            this.in_group_number = ipalace;
            this.palace = Palace.from_index(ipalace);
            this.palace_element = Element.from_palace(this.palace);
        }

        this.is_correct_orientation = ori == 0;

        MeanDBHelper helper = new MeanDBHelper(context);
        ArrayList<String> means = helper.query(this.number);
        card_name = means.get(0);
        card_explanation =  means.get(1);
        mean_correct_ori = means.get(2);
        mean_incorrect_ori = means.get(3);
    }

    public static int[] card_image_id = { 0,
            R.drawable.c1, R.drawable.c2, R.drawable.c3, R.drawable.c4, R.drawable.c5,
            R.drawable.c6, R.drawable.c7, R.drawable.c8, R.drawable.c9,
            R.drawable.c10, R.drawable.c11, R.drawable.c12, R.drawable.c13, R.drawable.c14, R.drawable.c15,
            R.drawable.c16, R.drawable.c17, R.drawable.c18, R.drawable.c19,
            R.drawable.c20, R.drawable.c21, R.drawable.c22, R.drawable.c23, R.drawable.c24, R.drawable.c25,
            R.drawable.c26, R.drawable.c27, R.drawable.c28, R.drawable.c29,
            R.drawable.c30, R.drawable.c31, R.drawable.c32, R.drawable.c33, R.drawable.c34, R.drawable.c35,
            R.drawable.c36, R.drawable.c37, R.drawable.c38, R.drawable.c39,
            R.drawable.c40, R.drawable.c41, R.drawable.c42, R.drawable.c43, R.drawable.c44, R.drawable.c45,
            R.drawable.c46, R.drawable.c47, R.drawable.c48, R.drawable.c49,
            R.drawable.c50, R.drawable.c51, R.drawable.c52, R.drawable.c53, R.drawable.c54, R.drawable.c55,
            R.drawable.c56, R.drawable.c57, R.drawable.c58, R.drawable.c59,
            R.drawable.c60, R.drawable.c61, R.drawable.c62, R.drawable.c63, R.drawable.c64, R.drawable.c65,
            R.drawable.c66, R.drawable.c67, R.drawable.c68, R.drawable.c69,
            R.drawable.c70, R.drawable.c71, R.drawable.c72, R.drawable.c73, R.drawable.c74, R.drawable.c75,
            R.drawable.c76, R.drawable.c77, R.drawable.c78
    };

    public static ArrayList<Card> get_all_cards(Context context)
    {
        ArrayList<Card> res = new ArrayList<>();

        for (int i = 1; i <= 78; i++)
        {
            res.add(new Card(i, 0, context));
        }

        return res;
    }
}

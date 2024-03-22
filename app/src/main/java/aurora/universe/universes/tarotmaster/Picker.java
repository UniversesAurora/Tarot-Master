package aurora.universe.universes.tarotmaster;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by miguelduarte on 4/8/2020.
 */

public class Picker
{
    public static ArrayList<Card> pick(
            int pick_num,
            boolean use_incorrect,
            PickMode pick_mode,
            Addition addition,
            Card indicator_card,
            Context context
    )
    {
        ArrayList<Integer> raw = new ArrayList<>();
        ArrayList<Card> translated = new ArrayList<>();

        Card additional_card = null;

        if (addition == Addition.CUT)
        {
            int cut = get_random(1, 78, raw);
            int cut_orientation = use_incorrect ? get_random(0, 1, new ArrayList<Integer>()) : 0;
            raw.add(cut);
            additional_card = new Card(cut, cut_orientation, context);
        }
        else if (addition == Addition.INDICATOR)
        {
            raw.add(indicator_card.number);
            additional_card = indicator_card;
        }

        if (pick_mode == PickMode.ONLY_MAJOR)
        {
            for (int i = 0; i < pick_num; i++)
            {
                int picked = get_random(1, 22, raw);
                int orientation = use_incorrect ? get_random(0, 1, new ArrayList<Integer>()) : 0;
                raw.add(picked);
                translated.add(new Card(picked, orientation, context));
            }
        }
        else if (pick_mode == PickMode.ONLY_MINOR)
        {
            for (int i = 0; i < pick_num; i++)
            {
                int picked = get_random(23, 78, raw);
                int orientation = use_incorrect ? get_random(0, 1, new ArrayList<Integer>()) : 0;
                raw.add(picked);
                translated.add(new Card(picked, orientation, context));
            }
        }
        else
        {
            for (int i = 0; i < pick_num; i++)
            {
                int picked = get_random(1, 78, raw);
                int orientation = use_incorrect ? get_random(0, 1, new ArrayList<Integer>()) : 0;
                raw.add(picked);
                translated.add(new Card(picked, orientation, context));
            }
        }



        if (addition != Addition.NONE && additional_card != null)
        {
            translated.add(additional_card);
        }

        return translated;
    }

    public static int get_random(int from, int to, ArrayList<Integer> without)
    {
        int result;
        do
        {
            result = (int)(from+Math.random()*(to-from+1));
        } while (without.contains(result));

        return result;
    }
}

package aurora.universe.universes.tarotmaster;

/**
 * Created by miguelduarte on 4/7/2020.
 */

public enum Palace {
    NONE, KING, QUEEN, KNIGHT, PAGE;

    public static Palace from_index(int index)
    {
        switch (index)
        {
            case 1:
                return KING;
            case 2:
                return QUEEN;
            case 3:
                return KNIGHT;
            case 4:
                return PAGE;
            default:
                return NONE;
        }
    }
}

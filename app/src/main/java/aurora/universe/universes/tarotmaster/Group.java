package aurora.universe.universes.tarotmaster;

/**
 * Created by miguelduarte on 4/7/2020.
 */

public enum Group {
    NONE, CUPS, WANDS, PENTACLES, SWORDS;

    public static Group from_index(int index)
    {
        switch (index)
        {
            case 1:
                return CUPS;
            case 2:
                return WANDS;  // 权杖
            case 3:
                return PENTACLES; // 星币
            case 4:
                return SWORDS;  // 宝剑
            default:
                return NONE;
        }
    }
}

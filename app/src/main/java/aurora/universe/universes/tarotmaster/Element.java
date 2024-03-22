package aurora.universe.universes.tarotmaster;

/**
 * Created by miguelduarte on 4/7/2020.
 */

public enum Element {
    NONE("无", "无", "无", "无"),
    WATER("水", "爱、想象力、连通", "暴躁、幼稚、无节制", "感受"),
    FIRE("火", "创造、发展、热情", "骄傲、自私、顽皮", "直觉"),
    EARTH("土", "实践、技能、坚持", "顽固、贪婪、无趣", "感官"),
    AIR("风", "逻辑、正义、辨别力", "欠考虑、武断、极端", "思考");

    private String name;
    private String pos_feature;
    private String neg_feature;
    private String jung_feature;

    private Element(String name, String pos_feature, String neg_feature, String jung_feature)
    {
        this.name = name;
        this.pos_feature = pos_feature;
        this.neg_feature = neg_feature;
        this.jung_feature = jung_feature;
    }

    public String get_name()
    {
        return name;
    }

    public String get_pos_feature()
    {
        return pos_feature;
    }

    public String get_neg_feature()
    {
        return neg_feature;
    }

    public String get_jung_feature()
    {
        return jung_feature;
    }

    public String get_intro()
    {
        return element_detail_mean(this) + "\n\n积极特征\n" + get_pos_feature() +
                "\n\n消极特征\n" + get_neg_feature() +
                "\n\n荣格理论\n" + get_jung_feature();
    }

    public static Element from_group(Group index)
    {
        switch (index)
        {
            case CUPS:
            return WATER;
            case WANDS:
                return FIRE;
            case PENTACLES:
                return EARTH;
            case SWORDS:
                return AIR;
            default:
                return NONE;
        }
    }

    public static Element from_palace(Palace index)
    {
        switch (index)
        {
            case QUEEN:
                return WATER;
            case KING:
                return FIRE;
            case PAGE:
                return EARTH;
            case KNIGHT:
                return AIR;
            default:
                return NONE;
        }
    }

    public static Element from_index(int index)
    {
        switch (index)
        {
            case 1:
                return WATER;
            case 2:
                return FIRE;
            case 3:
                return EARTH;
            case 4:
                return AIR;
            default:
                return NONE;
        }
    }

    public static int to_index(Element index)
    {
        switch (index)
        {
            case WATER:
                return 1;
            case FIRE:
                return 2;
            case EARTH:
                return 3;
            case AIR:
                return 4;
            default:
                return 0;
        }
    }

    /*
    public static String element_frequent_mean(Element element)
    {

    }

    public static String element_lost_mean(Element element)
    {

    }
    */

    public static String element_detail_mean(Element element)
    {
        switch (element)
        {
            case WATER:
                return "人家说女人温柔如水，水正是与感情、感受、潜意识相关的阴性元素。相对于火的主动，水扮演的是被动的角色，具有水特质的人特别注重人际关系与感情，较女性化，心思细腻敏感，有时多愁善感，喜欢照顾别人也需要受人保护。在占星学上水象星座有双鱼、巨蟹、和天蝎。";
            case FIRE:
                return "火与小牌中的权杖牌组相关，表现的是主动、积极、外向，富有创造力。如同「热情如火」一词，火通常与运动、战争、行动力、直觉有关。具有火要素特质的人，个性较急，直来直往，随时都精力充沛，在人群中特别引人注目，占星学上的火象星座狮子、射手、牡羊都具此特质。";
            case EARTH:
                return "土地滋养万物，最是稳定牢靠。如果说风是理论家的话，那土就是实践家了。土特质的人勤奋不懈，重视财富给予的安全感，经济有保障，便可以享乐，但较不知变通。所以与土相关的钱币牌组除了表现金钱物质方面之外，也代表物质享受和娱乐。土象星座有魔羯、金牛和处女。";
            case AIR:
                return "风是飘忽不定，无法捉摸，变动性强的。占星学上，风代表的抽象的思考分析与清晰的判断力，着重理论。实际的例子像是出版写作、旅行、学习等。然而，另一方面，风也和麻烦、问题、争执、疾病、死亡等负面事物相关，所以传统上与风有关的宝剑牌组几乎令人避之唯恐不及。";
            default:
                return "";
        }
    }

    public static String element_muti_solve(Element e1, Element e2)
    {
        String result = "";

        if (e1 == Element.FIRE && e2 == Element.AIR || e1 == Element.AIR && e2 == Element.FIRE)
            result = "友好的活跃，体现了"+e1.get_name()+"元素的"+ e1.get_pos_feature() +
                    "，和" + e2.get_name()+"元素的"+ e2.get_pos_feature() + "，情况改善。";
        else if (e1 == Element.WATER && e2 == Element.EARTH || e1 == Element.EARTH && e2 == Element.WATER)
            result = "友好的活跃，体现了"+e1.get_name()+"元素的"+ e1.get_pos_feature() +
                    "，和" + e2.get_name()+"元素的"+ e2.get_pos_feature() + "，情况改善。";
        else if (e1 == Element.FIRE && e2 == Element.EARTH || e1 == Element.EARTH && e2 == Element.FIRE)
            result = "友好的中和，体现了"+e1.get_name()+"元素的"+ e1.get_pos_feature() +
                    "，和" + e2.get_name()+"元素的"+ e2.get_pos_feature() + "，不会影响情况好坏，但会加强性质。";
        else if (e1 == Element.WATER && e2 == Element.AIR || e1 == Element.AIR && e2 == Element.WATER)
            result = "友好的中和，体现了"+e1.get_name()+"元素的"+ e1.get_pos_feature() +
                    "，和" + e2.get_name()+"元素的"+ e2.get_pos_feature() + "，不会影响情况好坏，但会加强性质。";
        else if (e1 == Element.WATER && e2 == Element.FIRE || e1 == Element.FIRE && e2 == Element.WATER)
            result = "敌对的虚弱中和，体现了"+e1.get_name()+"元素的"+ e1.get_neg_feature() +
                    "，和" + e2.get_name()+"元素的"+ e2.get_neg_feature() + "。对于这两种元素协调性差，出现矛盾，" +
                    e1.jung_feature + "和" + e2.jung_feature + "难以权衡。";
        else if (e1 == Element.AIR && e2 == Element.EARTH || e1 == Element.EARTH && e2 == Element.AIR)
            result = "敌对的虚弱中和，体现了"+e1.get_name()+"元素的"+ e1.get_neg_feature() +
                    "，和" + e2.get_name()+"元素的"+ e2.get_neg_feature() + "。对于这两种元素协调性差，出现矛盾，" +
                    e1.jung_feature + "和" + e2.jung_feature + "难以权衡。";

        return result;
    }
}

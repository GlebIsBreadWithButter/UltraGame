package Entities;

import java.util.Random;

public class UnitNameGiver {
    private static final String[] names = {"Лёха", "Васян", "Эдик", "Саня", "Димон", "Миха", "Тоха", "Серёга", "Валя", "Женёк", "Алёнка",
            "Подзасор", "Гробобовщик", "Грабрафатагхарт", "Володя", "Маринка", "Малинка", "СБК УБЦ"};

    public static String getNewName(){
        Random r = new Random();
        return names[r.nextInt(18)];
    }
}

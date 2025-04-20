package GameLogic;

import Cells.WorldCells.Castle;
import Entities.Unit;
import Entities.Units.*;

import java.util.ArrayList;

public class Texts {
    private static final String startText = """
            Добро пожаловать!
            Чтобы начать игру, введите цифру 1
            Чтобы выйти из игры, введите цифру 0""";
    private static final String startText2 = "Назовите своего первого героя...";
    private static final String startText3 = "Соберите свою первую армию, покупая войска...\n";
    private static final String startText4 = "\n\n===========Игра начинается!===========\n\n";
    private static final String unitShopText = """
            ===========МАГАЗИН ВОИНОВ===========
            Введите 0 для выхода или же соответствующую цифру (показаны слева) для покупки воина...
            Для просмотра баланса героя введите букву g
            Для просмотра казны и приобретённых улучшений введите букву f""";
    private static final String castleShopText = """
            ===========МАГАЗИН УЛУЧШЕНИЙ ЗАМКА===========
            Введите 0 для выхода или же соответствующую цифру (показаны слева) для покупки улучшения...
            Для просмотра баланса героя введите букву g
            Для просмотра казны и приобретённых улучшений введите букву f""";
    private static final String leavingText = "Покидаем строение...";
    private static final String divider = "============================================";
    private static final String controlsText = """
            ===========УПРАВЛЕНИЕ===========
            Перемещение по миру:
            NUM8 - вверх
            NUM9 - вверх вправо
            NUM6 - вправо
            NUM3 - вниз вправо
            NUM2 - вниз
            NUM1 - вниз влево
            NUM4 - влево
            NUM7 - вверх влево
            NUM5 - стоять на месте (тратит 1 сдвиг, но восстанавливает 2 очка перемещения)
            Прочее:
            h - показать своих героев
            a - показать информацию об армиях своих героев
            с - показать информацию о замке
            e - перейти в режим редактирования армии героя""";
    private static final String heroShopText = """
            ===========ТАВЕРНА===========
            Введите 0 для выхода или же соответствующую цифру (показаны слева) для покупки героя...
            Для найма героев в таверне используется ТОЛЬКО золото из казны
            Для просмотра казны и приобретённых улучшений введите букву f""";
    private static final String notEnoughGoldText = "Недостаточно золота!";
    private static final String enemyInsideWarningText = """
            ВНИМАНИЕ! В клетке, в которую вы хотите пройти, расположен враг!
            Продолжение движения приведёт к началу боя! Стоит ли идти дальше?
            0 - нет
            1 - да""";
    private static final String shopsEnteringOffer = """
            ===========ВОРОТА ЗАМКА===========
            0 - покинуть замок
            1 - зайти вовнутрь""";
    private static final String shopsChoiceOffer = """
            ===========ВЫБОР МАГАЗИНА===========
            1 - магазин воинов
            2 - магазин улучшений замка
            3 - таверна""";
    private static final String victoryText = "Вы захватили замок врага! Победа!";
    private static final String defeatText = "Ваш замок был захвачен врагом! Поражение!";
    private static final String goldStoringOffer = """
            ===========ПОПОЛНЕНИЕ КАЗНЫ===========
            0 - не пополнять казну
            1 - отдать казне половину золота
            2 - отдать казне всё золото""";
    public static final String myHeroesReportHeader = "===========ВАШИ ГЕРОИ===========";
    public static final String myHeroesArmiesReportHeader = "===========АРМИИ ВАШИХ ГЕРОЕВ===========";
    public static final String myCastleStatusReportHeader = "===========ВАШ ЗАМОК===========";
    public static final String worldMapStatus = "\n===========КАРТА МИРА===========";
    public static final String myHeroesArmiesEditorHeader = "\n===========РЕДАКТОР АРМИЙ===========";
    public static final String myHeroesArmiesEditor = """
            Управление редактором:
            0 - покинуть режим редактора
            1 - создать отряд (воины должны быть одного типа)
            2 - добавить воина в существующий отряд
            3 - объединить отряды
            4 - расформировать отряд
            5 - показать армию снова
            """;
    public static final String armyEditingFinished = "Редактирование армии героя завершено!";
    public static final String formSquadHelp = "-----Формирование отряда-----" +
            "\nЧтобы сформировать отряд, введите два номера воинов ОДНОГО ТИПА, из которых он будет создан";
    public static final String addUnitToSquadHelp = "-----Увеличение отряда-----" +
            "\nЧтобы добавить к существующему отряду воина, введите сначала номер воина, а потом номер отряда";
    public static final String mergeSquadsHelp = "-----Объединение отрядов-----" +
            "\nЧтобы объединить отряды ОДНОГО ТИПА, введите их номера";
    public static final String destroySquadHelp = "-----Расформирование отряда-----" +
            "\nЧтобы расформировать отряд, введите его номер";
    public static final String unitsFightHeader= "===========СРАЖЕНИЕ ВОИНОВ===========";
    public static final String fightHeaderEnd= "===========КОНЕЦ СРАЖЕНИЯ===========";
    public static final String unitSquadFightHeader= "===========СРАЖЕНИЕ ВОИНА С ОТРЯДОМ===========";
    public static final String squadsFightHeader= "===========CРАЖЕНИЕ ОТРЯДОВ===========";
    public static final String fightDecision= """
            Выберите, как нужно действовать
            0 - атаковать
            1 - защищаться""";
    public static final String fightStart= "===========Бой начинается!===========";
    public static final String fightEnd= "===========Бой окончен!===========";
    public static final String victoryTextSmall= "===========ПОБЕДА В БОЮ!===========";
    public static final String defeatTextSmall = "===========ПОРАЖЕНИЕ В БОЮ!===========";
    public static final String drawText = "===========БОЙ ЗАКОНЧИЛСЯ НИЧЬЁЙ!===========";
    public static final String wormsDenText = """
            ===========БЕРЛОГА ЧЕРВЯ===========
            0 - покинуть берлогу
            1 - начать копать тоннель""";
    public static final String wormsDenDirection = "Выберите направление для раскопок... (NUMPAD)";
    public static final String wormsDenLength = "Укажите длину раскопок...";
    public static final String  wormsDenEnterOffer = "4 - берлога червя";
    public static final String tunnelEnterOffer = "5 - вход в систему тоннелей";
    public static final String tunnelManageText = "===========ВХОД В СИСТЕМУ ТОННЕЛЕЙ===========";
    public static final String excavationStarted = "Проходка тоннеля началась!";
    public static final String victory1Text = "Ваше войско уничтожило всё войско врага в боях! Победа!";
    public static final String defeat1Text = "Ваше войско было уничтожило войском врага в боях! Поражение!";

    //PLAIN TEXTS-----------------------------------------------------
    public static String getStartText() {
        return startText;
    }

    public static String getStartText2() {
        return startText2;
    }

    public static String getStartText3() {
        return startText3;
    }

    public static String getStartText4() {
        return startText4;
    }

    public static String getUnitShopText() {
        return unitShopText;
    }

    public static String getDivider() {
        return divider;
    }

    public static String getCastleShopText() {
        return castleShopText;
    }

    public static String getLeavingText() {
        return leavingText;
    }

    public static String getControlsText() {
        return controlsText;
    }

    public static String getHeroShopText(){
        return heroShopText;
    }

    public static String getNotEnoughGoldText(){
        return notEnoughGoldText;
    }

    public static String getEnemyInsideWarningText() {
        return enemyInsideWarningText;
    }

    public static String getVictoryText() {
        return victoryText;
    }

    public static String getDefeatText() {
        return defeatText;
    }

    public static String getShopsChoiceOffer() {
        return shopsChoiceOffer;
    }

    public static String getShopsEnteringOffer() {
        return shopsEnteringOffer;
    }

    public static String getGoldStoringOffer(){
        return goldStoringOffer;
    }

    public static String getHeroMoveHeader(String name){
        return "===========ХОД ГЕРОЯ " + name + "===========";
    }

    //OFFERS--------------------------------------------------
    public static String getSpearmanOffer(){
        Spearman a = new Spearman();
        return "(" + a.getType() + ") " + a.getCost() + " золотых - стоимость найма копейщика";
    }

    public static String getCrossbowmanOffer(){
        Crossbowman a = new Crossbowman();
        return "(" + a.getType() + ") " + a.getCost() + " золотых - стоимость найма арбалетчика";
    }

    public static String getSwordsmanOffer(){
        Swordsman a = new Swordsman();
        return "(" + a.getType() + ") " + a.getCost() + " золотых - стоимость найма мечника";
    }

    public static String getHorsemanOffer(){
        Horseman a = new Horseman();
        return "(" + a.getType() + ") " + a.getCost() + " золотых - стоимость найма всадника";
    }

    public static String getPaladinOffer(){
        Paladin a = new Paladin();
        return "(" + a.getType() + ") " + a.getCost() + " золотых - стоимость найма паладина";
    }

    public static String getBuildingOffer(int arg) {
        return switch (arg) {
            case 1 -> "(1) " + Castle.LV1_UPGRADE_COST + " золотых - построить башню арбалетчиков";
            case 2 -> "(2) " + Castle.LV2_UPGRADE_COST + " золотых - построить гарнизон мечников";
            case 3 -> "(3) " + Castle.LV3_UPGRADE_COST + " золотых - построить конюшню всадников";
            case 4 -> "(4) " + Castle.LV4_UPGRADE_COST + " золотых - построить собор паладинов";
            case 5 -> "(5) " + Castle.LV5_UPGRADE_COST + " золотых - построить берлогу червя";
            default -> "";
        };
    }

    public static String getHeroOffer(){
        return "(1) " + Classifier.HEAVY_HERO_COST + " золотых - нанять героя-тяжеловеса\n" +
                "(2) " + Classifier.LIGHT_HERO_COST + " золотых - нанять героя-скорохода";
    }

    public static String getGoldPackMessage(int bounty) {
        return "Вы нашли сундук с сокровищами, в котором лежало " + bounty + " золотых!";
    }

    public static String getMoneyStoredReport(int stored){
        return "В казну было помещено золото в количестве " + stored + " единиц!";
    }

    public static String getMyCastleStatusReport(Player player){
        Castle c = player.getOwnedCastle();
        String report = "Казна: " + player.getBankGoldAmount() + " ед. золота";
        if (c.isBarracksBuilt())
            report += "\nПостроены бараки";
        if (c.isTowerBuilt())
            report += "\nПостроена башня арбалетчиков";
        if (c.isGarrisonBuilt())
            report += "\nПостроен гарнизон";
        if (c.isStableBuilt())
            report += "\nПостроена конюшня";
        if (c.isCathedralBuilt())
            report += "\nПостроен собор";
        if (c.isWormsDenBuilt())
            report += "\nПостроена берлога червя";
        return report;
    }

    public static String getUnitsDealtDamageReport(Unit first, Unit second, double damage) {
        return "Воин " + first.getName() + " наносит " + damage + " ед. урона воину " + second.getName() + "!";
    }

    public static String getUnitsFightResult(Unit my, Unit enemy, int receivedGold){
        String report = "";
        if ((my.getHealth()<=0) && (enemy.getHealth()<=0)){
            report = "Ваш воин " + my.getName() + " и воин врага " + enemy.getName() + " погибают в сражении!";
        }
        else if (my.getHealth()<=0){
            report = "Ваш воин " + my.getName() + " погибает в сражении!";
        }
        else {
            report = "Воин врага " + enemy.getName() + " погибает в сражении!";
        }
        if (receivedGold != 0)
            report += "\nВаш герой получает " + receivedGold + " золота!";
        return report;
    }

    public static String getUnitSquadFightResult(Unit my, ArrayList<Unit> enemy, int receivedGold){
        String report = "";
        if ((my.getHealth()<=0) && (enemy.get(0).getHealth()<=0)){
            report = "Ваш воин " + my.getName() + " и весь отряд врага погибают в сражении!";
        }
        else if (my.getHealth()<=0){
            report = "Ваш воин " + my.getName() + " погибает в сражении!";
        }
        else {
            report = "Весь отряд врага погибает в сражении!";
        }
        if (receivedGold != 0)
            report += "\nВаш герой получает " + receivedGold + " золота!";
        return report;
    }

    public static String getSquadUnitFightResult(ArrayList<Unit> my, Unit enemy, int receivedGold){
        String report = "";
        if (my.get(0).getHealth()<=0 && enemy.getHealth()<=0){
            report = "Весь ваш отряд и воин врага " + enemy.getName() + " погибают в сражении!";
        }
        else if (my.get(0).getHealth()<=0){
            report = "Весь ваш отряд погибает в сражении!";
        }
        else {
            report = "Воин врага " + enemy.getName() + " погибает в сражении!";
        }
        if (receivedGold != 0)
            report += "\nВаш герой получает " + receivedGold + " золота!";
        return report;
    }

    public static String getSquadsFightResult(ArrayList<Unit> my, ArrayList<Unit> enemy, int receivedGold){
        String report = "";
        if ((my.get(0).getHealth()<=0) && (enemy.get(0).getHealth()<=0)){
            report = "Весь ваш отряд и весь отряд врага погибают в сражении!";
        }
        else if (my.get(0).getHealth()<=0){
            report = "Весь ваш отряд погибает в сражении!";
        }
        else {
            report = "Весь отряд врага погибает в сражении!";
        }
        if (receivedGold != 0)
            report += "\nВаш герой получает " + receivedGold + " золота!";
        return report;
    }

    public static String getFirstStrikeAnnouncement(boolean startedByEnemy){
        String report;
        if(startedByEnemy)
            report = "Враг наносит первый удар!";
        else
            report = "Вы наносите первый удар!";
        return report;
    }

    public static void printEnemyMoveHeader(){
        System.out.println("===========ХОД ВРАГА===========");
    }

    public static void printTunnelsOffer(ArrayList<Tunnel> tunnels) {
        int num = 0;
        System.out.println("Прокопанные туннели:");
        for (Tunnel t : tunnels){
            if (t.getTimer()<=0)
                System.out.println("("+num+")" + " Тоннель №" + num);
            else
                System.out.println("Тоннель прокапывается...");
            num++;
        }
        System.out.println("Введите -1 для выхода или же соответствующую цифру (показаны слева) для прохождения через тоннель...");
    }

    public static String getHeroRanAwayText(String name) {
        return "Ваш герой " + name + ", оставшись без армии, сбегает с поля боя к замку!";
    }

    public static String getEnemyHeroRanAwayText(String name) {
        return "Вражеский герой " + name + ", оставшись без армии, сбегает с поля боя к замку!";
    }

    public static String getHeroDiedText(String name) {
        return "Ваш герой " + name + ", вступив в бой без армии, бесславно погибает!";
    }

    public static String getEnemyHeroDiedText(String name) {
        return "Вражеский герой " + name + ", вступив в бой без армии, бесславно погибает!";
    }
}

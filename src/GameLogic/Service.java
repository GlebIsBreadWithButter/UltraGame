package GameLogic;

import Cells.WorldCells.Castle;
import Entities.Hero;

import java.util.Scanner;

public class Service {
    private static final Scanner scanner = new Scanner(System.in);

    public static void showUnitShop(Castle castle){
        System.out.println(Texts.getDivider());
        if (castle.isBarracksBuilt()){
            System.out.println(Texts.getSpearmanOffer());
        }
        if (castle.isTowerBuilt()){
            System.out.println(Texts.getCrossbowmanOffer());
        }
        if (castle.isGarrisonBuilt()){
            System.out.println(Texts.getSwordsmanOffer());
        }
        if (castle.isStableBuilt()){
            System.out.println(Texts.getHorsemanOffer());
        }
        if (castle.isCathedralBuilt()){
            System.out.println(Texts.getPaladinOffer());
        }
        System.out.println(Texts.getDivider());
    }

    public static void showCastleShop(Castle castle){
        System.out.println(Texts.getDivider());
        if (!castle.isTowerBuilt()){
            System.out.println(Texts.getBuildingOffer(1));
        }
        if (!castle.isGarrisonBuilt()){
            System.out.println(Texts.getBuildingOffer(2));
        }
        if (!castle.isStableBuilt()){
            System.out.println(Texts.getBuildingOffer(3));
        }
        if (!castle.isCathedralBuilt()){
            System.out.println(Texts.getBuildingOffer(4));
        }
        if (!castle.isWormsDenBuilt()){
            System.out.println(Texts.getBuildingOffer(5));
        }
        System.out.println(Texts.getDivider());
    }

    public static void showHeroShop(){
        System.out.println(Texts.getDivider());
        System.out.println(Texts.getHeroOffer());
        System.out.println(Texts.getDivider());
    }

    public static void offerMoneyStoring(Hero hero){
        System.out.println(Texts.getGoldStoringOffer());
        int s;
        int choice = scanner.nextInt();
        if (choice == 1){
            s = hero.getGoldAmount()/2;
            hero.setGoldAmount(hero.getGoldAmount() - s);
            System.out.println(Texts.getMoneyStoredReport(s));
        }
        else if (choice == 2){
            s = hero.getGoldAmount();
            hero.setGoldAmount(hero.getGoldAmount() - s);
            System.out.println(Texts.getMoneyStoredReport(s));
        }
    }

    public static void editArmy(Hero hero){
        System.out.println(Texts.myHeroesArmiesEditorHeader);
        ArmyManager am = hero.getArmyManager();
        am.showArmy();
        am.showSquads();
        System.out.println(Texts.myHeroesArmiesEditor);
        boolean continueArmyEditing = true;
        while (continueArmyEditing) {
            int choice = scanner.nextInt();
            if (choice != 0) {
                switch (choice) {
                    case 1:
                        System.out.println(Texts.formSquadHelp);
                        int first = scanner.nextInt();
                        int second = scanner.nextInt();
                        am.formSquad(first, second);
                        break;
                    case 2:
                        System.out.println(Texts.addUnitToSquadHelp);
                        int unit = scanner.nextInt();
                        int squad = scanner.nextInt();
                        am.addUnitToSquad(unit, squad);
                        break;
                    case 3:
                        System.out.println(Texts.mergeSquadsHelp);
                        int firstSquad = scanner.nextInt();
                        int secondSquad = scanner.nextInt();
                        am.mergeSquads(firstSquad, secondSquad);
                        break;
                    case 4:
                        System.out.println(Texts.destroySquadHelp);
                        int squadToDestroy = scanner.nextInt();
                        am.destroySquad(squadToDestroy);
                        break;
                    case 5:
                        am.showArmy();
                        am.showSquads();
                        break;
                }
            }
            else {
                continueArmyEditing = false;
                System.out.println(Texts.armyEditingFinished);
            }
        }
    }
}

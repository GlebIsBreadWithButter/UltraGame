package Entities.Objects;

import Entities.Entity;
import GameLogic.Classifier;

public class GoldPack extends Entity {
    public GoldPack(int value){
        name = "Сундук с сокровищами";
        type = Classifier.GOLD_PACK_TYPE;
        bounty = value;
        lookName = "\u001B[45m" + "\u001B[33m" + " З " + "\u001B[0m";
    }
}

package CollectonUtils;

import BaseClass.SpaceMarine;

public class CollectionUtils {
    public static boolean checkExist(long ID) {
        for (SpaceMarine spaceMarine:CollectionManager.getLinkedList()) {
            return spaceMarine.getId() == ID;
        }
        return false;
    }


    static String display(SpaceMarine spaceMarine) {
        String info = "";
        info = String.format("ID элемента коллекции – %s\n" +
                        "Имя бойца –  %s\n" +
                        "Координата X – %s\n" +
                        "Координата Y – %s\n" +
                        "Дата и время создания элемента – %s\n" +
                        "Здоровье – %s\n" +
                        "Звание –  %s\n" +
                        "Оружие – %s\n" +
                        "Оружие бижнего действия – %s\n" +
                        "Расположение – %s\n" +
                        "_________________________________________________________\n",
                spaceMarine.getId(),
                spaceMarine.getName(),
                spaceMarine.getCoordinates().getX(),
                spaceMarine.getCoordinates().getY(),
                spaceMarine.getCreationDate(),
                spaceMarine.getHealth(),
                spaceMarine.getCategory(),
                spaceMarine.getWeaponType(),
                spaceMarine.getMeleeWeapon(),
                spaceMarine.getMeleeWeapon(),
                (spaceMarine.getChapter().getName() + "@" + spaceMarine.getChapter().getWorld()));








        return info;
    }
}

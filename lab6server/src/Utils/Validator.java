package Utils;

import BaseClass.*;

import java.util.Arrays;

public class Validator {

    private static boolean checkExistCategory(String toContains){
        return Arrays.stream(AstartesCategory.values()).anyMatch((astartesCategory) -> astartesCategory.name().equals(toContains));
    }

    private static boolean checkExistMeleeWeapon(String toContains) {
        return Arrays.stream(MeleeWeapon.values()).anyMatch((meleeWeapon) -> meleeWeapon.name().equals(toContains));
    }

    private static boolean checkExistWeapon(String toContains) {
        return Arrays.stream(Weapon.values()).anyMatch((weapon) -> weapon.name().equals(toContains));
    }


    public static boolean validateSpaceMarine(SpaceMarine spaceMarine) {
        return spaceMarine.getId() != 0 &&
            ( spaceMarine.getName() != null && !spaceMarine.getName().equals("")) &&
            spaceMarine.getCoordinates().getX() <= 878 &&
            (spaceMarine.getHealth() != 0 && spaceMarine.getHealth() > 0) &&
            (spaceMarine.getMeleeWeapon() == null || checkExistMeleeWeapon(spaceMarine.getMeleeWeapon().toString())) &&
            checkExistCategory(spaceMarine.getCategory().toString()) &&
            checkExistWeapon(spaceMarine.getWeaponType().toString());
    }
}

package Command.ConcreteCommand;

import BaseClass.Weapon;
import Command.Command;
import Command.CommandReceiver;

import java.io.IOException;
import java.net.Socket;

public class CountGreaterThanWeaponType extends Command {
    private static final long serialVersionUID = 32L;

    @Override
    public void execute(Object argObject, Socket socket) throws IOException {
        String arg = argObject.toString();
        if (arg.split(" ").length == 1) {
            CommandReceiver commandReceiver = new CommandReceiver(socket);
            Weapon weaponArg = null;
            switch (arg){
                case "BOLTGUN":
                    weaponArg = Weapon.BOLTGUN;
                    break;
                case "MELTAGUN":
                    weaponArg = Weapon.MELTAGUN;
                    break;
                case "COMBI_FLAMER":
                    weaponArg = Weapon.COMBI_FLAMER;
                    break;
                    case "FLAMER":
                    weaponArg = Weapon.FLAMER;
                    break;
                    case "INFERNO_PISTOL":
                    weaponArg = Weapon.INFERNO_PISTOL;
                    break;
            }
            commandReceiver.count_greater_than_weapon_type(weaponArg);
        }
        else { System.out.println("Некорректное количество аргументов. Для справки напишите help."); }
    }
}

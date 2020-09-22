package Command.ConcreteCommand;

import BaseClass.SpaceMarine;
import Command.Command;
import Command.CommandReceiver;
import Command.SerializedCommands.SerializedCombinedCommand;

import java.io.IOException;
import java.net.Socket;

public class Update extends Command {
    private static final long serialVersionUID = 32L;

    @Override
    public void execute(Object argObject, Socket socket) throws IOException {
        SerializedCombinedCommand combinedCommand = (SerializedCombinedCommand) argObject;
        String arg =  combinedCommand.getArg();
        SpaceMarine spaceMarine = (SpaceMarine) combinedCommand.getObject();
        if (arg.split(" ").length == 1) {
            CommandReceiver commandReceiver = new CommandReceiver(socket);
            commandReceiver.update(arg, spaceMarine);
        }
    }
}

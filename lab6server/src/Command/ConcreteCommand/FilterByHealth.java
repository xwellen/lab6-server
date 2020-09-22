package Command.ConcreteCommand;

import Command.Command;
import Command.CommandReceiver;

import java.io.IOException;
import java.net.Socket;

public class FilterByHealth extends Command {
    private static final long serialVersionUID = 32L;

    @Override
    public void execute(Object argObject, Socket socket) throws IOException {
        String arg = argObject.toString();
        if (arg.split(" ").length == 1) {
            CommandReceiver commandReceiver = new CommandReceiver(socket);
            commandReceiver.filter_by_health(Long.parseLong(arg));
        }
        else { System.out.println("Некорректное количество аргументов. Для справки напишите help."); }
    }
}

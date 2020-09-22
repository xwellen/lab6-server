package Command.ConcreteCommand;

import Command.Command;
import Command.CommandReceiver;

import java.io.IOException;
import java.net.Socket;

public class Add extends Command {
    private static final long serialVersionUID = 32L;

    @Override
    public void execute(Object argObject, Socket socket) throws IOException {
        CommandReceiver commandReceiver = new CommandReceiver(socket);
        commandReceiver.add(argObject);
    }
}

package Command;

import BaseClass.SpaceMarine;
import BaseClass.Weapon;
import CollectonUtils.CollectionManager;
import CollectonUtils.CollectionUtils;
import Command.SerializedCommands.SerializedMessage;
import Utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class CommandReceiver {
    private final Socket socket;
    private static final Logger logger = LoggerFactory.getLogger(CommandReceiver.class);
    private CollectionManager collectionManager = CollectionManager.getCollectionManager();

    public CommandReceiver(Socket socket) {
        this.socket = socket;
    }

    public void info() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        out.writeObject(new SerializedMessage(collectionManager.getInfo()));
        logger.info(String.format("Клиенту %s:%s отправлен результат работы команды INFO", socket.getInetAddress(), socket.getPort()));
    }

    public void show() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        out.writeObject(new SerializedMessage(collectionManager.show()));
        logger.info(String.format("Клиенту %s:%s отправлен результат работы команды SHOW", socket.getInetAddress(), socket.getPort()));
    }

    public void add(Object o) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        SpaceMarine spaceMarine = (SpaceMarine) o;

        if (Validator.validateSpaceMarine(spaceMarine)) {
            collectionManager.add(spaceMarine);
            out.writeObject(new SerializedMessage("Элемент добавлен в коллекцию."));
        } else {
            out.writeObject(new SerializedMessage("Полученный элемент не прошел валидацию на стороне сервера."));
        }

        logger.info(String.format("Клиенту %s:%s отправлен результат работы команды ADD", socket.getInetAddress(), socket.getPort()));
    }

    public void add_if_min(Object o) throws IOException{
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        SpaceMarine spaceMarine = (SpaceMarine) o;

        if (Validator.validateSpaceMarine(spaceMarine)) {
            collectionManager.add_if_min(spaceMarine);
            out.writeObject(new SerializedMessage("Элемент добавлен в коллекцию."));
        } else {
            out.writeObject(new SerializedMessage("Полученный элемент не прошел валидацию на стороне сервера."));
        }

        logger.info(String.format("Клиенту %s:%s отправлен результат работы команды ADD_IF_MIN", socket.getInetAddress(), socket.getPort()));

    }

    /**
     *
     * @param ID - апдейт элемента по ID.
     */
    public void update(String ID, SpaceMarine spaceMarine) throws IOException {
        Long marineId;
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        try {
            marineId = Long.parseLong(ID);
            if (CollectionUtils.checkExist(marineId)) {
                if (Validator.validateSpaceMarine(spaceMarine)) {
                    collectionManager.update(spaceMarine, marineId);
                    out.writeObject(new SerializedMessage("Команда update выполнена."));
                } else {
                    out.writeObject(new SerializedMessage("Полученный элемент не прошел валидацию на стороне сервера."));
                }
            }
            else {out.writeObject(new SerializedMessage("Элемента с таким ID нет в коллекции."));}
        } catch (NumberFormatException e) {
            out.writeObject(new SerializedMessage("Команда не выполнена. Вы ввели некорректный аргумент."));
        }

        logger.info(String.format("Клиенту %s:%s отправлен результат работы команды UPDATE", socket.getInetAddress(), socket.getPort()));
    }

    /**
     *
     * @param ID - удаление по ID.
     */
    public void remove_by_id(String ID) throws IOException {
        Long marineID;
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        try {
            marineID = Long.parseLong(ID);
            if (CollectionUtils.checkExist(marineID)) {
                collectionManager.remove_by_id(marineID);
                out.writeObject(new SerializedMessage("Элемент с ID " + marineID + " успешно удален из коллекции."));
            } else { out.writeObject(new SerializedMessage("Элемента с таким ID нет в коллекции."));}
        } catch (NumberFormatException e) {
            out.writeObject(new SerializedMessage("Команда не выполнена. Вы ввели некорректный аргумент."));
        }

        logger.info(String.format("Клиенту %s:%s отправлен результат работы команды REMOVE_BY_ID", socket.getInetAddress(), socket.getPort()));
    }

    public void clear() throws IOException {
        collectionManager.clear();
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        out.writeObject(new SerializedMessage("Коллекция успешно очищена."));
        logger.info(String.format("Клиенту %s:%s отправлен результат работы команды CLEAR", socket.getInetAddress(), socket.getPort()));
    }

    public void remove_greater(SpaceMarine spaceMarine) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        if (Validator.validateSpaceMarine(spaceMarine)) {
            out.writeObject(new SerializedMessage(collectionManager.remove_greater(spaceMarine)));
        } else {
            out.writeObject(new SerializedMessage("Полученный элемент не прошел валидацию на стороне сервера."));
        }

        logger.info(String.format("Клиенту %s:%s отправлен результат работы команды REMOVE_GREATER", socket.getInetAddress(), socket.getPort()));
    }

    public void remove_first() throws IOException{
        collectionManager.remove_first();
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        out.writeObject(new SerializedMessage("Выполнено."));
        logger.info(String.format("Клиенту %s:%s отправлен результат работы команды REMOVE_FIRST", socket.getInetAddress(), socket.getPort()));
    }

    public void average_of_health() throws IOException{
        String msg = collectionManager.average_of_health();
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        out.writeObject(new SerializedMessage(msg));
        logger.info(String.format("Клиенту %s:%s отправлен результат работы команды AVERAGE_OF_HEALTH", socket.getInetAddress(), socket.getPort()));
    }

    public void count_greater_than_weapon_type(Weapon weapon) throws IOException{
        String msg = collectionManager.count_greater_than_weapon_type(weapon);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        out.writeObject(new SerializedMessage(msg));
        logger.info(String.format("Клиенту %s:%s отправлен результат работы команды count_greater_than_weapon_type", socket.getInetAddress(), socket.getPort()));

    }

    public void filter_by_health(long targetHealth) throws IOException{
        String msg = collectionManager.filter_by_health(targetHealth);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        out.writeObject(new SerializedMessage(msg));
        logger.info(String.format("Клиенту %s:%s отправлен результат работы команды filter_by_health", socket.getInetAddress(), socket.getPort()));

    }


}

package CollectonUtils;

import BaseClass.SpaceMarine;
import BaseClass.Weapon;

import com.sun.deploy.security.SelectableSecurityManager;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class CollectionManager {
    private static LinkedList<SpaceMarine> linkedList;
    private List res = new ArrayList();
    private static ZonedDateTime creationDate;

    private static CollectionManager collectionManager;

    private CollectionManager() {}

    public static void initList() {
        if (linkedList == null) { linkedList = new LinkedList<>(); creationDate = ZonedDateTime.now(); }
    }

    public static LinkedList<SpaceMarine> getLinkedList() {
        return linkedList;
    }

    public static void add(SpaceMarine spaceMarine) {
        linkedList.add(spaceMarine);
    }


    public String getInfo() {
        String info = "";
        info += "Тип коллекции – " + linkedList.getClass().getName() + "\n";
        info += "Дата инициализации коллекции – " + creationDate + "\n";
        info += "Количество элементов в коллекции – " + linkedList.size() + "\n";
        info += "_________________________________________________________\n";

        return info;
    }

    public String show() {
        linkedList.sort(Comparator.comparing(SpaceMarine::getName));  // Сортировка коллекции по алфавиту.

        String info = linkedList
                .stream()
                .map(CollectionUtils::display)
                .collect(Collectors.joining(", ")    );
        if (info.equals("")) { info = "На данный момент коллекция пуста."; }
        return info;
    }



    public  void remove_by_id(long marineID) {
        linkedList.forEach(spaceMarine -> {
            if (spaceMarine.getId() == marineID) { linkedList.remove(spaceMarine); }
        });
    }

    public  void clear() {
        linkedList.clear();
    }



    public String remove_greater(SpaceMarine spaceMarine) {
        res.clear();
        linkedList.forEach(listStudyGroup -> {
            if (listStudyGroup.compareTo(spaceMarine) > 0) {
                appendToList(listStudyGroup.getId());
                linkedList.remove(listStudyGroup);
            }
        });

        if (res.isEmpty()) return "Таких элементов не найдено";
        return "Из коллекции удалены элементы с ID: " + res.toString().replaceAll("[\\[\\]]", "");
    }

    public  void update(SpaceMarine marineToUpdate, long elementId) {
        linkedList.forEach(spaceMarine -> {
            if (spaceMarine.getId() == elementId) {
                spaceMarine.setName(marineToUpdate.getName());
                spaceMarine.setCoordinates(marineToUpdate.getCoordinates());
                spaceMarine.setHealth(marineToUpdate.getHealth());
                spaceMarine.setCategory(marineToUpdate.getCategory());
                spaceMarine.setWeaponType(marineToUpdate.getWeaponType());
                spaceMarine.setMeleeWeapon(marineToUpdate.getMeleeWeapon());
                spaceMarine.setChapter(marineToUpdate.getChapter());
            }
        });
    }

    public  void remove_first() {
        if (linkedList.size() > 0) { linkedList.remove(0); }
    }

    public  String average_of_health(){
        String msg = "";
        if (linkedList.size() > 0){
            long healthSum = 0;
            for (SpaceMarine spaceMarine : linkedList) healthSum += spaceMarine.getHealth();
            msg = "Среднее значение здоровья: " + healthSum/(double)linkedList.size();
        }
        else {
            msg = "Коллекция пуста";
        }
        return msg;
    }

    public  String filter_by_health(long targetHealth){
        String msg = "";
        if (linkedList.size() > 0){
            int count = 0;
            for (SpaceMarine spaceMarine : linkedList) {
                if (spaceMarine.getHealth() == targetHealth){
                    msg += CollectionUtils.display(spaceMarine);
                    count++;
                }
            }
            if (count == 0) msg = "Бойцов с выбранным значением здоровья нет в коллекции";
        }
        else {
            msg = "Коллекция пуста";
        }
        return msg;
    }

    public  String count_greater_than_weapon_type(Weapon weapon){
        String res = "";
        if (linkedList.size() > 0){
            int count = 0;
            for (SpaceMarine spaceMarine : linkedList){
                if (spaceMarine.getWeaponType() != null && spaceMarine.getWeaponType().compareTo(weapon) > 0) count++;
            }
            res = "Результат: "+ count;
        }
        else {
            res = "Коллекция пуста";
        }
        return res;
    }

    public static SpaceMarine getMinElement() throws NullPointerException{
        if (linkedList.size() > 0){
            SpaceMarine result = linkedList.getFirst();
            for (SpaceMarine spaceMarine : linkedList){
                if (spaceMarine.compareTo(result) < 0) result = spaceMarine;
            }
            return result;
        }
        else throw new NullPointerException("Коллекция пуста");
    }

    public  void add_if_min(SpaceMarine spaceMarineToAdd){
        //String msg = "";
        try {
            SpaceMarine minElement = CollectionManager.getMinElement();
            if (spaceMarineToAdd.compareTo(minElement) < 0) {
                CollectionManager.add(spaceMarineToAdd);
                //System.out.println("Элемент успешно добавлен в коллекцию");
            } //else System.out.println("Элемент превосходит минимальный, не добавлен в коллекцию");
        }
        catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }
    }

    public  void addJsonObject(SpaceMarine spaceMarine) {
        spaceMarine.setId(IDGenerator.generateID((int) spaceMarine.getId()));
        linkedList.add(spaceMarine);
    }

    public static CollectionManager getCollectionManager() {
        if (collectionManager == null){
            collectionManager = new CollectionManager();
        }
        return collectionManager;
    }

    public void appendToList(Object o){
        res.add(o);
    }

}

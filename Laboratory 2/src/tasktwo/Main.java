package tasktwo;
import tasktwo.soldiers.Ivanov;
import tasktwo.soldiers.Petrov;
import tasktwo.soldiers.Necheporuk;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        ArrayList<Good> goods = new ArrayList<Good>();
        Storage outdoor = new Storage("outdoor");
        Storage van = new Storage("van");

        for (int i=0;i<20;i++){
            goods.add(new Good(i+1));
        }
        Storage warehouse = new Storage("warehouse",goods);
        Thread ivanov =new Thread(new Ivanov(warehouse, outdoor));
        Thread petrov = new Thread(new Petrov(outdoor,van));
        Thread necheporuk = new Thread(new Necheporuk(van));
        ivanov.setName("Ivanov");
        petrov.setName("Petrov");
        necheporuk.setName("Necheporuk");

        ivanov.start();
        petrov.start();
        necheporuk.start();
        try{
            ivanov.join();
            petrov.join();
            necheporuk.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        }
    }


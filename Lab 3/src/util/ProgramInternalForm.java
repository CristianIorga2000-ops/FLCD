package util;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

public class ProgramInternalForm extends ArrayList<Truple<String, Integer, Integer>>{

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        for(Truple<String, Integer, Integer> truple : this){
            str.append(truple).append("\n");
        }
        return str.toString();
    }
}

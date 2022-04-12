import java.io.Serializable;
import java.util.Hashtable;

public class ListOnline implements Serializable {
    private Hashtable<String, String> listOnline;


    public Hashtable<String, String> getListOnline() {
        return listOnline;
    }

    public void setListOnline(Hashtable<String, String> listOnline) {
        this.listOnline = listOnline;
    }
}

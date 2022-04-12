import java.io.Serializable;
import java.util.Hashtable;

public class Message implements Serializable {
    private String sender;
    private String text;
    private String receiver;
    private String type;
    private Hashtable<String, String> listOnline;
    private String request;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Hashtable<String, String> getListOnline() {
        return listOnline;
    }

    public void setListOnline(Hashtable<String, String> listOnline) {
        this.listOnline = listOnline;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
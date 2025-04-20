package GameLogic;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Message {
    static Logger LOGGER;
    static {
        try (FileInputStream ins = new FileInputStream("C:/Users/Глеб/IdeaProjects/UltraGame/log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(Message.class.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final Integer code;
    private final Integer data;
    private final Integer addData;
    private final Integer extraData;

    public Integer getCode() {
        return code;
    }

    public Integer getData() {
        return data;
    }

    public Integer getAddData() {
        return addData;
    }

    public Integer getExtraData() {
        return extraData;
    }

    public Message(){
        code = null;
        data = null;
        addData = null;
        extraData = null;
    }

    public Message(Integer code, Integer data, Integer addData, Integer extraData){
        this.code = code;
        this.data = data;
        this.addData = addData;
        this.extraData = extraData;
        LOGGER.log(Level.INFO, "Выслано сообщение состава: " + code + " " + data + " " + addData + " " +  extraData);
    }
}

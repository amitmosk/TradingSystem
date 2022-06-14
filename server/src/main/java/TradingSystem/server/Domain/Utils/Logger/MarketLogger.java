package TradingSystem.server.Domain.Utils.Logger;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MarketLogger {

    /*------------------------------------------------------ FIELDS ------------------------------------------------------*/

    private Logger logger;
    private FileHandler handler;

    /*------------------------------------------------ SINGLETON BUSINESS ------------------------------------------------*/
    private static class SingletonHolder{
        private static MarketLogger instance = new MarketLogger();
    }

    public static MarketLogger getInstance(){
        return MarketLogger.SingletonHolder.instance;
    }

    /*------------------------------------------------- CLASS CONSTRUCTOR -------------------------------------------------*/
    public MarketLogger() {
        this.logger = Logger.getLogger("Market Logger");
        try {
//            this.handler = new FileHandler("SystemLogger.txt");
            this.handler = new FileHandler("LogFiles/Market/MarketLogger.txt");
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);

        }
        catch(Exception e){}
    }

    /*-------------------------------------------------- CLASS FUNCTIONS --------------------------------------------------*/
    public void add_log(String info){
        this.logger.info(info);
    }


}


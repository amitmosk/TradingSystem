package Domain;

import java.util.List;

public class RequestHandler {
    private List<Request> request;
    private List<Request> handledRequest;

    private static class SingletonHolder{
        private static RequestHandler instance = new RequestHandler();
    }

    public static RequestHandler getInstance(){
        return RequestHandler.SingletonHolder.instance;
    }
}

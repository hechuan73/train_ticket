package ts.trainticket.httpUtils;

public class UrlProperties {

    public final static String clientIstioIp = "http://10.141.211.161:31380";
    // get all city name
    public final static String getAllCity = "/station/query";


    // All  G /D   other
    public final static String travel1Query = "/travel/query";
    public final static String travel2Query = "/travel2/query";

    public final static String login = "/login";
    public final static String register ="/register";

    public final static String findContacts = "/contacts/findContacts";


    public final static String orderQuery = "/order/query";

    public final static String createContacts = "/contacts/create";
    public final static String deleteContacts = "/contacts/deleteContacts";

    // submit order g/d
    public final static String gdPreserve = "/preserve";
    public final static String otherPreserve = "/preserveOther";

    // pay
    public final static String inside_payment = "/inside_payment/pay";
    // cancel order
    public final static String cancelOrder = "/cancelOrder";


    public final static String  getTravelAll = "/travel/queryAll";


}

package org.example.service;


public class RegisterUriService {

    private static RegisterUriService instance;
    private static String infoURI = "";
    private static String infoIP = "";

    private RegisterUriService() {}

    public static RegisterUriService getInstance() {
        if (instance == null) {
           instance = new RegisterUriService();
        }
        return  instance;
    }

    public boolean repeatedRequest(String infoURI, String infoIP) {

        boolean repated = ((RegisterUriService.infoIP.equals(infoIP)) && (RegisterUriService.infoURI.equals(infoURI)));

        if (repated) {
            RegisterUriService.infoIP = "";
            RegisterUriService.infoURI = "";
        } else {
            RegisterUriService.infoIP = infoIP;
            RegisterUriService.infoURI = infoURI;
        }

        return repated;
    }

}

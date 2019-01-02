package ts.trainticket.domain;

public class RegisterInfo {

    private String documentNum;
    private int documentType;
    private String email;
    private int gender;
    private String name;
    private String password;
    private String verificationCode;

    public RegisterInfo() {

    }

    public RegisterInfo(String email, String password) {
        this.email = email;
        this.password = password;

        gender = Gender.OTHER.getCode();
        name = "Default Name";
        password = "defaultPassword";
        documentType = DocumentType.NONE.getCode();
        documentNum = "0123456789";
        email = "352323";
    }


    public RegisterInfo(String documentNum, int documentType, String email, int gender, String name, String password, String verificationCode) {
        this.documentNum = documentNum;
        this.documentType = documentType;
        this.email = email;
        this.gender = gender;
        this.name = name;
        this.password = password;
        this.verificationCode = verificationCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDocumentType() {
        return documentType;
    }

    public void setDocumentType(int documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNum() {
        return documentNum;
    }

    public void setDocumentNum(String documentNum) {
        this.documentNum = documentNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}

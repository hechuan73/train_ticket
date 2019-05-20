package adminbasic.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@ToString
@Data
public class Contacts implements Serializable {

    private UUID id;

    private UUID accountId;

    private String name;

    private int documentType;

    private String documentNumber;

    private String phoneNumber;


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Contacts other = (Contacts) obj;
        return name.equals(other.getName())
                && accountId.equals(other.getAccountId())
                && documentNumber.equals(other.getDocumentNumber())
                && phoneNumber.equals(other.getPhoneNumber())
                && documentType == other.getDocumentType();
    }
}

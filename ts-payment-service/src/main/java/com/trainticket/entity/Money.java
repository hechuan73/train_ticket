package com.trainticket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

/**
 * @author fdse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Money {
    @Id
    @Column(name = "money_id", length = 36)
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    private String userId;
    private String money; //NOSONAR

}

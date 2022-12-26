package com.apka.kosciol.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "Recipient")
@Slf4j
public class Recipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recipient", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_user")
    private User userUser;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "firstName", length = 50)
    private String firstName;

    @Column(name = "lastName", length = 50)
    private String lastName;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    private List<Subscription> subscriptionList;

    public void setSubscriptionList(List<Subscription> subscriptionList) {
        try{
            this.subscriptionList.clear();
        }
        catch(Exception e){
            log.error(e.getMessage());
        }
        if (Objects.nonNull(subscriptionList)) {
            log.debug("setting subscription list {}", subscriptionList);
            subscriptionList.forEach(s -> s.setRecipient(this));
            this.subscriptionList = subscriptionList;
        }
    }
}
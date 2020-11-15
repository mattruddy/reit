package com.plaid.reit.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "end_user")
public class EndUser implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String email;

    @Column
    private String passDigest;

    @OneToOne(mappedBy = "endUser", cascade = CascadeType.ALL)
    private Account account;

    @OneToOne(mappedBy = "endUser", cascade = CascadeType.ALL)
    private Investor investor;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassDigest() {
        return passDigest;
    }

    public void setPassDigest(String passDigest) {
        this.passDigest = passDigest;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Investor getInvestor() {
        return investor;
    }

    public void setInvestor(Investor investor) {
        this.investor = investor;
    }
}

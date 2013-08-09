package no.nxc.bank.server.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
class User implements XMLEntity {
    private int key;
    private double cash;
    private String name;
    private String passHash;


    public int getKey() {
        return key;
    }
    @XmlElement
    public void setKey(int key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }
    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public double getCash() {
        return cash;
    }
    @XmlElement
    public void setCash(double cash) {
        this.cash = cash;
    }

    public String getPassHash() {
        return passHash;
    }

    @XmlElement
    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

}

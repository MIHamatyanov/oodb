package lab3.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "banks")
public class Collection {
    private List<Bank> banks;

    public Collection() {
    }


    @XmlElement(name = "bank")
    public List<Bank> getBanks() {
        return banks;
    }

    public void setBanks(List<Bank> banks) {
        this.banks = banks;
    }

    public void addBank(Bank bank) {
        banks.add(bank);
    }
}

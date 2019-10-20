package lab3.model;

import javax.xml.bind.annotation.XmlElement;

public class Worker extends Person {
    private Long id;
    private String position;
    private String code;

    public Worker() {
    }

    public Worker(String firstName, String lastName, String phoneNumber, String email, String position, String code) {
        super(firstName, lastName, phoneNumber, email);
        this.position = position;
        this.code = code;
    }

    @XmlElement(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement(name = "position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @XmlElement(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                super.toString() +
                ", position='" + position + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}

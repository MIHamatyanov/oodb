package lab6.model;

import lab6.annotations.Column;
import lab6.annotations.Entity;

@Entity
public class Worker extends Person {
    @Column
    private Long id;
    @Column
    private String position;
    @Column
    private String code;

    public Worker(String firstName, String lastName, String phoneNumber, String email, String position, String code) {
        super(firstName, lastName, phoneNumber, email);
        this.position = position;
        this.code = code;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

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

package lab5.model;

public class Bank {
    private Long id;
    private String name;
    private Worker worker;
    private Client client;

    public Bank(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Bank(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Bank {" +
                "\n id=" + id +
                ",\n name='" + name + '\'' +
                ",\n worker=" + worker +
                ",\n client=" + client +
                "\n}";
    }
}

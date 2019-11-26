package lab6.model;

import lab6.annotations.Column;
import lab6.annotations.Entity;
import lab6.annotations.OneToMany;

import java.util.List;

@Entity
public class Bank {
    @Column
    private Long id;
    @Column
    private String name;

    @OneToMany
    @Column
    private List<Worker> workers;

    @OneToMany
    @Column
    private List<Client> clients;

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

    public List<Worker> getWorkers() {
        return workers;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void addWorker(Worker worker) {
        if (!workers.contains(worker)) {
            workers.add(worker);
        }
    }

    public void addClient(Client client) {
        if (!clients.contains(client)) {
            clients.add(client);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bank { id = " + id + ", name='" + name + "'\nworkers: [\n");
        for (Worker worker : workers) {
            sb.append(worker.toString() + "\n");
        }
        sb.append("]\nclients: [\n");
        for (Client client : clients) {
            sb.append(client.toString() + "\n");
        }
        sb.append("]\n}");
        return sb.toString();
    }
}

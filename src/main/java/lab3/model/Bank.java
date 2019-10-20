package lab3.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

public class Bank {
    private Long id;
    private String name;
    private List<Worker> workers;
    private List<Client> clients;

    @XmlElement(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement(name = "bank")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElementWrapper(name = "workers")
    @XmlElement(name = "worker")
    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    @XmlElementWrapper(name = "clients")
    @XmlElement(name = "client")
    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
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

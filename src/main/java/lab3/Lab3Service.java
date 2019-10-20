package lab3;

import lab3.model.Bank;
import lab3.model.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Lab3Service {
    private static final File FILE = new File("src/main/java/lab3/banks.xml");

    static void save(Collection collection) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Collection.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            marshaller.marshal(collection, FILE);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    static Collection load() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Collection.class);
            Unmarshaller un = jaxbContext.createUnmarshaller();

            return (Collection) un.unmarshal(FILE);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }

    static Bank findBankByName(List<Bank> banks, String name) {
        return banks.stream().filter(bank -> bank.getName().equals(name)).findFirst().orElse(null);
    }

    static List<Bank> sortByName(List<Bank> banks) {
        return banks.stream().sorted(Comparator.comparing(Bank::getName)).collect(Collectors.toList());
    }
}

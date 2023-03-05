package homework;


import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {

    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private final NavigableMap<Customer, String> customerDataMap = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        if(customerDataMap.firstEntry() == null) {
            return null;
        }
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        return new LocalEntry(customerDataMap.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> higherEntry = customerDataMap.higherEntry(customer);
        if (higherEntry != null) {
            return new LocalEntry(higherEntry);
        }
        return null;
    }

    public void add(Customer customer, String data) {
        customerDataMap.put(new Customer(customer), data);
    }

    static class LocalEntry implements Map.Entry<Customer, String>{
        private final Customer key;
        private String value;

        public LocalEntry(Map.Entry<Customer, String> entry) {
            this.key = new Customer(entry.getKey());
            this.value = entry.getValue();
        }

        @Override
        public Customer getKey() {
            return key;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public String setValue(String value) {
            return this.value = value;
        }
    }
}

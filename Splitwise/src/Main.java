import java.util.*;

public class Main {
    public static void main(String[] args) {

        User user1 = new User(1,"Ganesh");
        User user2 = new User(2,"Pavan");
        User user3 = new User(3,"Gaurav");

        List<Payment> initialTransations = new ArrayList<>();
        List<Payment> minimalTransations = new ArrayList<>();

        initialTransations.add(new Payment(user1.id,user2.id,100));
        initialTransations.add(new Payment(user2.id,user3.id,50));
        initialTransations.add(new Payment(user2.id,user1.id,75));
        initialTransations.add(new Payment(user3.id,user1.id,90));
        initialTransations.add(new Payment(user3.id,user2.id,40));
        initialTransations.add(new Payment(user1.id,user3.id,80));

        HashMap<Integer, Double> userAmount = new HashMap<>();

        for(Payment it : initialTransations){
            int senderId = it.fromUserId;
            int receiverId = it.toUserId;
            double senderAmount = 0.0;
            if(userAmount.containsKey(senderId)) userAmount.get(senderId);
            double receiverAmount = 0.0;
            if(userAmount.containsKey(receiverId)) userAmount.get(receiverId);
            userAmount.put(senderId,senderAmount+it.amount);
            userAmount.put(receiverId,receiverAmount-it.amount);
        }

        PriorityQueue<Pair<Double, Integer>> credits = new PriorityQueue<>((a, b) -> Double.compare(b.getKey(), a.getKey()));
        PriorityQueue<Pair<Double, Integer>> debts = new PriorityQueue<>((a, b) -> Double.compare(b.getKey(), a.getKey()));
        for (Map.Entry<Integer, Double> entry : userAmount.entrySet()) {
            int userId = entry.getKey();
            double amount = entry.getValue();
            if(amount>0) credits.add(new Pair<>(amount, userId));
            else debts.add(new Pair<>(Math.abs(amount), userId));
        }

        double amountTransferred;
        Vector<Payment> transactions = new Vector<>();

        while (!credits.isEmpty()) {
            Pair<Double, Integer> receiver = credits.poll();
            Pair<Double, Integer> sender = debts.poll();

            amountTransferred = Math.min(sender.getKey(), receiver.getKey());

            transactions.add(new Payment(sender.getValue(), receiver.getValue(), amountTransferred));

            sender = new Pair<>(sender.getKey() - amountTransferred, sender.getValue());
            receiver = new Pair<>(receiver.getKey() - amountTransferred, receiver.getValue());

            if (sender.getKey() != 0)
                debts.add(sender);

            if (receiver.getKey() != 0)
                credits.add(receiver);
        }

        for (Payment transaction : transactions) {
            System.out.println(transaction.fromUserId + " " + transaction.toUserId + " " + transaction.amount);
        }
    }

    static class Pair<K, V> {
        private final K key;
        private final V value;
        Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
        K getKey() {
            return key;
        }
        V getValue() {
            return value;
        }

    }
}
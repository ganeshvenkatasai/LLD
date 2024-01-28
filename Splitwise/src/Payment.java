class Payment {
    int fromUserId;
    int toUserId;
    double amount;

    Payment(int fromUserId, int toUserId, double amount){
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amount = amount;
    }
}

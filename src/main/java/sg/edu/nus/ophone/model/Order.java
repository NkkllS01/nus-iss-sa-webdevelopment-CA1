package sg.edu.nus.ophone.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//code by Team3.Chen Sirui
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(length = 30)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "total_amount")
    private double totalAmount;

    //    @ManyToOne
//    @JoinColumn(name = "status", referencedColumnName = "id")
    private String orderStatus;

    @OneToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private PaymentRecord paymentRecord;

    @OneToMany(mappedBy = "order")
    private List<OrderDetails> orderDetails = new ArrayList<>();

    @OneToOne(mappedBy = "order")
    private Shipping shipping;

    // constructors
    public Order() {
    }

    public Order(User user, String orderDate) {
        this.user = user;
        this.orderDate = LocalDate.parse(orderDate);
        this.orderStatus = "In cart";
    }

    // getters and setters
    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails != null ? orderDetails : new ArrayList<>();
        if (orderDetails != null && !orderDetails.isEmpty()) {
            this.totalAmount = orderDetails.stream().map(OrderDetails::getAmount).reduce(0.0, Double::sum);
        } else {
            this.totalAmount = 0.0;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public PaymentRecord getPayment() {
        return paymentRecord;
    }

    public void setPayment(PaymentRecord paymentRecord) {
        this.paymentRecord = paymentRecord;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

}




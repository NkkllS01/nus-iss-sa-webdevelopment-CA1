package sg.edu.nus.ophone.model;

import jakarta.persistence.*;

import java.time.LocalDate;

//code by Team3.Song Jingze
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 1000)
    private String comment;

    private int rating;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    public Review() {}

    public Review(Product product, User user, int rating, String comment, String date) {
        this.product = product;
        this.user = user;
        this.rating = rating;
        this.comment = comment;
        this.date = LocalDate.parse(date);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String toString() {
        return "Review [id = " + id + ", rating = " + rating +
                ", comment = " + comment + ", date = " + date + "]";
    }
}


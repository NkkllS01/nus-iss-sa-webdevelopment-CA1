package sg.edu.nus.ophone.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import sg.edu.nus.ophone.interfacemethods.OrderInterface;
import sg.edu.nus.ophone.model.Order;
import sg.edu.nus.ophone.model.OrderDetails;
import sg.edu.nus.ophone.model.PaymentRecord;
import sg.edu.nus.ophone.model.Shipping;
import sg.edu.nus.ophone.service.OrderImplementation;
import sg.nus.iss.example.workshop.service.OrderDetailService;
import sg.nus.iss.example.workshop.service.OrderService;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
public class OrderController {
    @Autowired
    private OrderInterface orderService;
    
    @Autowired
    private OrderService orderService1;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    public void setOrderService(OrderImplementation orderImp) {
        this.orderService = orderImp;
    }

    @GetMapping ("/orders")
    // Http session data to store userId as an attribute
    public String displayOrders(Model model, HttpSession session, Locale locale) {
//        int username = (int) session.getAttribute("username");
//        List<Order> orders = orderService.findByUserId(username);
        List<Order> orders = orderService.findByUserId(1);
        List<Order> orderList = orders.stream()
                .filter(order -> !order.getOrderStatus().equalsIgnoreCase("In cart"))
                        .toList();
        model.addAttribute("orders", orderList);
        return "order-history";
    }
    
    /**
     * OrderController
     * 
     * 
     * Created by: LianDa,GaoZijie
     * Created on: 10/09/2024
     */
    @GetMapping("/cart")
    public String viewCart(@RequestParam Long userId, Model model) {
        
        Order cart = orderService1.getCartByUserId(userId);
        if (cart != null) {
            model.addAttribute("cart", cart);
            model.addAttribute("orderDetails", cart.getOrderDetails());
            return "cart";  
        } else {
            return "error";
        }
    }
    @PostMapping("/cart/remove/{productId}")
    public String removeCartItem(@RequestParam Long orderId, @PathVariable Long productId, Model model) {
        try {
            Order cart = orderService1.getOrderById(orderId);
            if (cart == null) {
                model.addAttribute("errorMessage", "Order not found.");
                return "error";  
            }
            boolean isRemoved = orderDetailService.removeOrderDetail(orderId, productId);
            if (!isRemoved) {
                model.addAttribute("errorMessage", "Order detail could not be removed.");
                return "error";  
            }
            return "redirect:/orders/cart?userId=" + cart.getUser().getId();
            
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Something went wrong. Please try again.");
            return "error";  
        }
    }
    @PostMapping("/cart/submit")
    @Transactional
    public String submitCart(@RequestParam Long userId, Model model) {
        Order cart = orderService1.getCartByUserId(userId);
        if (cart != null && "cart".equals(cart.getStatus())) {
            orderService1.createOrder(cart); 
            return "order_submitted";  
        } else {
            return "error"; 
        }
    }
    @PostMapping("/cart/updateQuantity/{productId}")
    public String updateCartItemQuantity(
            @RequestParam Long userId, 
            @PathVariable Long productId, 
            @RequestParam Integer quantity) {
        
        Order cart = orderService1.getCartByUserId(userId);
        if (cart != null) {
            orderDetailService.updateQuantity(cart.getUser().getId(), productId, quantity);
        }
        return "redirect:/orders/cart?userId=" + userId;  
    }
    

    @GetMapping("/orders/{id}")
    public String displayOrderDetails(Model model, @PathVariable("id") Long orderId, Locale locale) {
        Order order = orderService.findByOrderId(orderId);

        // get and add order details to model
        model.addAttribute("orderId", orderId);
        Double gst = (order.getTotalAmount() / 109) * 9;
        model.addAttribute("order", order);
        model.addAttribute("gst", gst);
        PaymentRecord paymentRecord = order.getPayment();
        model.addAttribute("payment", paymentRecord);
        Shipping shipping = order.getShipping();
        model.addAttribute("shipping", shipping);
        List<OrderDetails> orderDetails = orderService.findByOrder(order);
        model.addAttribute("orderDetails", orderDetails);

        return "order-details";
    }

    @GetMapping("/orders/{id}/cancel")
    public String cancelOrder(@PathVariable("id") Long orderId, Model model, HttpSession session) {
        model.addAttribute("orderId", orderId);
        Order order = orderService.findByOrderId(orderId);
         if (order == null) {
             model.addAttribute("errorMessage", "Order not found.");
             return "redirect:/orders";
         }

         Shipping shipping = order.getShipping();
         String shippingStatus = shipping.getShippingStatus();

         if (!shippingStatus.equalsIgnoreCase("Shipped") &&
            !shippingStatus.equalsIgnoreCase("Delivered") &&
            !shippingStatus.equalsIgnoreCase("Cancelled")) {
             // cancel order (set order, payment, shipping status)
             orderService.cancelOrder(order);

             // get and add order details to model
             Double gst = (order.getTotalAmount() / 109) * 9;
             model.addAttribute("order", order);
             model.addAttribute("gst", gst);
             Payment payment = order.getPayment();
             model.addAttribute("payment", payment);
             model.addAttribute("shipping", shipping);
             List<OrderDetails> orderDetails = orderService.findByOrder(order);
             model.addAttribute("orderDetails", orderDetails);
             return "order-cancel";
         } else {
             model.addAttribute("errorMessage",
                     "Your order cannot be cancelled as it has already been " +
                             shippingStatus.toLowerCase() + ".");
             return "order-cancel-fail";
         }
     }

     @GetMapping("/product/{id}/review")
    public String reviewProduct(@PathVariable("id") Long productId, Model model, HttpSession session) {
        return "product-review";
     }

}



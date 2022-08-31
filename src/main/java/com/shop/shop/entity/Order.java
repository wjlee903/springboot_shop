package com.shop.shop.entity;

import com.shop.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;  // 한 명의 회원은 여러번 주문을 할 수 있으므로 주문 엔티티 기준에서 단일 단방향 매핑

    private LocalDateTime orderDate;    // 주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;    // 주문상태

    /**
     * oneToMany의 FetchType은 LAZY 방식이 default
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    // OrderItem에 있는 order를 의미
    private List<OrderItem> orderItems = new ArrayList<>();

//    private LocalDateTime regTime;

//    private LocalDateTime updateTime;
}

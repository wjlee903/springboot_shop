package com.shop.shop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
public class CartItem extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;  // 하나의 장바구니에는 여러 개의 상품을 담을 수 있으므로 다대일 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;  // 장바구니에 담을 상품의 정보를 알아야하므로 상품 엔티티를 매핑, 하나의 상품은 여러 장바구니의 장바구니 상품으로 담길수 있으므로 다대일 관계

    private int count;
}

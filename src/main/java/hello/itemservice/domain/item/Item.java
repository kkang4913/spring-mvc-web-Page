package hello.itemservice.domain.item;

import lombok.Data;

/**
 * Item 상품 객체
 */
@Data
//@Getter @Setter
public class Item {

    private Long Id;            //유저 아이디
    private String itemName;    //유저 이름
    private Integer price;      //상품 가격, Integer 쓰는 이유는 가격이 들어가지 않을 수도 있다는 가정
    private Integer quantity;   //상품 수량, 수량이 없을수도 있다라는 가정


    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}

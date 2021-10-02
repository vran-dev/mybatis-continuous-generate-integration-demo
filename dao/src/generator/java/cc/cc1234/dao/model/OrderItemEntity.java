package cc.cc1234.dao.model;

import java.time.LocalDateTime;
import lombok.Data;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table order_item
 */
@Data
public class OrderItemEntity {
    /**
     * Database Column Remarks:
     *   id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_item.id
     *
     * @mbg.generated Sat Oct 02 10:48:46 CST 2021
     */
    private Long id;

    /**
     * Database Column Remarks:
     *   订单号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_item.order_no
     *
     * @mbg.generated Sat Oct 02 10:48:46 CST 2021
     */
    private String orderNo;

    /**
     * Database Column Remarks:
     *   订单id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_item.order_id
     *
     * @mbg.generated Sat Oct 02 10:48:46 CST 2021
     */
    private Long orderId;

    /**
     * Database Column Remarks:
     *   商品名称
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_item.goods_name
     *
     * @mbg.generated Sat Oct 02 10:48:46 CST 2021
     */
    private String goodsName;

    /**
     * Database Column Remarks:
     *   商品数量
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_item.goods_quantity
     *
     * @mbg.generated Sat Oct 02 10:48:46 CST 2021
     */
    private Integer goodsQuantity;

    /**
     * Database Column Remarks:
     *   商品单价，单位 分
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_item.goods_price
     *
     * @mbg.generated Sat Oct 02 10:48:46 CST 2021
     */
    private Integer goodsPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_item.create_at
     *
     * @mbg.generated Sat Oct 02 10:48:46 CST 2021
     */
    private LocalDateTime createAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_item.update_at
     *
     * @mbg.generated Sat Oct 02 10:48:46 CST 2021
     */
    private LocalDateTime updateAt;
}
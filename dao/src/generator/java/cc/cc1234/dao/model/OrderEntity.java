package cc.cc1234.dao.model;

import cc.cc1234.dao.enums.OrderStatus;
import java.time.LocalDateTime;
import lombok.Data;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table order
 */
@Data
public class OrderEntity {
    /**
     * Database Column Remarks:
     *   id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.id
     *
     * @mbg.generated Sat Oct 02 10:48:46 CST 2021
     */
    private Long id;

    /**
     * Database Column Remarks:
     *   userId
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.user_id
     *
     * @mbg.generated Sat Oct 02 10:48:46 CST 2021
     */
    private Long userId;

    /**
     * Database Column Remarks:
     *   订单号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.order_no
     *
     * @mbg.generated Sat Oct 02 10:48:46 CST 2021
     */
    private String orderNo;

    /**
     * Database Column Remarks:
     *   状态
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.status
     *
     * @mbg.generated Sat Oct 02 10:48:46 CST 2021
     */
    private OrderStatus status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.create_at
     *
     * @mbg.generated Sat Oct 02 10:48:46 CST 2021
     */
    private LocalDateTime createAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.update_at
     *
     * @mbg.generated Sat Oct 02 10:48:46 CST 2021
     */
    private LocalDateTime updateAt;
}
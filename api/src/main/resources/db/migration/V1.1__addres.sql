CREATE TABLE address
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    user_id    INT          NOT NULL,
    street     VARCHAR(100) NOT NULL,
    city       VARCHAR(100) NOT NULL,
    country    VARCHAR(100) NOT NULL,
    post_code  VARCHAR(10),
    is_default BOOLEAN      NOT NULL DEFAULT FALSE,
    create_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
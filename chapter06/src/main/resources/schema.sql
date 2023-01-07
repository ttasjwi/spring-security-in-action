CREATE TABLE IF NOT EXISTS `spring`.`member` (
    member_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    password TEXT NOT NULL,
    algorithm VARCHAR(45) NOT NULL,
    PRIMARY KEY (member_id)
);

CREATE TABLE IF NOT EXISTS `spring`.`authority` (
    authority_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    member_id BIGINT NOT NULL,
    PRIMARY KEY (authority_id)
);


CREATE TABLE IF NOT EXISTS `spring`.`product` (
    product_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    price DECIMAL(64,0) NOT NULL,
    currency VARCHAR(45) NOT NULL,
    PRIMARY KEY (product_id)
);

ALTER TABLE `spring`.`authority`
ADD FOREIGN KEY (member_id) references `spring`.`member` (member_id);

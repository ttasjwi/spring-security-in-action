INSERT IGNORE INTO `spring`.`member` (member_id, name, password)
VALUES ('1', 'john', '{bCrypt}$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG');

INSERT IGNORE INTO `spring`.`authority` (authority_id, name, member_id)
VALUES ('1', 'READ', '1');

INSERT IGNORE INTO `spring`.`authority` (authority_id, name, member_id)
VALUES ('2', 'WRITE', '1');

INSERT IGNORE INTO `spring`.`product` (product_id, name, price, currency)
VALUES ('1', 'Chocolate', 10, 'USD');

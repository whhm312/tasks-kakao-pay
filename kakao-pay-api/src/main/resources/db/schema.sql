CREATE TABLE `kakao_pay`.`TBL_LUCK` (
  `SEQ` INT NOT NULL AUTO_INCREMENT COMMENT '유효번호',
  `BLESSER_ID` VARCHAR(45) NOT NULL COMMENT '뿌린이 ID',
  `ROOM_ID` VARCHAR(45) NOT NULL COMMENT '대화방 ID',
  `AMOUNT` INT NOT NULL COMMENT '뿌린 총 금액',
  `MAX_GRABBER_COUNT` INT NOT NULL COMMENT '잡을 수 있는 대상자 숫자',
  `BLESSING_DATE` DATE NOT NULL COMMENT '뿌린 일시',
  `TOKEN` VARCHAR(3) NOT NULL COMMENT '토큰',
  PRIMARY KEY (`SEQ`))
COMMENT = '행운';

CREATE TABLE `kakao_pay`.`TBL_LUCK_DETAIL` (
  `SEQ` INT NOT NULL AUTO_INCREMENT COMMENT '유효번호',
  `LUCK_SEQ` INT NOT NULL COMMENT '행운 유효번호',
  `AMOUNT` INT NOT NULL COMMENT '뿌린 총 금액',
  PRIMARY KEY (`SEQ`))
COMMENT = '행운 금액 상세';

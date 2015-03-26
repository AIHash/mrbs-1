set global log_bin_trust_function_creators = 1;

ALTER TABLE `icd10dic` ADD COLUMN `py_code`  varchar(255) NULL AFTER `diagnosis`;

DROP TABLE IF EXISTS `pinyin`;

CREATE TABLE `pinyin` (
  `letter` char(1) NOT NULL,
  `chinese` char(1) NOT NULL,
  PRIMARY KEY  (`letter`)
)DEFAULT CHARSET=gbk;

insert  into `pinyin`(`letter`,`chinese`) values
('A','驁'),
('B','簿'),
('C','錯'),
('D','鵽'),
('E','樲'),
('F','鰒'),
('G','腂'),
('H','夻'),
('J','攈'),
('K','穒'),
('L','鱳'),
('M','旀'),
('N','桛'),
('O','漚'),
('P','曝'),
('Q','囕'),
('R','鶸'),
('S','蜶'),
('T','籜'),
('W','鶩'),
('X','鑂'),
('Y','韻'),
('Z','咗');

DELIMITER $$
DROP FUNCTION IF EXISTS `PINYIN`$$
CREATE FUNCTION `PINYIN`(str CHAR(255)) RETURNS char(255)
BEGIN
DECLARE hexCode char(4);
DECLARE pinyin varchar(255);
DECLARE firstChar char(1);
DECLARE aChar char(1);
DECLARE pos int;
DECLARE strLength int;

SET pinyin    = '';
SET strLength = CHAR_LENGTH(LTRIM(RTRIM(str)));
SET pos       = 1;
SET @str      = (CONVERT(str USING gbk));
WHILE pos <= strLength DO
        SET @aChar = SUBSTRING(@str,pos,1);
        SET hexCode = HEX(@aChar);

    IF hexCode >= "8140" AND hexCode <= "FEA0" THEN
        SELECT letter into firstChar
        FROM   pinyin
        WHERE  chinese >= @aChar
        LIMIT  1;
	ELSE SELECT @aChar into firstChar ;
	END IF;

    SET pinyin = CONCAT(pinyin,firstChar);
    SET pos = pos + 1;
END WHILE;

RETURN UPPER(pinyin);
END$$
DELIMITER ;

UPDATE icd10dic SET py_code = PINYIN(diagnosis);
UPDATE unified_user SET py_code = PINYIN(`name`);
UPDATE department SET py_code = PINYIN(`name`) WHERE id != 1;
DROP FUNCTION IF EXISTS `PINYIN`;
DROP TABLE IF EXISTS `pinyin`;

set global log_bin_trust_function_creators = 0;

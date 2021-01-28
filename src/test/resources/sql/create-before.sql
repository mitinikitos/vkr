DELETE FROM ship;
DELETE FROM own_operator;

INSERT INTO own_operator(name, address, phones, email, fax) VALUES
('FirstName', 'FirstAddress', '{"89876543210"}', 'qwerty@mail.ru', '{"89876543210"}'),
('SecondName', 'SecondAddress', '{"89012345678"}', 'asdfgh@mail.ru', '{"89012345678"}');

INSERT INTO ship(reg_num, name, type, imo, god_p, own_name, operator_name) VALUES
(1, 'FirstName', 'type', 123, 2010, 'FirstName', 'SecondName'),
(2, 'SecondName', 'type', 123, 2010, 'FirstName', 'FirstName'),
(3, 'ThirdName', 'type', 123, 2012, 'SecondName', 'FirstName'),
(4, 'FourthName', 'type', 123, 2015, 'SecondName', 'SecondName');
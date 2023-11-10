insert into entity.users (login, password, fullname, role)
VALUES ('test_user_login',  '$2a$12$nGwpF/6/pVCIdPHJudSNcOvu8DAlCiGxRJINZsJxHPXzRfUSRr3IC', 'test_user_full_name', 'USER');
insert into entity.users (login, password, fullname, role)
VALUES ('test_admin_login',  '$2a$12$nGwpF/6/pVCIdPHJudSNcOvu8DAlCiGxRJINZsJxHPXzRfUSRr3IC', 'test_admin_full_name', 'ADMIN');
insert into entity.shipping_company (id,name, phone)
VALUES ('0bcebc21-f51d-4e17-a917-0e0b19f57506','first_company', '89991234567');
insert into entity.route (id, departure_point, destination_point, shipping_company_id, route_duration_in_min)
VALUES ('71ff8607-6360-4f15-acb9-312a6081a6f5', 'start','finish', '0bcebc21-f51d-4e17-a917-0e0b19f57506', '100');
insert into entity.ticket (id, route_id, date_time, place_number, price)
VALUES ('33227e17-84d1-4129-82b6-f3f313ad72b4','71ff8607-6360-4f15-acb9-312a6081a6f5', '2024-04-27T21:39:48', '45', '500')
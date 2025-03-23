DELETE FROM locations where id > 1000000;

INSERT INTO locations (id, name, lat, lng, type)
VALUES (1000001, 'demo_data1', -80, 170, 0),
       (1000002, 'demo_data2', -70, -170, 1),
       (1000003, 'demo_data3', 80, -170, 1),
       (1000004, 'demo_data4', 70, 170, 0),
       (1000005, 'demo_data5', 29, -30, 0);
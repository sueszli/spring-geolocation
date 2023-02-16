DELETE FROM locations where id > 1000000;

INSERT INTO locations (id, name, lat, lng, type)
VALUES (1000001, 'demo_data', -80, 170, 0),
       (1000002, 'demo_data', -70, -170, 1),
       (1000003, 'demo_data', 80, -170, 1),
       (1000004, 'demo_data', 70, 170, 0),
       (1000005, 'demo_data', 29, -30, 0),
       (1000006, 'demo_data', 30, 30, 1),
       (1000007, 'demo_data', -40, 30, 1),
       (1000008, 'demo_data', -30, -30, 0),
       (1000009, 'demo_data', 0, 0, 0),
       (1000010, 'demo_data', 12, 65, 1);

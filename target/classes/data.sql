-- Insert drones
INSERT INTO DRONE (SERIAL_NUMBER, MODEL, WEIGHT_LIMIT, BATTERY_CAPACITY, STATE) VALUES
    ('SN001', 'LIGHTWEIGHT', 200, 100, 'LOADED'),
    ('SN002', 'MIDDLEWEIGHT', 300, 90, 'LOADED'),
    ('SN003', 'CRUISERWEIGHT', 400, 80, 'LOADED'),
    ('SN004', 'HEAVYWEIGHT', 500, 70, 'DELIVERED'),
    ('SN005', 'LIGHTWEIGHT', 200, 60, 'LOADED'),
    ('SN006', 'MIDDLEWEIGHT', 300, 50, 'LOADING'),
    ('SN007', 'CRUISERWEIGHT', 400, 40, 'IDLE'),
    ('SN008', 'HEAVYWEIGHT', 500, 30, 'DELIVERING'),
    ('SN009', 'LIGHTWEIGHT', 200, 20, 'IDLE'),
    ('SN010', 'MIDDLEWEIGHT', 300, 10, 'RETURNING');

-- Insert payloads
INSERT INTO PAYLOAD_BASE (ID, WEIGHT, DRONE_ID, PAYLOAD_TYPE) VALUES
    (1, 50, 1, 'MEDICATION'),
    (2, 75, 2, 'MEDICATION'),
    (3, 100, 3, 'MEDICATION'),
    (4, 125, 4, 'MEDICATION'),
    (5, 150, 5, 'MEDICATION'),
    (6, 175, 6, 'MEDICATION'),
    (8, 225, 8, 'MEDICATION');

-- Insert medications
INSERT INTO MEDICATION (ID, NAME, CODE, IMAGE) VALUES
    (1, 'Med1', 'MED1', 'image1.png'),
    (2, 'Med2', 'MED2', 'image2.png'),
    (3, 'Med3', 'MED3', 'image3.png'),
    (4, 'Med4', 'MED4', 'image4.png'),
    (5, 'Med5', 'MED5', 'image5.png'),
    (6, 'Med6', 'MED6', 'image6.png'),
    (8, 'Med8', 'MED8', 'image8.png');

ALTER TABLE payload_base ALTER COLUMN id RESTART WITH 11;
ALTER TABLE drone ALTER COLUMN id RESTART WITH 11;
ALTER TABLE battery_audit ALTER COLUMN id RESTART WITH 11;
-- Insert battery audits
INSERT INTO BATTERY_AUDIT (DRONE_ID, BATTERY_LEVEL, TIMESTAMP) VALUES
    (1, 100, '2023-10-01 10:00:00'),
    (2, 90, '2023-10-01 10:00:00'),
    (3, 80, '2023-10-01 10:00:00'),
    (4, 70, '2023-10-01 10:00:00'),
    (5, 60, '2023-10-01 10:00:00'),
    (6, 50, '2023-10-01 10:00:00'),
    (7, 40, '2023-10-01 10:00:00'),
    (8, 30, '2023-10-01 10:00:00'),
    (9, 20, '2023-10-01 10:00:00'),
    (10, 10, '2023-10-01 10:00:00');
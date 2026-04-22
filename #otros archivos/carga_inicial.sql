INSERT INTO customers (id_customer, email, fullname, phone, rfc) VALUES
(gen_random_uuid(), 'juan.perez@gmail.com', 'Juan Pérez', '5512345678', 'PEPJ800101ABC'),
(gen_random_uuid(), 'maria.lopez@gmail.com', 'María López', '5523456789', 'LOPM850202DEF'),
(gen_random_uuid(), 'carlos.sanchez@gmail.com', 'Carlos Sánchez', '5534567890', 'SACJ900303GHI'),
(gen_random_uuid(), 'ana.torres@gmail.com', 'Ana Torres', '5545678901', 'TOAA920404JKL'),
(gen_random_uuid(), 'luis.ramirez@gmail.com', 'Luis Ramírez', '5556789012', 'RALU870505MNO'),
(gen_random_uuid(), 'alexhizdev@gmail.com', 'Alexhiz Jimenez', '9713488633', 'JIJA951024HK6');


INSERT INTO rooms (id_room, number, type, price, beds, description, available) VALUES
(gen_random_uuid(), '101', 'STANDARD', 800.00, 1, 'Habitación estándar cómoda, ideal para una persona.', true),

(gen_random_uuid(), '102', 'SILVER', 1200.00, 2, 'Habitación silver con dos camas, perfecta para compartir.', true),

(gen_random_uuid(), '201', 'GOLD', 2500.00, 4, 'Habitación gold amplia con mayor capacidad y confort.', true),

(gen_random_uuid(), '202', 'PLATINUM', 3000.00, 3, 'Habitación platinum con servicios premium y vista exterior.', true),

(gen_random_uuid(), '301', 'DELUX', 3500.00, 5, 'Suite deluxe con lujo, espacio amplio y excelente vista.', true),

(gen_random_uuid(), '302', 'BRONZE', 700.00, 1, 'Habitación económica tipo bronze, funcional y accesible.', true);

--12345678 es el password
INSERT INTO users (id_user, username, password, role, enabled) VALUES 
(gen_random_uuid(),'alexis', '$2a$12$DGalZtNa5GTI4bbSeuCpPuO7XCV.GXj6BSqsIwcCbZIXMrJKl1Hce', 'ADMIN', true);


CREATE OR REPLACE FUNCTION fn_list_reservations () 
 RETURNS TABLE (quantity INT, reservationdate TEXT) 
AS $$
BEGIN
    RETURN QUERY 
    SELECT 
        (count(*)::int), 
        to_char(check_in_date, 'dd/MM/yyyy')
    FROM reservations
    GROUP BY 2 -- Agrupa por el segundo campo (la fecha formateada)
    ORDER BY 2 ASC;
END; $$ 
LANGUAGE 'plpgsql';
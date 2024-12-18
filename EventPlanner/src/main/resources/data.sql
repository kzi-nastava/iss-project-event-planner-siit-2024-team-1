INSERT INTO public."user" (active, id, latitude, longitude, number, role, token_expiration, user_type, activation_token, authorities, city, company, description, name, password, phone_number, photo, street, surname, username) VALUES (true, 1, 1, 1, 1, 1, '2024-12-09 19:22:23.471000', 'EventOrganizer', 'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqb2huYWRvZUBleGFtcGxlLmNvbSIsImlhdCI6MTczMzY4MjE0MywiZXhwIjoxNzMzNzY4NTQzfQ.F2Q4zEG8q4i8FLBOfD6biTKGmMcjpDC-2aw505OutNKF3w8lF901qxGLVI1KKmsh', 'ahahah', 'Chicago', 'asd', 'asdasd', 'asd', '$2a$10$3TYrKiXr/LEDyMPvjtvqU.VApcWoOrpUetE8PaiW6DXkB4I0kCYYm', 'asdas', null, 'sasd', 'asd', 'johnadoe@example.com');
INSERT INTO public."user" (active, id, latitude, longitude, number, role, token_expiration, user_type, activation_token, authorities, city, company, description, name, password, phone_number, photo, street, surname, username) VALUES (true, 2, 1, 1, 1, 2, '2024-12-09 19:39:51.043000', 'ServiceProvider', 'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhQGEuYSIsImlhdCI6MTczMzY4MzE5MSwiZXhwIjoxNzMzNzY5NTkxfQ.6kntnma1lTlTT1Kj2oZVI14pIDfWmwnXqi6FYWYHyf9_t1G-gQ5lG446ixDxRPEE', 'ahahah', 'asd', 'asd', 'asd', 'asd', '$2a$10$COCA2xSgJHIrM5RODGOIROdelB1Xz7AD0W9YKuyGD08eEY74VXmLG', 'asd', null, 'asd', 'asd', 'a@a.a');
INSERT INTO public."user" (active, id, latitude, longitude, number, role, token_expiration, user_type, activation_token, authorities, city, company, description, name, password, phone_number, photo, street, surname, username) VALUES (true, 52, 0, 0, 0, null, null, 'AuthenticatedUser', null, null, null, null, null, null, '$2a$10$SBA/7JEdT2FAQvONvjbsW.tqpcSZY5HEJPmFscNVn7d0IDwJ0fxcO', null, null, null, null, 'k@k.k');
INSERT INTO public."user" (active, id, latitude, longitude, number, role, token_expiration, user_type, activation_token, authorities, city, company, description, name, password, phone_number, photo, street, surname, username) VALUES (true, 53, 0, 0, 0, null, null, 'AuthenticatedUser', null, null, null, null, null, null, '$2a$10$whEsuP8YgniU5c13oQlK4OticBaEI59u0VF2wW.A4FXzN5HKZBkZm', null, null, null, null, 'o@o.o');
INSERT INTO public."user" (active, id, latitude, longitude, number, role, token_expiration, user_type, activation_token, authorities, city, company, description, name, password, phone_number, photo, street, surname, username) VALUES (true, 54, 1, 1, 1, 1, null, 'EventOrganizer', null, 'ahahah', 'Portland', null, null, 'Petar', '$2a$10$yX8EqFhvXldOdVCh6xkCi.JPwAmo9f3AMNZaXzYHu7uO3WzUNpv9a', '123456', null, 'as', 'Petrovic', 'pera@p.p');
INSERT INTO public."user" (active, id, latitude, longitude, number, role, token_expiration, user_type, activation_token, authorities, city, company, description, name, password, phone_number, photo, street, surname, username) VALUES (true, 152, 1, 1, 1, 2, null, 'ServiceProvider', null, 'ahahah', 'Denver', 'Goran akademija', 'aksdjasldkajsldkjasdlk', 'Goran', '$2a$10$F9z8liXTxHqldZxP/8VLw.bdefHSKsiK6zLPHbCtea8rNFVBoSZUm', '817281791879', null, 'asd', 'Goric', 'goran@g.g');


UPDATE public.id_generator
SET next_val = 200
WHERE sequence_name = 'user';

-- Insert Event Types
INSERT INTO event_type (title, description, is_active) VALUES
                                                           ('Tech Conference', 'Technology and innovation events', true),
                                                           ('Professional Workshop', 'Skill development and training', true),
                                                           ('Networking Event', 'Professional networking opportunities', true),
                                                           ('Educational Seminar', 'Knowledge sharing and learning', true),
                                                           ( 'Social Gathering', 'Community and social events', true);
--
-- -- No separate address table needed since it's an @Embeddable
--
-- Insert Events with embedded addresses
INSERT INTO event (
    title,
    description,
    max_participants,
    is_public,
    date,
    max_budget,
    type_id,
    street,
    city,
    number,
    longitude,
    latitude,
    organizer_id
) VALUES
      (
          'Tech Innovation Summit 2024',
          'Annual conference exploring cutting-edge technologies',
          500,
          true,
          '2024-12-30 18:00:00',
          75000.00,
          1,
          'Market Street',
          'Chicago',
          123,
          -122.4194,
          37.7749, 1
      ),
      (
          'Digital Marketing Workshop',
          'Hands-on training for modern marketing strategies',
          100,
          true,
          '2024-10-22 13:00:00',
          25000.00,
          2,
          'Broadway',
          'Chicago',
          456,
          -74.0060,
          40.7128, 1
      ),
      (
          'Startup Networking Night',
          'Connect with entrepreneurs and investors',
          200,
          true,
          '2024-11-05 18:00:00',
          15000.00,
          3,
          'Michigan Avenue',
          'Chicago',
          789,
          -87.6298,
          41.8781, 1
      ),
      (
          'AI & Machine Learning Forum',
          'Deep dive into artificial intelligence advances',
          300,
          true,
          '2024-12-10 10:00:00',
          50000.00,
          1,
          'Technology Drive',
          'Chicago',
          555,
          -97.7431,
          30.2672, 1
      ),
      (
          'Leadership Development Seminar',
          'Building effective leadership skills',
          150,
          true,
          '2024-08-20 09:30:00',
          30000.00,
          4,
          'Corporate Boulevard',
          'Chicago',
          777,
          -71.0589,
          42.3601, 1
      ),
      (
          'Cloud Computing Workshop',
          'Hands-on cloud infrastructure training',
          80,
          true,
          '2024-10-05 14:00:00',
          20000.00,
          2,
          'Innovation Way',
          'Chicago',
          234,
          -122.3321,
          47.6062, 1
      ),
      (
          'Entrepreneur Meetup',
          'Casual networking for business owners',
          120,
          true,
          '2024-09-28 17:30:00',
          10000.00,
          3,
          'Startup Street',
          'Chicago',
          445,
          -80.1918,
          25.7617, 1
      ),
      (
          'Community Tech Fair',
          'Local technology showcase and demos',
          400,
          true,
          '2024-08-30 11:00:00',
          35000.00,
          5,
          'Exhibition Drive',
          'Denver',
          567,
          -104.9903,
          39.7392, 1
      ),
      (
          'Agile Project Management',
          'Modern project management methodologies',
          100,
          true,
          '2024-12-05 10:00:00',
          25000.00,
          2,
          'Management Road',
          'Portland',
          333,
          -122.6784,
          45.5155, 54
      ),
      (
          'Tech Startup Pitch Night',
          'Pitch competition for new startups',
          150,
          true,
          '2024-12-18 18:00:00',
          20000.00,
          3,
          'Venture Avenue',
          'Los Angeles',
          678,
          -118.2437,
          34.0522, 54
      ),
      (
          'Cybersecurity Conference',
          'Latest in digital security and protection',
          200,
          true,
          '2024-11-25 09:00:00',
          40000.00,
          1,
          'Security Street',
          'Washington DC',
          901,
          -77.0369,
          38.9072, 54
      ),
      (
          'Digital Transformation Forum',
          'Business modernization strategies',
          180,
          true,
          '2024-09-08 13:00:00',
          35000.00,
          4,
          'Enterprise Road',
          'Houston',
          432,
          -95.3698,
          29.7604, 54
      );

insert into user_organizing_events(event_organizer_id,organizing_events_id) values
                                                                                (1,1),
                                                                                (1,2),
                                                                                (1,3),
                                                                                (1,4),
                                                                                (1,5),
                                                                                (1,6),
                                                                                (1,7),
                                                                                (1,8),
                                                                                (54,9),
                                                                                (54,10),
                                                                                (54,11),
                                                                                (54,12);


-- First, ensure we have a category
INSERT INTO category (title,description,pending) VALUES ('Entertainment','Entertainment description',false),
                                                        ('Funerality','Entertainment description',false),
                                                        ('Suicidabiliyu','Entertainment description',false),
                                                        ('Protest','Entertainment description',false);


INSERT INTO merchandise (
    merchandise_type,
    id,
    title,
    description,
    specificity,
    price,
    discount,
    visible,
    available,
    min_duration,
    max_duration,
    reservation_deadline,
    cancellation_deadline,
    automatic_reservation,
    deleted,
    street,
    city,
    number,
    longitude,
    latitude,
    category_id,
    state
) VALUES
-- DJ Service
('Product',1,  'Professional DJ Services',
 'Experienced DJ for your events',
 'Top-quality sound equipment included',
 500.00, 0, true, true,
 2, 8, 48, 24, true, false,
 'Security Street',
 'Chicago',
 901,
 -77.0369,
 38.9072, 1, 0),

-- Photography Service
('Product', 2, 'Event Photography',
 'Professional event photography service',
 'High-resolution photos, digital delivery within 1 week',
 800.00, 10, true, true,
 3, 12, 72, 48, false, false,
 'Security Street',
 'Chicago',
 901,
 -77.0369,
 38.9072, 1, 0),

-- Catering Service
('Product', 3,'Gourmet Catering',
 'Premium catering service for all events',
 'Custom menu options available, includes staff',
 1200.00, 5, true, true,
 4, 8, 96, 72, false, false,
 'Security Street',
 'Chicago',
 901,
 -77.0369,
 38.9072, 1, 0),

-- Event Planning Service
('Service',  4,'Full Event Planning',
 'Comprehensive event planning and coordination',
 'Includes venue selection, vendor coordination, and day-of management',
 2000.00, 0, true, true,
 10, 121, 43200, 120, false, false,
 'Security Street',
 'Washington DC',
 901,
 -77.0369,
 38.9072, 1, 0),
('Product',  5,'Kasandrina Torta',
 'Torta sa mlevenim orajima',
 'Includes venue selection, vendor coordination, and day-of management',
 2000.00, 0, true, true,
 10, 40, 5000, 120, false, false,
 'Nova',
 'Klisa',
 1,
 -77.0369,
 38.9072, 1, 0),

-- Decoration Service
('Service',  6,'Event Decoration',
 'Professional decoration and setup service',
 'Custom themes and designs available',
 600.00, 15, true, true,
 2, 6, 72, 48, true, false,
 'Security Street',
 'Chicago',
 901,
 -77.0369,
 38.9072, 1, 0);

--
-- -- INSERT INTO public."user"(
-- --     active, id, latitude, longitude, "number", role, user_type, authorities, city, company, description, name, password, phone_number, photo, street, surname, username, activation_token, token_expiration)
-- -- VALUES
-- --     (true, 1, 40.7128, -74.0060, 123, '0', 'authenticatedUser', 'USER', 'New York', 'TechCorp', 'Software engineer', 'John', '$2a$10$3TYrKiXr/LEDyMPvjtvqU.VApcWoOrpUetE8PaiW6DXkB4I0kCYYm', '555-1234', 'john_doe.jpg', '5th Ave', 'Doe', 'john.doe@gmail.com', '', '2024-12-31'),
-- --     (true, 2, 34.0522, -118.2437, 456, '2', 'ServiceProvider', 'USER', 'Los Angeles', 'Creative Solutions', 'Graphic designer', 'Jane', 'password456', '555-5678', 'jane_smith.jpg', 'Sunset Blvd', 'Smith', 'jane.smith@example.com', null, null),
-- --     (true, 3, 51.5074, -0.1278, 789, '1', 'event_organizer', 'USER', 'London', 'Innovate Ltd', 'Marketing manager', 'Alice', 'password789', '555-8765', 'alice_white.jpg', 'Oxford St', 'White', 'alice.white@gmail.com', null, null),
-- --     (true, 4, 48.8566, 2.3522, 101, '1', 'event_organizer', 'USER', 'Paris', 'Design Works', 'Product designer', 'Bob', 'password101', '555-1122', 'bob_brown.jpg', 'Champs-Elysees', 'Brown', 'bob.brown@example.com', null, null),
-- --     (true, 5, 40.7306, -73.9352, 202, '0', 'authenticated_user', 'USER', 'New York', 'Tech Innovators', 'Frontend developer', 'Charlie', 'password202', '555-3344', 'charlie_green.jpg', 'Broadway', 'Green', 'charlie.green@gmail.com', null, null),
-- --     (true, 6, 37.7749, -122.4194, 303, '0', 'authenticated_user', 'USER', 'San Francisco', 'EcoSolutions', 'Project manager', 'Dave', 'password303', '555-5566', 'dave_young.jpg', 'Market St', 'Young', 'dave.young@example.com', null, null),
-- --     (true, 7, 39.9042, 116.4074, 404, '1', 'event_organizer', 'USER', 'Beijing', 'Global Innovations', 'Business analyst', 'Eve', 'password404', '555-6677', 'eve_liu.jpg', 'Wangfujing', 'Liu', 'eve.liu@gmail.com', null, null),
-- --     (true, 8, 34.0522, -118.2437, 505, '1', 'service_provider', 'USER', 'Los Angeles', 'Tech Synergy', 'Software architect', 'Frank', 'password505', '555-7788', 'frank_lee.jpg', 'Wilshire Blvd', 'Lee', 'frank.lee@example.com', null, null),
-- --     (true, 9, 35.6895, 139.6917, 606, '2', 'event_organizer', 'USER', 'Tokyo', 'Creative Minds', 'Data scientist', 'Grace', 'password606', '555-8899', 'grace_kim.jpg', 'Shibuya', 'Kim', 'grace.kim@gmail.com', null, null),
-- --     (true, 10, 40.7306, -73.9352, 707, '2', 'service_provider', 'USER', 'New York', 'DesignHub', 'UX designer', 'Hank', 'password707', '555-9900', 'hank_jones.jpg', 'Park Ave', 'Jones', 'hank.jones@example.com', null, null);
--
--
-- -- insert into user_followed_events(event_id,user_id)
-- -- values
-- --     (1,1),
-- --     (2,1),
-- --     (3,1);
--
insert into user_merchandise(merchandise_id,service_provider_id) values (1,2),
                                                                        (2,2),
    (3,2),
                                                                        (4,2),
                                                                        (5,2),
                                                                        (6,2);


INSERT INTO public.merchandise_eventtype(
    eventtype_id, merchandise_id)
VALUES (1, 1);
INSERT INTO public.merchandise_eventtype(
    eventtype_id, merchandise_id)
VALUES (2, 1);
INSERT INTO public.merchandise_eventtype(
    eventtype_id, merchandise_id)
VALUES (3, 2);
INSERT INTO public.merchandise_eventtype(
    eventtype_id, merchandise_id)
VALUES (1, 4);
INSERT INTO public.merchandise_eventtype(
    eventtype_id, merchandise_id)
VALUES (2, 4);
INSERT INTO public.merchandise_eventtype(
    eventtype_id, merchandise_id)

VALUES (5, 4);

-- Insert 10 New Products for Goran
INSERT INTO merchandise (
    merchandise_type,
    id,
    title,
    description,
    specificity,
    price,
    discount,
    visible,
    available,
    min_duration,
    max_duration,
    reservation_deadline,
    cancellation_deadline,
    automatic_reservation,
    deleted,
    street,
    city,
    number,
    longitude,
    latitude,
    category_id,
    state
) VALUES
-- Product 1: Vintage Photo Booth
(
    'Product',
    7,
    'Retro Photo Booth Rental',
    'Vintage-style photo booth for weddings and events',
    'Includes unlimited prints and digital copies',
    350.00,
    5,
    true,
    true,
    2,
    8,
    72,
    48,
    true,
    false,
    'Innovation Way',
    'Chicago',
    234,
    -122.3321,
    47.6062,
    1,
    0
),

-- Product 2: Custom Cake Design
(
    'Product',
    8,
    'Artisan Custom Cake',
    'Handcrafted gourmet cake for special occasions',
    'Personalized design and flavor selection',
    250.00,
    10,
    true,
    true,
    1,
    2,
    120,
    72,
    false,
    false,
    'Pastry Lane',
    'Portland',
    456,
    -122.6784,
    45.5155,
    1,
    0
),

-- Product 3: Lighting Equipment Rental
(
    'Product',
    9,
    'Professional Event Lighting',
    'High-end lighting equipment for events',
    'LED par lights, moving heads, and control system included',
    600.00,
    0,
    true,
    true,
    1,
    3,
    96,
    72,
    true,
    false,
    'Tech Boulevard',
    'Denver',
    789,
    -104.9903,
    39.7392,
    1,
    0
),

-- Product 4: Vintage Car Rental
(
    'Product',
    10,
    'Classic Car for Events',
    'Vintage car rental for weddings and photo shoots',
    'Multiple classic models available, professional driver included',
    500.00,
    15,
    true,
    true,
    4,
    24,
    168,
    120,
    false,
    false,
    'Classic Avenue',
    'Los Angeles',
    321,
    -118.2437,
    34.0522,
    1,
    0
),

-- Product 5: Balloon Decoration Kit
(
    'Product',
    11,
    'Event Balloon Decor Package',
    'Professional balloon decoration and setup',
    'Custom color schemes, arch, columns, and custom shapes',
    200.00,
    5,
    true,
    true,
    1,
    2,
    48,
    24,
    true,
    false,
    'Party Street',
    'Chicago',
    567,
    -87.6298,
    41.8781,
    1,
    0
),

-- Product 6: Sound System Rental
(
    'Product',
    12,
    'Professional Audio Equipment',
    'High-quality sound system for events',
    'Speakers, mixers, microphones, and setup included',
    450.00,
    10,
    true,
    true,
    1,
    2,
    72,
    48,
    true,
    false,
    'Sound Avenue',
    'Seattle',
    890,
    -122.3321,
    47.6062,
    1,
    0
),

-- Product 7: Flower Wall Backdrop
(
    'Product',
    13,
    'Luxury Flower Wall',
    'Stunning floral backdrop for events and photos',
    'Customizable flower colors and sizes',
    300.00,
    0,
    true,
    true,
    1,
    1,
    120,
    72,
    false,
    false,
    'Bloom Street',
    'San Francisco',
    234,
    -122.4194,
    37.7749,
    1,
    0
),

-- Product 8: Themed Prop Collection
(
    'Product',
    14,
    'Event Theme Prop Set',
    'Comprehensive collection of themed props',
    'Various themes: Gatsby, Tropical, Vintage, Sci-Fi',
    400.00,
    15,
    true,
    true,
    1,
    2,
    96,
    48,
    true,
    false,
    'Props Lane',
    'Austin',
    456,
    -97.7431,
    30.2672,
    1,
    0
),

-- Product 9: Interactive Digital Guestbook
(
    'Product',
    15,
    'Digital Memories Guestbook',
    'Interactive digital guestbook for events',
    'Touchscreen display, instant photo and message capture',
    275.00,
    5,
    true,
    true,
    1,
    1,
    48,
    24,
    false,
    false,
    'Tech Plaza',
    'Boston',
    678,
    -71.0589,
    42.3601,
    1,
    0
),

-- Product 10: Projection Mapping Kit
(
    'Product',
    16,
    'Event Projection Mapping',
    'Advanced projection mapping technology',
    'Custom visual effects for venues and stages',
    750.00,
    0,
    true,
    true,
    2,
    4,
    168,
    120,
    true,
    false,
    'Digital Avenue',
    'Miami',
    901,
    -80.1918,
    25.7617,
    1,
    0
);

-- Insert 10 New Services for Goran
INSERT INTO merchandise (
    merchandise_type,
    id,
    title,
    description,
    specificity,
    price,
    discount,
    visible,
    available,
    min_duration,
    max_duration,
    reservation_deadline,
    cancellation_deadline,
    automatic_reservation,
    deleted,
    street,
    city,
    number,
    longitude,
    latitude,
    category_id,
    state
) VALUES
-- Service 1: Virtual Event Coordination
(
    'Service',
    17,
    'Virtual Event Management',
    'Comprehensive online event planning and execution',
    'Platform selection, technical setup, and virtual host services',
    1500.00,
    0,
    true,
    true,
    10,
    120,
    720,
    240,
    false,
    false,
    'Digital Street',
    'Chicago',
    123,
    -87.6298,
    41.8781,
    1,
    0
),

-- Service 2: Performer Booking
(
    'Service',
    18,
    'Entertainment Talent Booking',
    'Professional performer and artist booking service',
    'Musicians, comedians, magicians, and specialty acts',
    2000.00,
    10,
    true,
    true,
    24,
    168,
    1440,
    480,
    true,
    false,
    'Stage Avenue',
    'Los Angeles',
    456,
    -118.2437,
    34.0522,
    1,
    0
),

-- Service 3: Corporate Team Building
(
    'Service',
    19,
    'Team Building Experience',
    'Custom corporate team development programs',
    'Indoor and outdoor activities, professional facilitators',
    3000.00,
    5,
    true,
    true,
    8,
    40,
    720,
    240,
    false,
    false,
    'Leadership Road',
    'Seattle',
    789,
    -122.3321,
    47.6062,
    1,
    0
),

-- Service 4: Wedding Coordination
(
    'Service',
    20,
    'Full Wedding Planning',
    'Comprehensive wedding planning from concept to execution',
    'Vendor management, design, timeline coordination',
    5000.00,
    0,
    true,
    true,
    60,
    365,
    1440,
    720,
    true,
    false,
    'Romance Lane',
    'San Francisco',
    234,
    -122.4194,
    37.7749,
    1,
    0
),

-- Service 5: Event Technology Consulting
(
    'Service',
    21,
    'Event Tech Strategy',
    'Technology consulting for event innovation',
    'Digital transformation, tech integration, innovation workshops',
    2500.00,
    15,
    true,
    true,
    16,
    80,
    480,
    120,
    false,
    false,
    'Innovation Boulevard',
    'Austin',
    567,
    -97.7431,
    30.2672,
    1,
    0
),

-- Service 6: Sustainable Event Design
(
    'Service',
    22,
    'Eco-Friendly Event Planning',
    'Green event design and sustainability consulting',
    'Carbon-neutral strategies, eco-friendly vendor selection',
    1800.00,
    0,
    true,
    true,
    24,
    120,
    720,
    240,
    true,
    false,
    'Green Street',
    'Portland',
    890,
    -122.6784,
    45.5155,
    1,
    0
),

-- Service 7: International Event Management
(
    'Service',
    23,
    'Global Event Coordination',
    'International event planning and logistics',
    'Cross-cultural consulting, international vendor management',
    4000.00,
    10,
    true,
    true,
    40,
    240,
    1440,
    720,
    false,
    false,
    'Global Avenue',
    'New York',
    321,
    -74.0060,
    40.7128,
    1,
    0
),

-- Service 8: Event Brand Strategy
(
    'Service',
    24,
    'Event Branding Consultancy',
    'Strategic branding for events and experiences',
    'Brand identity, marketing strategy, experiential design',
    2200.00,
    5,
    true,
    true,
    16,
    80,
    480,
    120,
    true,
    false,
    'Creative Plaza',
    'Miami',
    456,
    -80.1918,
    25.7617,
    1,
    0
),

-- Service 9: VIP Guest Experience
(
    'Service',
    25,
    'Premium Guest Management',
    'White-glove service for high-profile event guests',
    'Concierge services, VIP coordination, luxury experience design',
    3500.00,
    0,
    true,
    true,
    24,
    120,
    720,
    240,
    false,
    false,
    'Luxury Lane',
    'Las Vegas',
    678,
    -115.1398,
    36.1699,
    1,
    0
);

-- Link these new merchandise items to Goran (user_id 152)
INSERT INTO user_merchandise(merchandise_id, service_provider_id)
SELECT id, 152
FROM merchandise
WHERE id BETWEEN 7 AND 25;

insert into event_type (is_active, description, title)
values (true,'all','all');

insert into user_favorite_merchandises (merchandise_id, user_id)
select id,54
from merchandise
where id between 6 and 15;


-- Unique addresses for users
UPDATE public."user" SET
                         street = '9 Svetosavska',
                         city = 'Novi Sad',
                         latitude = 45.26186379833459,
                         longitude = 19.846108584039364
WHERE id = 1;

UPDATE public."user" SET
                         street = 'Bulevar kralju Petra I',
                         city = 'Novi Sad',
                         latitude = 45.26195441843065,
                         longitude = 19.83795229186215
WHERE id = 54;

UPDATE public."user" SET
                         street = '6 Vojvode Supljikca',
                         city = 'Novi Sad',
                         latitude = 45.25899408714758,
                         longitude = 19.83885377678698
WHERE id = 152;

-- Unique addresses for events
UPDATE event SET
                 street = 'Futoska',
                 city = 'Novi Sad',
                 latitude = 45.2508322379121,
                 longitude = 19.827040712765598
WHERE id = 9;

UPDATE event SET
                 street = '45a Djordja Magarasevica',
                 city = 'Novi Sad',
                 latitude = 45.245272849009446,
                 longitude = 19.8206015347309
WHERE id = 10;

UPDATE event SET
                 street = '40 Bogdana Suputa',
                 city = 'Novi Sad',
                 latitude = 45.244487239311034,
                 longitude = 19.811500829775266
WHERE id = 11;

UPDATE event SET
                 street = '12 Stevana Dejanova',
                 city = 'Novi Sad',
                 latitude = 45.24829432352203,
                 longitude = 19.80428895037643
WHERE id = 12;

-- Unique addresses for merchandise
UPDATE merchandise SET
                       street = '14a Todora Toze jovanovica',
                       city = 'Novi Sad',
                       latitude = 45.25451805447678,
                       longitude = 19.806006064519025
WHERE id = 7;

UPDATE merchandise SET
                       street = '24 Koste Abrasevica',
                       city = 'Novi Sad',
                       latitude = 45.244487239311034,
                       longitude = 19.806778765883163
WHERE id = 8;

UPDATE merchandise SET
                       street = '96 Suboticka',
                       city = 'Novi Sad',
                       latitude = 45.24164686745039,
                       longitude = 19.81536433659605
WHERE id = 9;

UPDATE merchandise SET
                       street = '12 Kolo srpskih sestara',
                       city = 'Novi Sad',
                       latitude = 45.242130345029764,
                       longitude = 19.826096299987174
WHERE id = 10;

UPDATE merchandise SET
                       street = '26 Alekse Santica',
                       city = 'Novi Sad',
                       latitude = 45.243580753082036,
                       longitude = 19.835111149235704
WHERE id = 11;

UPDATE merchandise SET
                       street = '31 Antona Cehova',
                       city = 'Novi Sad',
                       latitude = 45.24666274724605,
                       longitude = 19.830045662515108
WHERE id = 12;

UPDATE merchandise SET
                       street = 'National employment service, 2 Alberta Tome',
                       city = 'Novi Sad',
                       latitude = 45.24708575300279,
                       longitude = 19.84060591449194
WHERE id = 13;

UPDATE merchandise SET
                       street = '5 Bulevar Cara Lazara',
                       city = 'Novi Sad',
                       latitude = 45.246421028256464,
                       longitude = 19.84816121671927
WHERE id = 14;

UPDATE merchandise SET
                       street = '39 Radnicka',
                       city = 'Novi Sad',
                       latitude = 45.25010713108641,
                       longitude = 19.850050042276113
WHERE id = 15;

UPDATE merchandise SET
                       street = '7 Miroslava Antica',
                       city = 'Novi Sad',
                       latitude = 45.25179903261297,
                       longitude = 19.84661581399096
WHERE id = 16;

-- Service-specific addresses
UPDATE merchandise SET
                       street = '34 Jevrejska',
                       city = 'Novi Sad',
                       latitude = 45.252765810856665,
                       longitude = 19.838116098985214
WHERE id = 17;

UPDATE merchandise SET
                       street = '11 Futoska',
                       city = 'Novi Sad',
                       latitude = 45.250892663063055,
                       longitude = 19.83519700494281
WHERE id = 18;

UPDATE merchandise SET
                       street = 'Skolska',
                       city = 'Novi Sad',
                       latitude = 45.249140307585435,
                       longitude = 19.836398984842617
WHERE id = 19;

UPDATE merchandise SET
                       street = 'Turgenjeva',
                       city = 'Novi Sad',
                       latitude = 45.25506184322807,
                       longitude = 19.832363766607585
WHERE id = 20;

UPDATE merchandise SET
                       street = '8 Milesevska',
                       city = 'Novi Sad',
                       latitude = 45.255545206635574,
                       longitude = 19.828414404079652
WHERE id = 21;

UPDATE merchandise SET
                       street = 'Galeriaj podova, 11 Hajduk Veljkova',
                       city = 'Novi Sad',
                       latitude = 45.25482015998148,
                       longitude = 19.82240450458064
WHERE id = 22;

UPDATE merchandise SET
                       street = 'Paje Marganovica',
                       city = 'Novi Sad',
                       latitude = 45.25222200011958,
                       longitude = 19.821288380387923
WHERE id = 23;

UPDATE merchandise SET
                       street = 'Bulevar kralja Petra I',
                       city = 'Novi Sad',
                       latitude = 45.25965362948473,
                       longitude = 19.829616383979427
WHERE id = 24;

UPDATE merchandise SET
                       street = '17 Paje Markovica Adamova',
                       city = 'Novi Sad',
                       latitude = 45.26134524661078,
                       longitude = 19.826525578522812
WHERE id = 25;

UPDATE merchandise SET
                       street = 'Save Kovacevica',
                       number='7',
                       city = 'Novi Sad',
                       latitude = 45.26315763760297,
                       longitude = 19.832793045143227
WHERE id = 26;

UPDATE event SET
                 street = 'Futoski put',
                 city = 'Novi Sad',
                 number='2',
                 latitude = 45.246239738339256,
                 longitude = 19.798622473705908
WHERE id between 1 and 9;

UPDATE merchandise SET
                       street = 'Hajduk Veljkova',
                       city = 'Novi Sad',
                       number='7',
                       latitude = 45.25482015998148,
                       longitude = 19.82240450458064
WHERE id between 1 and 6;

--
-- -- Reviews for "Tech Innovation Summit 2024"
-- INSERT INTO review (id, comment, rating, status, deleted, created_at, reviewer_id) VALUES
--                                                                                      (41, 'Amazing experience! Learned so much about cutting-edge technology.', 5, 'PENDING', false, '2023-09-15 10:00:00', 1),
--                                                                                      (42, 'Well-organized but I felt the sessions were too long.', 4, 'PENDING', false, '2023-09-16 11:00:00', 2),
--                                                                                      (43, 'The location was too crowded for the number of attendees.', 3, 'PENDING', false, '2023-09-17 12:00:00', 1),
--                                                                                      (44, 'Great event! Excellent speakers and engaging sessions.', 5, 'PENDING', false, '2023-09-18 13:00:00', 1);
--
-- -- Reviews for "AI & Machine Learning Forum"
-- INSERT INTO review (id, comment, rating, status, deleted, created_at, reviewer_id) VALUES
--                                                                                      (45, 'Fantastic forum for AI enthusiasts. Great networking opportunities.', 5, 'PENDING', false, '2023-08-10 09:00:00', 2),
--                                                                                      (46, 'Loved the hands-on workshops. Very informative.', 5, 'PENDING', false, '2023-08-11 10:00:00', 2),
--                                                                                      (47, 'Would have liked more beginner-friendly content.', 3, 'PENDING', false, '2023-08-12 11:00:00', 1),
--                                                                                      (48, 'Keynote speaker was excellent! Very inspiring.', 4, 'PENDING', false, '2023-08-13 12:00:00', 2);
--
-- -- Reviews for "Startup Networking Night"
-- INSERT INTO review (id, comment, rating, status, deleted, created_at, reviewer_id) VALUES
--                                                                                      (49, 'Met a lot of amazing people. Great for entrepreneurs!', 5, 'PENDING', false, '2023-07-20 14:00:00', 2),
--                                                                                      (50, 'Good opportunity, but the event seemed too short.', 4, 'PENDING', false, '2023-07-21 15:00:00', 2),
--                                                                                      (51, 'Loved the casual atmosphere, and it was easy to connect with others.', 5, 'PENDING', false, '2023-07-22 16:00:00', 1),
--                                                                                      (52, 'Food options were limited, but overall a good experience.', 3, 'PENDING', false, '2023-07-23 17:00:00', 1);
--
-- -- Link Reviews to Events
-- INSERT INTO event_reviews (event_id, review_id) VALUES
-- -- Tech Innovation Summit (event_id: 1)
-- (1, 41),
-- (1, 42),
-- (1, 43),
-- (1, 44),
--
-- -- AI & Machine Learning Forum (event_id: 4)
-- (4, 45),
-- (4, 46),
-- (4, 47),
-- (4, 48),
--
-- -- Startup Networking Night (event_id: 3)
-- (3, 49),
-- (3, 50),
-- (3, 51),
-- (3, 52);
--
-- -- Reviews for "Professional DJ Services"
-- INSERT INTO review (id, comment, rating, status, deleted, created_at, reviewer_id) VALUES
--                                                                                      (53, 'The DJ played great music and kept the energy high throughout the event.', 5, 'PENDING', false, '2023-06-10 18:00:00', 2),
--                                                                                      (54, 'Good service but arrived slightly late.', 4, 'PENDING', false, '2023-06-11 19:00:00', 2),
--                                                                                      (55, 'Decent performance, but I expected more variety in the playlist.', 3, 'PENDING', false, '2023-06-12 20:00:00', 1),
--                                                                                      (56, 'Excellent work! My guests had a blast!', 5, 'PENDING', false, '2023-06-13 21:00:00', 2);
--
-- -- Reviews for "Event Photography"
-- INSERT INTO review (id, comment, rating, status, deleted, created_at, reviewer_id) VALUES
--                                                                                      (57, 'The photos came out beautifully, capturing every special moment.', 5, 'PENDING', false, '2023-05-01 10:30:00', 1),
--                                                                                      (58, 'Professional service, but delivery was delayed.', 4, 'PENDING', false, '2023-05-02 11:30:00', 2),
--                                                                                      (59, 'Attention to detail was stunning. Would hire again.', 5, 'PENDING', false, '2023-05-03 12:30:00', 1),
--                                                                                      (60, 'Good quality but a bit pricey.', 4, 'PENDING', false, '2023-05-04 13:30:00', 2);
--
-- -- Link Reviews to Merchandise
-- INSERT INTO merchandise_reviews (merchandise_id, review_id) VALUES
-- -- DJ Service (merchandise_id: 1)
-- (1, 53),
-- (1, 54),
-- (1, 55),
-- (1, 56),
--
-- -- Photography Service (merchandise_id: 2)
-- (2, 57),
-- (2, 58),
-- (2, 59),
-- (2, 60);
--
-- -- INSERT INTO merchandise (
-- --     merchandise_type,
-- --     id,
-- --     title,
-- --     description,
-- --     specificity,
-- --     price,
-- --     discount,
-- --     visible,
-- --     available,
-- --     min_duration,
-- --     max_duration,
-- --     reservation_deadline,
-- --     cancellation_deadline,
-- --     automatic_reservation,
-- --     deleted,
-- --     street,
-- --     city,
-- --     number,
-- --     longitude,
-- --     latitude,
-- --     category_id,
-- --     state
-- -- ) VALUES
-- -- -- DJ Service
-- -- ('Product',1,  'Professional DJ Services',
-- --  'Experienced DJ for your events',
-- --  'Top-quality sound equipment included',
-- --  500.00, 0, true, true,
-- --  2, 8, 48, 24, true, false,
-- --  'Security Street',
-- --  'Washington DC',
-- --  901,
-- --  -77.0369,
-- --  38.9072, 1, 0),
-- --
-- -- -- Photography Service
-- -- ('Product', 2, 'Event Photography',
-- --  'Professional event photography service',
-- --  'High-resolution photos, digital delivery within 1 week',
-- --  800.00, 10, true, true,
-- --  3, 12, 72, 48, false, false,
-- --  'Security Street',
-- --  'Washington DC',
-- --  901,
-- --  -77.0369,
-- --  38.9072, 1, 0),
-- --
-- -- -- Catering Service
-- -- ('Product', 3,'Gourmet Catering',
-- --  'Premium catering service for all events',
-- --  'Custom menu options available, includes staff',
-- --  1200.00, 5, true, true,
-- --  4, 8, 96, 72, false, false,
-- --  'Security Street',
-- --  'Washington DC',
-- --  901,
-- --  -77.0369,
-- --  38.9072, 1, 0),
-- --
-- -- -- Event Planning Service
-- -- ('Service',  4,'Full Event Planning',
-- --  'Comprehensive event planning and coordination',
-- --  'Includes venue selection, vendor coordination, and day-of management',
-- --  2000.00, 0, true, true,
-- --  10, 121, 43200, 120, false, false,
-- --  'Security Street',
-- --  'Washington DC',
-- --  901,
-- --  -77.0369,
-- --  38.9072, 1, 0),
-- -- ('Product',  5,'Kasandrina Torta',
-- --  'Torta sa mlevenim orajima',
-- --  'Includes venue selection, vendor coordination, and day-of management',
-- --  2000.00, 0, true, true,
-- --  10, 40, 5000, 120, false, false,
-- --  'Nova',
-- --  'Klisa',
-- --  1,
-- --  -77.0369,
-- --  38.9072, 1, 0),
-- --
-- -- -- Decoration Service
-- -- ('Service',  6,'Event Decoration',
-- --  'Professional decoration and setup service',
-- --  'Custom themes and designs available',
-- --  600.00, 15, true, true,
-- --  2, 6, 72, 48, true, false,
-- --  'Security Street',
-- --  'Washington DC',
-- --  901,
-- --  -77.0369,
-- --  38.9072, 1, 0);
-- --
-- -- INSERT INTO review (id, comment, rating, status) VALUES
-- -- -- DJ Service Reviews (ID: 1)
-- -- (1, 'Amazing DJ! Kept the party going all night long', 5, true),
-- -- (2, 'Great music selection and very professional', 5, true),
-- -- (3, 'Good service but could improve song transitions', 4, true),
-- -- (4, 'Fantastic energy and crowd engagement', 5, true),
-- --
-- -- -- Photography Service Reviews (ID: 2)
-- -- (5, 'Beautiful photos, captured every moment perfectly', 5, true),
-- -- (6, 'Professional service but took longer than promised for delivery', 3, true),
-- -- (7, 'Excellent attention to detail', 4, true),
-- -- (8, 'Worth every penny, amazing quality', 5, true),
-- --
-- -- -- Catering Service Reviews (ID: 3)
-- -- (9, 'Food was delicious and presentation was beautiful', 5, true),
-- -- (10, 'Great variety but some dishes were cold', 3, true),
-- -- (11, 'Professional staff and excellent service', 4, true),
-- -- (12, 'Outstanding quality and presentation', 5, true),
-- --
-- -- -- Event Planning Service Reviews (ID: 4)
-- -- (13, 'Made our event completely stress-free', 5, true),
-- -- (14, 'Excellent coordination and attention to detail', 5, true),
-- -- (15, 'Good planning but some communication issues', 4, true),
-- -- (16, 'Went above and beyond our expectations', 5, true),
-- --
-- -- -- Decoration Service Reviews (ID: 5)
-- -- (17, 'Beautiful decorations, exactly what we wanted', 5, true),
-- -- (18, 'Creative designs but setup was delayed', 3, true),
-- -- (19, 'Transformed the venue beautifully', 4, true),
-- -- (20, 'High-quality materials and great execution', 5, true);
-- --
-- -- INSERT INTO merchandise_reviews (merchandise_id, review_id) VALUES
-- -- -- DJ Service (merchandise_id: 1)
-- -- (1, 1),
-- -- (1, 2),
-- -- (1, 3),
-- -- (1, 4),
-- --
-- -- -- Photography Service (merchandise_id: 2)
-- -- (2, 5),
-- -- (2, 6),
-- -- (2, 7),
-- -- (2, 8),
-- --
-- -- -- Catering Service (merchandise_id: 3)
-- -- (3, 9),
-- -- (3, 10),
-- -- (3, 11),
-- -- (3, 12),
-- --
-- -- -- Event Planning Service (merchandise_id: 4)
-- -- (4, 13),
-- -- (4, 14),
-- -- (4, 15),
-- -- (4, 16),
-- --
-- -- -- Decoration Service (merchandise_id: 5)
-- -- (5, 17),
-- -- (5, 18),
-- -- (5, 19),
-- -- (5, 20);
-- --
-- -- -- INSERT INTO public."user"(
-- -- --     active, id, latitude, longitude, "number", role, user_type, authorities, city, company, description, name, password, phone_number, photo, street, surname, username, activation_token, token_expiration)
-- -- -- VALUES
-- -- --     (true, 1, 40.7128, -74.0060, 123, '0', 'authenticatedUser', 'USER', 'New York', 'TechCorp', 'Software engineer', 'John', '$2a$10$3TYrKiXr/LEDyMPvjtvqU.VApcWoOrpUetE8PaiW6DXkB4I0kCYYm', '555-1234', 'john_doe.jpg', '5th Ave', 'Doe', 'john.doe@gmail.com', '', '2024-12-31'),
-- -- --     (true, 2, 34.0522, -118.2437, 456, '2', 'ServiceProvider', 'USER', 'Los Angeles', 'Creative Solutions', 'Graphic designer', 'Jane', 'password456', '555-5678', 'jane_smith.jpg', 'Sunset Blvd', 'Smith', 'jane.smith@example.com', null, null),
-- -- --     (true, 3, 51.5074, -0.1278, 789, '1', 'event_organizer', 'USER', 'London', 'Innovate Ltd', 'Marketing manager', 'Alice', 'password789', '555-8765', 'alice_white.jpg', 'Oxford St', 'White', 'alice.white@gmail.com', null, null),
-- -- --     (true, 4, 48.8566, 2.3522, 101, '1', 'event_organizer', 'USER', 'Paris', 'Design Works', 'Product designer', 'Bob', 'password101', '555-1122', 'bob_brown.jpg', 'Champs-Elysees', 'Brown', 'bob.brown@example.com', null, null),
-- -- --     (true, 5, 40.7306, -73.9352, 202, '0', 'authenticated_user', 'USER', 'New York', 'Tech Innovators', 'Frontend developer', 'Charlie', 'password202', '555-3344', 'charlie_green.jpg', 'Broadway', 'Green', 'charlie.green@gmail.com', null, null),
-- -- --     (true, 6, 37.7749, -122.4194, 303, '0', 'authenticated_user', 'USER', 'San Francisco', 'EcoSolutions', 'Project manager', 'Dave', 'password303', '555-5566', 'dave_young.jpg', 'Market St', 'Young', 'dave.young@example.com', null, null),
-- -- --     (true, 7, 39.9042, 116.4074, 404, '1', 'event_organizer', 'USER', 'Beijing', 'Global Innovations', 'Business analyst', 'Eve', 'password404', '555-6677', 'eve_liu.jpg', 'Wangfujing', 'Liu', 'eve.liu@gmail.com', null, null),
-- -- --     (true, 8, 34.0522, -118.2437, 505, '1', 'service_provider', 'USER', 'Los Angeles', 'Tech Synergy', 'Software architect', 'Frank', 'password505', '555-7788', 'frank_lee.jpg', 'Wilshire Blvd', 'Lee', 'frank.lee@example.com', null, null),
-- -- --     (true, 9, 35.6895, 139.6917, 606, '2', 'event_organizer', 'USER', 'Tokyo', 'Creative Minds', 'Data scientist', 'Grace', 'password606', '555-8899', 'grace_kim.jpg', 'Shibuya', 'Kim', 'grace.kim@gmail.com', null, null),
-- -- --     (true, 10, 40.7306, -73.9352, 707, '2', 'service_provider', 'USER', 'New York', 'DesignHub', 'UX designer', 'Hank', 'password707', '555-9900', 'hank_jones.jpg', 'Park Ave', 'Jones', 'hank.jones@example.com', null, null);
-- --
-- --
-- -- -- insert into user_followed_events(event_id,user_id)
-- -- -- values
-- -- --     (1,1),
-- -- --     (2,1),
-- -- --     (3,1);
-- --
-- -- insert into user_merchandise(merchandise_id,service_provider_id) values (1,2);
-- -- insert into user_merchandise(merchandise_id,service_provider_id) values (2,2);
-- -- insert into user_merchandise(merchandise_id,service_provider_id) values (3,2);
-- -- insert into user_merchandise(merchandise_id,service_provider_id) values (4,2);
-- --
-- -- INSERT INTO public.merchandise_eventtype(
-- --     eventtype_id, merchandise_id)
-- -- VALUES (1, 1);
-- -- INSERT INTO public.merchandise_eventtype(
-- --     eventtype_id, merchandise_id)
-- -- VALUES (2, 1);
-- -- INSERT INTO public.merchandise_eventtype(
-- --     eventtype_id, merchandise_id)
-- -- VALUES (3, 2);
-- -- INSERT INTO public.merchandise_eventtype(
-- --     eventtype_id, merchandise_id)
-- -- VALUES (1, 4);
-- -- INSERT INTO public.merchandise_eventtype(
-- --     eventtype_id, merchandise_id)
-- -- VALUES (2, 4);
-- -- INSERT INTO public.merchandise_eventtype(
-- --     eventtype_id, merchandise_id)
--
-- -- VALUES (5, 4);
--
-- -- Insert 10 New Products for Goran
-- INSERT INTO merchandise (
--     merchandise_type,
--     id,
--     title,
--     description,
--     specificity,
--     price,
--     discount,
--     visible,
--     available,
--     min_duration,
--     max_duration,
--     reservation_deadline,
--     cancellation_deadline,
--     automatic_reservation,
--     deleted,
--     street,
--     city,
--     number,
--     longitude,
--     latitude,
--     category_id,
--     state
-- ) VALUES
-- -- Product 1: Vintage Photo Booth
-- (
--     'Product',
--     7,
--     'Retro Photo Booth Rental',
--     'Vintage-style photo booth for weddings and events',
--     'Includes unlimited prints and digital copies',
--     350.00,
--     5,
--     true,
--     true,
--     2,
--     8,
--     72,
--     48,
--     true,
--     false,
--     'Innovation Way',
--     'Chicago',
--     234,
--     -122.3321,
--     47.6062,
--     1,
--     0
-- ),
--
-- -- Product 2: Custom Cake Design
-- (
--     'Product',
--     8,
--     'Artisan Custom Cake',
--     'Handcrafted gourmet cake for special occasions',
--     'Personalized design and flavor selection',
--     250.00,
--     10,
--     true,
--     true,
--     1,
--     2,
--     120,
--     72,
--     false,
--     false,
--     'Pastry Lane',
--     'Portland',
--     456,
--     -122.6784,
--     45.5155,
--     1,
--     0
-- ),
--
-- -- Product 3: Lighting Equipment Rental
-- (
--     'Product',
--     9,
--     'Professional Event Lighting',
--     'High-end lighting equipment for events',
--     'LED par lights, moving heads, and control system included',
--     600.00,
--     0,
--     true,
--     true,
--     1,
--     3,
--     96,
--     72,
--     true,
--     false,
--     'Tech Boulevard',
--     'Denver',
--     789,
--     -104.9903,
--     39.7392,
--     1,
--     0
-- ),
--
-- -- Product 4: Vintage Car Rental
-- (
--     'Product',
--     10,
--     'Classic Car for Events',
--     'Vintage car rental for weddings and photo shoots',
--     'Multiple classic models available, professional driver included',
--     500.00,
--     15,
--     true,
--     true,
--     4,
--     24,
--     168,
--     120,
--     false,
--     false,
--     'Classic Avenue',
--     'Los Angeles',
--     321,
--     -118.2437,
--     34.0522,
--     1,
--     0
-- ),
--
-- -- Product 5: Balloon Decoration Kit
-- (
--     'Product',
--     11,
--     'Event Balloon Decor Package',
--     'Professional balloon decoration and setup',
--     'Custom color schemes, arch, columns, and custom shapes',
--     200.00,
--     5,
--     true,
--     true,
--     1,
--     2,
--     48,
--     24,
--     true,
--     false,
--     'Party Street',
--     'Chicago',
--     567,
--     -87.6298,
--     41.8781,
--     1,
--     0
-- ),
--
-- -- Product 6: Sound System Rental
-- (
--     'Product',
--     12,
--     'Professional Audio Equipment',
--     'High-quality sound system for events',
--     'Speakers, mixers, microphones, and setup included',
--     450.00,
--     10,
--     true,
--     true,
--     1,
--     2,
--     72,
--     48,
--     true,
--     false,
--     'Sound Avenue',
--     'Seattle',
--     890,
--     -122.3321,
--     47.6062,
--     1,
--     0
-- ),
--
-- -- Product 7: Flower Wall Backdrop
-- (
--     'Product',
--     13,
--     'Luxury Flower Wall',
--     'Stunning floral backdrop for events and photos',
--     'Customizable flower colors and sizes',
--     300.00,
--     0,
--     true,
--     true,
--     1,
--     1,
--     120,
--     72,
--     false,
--     false,
--     'Bloom Street',
--     'San Francisco',
--     234,
--     -122.4194,
--     37.7749,
--     1,
--     0
-- ),
--
-- -- Product 8: Themed Prop Collection
-- (
--     'Product',
--     14,
--     'Event Theme Prop Set',
--     'Comprehensive collection of themed props',
--     'Various themes: Gatsby, Tropical, Vintage, Sci-Fi',
--     400.00,
--     15,
--     true,
--     true,
--     1,
--     2,
--     96,
--     48,
--     true,
--     false,
--     'Props Lane',
--     'Austin',
--     456,
--     -97.7431,
--     30.2672,
--     1,
--     0
-- ),
--
-- -- Product 9: Interactive Digital Guestbook
-- (
--     'Product',
--     15,
--     'Digital Memories Guestbook',
--     'Interactive digital guestbook for events',
--     'Touchscreen display, instant photo and message capture',
--     275.00,
--     5,
--     true,
--     true,
--     1,
--     1,
--     48,
--     24,
--     false,
--     false,
--     'Tech Plaza',
--     'Boston',
--     678,
--     -71.0589,
--     42.3601,
--     1,
--     0
-- ),
--
-- -- Product 10: Projection Mapping Kit
-- (
--     'Product',
--     16,
--     'Event Projection Mapping',
--     'Advanced projection mapping technology',
--     'Custom visual effects for venues and stages',
--     750.00,
--     0,
--     true,
--     true,
--     2,
--     4,
--     168,
--     120,
--     true,
--     false,
--     'Digital Avenue',
--     'Miami',
--     901,
--     -80.1918,
--     25.7617,
--     1,
--     0
-- );
--
-- -- Insert 10 New Services for Goran
-- INSERT INTO merchandise (
--     merchandise_type,
--     id,
--     title,
--     description,
--     specificity,
--     price,
--     discount,
--     visible,
--     available,
--     min_duration,
--     max_duration,
--     reservation_deadline,
--     cancellation_deadline,
--     automatic_reservation,
--     deleted,
--     street,
--     city,
--     number,
--     longitude,
--     latitude,
--     category_id,
--     state
-- ) VALUES
-- -- Service 1: Virtual Event Coordination
-- (
--     'Service',
--     17,
--     'Virtual Event Management',
--     'Comprehensive online event planning and execution',
--     'Platform selection, technical setup, and virtual host services',
--     1500.00,
--     0,
--     true,
--     true,
--     10,
--     120,
--     720,
--     240,
--     false,
--     false,
--     'Digital Street',
--     'Chicago',
--     123,
--     -87.6298,
--     41.8781,
--     1,
--     0
-- ),
--
-- -- Service 2: Performer Booking
-- (
--     'Service',
--     18,
--     'Entertainment Talent Booking',
--     'Professional performer and artist booking service',
--     'Musicians, comedians, magicians, and specialty acts',
--     2000.00,
--     10,
--     true,
--     true,
--     24,
--     168,
--     1440,
--     480,
--     true,
--     false,
--     'Stage Avenue',
--     'Los Angeles',
--     456,
--     -118.2437,
--     34.0522,
--     1,
--     0
-- ),
--
-- -- Service 3: Corporate Team Building
-- (
--     'Service',
--     19,
--     'Team Building Experience',
--     'Custom corporate team development programs',
--     'Indoor and outdoor activities, professional facilitators',
--     3000.00,
--     5,
--     true,
--     true,
--     8,
--     40,
--     720,
--     240,
--     false,
--     false,
--     'Leadership Road',
--     'Seattle',
--     789,
--     -122.3321,
--     47.6062,
--     1,
--     0
-- ),
--
-- -- Service 4: Wedding Coordination
-- (
--     'Service',
--     20,
--     'Full Wedding Planning',
--     'Comprehensive wedding planning from concept to execution',
--     'Vendor management, design, timeline coordination',
--     5000.00,
--     0,
--     true,
--     true,
--     60,
--     365,
--     1440,
--     720,
--     true,
--     false,
--     'Romance Lane',
--     'San Francisco',
--     234,
--     -122.4194,
--     37.7749,
--     1,
--     0
-- ),
--
-- -- Service 5: Event Technology Consulting
-- (
--     'Service',
--     21,
--     'Event Tech Strategy',
--     'Technology consulting for event innovation',
--     'Digital transformation, tech integration, innovation workshops',
--     2500.00,
--     15,
--     true,
--     true,
--     16,
--     80,
--     480,
--     120,
--     false,
--     false,
--     'Innovation Boulevard',
--     'Austin',
--     567,
--     -97.7431,
--     30.2672,
--     1,
--     0
-- ),
--
-- -- Service 6: Sustainable Event Design
-- (
--     'Service',
--     22,
--     'Eco-Friendly Event Planning',
--     'Green event design and sustainability consulting',
--     'Carbon-neutral strategies, eco-friendly vendor selection',
--     1800.00,
--     0,
--     true,
--     true,
--     24,
--     120,
--     720,
--     240,
--     true,
--     false,
--     'Green Street',
--     'Portland',
--     890,
--     -122.6784,
--     45.5155,
--     1,
--     0
-- ),
--
-- -- Service 7: International Event Management
-- (
--     'Service',
--     23,
--     'Global Event Coordination',
--     'International event planning and logistics',
--     'Cross-cultural consulting, international vendor management',
--     4000.00,
--     10,
--     true,
--     true,
--     40,
--     240,
--     1440,
--     720,
--     false,
--     false,
--     'Global Avenue',
--     'New York',
--     321,
--     -74.0060,
--     40.7128,
--     1,
--     0
-- ),
--
-- -- Service 8: Event Brand Strategy
-- (
--     'Service',
--     24,
--     'Event Branding Consultancy',
--     'Strategic branding for events and experiences',
--     'Brand identity, marketing strategy, experiential design',
--     2200.00,
--     5,
--     true,
--     true,
--     16,
--     80,
--     480,
--     120,
--     true,
--     false,
--     'Creative Plaza',
--     'Miami',
--     456,
--     -80.1918,
--     25.7617,
--     1,
--     0
-- ),
--
-- -- Service 9: VIP Guest Experience
-- (
--     'Service',
--     25,
--     'Premium Guest Management',
--     'White-glove service for high-profile event guests',
--     'Concierge services, VIP coordination, luxury experience design',
--     3500.00,
--     0,
--     true,
--     true,
--     24,
--     120,
--     720,
--     240,
--     false,
--     false,
--     'Luxury Lane',
--     'Las Vegas',
--     678,
--     -115.1398,
--     36.1699,
--     1,
--     0
-- );
--
-- -- Link these new merchandise items to Goran (user_id 152)
-- INSERT INTO user_merchandise(merchandise_id, service_provider_id)
-- SELECT id, 152
-- FROM merchandise
-- WHERE id BETWEEN 7 AND 25;
--
-- insert into event_type (is_active, description, title)
-- values (true,'all','all');
--
-- insert into user_favorite_merchandises (merchandise_id, user_id)
-- select id,54
-- from merchandise
-- where id between 6 and 15;


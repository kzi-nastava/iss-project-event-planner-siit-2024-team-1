INSERT INTO public."user" (active, id, latitude, longitude, number, role, token_expiration, user_type, activation_token, authorities, city, company, description, name, password, phone_number, photo, street, surname, username) VALUES (true, 1, 1, 1, 1, 1, '2024-12-09 19:22:23.471000', 'EventOrganizer', 'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqb2huYWRvZUBleGFtcGxlLmNvbSIsImlhdCI6MTczMzY4MjE0MywiZXhwIjoxNzMzNzY4NTQzfQ.F2Q4zEG8q4i8FLBOfD6biTKGmMcjpDC-2aw505OutNKF3w8lF901qxGLVI1KKmsh', 'ahahah', 'asda', 'asd', 'asdasd', 'asd', '$2a$10$3TYrKiXr/LEDyMPvjtvqU.VApcWoOrpUetE8PaiW6DXkB4I0kCYYm', 'asdas', null, 'sasd', 'asd', 'johnadoe@example.com');
INSERT INTO public."user" (active, id, latitude, longitude, number, role, token_expiration, user_type, activation_token, authorities, city, company, description, name, password, phone_number, photo, street, surname, username) VALUES (true, 2, 1, 1, 1, 2, '2024-12-09 19:39:51.043000', 'ServiceProvider', 'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhQGEuYSIsImlhdCI6MTczMzY4MzE5MSwiZXhwIjoxNzMzNzY5NTkxfQ.6kntnma1lTlTT1Kj2oZVI14pIDfWmwnXqi6FYWYHyf9_t1G-gQ5lG446ixDxRPEE', 'ahahah', 'asd', 'asd', 'asd', 'asd', '$2a$10$COCA2xSgJHIrM5RODGOIROdelB1Xz7AD0W9YKuyGD08eEY74VXmLG', 'asd', null, 'asd', 'asd', 'a@a.a');


UPDATE public.id_generator
SET next_val = 100
WHERE sequence_name = 'user';

-- Insert Event Types
INSERT INTO event_type (title, description, is_active) VALUES
                                                           ('Tech Conference', 'Technology and innovation events', true),
                                                           ('Professional Workshop', 'Skill development and training', true),
                                                           ('Networking Event', 'Professional networking opportunities', true),
                                                           ('Educational Seminar', 'Knowledge sharing and learning', true),
                                                           ('Social Gathering', 'Community and social events', true);

-- No separate address table needed since it's an @Embeddable

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
          'San Francisco',
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
          'New York',
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
          'Austin',
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
          'Boston',
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
          'Seattle',
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
          'Miami',
          445,
          -80.1918,
          25.7617, 1
      ),
      (
          'Data Science Summit',
          'Latest trends in data analytics',
          250,
          true,
          '2024-11-15 09:00:00',
          45000.00,
          1,
          'Analytics Avenue',
          'San Jose',
          890,
          -121.8863,
          37.3382, 1
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
          45.5155, 1
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
          34.0522, 1
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
          38.9072, 1
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
          29.7604, 1
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
                                                                                (1,9),
                                                                                (1,10),
                                                                                (1,11);


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
 'Washington DC',
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
 'Washington DC',
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
 'Washington DC',
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
 'Washington DC',
 901,
 -77.0369,
 38.9072, 1, 0);


-- INSERT INTO public."user"(
--     active, id, latitude, longitude, "number", role, user_type, authorities, city, company, description, name, password, phone_number, photo, street, surname, username, activation_token, token_expiration)
-- VALUES
--     (true, 1, 40.7128, -74.0060, 123, '0', 'authenticatedUser', 'USER', 'New York', 'TechCorp', 'Software engineer', 'John', '$2a$10$3TYrKiXr/LEDyMPvjtvqU.VApcWoOrpUetE8PaiW6DXkB4I0kCYYm', '555-1234', 'john_doe.jpg', '5th Ave', 'Doe', 'john.doe@gmail.com', '', '2024-12-31'),
--     (true, 2, 34.0522, -118.2437, 456, '2', 'ServiceProvider', 'USER', 'Los Angeles', 'Creative Solutions', 'Graphic designer', 'Jane', 'password456', '555-5678', 'jane_smith.jpg', 'Sunset Blvd', 'Smith', 'jane.smith@example.com', null, null),
--     (true, 3, 51.5074, -0.1278, 789, '1', 'event_organizer', 'USER', 'London', 'Innovate Ltd', 'Marketing manager', 'Alice', 'password789', '555-8765', 'alice_white.jpg', 'Oxford St', 'White', 'alice.white@gmail.com', null, null),
--     (true, 4, 48.8566, 2.3522, 101, '1', 'event_organizer', 'USER', 'Paris', 'Design Works', 'Product designer', 'Bob', 'password101', '555-1122', 'bob_brown.jpg', 'Champs-Elysees', 'Brown', 'bob.brown@example.com', null, null),
--     (true, 5, 40.7306, -73.9352, 202, '0', 'authenticated_user', 'USER', 'New York', 'Tech Innovators', 'Frontend developer', 'Charlie', 'password202', '555-3344', 'charlie_green.jpg', 'Broadway', 'Green', 'charlie.green@gmail.com', null, null),
--     (true, 6, 37.7749, -122.4194, 303, '0', 'authenticated_user', 'USER', 'San Francisco', 'EcoSolutions', 'Project manager', 'Dave', 'password303', '555-5566', 'dave_young.jpg', 'Market St', 'Young', 'dave.young@example.com', null, null),
--     (true, 7, 39.9042, 116.4074, 404, '1', 'event_organizer', 'USER', 'Beijing', 'Global Innovations', 'Business analyst', 'Eve', 'password404', '555-6677', 'eve_liu.jpg', 'Wangfujing', 'Liu', 'eve.liu@gmail.com', null, null),
--     (true, 8, 34.0522, -118.2437, 505, '1', 'service_provider', 'USER', 'Los Angeles', 'Tech Synergy', 'Software architect', 'Frank', 'password505', '555-7788', 'frank_lee.jpg', 'Wilshire Blvd', 'Lee', 'frank.lee@example.com', null, null),
--     (true, 9, 35.6895, 139.6917, 606, '2', 'event_organizer', 'USER', 'Tokyo', 'Creative Minds', 'Data scientist', 'Grace', 'password606', '555-8899', 'grace_kim.jpg', 'Shibuya', 'Kim', 'grace.kim@gmail.com', null, null),
--     (true, 10, 40.7306, -73.9352, 707, '2', 'service_provider', 'USER', 'New York', 'DesignHub', 'UX designer', 'Hank', 'password707', '555-9900', 'hank_jones.jpg', 'Park Ave', 'Jones', 'hank.jones@example.com', null, null);


-- insert into user_followed_events(event_id,user_id)
-- values
--     (1,1),
--     (2,1),
--     (3,1);

insert into user_merchandise(merchandise_id,service_provider_id) values (4,2);


-- Reviews for "Tech Innovation Summit 2024"
INSERT INTO review (id, comment, rating, status, deleted, created_at, reviewer_id) VALUES
                                                                                     (41, 'Amazing experience! Learned so much about cutting-edge technology.', 5, 'PENDING', false, '2023-09-15 10:00:00', 1),
                                                                                     (42, 'Well-organized but I felt the sessions were too long.', 4, 'PENDING', false, '2023-09-16 11:00:00', 2),
                                                                                     (43, 'The location was too crowded for the number of attendees.', 3, 'PENDING', false, '2023-09-17 12:00:00', 1),
                                                                                     (44, 'Great event! Excellent speakers and engaging sessions.', 5, 'PENDING', false, '2023-09-18 13:00:00', 1);

-- Reviews for "AI & Machine Learning Forum"
INSERT INTO review (id, comment, rating, status, deleted, created_at, reviewer_id) VALUES
                                                                                     (45, 'Fantastic forum for AI enthusiasts. Great networking opportunities.', 5, 'PENDING', false, '2023-08-10 09:00:00', 2),
                                                                                     (46, 'Loved the hands-on workshops. Very informative.', 5, 'PENDING', false, '2023-08-11 10:00:00', 2),
                                                                                     (47, 'Would have liked more beginner-friendly content.', 3, 'PENDING', false, '2023-08-12 11:00:00', 1),
                                                                                     (48, 'Keynote speaker was excellent! Very inspiring.', 4, 'PENDING', false, '2023-08-13 12:00:00', 2);

-- Reviews for "Startup Networking Night"
INSERT INTO review (id, comment, rating, status, deleted, created_at, reviewer_id) VALUES
                                                                                     (49, 'Met a lot of amazing people. Great for entrepreneurs!', 5, 'PENDING', false, '2023-07-20 14:00:00', 2),
                                                                                     (50, 'Good opportunity, but the event seemed too short.', 4, 'PENDING', false, '2023-07-21 15:00:00', 2),
                                                                                     (51, 'Loved the casual atmosphere, and it was easy to connect with others.', 5, 'PENDING', false, '2023-07-22 16:00:00', 1),
                                                                                     (52, 'Food options were limited, but overall a good experience.', 3, 'PENDING', false, '2023-07-23 17:00:00', 1);

-- Link Reviews to Events
INSERT INTO event_reviews (event_id, review_id) VALUES
-- Tech Innovation Summit (event_id: 1)
(1, 41),
(1, 42),
(1, 43),
(1, 44),

-- AI & Machine Learning Forum (event_id: 4)
(4, 45),
(4, 46),
(4, 47),
(4, 48),

-- Startup Networking Night (event_id: 3)
(3, 49),
(3, 50),
(3, 51),
(3, 52);

-- Reviews for "Professional DJ Services"
INSERT INTO review (id, comment, rating, status, deleted, created_at, reviewer_id) VALUES
                                                                                     (53, 'The DJ played great music and kept the energy high throughout the event.', 5, 'PENDING', false, '2023-06-10 18:00:00', 2),
                                                                                     (54, 'Good service but arrived slightly late.', 4, 'PENDING', false, '2023-06-11 19:00:00', 2),
                                                                                     (55, 'Decent performance, but I expected more variety in the playlist.', 3, 'PENDING', false, '2023-06-12 20:00:00', 1),
                                                                                     (56, 'Excellent work! My guests had a blast!', 5, 'PENDING', false, '2023-06-13 21:00:00', 2);

-- Reviews for "Event Photography"
INSERT INTO review (id, comment, rating, status, deleted, created_at, reviewer_id) VALUES
                                                                                     (57, 'The photos came out beautifully, capturing every special moment.', 5, 'PENDING', false, '2023-05-01 10:30:00', 1),
                                                                                     (58, 'Professional service, but delivery was delayed.', 4, 'PENDING', false, '2023-05-02 11:30:00', 2),
                                                                                     (59, 'Attention to detail was stunning. Would hire again.', 5, 'PENDING', false, '2023-05-03 12:30:00', 1),
                                                                                     (60, 'Good quality but a bit pricey.', 4, 'PENDING', false, '2023-05-04 13:30:00', 2);

-- Link Reviews to Merchandise
INSERT INTO merchandise_reviews (merchandise_id, review_id) VALUES
-- DJ Service (merchandise_id: 1)
(1, 53),
(1, 54),
(1, 55),
(1, 56),

-- Photography Service (merchandise_id: 2)
(2, 57),
(2, 58),
(2, 59),
(2, 60);
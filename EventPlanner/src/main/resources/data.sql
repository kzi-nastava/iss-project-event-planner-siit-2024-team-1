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
    latitude
) VALUES
      (
          'Tech Innovation Summit 2024',
          'Annual conference exploring cutting-edge technologies',
          500,
          true,
          '2024-09-15 09:00:00',
          75000.00,
          1,
          'Market Street',
          'San Francisco',
          123,
          -122.4194,
          37.7749
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
          40.7128
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
          41.8781
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
          30.2672
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
          42.3601
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
          47.6062
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
          25.7617
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
          37.3382
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
          39.7392
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
          45.5155
      ),
      (
          'Tech Startup Pitch Night',
          'Pitch competition for new startups',
          150,
          true,
          '2024-10-18 18:00:00',
          20000.00,
          3,
          'Venture Avenue',
          'Los Angeles',
          678,
          -118.2437,
          34.0522
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
          38.9072
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
          29.7604
      );

-- First, ensure we have a category
INSERT INTO category (title,description,pending) VALUES ('Entertainment','Entertainment description',false);
-- First, ensure we have the id generator table and category
-- Insert into merchandise table (using SINGLE_TABLE inheritance)
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
    category_id
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
 38.9072, 1),

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
 38.9072, 1),

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
 38.9072, 1),

-- Event Planning Service
('Service',  4,'Full Event Planning',
 'Comprehensive event planning and coordination',
 'Includes venue selection, vendor coordination, and day-of management',
 2000.00, 0, true, true,
 10, 40, 168, 120, false, false,
 'Security Street',
 'Washington DC',
 901,
 -77.0369,
 38.9072, 1),

-- Decoration Service
('Service',  5,'Event Decoration',
 'Professional decoration and setup service',
 'Custom themes and designs available',
 600.00, 15, true, true,
 2, 6, 72, 48, true, false,
 'Security Street',
 'Washington DC',
 901,
 -77.0369,
 38.9072, 1);

INSERT INTO review (id, comment, rating, status) VALUES
-- DJ Service Reviews (ID: 1)
(1, 'Amazing DJ! Kept the party going all night long', 5, true),
(2, 'Great music selection and very professional', 5, true),
(3, 'Good service but could improve song transitions', 4, true),
(4, 'Fantastic energy and crowd engagement', 5, true),

-- Photography Service Reviews (ID: 2)
(5, 'Beautiful photos, captured every moment perfectly', 5, true),
(6, 'Professional service but took longer than promised for delivery', 3, true),
(7, 'Excellent attention to detail', 4, true),
(8, 'Worth every penny, amazing quality', 5, true),

-- Catering Service Reviews (ID: 3)
(9, 'Food was delicious and presentation was beautiful', 5, true),
(10, 'Great variety but some dishes were cold', 3, true),
(11, 'Professional staff and excellent service', 4, true),
(12, 'Outstanding quality and presentation', 5, true),

-- Event Planning Service Reviews (ID: 4)
(13, 'Made our event completely stress-free', 5, true),
(14, 'Excellent coordination and attention to detail', 5, true),
(15, 'Good planning but some communication issues', 4, true),
(16, 'Went above and beyond our expectations', 5, true),

-- Decoration Service Reviews (ID: 5)
(17, 'Beautiful decorations, exactly what we wanted', 5, true),
(18, 'Creative designs but setup was delayed', 3, true),
(19, 'Transformed the venue beautifully', 4, true),
(20, 'High-quality materials and great execution', 5, true);

INSERT INTO merchandise_reviews (merchandise_id, review_id) VALUES
-- DJ Service (merchandise_id: 1)
(1, 1),
(1, 2),
(1, 3),
(1, 4),

-- Photography Service (merchandise_id: 2)
(2, 5),
(2, 6),
(2, 7),
(2, 8),

-- Catering Service (merchandise_id: 3)
(3, 9),
(3, 10),
(3, 11),
(3, 12),

-- Event Planning Service (merchandise_id: 4)
(4, 13),
(4, 14),
(4, 15),
(4, 16),

-- Decoration Service (merchandise_id: 5)
(5, 17),
(5, 18),
(5, 19),
(5, 20);

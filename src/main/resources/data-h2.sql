INSERT INTO artist (artist_id, artist_name, artist_genre)
VALUES (1, 'Meshuggah', 'METAL'),
       (2, 'Bob Marley & The Wailers', 'REGGAE'),
       (3, 'Thirty Seconds to Mars', 'ROCK'),
       (4, 'Ricky Gervais', 'COMEDY'),
       (5, 'Johnny Depp', 'ROCK'),
       (6, 'Jamie Fox', 'POP'),
       (7, 'Flea', 'ROCK'),
       (8, 'Red Hot Chili Peppers', 'ROCK'),
       (9, 'Nirvana', 'GRUNGE'),
       (10, 'Queens Of The Stone Age', 'ROCK'),
       (11, 'Rival Sons', 'ROCK');

INSERT INTO member (member_id, member_name)
VALUES (1, 'Flea'),
       (2, 'Ricky Gervais'),
       (3, 'Jared Leto'),
       (4, 'Jamie Fox'),
       (5, 'Johnny Depp'),
       (6, 'Dave Grohl'),
       (7, 'Jens Kidman'),
       (8, 'Bob Marley'),
       (9, 'Jay Buchanan');

INSERT INTO artist_member (artist_id, member_id)
VALUES (1, 7),
       (2, 8),
       (3, 3),
       (4, 2),
       (5, 5),
       (6, 4),
       (7, 1),
       (8, 1),
       (9, 6),
       (10, 6),
       (11, 9);

INSERT INTO song (song_id, song_title, song_length_in_seconds, artist_id, song_genre, release_date)
VALUES (1, 'Combustion', 248, 1, 'METAL','1980-08-08'),
       (2, 'Electric Red', 351, 1, 'METAL','1980-08-08'),
       (3, 'Bleed', 442, 1, 'METAL','1980-08-08'),

       (4, 'Easy Skanking', 178, 2, 'REGGAE','1980-08-08'),
       (5, 'Kaya', 195, 2, 'REGGAE','1980-08-08'),
       (6, 'Is This Love', 232, 2, 'REGGAE','1980-08-08'),

       (7, 'Escape', 143, 3, 'ROCK','1980-08-08'),
       (8, 'Night Of The Hunter', 340, 3, 'ROCK','1980-08-08'),
       (9, 'Kings And Queens', 347, 3, 'ROCK','1980-08-08'),

       (10, 'Average Sized Penis', 325, 4, 'COMEDY','1980-08-08'),

       (11, 'Jordan', 300, 11, 'ROCK','1980-08-08');

INSERT INTO album (album_id, album_title, album_length, album_year, tracks, artist_id)
VALUES (1, 'Obzen', 1041, 2008, 3, 1),
       (2, 'Kaya', 605, 1978, 3, 2),
       (3, 'This Is War', 830, 2009, 3, 3),
       (4, 'Legend - The Best Of Bob Marley And The Wailers', 4320, 2002, 16, 2);

INSERT INTO album_song (album_id, song_id, track_number)
VALUES (1, 1,1),
       (1, 2,2),
       (1, 3,3),

       (2, 4,1),
       (2, 5,2),
       (2, 6,3),

       (3, 7,1),
       (3, 8,2),
       (3, 9,3),

       (4, 6,1);

ALTER TABLE artist
    ALTER COLUMN artist_id RESTART WITH 100;
ALTER TABLE song
    ALTER COLUMN song_id RESTART WITH 100;
ALTER TABLE album
    ALTER COLUMN album_id RESTART WITH 100;
ALTER TABLE member
    ALTER COLUMN member_id RESTART WITH 100;
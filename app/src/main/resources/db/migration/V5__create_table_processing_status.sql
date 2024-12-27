CREATE TABLE IF NOT EXISTS logistics.processing_status (
    id SERIAL PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    status INTEGER NOT NULL,
    description VARCHAR(50)
);

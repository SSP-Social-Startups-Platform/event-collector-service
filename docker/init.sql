CREATE DATABASE rtap_database
CREATE USER rtap_user WITH PASSWORD 'rtap_pass';
GRANT ALL PRIVILEGES ON DATABASE "rtap_database" to rtap_user;
-- Database Schema for Manish Hardware Backend
-- Run this script to create the database and tables

CREATE DATABASE IF NOT EXISTS manish_hardware;
USE manish_hardware;

-- Categories table
CREATE TABLE categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    image_url VARCHAR(500),
    sort_order INT DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Products table
CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT NOT NULL,
    brand VARCHAR(255),
    image_url VARCHAR(500),
    technical_specs TEXT,
    usage_info TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Product translations table
CREATE TABLE product_translations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    language_code VARCHAR(10) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    UNIQUE KEY unique_product_lang (product_id, language_code),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Banners table
CREATE TABLE banners (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    image_url VARCHAR(500),
    link_url VARCHAR(500),
    sort_order INT DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- App config table
CREATE TABLE app_config (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    key_name VARCHAR(255) UNIQUE NOT NULL,
    value TEXT,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Quote requests table
CREATE TABLE quote_requests (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    product_id BIGINT,
    quantity VARCHAR(100),
    location TEXT,
    language_code VARCHAR(10) DEFAULT 'en',
    status ENUM('pending', 'contacted', 'completed') DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Message templates table
CREATE TABLE message_templates (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(50) NOT NULL, -- e.g., 'whatsapp_quote'
    language_code VARCHAR(10) NOT NULL,
    template TEXT NOT NULL,
    UNIQUE KEY unique_type_lang (type, language_code)
);

-- Supported languages table
CREATE TABLE supported_languages (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(10) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    is_default BOOLEAN DEFAULT FALSE
);

-- Indexes for performance
CREATE INDEX idx_categories_sort_order ON categories(sort_order);
CREATE INDEX idx_categories_is_active ON categories(is_active);
CREATE INDEX idx_products_category_id ON products(category_id);
CREATE INDEX idx_products_is_active ON products(is_active);
CREATE INDEX idx_product_translations_product_id ON product_translations(product_id);
CREATE INDEX idx_product_translations_language_code ON product_translations(language_code);
CREATE INDEX idx_banners_sort_order ON banners(sort_order);
CREATE INDEX idx_banners_is_active ON banners(is_active);
CREATE INDEX idx_quote_requests_status ON quote_requests(status);
CREATE INDEX idx_quote_requests_created_at ON quote_requests(created_at);
CREATE INDEX idx_message_templates_type ON message_templates(type);
CREATE INDEX idx_message_templates_language_code ON message_templates(language_code);

-- Sample data
INSERT INTO supported_languages (code, name, is_default) VALUES ('en', 'English', TRUE);
INSERT INTO supported_languages (code, name, is_default) VALUES ('ne', 'नेपाली', FALSE);

INSERT INTO app_config (key_name, value) VALUES ('phone_number', '+977-1234567890');
INSERT INTO app_config (key_name, value) VALUES ('whatsapp_number', '+977-1234567890');
INSERT INTO app_config (key_name, value) VALUES ('address', 'Kathmandu, Nepal');
INSERT INTO app_config (key_name, value) VALUES ('business_hours', '9 AM - 6 PM');

INSERT INTO message_templates (type, language_code, template) VALUES ('whatsapp_quote', 'en', 'Hello, I want to inquire about {product} for {quantity}. Name: {name}, Location: {location}');
INSERT INTO message_templates (type, language_code, template) VALUES ('whatsapp_quote', 'ne', 'नमस्ते, म {product} को लागि {quantity} को लागि सोध्न चाहन्छु। नाम: {name}, स्थान: {location}');

INSERT INTO categories (name, description, sort_order) VALUES ('Cement', 'High quality cement for construction', 1);
INSERT INTO categories (name, description, sort_order) VALUES ('Steel', 'TMT bars and steel products', 2);
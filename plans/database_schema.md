# Database Schema

## Overview

The database uses MySQL/PostgreSQL with proper normalization and indexing. All data is configurable, no hardcoded values.

## Tables

### categories

```sql
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
```

### products

```sql
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
```

### product_translations

```sql
CREATE TABLE product_translations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    language_code VARCHAR(10) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    UNIQUE KEY unique_product_lang (product_id, language_code),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
```

### banners

```sql
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
```

### app_config

```sql
CREATE TABLE app_config (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    key_name VARCHAR(255) UNIQUE NOT NULL,
    value TEXT,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
-- Sample data: phone_number, whatsapp_number, address, business_hours
```

### quote_requests

```sql
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
```

### message_templates

```sql
CREATE TABLE message_templates (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(50) NOT NULL, -- e.g., 'whatsapp_quote'
    language_code VARCHAR(10) NOT NULL,
    template TEXT NOT NULL,
    UNIQUE KEY unique_type_lang (type, language_code)
);
-- Sample: "Hello, I want to inquire about {product} for {quantity}. Name: {name}, Location: {location}"
```

### supported_languages

```sql
CREATE TABLE supported_languages (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(10) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    is_default BOOLEAN DEFAULT FALSE
);
-- Sample: ('en', 'English'), ('ne', 'नेपाली')
```

## Indexes

- categories: sort_order, is_active
- products: category_id, is_active
- product_translations: product_id, language_code
- banners: sort_order, is_active
- quote_requests: status, created_at
- message_templates: type, language_code

## Constraints

- Foreign key constraints for referential integrity
- Unique constraints where applicable
- NOT NULL for required fields

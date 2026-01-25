# Backend API Design

## Overview

The backend provides RESTful APIs using Spring Boot. Public APIs are open, admin APIs require authentication. All responses include localization support.

## Base URL

- Production: `https://api.manishhardware.com/v1`
- Versioning: Path-based (`/v1/`)

## Authentication

- Admin APIs: Bearer token (JWT)
- Public APIs: No auth required

## Response Format

```json
{
  "success": true,
  "data": {},
  "message": "Success",
  "language": "en"
}
```

## Public Endpoints

### App Configuration

- **GET** `/config`
  - Returns app config, supported languages, contact numbers

### Banners

- **GET** `/banners`
  - Paginated list of dynamic banners

### Categories

- **GET** `/categories`
  - List all categories with metadata

### Products

- **GET** `/products?category_id={id}&page={page}&limit={limit}`
  - Paginated products by category
- **GET** `/products/{id}`
  - Product details with translations

### Quote Requests

- **POST** `/quotes`
  - Submit quote request
  - Body: `{name, phone, product_id, quantity, location, language}`

### Localization

- **GET** `/locales/{lang}`
  - Get localization strings for language

## Admin Endpoints (Requires Auth)

### Admin Categories

- **GET** `/admin/categories`
- **POST** `/admin/categories`
- **PUT** `/admin/categories/{id}`
- **DELETE** `/admin/categories/{id}`

### Admin Products

- **GET** `/admin/products`
- **POST** `/admin/products`
- **PUT** `/admin/products/{id}`
- **DELETE** `/admin/products/{id}`

### Product Translations

- **POST** `/admin/products/{id}/translations`
- **PUT** `/admin/products/{id}/translations/{lang}`

### Admin Banners

- **GET** `/admin/banners`
- **POST** `/admin/banners`
- **PUT** `/admin/banners/{id}`
- **DELETE** `/admin/banners/{id}`

### App Config

- **GET** `/admin/config`
- **PUT** `/admin/config`

### Admin Quote Requests

- **GET** `/admin/quotes`
- **PUT** `/admin/quotes/{id}/status`

### Message Templates

- **GET** `/admin/templates`
- **PUT** `/admin/templates/{type}`

## Error Handling

- 400: Bad Request
- 401: Unauthorized
- 404: Not Found
- 500: Internal Server Error

## Rate Limiting

- Quote requests: 10 per hour per IP
- Admin APIs: 100 per minute per token

## Validation

- Server-side validation for all inputs
- Localized error messages

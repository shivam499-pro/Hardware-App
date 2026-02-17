# Manish Hardware - API Endpoints Documentation

## Overview
This document provides a comprehensive list of all API endpoints connecting the Backend, Admin Panel, and Mobile App.

**Base URL:** `http://localhost:8080/api/v1`

---

## Authentication Endpoints (`/auth`)

| Method | Endpoint | Description | Admin | Mobile |
|--------|----------|-------------|-------|--------|
| POST | `/auth/login` | User login | ✅ | ✅ |
| POST | `/auth/register` | Register new admin user | ✅ | ✅ |
| GET | `/auth/me` | Get current user profile | ✅ | ✅ |
| POST | `/auth/change-password` | Change password | ✅ | ✅ |
| PUT | `/auth/profile` | Update profile | ✅ | ✅ |
| GET | `/auth/users` | Get all admin users | ✅ | ❌ |
| PUT | `/auth/users/{id}/deactivate` | Deactivate user | ✅ | ❌ |
| PUT | `/auth/users/{id}/activate` | Activate user | ✅ | ❌ |
| PUT | `/auth/users/{id}/reset-password` | Reset user password | ✅ | ❌ |
| GET | `/auth/users/by-email/{email}` | Get user by email | ✅ | ✅ |

---

## Category Endpoints (`/categories`)

| Method | Endpoint | Description | Admin | Mobile |
|--------|----------|-------------|-------|--------|
| GET | `/categories` | Get all active categories | ✅ | ✅ |
| GET | `/categories/{id}` | Get category by ID | ✅ | ✅ |
| GET | `/categories/ordered` | Get categories ordered by sort | ✅ | ✅ |
| POST | `/categories` | Create category | ✅ | ✅ |
| PUT | `/categories/{id}` | Update category | ✅ | ✅ |
| DELETE | `/categories/{id}` | Delete category (soft) | ✅ | ✅ |

---

## Product Endpoints (`/products`)

| Method | Endpoint | Description | Admin | Mobile |
|--------|----------|-------------|-------|--------|
| GET | `/products` | Get all products (paginated) | ✅ | ✅ |
| GET | `/products/{id}` | Get product by ID | ✅ | ✅ |
| GET | `/products/{id}/lang/{languageCode}` | Get product by ID and language | ✅ | ✅ |
| GET | `/products/category/{categoryId}` | Get products by category | ✅ | ✅ |
| GET | `/products/search` | Search products | ✅ | ✅ |
| GET | `/products/{id}/translations` | Get product translations | ✅ | ✅ |
| GET | `/products/translations/language/{languageCode}` | Get translations by language | ✅ | ✅ |
| GET | `/products/{id}/translations/{languageCode}/exists` | Check translation exists | ✅ | ✅ |
| GET | `/products/count/category/{categoryId}` | Count products in category | ✅ | ✅ |
| POST | `/products` | Create product | ✅ | ✅ |
| PUT | `/products/{id}` | Update product | ✅ | ✅ |
| DELETE | `/products/{id}` | Delete product (soft) | ✅ | ✅ |
| DELETE | `/products/{id}/hard` | Hard delete product | ✅ | ✅ |
| POST | `/products/{id}/translations` | Add translation | ✅ | ✅ |
| PUT | `/products/{id}/translations/{languageCode}` | Update translation | ✅ | ✅ |
| DELETE | `/products/{id}/translations/{languageCode}` | Delete translation | ✅ | ✅ |

---

## Banner Endpoints (`/banners`)

| Method | Endpoint | Description | Admin | Mobile |
|--------|----------|-------------|-------|--------|
| GET | `/banners` | Get active banners | ✅ | ✅ |
| GET | `/banners/all` | Get all banners | ✅ | ✅ |
| GET | `/banners/{id}` | Get banner by ID | ✅ | ✅ |
| GET | `/banners/unsorted` | Get active banners unsorted | ✅ | ✅ |
| GET | `/banners/desc` | Get active banners descending | ✅ | ✅ |
| POST | `/banners` | Create banner | ✅ | ✅ |
| PUT | `/banners/{id}` | Update banner | ✅ | ✅ |
| DELETE | `/banners/{id}` | Delete banner (soft) | ✅ | ✅ |
| DELETE | `/banners/{id}/hard` | Hard delete banner | ✅ | ✅ |
| PUT | `/banners/{id}/sort` | Update sort order | ✅ | ✅ |
| PUT | `/banners/sort-order` | Batch update sort order | ✅ | ✅ |

---

## Quote Request Endpoints (`/quotes`)

| Method | Endpoint | Description | Admin | Mobile |
|--------|----------|-------------|-------|--------|
| GET | `/quotes` | Get all quotes (paginated) | ✅ | ✅ |
| GET | `/quotes/recent` | Get recent quotes | ✅ | ✅ |
| GET | `/quotes/{id}` | Get quote by ID | ✅ | ✅ |
| GET | `/quotes/status/{status}` | Get quotes by status | ✅ | ✅ |
| GET | `/quotes/search` | Search quotes | ✅ | ✅ |
| GET | `/quotes/date-range` | Get quotes by date range | ✅ | ✅ |
| GET | `/quotes/statistics` | Get quote statistics | ✅ | ✅ |
| GET | `/quotes/product/{productId}` | Get quotes by product | ✅ | ✅ |
| GET | `/quotes/phone/{phone}` | Get quotes by phone | ✅ | ✅ |
| GET | `/quotes/language/{languageCode}` | Get quotes by language | ✅ | ✅ |
| GET | `/quotes/after/{date}` | Get quotes after date | ✅ | ✅ |
| POST | `/quotes` | Submit quote request | ✅ | ✅ |
| POST | `/quotes/statuses` | Get quotes by multiple statuses | ✅ | ✅ |
| PUT | `/quotes/{id}` | Update quote | ✅ | ✅ |
| PATCH | `/quotes/{id}/status` | Update quote status | ✅ | ✅ |
| PATCH | `/quotes/{id}/contacted` | Mark as contacted | ✅ | ✅ |
| PATCH | `/quotes/{id}/completed` | Mark as completed | ✅ | ✅ |
| DELETE | `/quotes/{id}` | Delete quote | ✅ | ✅ |

---

## App Config Endpoints (`/config`)

| Method | Endpoint | Description | Admin | Mobile |
|--------|----------|-------------|-------|--------|
| GET | `/config` | Get all configs as map | ✅ | ✅ |
| GET | `/config/business` | Get business config | ✅ | ✅ |
| GET | `/config/{key}` | Get config by key | ✅ | ✅ |
| GET | `/config/{key}/value` | Get config value | ✅ | ✅ |
| GET | `/config/{key}/value/{defaultValue}` | Get config value with default | ✅ | ✅ |
| POST | `/config/batch` | Get multiple configs | ✅ | ✅ |
| GET | `/config/admin/all` | Get all configs (list) | ✅ | ✅ |
| POST | `/config/admin` | Create config | ✅ | ✅ |
| PUT | `/config/admin/{key}` | Save config | ✅ | ✅ |
| PUT | `/config/admin/id/{id}` | Update config by ID | ✅ | ✅ |
| DELETE | `/config/admin/{id}` | Delete config by ID | ✅ | ✅ |
| DELETE | `/config/admin/key/{key}` | Delete config by key | ✅ | ✅ |
| POST | `/config/admin/batch` | Batch save configs | ✅ | ✅ |

---

## Supported Language Endpoints (`/languages`)

| Method | Endpoint | Description | Admin | Mobile |
|--------|----------|-------------|-------|--------|
| GET | `/languages` | Get active languages | ✅ | ✅ |
| GET | `/languages/default` | Get default language | ✅ | ✅ |
| GET | `/languages/default/code` | Get default language code | ✅ | ✅ |
| GET | `/languages/{code}` | Get language by code | ✅ | ✅ |
| GET | `/languages/{code}/supported` | Check if language supported | ✅ | ✅ |
| GET | `/languages/active` | Get active languages unsorted | ✅ | ✅ |
| GET | `/languages/default/any` | Get default language any | ✅ | ✅ |
| GET | `/languages/admin/all` | Get all languages | ✅ | ✅ |
| GET | `/languages/admin/{id}` | Get language by ID | ✅ | ✅ |
| POST | `/languages/admin` | Create language | ✅ | ✅ |
| PUT | `/languages/admin/{id}` | Update language | ✅ | ✅ |
| PUT | `/languages/admin/default/{code}` | Set default language | ✅ | ✅ |
| PUT | `/languages/admin/default-direct/{code}` | Set default language direct | ✅ | ✅ |
| PATCH | `/languages/admin/{id}/toggle` | Toggle language active | ✅ | ✅ |
| DELETE | `/languages/admin/{id}` | Delete language | ✅ | ✅ |
| DELETE | `/languages/admin/code/{code}` | Delete language by code | ✅ | ✅ |

---

## Message Template Endpoints (`/templates`)

| Method | Endpoint | Description | Admin | Mobile |
|--------|----------|-------------|-------|--------|
| GET | `/templates` | Get all templates | ✅ | ✅ |
| GET | `/templates/types` | Get all template types | ✅ | ✅ |
| GET | `/templates/{id}` | Get template by ID | ✅ | ✅ |
| GET | `/templates/{type}/{languageCode}` | Get template by type and language | ✅ | ✅ |
| GET | `/templates/{type}/{languageCode}/content` | Get template content | ✅ | ✅ |
| GET | `/templates/type/{type}` | Get templates by type | ✅ | ✅ |
| GET | `/templates/language/{languageCode}` | Get templates by language | ✅ | ✅ |
| GET | `/templates/map/{languageCode}` | Get templates as map | ✅ | ✅ |
| POST | `/templates/render` | Render template | ✅ | ✅ |
| POST | `/templates/render/whatsapp-quote` | Render WhatsApp quote | ✅ | ✅ |
| POST | `/templates/admin` | Create template | ✅ | ✅ |
| PUT | `/templates/admin/{type}/{languageCode}` | Save template | ✅ | ✅ |
| PUT | `/templates/admin/{id}` | Update template | ✅ | ✅ |
| DELETE | `/templates/admin/{id}` | Delete template | ✅ | ✅ |
| DELETE | `/templates/admin/{type}/{languageCode}` | Delete template by type/language | ✅ | ✅ |

---

## Admin Dashboard Endpoints (`/admin`)

| Method | Endpoint | Description | Admin | Mobile |
|--------|----------|-------------|-------|--------|
| GET | `/admin/dashboard` | Get dashboard stats | ✅ | ❌ |
| GET | `/admin/stats` | Get statistics | ✅ | ❌ |

---

## Data Flow Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                        FRONTEND LAYER                           │
├─────────────────────────┬───────────────────────────────────────┤
│     Admin Panel         │         Mobile App                    │
│    (React + MUI)        │     (React Native + Expo)            │
│                         │                                        │
│  - Dashboard            │  - Home Screen                        │
│  - Categories           │  - Categories                         │
│  - Products             │  - Products                           │
│  - Quotes               │  - Quote Request                      │
│  - Banners              │  - Contact                            │
│  - Languages            │  - About                              │
│  - Templates            │  - Settings                           │
│  - Config               │                                        │
│  - Users                │                                        │
└────────────┬────────────┴──────────────┬────────────────────────┘
             │                             │
             │    HTTP/REST (JSON)         │
             │                             │
             ▼                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                     BACKEND LAYER                               │
│                  Spring Boot 3.2.0                              │
├─────────────────────────────────────────────────────────────────┤
│  Controllers                                                    │
│  ├── AuthController          - Authentication & User Management │
│  ├── CategoryController      - Category CRUD                    │
│  ├── ProductController       - Product CRUD & Translations      │
│  ├── BannerController        - Banner Management                │
│  ├── QuoteRequestController  - Quote Request Management         │
│  ├── AppConfigController     - App Configuration                │
│  ├── SupportedLanguageController - Language Management          │
│  ├── MessageTemplateController - Template Management            │
│  └── AdminController         - Dashboard & Statistics           │
├─────────────────────────────────────────────────────────────────┤
│  Services                                                       │
│  ├── AuthService             - Authentication Logic             │
│  ├── CategoryService         - Category Business Logic          │
│  ├── ProductService          - Product Business Logic           │
│  ├── BannerService           - Banner Business Logic            │
│  ├── QuoteRequestService     - Quote Request Logic              │
│  ├── AppConfigService        - Config Management                │
│  ├── SupportedLanguageService - Language Logic                  │
│  └── MessageTemplateService  - Template Rendering               │
├─────────────────────────────────────────────────────────────────┤
│  Security                                                       │
│  ├── JWT Authentication       - Token-based Auth                │
│  ├── Role-based Access        - ADMIN role required             │
│  └── CORS Configuration       - Cross-origin support            │
└─────────────────────────────────────────────────────────────────┘
             │
             │  JPA/Hibernate
             │
             ▼
┌─────────────────────────────────────────────────────────────────┐
│                      DATABASE LAYER                             │
│                        MySQL 8                                  │
├─────────────────────────────────────────────────────────────────┤
│  Tables                                                         │
│  ├── admin_users          - Admin user accounts                 │
│  ├── categories           - Product categories                  │
│  ├── products             - Products                            │
│  ├── product_translations - Product translations (i18n)         │
│  ├── banners              - Home page banners                   │
│  ├── quote_requests       - Customer quote requests             │
│  ├── app_config           - Application configuration           │
│  ├── supported_languages  - Supported languages                 │
│  └── message_templates    - Message templates (WhatsApp, etc.)  │
└─────────────────────────────────────────────────────────────────┘
```

---

## Authentication Flow

```
1. Login Request
   ┌──────────┐     POST /auth/login      ┌──────────┐
   │  Client  │ ─────────────────────────▶│  Server  │
   │          │    {username, password}    │          │
   └──────────┘                            └──────────┘
                                                  │
                                                  │ Validate credentials
                                                  │ Generate JWT token
                                                  ▼
   ┌──────────┐     JWT Token Response    ┌──────────┐
   │  Client  │ ◀─────────────────────────│  Server  │
   │          │  {accessToken, expiresIn} │          │
   └──────────┘                            └──────────┘

2. Authenticated Request
   ┌──────────┐     GET /api/endpoint     ┌──────────┐
   │  Client  │ ─────────────────────────▶│  Server  │
   │          │   Authorization: Bearer   │          │
   └──────────┘       <JWT_TOKEN>         └──────────┘
                                                  │
                                                  │ Validate JWT
                                                  │ Check role
                                                  ▼
   ┌──────────┐     JSON Response         ┌──────────┐
   │  Client  │ ◀─────────────────────────│  Server  │
   └──────────┘                            └──────────┘
```

---

## Running the Application

### Backend
```bash
cd backend/manish-hardware-backend
mvn spring-boot:run
```

### Admin Panel
```bash
cd admin
npm install
npm start
```

### Mobile App
```bash
cd mobile
npm install
npx expo start
```

---

## Testing

### Run Backend Tests
```bash
cd backend/manish-hardware-backend
mvn test
```

### Run Integration Tests
```bash
mvn test -Dtest=EndToEndIntegrationTest
```

---

## Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_DATASOURCE_URL` | Database URL | `jdbc:mysql://localhost:3306/manish_hardware` |
| `SPRING_DATASOURCE_USERNAME` | Database username | `root` |
| `SPRING_DATASOURCE_PASSWORD` | Database password | - |
| `SERVER_PORT` | Server port | `8080` |
| `APP_JWT_SECRET` | JWT secret key | - |
| `APP_JWT_EXPIRATION` | JWT expiration (ms) | `86400000` |

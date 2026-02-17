# End-to-End API Connections Documentation

This document provides a comprehensive overview of all API endpoints and their connections between the Backend, Admin Panel, and Mobile App.

## Architecture Overview

```
                    +-------------------+
                    |   MySQL Database  |
                    +-------------------+
                            ^
                            | (JPA/Hibernate)
                            v
                    +-------------------+
                    |  Spring Boot      |
                    |  Backend          |
                    |  (Port 8080)      |
                    +-------------------+
                            ^
                            | (REST API)
            +---------------+---------------+
            |                               |
            v                               v
    +-------------------+           +-------------------+
    |  React Admin      |           |  React Native     |
    |  Panel            |           |  Mobile App       |
    |  (Port 3000)      |           |  (Expo)           |
    +-------------------+           +-------------------+
```

## Base URL

- **Backend API**: `http://localhost:8080/api/v1`

---

## Authentication APIs

### Endpoints

| Method | Endpoint | Description | Admin | Mobile |
|--------|----------|-------------|-------|--------|
| POST | `/auth/login` | User login | Login.tsx | authService.login() |
| POST | `/auth/register` | Register new admin user | Users.tsx | authService.register() |
| GET | `/auth/me` | Get current user profile | - | authService.getCurrentUser() |
| POST | `/auth/change-password` | Change password | Settings.tsx | authService.changePassword() |
| PUT | `/auth/profile` | Update profile | - | authService.updateProfile() |
| GET | `/auth/users` | Get all admin users | Users.tsx | authService.getAllUsers() |
| PUT | `/auth/users/{id}/deactivate` | Deactivate user | Users.tsx | authService.deactivateUser() |
| PUT | `/auth/users/{id}/activate` | Activate user | Users.tsx | authService.activateUser() |
| PUT | `/auth/users/{id}/reset-password` | Reset user password | Users.tsx | authService.resetUserPassword() |
| GET | `/auth/users/by-email/{email}` | Get user by email | Users.tsx | authService.getUserByEmail() |

### Data Flow

```
Admin Panel                    Backend                      Database
    |                             |                            |
    |---POST /auth/login--------->|                            |
    |                             |---SELECT admin_user------->|
    |                             |<---admin_user_data--------|
    |                             |---BCrypt verify-----------|
    |<---JWT Token----------------|                            |
    |                             |                            |
```

---

## Category APIs

### Endpoints

| Method | Endpoint | Description | Admin | Mobile |
|--------|----------|-------------|-------|--------|
| GET | `/categories` | Get all active categories | Categories.tsx, Dashboard.tsx | categoryService.getAllCategories() |
| GET | `/categories/{id}` | Get category by ID | - | categoryService.getCategoryById() |
| POST | `/categories` | Create category | Categories.tsx | categoryService.createCategory() |
| PUT | `/categories/{id}` | Update category | Categories.tsx | categoryService.updateCategory() |
| DELETE | `/categories/{id}` | Delete category (soft) | Categories.tsx | categoryService.deleteCategory() |
| GET | `/categories/ordered` | Get categories ordered | - | categoryService.getAllCategoriesOrdered() |

### Data Flow

```
Mobile App                     Backend                      Database
    |                             |                            |
    |---GET /categories---------->|                            |
    |                             |---SELECT category--------->|
    |                             |<---category_data----------|
    |<---Category[]---------------|                            |
    |                             |                            |
```

---

## Product APIs

### Endpoints

| Method | Endpoint | Description | Admin | Mobile |
|--------|----------|-------------|-------|--------|
| GET | `/products` | Get all products (paginated) | Products.tsx | productService.getAllProducts() |
| GET | `/products/category/{id}` | Get products by category | Products.tsx | productService.getProductsByCategory() |
| GET | `/products/{id}` | Get product by ID | - | ProductDetailScreen.tsx |
| GET | `/products/{id}/lang/{code}` | Get product by language | - | productService.getProductByIdAndLanguage() |
| GET | `/products/search` | Search products | Products.tsx | productService.searchProducts() |
| GET | `/products/{id}/translations` | Get product translations | - | productService.getProductTranslations() |
| POST | `/products` | Create product | Products.tsx | productService.createProduct() |
| PUT | `/products/{id}` | Update product | Products.tsx | productService.updateProduct() |
| DELETE | `/products/{id}` | Delete product (soft) | Products.tsx | productService.deleteProduct() |
| POST | `/products/{id}/translations` | Add translation | Products.tsx | productService.addTranslation() |
| PUT | `/products/{id}/translations/{code}` | Update translation | - | productService.updateTranslation() |
| DELETE | `/products/{id}/translations/{code}` | Delete translation | - | productService.deleteTranslation() |
| GET | `/products/translations/language/{code}` | Get translations by language | - | productService.getTranslationsByLanguage() |
| GET | `/products/{id}/translations/{code}/exists` | Check translation exists | - | productService.translationExists() |
| GET | `/products/count/category/{id}` | Count products in category | - | productService.countInCategory() |
| DELETE | `/products/{id}/hard` | Hard delete product | - | productService.hardDeleteProduct() |

---

## Banner APIs

### Endpoints

| Method | Endpoint | Description | Admin | Mobile |
|--------|----------|-------------|-------|--------|
| GET | `/banners/all` | Get all banners | Banners.tsx | bannerService.getAllBanners() |
| GET | `/banners` | Get active banners | - | HomeScreen.tsx |
| GET | `/banners/{id}` | Get banner by ID | - | bannerService.getBannerById() |
| POST | `/banners` | Create banner | Banners.tsx | bannerService.createBanner() |
| PUT | `/banners/{id}` | Update banner | Banners.tsx | bannerService.updateBanner() |
| DELETE | `/banners/{id}` | Delete banner (soft) | Banners.tsx | bannerService.deleteBanner() |
| DELETE | `/banners/{id}/hard` | Hard delete banner | - | bannerService.hardDeleteBanner() |
| PUT | `/banners/{id}/sort` | Update sort order | Banners.tsx | bannerService.updateSortOrder() |
| PUT | `/banners/sort-order` | Batch update sort order | - | bannerService.batchUpdateSortOrder() |
| GET | `/banners/unsorted` | Get active unsorted | - | bannerService.getActiveBannersUnsorted() |
| GET | `/banners/desc` | Get active descending | - | bannerService.getActiveBannersDesc() |

---

## Quote Request APIs

### Endpoints

| Method | Endpoint | Description | Admin | Mobile |
|--------|----------|-------------|-------|--------|
| GET | `/quotes` | Get all quotes (paginated) | Quotes.tsx | quoteService.getAllQuoteRequests() |
| GET | `/quotes/recent` | Get recent quotes | Dashboard.tsx | quoteService.getRecentQuoteRequests() |
| GET | `/quotes/{id}` | Get quote by ID | - | quoteService.getQuoteRequestById() |
| GET | `/quotes/status/{status}` | Get quotes by status | Quotes.tsx | quoteService.getQuoteRequestsByStatus() |
| GET | `/quotes/search` | Search quotes | Quotes.tsx | quoteService.searchQuoteRequests() |
| GET | `/quotes/date-range` | Get quotes by date range | - | - |
| GET | `/quotes/statistics` | Get quote statistics | Quotes.tsx, Dashboard.tsx | quoteService.getQuoteStatistics() |
| GET | `/quotes/product/{id}` | Get quotes by product | - | - |
| GET | `/quotes/phone/{phone}` | Get quotes by phone | - | - |
| GET | `/quotes/language/{code}` | Get quotes by language | - | quoteService.getQuoteRequestsByLanguage() |
| GET | `/quotes/after/{date}` | Get quotes after date | - | quoteService.getQuoteRequestsAfterDate() |
| POST | `/quotes/statuses` | Get quotes by statuses | - | quoteService.getQuoteRequestsByStatuses() |
| POST | `/quotes` | Create quote request | - | RequestQuoteScreen.tsx |
| PUT | `/quotes/{id}` | Update quote request | - | quoteService.updateQuoteRequest() |
| PATCH | `/quotes/{id}/status` | Update quote status | Quotes.tsx | quoteService.updateQuoteStatus() |
| PATCH | `/quotes/{id}/contacted` | Mark as contacted | Quotes.tsx | quoteService.markAsContacted() |
| PATCH | `/quotes/{id}/completed` | Mark as completed | Quotes.tsx | quoteService.markAsCompleted() |
| DELETE | `/quotes/{id}` | Delete quote | Quotes.tsx | quoteService.deleteQuoteRequest() |

---

## App Configuration APIs

### Endpoints

| Method | Endpoint | Description | Admin | Mobile |
|--------|----------|-------------|-------|--------|
| GET | `/config` | Get all configs as map | - | configService.getAllConfigsAsMap() |
| GET | `/config/business` | Get business config | Config.tsx | ContactScreen.tsx, AboutScreen.tsx |
| GET | `/config/{key}` | Get config by key | - | configService.getConfigByKey() |
| GET | `/config/{key}/value` | Get config value | - | configService.getConfigValue() |
| GET | `/config/{key}/value/{default}` | Get value with default | - | configService.getConfigValueWithDefault() |
| POST | `/config/batch` | Get multiple configs | - | configService.getConfigsByKeys() |
| GET | `/config/admin/all` | Get all configs (admin) | Config.tsx | configService.getAllConfigs() |
| POST | `/config/admin` | Create config | Config.tsx | configService.createConfig() |
| PUT | `/config/admin/{key}` | Save config | Config.tsx | configService.saveConfig() |
| PUT | `/config/admin/id/{id}` | Update config by ID | - | configService.updateConfig() |
| DELETE | `/config/admin/{id}` | Delete config | Config.tsx | configService.deleteConfig() |
| DELETE | `/config/admin/key/{key}` | Delete config by key | - | configService.deleteConfigByKey() |
| POST | `/config/admin/batch` | Batch save configs | Config.tsx | configService.batchSaveConfigs() |

---

## Supported Language APIs

### Endpoints

| Method | Endpoint | Description | Admin | Mobile |
|--------|----------|-------------|-------|--------|
| GET | `/languages` | Get active languages | Templates.tsx | HomeScreen.tsx |
| GET | `/languages/default` | Get default language | - | languageService.getDefaultLanguage() |
| GET | `/languages/default/code` | Get default language code | - | languageService.getDefaultLanguageCode() |
| GET | `/languages/{code}` | Get language by code | - | languageService.getLanguageByCode() |
| GET | `/languages/{code}/supported` | Check if supported | - | languageService.isLanguageSupported() |
| GET | `/languages/admin/all` | Get all languages | Languages.tsx | languageService.getAllLanguages() |
| GET | `/languages/admin/{id}` | Get language by ID | - | languageService.getLanguageById() |
| POST | `/languages/admin` | Create language | Languages.tsx | languageService.createLanguage() |
| PUT | `/languages/admin/{id}` | Update language | Languages.tsx | languageService.updateLanguage() |
| PUT | `/languages/admin/default/{code}` | Set default language | Languages.tsx | languageService.setDefaultLanguage() |
| PUT | `/languages/admin/default-direct/{code}` | Set default direct | - | languageService.setDefaultLanguageDirect() |
| GET | `/languages/active` | Get active unsorted | - | languageService.getActiveLanguagesUnsorted() |
| GET | `/languages/default/any` | Get default any | - | languageService.getDefaultLanguageAny() |
| PATCH | `/languages/admin/{id}/toggle` | Toggle active | Languages.tsx | languageService.toggleLanguageActive() |
| DELETE | `/languages/admin/{id}` | Delete language | Languages.tsx | languageService.deleteLanguage() |
| DELETE | `/languages/admin/code/{code}` | Delete by code | - | languageService.deleteLanguageByCode() |

---

## Message Template APIs

### Endpoints

| Method | Endpoint | Description | Admin | Mobile |
|--------|----------|-------------|-------|--------|
| GET | `/templates` | Get all templates | Templates.tsx | templateService.getAllTemplates() |
| GET | `/templates/types` | Get template types | Templates.tsx | templateService.getAllTemplateTypes() |
| GET | `/templates/{id}` | Get template by ID | - | templateService.getTemplateById() |
| GET | `/templates/{type}/{code}` | Get by type and language | - | templateService.getTemplateByTypeAndLanguage() |
| GET | `/templates/type/{type}` | Get templates by type | - | templateService.getTemplatesByType() |
| GET | `/templates/language/{code}` | Get templates by language | - | templateService.getTemplatesByLanguage() |
| GET | `/templates/map/{code}` | Get templates as map | - | templateService.getTemplatesAsMapByLanguage() |
| POST | `/templates/render` | Render template | - | templateService.renderTemplate() |
| POST | `/templates/render/whatsapp-quote` | Render WhatsApp quote | - | RequestQuoteScreen.tsx |
| GET | `/templates/{type}/{code}/content` | Get template content | - | AboutScreen.tsx |
| POST | `/templates/admin` | Create template | Templates.tsx | templateService.createTemplate() |
| PUT | `/templates/admin/{type}/{code}` | Save template | - | templateService.saveTemplate() |
| PUT | `/templates/admin/{id}` | Update template | Templates.tsx | templateService.updateTemplate() |
| DELETE | `/templates/admin/{id}` | Delete template | Templates.tsx | templateService.deleteTemplate() |
| DELETE | `/templates/admin/{type}/{code}` | Delete by type and code | - | templateService.deleteTemplateByTypeAndLanguage() |

---

## Admin Dashboard APIs

### Endpoints

| Method | Endpoint | Description | Admin | Mobile |
|--------|----------|-------------|-------|--------|
| GET | `/admin/dashboard` | Get dashboard data | Dashboard.tsx | - |
| GET | `/admin/stats` | Get statistics | - | - |

### Dashboard Data Flow

```
Admin Panel                    Backend                      Database
    |                             |                            |
    |---GET /admin/dashboard----->|                            |
    |                             |---SELECT COUNT(*)--------->|
    |                             |<---counts------------------|
    |                             |---SELECT quotes----------->|
    |                             |<---recent_quotes----------|
    |<---{totalCategories,        |                            |
    |     totalBanners,           |                            |
    |     quoteStats,             |                            |
    |     recentQuotes}-----------|                            |
```

---

## Security Configuration

### JWT Authentication Flow

```
Client                         Backend                      Database
    |                             |                            |
    |---POST /auth/login--------->|                            |
    |    {username, password}     |                            |
    |                             |---SELECT admin_user------->|
    |                             |<---user_data--------------|
    |                             |                            |
    |                             |---BCrypt.verify()--------->|
    |                             |                            |
    |                             |---JwtTokenProvider-------->|
    |<---{accessToken,            |                            |
    |     tokenType,              |                            |
    |     expiresIn,              |                            |
    |     username,               |                            |
    |     fullName}---------------|                            |
    |                             |                            |
    |---GET /api/protected------->|                            |
    |    Authorization: Bearer xxx |                            |
    |                             |---JwtAuthenticationFilter->|
    |                             |---Validate token---------->|
    |<---Protected Data-----------|                            |
```

### Role-Based Access Control

- All admin endpoints require `ADMIN` role
- Public endpoints (GET active categories, products, etc.) are accessible without authentication
- JWT tokens expire after 24 hours (86400000 ms)

---

## Error Handling

### Standard Error Response

```json
{
  "timestamp": "2024-01-15T10:30:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/v1/categories"
}
```

### HTTP Status Codes

| Status | Description |
|--------|-------------|
| 200 | Success |
| 201 | Created |
| 204 | No Content (successful delete) |
| 400 | Bad Request |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |
| 500 | Internal Server Error |

---

## CORS Configuration

The backend is configured to accept requests from:
- Admin Panel: `http://localhost:3000`
- Mobile App: Expo development server

```java
@CrossOrigin(origins = "*")
```

---

## Database Schema

### Tables

1. **admin_user** - Admin users
2. **category** - Product categories
3. **product** - Products
4. **product_translation** - Product translations
5. **banner** - Homepage banners
6. **quote_request** - Quote requests
7. **app_config** - Application configuration
8. **supported_language** - Supported languages
9. **message_template** - Message templates

---

## Testing

### Backend Tests

Located in: `backend/manish-hardware-backend/src/test/`

- Controller Tests: Test REST endpoints
- Service Tests: Test business logic
- Integration Tests: Test end-to-end flows

### Admin Panel Tests

Located in: `admin/src/App.test.tsx`

- Component rendering tests
- Authentication flow tests
- API mock tests

---

## Deployment Checklist

### Backend
- [ ] Update database connection properties
- [ ] Set secure JWT secret key
- [ ] Configure CORS for production domains
- [ ] Enable HTTPS
- [ ] Set up database connection pooling

### Admin Panel
- [ ] Update API_BASE_URL for production
- [ ] Build production bundle: `npm run build`
- [ ] Configure web server (nginx/Apache)

### Mobile App
- [ ] Update API_BASE_URL for production
- [ ] Build APK/IPA: `expo build:android` / `expo build:ios`
- [ ] Configure app signing

---

## Monitoring

### Health Check Endpoints

- Backend: `GET /actuator/health` (if Spring Actuator is enabled)
- Database: Monitor connection pool

### Logging

- Backend: `logging.level.com.manish.hardware=DEBUG`
- Admin: Browser console
- Mobile: Expo development tools

---

## Summary

This documentation covers all API endpoints and their connections between:

1. **Backend (Spring Boot)** - REST API server on port 8080
2. **Admin Panel (React)** - Web admin interface on port 3000
3. **Mobile App (React Native/Expo)** - Mobile application

All three components are fully integrated and communicate via REST APIs with JWT authentication for protected endpoints.
# 🏗️ Hardware App - Manish Hardware Mobile Application

<div align="center">

![React Native](https://img.shields.io/badge/React%20Native-0.81.5-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Runtime-336791.svg)
![Expo](https://img.shields.io/badge/Expo-~54.0.33-black.svg)
![Redux Toolkit](https://img.shields.io/badge/Redux%20Toolkit-2.11-purple.svg)
![TypeScript](https://img.shields.io/badge/TypeScript-5.9-3178C6.svg)

**A professional mobile e-commerce application for hardware and construction materials business in Nepal**

[📱 Download APK](#) | [🌐 Live Demo](#) | [📖 Documentation](#)

</div>

---

## 📋 Table of Contents
- [🎯 Overview](#-overview)
- [✨ Features](#-features)
- [🏗️ Architecture](#️-architecture)
- [🛠️ Tech Stack](#️-tech-stack)
- [📁 Project Structure](#-project-structure)
- [🚀 Getting Started](#-getting-started)
- [📱 Screenshots](#-screenshots)
- [📊 Database Setup](#-database-setup)
- [🌍 Localization](#-localization)
- [🔐 Security Architecture](#-security-architecture)
- [🤝 Business Rules](#-business-rules)
- [📈 Roadmap](#-roadmap)
- [👥 Contributing](#-contributing)
- [📄 License](#-license)

---

## 🎯 Overview

**Hardware App** is a comprehensive mobile e-commerce application designed specifically for Manish Hardware, a leading hardware and construction materials supplier in Nepal. The app serves as a digital catalog, quote management system, and customer portal for individual home builders, contractors, engineers, and construction companies.

### 🎯 **Key Objectives**
- ✅ **Digital Product Catalog**: Browse construction materials by category
- ✅ **Multi-Item Quote Cart**: Add multiple products and submit bulk quote requests
- ✅ **Customer Authentication**: Secure login/signup with JWT-based authentication
- ✅ **Order History Tracking**: View past quote requests with live status updates
- ✅ **Full-Text Product Search**: Search by product name, brand, or category
- ✅ **WhatsApp & Phone Integration**: Direct ordering via WhatsApp and phone call
- ✅ **Bilingual Support**: English and Nepali (नेपाली) languages
- ✅ **Payment After Delivery**: Cash-on-delivery business model

---

## ✨ Features

### 📱 **Mobile Application**

#### Navigation & Structure
- **🏠 Home Screen**: Dynamic banners, quick action buttons (Call/WhatsApp), category grid, language toggle
- **🔍 Search Screen**: Debounced full-text search with category quick-browse chips and "Add to Cart" from results
- **🛒 Cart Tab**: Multi-item quote cart with item count badge on tab bar
- **👤 Profile Tab**: Customer login/signup portal and authenticated quote history dashboard

#### Core Features
- **📂 Category Browsing**: Paginated product listings organized by category
- **📋 Product Details**: Technical specifications, usage info, and "Add to Quote Cart" action
- **💬 Quote Requests**: Single-item quote via WhatsApp or bulk multi-item cart checkout
- **🛒 Bulk Quote Submission**: Add multiple products to cart, submit all at once with a single form
- **📜 Quote History**: View all past quote requests with color-coded status (Pending / Contacted / Completed)
- **🔐 Customer Auth**: Secure registration and login using phone number and password
- **📞 Contact & Location**: Business information with map integration
- **ℹ️ About Us**: Company history and trust indicators

#### UX Polish
- **🔴 Cart Badge**: Live item count displayed on the Cart tab icon
- **⚡ Auto-Fill Checkout**: Logged-in users get their name, phone, and location auto-filled
- **🔄 Pull-to-Refresh**: Quote history supports pull-to-refresh
- **📊 Status Icons**: Quote status badges include contextual icons (⏰ Pending, 📞 Contacted, ✅ Completed)

### 🖥️ **Admin Panel**
- **📊 Dashboard**: Quote statistics and recent activity
- **📦 Product Management**: Full CRUD for products with multi-language translations
- **📂 Category Management**: Create and organize product categories
- **📋 Quote Management**: View, filter, and update quote request statuses
- **🎨 Banner Management**: Manage promotional banners
- **⚙️ Configuration**: Business settings (phone, address, hours)
- **🌐 Language Management**: Add/manage supported languages and translations

### 🔐 **Security Architecture**
- **Separate User Tables**: `AdminUser` for dashboard access, `Customer` for mobile app users
- **Role-Based JWT Tokens**: `ROLE_ADMIN` and `ROLE_CUSTOMER` with separate authentication endpoints
- **Phone-Verified History**: Customers can only view their own quote history (server-side JWT verification)
- **Admin Isolation**: Mobile app users can never access admin endpoints

### 🌐 **Multi-Language Support**
- 🇺🇸 **English**: Complete English localization
- 🇳🇵 **Nepali**: Native Nepali (नेपाली) support
- 🔄 **Language Switcher**: In-app language toggle
- 📝 **Backend-Driven**: All text managed via API

---

## 🏗️ Architecture

```
┌──────────────────────┐    ┌──────────────────────┐    ┌──────────────────────┐
│     Mobile App       │    │       Backend         │    │    Admin Panel       │
│   (React Native)     │◄──►│    (Spring Boot)      │◄──►│      (React)         │
│                      │    │                       │    │                      │
│ • Bottom Tab Nav     │    │ • REST APIs           │    │ • Dashboard          │
│ • Home / Search      │    │ • Admin JWT Auth      │    │ • CRUD Operations    │
│ • Cart / Profile     │    │ • Customer JWT Auth   │    │ • Content Management │
│ • Redux State Mgmt   │    │ • PostgreSQL DB       │    │ • Quote Management   │
│ • Auth + History     │    │ • Role-Based Security │    │                      │
└──────────────────────┘    └──────────────────────┘    └──────────────────────┘
```

### 📊 **System Components**
- **Frontend**: React Native 0.81.5 with Expo 54
- **Backend**: Spring Boot 3.2.0 with JPA/Hibernate
- **Database**: PostgreSQL with optimized indexing
- **Admin**: React 18 with Material-UI
- **State Management**: Redux Toolkit 2.11
- **Authentication**: JWT with separate Admin/Customer flows

---

## 🛠️ Tech Stack

### 📱 **Mobile Application**
```json
{
  "framework": "React Native 0.81.5",
  "platform": "Expo ~54.0.33",
  "language": "TypeScript 5.9",
  "state": "Redux Toolkit 2.11",
  "navigation": "React Navigation 7 (Stack + Bottom Tabs)",
  "networking": "Axios 1.13",
  "localization": "i18next 25",
  "icons": "react-native-vector-icons (Ionicons)",
  "storage": "AsyncStorage + MMKV"
}
```

### ⚙️ **Backend API**
```json
{
  "framework": "Spring Boot 3.2.0",
  "language": "Java 17",
  "database": "PostgreSQL",
  "orm": "JPA/Hibernate",
  "security": "Spring Security + JWT (jjwt 0.12.5)",
  "validation": "Bean Validation",
  "documentation": "OpenAPI/Swagger (springdoc 2.3)",
  "utilities": "Lombok"
}
```

### 🖥️ **Admin Panel**
```json
{
  "framework": "React 18",
  "ui": "Material-UI",
  "routing": "React Router",
  "forms": "React Hook Form",
  "http": "Axios",
  "build": "Create React App"
}
```

---

## 📁 Project Structure

```
manish_Hardware/
├── mobile/                          # React Native Mobile App
│   ├── App.tsx                      # Root component (Redux Provider)
│   ├── src/
│   │   ├── app/                     # App config, i18n, theme
│   │   │   └── i18n/locales/        # en.json, ne.json translations
│   │   ├── features/                # Feature modules
│   │   │   ├── home/                # Home screen with banners & categories
│   │   │   ├── search/              # Debounced product search + category chips
│   │   │   ├── cart/                # Cart screen + Checkout bulk submission
│   │   │   ├── profile/             # Login, Register, Quote History
│   │   │   ├── categories/          # Category listing screen
│   │   │   ├── products/            # Product detail + Add to Cart
│   │   │   ├── quotes/              # Single-item quote request form
│   │   │   ├── contact/             # Business contact & map
│   │   │   └── about/               # About us screen
│   │   ├── navigation/              # Stack + Bottom Tab navigators
│   │   ├── services/api/            # Axios API client + service modules
│   │   ├── store/                   # Redux store configuration
│   │   │   └── slices/              # appSlice, authSlice, cartSlice, etc.
│   │   └── types/                   # TypeScript type definitions
│   └── package.json
│
├── backend/manish-hardware-backend/ # Spring Boot Backend
│   └── src/main/java/com/manish/hardware/
│       ├── config/                  # SecurityConfig, CORS
│       ├── controller/              # REST API controllers
│       │   ├── AuthController        # Admin authentication
│       │   ├── CustomerAuthController # Customer login/register
│       │   ├── ProductController      # Product CRUD
│       │   ├── CategoryController     # Category CRUD
│       │   ├── QuoteRequestController # Quote management + phone verification
│       │   ├── BannerController       # Banner management
│       │   └── ...                    # Config, Language, Template controllers
│       ├── model/                   # JPA entities
│       │   ├── AdminUser             # Admin users (dashboard)
│       │   ├── Customer              # Mobile app customers (separate table)
│       │   ├── Product               # Products with translations
│       │   ├── Category              # Product categories
│       │   ├── QuoteRequest          # Quote requests with status tracking
│       │   └── ...                   # Banner, AppConfig, etc.
│       ├── repository/              # Spring Data JPA repositories
│       ├── security/                # JWT provider, filters, UserDetailsService
│       ├── service/                 # Business logic services
│       └── dto/                     # Data Transfer Objects
│
├── admin/                           # React Admin Panel
├── docs/                            # Documentation
└── README.md
```

---

## 🚀 Getting Started

### 📋 **Prerequisites**
- Node.js 18+
- Java 17+
- PostgreSQL (or H2 for testing)
- Android Studio / Expo Go app (for Android development)
- Xcode (for iOS development, macOS only)

### 🏃‍♂️ **Quick Start**

1. **Clone the repository**
   ```bash
   git clone https://github.com/shivam499-pro/Hardware-App.git
   cd Hardware-App
   ```

2. **Setup Backend**
   ```bash
   cd backend/manish-hardware-backend
   mvn clean install
   mvn spring-boot:run
   ```

3. **Setup Mobile App**
   ```bash
   cd mobile
   npm install
   npx expo start
   ```

4. **Setup Admin Panel**
   ```bash
   cd admin
   npm install
   npm start
   ```

---

## 📱 Screenshots

<div align="center">

### 🏠 Home Screen
<img src="screenshots/home.png" width="300" alt="Home Screen">

### 🔍 Search
<img src="screenshots/search.png" width="300" alt="Search Screen">

### 🛒 Quote Cart
<img src="screenshots/cart.png" width="300" alt="Cart Screen">

### 👤 Profile & Quote History
<img src="screenshots/profile.png" width="300" alt="Profile Screen">

### 📋 Product Details
<img src="screenshots/product.png" width="300" alt="Product Details">

</div>

---

## 📊 Database Setup

### 🗄️ **PostgreSQL Configuration**
```sql
-- Create database
CREATE DATABASE manish_hardware;

-- The schema is auto-generated by JPA/Hibernate
-- Tables created automatically: admin_users, customers, products, categories,
-- quote_requests, banners, app_configs, supported_languages, etc.
```

### 🔧 **Application Properties**
```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/manish_hardware
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update

# JWT Configuration
app.jwt.secret=your-secret-key-here
app.jwt.expiration=86400000
```

---

## 🌍 Localization

### 🗣️ **Supported Languages**
- **English (en)**: Default language
- **Nepali (ne)**: Native Nepali support

### 📝 **Translation Files**
```
mobile/src/app/i18n/locales/
├── en.json    # English translations
└── ne.json    # Nepali translations
```

### 🔄 **Language Switching**
- In-app language toggle on Home Screen
- Persistent language selection
- Backend-driven product translations

---

## 🔐 Security Architecture

### 🏛️ **Dual Authentication System**

| Feature | Admin Users | Customer Users |
|---------|-------------|----------------|
| **Database Table** | `admin_users` | `customers` |
| **Login Identifier** | Username | Phone Number |
| **API Endpoint** | `/api/v1/auth/**` | `/api/v1/customer-auth/**` |
| **JWT Role** | `ROLE_ADMIN` | `ROLE_CUSTOMER` |
| **Access** | Full dashboard + API | Own quote history only |

### 🛡️ **Quote History Protection**
When a customer requests their quote history via `GET /api/v1/quotes/phone/{phone}`, the server:
1. Extracts the JWT token from the request
2. Verifies the caller's phone matches the `{phone}` parameter
3. Returns `403 Forbidden` if someone tries to view another customer's data
4. Admins bypass this check and can view all quotes

---

## 🤝 Business Rules

### 💰 **Payment Policy**
- ❌ No online payments accepted
- ✅ Payment collected after delivery
- ✅ Cash-on-delivery model
- ✅ Prices confirmed via contact (dynamic pricing)

### 📞 **Communication Channels**
- 📱 **WhatsApp**: Primary ordering channel
- 📞 **Phone Call**: Direct contact for inquiries
- 🛒 **In-App Cart**: Bulk quote requests submitted to backend

### 🎯 **Target Users**
- 👷 Individual home builders
- 🏗️ Contractors and construction companies
- 👨‍🔬 Engineers and architects
- 👨‍🏭 Small construction businesses
- 🏘️ Local customers within delivery radius

---

## 📈 Roadmap

### ✅ **Phase 1 — Navigation & Structure** (Completed)
- Bottom Tab Navigator (Home, Search, Cart, Profile)
- Stack-based screen navigation for detail screens

### ✅ **Phase 2 — Quote Cart System** (Completed)
- Redux-powered multi-item quote cart
- Bulk checkout with "Loop & Submit" to backend
- Cart item management (add, remove, update quantity)

### ✅ **Phase 3 — Customer Authentication** (Completed)
- Dedicated `Customer` database table (isolated from Admin)
- JWT-based login/signup via phone number
- Authenticated quote history with server-side phone verification
- Auto-fill checkout form for logged-in users

### ✅ **Phase 4 — Search & Polish** (Completed)
- Full-text product search with 400ms debounce
- Category quick-browse chips
- Live cart badge on tab icon
- Enhanced quote history cards with status icons

### 🔮 **Future Enhancements**
- 🔒 **Login Persistence**: Save auth token to device storage
- 📞 **Real Call/WhatsApp**: Native phone dialing and WhatsApp deep links
- 🎨 **Splash Screen**: Branded loading animation
- ⏳ **Skeleton Loading**: Shimmer placeholders instead of spinners
- 📴 **Offline Mode**: Friendly "No Internet" handling
- 🌙 **Dark Mode**: System-aware dark theme toggle
- 🔔 **Push Notifications**: Quote status change alerts
- 📍 **GPS Tracking**: Delivery location optimization
- 📊 **Analytics**: Sales and customer insights
- 💳 **Payment Integration**: Future payment options

---

## 👥 Contributing

We welcome contributions! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### 📝 **Development Guidelines**
- Follow existing code style
- Write meaningful commit messages
- Run `npx tsc --noEmit` before committing (mobile)
- Run `mvn clean compile` before committing (backend)
- Test on multiple devices
- Ensure localization coverage

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- **Manish Hardware** for the business requirements
- **React Native Community** for excellent documentation
- **Spring Boot Team** for robust framework
- **Expo Team** for seamless development experience

---

<div align="center">

**Made with ❤️ for Manish Hardware, Nepal**

[⬆️ Back to Top](#-hardware-app---manish-hardware-mobile-application)

</div>
# 🏗️ Hardware App - Manish Hardware Mobile Application

<div align="center">

![Hardware App](https://img.shields.io/badge/React%20Native-0.73.6-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)
![MySQL](https://img.shields.io/badge/MySQL-8.0-orange.svg)
![Expo](https://img.shields.io/badge/Expo-~50.0.0-black.svg)

**A professional mobile application for hardware and construction materials business in Nepal**

[📱 Download APK](#) | [🌐 Live Demo](#) | [📖 Documentation](#)

</div>

---

## 📋 Table of Contents
- [🎯 Overview](#-overview)
- [✨ Features](#-features)
- [🏗️ Architecture](#️-architecture)
- [🛠️ Tech Stack](#️-tech-stack)
- [🚀 Getting Started](#-getting-started)
- [📱 Screenshots](#-screenshots)
- [🔧 Installation](#-installation)
- [📊 Database Setup](#-database-setup)
- [🌍 Localization](#-localization)
- [🤝 Business Rules](#-business-rules)
- [📈 Roadmap](#-roadmap)
- [👥 Contributing](#-contributing)
- [📄 License](#-license)

---

## 🎯 Overview

**Hardware App** is a comprehensive mobile application designed specifically for Manish Hardware, a leading hardware and construction materials supplier in Nepal. The app serves as a digital catalog and ordering platform for individual home builders, contractors, engineers, and construction companies.

### 🎯 **Key Objectives**
- ✅ **Digital Product Catalog**: Browse construction materials by category
- ✅ **WhatsApp Integration**: Direct ordering via WhatsApp messaging
- ✅ **Phone Call Integration**: One-tap calling for inquiries
- ✅ **Bilingual Support**: English and Nepali (नेपाली) languages
- ✅ **Offline-First**: Works reliably on low-end Android devices
- ✅ **Payment After Delivery**: Cash-on-delivery business model

---

## ✨ Features

### 📱 **Mobile Application**
- **🏠 Home Screen**: Dynamic banners, quick action buttons, product categories
- **📂 Category Browsing**: Paginated product listings with search
- **📋 Product Details**: Technical specifications, usage information
- **💬 Quote Requests**: Form validation with WhatsApp integration
- **📞 Contact & Location**: Business information with map integration
- **ℹ️ About Us**: Company history and trust indicators

### 🔐 **Business Compliance**
- ❌ **No Online Payments**: Only WhatsApp/call ordering
- ✅ **Payment After Delivery**: Traditional cash-on-delivery
- ✅ **Dynamic Pricing**: Prices not hardcoded, confirmed via contact
- ✅ **Configuration-Driven**: All content managed via backend

### 🌐 **Multi-Language Support**
- 🇺🇸 **English**: Complete English localization
- 🇳🇵 **Nepali**: Native Nepali (नेपाली) support
- 🔄 **Language Switcher**: In-app language toggle
- 📝 **Backend-Driven**: All text managed via API

---

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Mobile App    │    │     Backend     │    │   Admin Panel   │
│  (React Native) │◄──►│  (Spring Boot)  │◄──►│    (React)      │
│                 │    │                 │    │                 │
│ • Home Screen   │    │ • REST APIs     │    │ • Dashboard     │
│ • Categories    │    │ • JWT Auth      │    │ • CRUD Ops      │
│ • Products      │    │ • MySQL DB      │    │ • Content Mgmt  │
│ • Quotes        │    │ • Localization  │    │                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

### 📊 **System Components**
- **Frontend**: React Native with Expo
- **Backend**: Spring Boot with JPA
- **Database**: MySQL with optimized indexing
- **Admin**: React with Material-UI
- **Deployment**: Docker + Cloud hosting

---

## 🛠️ Tech Stack

### 📱 **Mobile Application**
```json
{
  "framework": "React Native 0.73.6",
  "platform": "Expo ~50.0.0",
  "language": "TypeScript",
  "state": "Redux Toolkit",
  "navigation": "React Navigation",
  "networking": "Axios",
  "localization": "i18next",
  "storage": "AsyncStorage + MMKV"
}
```

### ⚙️ **Backend API**
```json
{
  "framework": "Spring Boot 3.2.0",
  "language": "Java 17",
  "database": "MySQL 8.0",
  "orm": "JPA/Hibernate",
  "security": "Spring Security + JWT",
  "validation": "Bean Validation",
  "documentation": "OpenAPI/Swagger"
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

## 🚀 Getting Started

### 📋 **Prerequisites**
- Node.js 18+
- Java 17+
- MySQL 8.0+
- Android Studio (for Android development)
- Xcode (for iOS development, macOS only)

### 🏃‍♂️ **Quick Start**

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/hardware-app.git
   cd hardware-app
   ```

2. **Setup Mobile App**
   ```bash
   cd mobile
   npm install
   npm start
   ```

3. **Setup Backend**
   ```bash
   cd ../backend/manish-hardware-backend
   mvn clean install
   mvn spring-boot:run
   ```

4. **Setup Database**
   ```sql
   mysql -u root -p < backend/schema.sql
   ```

5. **Setup Admin Panel**
   ```bash
   cd ../admin
   npm install
   npm start
   ```

---

## 📱 Screenshots

<div align="center">

### 🏠 Home Screen
<img src="screenshots/home.png" width="300" alt="Home Screen">

### 📂 Categories
<img src="screenshots/categories.png" width="300" alt="Categories">

### 📋 Product Details
<img src="screenshots/product.png" width="300" alt="Product Details">

### 💬 Quote Request
<img src="screenshots/quote.png" width="300" alt="Quote Request">

</div>

---

## 🔧 Installation

### 📱 **Mobile App Setup**
```bash
# Install dependencies
npm install

# Install Expo CLI globally
npm install -g @expo/cli

# Start development server
npm start

# Run on Android
npm run android

# Run on iOS (macOS only)
npm run ios
```

### ⚙️ **Backend Setup**
```bash
# Install dependencies
mvn clean install

# Run the application
mvn spring-boot:run

# Build for production
mvn clean package
```

### 🖥️ **Admin Panel Setup**
```bash
# Install dependencies
npm install

# Start development server
npm start

# Build for production
npm run build
```

---

## 📊 Database Setup

### 🗄️ **MySQL Configuration**
```sql
-- Create database
CREATE DATABASE manish_hardware;

-- Run schema
SOURCE backend/schema.sql;

-- Sample data insertion
INSERT INTO categories (name, description) VALUES
('Cement', 'High quality cement for construction'),
('Steel', 'TMT bars and steel products');
```

### 🔧 **Application Properties**
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/manish_hardware
spring.datasource.username=your_username
spring.datasource.password=your_password

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
- In-app language toggle
- Persistent language selection
- Backend-driven translations

---

## 🤝 Business Rules

### 💰 **Payment Policy**
- ❌ No online payments accepted
- ✅ Payment collected after delivery
- ✅ Cash-on-delivery model
- ✅ Prices confirmed via contact

### 📞 **Communication Channels**
- 📱 **WhatsApp**: Primary ordering channel
- 📞 **Phone Call**: Direct contact for inquiries
- 📧 **No Email**: Traditional communication preferred

### 🎯 **Target Users**
- 👷 Individual home builders
- 🏗️ Contractors and construction companies
- 👨‍🔬 Engineers and architects
- 👨‍🏭 Small construction businesses
- 🏘️ Local customers within delivery radius

---

## 📈 Roadmap

### 🚀 **Version 1.0** (Current)
- ✅ Mobile app with core features
- ✅ Backend API with authentication
- ✅ Admin panel for content management
- ✅ Bilingual localization
- ✅ WhatsApp integration

### 🔮 **Future Enhancements**
- 🔄 **Inventory Sync**: Real-time stock management
- 📍 **GPS Tracking**: Delivery location optimization
- 📊 **Analytics**: Sales and customer insights
- 🔔 **Push Notifications**: Order updates
- 📱 **Multi-branch**: Support for multiple locations
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
- Update documentation
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
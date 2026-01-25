# Localization Strategy

## Overview

The app supports English and Nepali (नेपाली) with backend-driven localization. No hardcoded strings in the mobile app.

## Backend Localization

- **Supported Languages Table**: Stores language codes and names
- **Product Translations Table**: Localized names and descriptions for products
- **Message Templates Table**: Localized WhatsApp message templates
- **App Config**: Localized contact info, trust messages
- **API Responses**: Include language-specific data

## Mobile App Localization

- **Library**: react-i18next
- **Language Detection**: Device language or user selection
- **Storage**: Selected language persisted in AsyncStorage
- **Fallback**: English as default
- **Dynamic Loading**: Fetch localization strings from backend on app start

## Language Switcher

- Available in app settings/home screen
- Immediate language change without restart
- Persist selection across sessions

## Content Management

- Admin panel allows editing translations
- Bulk import/export for translations
- Validation for required fields in all languages

## WhatsApp Messages

- Templates stored in database with placeholders
- Dynamic replacement: {product}, {quantity}, {name}, {location}
- Language-specific templates

## Implementation Details

- **Mobile**: Use `t()` function for all strings
- **Backend**: Return localized data based on request header or param
- **Admin**: Rich text editor for translations
- **Testing**: Ensure all screens work in both languages

## Example Translation Keys

- home.welcome: "Welcome to Manish Hardware"
- product.price_note: "Prices vary daily. Confirm via call or WhatsApp."
- button.call_now: "Call Now"

## Maintenance

- Regular updates via admin panel
- Community feedback for improvements
- Professional translation services for accuracy

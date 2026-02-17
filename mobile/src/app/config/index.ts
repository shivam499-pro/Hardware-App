/**
 * App Configuration
 * Central configuration for the mobile app
 */

// API Configuration
export const API_CONFIG = {
  BASE_URL: __DEV__ 
    ? 'http://localhost:8080/api/v1' 
    : 'https://api.manishhardware.com/api/v1',
  TIMEOUT: 15000,
  RETRY_COUNT: 3,
};

// App Information
export const APP_INFO = {
  NAME: 'Manish Hardware',
  VERSION: '1.0.0',
  BUILD_NUMBER: '1',
};

// Feature Flags
export const FEATURES = {
  ENABLE_OFFLINE_MODE: true,
  ENABLE_PUSH_NOTIFICATIONS: false,
  ENABLE_ANALYTICS: false,
  ENABLE_WHATSAPP_INTEGRATION: true,
};

// Storage Keys
export const STORAGE_KEYS = {
  AUTH_TOKEN: 'auth_token',
  USER_LANGUAGE: 'user-language',
  USER_PREFERENCES: 'user_preferences',
  CACHED_CATEGORIES: 'cached_categories',
  CACHED_PRODUCTS: 'cached_products',
  CACHED_CONFIG: 'cached_config',
  LAST_SYNC: 'last_sync',
};

// Default Values
export const DEFAULTS = {
  LANGUAGE: 'en',
  CURRENCY: 'NPR',
  COUNTRY: 'NP',
  PHONE_NUMBER: '+977-1234567890',
  WHATSAPP_NUMBER: '+977-1234567890',
  ADDRESS: 'Kathmandu, Nepal',
  BUSINESS_HOURS: 'Sunday - Friday: 9:00 AM - 6:00 PM, Saturday: 9:00 AM - 4:00 PM',
};

// Pagination
export const PAGINATION = {
  DEFAULT_PAGE_SIZE: 20,
  MAX_PAGE_SIZE: 100,
};

// Animation Durations
export const ANIMATION = {
  SHORT: 200,
  MEDIUM: 400,
  LONG: 600,
};

// Colors (for reference - use theme for actual styling)
export const COLORS = {
  PRIMARY: '#2c3e50',
  SECONDARY: '#e74c3c',
  SUCCESS: '#27ae60',
  WARNING: '#f39c12',
  ERROR: '#e74c3c',
  WHATSAPP: '#25D366',
  BACKGROUND: '#f5f5f5',
  CARD: '#ffffff',
  TEXT_PRIMARY: '#2c3e50',
  TEXT_SECONDARY: '#7f8c8d',
};

export default {
  API_CONFIG,
  APP_INFO,
  FEATURES,
  STORAGE_KEYS,
  DEFAULTS,
  PAGINATION,
  ANIMATION,
  COLORS,
};

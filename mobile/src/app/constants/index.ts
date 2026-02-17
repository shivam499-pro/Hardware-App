/**
 * App Constants
 * Central constants for the mobile app
 */

// Screen Names
export const SCREENS = {
  HOME: 'Home',
  CATEGORY: 'Category',
  PRODUCT_DETAIL: 'ProductDetail',
  REQUEST_QUOTE: 'RequestQuote',
  CONTACT: 'Contact',
  ABOUT: 'About',
} as const;

// Quote Status
export const QUOTE_STATUS = {
  PENDING: 'PENDING',
  CONTACTED: 'CONTACTED',
  COMPLETED: 'COMPLETED',
  CANCELLED: 'CANCELLED',
} as const;

// Language Codes
export const LANGUAGE_CODES = {
  ENGLISH: 'en',
  NEPALI: 'ne',
} as const;

// Supported Languages
export const SUPPORTED_LANGUAGES = [
  { code: LANGUAGE_CODES.ENGLISH, name: 'English', nativeName: 'English' },
  { code: LANGUAGE_CODES.NEPALI, name: 'Nepali', nativeName: 'नेपाली' },
];

// Date Formats
export const DATE_FORMATS = {
  DISPLAY: 'DD/MM/YYYY',
  DISPLAY_WITH_TIME: 'DD/MM/YYYY HH:mm',
  API: 'YYYY-MM-DD',
  API_WITH_TIME: 'YYYY-MM-DDTHH:mm:ss',
};

// Validation Patterns
export const VALIDATION = {
  PHONE: /^[+]?[\d\s-]{10,15}$/,
  EMAIL: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
  WHATSAPP: /^[+]?[\d]{10,15}$/,
};

// Error Messages
export const ERROR_MESSAGES = {
  NETWORK_ERROR: 'Unable to connect to server. Please check your internet connection.',
  GENERIC_ERROR: 'Something went wrong. Please try again.',
  VALIDATION_ERROR: 'Please check your input and try again.',
  NOT_FOUND: 'The requested resource was not found.',
  UNAUTHORIZED: 'You are not authorized to perform this action.',
};

// Success Messages
export const SUCCESS_MESSAGES = {
  QUOTE_SUBMITTED: 'Your quote request has been submitted successfully!',
  SAVED: 'Changes saved successfully.',
};

// Placeholder Images
export const PLACEHOLDER_IMAGES = {
  PRODUCT: 'https://via.placeholder.com/150?text=Product',
  CATEGORY: 'https://via.placeholder.com/80?text=Category',
  BANNER: 'https://via.placeholder.com/400x200?text=Banner',
};

// Social Links
export const SOCIAL_LINKS = {
  WHATSAPP: 'whatsapp://send',
  PHONE: 'tel:',
  EMAIL: 'mailto:',
  MAPS: 'https://maps.google.com/?q=',
};

// Sort Options
export const SORT_OPTIONS = {
  NAME_ASC: { label: 'Name (A-Z)', value: 'name_asc' },
  NAME_DESC: { label: 'Name (Z-A)', value: 'name_desc' },
  NEWEST: { label: 'Newest First', value: 'newest' },
  OLDEST: { label: 'Oldest First', value: 'oldest' },
} as const;

// App Routes
export const ROUTES = {
  HOME: 'Home',
  CATEGORY: 'Category',
  PRODUCT_DETAIL: 'ProductDetail',
  REQUEST_QUOTE: 'RequestQuote',
  CONTACT: 'Contact',
  ABOUT: 'About',
};

export default {
  SCREENS,
  QUOTE_STATUS,
  LANGUAGE_CODES,
  SUPPORTED_LANGUAGES,
  DATE_FORMATS,
  VALIDATION,
  ERROR_MESSAGES,
  SUCCESS_MESSAGES,
  PLACEHOLDER_IMAGES,
  SOCIAL_LINKS,
  SORT_OPTIONS,
  ROUTES,
};

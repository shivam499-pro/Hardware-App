/**
 * Home Feature Types
 */

// Category type (re-exported from services)
export interface Category {
  id: number;
  name: string;
  description: string;
  imageUrl: string;
  sortOrder: number;
  isActive: boolean;
  createdAt?: string;
  updatedAt?: string;
}

// Banner type (re-exported from services)
export interface Banner {
  id: number;
  title: string;
  imageUrl: string;
  linkUrl?: string;
  sortOrder: number;
  isActive: boolean;
  createdAt?: string;
  updatedAt?: string;
}

// Business Config type
export interface BusinessConfig {
  phone_number?: string;
  whatsapp_number?: string;
  address?: string;
  business_hours?: string;
  business_name?: string;
  business_email?: string;
  map_latitude?: string;
  map_longitude?: string;
  map_zoom_level?: string;
}

// Supported Language type
export interface SupportedLanguage {
  id: number;
  code: string;
  name: string;
  nativeName?: string;
  isDefault: boolean;
  isActive: boolean;
  createdAt?: string;
  updatedAt?: string;
}

// Home Screen State
export interface HomeScreenState {
  categories: Category[];
  banners: Banner[];
  businessConfig: BusinessConfig;
  languages: SupportedLanguage[];
  loading: boolean;
  error: string | null;
  refreshing: boolean;
}

// Home Screen Actions
export type HomeScreenAction =
  | { type: 'SET_CATEGORIES'; payload: Category[] }
  | { type: 'SET_BANNERS'; payload: Banner[] }
  | { type: 'SET_BUSINESS_CONFIG'; payload: BusinessConfig }
  | { type: 'SET_LANGUAGES'; payload: SupportedLanguage[] }
  | { type: 'SET_LOADING'; payload: boolean }
  | { type: 'SET_ERROR'; payload: string | null }
  | { type: 'SET_REFRESHING'; payload: boolean }
  | { type: 'RESET' };

/**
 * Global Types
 * Re-export all types from feature modules for easy access
 */

// Navigation types
export type { RootStackParamList } from '../navigation';

// API Types
export type { PaginatedResponse, ApiError } from '../services/api/types';

// Feature Types
export type { Category, CategoryWithProducts } from '../features/categories/types';
export type { Product, ProductTranslation, ProductQueryParams } from '../features/products/types';
export type { QuoteRequest, QuoteRequestInput, QuoteStatus } from '../features/quotes/types';
export type { BusinessConfig } from '../features/contact/types';
export type { AboutData } from '../features/about/types';

// Common Types
export interface User {
  id: number;
  username: string;
  email?: string;
  fullName?: string;
  isActive: boolean;
  createdAt?: string;
}

export interface AppState {
  isLoading: boolean;
  error: string | null;
  language: string;
  user: User | null;
}

export interface NavigationParams {
  screen: string;
  params?: Record<string, any>;
}

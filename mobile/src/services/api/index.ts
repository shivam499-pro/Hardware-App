export { default as apiClient, getErrorMessage } from './apiClient';
export * from './types';
export * from './categoryService';
export * from './productService';
export * from './bannerService';
export * from './quoteService';
export * from './configService';
export * from './languageService';
export * from './templateService';
export * from './authService';

// Re-export storage and external services
export * from '../storage';
export * from '../external';

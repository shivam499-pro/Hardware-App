import apiClient from './apiClient';
import { PaginatedResponse } from './types';

export interface ProductTranslation {
  id: number;
  productId: number;
  languageCode: string;
  name: string;
  description: string;
}

export interface Product {
  id: number;
  categoryId: number;
  brand: string;
  imageUrl: string;
  technicalSpecs: string;
  usageInfo: string;
  isActive: boolean;
  translations?: ProductTranslation[];
  createdAt?: string;
  updatedAt?: string;
}

export interface ProductQueryParams {
  page?: number;
  size?: number;
  sortBy?: string;
  sortDir?: 'asc' | 'desc';
}

export const productService = {
  // Get all products with pagination
  getAllProducts: async (params?: ProductQueryParams): Promise<PaginatedResponse<Product>> => {
    const response = await apiClient.get<PaginatedResponse<Product>>('/products', { params });
    return response.data;
  },

  // Get products by category with pagination
  getProductsByCategory: async (
    categoryId: number,
    params?: ProductQueryParams
  ): Promise<PaginatedResponse<Product>> => {
    const response = await apiClient.get<PaginatedResponse<Product>>(
      `/products/category/${categoryId}`,
      { params }
    );
    return response.data;
  },

  // Get product by ID with translations
  getProductById: async (id: number): Promise<Product> => {
    const response = await apiClient.get<Product>(`/products/${id}`);
    return response.data;
  },

  // Get product by ID and language
  getProductByIdAndLanguage: async (id: number, languageCode: string): Promise<Product> => {
    const response = await apiClient.get<Product>(`/products/${id}/lang/${languageCode}`);
    return response.data;
  },

  // Search products
  searchProducts: async (
    query: string,
    categoryId?: number,
    params?: ProductQueryParams
  ): Promise<PaginatedResponse<Product>> => {
    const response = await apiClient.get<PaginatedResponse<Product>>('/products/search', {
      params: {
        q: query,
        categoryId,
        ...params,
      },
    });
    return response.data;
  },

  // Get product translations
  getProductTranslations: async (productId: number): Promise<ProductTranslation[]> => {
    const response = await apiClient.get<ProductTranslation[]>(
      `/products/${productId}/translations`
    );
    return response.data;
  },

  // Admin: Create product
  createProduct: async (product: Partial<Product>): Promise<Product> => {
    const response = await apiClient.post<Product>('/products', product);
    return response.data;
  },

  // Admin: Update product
  updateProduct: async (id: number, product: Partial<Product>): Promise<Product> => {
    const response = await apiClient.put<Product>(`/products/${id}`, product);
    return response.data;
  },

  // Admin: Delete product (soft delete)
  deleteProduct: async (id: number): Promise<void> => {
    await apiClient.delete(`/products/${id}`);
  },

  // Admin: Add translation to product
  addTranslation: async (
    productId: number,
    translation: Partial<ProductTranslation>
  ): Promise<ProductTranslation> => {
    const response = await apiClient.post<ProductTranslation>(
      `/products/${productId}/translations`,
      translation
    );
    return response.data;
  },

  // Admin: Update translation
  updateTranslation: async (
    productId: number,
    languageCode: string,
    translation: Partial<ProductTranslation>
  ): Promise<ProductTranslation> => {
    const response = await apiClient.put<ProductTranslation>(
      `/products/${productId}/translations/${languageCode}`,
      translation
    );
    return response.data;
  },

  // Admin: Delete translation
  deleteTranslation: async (productId: number, languageCode: string): Promise<void> => {
    await apiClient.delete(`/products/${productId}/translations/${languageCode}`);
  },

  // Get translations by language code
  getTranslationsByLanguage: async (languageCode: string): Promise<ProductTranslation[]> => {
    const response = await apiClient.get<ProductTranslation[]>(`/products/translations/language/${languageCode}`);
    return response.data;
  },

  // Check if translation exists
  translationExists: async (productId: number, languageCode: string): Promise<boolean> => {
    const response = await apiClient.get<boolean>(`/products/${productId}/translations/${languageCode}/exists`);
    return response.data;
  },

  // Count products in category
  countInCategory: async (categoryId: number): Promise<number> => {
    const response = await apiClient.get<number>(`/products/count/category/${categoryId}`);
    return response.data;
  },

  // Admin: Hard delete product
  hardDeleteProduct: async (id: number): Promise<void> => {
    await apiClient.delete(`/products/${id}/hard`);
  },
};

import apiClient from './apiClient';

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

export interface CategoryWithProducts extends Category {
  productCount?: number;
}

export const categoryService = {
  // Get all active categories
  getAllCategories: async (): Promise<Category[]> => {
    const response = await apiClient.get<Category[]>('/categories');
    return response.data;
  },

  // Get category by ID
  getCategoryById: async (id: number): Promise<Category> => {
    const response = await apiClient.get<Category>(`/categories/${id}`);
    return response.data;
  },

  // Admin: Create new category
  createCategory: async (category: Partial<Category>): Promise<Category> => {
    const response = await apiClient.post<Category>('/categories', category);
    return response.data;
  },

  // Admin: Update category
  updateCategory: async (id: number, category: Partial<Category>): Promise<Category> => {
    const response = await apiClient.put<Category>(`/categories/${id}`, category);
    return response.data;
  },

  // Admin: Delete category (soft delete)
  deleteCategory: async (id: number): Promise<void> => {
    await apiClient.delete(`/categories/${id}`);
  },

  // Get all active categories ordered by sort order
  getAllCategoriesOrdered: async (): Promise<Category[]> => {
    const response = await apiClient.get<Category[]>('/categories/ordered');
    return response.data;
  },
};

/**
 * Categories Feature Types
 */

// Category type
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

// Category with product count
export interface CategoryWithProducts extends Category {
  productCount?: number;
}

// Product type (simplified for category screen)
export interface CategoryProduct {
  id: number;
  categoryId: number;
  brand: string;
  imageUrl: string;
  technicalSpecs: string;
  usageInfo: string;
  isActive: boolean;
  translations?: Array<{
    id: number;
    productId: number;
    languageCode: string;
    name: string;
    description: string;
  }>;
  createdAt?: string;
  updatedAt?: string;
}

// Category Screen State
export interface CategoryScreenState {
  products: CategoryProduct[];
  loading: boolean;
  error: string | null;
}

// Category Screen Props
export interface CategoryScreenProps {
  route: {
    params: {
      categoryId: number;
      categoryName: string;
    };
  };
  navigation: any;
}

/**
 * Products Feature Types
 */

// Product Translation
export interface ProductTranslation {
  id: number;
  productId: number;
  languageCode: string;
  name: string;
  description: string;
}

// Product type
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

// Product Query Parameters
export interface ProductQueryParams {
  page?: number;
  size?: number;
  sortBy?: string;
  sortDir?: 'asc' | 'desc';
}

// Product Detail Screen State
export interface ProductDetailScreenState {
  product: Product | null;
  loading: boolean;
  error: string | null;
}

// Product Detail Screen Props
export interface ProductDetailScreenProps {
  route: {
    params: {
      productId: number;
    };
  };
  navigation: any;
}

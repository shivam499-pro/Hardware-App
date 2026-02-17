/**
 * Quotes Feature Types
 */

// Quote Status Type
export type QuoteStatus = 'pending' | 'contacted' | 'completed';

// Quote Request - matches backend QuoteRequest model
export interface QuoteRequest {
  id?: number;
  name: string;
  phone: string;
  productId?: number;
  quantity: string;
  location: string;
  languageCode?: string;
  status?: QuoteStatus;
  createdAt?: string;
  updatedAt?: string;
}

// Quote Request Input (for creating new quote)
export interface QuoteRequestInput {
  name: string;
  phone: string;
  productId?: number;
  quantity: string;
  location: string;
  languageCode?: string;
}

// Quote Screen Props
export interface QuoteScreenProps {
  route: {
    params: {
      productId?: number;
      productName?: string;
    };
  };
  navigation: any;
}

// Quote Screen State
export interface QuoteScreenState {
  formData: QuoteRequestInput;
  loading: boolean;
  error: string | null;
  success: boolean;
}

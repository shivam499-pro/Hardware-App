import apiClient from './apiClient';
import { PaginatedResponse } from './types';

export type QuoteStatus = 'pending' | 'contacted' | 'completed';

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

export interface QuoteStatistics {
  total: number;
  pending: number;
  contacted: number;
  completed: number;
}

export const quoteService = {
  // Submit a new quote request (public)
  submitQuoteRequest: async (quote: QuoteRequest): Promise<QuoteRequest> => {
    const response = await apiClient.post<QuoteRequest>('/quotes', quote);
    return response.data;
  },

  // Admin: Get all quote requests with pagination
  getAllQuoteRequests: async (params?: {
    page?: number;
    size?: number;
    sortBy?: string;
    sortDir?: string;
  }): Promise<PaginatedResponse<QuoteRequest>> => {
    const response = await apiClient.get<PaginatedResponse<QuoteRequest>>('/quotes', { params });
    return response.data;
  },

  // Admin: Get recent quote requests
  getRecentQuoteRequests: async (): Promise<QuoteRequest[]> => {
    const response = await apiClient.get<QuoteRequest[]>('/quotes/recent');
    return response.data;
  },

  // Admin: Get quote request by ID
  getQuoteRequestById: async (id: number): Promise<QuoteRequest> => {
    const response = await apiClient.get<QuoteRequest>(`/quotes/${id}`);
    return response.data;
  },

  // Admin: Get quote requests by status
  getQuoteRequestsByStatus: async (
    status: QuoteStatus,
    params?: { page?: number; size?: number }
  ): Promise<PaginatedResponse<QuoteRequest>> => {
    const response = await apiClient.get<PaginatedResponse<QuoteRequest>>(
      `/quotes/status/${status}`,
      { params }
    );
    return response.data;
  },

  // Admin: Search quote requests
  searchQuoteRequests: async (
    query: string,
    params?: { page?: number; size?: number }
  ): Promise<PaginatedResponse<QuoteRequest>> => {
    const response = await apiClient.get<PaginatedResponse<QuoteRequest>>('/quotes/search', {
      params: { q: query, ...params },
    });
    return response.data;
  },

  // Admin: Get quote statistics
  getQuoteStatistics: async (): Promise<QuoteStatistics> => {
    const response = await apiClient.get<QuoteStatistics>('/quotes/statistics');
    return response.data;
  },

  // Admin: Update quote request
  updateQuoteRequest: async (id: number, quote: Partial<QuoteRequest>): Promise<QuoteRequest> => {
    const response = await apiClient.put<QuoteRequest>(`/quotes/${id}`, quote);
    return response.data;
  },

  // Admin: Update quote status
  updateQuoteStatus: async (id: number, status: QuoteStatus): Promise<QuoteRequest> => {
    const response = await apiClient.patch<QuoteRequest>(`/quotes/${id}/status`, null, {
      params: { status },
    });
    return response.data;
  },

  // Admin: Mark as contacted
  markAsContacted: async (id: number): Promise<QuoteRequest> => {
    const response = await apiClient.patch<QuoteRequest>(`/quotes/${id}/contacted`);
    return response.data;
  },

  // Admin: Mark as completed
  markAsCompleted: async (id: number): Promise<QuoteRequest> => {
    const response = await apiClient.patch<QuoteRequest>(`/quotes/${id}/completed`);
    return response.data;
  },

  // Admin: Delete quote request
  deleteQuoteRequest: async (id: number): Promise<void> => {
    await apiClient.delete(`/quotes/${id}`);
  },

  // Admin: Get quote requests by language code
  getQuoteRequestsByLanguage: async (languageCode: string): Promise<QuoteRequest[]> => {
    const response = await apiClient.get<QuoteRequest[]>(`/quotes/language/${languageCode}`);
    return response.data;
  },

  // Admin: Get quote requests created after a date
  getQuoteRequestsAfterDate: async (date: string): Promise<QuoteRequest[]> => {
    const response = await apiClient.get<QuoteRequest[]>(`/quotes/after/${date}`);
    return response.data;
  },

  // Admin: Get quote requests by multiple statuses
  getQuoteRequestsByStatuses: async (statuses: QuoteStatus[]): Promise<QuoteRequest[]> => {
    const response = await apiClient.post<QuoteRequest[]>('/quotes/statuses', statuses);
    return response.data;
  },
};

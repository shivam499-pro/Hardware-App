import apiClient from './apiClient';

export interface SupportedLanguage {
  id: number;
  code: string;
  name: string;
  isDefault: boolean;
  isActive: boolean;
  createdAt?: string;
  updatedAt?: string;
}

export const languageService = {
  // Get all active languages (public)
  getActiveLanguages: async (): Promise<SupportedLanguage[]> => {
    const response = await apiClient.get<SupportedLanguage[]>('/languages');
    return response.data;
  },

  // Get default language (public)
  getDefaultLanguage: async (): Promise<SupportedLanguage> => {
    const response = await apiClient.get<SupportedLanguage>('/languages/default');
    return response.data;
  },

  // Get default language code (public)
  getDefaultLanguageCode: async (): Promise<string> => {
    const response = await apiClient.get<string>('/languages/default/code');
    return response.data;
  },

  // Check if language is supported (public)
  isLanguageSupported: async (code: string): Promise<boolean> => {
    const response = await apiClient.get<boolean>(`/languages/${code}/supported`);
    return response.data;
  },

  // Get language by code (public)
  getLanguageByCode: async (code: string): Promise<SupportedLanguage> => {
    const response = await apiClient.get<SupportedLanguage>(`/languages/${code}`);
    return response.data;
  },

  // Admin: Get all languages including inactive
  getAllLanguages: async (): Promise<SupportedLanguage[]> => {
    const response = await apiClient.get<SupportedLanguage[]>('/languages/admin/all');
    return response.data;
  },

  // Admin: Create language
  createLanguage: async (language: Partial<SupportedLanguage>): Promise<SupportedLanguage> => {
    const response = await apiClient.post<SupportedLanguage>('/languages/admin', language);
    return response.data;
  },

  // Admin: Update language
  updateLanguage: async (id: number, language: Partial<SupportedLanguage>): Promise<SupportedLanguage> => {
    const response = await apiClient.put<SupportedLanguage>(`/languages/admin/${id}`, language);
    return response.data;
  },

  // Admin: Set default language
  setDefaultLanguage: async (code: string): Promise<void> => {
    await apiClient.put(`/languages/admin/default/${code}`);
  },

  // Admin: Toggle language active status
  toggleLanguageActive: async (id: number): Promise<SupportedLanguage> => {
    const response = await apiClient.patch<SupportedLanguage>(`/languages/admin/${id}/toggle`);
    return response.data;
  },

  // Admin: Delete language
  deleteLanguage: async (id: number): Promise<void> => {
    await apiClient.delete(`/languages/admin/${id}`);
  },

  // Get all active languages unsorted
  getActiveLanguagesUnsorted: async (): Promise<SupportedLanguage[]> => {
    const response = await apiClient.get<SupportedLanguage[]>('/languages/active');
    return response.data;
  },

  // Get default language any
  getDefaultLanguageAny: async (): Promise<SupportedLanguage> => {
    const response = await apiClient.get<SupportedLanguage>('/languages/default/any');
    return response.data;
  },

  // Admin: Set default language direct
  setDefaultLanguageDirect: async (code: string): Promise<void> => {
    await apiClient.put(`/languages/admin/default-direct/${code}`);
  },
};

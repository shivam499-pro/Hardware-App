import apiClient from './apiClient';

export interface MessageTemplate {
  id: number;
  type: string;
  languageCode: string;
  template: string;
  createdAt?: string;
  updatedAt?: string;
}

export const templateService = {
  // Get all templates (public)
  getAllTemplates: async (): Promise<MessageTemplate[]> => {
    const response = await apiClient.get<MessageTemplate[]>('/templates');
    return response.data;
  },

  // Get all template types (public)
  getAllTemplateTypes: async (): Promise<string[]> => {
    const response = await apiClient.get<string[]>('/templates/types');
    return response.data;
  },

  // Get template by ID (public)
  getTemplateById: async (id: number): Promise<MessageTemplate> => {
    const response = await apiClient.get<MessageTemplate>(`/templates/${id}`);
    return response.data;
  },

  // Get template by type and language (public)
  getTemplateByTypeAndLanguage: async (type: string, languageCode: string): Promise<MessageTemplate> => {
    const response = await apiClient.get<MessageTemplate>(`/templates/${type}/${languageCode}`);
    return response.data;
  },

  // Get templates by type (public)
  getTemplatesByType: async (type: string): Promise<MessageTemplate[]> => {
    const response = await apiClient.get<MessageTemplate[]>(`/templates/type/${type}`);
    return response.data;
  },

  // Get templates by language (public)
  getTemplatesByLanguage: async (languageCode: string): Promise<MessageTemplate[]> => {
    const response = await apiClient.get<MessageTemplate[]>(`/templates/language/${languageCode}`);
    return response.data;
  },

  // Get templates as map by language (public)
  getTemplatesAsMapByLanguage: async (languageCode: string): Promise<Record<string, string>> => {
    const response = await apiClient.get<Record<string, string>>(`/templates/map/${languageCode}`);
    return response.data;
  },

  // Render template with variables (public)
  renderTemplate: async (type: string, languageCode: string, variables: Record<string, string>): Promise<string> => {
    const response = await apiClient.post<string>('/templates/render', variables, {
      params: { type, languageCode },
    });
    return response.data;
  },

  // Render WhatsApp quote template (public)
  renderWhatsAppQuoteTemplate: async (
    languageCode: string,
    params: { product: string; quantity: string; name: string; location: string }
  ): Promise<string> => {
    const response = await apiClient.post<string>('/templates/render/whatsapp-quote', params, {
      params: { languageCode },
    });
    return response.data;
  },

  // Get template content by type and language
  getTemplateContent: async (type: string, languageCode: string): Promise<string> => {
    const response = await apiClient.get<string>(`/templates/${type}/${languageCode}/content`);
    return response.data;
  },

  // Admin: Create template
  createTemplate: async (template: Partial<MessageTemplate>): Promise<MessageTemplate> => {
    const response = await apiClient.post<MessageTemplate>('/templates/admin', template);
    return response.data;
  },

  // Admin: Update template by ID
  updateTemplate: async (id: number, template: Partial<MessageTemplate>): Promise<MessageTemplate> => {
    const response = await apiClient.put<MessageTemplate>(`/templates/admin/${id}`, template);
    return response.data;
  },

  // Admin: Delete template
  deleteTemplate: async (id: number): Promise<void> => {
    await apiClient.delete(`/templates/admin/${id}`);
  },
};

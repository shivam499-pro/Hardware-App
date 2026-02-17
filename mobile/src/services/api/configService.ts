import apiClient from './apiClient';

export interface AppConfig {
  id: number;
  keyName: string;
  value: string;
  updatedAt?: string;
}

export interface BusinessConfig {
  phone_number?: string;
  whatsapp_number?: string;
  address?: string;
  business_hours?: string;
  business_name?: string;
  business_email?: string;
  map_latitude?: string;
  map_longitude?: string;
  map_zoom_level?: string;
}

export const configService = {
  // Get all configs as map (public)
  getAllConfigsAsMap: async (): Promise<Record<string, string>> => {
    const response = await apiClient.get<Record<string, string>>('/config');
    return response.data;
  },

  // Get business config (public)
  getBusinessConfig: async (): Promise<BusinessConfig> => {
    const response = await apiClient.get<BusinessConfig>('/config/business');
    return response.data;
  },

  // Get config by key (public)
  getConfigByKey: async (key: string): Promise<AppConfig> => {
    const response = await apiClient.get<AppConfig>(`/config/${key}`);
    return response.data;
  },

  // Get config value by key (public)
  getConfigValue: async (key: string): Promise<string> => {
    const response = await apiClient.get<string>(`/config/${key}/value`);
    return response.data;
  },

  // Get config value by key with default (public)
  getConfigValueWithDefault: async (key: string, defaultValue: string): Promise<string> => {
    const response = await apiClient.get<string>(`/config/${key}/value/${defaultValue}`);
    return response.data;
  },

  // Get multiple configs by keys (public)
  getConfigsByKeys: async (keys: string[]): Promise<Record<string, string>> => {
    const response = await apiClient.post<Record<string, string>>('/config/batch', keys);
    return response.data;
  },

  // Admin: Get all configs as list
  getAllConfigs: async (): Promise<AppConfig[]> => {
    const response = await apiClient.get<AppConfig[]>('/config/admin/all');
    return response.data;
  },

  // Admin: Create config
  createConfig: async (config: Partial<AppConfig>): Promise<AppConfig> => {
    const response = await apiClient.post<AppConfig>('/config/admin', config);
    return response.data;
  },

  // Admin: Create or update config
  saveConfig: async (key: string, value: string): Promise<AppConfig> => {
    const response = await apiClient.put<AppConfig>(`/config/admin/${key}`, { value });
    return response.data;
  },

  // Admin: Update config by ID
  updateConfig: async (id: number, config: Partial<AppConfig>): Promise<AppConfig> => {
    const response = await apiClient.put<AppConfig>(`/config/admin/id/${id}`, config);
    return response.data;
  },

  // Admin: Delete config by ID
  deleteConfig: async (id: number): Promise<void> => {
    await apiClient.delete(`/config/admin/${id}`);
  },

  // Admin: Delete config by key
  deleteConfigByKey: async (key: string): Promise<void> => {
    await apiClient.delete(`/config/admin/key/${key}`);
  },

  // Admin: Batch save configs
  batchSaveConfigs: async (configs: Record<string, string>): Promise<void> => {
    await apiClient.post('/config/admin/batch', configs);
  },
};

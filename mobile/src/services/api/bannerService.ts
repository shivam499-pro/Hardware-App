import apiClient from './apiClient';

export interface Banner {
  id: number;
  title: string;
  imageUrl: string;
  linkUrl: string;
  sortOrder: number;
  isActive: boolean;
  createdAt?: string;
  updatedAt?: string;
}

export const bannerService = {
  // Get all active banners (public)
  getActiveBanners: async (): Promise<Banner[]> => {
    const response = await apiClient.get<Banner[]>('/banners');
    return response.data;
  },

  // Get banner by ID
  getBannerById: async (id: number): Promise<Banner> => {
    const response = await apiClient.get<Banner>(`/banners/${id}`);
    return response.data;
  },

  // Admin: Get all banners including inactive
  getAllBanners: async (): Promise<Banner[]> => {
    const response = await apiClient.get<Banner[]>('/banners/all');
    return response.data;
  },

  // Admin: Create banner
  createBanner: async (banner: Partial<Banner>): Promise<Banner> => {
    const response = await apiClient.post<Banner>('/banners', banner);
    return response.data;
  },

  // Admin: Update banner
  updateBanner: async (id: number, banner: Partial<Banner>): Promise<Banner> => {
    const response = await apiClient.put<Banner>(`/banners/${id}`, banner);
    return response.data;
  },

  // Admin: Delete banner (soft delete)
  deleteBanner: async (id: number): Promise<void> => {
    await apiClient.delete(`/banners/${id}`);
  },

  // Admin: Update sort order
  updateSortOrder: async (id: number, sortOrder: number): Promise<void> => {
    await apiClient.put(`/banners/${id}/sort`, null, {
      params: { sortOrder },
    });
  },

  // Admin: Batch update sort order
  batchUpdateSortOrder: async (bannerIds: number[]): Promise<void> => {
    await apiClient.put('/banners/sort-order', bannerIds);
  },

  // Get all active banners unsorted
  getActiveBannersUnsorted: async (): Promise<Banner[]> => {
    const response = await apiClient.get<Banner[]>('/banners/unsorted');
    return response.data;
  },

  // Get all active banners descending
  getActiveBannersDesc: async (): Promise<Banner[]> => {
    const response = await apiClient.get<Banner[]>('/banners/desc');
    return response.data;
  },
};

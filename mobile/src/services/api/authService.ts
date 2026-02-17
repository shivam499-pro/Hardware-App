import apiClient from './apiClient';

export interface AdminUser {
  id: number;
  username: string;
  email: string;
  fullName: string;
  isActive: boolean;
  role: string;
  createdAt?: string;
  updatedAt?: string;
  lastLogin?: string;
}

export interface LoginCredentials {
  username: string;
  password: string;
}

export interface LoginResponse {
  accessToken: string;
  tokenType: string;
  expiresIn: number;
  username: string;
  fullName: string;
}

export interface RegisterRequest {
  username: string;
  password: string;
  email?: string;
  fullName?: string;
}

export interface ChangePasswordRequest {
  currentPassword: string;
  newPassword: string;
}

export interface UserProfile {
  id: number;
  username: string;
  email: string;
  fullName: string;
  isActive: boolean;
}

export const authService = {
  // Login
  login: async (credentials: LoginCredentials): Promise<LoginResponse> => {
    const response = await apiClient.post<LoginResponse>('/auth/login', credentials);
    return response.data;
  },

  // Register new admin user
  register: async (request: RegisterRequest): Promise<{ message: string; username: string }> => {
    const response = await apiClient.post<{ message: string; username: string }>('/auth/register', request);
    return response.data;
  },

  // Get current user profile
  getCurrentUser: async (): Promise<UserProfile> => {
    const response = await apiClient.get<UserProfile>('/auth/me');
    return response.data;
  },

  // Change password
  changePassword: async (request: ChangePasswordRequest): Promise<{ message: string }> => {
    const response = await apiClient.post<{ message: string }>('/auth/change-password', request);
    return response.data;
  },

  // Update profile
  updateProfile: async (fullName?: string, email?: string): Promise<{ message: string; fullName: string; email: string }> => {
    const response = await apiClient.put<{ message: string; fullName: string; email: string }>('/auth/profile', { fullName, email });
    return response.data;
  },

  // Admin: Get all admin users
  getAllUsers: async (): Promise<AdminUser[]> => {
    const response = await apiClient.get<AdminUser[]>('/auth/users');
    return response.data;
  },

  // Admin: Deactivate user
  deactivateUser: async (id: number): Promise<{ message: string }> => {
    const response = await apiClient.put<{ message: string }>(`/auth/users/${id}/deactivate`);
    return response.data;
  },

  // Admin: Activate user
  activateUser: async (id: number): Promise<{ message: string }> => {
    const response = await apiClient.put<{ message: string }>(`/auth/users/${id}/activate`);
    return response.data;
  },

  // Admin: Reset user password
  resetUserPassword: async (id: number, newPassword: string): Promise<{ message: string }> => {
    const response = await apiClient.put<{ message: string }>(`/auth/users/${id}/reset-password`, { newPassword });
    return response.data;
  },

  // Admin: Get user by email
  getUserByEmail: async (email: string): Promise<Partial<AdminUser>> => {
    const response = await apiClient.get<Partial<AdminUser>>(`/auth/users/by-email/${email}`);
    return response.data;
  },
};

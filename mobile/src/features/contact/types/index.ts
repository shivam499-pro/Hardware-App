/**
 * Contact Feature Types
 */

// Business Config for contact
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

// Alias for backwards compatibility
export type ContactConfig = BusinessConfig;

// Contact Screen State
export interface ContactScreenState {
  config: ContactConfig;
  loading: boolean;
  error: string | null;
}

// Contact Action
export interface ContactAction {
  type: 'CALL' | 'WHATSAPP' | 'EMAIL' | 'MAP';
  value?: string;
}

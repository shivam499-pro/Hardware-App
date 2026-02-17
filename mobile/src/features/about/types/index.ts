/**
 * About Feature Types
 */

// About Data
export interface AboutData {
  businessName: string;
  aboutContent: string;
  services: string[];
  whyChooseUs: string[];
  experience: string;
}

// About Screen State
export interface AboutScreenState {
  aboutData: AboutData;
  loading: boolean;
  error: string | null;
}

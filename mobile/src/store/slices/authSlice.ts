import { createSlice, PayloadAction } from '@reduxjs/toolkit';

export interface AuthState {
  token: string | null;
  phone: string | null;
  fullName: string | null;
  location: string | null;
  isAuthenticated: boolean;
}

const initialState: AuthState = {
  token: null,
  phone: null,
  fullName: null,
  location: null,
  isAuthenticated: false,
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    setAuthCredentials: (
      state,
      action: PayloadAction<{ token: string; phone: string; fullName: string; location?: string }>
    ) => {
      state.token = action.payload.token;
      state.phone = action.payload.phone;
      state.fullName = action.payload.fullName;
      if (action.payload.location) {
        state.location = action.payload.location;
      }
      state.isAuthenticated = true;
    },
    logout: (state) => {
      state.token = null;
      state.phone = null;
      state.fullName = null;
      state.location = null;
      state.isAuthenticated = false;
    },
  },
});

export const { setAuthCredentials, logout } = authSlice.actions;

export default authSlice.reducer;

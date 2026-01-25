import React from 'react';
import { StatusBar } from 'expo-status-bar';
import AppNavigator from './src/navigation';
import './src/app/i18n'; // Initialize i18n

export default function App() {
  return (
    <>
      <AppNavigator />
      <StatusBar style="auto" />
    </>
  );
}

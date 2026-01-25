# Mobile App Folder Structure

## Overview

The mobile app uses React Native with TypeScript, following a feature-based Clean Architecture. This structure promotes modularity, testability, and maintainability.

```text
manish_hardware_mobile/
├── android/                    # Android native code
├── ios/                        # iOS native code
├── src/
│   ├── app/                    # App-level configurations
│   │   ├── config/             # Environment configs (.env)
│   │   ├── constants/          # App constants
│   │   ├── i18n/               # Localization files
│   │   │   ├── locales/        # Language JSON files
│   │   │   └── index.ts
│   │   └── theme/              # Design system
│   ├── assets/                 # Images, fonts, icons
│   ├── components/             # Shared UI components
│   │   ├── common/             # Reusable components
│   │   └── ui/                 # Basic UI elements
│   ├── features/               # Feature-based modules
│   │   ├── home/
│   │   │   ├── components/     # Feature-specific components
│   │   │   ├── screens/        # Screens
│   │   │   ├── hooks/          # Custom hooks
│   │   │   ├── types/          # TypeScript types
│   │   │   └── index.ts
│   │   ├── products/
│   │   │   ├── components/
│   │   │   ├── screens/
│   │   │   ├── hooks/
│   │   │   ├── types/
│   │   │   └── index.ts
│   │   ├── categories/
│   │   ├── quotes/
│   │   ├── contact/
│   │   └── about/
│   ├── navigation/             # Navigation setup
│   ├── services/               # API services, external integrations
│   │   ├── api/                # Axios setup, API clients
│   │   ├── storage/            # AsyncStorage/MMKV wrappers
│   │   └── external/           # WhatsApp, Phone, Maps
│   ├── store/                  # Redux Toolkit store
│   │   ├── slices/             # Redux slices
│   │   └── index.ts
│   ├── utils/                  # Utility functions
│   └── types/                  # Global TypeScript types
├── App.tsx                     # Root component
├── index.js                    # Entry point
├── package.json
├── tsconfig.json
├── babel.config.js
└── metro.config.js
```

## Key Principles

- **Feature-based**: Each feature is self-contained
- **Separation of Concerns**: UI, business logic, data access separated
- **Shared Components**: Reusable across features
- **Type Safety**: Full TypeScript coverage
- **Modular Services**: API, storage, external services isolated

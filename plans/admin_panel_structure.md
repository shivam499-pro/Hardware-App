# Admin Panel Structure

## Overview

The admin panel is a web-based application for managing app content, built with
React and a modern UI library like Material-UI or Ant Design.

## Technology Stack

- **Frontend**: React with TypeScript
- **State Management**: Redux or Context API
- **Routing**: React Router
- **UI Library**: Material-UI
- **HTTP Client**: Axios
- **Authentication**: JWT tokens

## Folder Structure

```text
admin_panel/
├── public/
├── src/
│   ├── components/
│   │   ├── common/          # Shared components
│   │   └── layout/          # Header, sidebar, footer
│   ├── pages/
│   │   ├── dashboard/       # Overview dashboard
│   │   ├── categories/      # Category management
│   │   ├── products/        # Product management
│   │   ├── banners/         # Banner management
│   │   ├── quotes/          # Quote requests
│   │   ├── config/          # App configuration
│   │   ├── translations/    # Localization management
│   │   └── users/           # User management (future)
│   ├── services/            # API services
│   ├── utils/               # Helpers
│   ├── hooks/               # Custom hooks
│   ├── types/               # TypeScript types
│   └── App.tsx
├── package.json
└── tsconfig.json
```

## Key Features

- **Authentication**: Login/logout with JWT
- **Role-based Access**: Admin, Editor roles
- **Dashboard**: Metrics on quotes, products
- **CRUD Operations**: For all entities
- **Image Upload**: For products and banners
- **Bulk Actions**: Import/export data
- **Audit Logs**: Track changes

## Pages

- **Dashboard**: Charts for quote requests, active products
- **Categories**: List, add, edit, delete categories
- **Products**: Manage products with translations
- **Banners**: Upload and manage promotional banners
- **Quote Requests**: View and update status
- **App Config**: Edit contact info, templates
- **Translations**: Edit localization strings

## Security

- Secure API calls with token
- Input validation
- XSS protection
- Session management

## Deployment

- Static hosting (Netlify, Vercel) or server-side (Node.js)
- Environment-based configs

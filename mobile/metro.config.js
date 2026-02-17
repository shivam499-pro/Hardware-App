const { getDefaultConfig } = require('expo/metro-config');

const config = getDefaultConfig(__dirname);

// Add support for absolute imports from src/
config.resolver.sourceExts = [...config.resolver.sourceExts, 'ts', 'tsx'];
config.watchFolders = [__dirname];

module.exports = config;

import React, { useEffect, useState, useCallback } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  FlatList,
  Image,
  Dimensions,
  ActivityIndicator,
  Linking,
  RefreshControl,
} from 'react-native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../../navigation';
import { categoryService, Category } from '../../../services/api/categoryService';
import { bannerService, Banner } from '../../../services/api/bannerService';
import { configService, BusinessConfig } from '../../../services/api/configService';
import { languageService, SupportedLanguage } from '../../../services/api/languageService';
import { useTranslation } from 'react-i18next';

type HomeScreenNavigationProp = StackNavigationProp<RootStackParamList, 'Home'>;

interface Props {
  navigation: HomeScreenNavigationProp;
}

const { width } = Dimensions.get('window');

const HomeScreen: React.FC<Props> = ({ navigation }) => {
  const { t, i18n } = useTranslation();
  const [categories, setCategories] = useState<Category[]>([]);
  const [banners, setBanners] = useState<Banner[]>([]);
  const [businessConfig, setBusinessConfig] = useState<BusinessConfig>({});
  const [languages, setLanguages] = useState<SupportedLanguage[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [refreshing, setRefreshing] = useState(false);

  const fetchData = async () => {
    try {
      setError(null);
      const [categoriesData, bannersData, configData, languagesData] = await Promise.all([
        categoryService.getAllCategories(),
        bannerService.getActiveBanners(),
        configService.getBusinessConfig(),
        languageService.getActiveLanguages(),
      ]);
      setCategories(categoriesData);
      setBanners(bannersData);
      setBusinessConfig(configData);
      setLanguages(languagesData);
    } catch (err: any) {
      console.error('Failed to fetch data:', err);
      setError(t('common.error_loading'));
    } finally {
      setLoading(false);
      setRefreshing(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const onRefresh = useCallback(() => {
    setRefreshing(true);
    fetchData();
  }, []);

  const handleCategoryPress = (category: Category) => {
    navigation.navigate('Category', {
      categoryId: category.id,
      categoryName: category.name,
    });
  };

  const handleCallPress = () => {
    const phone = businessConfig.phone_number || businessConfig.whatsapp_number;
    if (phone) {
      Linking.openURL(`tel:${phone}`);
    }
  };

  const handleWhatsAppPress = () => {
    const whatsapp = businessConfig.whatsapp_number || businessConfig.phone_number;
    if (whatsapp) {
      const message = encodeURIComponent(t('whatsapp.greeting'));
      Linking.openURL(`https://wa.me/${whatsapp.replace(/\D/g, '')}?text=${message}`);
    }
  };

  const handleLanguageChange = (code: string) => {
    i18n.changeLanguage(code);
  };

  const renderCategory = ({ item }: { item: Category }) => (
    <TouchableOpacity
      style={styles.categoryCard}
      onPress={() => handleCategoryPress(item)}
    >
      {item.imageUrl ? (
        <Image source={{ uri: item.imageUrl }} style={styles.categoryImage} />
      ) : (
        <View style={[styles.categoryImage, styles.categoryPlaceholder]}>
          <Text style={styles.categoryPlaceholderText}>{item.name.charAt(0)}</Text>
        </View>
      )}
      <Text style={styles.categoryName}>{item.name}</Text>
    </TouchableOpacity>
  );

  const renderBanner = ({ item }: { item: Banner }) => (
    <TouchableOpacity onPress={() => item.linkUrl && Linking.openURL(item.linkUrl)}>
      <Image
        source={{ uri: item.imageUrl }}
        style={styles.banner}
        defaultSource={require('../../../assets/adaptive-icon.png')}
      />
    </TouchableOpacity>
  );

  if (loading) {
    return (
      <View style={styles.loadingContainer}>
        <ActivityIndicator size="large" color="#2c3e50" />
        <Text style={styles.loadingText}>{t('common.loading')}</Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <FlatList
        data={[{ key: 'content' }]}
        renderItem={() => (
          <View>
            {/* Language Toggle */}
            <View style={styles.languageToggle}>
              {languages.map((lang) => (
                <TouchableOpacity
                  key={lang.code}
                  style={[
                    styles.languageButton,
                    i18n.language === lang.code && styles.languageButtonActive,
                  ]}
                  onPress={() => handleLanguageChange(lang.code)}
                >
                  <Text style={styles.languageText}>{lang.code.toUpperCase()}</Text>
                </TouchableOpacity>
              ))}
            </View>

            {/* Quick Action Buttons */}
            <View style={styles.quickActions}>
              <TouchableOpacity style={styles.actionButton} onPress={handleCallPress}>
                <Text style={styles.actionButtonText}>{t('home.call_now')}</Text>
              </TouchableOpacity>
              <TouchableOpacity style={[styles.actionButton, styles.whatsappButton]} onPress={handleWhatsAppPress}>
                <Text style={styles.actionButtonText}>{t('home.whatsapp_now')}</Text>
              </TouchableOpacity>
            </View>

            {/* Banners */}
            {banners.length > 0 && (
              <FlatList
                data={banners}
                horizontal
                pagingEnabled
                showsHorizontalScrollIndicator={false}
                keyExtractor={(item) => item.id.toString()}
                renderItem={renderBanner}
              />
            )}

            {/* Categories */}
            <View style={styles.section}>
              <Text style={styles.sectionTitle}>{t('home.product_categories')}</Text>
              {error && (
                <View style={styles.errorContainer}>
                  <Text style={styles.errorText}>{error}</Text>
                  <TouchableOpacity onPress={fetchData} style={styles.retryButton}>
                    <Text style={styles.retryText}>{t('common.retry')}</Text>
                  </TouchableOpacity>
                </View>
              )}
              <View style={styles.categoriesGrid}>
                {categories.map((category) => (
                  <View key={category.id} style={styles.categoryWrapper}>
                    {renderCategory({ item: category })}
                  </View>
                ))}
              </View>
            </View>

            {/* Trust Highlights */}
            <View style={styles.section}>
              <Text style={styles.sectionTitle}>{t('home.why_choose_us')}</Text>
              <View style={styles.trustItem}>
                <Text style={styles.trustText}>✓ {t('home.quality_products')}</Text>
              </View>
              <View style={styles.trustItem}>
                <Text style={styles.trustText}>✓ {t('home.competitive_prices')}</Text>
              </View>
              <View style={styles.trustItem}>
                <Text style={styles.trustText}>✓ {t('home.fast_delivery')}</Text>
              </View>
              <View style={styles.trustItem}>
                <Text style={styles.trustText}>✓ {t('home.payment_after_delivery')}</Text>
              </View>
            </View>

            {/* Business Info */}
            {businessConfig.business_name && (
              <View style={styles.section}>
                <Text style={styles.sectionTitle}>{t('home.contact_info')}</Text>
                {businessConfig.address && (
                  <Text style={styles.infoText}>📍 {businessConfig.address}</Text>
                )}
                {businessConfig.business_hours && (
                  <Text style={styles.infoText}>🕐 {businessConfig.business_hours}</Text>
                )}
                {businessConfig.phone_number && (
                  <Text style={styles.infoText}>📞 {businessConfig.phone_number}</Text>
                )}
              </View>
            )}
          </View>
        )}
        keyExtractor={(item) => item.key}
        showsVerticalScrollIndicator={false}
        refreshControl={
          <RefreshControl refreshing={refreshing} onRefresh={onRefresh} colors={['#2c3e50']} />
        }
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f5f5f5',
  },
  loadingContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  loadingText: {
    marginTop: 10,
    color: '#2c3e50',
  },
  languageToggle: {
    flexDirection: 'row',
    justifyContent: 'flex-end',
    padding: 10,
  },
  languageButton: {
    paddingHorizontal: 15,
    paddingVertical: 5,
    marginHorizontal: 5,
    backgroundColor: '#34495e',
    borderRadius: 5,
  },
  languageButtonActive: {
    backgroundColor: '#e74c3c',
  },
  languageText: {
    color: '#fff',
    fontWeight: 'bold',
  },
  quickActions: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    padding: 15,
  },
  actionButton: {
    backgroundColor: '#e74c3c',
    paddingHorizontal: 20,
    paddingVertical: 10,
    borderRadius: 5,
    flex: 1,
    marginHorizontal: 5,
  },
  whatsappButton: {
    backgroundColor: '#25D366',
  },
  actionButtonText: {
    color: '#fff',
    textAlign: 'center',
    fontWeight: 'bold',
  },
  banner: {
    width: width,
    height: 200,
    resizeMode: 'cover',
  },
  section: {
    padding: 15,
  },
  sectionTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    marginBottom: 10,
    color: '#2c3e50',
  },
  categoriesGrid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'space-between',
    paddingBottom: 10,
  },
  categoryWrapper: {
    width: '48%',
    marginBottom: 10,
  },
  categoryCard: {
    backgroundColor: '#fff',
    borderRadius: 8,
    padding: 10,
    alignItems: 'center',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  categoryImage: {
    width: 80,
    height: 80,
    borderRadius: 8,
    marginBottom: 8,
  },
  categoryPlaceholder: {
    backgroundColor: '#34495e',
    justifyContent: 'center',
    alignItems: 'center',
  },
  categoryPlaceholderText: {
    color: '#fff',
    fontSize: 32,
    fontWeight: 'bold',
  },
  categoryName: {
    fontSize: 14,
    fontWeight: '600',
    color: '#2c3e50',
    textAlign: 'center',
  },
  trustItem: {
    paddingVertical: 5,
  },
  trustText: {
    fontSize: 16,
    color: '#27ae60',
  },
  infoText: {
    fontSize: 14,
    color: '#2c3e50',
    marginBottom: 5,
  },
  errorContainer: {
    alignItems: 'center',
    padding: 20,
  },
  errorText: {
    color: '#e74c3c',
    marginBottom: 10,
  },
  retryButton: {
    backgroundColor: '#34495e',
    paddingHorizontal: 20,
    paddingVertical: 10,
    borderRadius: 5,
  },
  retryText: {
    color: '#fff',
  },
});

export default HomeScreen;

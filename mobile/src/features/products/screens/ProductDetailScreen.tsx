import React, { useEffect, useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Image,
  Alert,
  ActivityIndicator,
} from 'react-native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RouteProp } from '@react-navigation/native';
import { RootStackParamList } from '../../../navigation';
import { productService, Product } from '../../../services/api';

type ProductDetailScreenNavigationProp = StackNavigationProp<RootStackParamList, 'ProductDetail'>;
type ProductDetailScreenRouteProp = RouteProp<RootStackParamList, 'ProductDetail'>;

interface Props {
  navigation: ProductDetailScreenNavigationProp;
  route: ProductDetailScreenRouteProp;
}

const ProductDetailScreen: React.FC<Props> = ({ navigation, route }) => {
  const { productId } = route.params;
  const [product, setProduct] = useState<Product | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    fetchProduct();
  }, [productId]);

  const fetchProduct = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await productService.getProductById(productId);
      setProduct(data);
    } catch (err) {
      setError('Failed to load product details. Please try again.');
      console.error('Error fetching product:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleCallPress = () => {
    Alert.alert('Call', 'Calling +977-1234567890...');
  };

  const handleWhatsAppPress = () => {
    if (product) {
      const productName = product.translations?.[0]?.name || product.brand;
      navigation.navigate('RequestQuote', {
        productId: product.id,
        productName,
      });
    }
  };

  const getProductName = (): string => {
    return product?.translations?.[0]?.name || product?.brand || 'Unknown Product';
  };

  const getProductDescription = (): string => {
    return product?.translations?.[0]?.description || 'No description available.';
  };

  if (loading) {
    return (
      <View style={styles.loadingContainer}>
        <ActivityIndicator size="large" color="#e74c3c" />
        <Text style={styles.loadingText}>Loading product details...</Text>
      </View>
    );
  }

  if (error || !product) {
    return (
      <View style={styles.errorContainer}>
        <Text style={styles.errorText}>{error || 'Product not found'}</Text>
        <TouchableOpacity style={styles.retryButton} onPress={fetchProduct}>
          <Text style={styles.retryButtonText}>Retry</Text>
        </TouchableOpacity>
      </View>
    );
  }

  return (
    <ScrollView style={styles.container}>
      <Image source={{ uri: product.imageUrl || 'https://via.placeholder.com/300x300?text=Product' }} style={styles.productImage} />

      <View style={styles.content}>
        <Text style={styles.productName}>{getProductName()}</Text>
        <Text style={styles.productBrand}>{product.brand}</Text>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Description</Text>
          <Text style={styles.sectionText}>{getProductDescription()}</Text>
        </View>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Technical Specifications</Text>
          <Text style={styles.sectionText}>{product.technicalSpecs || 'No specifications available.'}</Text>
        </View>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Usage Information</Text>
          <Text style={styles.sectionText}>{product.usageInfo || 'No usage information available.'}</Text>
        </View>

        <View style={styles.priceSection}>
          <Text style={styles.priceNote}>
            Prices vary daily. Confirm via call or WhatsApp.
          </Text>
          <Text style={styles.deliveryNote}>
            Payment will be collected AFTER items are delivered at your location.
          </Text>
        </View>

        <View style={styles.actionButtons}>
          <TouchableOpacity
            style={[styles.button, styles.callButton]}
            onPress={handleCallPress}
          >
            <Text style={styles.buttonText}>Call Order</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.button, styles.whatsappButton]}
            onPress={handleWhatsAppPress}
          >
            <Text style={styles.buttonText}>WhatsApp Order</Text>
          </TouchableOpacity>
        </View>
      </View>
    </ScrollView>
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
    backgroundColor: '#f5f5f5',
  },
  loadingText: {
    marginTop: 10,
    fontSize: 16,
    color: '#7f8c8d',
  },
  errorContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#f5f5f5',
    padding: 20,
  },
  errorText: {
    fontSize: 16,
    color: '#e74c3c',
    textAlign: 'center',
    marginBottom: 20,
  },
  retryButton: {
    backgroundColor: '#e74c3c',
    paddingVertical: 12,
    paddingHorizontal: 30,
    borderRadius: 5,
  },
  retryButtonText: {
    color: '#fff',
    fontSize: 16,
    fontWeight: 'bold',
  },
  productImage: {
    width: '100%',
    height: 300,
  },
  content: {
    padding: 15,
  },
  productName: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#2c3e50',
    marginBottom: 5,
  },
  productBrand: {
    fontSize: 18,
    color: '#7f8c8d',
    marginBottom: 20,
  },
  section: {
    marginBottom: 20,
  },
  sectionTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#2c3e50',
    marginBottom: 10,
  },
  sectionText: {
    fontSize: 16,
    color: '#34495e',
    lineHeight: 24,
  },
  priceSection: {
    backgroundColor: '#fff3cd',
    padding: 15,
    borderRadius: 8,
    marginBottom: 20,
    borderLeftWidth: 4,
    borderLeftColor: '#ffc107',
  },
  priceNote: {
    fontSize: 16,
    color: '#856404',
    fontStyle: 'italic',
    marginBottom: 10,
  },
  deliveryNote: {
    fontSize: 14,
    color: '#856404',
    fontWeight: 'bold',
  },
  actionButtons: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginTop: 20,
  },
  button: {
    flex: 1,
    paddingVertical: 15,
    borderRadius: 8,
    marginHorizontal: 5,
    elevation: 2,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
  },
  callButton: {
    backgroundColor: '#e74c3c',
  },
  whatsappButton: {
    backgroundColor: '#27ae60',
  },
  buttonText: {
    color: '#fff',
    textAlign: 'center',
    fontWeight: 'bold',
    fontSize: 16,
  },
});

export default ProductDetailScreen;
import React, { useEffect, useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  FlatList,
  TouchableOpacity,
  Image,
  ActivityIndicator,
} from 'react-native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RouteProp } from '@react-navigation/native';
import { RootStackParamList } from '../../../navigation';
import { productService, Product } from '../../../services/api';

type CategoryScreenNavigationProp = StackNavigationProp<RootStackParamList, 'Category'>;
type CategoryScreenRouteProp = RouteProp<RootStackParamList, 'Category'>;

interface Props {
  navigation: CategoryScreenNavigationProp;
  route: CategoryScreenRouteProp;
}

const CategoryScreen: React.FC<Props> = ({ navigation, route }) => {
  const { categoryId, categoryName } = route.params;
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    fetchProducts();
  }, [categoryId]);

  const fetchProducts = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await productService.getProductsByCategory(categoryId, {
        page: 0,
        size: 50,
        sortBy: 'id',
        sortDir: 'asc',
      });
      setProducts(response.content);
    } catch (err) {
      setError('Failed to load products. Please try again.');
      console.error('Error fetching products:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleProductPress = (product: Product) => {
    navigation.navigate('ProductDetail', { productId: product.id });
  };

  const handleCallPress = () => {
    console.log('Call for price');
  };

  const handleWhatsAppPress = (product: Product) => {
    const productName = product.translations?.[0]?.name || product.brand;
    navigation.navigate('RequestQuote', { productId: product.id, productName });
  };

  const getProductDisplayName = (product: Product): string => {
    return product.translations?.[0]?.name || product.brand || 'Unknown Product';
  };

  const getProductImage = (product: Product): string => {
    return product.imageUrl || 'https://via.placeholder.com/150?text=Product';
  };

  const renderProduct = ({ item }: { item: Product }) => (
    <TouchableOpacity
      style={styles.productCard}
      onPress={() => handleProductPress(item)}
    >
      <Image source={{ uri: getProductImage(item) }} style={styles.productImage} />
      <View style={styles.productInfo}>
        <Text style={styles.productName}>{getProductDisplayName(item)}</Text>
        <Text style={styles.productBrand}>{item.brand}</Text>
        <Text style={styles.priceNote}>Call/WhatsApp for Price</Text>
        <View style={styles.actionButtons}>
          <TouchableOpacity
            style={[styles.button, styles.callButton]}
            onPress={handleCallPress}
          >
            <Text style={styles.buttonText}>Call</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.button, styles.whatsappButton]}
            onPress={() => handleWhatsAppPress(item)}
          >
            <Text style={styles.buttonText}>WhatsApp</Text>
          </TouchableOpacity>
        </View>
      </View>
    </TouchableOpacity>
  );

  if (loading) {
    return (
      <View style={styles.loadingContainer}>
        <ActivityIndicator size="large" color="#e74c3c" />
        <Text style={styles.loadingText}>Loading products...</Text>
      </View>
    );
  }

  if (error) {
    return (
      <View style={styles.errorContainer}>
        <Text style={styles.errorText}>{error}</Text>
        <TouchableOpacity style={styles.retryButton} onPress={fetchProducts}>
          <Text style={styles.retryButtonText}>Retry</Text>
        </TouchableOpacity>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <FlatList
        data={products}
        keyExtractor={(item) => item.id.toString()}
        renderItem={renderProduct}
        contentContainerStyle={styles.listContainer}
        showsVerticalScrollIndicator={false}
        ListEmptyComponent={
          <View style={styles.emptyContainer}>
            <Text style={styles.emptyText}>No products found in this category</Text>
          </View>
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
  emptyContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    paddingVertical: 50,
  },
  emptyText: {
    fontSize: 16,
    color: '#7f8c8d',
    textAlign: 'center',
  },
  listContainer: {
    padding: 10,
  },
  productCard: {
    backgroundColor: '#fff',
    borderRadius: 8,
    marginVertical: 5,
    padding: 15,
    flexDirection: 'row',
    elevation: 2,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
  },
  productImage: {
    width: 80,
    height: 80,
    borderRadius: 8,
    marginRight: 15,
  },
  productInfo: {
    flex: 1,
  },
  productName: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#2c3e50',
    marginBottom: 5,
  },
  productBrand: {
    fontSize: 14,
    color: '#7f8c8d',
    marginBottom: 5,
  },
  priceNote: {
    fontSize: 12,
    color: '#e74c3c',
    fontStyle: 'italic',
    marginBottom: 10,
  },
  actionButtons: {
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  button: {
    flex: 1,
    paddingVertical: 8,
    paddingHorizontal: 15,
    borderRadius: 5,
    marginHorizontal: 2,
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
    fontSize: 12,
  },
});

export default CategoryScreen;
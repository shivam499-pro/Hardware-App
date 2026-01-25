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

type CategoryScreenNavigationProp = StackNavigationProp<RootStackParamList, 'Category'>;
type CategoryScreenRouteProp = RouteProp<RootStackParamList, 'Category'>;

interface Props {
  navigation: CategoryScreenNavigationProp;
  route: CategoryScreenRouteProp;
}

interface Product {
  id: number;
  name: string;
  brand: string;
  image: string;
}

const CategoryScreen: React.FC<Props> = ({ navigation, route }) => {
  const { categoryId, categoryName } = route.params;
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Mock data - replace with API call
    const mockProducts: Product[] = [
      { id: 1, name: 'OPC Cement 50kg', brand: 'Shree Cement', image: 'https://via.placeholder.com/150?text=Cement' },
      { id: 2, name: 'TMT Steel Bars 12mm', brand: 'JSW Steel', image: 'https://via.placeholder.com/150?text=Steel' },
      { id: 3, name: 'Red Clay Bricks', brand: 'Local Brand', image: 'https://via.placeholder.com/150?text=Bricks' },
    ];

    setTimeout(() => {
      setProducts(mockProducts);
      setLoading(false);
    }, 1000);
  }, [categoryId]);

  const handleProductPress = (product: Product) => {
    navigation.navigate('ProductDetail', { productId: product.id });
  };

  const handleCallPress = () => {
    console.log('Call for price');
  };

  const handleWhatsAppPress = () => {
    navigation.navigate('RequestQuote', { productId: undefined, productName: undefined });
  };

  const renderProduct = ({ item }: { item: Product }) => (
    <TouchableOpacity
      style={styles.productCard}
      onPress={() => handleProductPress(item)}
    >
      <Image source={{ uri: item.image }} style={styles.productImage} />
      <View style={styles.productInfo}>
        <Text style={styles.productName}>{item.name}</Text>
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
            onPress={handleWhatsAppPress}
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

  return (
    <View style={styles.container}>
      <FlatList
        data={products}
        keyExtractor={(item) => item.id.toString()}
        renderItem={renderProduct}
        contentContainerStyle={styles.listContainer}
        showsVerticalScrollIndicator={false}
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
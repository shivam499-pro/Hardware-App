import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Image,
  Alert,
} from 'react-native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RouteProp } from '@react-navigation/native';
import { RootStackParamList } from '../../../navigation';

type ProductDetailScreenNavigationProp = StackNavigationProp<RootStackParamList, 'ProductDetail'>;
type ProductDetailScreenRouteProp = RouteProp<RootStackParamList, 'ProductDetail'>;

interface Props {
  navigation: ProductDetailScreenNavigationProp;
  route: ProductDetailScreenRouteProp;
}

const ProductDetailScreen: React.FC<Props> = ({ navigation, route }) => {
  const { productId } = route.params;

  // Mock product data - replace with API call
  const product = {
    id: productId,
    name: 'OPC Cement 50kg',
    brand: 'Shree Cement',
    image: 'https://via.placeholder.com/300x300?text=Cement',
    description: 'High quality Ordinary Portland Cement suitable for all construction needs.',
    technicalSpecs: 'Grade: 43, Packing: 50kg bags, Compressive Strength: 43 MPa',
    usage: 'Ideal for concrete, mortar, plastering, and general construction work.',
  };

  const handleCallPress = () => {
    Alert.alert('Call', 'Calling +977-1234567890...');
  };

  const handleWhatsAppPress = () => {
    navigation.navigate('RequestQuote', {
      productId: product.id,
      productName: product.name,
    });
  };

  return (
    <ScrollView style={styles.container}>
      <Image source={{ uri: product.image }} style={styles.productImage} />

      <View style={styles.content}>
        <Text style={styles.productName}>{product.name}</Text>
        <Text style={styles.productBrand}>{product.brand}</Text>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Description</Text>
          <Text style={styles.sectionText}>{product.description}</Text>
        </View>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Technical Specifications</Text>
          <Text style={styles.sectionText}>{product.technicalSpecs}</Text>
        </View>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Usage Information</Text>
          <Text style={styles.sectionText}>{product.usage}</Text>
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
import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';

// Import screens
import HomeScreen from 'features/home/screens/HomeScreen';
import CategoryScreen from 'features/categories/screens/CategoryScreen';
import ProductDetailScreen from 'features/products/screens/ProductDetailScreen';
import RequestQuoteScreen from 'features/quotes/screens/RequestQuoteScreen';
import ContactScreen from 'features/contact/screens/ContactScreen';
import AboutScreen from 'features/about/screens/AboutScreen';

export type RootStackParamList = {
  Home: undefined;
  Category: { categoryId: number; categoryName: string };
  ProductDetail: { productId: number };
  RequestQuote: { productId?: number; productName?: string };
  Contact: undefined;
  About: undefined;
};

const Stack = createStackNavigator<RootStackParamList>();

const AppNavigator: React.FC = () => {
  return (
    <NavigationContainer>
      <Stack.Navigator
        initialRouteName="Home"
        screenOptions={{
          headerStyle: {
            backgroundColor: '#2c3e50',
          },
          headerTintColor: '#fff',
          headerTitleStyle: {
            fontWeight: 'bold',
          },
        }}
      >
        <Stack.Screen
          name="Home"
          component={HomeScreen}
          options={{ title: 'Manish Hardware' }}
        />
        <Stack.Screen
          name="Category"
          component={CategoryScreen}
          options={({ route }) => ({ title: route.params.categoryName })}
        />
        <Stack.Screen
          name="ProductDetail"
          component={ProductDetailScreen}
          options={{ title: 'Product Details' }}
        />
        <Stack.Screen
          name="RequestQuote"
          component={RequestQuoteScreen}
          options={{ title: 'Request Quote' }}
        />
        <Stack.Screen
          name="Contact"
          component={ContactScreen}
          options={{ title: 'Contact & Location' }}
        />
        <Stack.Screen
          name="About"
          component={AboutScreen}
          options={{ title: 'About Us' }}
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
};

export default AppNavigator;
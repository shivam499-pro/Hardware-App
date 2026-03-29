import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import Icon from 'react-native-vector-icons/Ionicons';

// Import screens
import HomeScreen from 'features/home/screens/HomeScreen';
import CategoryScreen from 'features/categories/screens/CategoryScreen';
import ProductDetailScreen from 'features/products/screens/ProductDetailScreen';
import RequestQuoteScreen from 'features/quotes/screens/RequestQuoteScreen';
import ContactScreen from 'features/contact/screens/ContactScreen';
import AboutScreen from 'features/about/screens/AboutScreen';

// New Screens
import SearchScreen from 'features/search/screens/SearchScreen';
import CartScreen from 'features/cart/screens/CartScreen';
import ProfileScreen from 'features/profile/screens/ProfileScreen';
import LoginScreen from 'features/profile/screens/LoginScreen';
import RegisterScreen from 'features/profile/screens/RegisterScreen';
import CartCheckoutScreen from 'features/cart/screens/CartCheckoutScreen';

export type RootStackParamList = {
  Home: undefined; // Home will now correspond to the TabNavigator
  Category: { categoryId: number; categoryName: string };
  ProductDetail: { productId: number };
  RequestQuote: { productId?: number; productName?: string };
  CartCheckout: undefined;
  Login: undefined;
  Register: undefined;
  Contact: undefined;
  About: undefined;
};

const Stack = createStackNavigator<RootStackParamList>();
const Tab = createBottomTabNavigator();

// Create the Bottom Tab Navigator
const MainTabs = () => {
  const cartItemCount = require('react-redux').useSelector((state: any) => state.cart?.items?.length || 0);
  
  return (
    <Tab.Navigator
      screenOptions={({ route }) => ({
        tabBarIcon: ({ focused, color, size }) => {
          let iconName = 'help-circle-outline';

          if (route.name === 'HomeTab') {
            iconName = focused ? 'home' : 'home-outline';
          } else if (route.name === 'SearchTab') {
            iconName = focused ? 'search' : 'search-outline';
          } else if (route.name === 'CartTab') {
            iconName = focused ? 'cart' : 'cart-outline';
          } else if (route.name === 'ProfileTab') {
            iconName = focused ? 'person' : 'person-outline';
          }

          return <Icon name={iconName} size={size} color={color} />;
        },
        tabBarActiveTintColor: '#e74c3c',
        tabBarInactiveTintColor: 'gray',
        headerStyle: {
          backgroundColor: '#2c3e50',
        },
        headerTintColor: '#fff',
        headerTitleStyle: {
          fontWeight: 'bold',
        },
      })}
    >
      <Tab.Screen 
        name="HomeTab" 
        component={HomeScreen} 
        options={{ title: 'Home', tabBarLabel: 'Home' }} 
      />
      <Tab.Screen 
        name="SearchTab" 
        component={SearchScreen} 
        options={{ title: 'Search', tabBarLabel: 'Search' }} 
      />
      <Tab.Screen 
        name="CartTab" 
        component={CartScreen} 
        options={{ 
          title: 'Quote Cart', 
          tabBarLabel: 'Cart',
          tabBarBadge: cartItemCount > 0 ? cartItemCount : undefined,
          tabBarBadgeStyle: { backgroundColor: '#e74c3c' },
        }} 
      />
      <Tab.Screen 
        name="ProfileTab" 
        component={ProfileScreen} 
        options={{ title: 'Profile', tabBarLabel: 'Profile' }} 
      />
    </Tab.Navigator>
  );
};

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
          component={MainTabs}
          options={{ headerShown: false }} // Hide Header since Tabs have their own header
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
          name="CartCheckout"
          component={CartCheckoutScreen}
          options={{ title: 'Checkout Details' }}
        />
        <Stack.Screen
          name="Login"
          component={LoginScreen}
          options={{ title: 'Log In' }}
        />
        <Stack.Screen
          name="Register"
          component={RegisterScreen}
          options={{ title: 'Create Account' }}
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